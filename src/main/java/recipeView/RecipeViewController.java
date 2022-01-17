package recipeView;

import CommentSection.CommentController;
import CommentSection.CommentViewController;
import DBController.RatingDBController;
import Datastructures.Recipe;
import Datastructures.UserModel;
import FavoriteSection.FavoriteController;
import Session.UserSession;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class RecipeViewController {

    private Recipe recipe;
    private UserModel userModel;
    private RatingDBController ratingDBController;
    private CommentController commentController;
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
        this.userModel = new UserSession().getUserSession();
        this.ratingDBController = new RatingDBController();
        this.commentController = new CommentController();
    }

    public void createRecipeComment() throws IOException {
        if(userModel != null){
            if(commentField.getText().isEmpty()) return;

            String text = commentField.getText();
            if(text.length() > 400) {
                createAndShowNotification("Kommentar zu lang!", "Schreibe weniger als 400 Zeichen...");
                return;
            }

            if(commentController.insertNewComment(userModel, recipe, text)){
                displayComments();
                commentField.clear();
                return;
            }
            createAndShowNotification("Oops, das sieht schlecht aus...","Da ging leider etwas schief :(");

        }else {
            createAndShowNotification("Du kannst noch keinen Kommentar verfassen!", "Bitte logge dich dazu ein...");
        }

    }

    private void createAndShowNotification(String title, String text) {
        Notifications notification = Notifications.create();
        notification.title(title);
        notification.text(text);
        notification.show();
    }

    public void cancelCommenting() {
        commentField.clear();
    }

    public void displayComments() throws IOException {

        FXMLLoader commentSection = new FXMLLoader(getClass().getResource("/CommentSection/commentSection.fxml"));
        ScrollPane root = commentSection.load();

        CommentViewController con  = commentSection.getController();
        con.setRecipe(recipe);

        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle("Top 10 Kommentare");
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
            createAndShowNotification("Oops, das ging wohl schief :(","Bitte logge dich ein, um diese Funktion zu nutzen.. :)");
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
                    createAndShowNotification("Oops, das ging wohl schief :(", "Setze dein dislike zurück um das Rezept zu liken.. :)");
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
            createAndShowNotification("Oops, das ging wohl schief :(", "Bitte logge dich ein, um diese Funktion zu nutzen.. :)");
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
                    createAndShowNotification("Oops, das ging wohl schief :(","Setze dein like zurück um das Rezept zu disliken.. :)");
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
            createAndShowNotification("Oops, das ging wohl schief :(","Bitte logge dich ein, um diese Funktion zu nutzen.. :)");
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

        ScrollPane description = (ScrollPane) container.getChildren().get(2);
        Label desc = (Label)description.getContent();
        desc.setText(recipe.getDescription());

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

        Label ingredients = (Label) container.getChildren().get(12);
        ingredients.setText(recipe.getIngredients());

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
