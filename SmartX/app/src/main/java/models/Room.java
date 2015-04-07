package models;

public class Room {
    private String id ;
    private String name;
    private String user_id;

    public String get_roomName() {
        return name;
    }

    public void set_roomName(String roomName) {
        this.name = roomName;
    }

    public String get_id() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Room(String roomName ,String roomID) {
        name = roomName;
        id  = roomID;
    }

    public Room() {
    }
}
