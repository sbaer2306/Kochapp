package DBController;

import Datastructures.FavoriteInformation;
import Datastructures.Recipe;
import Datastructures.RecipeComment;
import Datastructures.UserModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RatingDBController extends DBConnectionController {

    public RatingDBController() {
        super();
    }

    //Bewerten

    //Like
    public boolean insertNewLike(UserModel user, Recipe likedRecipe) {
        return rateRecipe(user, likedRecipe, true);
    }

    //Dislike
    public boolean insertNewDisLike(UserModel user, Recipe dislikedRecipe) {
        return rateRecipe(user, dislikedRecipe, false);
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

            int liked = 1;
            if (!like) liked = 0;
            pstmt.setString(3, String.valueOf(liked));

            int affected = pstmt.executeUpdate(); //Die beeinflussten Rows in der DB
            boolean statementSuccessfull = (affected == 1);

            //likes in recipes Tabelle um eins erh??hen und user
            if (like) return (statementSuccessfull && incrementLikes(recipe.getId()));
            return (statementSuccessfull && incrementDislikes(recipe.getId()));

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Ratings r??ckg??ngig machen

    //Like zur??cknehmen
    public boolean revokeLike(UserModel user, Recipe recipe) throws SQLException {
        return undoRating(user, recipe, true);
    }

    //Dislike zur??cknehmen
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

    //L??scht den Kommentar ??ber die ID
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

    //Holt die num neusten Kommentare zu recipe aus der DB und gibt sie zur??ck
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

    //Kommentarerstellung
    private ArrayList<RecipeComment> getCommentList(ResultSet resultSet) throws SQLException {
    ArrayList<RecipeComment> comments= new ArrayList<>();

        while (resultSet.next()) {
            RecipeComment comment= new RecipeComment();

            comment.setCommentID(resultSet.getString("comment_id"));
            comment.setAuthor_uid(resultSet.getString("author_uid"));
            comment.setRecipe_rid(resultSet.getString("recipe_rid"));
            comment.setText(resultSet.getString("text"));
            comment.setDatetime(resultSet.getString("creation_time"));

            comments.add(comment);
        }
        if(comments.size() > 0 )return comments;
        return null;
    }

    //Favorisieren
    public boolean insertFavorite(FavoriteInformation fav) throws SQLException {

        String sql= "INSERT INTO users_recipes_favorites (recipe_rid, username_uid) VALUES (?,?)";

        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, fav.getRecipeId());
        pstmt.setString(2, fav.getOwnerId());

        int affected= pstmt.executeUpdate();

        return (affected==1);
    }

    public boolean deleteFavorite(FavoriteInformation fav) throws SQLException {

        String sql= "DELETE FROM users_recipes_favorites WHERE recipe_rid=? AND username_uid=?";

        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, fav.getRecipeId());
        pstmt.setString(2, fav.getOwnerId());

        int affected= pstmt.executeUpdate();

        return (affected==1);
    }

    public ArrayList<FavoriteInformation> getUsersFavorites(String username) {

        String sql = "SELECT users_recipes_favorites.username_uid, users_recipes_favorites.recipe_rid, users_recipes_favorites.added_time, recipes.title " +
                "FROM users_recipes_favorites "+
                "INNER JOIN recipes ON users_recipes_favorites.recipe_rid=recipes.recipe_rid WHERE username_uid=?;";

        PreparedStatement pstmt = null;
        try {
            pstmt = connection.prepareStatement(sql);

            pstmt.setString(1, username);

            ResultSet resultSet = pstmt.executeQuery();

            return getFavoriteList(resultSet);

        } catch (SQLException e) {
            return null;
        }
    }

    private ArrayList<FavoriteInformation> getFavoriteList(ResultSet resultSet) throws SQLException {
        ArrayList<FavoriteInformation> favs = new ArrayList<>();

        while (resultSet.next()){
            FavoriteInformation fav = new FavoriteInformation();
            fav.setOwnerId(resultSet.getString("username_uid"));
            fav.setRecipeId(resultSet.getString("recipe_rid"));
            fav.setRecipeTitle(resultSet.getString("title"));
            fav.setAddedDatetime(resultSet.getString("added_time"));

            favs.add(fav);
        }

        return favs;
    }

}

