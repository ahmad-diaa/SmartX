package models;

/**
 *SE Sprint1
 *Type.java
 *
 * @author Amir
 * @author youmna
 */

public class Type {

    /**
     * The available type for device.
     */
    private String name;

    /**
     * The id for device.
     */
    private  int id;
    /**
     * creates new instance of Types model
     */
    public Type(String name ){
        this.name = name;
    }
    /**
     * set id of device.
     *
     */

    public void setId(int id) {
        this.id = id;
    }
    /**
     * get available id for device.
     *
     * @return the id for device.
     */
    public int getId() {

        return id;
    }

    /**
     * get available type for device.
     *
     * @return the available type for device.
     */
    public String getName() {
        return name;
    }

    /**
     * set available type for device.
     *
     * @param name the available type for device.
     */
    public void setName(String name) {
        this.name = name;
    }



}
