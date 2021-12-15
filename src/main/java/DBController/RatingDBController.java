package DBController;

import Datastructures.RecipeComment;
import Datastructures.Recipe;
import Datastructures.UserModel;

import javax.xml.stream.events.Comment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatingDBController extends DBConnectionController {

    public RatingDBController() {
        super();
    }

    //Bewerten

    //Like von user auf likedRecipe eintragen
    public boolean insertNewLike(UserModel user, Recipe likedRecipe) {
        return rateRecipe(user, likedRecipe, true);
    }

    //Like von user auf dislikedRecipe eintragen
    public boolean insertNewDisLike(UserModel user, Recipe dislikedRecipe) {
        return rateRecipe(user, dislikedRecipe, false);
    }

    //like == true --> user hat geliked
    //like == false --> dislike
    private boolean rateRecipe(UserModel user, Recipe recipe, boolean like) {

        String username = user.getUsername();
        String recipeid = recipe.getId();

        String sql = "INSERT INTO user_recipes_ratings (user_uid, recipe_rid, liked) VALUES (?,?,?)";
        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, username);
            pstmt.setString(2, recipeid);

            //tinyint in DB --> 0 gleich false, positive Werte gleich true
            //--> liked == Wert in DB
            int liked = 1;
            if (!like) liked = 0;
            pstmt.setString(3, String.valueOf(liked));

            int affected = pstmt.executeUpdate(); //Die beeinflussten Rows in der DB
            boolean statementSuccessfull = (affected == 1);

            //likes in recipes Tabelle um eins erhöhen und user
            if (like) return (statementSuccessfull && incrementLikes(recipe.getId()));
            return (statementSuccessfull && incrementDislikes(recipe.getId()));

        } catch (SQLException e) {
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

        return affected == 1;

    }

    private boolean incrementDislikes(String recipeId) {
        String sql = "UPDATE recipes SET dislikes=dislikes+1 WHERE recipe_rid=?";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, recipeId);
            int affected = pstmt.executeUpdate();

            return affected == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    //Ratings rückgängig machen

    //Like zurücknehmen
    public boolean revokeLike(UserModel user, Recipe recipe) throws SQLException {
        return undoRating(user, recipe, true);
    }

    //Dislike zurücknehmen
    public boolean revokeDislike(UserModel user, Recipe recipe) throws SQLException {
        return undoRating(user, recipe, false);
    }

    private boolean undoRating(UserModel user, Recipe recipe, boolean likedRecipe) throws SQLException {

        String sql = "DELETE FROM user_recipes_ratings WHERE user_uid=? AND recipe_rid=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, user.getUsername());
        pstmt.setString(2, recipe.getId());

        int affected = pstmt.executeUpdate(); //affected --> betroffene rows in der DB

        if (likedRecipe) return (affected == 1 && decrementLikes(recipe.getId()));
        else return (affected == 1 && decrementDislikes(recipe.getId()));
    }

    private boolean decrementLikes(String recipeId) {
        String sql = "UPDATE recipes SET likes=likes-1 WHERE recipe_rid=?";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, recipeId);
            int affected = pstmt.executeUpdate();

            return affected == 1;
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

            return affected == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }


    //Vom User bewertete Rezept IDs erhalten
    public ArrayList<String> getLikedRecipeIDs(String user_uid){
        return getRatedRecipeIDs(user_uid, true);
    }

    public ArrayList<String> getDislikedRecipeIDs(String user_uid){
        return getRatedRecipeIDs(user_uid, false);
    }

    private ArrayList<String> getRatedRecipeIDs(String userID, boolean like) {
        ArrayList<String> likes = new ArrayList<>();

        String sql = "SELECT recipe_rid FROM user_recipes_ratings WHERE user_uid=? AND liked=?";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, userID);

            String l = "1";
            if (!like) l = "0";

            pstmt.setString(2, l);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                likes.add(resultSet.getString("recipe_rid"));
            }
            return likes;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }
    }


    //Kommentieren
    public boolean insertComment(RecipeComment comment) throws SQLException {

        String sql = "INSERT INTO users_recipes_comments (recipe_rid,author_uid,text) VALUES (?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, comment.getRecipe_rid());
        pstmt.setString(2, comment.getAuthor_uid());
        pstmt.setString(3, comment.getText());

        int affected = pstmt.executeUpdate();

        return (affected == 1);
    }

    //Löscht den Kommentar über die ID
    public boolean deleteComment(RecipeComment comment) throws SQLException {

        String sql = "DELETE FROM users_recipes_comments WHERE comment_id=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1,comment.getCommentID());

        int affected = pstmt.executeUpdate();

        return (affected == 1);
    }

    public ArrayList<RecipeComment> getUsersComments(String username) {

        String sql = "SELECT * FROM users_recipes_comments WHERE author_uid=?";

        PreparedStatement pstmt = null;
        try {
           pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, username);

        ResultSet resultSet = pstmt.executeQuery();

        return getCommentList(resultSet);

        } catch (SQLException e) {
        return null;
    }
    }

    //Holt die num neusten Kommentare zu recipe aus der DB und gibt sie zurück
    //num= 10 --> 10 neuste Kommentare
    public ArrayList<RecipeComment> getRecipesLatestComments(Recipe recipe, int num){
        String sql = "SELECT * FROM users_recipes_comments WHERE recipe_rid=? ORDER BY creation_time DESC LIMIT ?";

        PreparedStatement pstmt = null;
        try {
           pstmt = connection.prepareStatement(sql);


            pstmt.setString(1, recipe.getId());
            pstmt.setInt(2, num);

            ResultSet resultSet = pstmt.executeQuery();

            return getCommentList(resultSet);

        } catch (SQLException e) {
            return null;
        }
    }

    //Helferfunktion
    private ArrayList<RecipeComment> getCommentList(ResultSet resultSet) throws SQLException {
    ArrayList<RecipeComment> comments= new ArrayList<>();

        return getCommentList(resultSet);

        } catch (SQLException e) {
        return null;
    }
    }

    //Holt die num neusten Kommentare zu recipe aus der DB und gibt sie zurück
    //num= 10 --> 10 neuste Kommentare
    public ArrayList<RecipeComment> getRecipesLatestComments(Recipe recipe, int num){
        String sql = "SELECT * FROM users_recipes_comments WHERE recipe_rid=? ORDER BY creation_time DESC LIMIT ?";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);


            pstmt.setString(1, recipe.getId());
            pstmt.setInt(2, num);

            ResultSet resultSet = pstmt.executeQuery();

            return getCommentList(resultSet);

        } catch (SQLException e) {
            return null;
        }
    }

    //Helferfunktion
    private ArrayList<RecipeComment> getCommentList(ResultSet resultSet) throws SQLException {
    ArrayList<RecipeComment> comments= new ArrayList<>();

        while (resultSet.next()) {
            RecipeComment comment= new RecipeComment();

            comment.setCommentID(resultSet.getString("comment_id"));
            comment.setAuthor_uid(resultSet.getString("author_uid"));
            comment.setRecipe_rid(resultSet.getString("recipe_rid"));
            comment.setText(resultSet.getString("text"));

            comments.add(comment);
        }
        if(comments.size() > 0 )return comments;
        return null;
    }
}

