package DBController;

import Datastructures.Recipe;
import Datastructures.UsersRecipeInformation;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RecipeDBController extends DBConnectionController{
    public RecipeDBController(){
        super();
    }


    public boolean InsertRecipe(Recipe recipe) throws SQLException, FileNotFoundException {
        String sql = "INSERT INTO recipes (recipe_rid,title,image,portions,ingredients,description,duration,ingredients_cost,author_uid,difficulty_did) VALUES (?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        String id = createRecipeID(recipe.getAuthor(),recipe.getTitle());
        recipe.setId(id);
        pstmt.setString(1, id);
        pstmt.setString(2, recipe.getTitle());


        //zum Einfügen des Bilds
        File imageFile= recipe.getImageFile();
        System.out.println(imageFile.exists());
        FileInputStream fileInputStream= new FileInputStream(imageFile);
        pstmt.setBinaryStream(3, fileInputStream);

        pstmt.setString(4, recipe.getPortions() );
        pstmt.setString(5, recipe.getIngredients());
        pstmt.setString(6, recipe.getDescription());
        pstmt.setString(7,recipe.getDuration());
        pstmt.setString(8,recipe.getIngredientsCost());
        pstmt.setString(9,recipe.getAuthor());
        pstmt.setString(10,recipe.getDifficulty());
        int affected= pstmt.executeUpdate();

        boolean insertedCategories= true;
        if(recipe.getCategories() != null && recipe.getCategories().size()>0 )  insertedCategories= insertRecipeCategories(recipe);

        return (affected==1) && insertedCategories;

    }

    private String createRecipeID(String author, String title) {
        return author+title;
    }

    private boolean insertRecipeCategories(Recipe recipe) throws SQLException {

        ArrayList<String> categories = recipe.getCategories();
        String rid= recipe.getId();

        String sql="INSERT INTO recipe_categories(recipe_rid, category_name_cid) VALUES (?,?)";


        //append to sql
        for (int i = 1; i<categories.size(); i++) {
            sql+=",(?,?)";
        }

        PreparedStatement pstmt = connection.prepareStatement(sql);

        int paramIndex=1;

        for (String cat:
                categories) {
            pstmt.setString(paramIndex, rid);
            paramIndex++;
            pstmt.setString(paramIndex, cat);
            paramIndex++;
        }

        return pstmt.executeUpdate()==categories.size();
    }

    //Um ein Rezept nur über die ID zu laden (da FavoriteInformation und RecipeComment nur die ID speichern -->
    // nützlich für die Accountverwaltung, bzw die Verwaltung der Kommentare und Favoriten)
    public Recipe getRecipeByID(String recipeId) throws SQLException, IOException {
        Recipe recipe= new Recipe();

        String sql = "SELECT * FROM recipes WHERE recipe_rid=?";

        PreparedStatement pstmt= connection.prepareStatement(sql);
        pstmt.setString(1, recipeId);

        ResultSet resultSet= pstmt.executeQuery();

        while (resultSet.next()){
            recipe.setId(recipeId);
            recipe.setTitle(resultSet.getString("title"));
            recipe.setImage(resultSet.getBlob("image"));
            recipe.setPortions(resultSet.getString("portions"));
            recipe.setIngredients(resultSet.getString("ingredients"));
            recipe.setDescription(resultSet.getString("description"));
            recipe.setDuration(resultSet.getString("duration"));
            recipe.setIngredientsCost(resultSet.getString("ingredients_cost"));
            recipe.setLikes(resultSet.getString("likes"));
            recipe.setDislikes(resultSet.getString("dislikes"));
            recipe.setCreationTime(resultSet.getString("creation_time"));
            recipe.setDifficulty(resultSet.getString("difficulty_did"));
            recipe.setAuthor(resultSet.getString("author_uid"));
        }

        recipe.setCategories(getRecipesCategories(recipeId));

        return recipe;
    }

    private ArrayList<String> getRecipesCategories(String recipeId) throws SQLException {

        String sql = "SELECT * FROM recipe_categories WHERE recipe_rid=?";

        PreparedStatement preparedStatement= connection.prepareStatement(sql);

        preparedStatement.setString(1,recipeId);

        ResultSet res = preparedStatement.executeQuery();

        ArrayList<String> categories = new ArrayList<>();

        while(res.next()){
            categories.add(res.getString("category_name_cid"));
        }

        return categories;

    }

    public boolean deleteRecipeById(String recipeId) throws SQLException {

        String sql= "DELETE FROM recipes WHERE recipe_rid=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        pstmt.setString(1, recipeId);

        return (pstmt.executeUpdate() == 1);
    }

    //gibt die Informationen zum vom Nutzer erstellen Objekt zurück
    public ArrayList<UsersRecipeInformation> getUsersRecipeInformation(String username) throws SQLException {
        String sql = "SELECT recipe_rid, title, creation_time  FROM recipes WHERE author_uid=?";

        PreparedStatement preparedStatement= connection.prepareStatement(sql);

        preparedStatement.setString(1,username);

        ResultSet res = preparedStatement.executeQuery();

        ArrayList<UsersRecipeInformation> recInfo = new ArrayList<>();

        while(res.next()){
            UsersRecipeInformation info = new UsersRecipeInformation(res.getString("recipe_rid"),
                    res.getString("title"),
                    res.getString("creation_time"));
            recInfo.add(info);
        }

        return recInfo;
    }

}
