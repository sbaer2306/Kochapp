package recipeView;

import Datastructures.Recipe;
import Session.UserSession;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

public class RecipeViewController {

    private Recipe recipe;
    private RatingController ratingController;
    
    @FXML
    private AnchorPane container;

    @FXML
    private Button thumbUp;
    @FXML
    private Button thumbDown;
    @FXML
    private Button favorite;

    public void like() {
        if(new UserSession().sessionExists()) {
            if(!ratingController.like(recipe)) {
                System.out.println("Recipe has been liked..");
                Image image = new Image("resources/recipeView/fx-images/1x/baseline_thumb_up_black_24dp.png");
                ImageView imageView = (ImageView) thumbUp.getGraphic();
                imageView.setImage(image);
            }
            else {
                ratingController.revokeLike(recipe);
                System.out.println("Like has been revoked..");
                Image image = new Image("resources/recipeView/fx-images/1x/outline_thumb_up_black_24dp.png");
                ImageView imageView = (ImageView) thumbUp.getGraphic();
                imageView.setImage(image);
            }
        }
        else {
            System.out.println("Please log into your account to use this function..");
        }
    }

    public void dislike() {
        if(new UserSession().sessionExists()) {
            if(!ratingController.dislike(recipe)) {
                System.out.println("Recipe has been disliked..");
                Image image = new Image("resources/recipeView/fx-images/1x/baseline_thumb_down_black_24dp.png");
                ImageView imageView = (ImageView) thumbDown.getGraphic();
                imageView.setImage(image);
            }
            else {
                ratingController.revokeDislike(recipe);
                System.out.println("Dislike has been revoked..");
                Image image = new Image("resources/recipeView/fx-images/1x/outline_thumb_down_black_24dp.png");
                ImageView imageView = (ImageView) thumbDown.getGraphic();
                imageView.setImage(image);
            }
        }
        else {
            System.out.println("Please log into your account use this function..");
        }
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public RecipeViewController() {
        this.ratingController = new RatingController();
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


    }
}
