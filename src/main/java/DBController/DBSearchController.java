package DBController;

import Clerks.ErrorClerk;
import Datastructures.Recipe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBSearchController extends DBConnectionController{
    public DBSearchController(){
        super();
    }
    //Simple Search Query for Recipe Title
    public ArrayList<Recipe> searchQuery(String buzzword, int returnQuantity) throws SQLException, IOException {
        String sql = "select  *, (CAST(likes AS SIGNED) - CAST(dislikes AS SIGNED)) AS difference  from recipes where title LIKE '%"+ buzzword + "%' ORDER BY difference DESC LIMIT " + returnQuantity;
        return this.getRecipesFromSQLStatement(sql);
    }
    //Get Top 5 Recipes for Startingpage
    public ArrayList<Recipe> getTopFiveRecipes() throws SQLException, IOException{
        String sql ="SELECT *, (CAST(likes AS SIGNED) - CAST(dislikes AS SIGNED)) AS difference FROM `recipes` ORDER BY difference DESC LIMIT 5";
        return this.getRecipesFromSQLStatement(sql);
    }
    public ArrayList<Recipe> extendedSearchQuery(String buzzword, String priceMax, int durationMax,String difficulty, ArrayList<String> categories) throws SQLException, IOException{
        StringBuilder SBsql = new StringBuilder();
        SBsql.append("SELECT *, (CAST(likes AS SIGNED) - CAST(dislikes AS SIGNED)) AS difference FROM `recipes` ");
        if(!priceMax.isEmpty()){
            SBsql.append( "WHERE (ingredients_cost BETWEEN 0.00 AND " + priceMax+   " ) ");
        }
        if(!buzzword.isEmpty()){
            SBsql.append( "AND (title LIKE '%"+ buzzword + "%' ) ");
        }
        SBsql.append("AND (duration BETWEEN 0 AND " + durationMax + " ) ");
        SBsql.append(  "AND (difficulty_did ='" + difficulty + "' ) ");
        StringBuilder SBcategories = new StringBuilder();
        if(!categories.isEmpty()){
            if(!(categories.get(0)=="")){
                for(String s:categories){
                    SBcategories.append("AND recipes.recipe_rid in ( select recipe_rid from recipe_categories where recipe_categories.category_name_cid = '" + s + "')");
                }
            }
        }
        String sql = SBsql.toString() + SBcategories.toString() + "ORDER BY difference DESC LIMIT 5";
        System.out.println(sql);
        return this.getRecipesFromSQLStatement(sql);
    }
    //Search a recipe by using a simple sql statement
    private ArrayList<Recipe> getRecipesFromSQLStatement(String sql){
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
            while (resultSet.next()) {
                Recipe recipe = new Recipe();
                recipe.setId(resultSet.getString("recipe_rid"));
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
                String sqlCategories = "SELECT * FROM `recipe_categories` WHERE `recipe_rid`=" +"\""+recipe.getId()+"\"";
                Statement statement1 = connection.createStatement();
                ResultSet resultSetCategories = statement1.executeQuery(sqlCategories);
                ArrayList<String> categories = new ArrayList<String>();
                while (resultSetCategories.next()) {
                    categories.add(resultSetCategories.getString(2));
                }
                recipe.setCategories(categories);
                recipeArrayList.add(recipe);
            }
            return recipeArrayList;
        }catch (SQLException sqlException){
            ErrorClerk.getInstance().showErrorMessage(sqlException.toString());
            return null;
        }
        catch (IOException e){
            ErrorClerk.getInstance().showErrorMessage(e.toString());
            return null;
        }
    }
    
}
