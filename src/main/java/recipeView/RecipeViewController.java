package recipeView;

import Datastructures.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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

        ListView ingredients = (ListView) container.getChildren().get(12);
        ingredients.getItems().add(recipe.getIngredients());
    }
}
