package homepage;

import DBController.DBSearchController;
import Datastructures.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewController {

    @FXML
    private BorderPane bp;

    public void initialize() throws Exception{
        BorderPane searchBar = (BorderPane) FXMLLoader.load(getClass().getResource("/homepage/SearchBar.fxml"));
        // ScrollPane previewTop5 = (ScrollPane) FXMLLoader.load(getClass().getResource("/homepage/previewtop5_2.fxml"));
        bp.setTop(searchBar);
        //((ScrollPane)(bp.getCenter())).setContent(previewTop5);
        // bp.setCenter(previewTop5);
        displayMostLikedRecipes((ScrollPane) bp.getCenter());
    }

    public void displayMostLikedRecipes(ScrollPane pane) {
        ArrayList<Recipe> mostLiked = new ArrayList<Recipe>();
        try {
            DBSearchController dbSearchController = new DBSearchController();
            mostLiked = dbSearchController.getTopFiveRecipes();
        }catch (SQLException | IOException | NullPointerException e) {
            e.printStackTrace();
        }
        try {
            VBox container = (VBox) pane.getContent();

            Label mostLikedRecipes = new Label("Unsere Top 5 Rezepte");
            container.getChildren().add(mostLikedRecipes);

            for(int i = 1; i < 6; i++) {

                HBox recipeContainer = (HBox) FXMLLoader.load(getClass().getResource("/homepage/previewElement.fxml"));

                // Aufbaue des Images
                Button imageContainer = (Button) recipeContainer.getChildren().get(0);
                ImageView view = new ImageView();
                view.setFitWidth(250);
                view.setFitHeight(150);
                view.setImage(mostLiked.get(i - 1).getImage());
                imageContainer.setGraphic(view);

                // Container für die Attribute des Rezepts
                AnchorPane attributeContainer = (AnchorPane) recipeContainer.getChildren().get(1);

                // Aufbau des Titels
                Label title = (Label) attributeContainer.getChildren().get(0);
                title.setText(mostLiked.get(i - 1).getTitle());

                // Aufbau der Beschreibung
                Label description = (Label) attributeContainer.getChildren().get(1);
                description.setText(mostLiked.get(i - 1).getDescription());

                // Aufbau der Likes
                Label likes = (Label) attributeContainer.getChildren().get(2);
                likes.setText(mostLiked.get(i - 1).getLikes());

                // Aufbau der Dauer
                Label duration = (Label) attributeContainer.getChildren().get(4);
                duration.setText(mostLiked.get(i - 1).getDuration());

                // Aufbau der Schwierigkeit
                Label difficulty = (Label) attributeContainer.getChildren().get(8);
                difficulty.setText(mostLiked.get(i - 1).getDifficulty());

                // Aufbau der Kosten
                Label price = (Label) attributeContainer.getChildren().get(6);
                price.setText(mostLiked.get(i - 1).getIngredientsCost());

                container.getChildren().add(recipeContainer);
            }
        } catch(IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

}