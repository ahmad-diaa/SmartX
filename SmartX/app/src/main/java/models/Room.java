package models;


/**
 *SE Sprint1
 *Room.java
 *
 * @author Amir
 */

public class Room {
    /**
     * The primary key of the room.
     */
    private int id ;

    /**
     * The name of the room.
     */
    private String name;

    /**
     * An image for the room.
     */
    private String photo = "Photo";

    /**
     * set an image for the room.
     *
     * @param photo image for the room.
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    /**
     * get id of the room.
     *
     * @return id the primary key of the room.
     */
    public int getId() {
        return id;
    }

    /**
     * get name of the room.
     *
     * @return name of the room.
     */
    public String getName() {
        return name;
    }

    /**
     * get image of the room.
     *
     * @return photo of the room.
     */
    public String getPhoto() {
        return photo;
    }


    /**
     * set name for the room
     *
     * @param name of the room
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set id of the room
     *
     * @param id the primary key of the room
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Constructor to create a room with given name
     *
     * @param roomName the name of the room.
     */
    public Room(String roomName) {
        name = roomName;
    }

}
