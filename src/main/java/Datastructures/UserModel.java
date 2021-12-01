package Datastructures;

import Clerks.HashingClerk;

public class UserModel {
    private String username;
    private String email;
    private String pwdHash;

    //TODO: Beim Login evtl noch speichern welche Rezepte geliked und/oder
    //Favorisiert wurden
    //--> später schnellere Darstellung auf dem UI

    //Für die Registrierung
    public UserModel(String username, String email, String pwd) {
        HashingClerk alan = new HashingClerk();
        this.username = username;
        this.email = email;
        this.pwdHash = alan.hash(pwd);
    }

    //Für den Login
    public UserModel(String username, String pwd) {
        HashingClerk carl = new HashingClerk();
        this.username = username;
        this.pwdHash = carl.hash(pwd);
    }

    public UserModel() {
        //um mit Gettern und Settern zu arbeiten falls nötig
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwdHash() {
        return pwdHash;
    }

    public void setPwdHash(String pwdHash) {
        this.pwdHash = pwdHash;
    }
}
