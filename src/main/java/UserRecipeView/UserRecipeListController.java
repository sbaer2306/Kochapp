package UserRecipeView;

import DBController.RecipeDBController;
import Datastructures.Recipe;
import Datastructures.UsersRecipeInformation;
import Session.UserSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import recipeView.RecipeViewController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRecipeListController {

    ArrayList<UsersRecipeInformation> recipeList;
    RecipeDBController rdbController = new RecipeDBController();
    UserRecipeListViewController viewController;


    public UserRecipeListController(){
        try{
            recipeList = rdbController.getUsersRecipeInformation(new UserSession().getUserSession().getUsername());
        } catch(SQLException ignored){}
        FXMLLoader loader = new FXMLLoader(getClass().getResource("userRecipeList.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Eigene Rezepte");
        try{
            stage.setScene(new Scene(loader.load()));
        } catch(IOException ignored){}
        viewController = loader.getController();
        
        for (int i = 0; i < recipeList.size(); i++){
            addPreview(recipeList.get(i).getRecipeTitle(), i);
        }
        stage.show();
    }

    public void deleteRecipe(int i){
        try{
            rdbController.deleteRecipeById(recipeList.get(i).getRecipeId());
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void showFullRecipe(int i){
        Recipe recipe = null;
        try {
            recipe = new RecipeDBController().getRecipeByID(recipeList.get(i).getRecipeId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlRecipeView = new FXMLLoader(getClass().getResource("/recipeView/recipeView.fxml"));
        ScrollPane root = null;
        try {
            root = fxmlRecipeView.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        RecipeViewController con  = fxmlRecipeView.getController();
        con.setRecipe(recipe);


        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle(recipe.getTitle());
        stage.setScene(scene);

        con.showRecipe();
        stage.show();
    }

    public void addPreview(String title, int index){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipePreview.fxml"));
        try {
            GridPane preview = loader.load();
            PreviewController pc = loader.getController();
            pc.setListController(this);
            pc.setChildID(index);
            pc.setTitle(title);

            viewController.addPreview(preview);
        } catch (IOException ignored) {}
    }
}
