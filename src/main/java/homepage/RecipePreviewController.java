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
        // VBox containter = ((ScrollPane) (bp.getCenter()).getContent());
        // containter.setPrefWidth(790);

        containter.getChildren().clear();

        for(int i = 0; i < recipeList.size(); i++) {
            previewElement = FXMLLoader.load(getClass().getResource("/homepage/previewElement.fxml"));

            //Für Bild
            Button b = (Button) previewElement.getChildren().get(0);
            ImageView view = new ImageView();
            view.setFitWidth(250);
            view.setFitHeight(150);
            view.setImage(recipeList.get(i).getImage());
            b.setGraphic(view);

            AnchorPane pane = (AnchorPane) previewElement.getChildren().get(1);

            //Für Titel
            Label title = (Label) pane.getChildren().get(0);
            title.setText(recipeList.get(i).getTitle());

            //Für Beschreibung
            Label description = (Label) pane.getChildren().get(1);
            description.setText(recipeList.get(i).getDescription());

            //Für Bewertung
            Label likes = (Label) pane.getChildren().get(2);
            likes.setText(recipeList.get(i).getLikes());

            //Für Zeit
            Label  duration = (Label) pane.getChildren().get(4);
            duration.setText(recipeList.get(i).getDuration());

            //Für Schwierigkeit
            Label difficulty = (Label) pane.getChildren().get(8);
            difficulty.setText(recipeList.get(i).getDifficulty());

            //Für Preis
            Label price = (Label) pane.getChildren().get(6);
            price.setText(recipeList.get(i).getIngredientsCost());

            //recipeView.add(previewElement);
            containter.getChildren().add(previewElement);
        }

        // ScrollPane sp = new ScrollPane();
        // sp.setPrefWidth(800);

        // sp.setContent(containter);

        // bp.setCenter(sp);
    }


}
