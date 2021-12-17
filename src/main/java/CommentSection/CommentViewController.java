package CommentSection;

import Datastructures.Recipe;
import Datastructures.RecipeComment;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import recipeView.RecipeViewController;

import java.awt.*;
import java.util.ArrayList;

public class CommentViewController {

    @FXML
    private VBox section;

    @FXML
    private Label commentLabel;

    private Recipe recipe;
    private CommentController commentController;

    public CommentViewController() {
        this.commentController = new CommentController();
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public void showComments() {
        ArrayList<RecipeComment> comments = commentController.getRecipesLatestComments(recipe);
        commentLabel.setText(String.valueOf(comments.size()) + " Kommentare");

        ArrayList<VBox> containers = new ArrayList<VBox>();

        for(int i = 0; i < comments.size(); i++) {
            VBox commentContainer = new VBox();
            VBox.setMargin(commentContainer, new Insets(0,15,0,15));
            commentContainer.getStyleClass().add("commentContainer");
            Label author = new Label(comments.get(i).getAuthor_uid());
            author.getStyleClass().add("author");
            Label date = new Label(comments.get(i).getDatetime());
            date.getStyleClass().add("date");
            Label text = new Label(comments.get(i).getText());
            text.getStyleClass().add("content");
            text.setWrapText(true);
            commentContainer.getChildren().add(author);
            commentContainer.getChildren().add(date);
            commentContainer.getChildren().add(text);
            containers.add(commentContainer);
        }

        section.getChildren().addAll(containers);

    }
}
