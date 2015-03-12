package models;


public class Room {
    private String id;
    private String name;
    private String photo;

    public String get_roomName() {
        return name;
    }

    public void set_roomName(String roomName) {
        this.name = roomName;
    }

    public String get_id() {
        return id;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Room(String roomName) {

        name = roomName;
    }

    public Room() {
    }
}
