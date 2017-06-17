package edu.upc.eetac.dsa.eetakemongoandroid.Model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Ignacio on 15/05/2017.
 */
public class User implements Serializable {
    private String name;
    private String surname;
    private String username;
    private String password;
    private String email;
    private int rol;
    private String image;
    private List<Eetakemon> eetakemons;

    public User() {
    }

    public String getName() {
        return name;
    }

    public int getRol() {
        return rol;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Eetakemon> getEetakemons() {
        return eetakemons;
    }

    public void setEetakemons(List<Eetakemon> eetakemons) {
        this.eetakemons = eetakemons;
    }
}