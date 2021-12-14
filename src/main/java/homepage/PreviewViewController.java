package homepage;

import Datastructures.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class PreviewViewController {

    private Recipe recipe;

/*    public void setTitleText(String title) {
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

    public void loadRecipe() {
        Stage stage = new Stage();
        ScrollPane root = new ScrollPane();

        try {
            root = (ScrollPane) FXMLLoader.load(getClass().getResource("/recipeWindow/recipeView.fxml"));
        } catch (IOException e) {
            e.fillInStackTrace();
        }

        Scene scene = new Scene(root, 1000, 600, Color.WHITE);

        stage.setTitle(recipe.getTitle());
        stage.setScene(scene);

        showRecipe((AnchorPane) root.getContent(), recipe);
        stage.show();
    }

    private void showRecipe(AnchorPane root, Recipe recipe){
        ImageView img = (ImageView) root.getChildren().get(0);
        img.setImage(recipe.getImage());

        Label title = (Label) root.getChildren().get(1);
        title.setText(recipe.getTitle());

        Label description = (Label) root.getChildren().get(2);
        description.setText(recipe.getDescription());

        Label likes = (Label) root.getChildren().get(6);
        likes.setText(recipe.getLikes());

        Label dislikes = (Label) root.getChildren().get(7);
        dislikes.setText(recipe.getDislikes());

        Label portions = (Label) root.getChildren().get(9);
        portions.setText("Zutaten für " + recipe.getPortions() + " Person");

        ListView ingredients = (ListView) root.getChildren().get(10);
        ingredients.getItems().add(recipe.getIngredients());

    }

}
