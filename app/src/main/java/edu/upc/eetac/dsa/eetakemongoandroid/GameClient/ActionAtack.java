package edu.upc.eetac.dsa.eetakemongoandroid.GameClient;

/**
 * Created by Miguel Angel on 13/06/2017.
 */
public class ActionAtack {

    private String atackName;
    private int damageBase;

    public ActionAtack(String atackName, int damageBase){
        this.atackName = atackName;
        this.damageBase = damageBase;
    }

    public String getAtackName() {
        return atackName;
    }

    public void setAtackName(String atackName) {
        this.atackName = atackName;
    }

    public int getDamageBase() {
        return damageBase;
    }

    public void setDamageBase(int damageBase) {
        this.damageBase = damageBase;
    }
}
