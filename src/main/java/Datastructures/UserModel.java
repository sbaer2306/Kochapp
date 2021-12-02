package Datastructures;

import Clerks.HashingClerk;
import DBController.RatingDBController;

import java.util.ArrayList;

public class UserModel {
    private String username;
    private String email;
    private String pwdHash;

    private ArrayList<String> likedRecipeIDs;
    private ArrayList<String> dislikedRecipeIDs;



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

        //Bewertungen holen
        RatingDBController peter= new RatingDBController();
        this.likedRecipeIDs = peter.getLikedRecipeIDs(this.username);
        this.dislikedRecipeIDs = peter.getDislikedRecipeIDs(this.username);

        //falls Fehler bei der Abfrage
        if(likedRecipeIDs == null) likedRecipeIDs= new ArrayList<>();
        if(dislikedRecipeIDs == null) dislikedRecipeIDs= new ArrayList<>();
    }

    public UserModel() {
        //um mit Gettern und Settern zu arbeiten falls nötig
    }

    public ArrayList<String> getLikedRecipeIDs() {
        return likedRecipeIDs;
    }

    public void setLikedRecipeIDs(ArrayList<String> likedRecipeIDs) {
        this.likedRecipeIDs = likedRecipeIDs;
    }

    public ArrayList<String> getDislikedRecipeIDs() {
        return dislikedRecipeIDs;
    }

    public void setDislikedRecipeIDs(ArrayList<String> dislikedRecipeIDs) {
        this.dislikedRecipeIDs = dislikedRecipeIDs;
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
