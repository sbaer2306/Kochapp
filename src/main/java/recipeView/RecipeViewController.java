package recipeView;

import CommentSection.CommentController;
import CommentSection.CommentViewController;
import DBController.RatingDBController;
import Datastructures.FavoriteInformation;
import Datastructures.Recipe;
import Datastructures.RecipeComment;
import Datastructures.UserModel;
import FavoriteSection.FavoriteController;
import Session.UserSession;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class RecipeViewController {

    private Recipe recipe;
    // private RatingController ratingController;
    // private UserSession userSession;
    private UserModel userModel;
    private RatingDBController ratingDBController;
    private CommentController commentController;
    private CommentViewController commentViewController;
    private FavoriteController favoriteController;
    private boolean isFavorite;

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
    @FXML
    private TextArea commentField;
    @FXML
    private Button favoriteWhite;
    @FXML
    private Button favoriteBlack;
    @FXML
    private Label favoriteLabel;

    public RecipeViewController() {
        // this.ratingController = new RatingController(userModel);
        // this.userSession = new UserSession();
        this.userModel = new UserSession().getUserSession();
        this.ratingDBController = new RatingDBController();
    }

    public void createRecipeComment() throws SQLException {
        RecipeComment recipeComment;
        if(userModel != null) {
            recipeComment = new RecipeComment(userModel, recipe, commentField.getText());
            ratingDBController.insertComment(recipeComment);
            System.out.println("Recipe Comment has been inserted..");
        }
        else {
            Notifications notification = Notifications.create();
            notification.title("Woooooah, das ging wohl in die Hose!");
            notification.text("Bitte melde dich an, um einen Kommentar zu veröffentlichen.. :)");
            notification.show();
        }
    }

    public void displayComments() throws IOException {
        // commentController = new CommentController(recipe);

        FXMLLoader commentSection = new FXMLLoader(getClass().getResource("/CommentSection/commentSection.fxml"));
        ScrollPane root = commentSection.load();

        CommentViewController con  = commentSection.getController();
        con.setRecipe(recipe);

        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Kommentar Sektion");
        stage.setScene(scene);

        con.showComments();

        stage.show();
    }

    public void favorite() throws SQLException {
        if(userModel != null){

            if(!isFavorite){
                favoriteController.insertFavoriteRecipe();
            }else{
                favoriteController.deleteFavoriteRecipe();
            }
            displayFavorite();

        }else{
            Notifications notification = Notifications.create();
            notification.title("Oops, das ging wohl schief :(");
            notification.text("Bitte logge dich ein, um diese Funktion zu nutzen.. :)");
            notification.show();
        }
    }

    public void like() throws SQLException {
        if(userModel != null) {
            if(userModel.getLikedRecipeIDs().contains(recipe.getId())) {
                boolean revoked = ratingDBController.revokeLike(userModel, recipe);
                if(revoked) {
                    int number = Integer.parseInt(recipe.getLikes()) -1;
                    recipe.setLikes(String.valueOf(number));
                    userModel.getLikedRecipeIDs().remove(recipe.getId());
                    thumbUpWhite.setVisible(true);
                    thumbUpBlack.setVisible(false);
                    likesNumber.setText(recipe.getLikes());
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
                        int number = Integer.parseInt(recipe.getLikes()) +1;
                        recipe.setLikes(String.valueOf(number));
                        thumbUpWhite.setVisible(false);
                        thumbUpBlack.setVisible(true);
                        likesNumber.setText(recipe.getLikes());
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
                    int number = Integer.parseInt(recipe.getDislikes()) -1;
                    recipe.setDislikes(String.valueOf(number));
                    userModel.getDislikedRecipeIDs().remove(recipe.getId());
                    thumbDownBlack.setVisible(false);
                    dislikesNumber.setText(recipe.getDislikes());
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
                        int number = Integer.parseInt(recipe.getDislikes()) +1;
                        recipe.setDislikes(String.valueOf(number));
                        thumbDownBlack.setVisible(true);
                        dislikesNumber.setText(recipe.getDislikes());
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

    private void displayFavorite(){
        if(userModel != null){
            favoriteController = new FavoriteController(userModel, recipe);
            isFavorite = favoriteController.getFavorite() != null;

            if(!isFavorite){
                favoriteWhite.setVisible(true);
                favoriteBlack.setVisible(false);
                favoriteLabel.setText("Rezept favorisieren");
            }else{
                favoriteWhite.setVisible(false);
                favoriteBlack.setVisible(true);
                favoriteLabel.setText("Rezept favorisiert");
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
        displayFavorite();
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
