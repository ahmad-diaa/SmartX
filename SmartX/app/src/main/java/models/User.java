package models;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;
    private String phone;


    public User(String name,String password,String email, String phone) {
        this.name=name;
        this.password=password;
        this.email = email ;
        this.phone = phone;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public  String getEmail() { return email; }

    public String getPassword() {
        return password;
    }

    public void setID(int ID) {
        this.id = id;
    }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
