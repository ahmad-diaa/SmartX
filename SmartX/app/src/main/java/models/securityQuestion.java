package models;


/**
 * SE Sprint2
 * securityQuestion.java
 *
 * @author Ahmad Abdalraheem
 */

public class securityQuestion {
    private String securityQ;
    private int id;

    public securityQuestion(String s, int i) {
        securityQ = s;
        id = i;
    }

    /**
     * get the id of the user.
     * return the user's id.
     */

    public int getId() {
        return id;
    }

    /**
     * set the id of the user.
     *
     * @param id the new id for the user.
     */

    public void setId(int id) {
        this.id = id;
    }

    /**
     * get security question of the user.
     */
    public String getSecurityQ() {
        return securityQ;
    }

    /**
     * set the security question of the user.
     *
     * @param q the new id for the user.
     */
    public void setSecurityQ(String q) {
        this.securityQ = q;
    }


}
