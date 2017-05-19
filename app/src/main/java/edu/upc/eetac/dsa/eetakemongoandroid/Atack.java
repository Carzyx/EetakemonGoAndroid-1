package edu.upc.eetac.dsa.eetakemongoandroid;

/**
 * Created by Ignacio on 29/04/2017.
 */
public class Atack {

    private int id;
    private String name;
    private EetakemonType type;
    private int damageBase;
    private String description;

    public Atack() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EetakemonType getType() {
        return type;
    }

    public void setType(EetakemonType type) {
        this.type = type;
    }

    public int getDamageBase() {
        return damageBase;
    }

    public void setDamageBase(int damagebase) {
        this.damageBase = damagebase;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
