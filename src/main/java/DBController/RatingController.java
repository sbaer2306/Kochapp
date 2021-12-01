package DBController;

import Datastructures.Recipe;
import Datastructures.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class RatingController extends DBConnectionController{

    public RatingController(){
        super();
    }

    //like == true --> user hat geliked
    //like == false --> dislike
    public boolean rateRecipe(UserModel user, Recipe recipe, boolean like){

        String username=user.getUsername();
        String recipeid= recipe.getId();

        String sql = "INSERT INTO user_recipes_ratings (user_uid, recipe_rid, liked) VALUES (?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1,username);
            pstmt.setString(2, recipeid);

            int liked=0;
            if(like) liked=1;

            pstmt.setString(3, String.valueOf(liked));

            pstmt.execute();

            //likes in recipes Tabelle um eins erhöhen
            String currentLikes= getRecipeRatingCount(recipe, like); //altuelle Like Anzahl
            int newLikeCount = Integer.parseInt(currentLikes);
            newLikeCount++; //Erhöhen

            return updateRecipeLikes(recipe,newLikeCount,like); //aktualisieren

        }
        //PK bereits vorhanden --> SQLIntegrityConstraintViolationException
        //--> d.h.: User hat bereits bewertet
        //TODO: woanders catchen und entsprechende Fehlermelndung ausgeben
        catch(SQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            return false;
        }
        catch (SQLException e ) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updateRecipeLikes(Recipe recipe, int newLikeCount, boolean like) throws SQLException {

        String sql= "UPDATE recipes SET likes=? WHERE recipe_rid=?";
        if(!like)sql = "UPDATE recipes SET dislikes=? WHERE recipe_rid=?";

         PreparedStatement pstmt = connection.prepareStatement(sql);

            //pstmt.setString(1,column);
            pstmt.setString(1, String.valueOf(newLikeCount));
            pstmt.setString(2, recipe.getId());

            return pstmt.execute();
    }

    //Falls Strings als Übergabeparameter
    //Wenn möglich obere Funktion nehmen
    public boolean rateRecipe(String username, String recipeid, boolean like){
        UserModel user= new UserModel();
        user.setUsername(username);

        Recipe recipe= new Recipe();
        recipe.setId(recipeid);

        return rateRecipe(user, recipe, like);
    }

    //getLikes == true --> gibt Like Anzahl zurück
    //getLikes == false --> gibt Dislike Anzahl zurück
    public String getRecipeRatingCount(Recipe recipe, boolean getLikes) throws SQLException {
        String count="";

        String sql= "select  * from recipes WHERE recipe_rid=?";

        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, recipe.getId());

        ResultSet res = pstmt.executeQuery();

        while(res.next()) {
            if(getLikes)count= res.getString("likes");
            else count=res.getString("dislikes");
        }

        return count;
    }

    public boolean undoRating(UserModel user, Recipe recipe, boolean likedRecipe) throws SQLException {

        String sql="DELETE FROM user_recipes_ratings WHERE user_uid=? AND recipe_rid=?";
        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, recipe.getId());

        String temp= getRecipeRatingCount(recipe, likedRecipe);
        int newLikeCount= Integer.parseInt(temp);
        if(likedRecipe)newLikeCount--;

        pstmt.executeQuery();
        return updateRecipeLikes(recipe, newLikeCount, likedRecipe);
    }
}
