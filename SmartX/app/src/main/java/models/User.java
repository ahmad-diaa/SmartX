package models;

public class User {
    private int id;
    private String name;
    private String password;
    private String email;

    public User(String name,String password, String email) {
        this.name=name;
        this.password=password;
        this.email=email;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setID(int ID) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
