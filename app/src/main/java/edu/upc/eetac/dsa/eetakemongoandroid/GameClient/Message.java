package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

/**
 * Created by Miguel Angel on 13/06/2017.
 */
public class Message {

    private String action;
    private String userHome;
    private String userGuest;

    public Message(String action, String usernameHome, String usernameGuest) {
        this.action = action;
        this.userHome = usernameHome;
        this.userGuest = usernameGuest;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }


    public String getUserHome() {
        return userHome;
    }

    public void setUserHome(String userHome) {
        this.userHome = userHome;
    }

    public String getUserGuest() {
        return userGuest;
    }

    public void setUserGuest(String userGuest) {
        this.userGuest = userGuest;
    }
}
