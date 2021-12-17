package CommentSection;

import DBController.RatingDBController;
import Datastructures.Recipe;
import Datastructures.RecipeComment;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.ArrayList;

public class CommentController {

    private RatingDBController ratingDBController;

    public CommentController() {
        this.ratingDBController = new RatingDBController();
    }

    // Return 10 Comments from Database
    public ArrayList<RecipeComment> getRecipesLatestComments(Recipe recipe) {
        ArrayList<RecipeComment> comments;
        comments = ratingDBController.getRecipesLatestComments(recipe, 10);
        return comments;
    }

}
