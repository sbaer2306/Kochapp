package DBController;

import Clerks.ErrorClerk;
import Datastructures.Recipe;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class DBSearchController extends DBConnectionController{
    public DBSearchController(){
        super();
    }
    //Simple Search Query for Recipe Title
    public ArrayList<Recipe> searchQuery(String buzzword, int returnQuantity) throws SQLException, IOException {
        String sql = "select  *, (CAST(likes AS SIGNED) - CAST(dislikes AS SIGNED)) AS difference  from recipes where title LIKE ? ORDER BY difference DESC LIMIT ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, "%" + buzzword + "%");
        pstmt.setInt(2,returnQuantity);
        return this.getRecipesFromSQLStatement(pstmt);
    }
    //Get Top 5 most liked recipes for startingpage
    public ArrayList<Recipe> getTopFiveRecipes() throws SQLException, IOException{
        String sql ="SELECT *, (CAST(likes AS SIGNED) - CAST(dislikes AS SIGNED)) AS difference FROM `recipes` ORDER BY difference DESC LIMIT 5";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        return this.getRecipesFromSQLStatement(pstmt);
    }
    //get recipe of the week based on most liked recipe of the last week
    public Recipe getRecipeOfTheWeek() throws SQLException, IOException{
        String sql ="SELECT * FROM recipes WHERE recipe_rid=(SELECT recipe_rid FROM user_recipes_ratings WHERE (liked_time between date_sub(now(),INTERVAL 1 WEEK) and now()) AND liked=1 GROUP BY recipe_rid ORDER BY COUNT(*) DESC LIMIT 1)";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        Recipe recipe = new Recipe();
        ArrayList<Recipe> recipes = this.getRecipesFromSQLStatement(pstmt);
        if(recipes.isEmpty()) {
            sql="SELECT * FROM recipes LIMIT 1";
            recipe=this.getRecipesFromSQLStatement(pstmt).get(0);
        }
        else {
            recipe=recipes.get(0);
        }
        return recipe;
    }
    //make extendedsearch based on parameters of extended search
    public ArrayList<Recipe> extendedSearchQuery(String buzzword, String priceMax, int durationMax,String difficulty, ArrayList<String> categories) throws SQLException, IOException{
        StringBuilder SBsql = new StringBuilder();
        SBsql.append("SELECT *, (CAST(likes AS SIGNED) - CAST(dislikes AS SIGNED)) AS difference FROM `recipes` ");
        SBsql.append("WHERE TRUE ");
        if(!priceMax.equals("0")){
            SBsql.append( "AND (ingredients_cost BETWEEN 0.00 AND " + priceMax+   " ) ");
        }
        if(!buzzword.isEmpty()){
            SBsql.append( "AND (title LIKE '%"+ buzzword + "%' ) ");
        }
        if(durationMax!=0){
            SBsql.append("AND (duration BETWEEN 0 AND " + durationMax + " ) ");
        }
        if(!difficulty.isEmpty()){
            SBsql.append(  "AND (difficulty_did ='" + difficulty + "' ) ");
        }
        StringBuilder SBcategories = new StringBuilder();
        if(!categories.isEmpty()){
            if(!(categories.get(0)=="")){
                for(String s:categories){
                    SBcategories.append("AND recipes.recipe_rid in ( select recipe_rid from recipe_categories where recipe_categories.category_name_cid = '" + s + "')");
                }
            }
        }
        String sql = SBsql.toString() + SBcategories.toString() + "ORDER BY difference DESC LIMIT 5";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        return this.getRecipesFromSQLStatement(pstmt);
    }
    //Search a recipe by using a simple sql statement
    private ArrayList<Recipe> getRecipesFromSQLStatement(PreparedStatement preparedStatement){
        try {
            ResultSet resultSet = preparedStatement.executeQuery();
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
                String sqlCategories = "SELECT * FROM `recipe_categories` WHERE `recipe_rid`=?";
                PreparedStatement pstmt = connection.prepareStatement(sqlCategories);
                pstmt.setString(1,recipe.getId());
                ResultSet resultSetCategories = pstmt.executeQuery();
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
