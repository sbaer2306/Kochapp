package homepage;

import DBController.DBSearchController;
import Datastructures.Recipe;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import recipeView.RecipeViewController;

import java.io.IOException;
import java.sql.SQLException;

public class RecipeOfWeekViewController {
    private Recipe recipe;
    private DBSearchController dbSearchController;

    public RecipeOfWeekViewController(){
        dbSearchController = new DBSearchController();
        setRecipeOfWeek();
    }

    private void setRecipeOfWeek(){
        try{
            recipe = dbSearchController.getRecipeOfTheWeek();
        }catch(SQLException |IOException e){
            e.fillInStackTrace();
        }
    }

    public AnchorPane assembleRecipe(AnchorPane pane){

        //Titel
        Label title = (Label) pane.getChildren().get(1);
        title.setText(recipe.getTitle());

        //Bild
        Button imageContainer = (Button) pane.getChildren().get(2);
        ImageView view = (ImageView) imageContainer.getGraphic();

        Rectangle clip = new Rectangle(
                view.getFitWidth(), view.getFitHeight()
        );
        view.setImage(recipe.getImage());

        clip.setArcWidth(30);
        clip.setArcHeight(30);
        view.setClip(clip);

        //Likes
        Label likes = (Label) pane.getChildren().get(4);
        likes.setText(recipe.getLikes());

        //Schwierigkeit
        Label difficulty = (Label) pane.getChildren().get(6);
        difficulty.setText(recipe.getDifficulty());

        //Duration
        Label duration = (Label) pane.getChildren().get(8);
        duration.setText(recipe.getDuration());

        //Preis
        Label price = (Label) pane.getChildren().get(10);
        price.setText(recipe.getIngredientsCost());

        return pane;
    }

    public void createRecipeViewStage() throws IOException {
        FXMLLoader fxmlRecipeView = new FXMLLoader(getClass().getResource("/recipeView/recipeView.fxml"));
        ScrollPane root = fxmlRecipeView.load();

        RecipeViewController con  = fxmlRecipeView.getController();
        con.setRecipe(recipe);
        System.out.println(recipe.getTitle());
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
