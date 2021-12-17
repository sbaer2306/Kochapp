package homepage;

import Datastructures.Recipe;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import recipeView.RecipeViewController;

import java.io.IOException;

public class FavoriteViewController {

    private Recipe recipe;

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void assembleFavorite(HBox element){
        Label title = (Label) element.getChildren().get(0);
        title.setText(recipe.getTitle());
    }

    public void createRecipeViewStage() throws IOException {
        FXMLLoader fxmlRecipeView = new FXMLLoader(getClass().getResource("/recipeView/recipeView.fxml"));
        ScrollPane root = fxmlRecipeView.load();

        RecipeViewController con  = fxmlRecipeView.getController();
        con.setRecipe(recipe);

        Stage stage = new Stage();
        Scene scene = new Scene(root, 1000, 600);
        stage.setTitle(recipe.getTitle());
        stage.setScene(scene);

        con.showRecipe();
        stage.show();
    }

    public void removeElement(Event event){
        Button btn = (Button) event.getTarget();
        HBox h = (HBox) btn.getParent();
        VBox v = (VBox) h.getParent();

        for(int i = 0; i < v.getChildren().size(); i++){
            if(h.equals(v.getChildren().get(i))){
                v.getChildren().remove(i);
            }
        }

        //TODO favorisiertes Rezept vom User entfernen
    }
}
