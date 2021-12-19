package CommentSection;

import DBController.RatingDBController;
import Datastructures.Recipe;
import Datastructures.RecipeComment;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

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

    // Delete comment
    public void deleteComment(Label label, ArrayList<RecipeComment> comments) {
        label.setOnMouseClicked(e -> {

            VBox container = (VBox) label.getParent();
            VBox section = (VBox) container.getParent();
            section.getChildren().remove(container);

            Label text = (Label) container.getChildren().get(2);
            Iterator<RecipeComment> iterator = comments.iterator();
            while(iterator.hasNext()) {
                RecipeComment comment = iterator.next();
                if(comment.getText().equals(text.getText())) {
                    iterator.remove();
                    try {
                        ratingDBController.deleteComment(comment);
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }

            Notifications notification = Notifications.create();
            notification.title("Dein Kommentar wurde erfolgreich gelöscht.");
            notification.text("Schade, wir werden dein Feedback vermissen.. :(");
            notification.show();

        });
    }

}
