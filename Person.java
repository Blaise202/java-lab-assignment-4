public abstract class Person {
  private String email;
  private String name;
  private String phone;

  public Person(String email, String name, String phone){
    this.email = email;
    this.name = name;
    this.phone = phone;
  }

  public String getEmail(){
    return email;
  }
  public String getName(){
    return name;
  }
  public String getPhone(){
    return phone;
  }

  @Override
  public String toString(){
    return name + " ("+ email +")";
  }
}
