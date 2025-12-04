import java.util.Scanner;
import java.io.IOException;

public class Main {
  public static void main(String[] args) {

    ClinicManagementSystem clinicMS = new ClinicManagementSystem();
    Scanner scanner = new Scanner(System.in);
    boolean isRunning = true;

    System.out.println("Initializing system...");
    while(isRunning){
      System.out.println("---Natembea Clinic Management System--- \n");
      System.out.println("Choose the action: \n");
      System.out.println("1. Add New Patient");
      System.out.println("2. Schedule Appointment");
      System.out.println("3. Generate Patient Report");
      System.out.println("4. Generate Daily Appointment Report");
      System.out.println("5. Save Data to File");
      System.out.println("6. Load Data from File");
      System.out.println("7. Exit");
      System.out.print("Enter choice: ");

      int choice = scanner.nextInt();

      switch(choice){
        case 1:
          System.out.print("Enter Name: ");
          String name = scanner.nextLine();
          System.out.print("Enter Email: ");
          String email = scanner.nextLine();
          System.out.print("Enter Phone: ");
          String phone = scanner.nextLine();
          clinicMS.addPatient(name, email, phone);
          break;
        case 2:
          System.out.print("Enter Patient ID: ");
          int pId = scanner.nextInt();
          scanner.nextLine(); // Consume newline
          System.out.print("Date (YYYY-MM-DD): ");
          String date = scanner.nextLine();
          System.out.print("Time (HH:MM): ");
          String time = scanner.nextLine();
          System.out.print("Doctor Name: ");
          String doc = scanner.nextLine();
          System.out.print("Type/Description: ");
          String type = scanner.nextLine();
          clinicMS.scheduleAppointment(pId, date, time, doc, type);
          break;
        case 3:
          System.out.print("Enter patient ID:");
          int ptId = scanner.nextInt();
          System.out.println(clinicMS.generatePatientReport(ptId));
        case 4:
          System.out.print("Enter Date (YYYY-MM-DD): ");
          String rDate = scanner.nextLine();
          System.out.println(clinicMS.generateDailyAppointments(rDate));
          break;
        case 5:
          try{
            clinicMS.saveToFile("data/patients.txt");
            System.out.println("Data saved to patients.txt");
          }catch(IOException ex){
            System.out.println("File Error: " + ex.getMessage());
          }
          break;
        case 6:
          try{
            clinicMS.loadFromFile("data/patients.txt");
            System.out.println("Data loaded from patients.txt");
          }catch(IOException ex){
            System.out.println("File Error: " + ex.getMessage());
          }
          break;
        case 7:
          isRunning = false;
          System.out.println("Exiting system. Goodbye!");
          break;
        default:
          System.out.println("Invalid Input! Try again?");
      }
    }
    scanner.close();
  }
}
