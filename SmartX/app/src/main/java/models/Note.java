package models;

/**
 * Created by maggiemoheb on 4/7/15.
 */
public class Note {
    int userID;
    String body;
    int roomID;
    String deviceID;
    int noteID;
    public Note() {

    }

    public Note(int userID, int roomID, String deviceID, int noteID, String body) {
        this.userID = userID;
        this.roomID = roomID;
        this.deviceID = deviceID;
        this.noteID = noteID;
        this.body = body;
    }

    public int getUserID() {
        return this.userID;
    }
    public int getRoomID(){
        return this.roomID;
    }
    public String getDeviceID(){
        return this.deviceID;
    }
    public int getNoteID(){
        return this.noteID;
    }
    public String getBody(){
        return this.body;
    }
    public void setUserID(int userID){
        this.userID = userID;
    }
    public void setRoomID(int roomID){
        this.roomID = roomID;
    }
    public void setDeviceID(String deviceID){
        this.deviceID = deviceID;
    }
    public void setNoteID(int noteID){
        this.noteID = noteID;
    }
    public void setBody(String body){
        this.body = body;
    }
}
