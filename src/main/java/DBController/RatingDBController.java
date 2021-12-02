package DBController;

import Datastructures.Recipe;
import Datastructures.UserModel;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RatingDBController extends DBConnectionController{

    public RatingDBController(){
        super();
    }

    //Bewerten

    //Like von user auf likedRecipe eintragen
    public boolean insertNewLike(UserModel user, Recipe likedRecipe){
        return rateRecipe(user,likedRecipe,true);
    }

    //Like von user auf dislikedRecipe eintragen
    public boolean insertNewDisLike(UserModel user, Recipe dislikedRecipe){
        return rateRecipe(user,dislikedRecipe,false);
    }

    //like == true --> user hat geliked
    //like == false --> dislike
    private boolean rateRecipe(UserModel user, Recipe recipe, boolean like) {

        String username=user.getUsername();
        String recipeid= recipe.getId();

        String sql = "INSERT INTO user_recipes_ratings (user_uid, recipe_rid, liked) VALUES (?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1,username);
            pstmt.setString(2, recipeid);

            //tinyint in DB --> 0 gleich false, positive Werte gleich true
            //--> liked == Wert in DB
            int liked=1;
            if(!like)liked=0;
            pstmt.setString(3, String.valueOf(liked));

            int affected= pstmt.executeUpdate(); //Die beeinflussten Rows in der DB
            boolean statementSuccessfull= (affected==1);

            //likes in recipes Tabelle um eins erhöhen und user
            if(like) return ( statementSuccessfull && incrementLikes(recipe.getId()));
            return ( statementSuccessfull && incrementDislikes(recipe.getId()));

        }
        catch (SQLException e ) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean incrementLikes(String recipeId) throws SQLException {
        String sql = "UPDATE recipes SET likes=likes+1 WHERE recipe_rid=?";

        PreparedStatement pstmt = null;

            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, recipeId);
            int affected = pstmt.executeUpdate();

            return affected==1;

    }

    private boolean incrementDislikes(String recipeId) {
        String sql = "UPDATE recipes SET dislikes=dislikes+1 WHERE recipe_rid=?";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, recipeId);
            int affected = pstmt.executeUpdate();

            return affected==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    //Ratings rückgängig machen

    //Like zurücknehmen
    public boolean revokeLike(UserModel user, Recipe recipe) throws SQLException {
        return undoRating(user,recipe,true);
    }

    //Dislike zurücknehmen
    public boolean revokeDislike(UserModel user, Recipe recipe) throws SQLException {
        return undoRating(user,recipe,false);
    }

    private boolean undoRating(UserModel user, Recipe recipe, boolean likedRecipe) throws SQLException {

        String sql="DELETE FROM user_recipes_ratings WHERE user_uid=? AND recipe_rid=?";
        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, recipe.getId());

        int affected= pstmt.executeUpdate(); //affected --> betroffene rows in der DB

        if(likedRecipe) return (affected==1 && decrementLikes(recipe.getId()));
        else return (affected==1 && decrementDislikes(recipe.getId()));
    }

    private boolean decrementLikes(String recipeId) {
        String sql = "UPDATE recipes SET likes=likes-1 WHERE recipe_rid=?";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, recipeId);
            int affected = pstmt.executeUpdate();

            return affected==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    private boolean decrementDislikes(String recipeId) {
        String sql = "UPDATE recipes SET dislikes=dislikes-1 WHERE recipe_rid=?";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, recipeId);
            int affected = pstmt.executeUpdate();

            return affected==1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }
}
