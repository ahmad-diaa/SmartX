package models;

/**
 *SE Sprint1
 *Type.java
 *
 * @author Amir
 */

public class Type {

    /**
     * The available type for device.
     */
    private String name;
    /**
     * The type of device's clicker.
     */
    private int clickerType;

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

    /**
     * get the type for device's clicker.
     *
     * @return the type of device's clicker.
     */
    public int getClickerType() {
        return clickerType;
    }
}
