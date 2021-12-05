package homepage;

import Datastructures.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.ArrayList;

public class RecipePreviewController {

    private BorderPane bp;
    //private ArrayList<Recipe> recipeList;
    private ArrayList<HBox> recipeView;
    private VBox containter;
    private HBox previewElement;

    public RecipePreviewController(BorderPane bp) throws IOException {
        this.bp = bp;
    }

    public void assemblePreview(ArrayList<Recipe> recipeList) throws IOException{
        ScrollPane sp = (ScrollPane) bp.getCenter();
        VBox containter = (VBox) sp.getContent();

        if(recipeList.size() <= 1){
            containter.setMinWidth(745);
        }

        containter.getChildren().remove(1, containter.getChildren().size());

        Label mostLikedRecipes = (Label) containter.getChildren().get(0);

        switch(recipeList.size()){
            case 0: mostLikedRecipes.setText("Es wurden keine Rezepte gefunden");
                break;
            case 1: mostLikedRecipes.setText("Es wurde " + recipeList.size() + " Rezept gefunden");
                break;
            default: mostLikedRecipes.setText("Es wurden " + recipeList.size() + " Rezepte gefunden");
                break;
        }
        mostLikedRecipes.getStyleClass().add("h2");

        for(int i = 0; i < recipeList.size(); i++) {
            previewElement = FXMLLoader.load(getClass().getResource("/homepage/previewElement.fxml"));

            if(i % 2 == 0){
                previewElement.getStyleClass().add("colorBoxes2");
            }else{
                previewElement.getStyleClass().add("colorBoxes1");
            }

            //Für Bild
            Button b = (Button) previewElement.getChildren().get(0);
            ImageView view = (ImageView) b.getGraphic();

            Rectangle clip = new Rectangle(
                    view.getFitWidth(), view.getFitHeight()
            );
            view.setImage(recipeList.get(i).getImage());

            clip.setArcWidth(30);
            clip.setArcHeight(30);
            view.setClip(clip);

            AnchorPane pane = (AnchorPane) previewElement.getChildren().get(1);

            //Für Titel
            Label title = (Label) pane.getChildren().get(0);
            title.setText(recipeList.get(i).getTitle());

            //Für Bewertung
            Label likes = (Label) pane.getChildren().get(1);
            likes.setText(recipeList.get(i).getLikes());

            //Für Zeit
            Label  duration = (Label) pane.getChildren().get(3);
            duration.setText(recipeList.get(i).getDuration());

            //Für Schwierigkeit
            Label difficulty = (Label) pane.getChildren().get(5);
            difficulty.setText(recipeList.get(i).getDifficulty());

            //Für Preis
            Label price = (Label) pane.getChildren().get(7);
            price.setText(recipeList.get(i).getIngredientsCost());

            //recipeView.add(previewElement);
            containter.getChildren().add(previewElement);
        }
    }


}
