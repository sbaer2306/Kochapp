package recipeView;

import DBController.RatingDBController;
import Datastructures.Recipe;
import Datastructures.UserModel;
import Session.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;

public class RecipeViewController {

    private Recipe recipe;
    // private RatingController ratingController;
    // private UserSession userSession;
    private UserModel userModel;
    private RatingDBController ratingDBController;
    
    @FXML
    private AnchorPane container;

    @FXML
    private Button thumbUpWhite;
    @FXML
    private Button thumbUpBlack;
    @FXML
    private Button thumbDownWhite;
    @FXML
    private Button thumbDownBlack;
    @FXML
    private Button favorite;
    @FXML
    private Label likesNumber;
    @FXML
    private Label dislikesNumber;

    public RecipeViewController() {
        // this.ratingController = new RatingController(userModel);
        // this.userSession = new UserSession();
        this.userModel = new UserSession().getUserSession();
        this.ratingDBController = new RatingDBController();
    }

    public void like() throws SQLException {
        if(userModel != null) {
            if(userModel.getLikedRecipeIDs().contains(recipe.getId())) {
                boolean revoked = ratingDBController.revokeLike(userModel, recipe);
                if(revoked) {
                    userModel.getLikedRecipeIDs().remove(recipe.getId());
                    thumbUpWhite.setVisible(true);
                    thumbUpBlack.setVisible(false);
                    likesNumber.setText("" + (Integer.parseInt(recipe.getLikes()) -1));
                }
            }
            else {
                if(userModel.getDislikedRecipeIDs().contains(recipe.getId())) {
                    Notifications notification = Notifications.create();
                    notification.title("Oops, das ging wohl schief :(");
                    notification.text("Setze dein dislike zurück um das Rezept zu liken.. :)");
                    notification.show();
                }
                else {
                    boolean liked = ratingDBController.insertNewLike(userModel, recipe);
                    userModel.getLikedRecipeIDs().add(recipe.getId());
                    if(liked) {
                        thumbUpWhite.setVisible(false);
                        thumbUpBlack.setVisible(true);
                        likesNumber.setText("" + (Integer.parseInt(recipe.getLikes()) +1));
                    }
                }
            }
        }
        else {
            Notifications notification = Notifications.create();
            notification.title("Oops, das ging wohl schief :(");
            notification.text("Bitte logge dich ein, um diese Funktion zu nutzen.. :)");
            notification.show();
        }
    }

    public void dislike() throws SQLException {
        if(userModel != null) {
            if(userModel.getDislikedRecipeIDs().contains(recipe.getId())) {
                boolean revoked = ratingDBController.revokeDislike(userModel, recipe);
                if(revoked) {
                    userModel.getDislikedRecipeIDs().remove(recipe.getId());
                    thumbDownBlack.setVisible(false);
                    dislikesNumber.setText("" + (Integer.parseInt(recipe.getDislikes()) -1));
                }
            }
            else {
                if(userModel.getLikedRecipeIDs().contains(recipe.getId())) {
                    Notifications notification = Notifications.create();
                    notification.title("Oops, das ging wohl schief :(");
                    notification.text("Setze dein like zurück um das Rezept zu disliken.. :)");
                    notification.show();
                }
                else {
                    boolean disliked= ratingDBController.insertNewDisLike(userModel, recipe);
                    userModel.getDislikedRecipeIDs().add(recipe.getId());
                    if(disliked) {
                        thumbDownBlack.setVisible(true);
                        dislikesNumber.setText("" + (Integer.parseInt(recipe.getDislikes()) +1));
                    }
                }
            }
        }
        else {
            Notifications notification = Notifications.create();
            notification.title("Oops, das ging wohl schief :(");
            notification.text("Bitte logge dich ein, um diese Funktion zu nutzen.. :)");
            notification.show();
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    private void displayRating() {
        if(userModel != null) {
            if(userModel.getLikedRecipeIDs().contains(recipe.getId())) {
                thumbUpBlack.setVisible(true);
            }
            else {
                thumbUpWhite.setVisible(true);
            }
            if(userModel.getDislikedRecipeIDs().contains(recipe.getId())) {
                thumbDownBlack.setVisible(true);
            }
            else {
                thumbDownWhite.setVisible(true);
            }
        }
    }


    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void showRecipe(){

        ImageView img = (ImageView) container.getChildren().get(0);
        img.setImage(recipe.getImage());

        Label title = (Label) container.getChildren().get(1);
        title.setText(recipe.getTitle());

        Label description = (Label) container.getChildren().get(2);
        description.setText(recipe.getDescription());

        Label likes = (Label) container.getChildren().get(6);
        likes.setText(recipe.getLikes());

        Label dislikes = (Label) container.getChildren().get(7);
        dislikes.setText(recipe.getDislikes());

        Label portions = (Label) container.getChildren().get(9);
        portions.setText("Zutaten für " + recipe.getPortions() + " Person");

        Label difficulty = (Label) container.getChildren().get(16);
        difficulty.setText(recipe.getDifficulty());

        Label duration = (Label) container.getChildren().get(17);
        duration.setText(recipe.getDuration());

        Label price = (Label) container.getChildren().get(18);
        price.setText(recipe.getIngredientsCost());

        Label categoryLabel = (Label) container.getChildren().get(19);
        String categories = Arrays.toString(recipe.getCategories().toArray()).replace("[", "").replace("]", "");
        categoryLabel.setText(categories);

        Label authorAndDate = (Label) container.getChildren().get(11);
        authorAndDate.setText("Von: " + recipe.getAuthor() + ", " + recipe.getCreationTime());

        ListView ingredients = (ListView) container.getChildren().get(12);
        ingredients.getItems().add(recipe.getIngredients());

        displayRating();


    }
}
