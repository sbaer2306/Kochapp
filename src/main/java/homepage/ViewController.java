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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewController {

    @FXML
    private BorderPane bp;

    public void initialize() throws Exception{
        BorderPane searchBar = (BorderPane) FXMLLoader.load(getClass().getResource("/homepage/SearchBar.fxml"));
        bp.setTop(searchBar);
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
            container.setMinWidth(733);

            Label mostLikedRecipes = (Label) container.getChildren().get(0);
            mostLikedRecipes.setText("Unsere Top 5 Rezepte");
            mostLikedRecipes.getStyleClass().add("h1");

            for(int i = 1; i < 6; i++) {
                HBox recipeContainer = (HBox) FXMLLoader.load(getClass().getResource("/homepage/previewElement.fxml"));



                if(i % 2 == 0){
                    recipeContainer.getStyleClass().add("colorBoxes1");
                }else{
                    recipeContainer.getStyleClass().add("colorBoxes2");
                }

                // Aufbaue des Images
                Button imageContainer = (Button) recipeContainer.getChildren().get(0);
                ImageView view = (ImageView) imageContainer.getGraphic();

                Rectangle clip = new Rectangle(
                        view.getFitWidth(), view.getFitHeight()
                );
                view.setImage(mostLiked.get(i - 1).getImage());

                clip.setArcWidth(30);
                clip.setArcHeight(30);
                view.setClip(clip);

                // Container für die Attribute des Rezepts
                AnchorPane attributeContainer = (AnchorPane) recipeContainer.getChildren().get(1);

                // Aufbau des Titels
                Label title = (Label) attributeContainer.getChildren().get(0);
                title.setText(mostLiked.get(i - 1).getTitle());

                // Aufbau der Likes
                Label likes = (Label) attributeContainer.getChildren().get(1);
                likes.setText(mostLiked.get(i - 1).getLikes());

                // Aufbau der Dauer
                Label duration = (Label) attributeContainer.getChildren().get(3);
                duration.setText(mostLiked.get(i - 1).getDuration());

                // Aufbau der Schwierigkeit
                Label difficulty = (Label) attributeContainer.getChildren().get(5);
                difficulty.setText(mostLiked.get(i - 1).getDifficulty());

                // Aufbau der Kosten
                Label price = (Label) attributeContainer.getChildren().get(7);
                price.setText(mostLiked.get(i - 1).getIngredientsCost());

                container.getChildren().add(recipeContainer);
            }
        } catch(IOException | ClassCastException e) {
            e.printStackTrace();
        }
    }

}