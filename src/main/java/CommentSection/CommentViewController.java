package CommentSection;

import Datastructures.Recipe;
import Datastructures.RecipeComment;
import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
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

        if (comments != null && comments.size() > 0) {
            commentLabel.setText(comments.size() + " Kommentare");

            ArrayList<VBox> containers = new ArrayList<VBox>();

            for (RecipeComment comment : comments) {
                VBox commentContainer = new VBox();
                VBox.setMargin(commentContainer, new Insets(0, 15, 0, 15));
                commentContainer.getStyleClass().add("commentContainer");
                Label author = new Label(comment.getAuthor_uid());
                author.getStyleClass().add("author");
                Label date = new Label(comment.getDatetime());
                date.getStyleClass().add("date");
                Label text = new Label(comment.getText());
                text.getStyleClass().add("content");
                text.setWrapText(true);
                commentContainer.getChildren().add(author);
                commentContainer.getChildren().add(date);
                commentContainer.getChildren().add(text);
                containers.add(commentContainer);
            }

            section.getChildren().addAll(containers);

        }
        else{
            Notifications note = Notifications.create();
            note.text("Es sind noch keine Kommentare zu diesem Rezept vorhanden.\n Schreibe den ersten Kommentar!");
            note.title("Keine Kommentare vorhanden :(");
            note.show();
        }
    }
}
