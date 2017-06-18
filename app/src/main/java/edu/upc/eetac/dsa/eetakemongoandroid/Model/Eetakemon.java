package edu.upc.eetac.dsa.eetakemongoandroid.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Miguel Angel on 13/03/2017.
 */
public class Eetakemon implements Serializable {

    private String name;
    private int level;
    private int ps;
    private EetakemonType type;
    private String image;
    private String description;
    private List<Atack> atacks;

    public Eetakemon() {
    }

    public Eetakemon(String name, int level, int ps, EetakemonType type, String image,
                     String descrption) {
        this.type = type;
        this.name = name;
        this.level = level;
        this.ps = ps;
        this.image = image;
        this.description = descrption;
    }

    public EetakemonType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public int getPs() {
        return ps;
    }

    public List<Atack> getAtacks() {
        return atacks;
    }

    public void setType(EetakemonType type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setPs(int ps) {
        this.ps = ps;
    }

    public void setAtacks(List<Atack> atacks) {
        this.atacks = atacks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
/*public int getDamage(int id, Eetakemon eetakemon){
        int typeDamage=1;
        AtacksEetakemon eetackemonatack=new AtacksEetakemon();
        for (int i=0;i<4;i++){
            if(atacks[i].getIdatack()==id)
                eetackemonatack=atacks[i];
        }
        return eetackemonatack.damageBase*typeDamage*level;
    }*/

}
