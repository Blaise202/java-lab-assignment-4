import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Date;

public class Patient extends Person {
  // attributes
  private int patientId;
  private ArrayList<MedicalRecord> medicalHistory;
  private TreeMap<Date, Appointment> appointments;

  // constructor
  public Patient(int patientId, String name, String email, String phone){
    super(email, name, phone);
    this.patientId = patientId;
    this.medicalHistory = new ArrayList<>();
    this.appointments = new TreeMap<>();
  }

  // getters
  public int getPatientId(){
    return patientId;
  }
  public ArrayList<MedicalRecord> getMedicalHistory(){
    return medicalHistory;
  }
  public TreeMap<Date, Appointment> getAppointments(){
    return appointments;
  }

  // methods to add records and appointments
  public void addMedicalRecord(MedicalRecord record){
    this.medicalHistory.add(record);
  }
  public void addAppointment(Date date, Appointment appointment){
    this.appointments.put(date, appointment);
  }

}
