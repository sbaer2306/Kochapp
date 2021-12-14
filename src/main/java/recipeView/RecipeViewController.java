package recipeView;

import Datastructures.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.util.Arrays;

public class RecipeViewController {

    private Recipe recipe;
    
    @FXML
    private AnchorPane container;

    public Recipe getRecipe() {
        return recipe;
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
