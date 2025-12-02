public class Doctor extends Person {

  // constructor
  public Doctor(String name, String email, String phone){
    super(email, name, phone);
  }

  @Override
  public String toString(){
    return "Dr. " + this.getName() ;
  }
}
