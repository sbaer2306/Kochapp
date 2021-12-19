package FavoriteSection;

import DBController.RatingDBController;
import Datastructures.FavoriteInformation;
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
import org.controlsfx.control.Rating;
import recipeView.RecipeViewController;

import java.io.IOException;
import java.sql.SQLException;

public class FavoriteViewController {

    private FavoriteInformation favorite;
    private RatingDBController ratingDBController;

    public FavoriteViewController(){
        this.ratingDBController = new RatingDBController();
    }

    public void setFavoriteInformation(FavoriteInformation fav) {
        this.favorite = fav;
    }

    //Text vom favorisieten Rezept wird angezeigt
    public void assembleFavorite(HBox element){
        Label title = (Label) element.getChildren().get(0);
        title.setText(favorite.getRecipeTitle());
        Label date = (Label) element.getChildren().get(1);
        date.setText(favorite.getAddedDatetime());
    }

    //Wenn man auf ein favorisiertes Rezept dessen Namen klickt, öffnet sich das dazugehörige Rezept
    public void createRecipeViewStage() throws IOException, SQLException {
        Recipe recipe = ratingDBController.getRecipeByID(favorite.getRecipeId());

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

    //Wenn man auf entfernen klickt löscht sich das favorisierte Rezept sowohl in der Anzeige, als auch in der Datenbank
    public void removeElement(Event event) throws SQLException{
        Button btn = (Button) event.getTarget();
        HBox h = (HBox) btn.getParent();
        VBox v = (VBox) h.getParent();

        for(int i = 0; i < v.getChildren().size(); i++){
            if(h.equals(v.getChildren().get(i))){
                v.getChildren().remove(i);
            }
        }
        ratingDBController.deleteFavorite(favorite);
    }


    //Setz dem Text ein unterschricht wenn man mit der Maus drüberfährt
    public void underlineTrue(Event event){
        Label lbl = (Label) event.getTarget();
        lbl.setUnderline(true);
    }
    //Unterschrich wird entfernt vom Text wenn man mit der Maus drüberfährt
    public void underlineFalse(Event event){
        Label lbl = (Label) event.getTarget();
        lbl.setUnderline(false);
    }
}
