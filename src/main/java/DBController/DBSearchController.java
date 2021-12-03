package DBController;

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
    public ArrayList<Recipe> extendedSearchQuery(String buzzword, String priceMin, String priceMax, int durationMin, int durationMax,String difficulty, ArrayList<String> categories) throws SQLException, IOException{
        StringBuilder SBsql = new StringBuilder();
        SBsql.append("SELECT *, (CAST(likes AS SIGNED) - CAST(dislikes AS SIGNED)) AS difference FROM `recipes` ");
        SBsql.append( "WHERE (ingredients_cost BETWEEN " + priceMin + " AND " + priceMax+   " ) ");
        if(!buzzword.isEmpty()){
            SBsql.append( "AND (title LIKE '%"+ buzzword + "%' ) ");
        }
        SBsql.append("AND (duration BETWEEN " + durationMin + " AND " + durationMax + " ) ");
        SBsql.append(  "AND (difficulty_did ='" + difficulty + "' ) ");
        StringBuilder SBcategories = new StringBuilder();
        if(!categories.isEmpty()){
            for(String s:categories){
               SBcategories.append("AND recipes.recipe_rid in ( select recipe_rid from recipe_categories where recipe_categories.category_name_cid = '" + s + "')");
            }
        }
        String sql = SBsql.toString() + SBcategories.toString() + "ORDER BY difference DESC";
        System.out.println(sql);
        return this.getRecipesFromSQLStatement(sql);
    }
    //Search a recipe by using a simple sql statement
    private ArrayList<Recipe> getRecipesFromSQLStatement(String sql) throws SQLException, IOException {
        ResultSet resultSet = statement.executeQuery(sql);
        //get Columncount to loop through
        ResultSetMetaData metadata = resultSet.getMetaData();
        int columnCount = metadata.getColumnCount();
        ArrayList<Recipe> recipeArrayList = new ArrayList<Recipe>();
        while (resultSet.next()) {
            Recipe recipe = new Recipe();
            for (int i = 1; i <= columnCount; i++) {
                //Create Recipe objekt by looping through the resultset and using the setter methods
                switch (i) {
                    case 1:
                        recipe.setId(resultSet.getString(i));
                        break;
                    case 2:
                        recipe.setTitle(resultSet.getString(i));
                        break;
                    case 3:
                        recipe.setImage(resultSet.getBlob(i));
                        break;
                    case 4:
                        recipe.setPortions(resultSet.getString(i));
                        break;
                    case 5:
                        recipe.setIngredients(resultSet.getString(i));
                        break;
                    case 6:
                        recipe.setDescription(resultSet.getString(i));
                        break;
                    case 7:
                        recipe.setDuration(resultSet.getString(i));
                        break;
                    case 8:
                        recipe.setIngredientsCost(resultSet.getString(i));
                        break;
                    case 9:
                        recipe.setLikes(resultSet.getString(i));
                        break;
                    case 10:
                        recipe.setDislikes(resultSet.getString(i));
                        break;
                    case 11:
                        recipe.setCreationTime(resultSet.getString(i));
                        break;
                    case 12:
                        recipe.setDifficulty(resultSet.getString(i));
                        break;
                    case 13:
                        recipe.setAuthor(resultSet.getString(i));
                        break;
                    default:
                        break;
                }

            }
            //get categories specified by the id from the resultset before
            String sqlCategories = "SELECT * FROM `recipe_categories` WHERE `recipe_rid`=" + recipe.getId();
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
    }
    //TODO extenden Search
}
