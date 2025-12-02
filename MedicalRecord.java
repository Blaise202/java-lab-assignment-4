public class MedicalRecord {
  public String date;
  public String description;

  public MedicalRecord(String date, String description){
    this.date = date;
    this.description = date;
  }

  public String getDate(){
    return date;
  }
  public String getDescription(){
    return description;
  }

  @Override
  public String toString() {
      return "[" + date + "] " + description;
  }
}
