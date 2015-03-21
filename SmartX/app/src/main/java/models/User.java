package models;

public class User {
    private int id;
    private String name;
    private String password;
    public User(String name,String password) {
        this.name=name;
        this.password=password;
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
}