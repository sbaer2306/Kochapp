package homepage;

import Datastructures.Recipe;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import recipeView.RecipeViewController;

import java.io.IOException;

public class PreviewViewController {

    private Recipe recipe;

    /*
    public void setTitleText(String title) {
        this.title.setText(title);
    }*/

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public HBox assemblePreview(HBox recipeContainer, int count) {
        setHBoxStyle(recipeContainer, count);

        // Aufbaue des Images
        Button imageContainer = (Button) recipeContainer.getChildren().get(0);
        ImageView view = (ImageView) imageContainer.getGraphic();

        Rectangle clip = new Rectangle(
                view.getFitWidth(), view.getFitHeight()
        );
        view.setImage(recipe.getImage());

        clip.setArcWidth(30);
        clip.setArcHeight(30);
        view.setClip(clip);

        // Container für die Attribute des Rezepts
        AnchorPane attributeContainer = (AnchorPane) recipeContainer.getChildren().get(1);

        // Aufbau des Titels
        Label title = (Label) attributeContainer.getChildren().get(0);
        title.setText(recipe.getTitle());

        // Aufbau der Likes
        Label likes = (Label) attributeContainer.getChildren().get(1);
        likes.setText(recipe.getLikes());

        // Aufbau der Dauer
        Label duration = (Label) attributeContainer.getChildren().get(3);
        duration.setText(recipe.getDuration());

        // Aufbau der Schwierigkeit
        Label difficulty = (Label) attributeContainer.getChildren().get(5);
        difficulty.setText(recipe.getDifficulty());

        // Aufbau der Kosten
        Label price = (Label) attributeContainer.getChildren().get(7);
        price.setText(recipe.getIngredientsCost());

        return recipeContainer;

    }

    private void setHBoxStyle(HBox hbox, int counter){

        if (counter % 2 == 0) {
            hbox.getStyleClass().add("colorBoxes1");
        } else {
            hbox.getStyleClass().add("colorBoxes2");
        }
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

    public void underlineTrue(Event event){
        Label lbl = (Label) event.getTarget();
        lbl.setUnderline(true);
    }

    public void underlineFalse(Event event){
        Label lbl = (Label) event.getTarget();
        lbl.setUnderline(false);
    }
}
