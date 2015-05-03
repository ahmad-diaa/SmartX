package models;
/**
 * Created by zamzamy on 4/30/15.
 */
public class Plug {
        /**
         * The id of the user having the plug.
         */
        int user_id;
        /**
         * The name of the plug.
         */
        String name;
        /**
         * The status of the plug: on or off.
         */
        String status;
        /**
         * The id of the room to which the plug belongs.
         */
        int room_id;
        /**
         * The primary key of the plug.
         */
        String plug_id;
        /**
         * The name of the photo belonging to the plug.
         */
        String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
         * Constructor to create a plug with given parameters.
         *
         * @param name     the type of the plug.
         * @param roomID   the id of the room to which the plug belongs.
         * @param userID   the id of the user having the plug.
         * @param plugID the primary key of plug.
         * @param status   the status of the plug: on or off.
         */
        public Plug(String name, int roomID, int userID, String plugID, String status, String photo) {
            this.name = name;
            this.room_id = roomID;
            this.user_id = userID;
            this.status = status;
            this.plug_id = plugID;
            this.photo = photo;
        }

        /**
         * get the id of the user having the plug.
         *
         * @return the id of the user.
         */
        public int getUserID() {
            return user_id;
        }

        /**
         * set the id of the user having the plug.
         *
         * @param userID the id of the user.
         */
        public void setUserID(int userID) {
            this.user_id = userID;
        }

        /**
         * get the primary key of plug.
         *
         * @return id of the plug.
         */
        public String getPlugID() {
            return plug_id;
        }

        /**
         * set get the primary key of device.
         *
         * @param plugID the id of plug.
         */
        public void setPlugID(String plugID) {
            this.plug_id = plugID;
        }

        /**
         * get type of the plug.
         *
         * @return type of the plug.
         */
        public String getName() {
            return name;
        }

        /**
         * set type of the plug.
         *
         * @param name the type of the plug.
         */
        public void setName(String name) {
            this.name = name;
        }

        /**
         * get the status of the plug: on or off.
         *
         * @return status of the plug.
         */
        public String getStatus() {
            return status;
        }

        /**
         * set the status of the plug: on or off.
         *
         * @param status
         */
        public void setStatus(String status) {
            this.status = status;
        }

        /**
         * get the id of the room to which the plug belongs.
         *
         * @return id of the room.
         */
        public int getRoomID() {
            return room_id;
        }

        /**
         * set the id of the room to which the plug belongs.
         *
         * @param roomID the id of the room.
         */
        public void setRoomID(int roomID) {
            this.room_id = roomID;
        }
    }

