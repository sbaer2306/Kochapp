package Datastructures;

import Clerks.HashingClerk;
import DBController.RatingDBController;
import DBController.RecipeDBController;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {
    private String username;
    private String email;
    private String pwdHash;

    private ArrayList<String> likedRecipeIDs;
    private ArrayList<String> dislikedRecipeIDs;
    private ArrayList<RecipeComment> recipeComments;
    ArrayList<FavoriteInformation> favorites;

    ArrayList<UsersRecipeInformation> usersRecipes;

    public UserModel() {}

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

        //Kommentare holen
        this.recipeComments=peter.getUsersComments(this.username);
        //Favoriten holen
        this.favorites= peter.getUsersFavorites(this.username);

        //Informationen zu den vom User hochgeladenen Rezepten holen
        loadAndSetUsersRecipesInformation();

        //falls Fehler bei der Abfrage
        if(likedRecipeIDs == null) likedRecipeIDs= new ArrayList<>();
        if(dislikedRecipeIDs == null) dislikedRecipeIDs= new ArrayList<>();
    }

    public void loadAndSetUsersRecipesInformation()  {
        RecipeDBController peter= new RecipeDBController();
        try {
            this.usersRecipes = peter.getUsersRecipeInformation(this.username);
        } catch (SQLException e) {
            usersRecipes= null;
            System.out.println("User hat keine Rezepte hochgeladen: "+e);
        }
    }

    //Getter und Setter
    public ArrayList<String> getLikedRecipeIDs() {
        return likedRecipeIDs;
    }

    public ArrayList<String> getDislikedRecipeIDs() {
        return dislikedRecipeIDs;
    }

    public ArrayList<RecipeComment> getRecipeComments() {
        return recipeComments;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPwdHash() {
        return pwdHash;
    }
}
