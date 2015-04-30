package models;

/**
 * Created by ahmad on 30/04/15.
 */
public class securityQuestion {
    private String securityQ;
    private int id;
    public securityQuestion(String s , int i ){
        securityQ =s ;
        id = i;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecurityQ() {
        return securityQ;
    }
}
