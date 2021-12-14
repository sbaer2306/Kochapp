package DBController;

import Datastructures.Recipe;
import javafx.scene.image.Image;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class DBInsertController extends DBConnectionController{
    public DBInsertController(){
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

    public boolean deleteRecipe(Recipe recipe) throws SQLException {

        String sql= "DELETE FROM recipes WHERE recipe_rid=?";
        PreparedStatement pstmt = connection.prepareStatement(sql);

        if(recipe.getId()!= null) {
            pstmt.setString(1, recipe.getId());
        }
        else {
            pstmt.setString(1, createRecipeID(recipe.getAuthor(),recipe.getTitle()));
        }
        boolean success= (pstmt.executeUpdate() == 1);
        return success;
    }
}
