public class Appointment {
  private int patientId;
  private String date;
  private String time; 
  private String doctorName;
  private String type;

  public Appointment(int patientId, String date, String time, String doctorName, String type){
    this.patientId = patientId;
    this.date = date;
    this.time = time;
    this.doctorName = doctorName;
    this.type = type;
  }

  public int getPatientId(){
    return patientId;
  }
  public String getDate(){
    return date;
  }
  public String getTime(){
    return time;
  }
  public String getDoctorName(){
    return doctorName;
  }
  public String getType(){
    return type;
  }

  @Override
  public String toString() {
    return date + " " + time + ": " + type + " with " + doctorName;
  }
}
