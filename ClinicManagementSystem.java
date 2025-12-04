import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

import interfaces.FileOperations;
import interfaces.ReportGenerator;

public class ClinicManagementSystem implements FileOperations, ReportGenerator {

  private HashMap<Integer, Patient> patients;
  private ArrayList<Doctor> doctors;
  private TreeMap<String, ArrayList<Appointment>> appointmentsByDate;
  private static int nextPatientId = 1;


  public ClinicManagementSystem(){
    this.patients = new HashMap<>();
    this.doctors = new ArrayList<>();
    this.appointmentsByDate = new TreeMap<>();

    // initiate some doctors
    doctors.add(new Doctor("Prince", "prince@ashesiclinic.com", "0536433234"));
    doctors.add(new Doctor("Kwasi", "kwasi@ashesiclinic.com", "0234217364"));
  }

  // helper to add patient
  public void addPatient(String name, String email, String phone){
    Patient patient = new Patient(nextPatientId++, name, email, phone);
    patients.put(patient.getPatientId(), patient);
    System.out.println("Patient added successfully. ID: " + patient.getPatientId());
  }

  // internal helper for adding the patient
  public void addPatient(int id, String name, String email, String phone){
    Patient patient = new Patient(id, name, email, phone);
    patients.put(id, patient);
    // Ensure ID counter stays ahead of loaded IDs
    if (id >= nextPatientId) {
        nextPatientId = id + 1;
    }
  }

  public Patient findPatient(int patientId) {
    return patients.get(patientId);
  }

  public void scheduleAppointment(int patientId, String date, String time, String doctorName, String type) {
    Patient patient = findPatient(patientId);
    if (patient == null) {
        System.out.println("Error: Patient not found.");
        return;
    }
    
    Appointment apt = new Appointment(patientId, date, time, doctorName, type);

    // 1. Add to Patient's sorted TreeMap (Key: Date object combining date + time)
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    try {
        Date fullDate = sdf.parse(date + " " + time);
        patient.addAppointment(fullDate, apt);
    } catch (ParseException e) {
        System.out.println("Error parsing date/time for sorting: " + e.getMessage());
        // Fallback: use current date or handle error
        patient.addAppointment(new Date(), apt); 
    }

    // 2. Add to System's TreeMap (Key: String date YYYY-MM-DD)
    appointmentsByDate.putIfAbsent(date, new ArrayList<>());
    appointmentsByDate.get(date).add(apt);

    System.out.println("Appointment scheduled.");
  }

  @Override
  public void saveToFile(String filename) throws IOException{
    try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
      for (Patient p : patients.values()) {
          // Format: ID,Name,Email,Phone
          writer.println(p.getPatientId() + "," + p.getName() + "," + p.getEmail() + "," + p.getPhone());
      }
    }
  }
  

  @Override
  public void loadFromFile(String filename) throws IOException {
    try (Scanner scanner = new Scanner(new File(filename))) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        // Simple CSV parsing
        String[] parts = line.split(",");
        if (parts.length >= 4) {
          int id = Integer.parseInt(parts[0].trim());
          String name = parts[1].trim();
          String email = parts[2].trim();
          String phone = parts[3].trim();
          addPatient(id, name, email, phone);
        }
      }
    }
  }

  @Override
  public String generatePatientReport(int patientId){
    Patient pt = findPatient(patientId);
    if(pt == null) return "Patient Not found";

    StringBuilder report = new StringBuilder();

    report.append("=== PATIENT REPORT ===\n");
    report.append("ID: ").append(pt.getPatientId()).append("\n");
    report.append("Name: ").append(pt.getName()).append("\n");
    report.append("Email: ").append(pt.getEmail()).append("\n");
    report.append("Phone: ").append(pt.getPhone()).append("\n");

    report.append("\n-- Appointments (Sorted by Date) --\n");

    for(Appointment apt: pt.getAppointments().values()){
      report.append(apt).append("\n");
    }

    report.append("\n-- Medical History --\n");
    for (MedicalRecord rec : pt.getMedicalHistory()) {
      report.append(rec).append("\n");
    }

    return report.toString();

  }


  @Override
  public String generateDailyAppointments(String date){

    StringBuilder report = new StringBuilder();

    report.append("=== APPOINTMENTS FOR ").append(date).append(" ===\n");

    ArrayList<Appointment> apts = appointmentsByDate.get(date);
    if(apts == null || apts.isEmpty()){
      report.append("No appointments scheduled. \n");
    }else{
      for(Appointment apt : apts){
        Patient pt = findPatient(apt.getPatientId());
        String pName = (pt != null) ? pt.getName() : "Unkown ID";
        report.append(apt.getTime())
              .append(" - Patient: ")
              .append(pName)
              .append(" - Dr.")
              .append(apt.getDoctorName()).append("(")
              .append(apt.getType())
              .append(")\n");
      }
    }

    return report.toString();

  }

}
