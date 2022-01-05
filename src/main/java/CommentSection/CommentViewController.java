package CommentSection;

import Datastructures.Recipe;
import Datastructures.RecipeComment;
import Datastructures.UserModel;
import Session.UserSession;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Notifications;
import java.util.ArrayList;

public class CommentViewController {

    @FXML
    private VBox section;
    @FXML
    private Label commentLabel;
    private Label deleteLabel;
    private Recipe recipe;
    private CommentController commentController;
    private UserModel userModel;

    public CommentViewController() {
        this.commentController = new CommentController();
        this.userModel = new UserSession().getUserSession();
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

            ArrayList<Label> deleteLabels = new ArrayList<Label>();
            if(userModel != null) {
                ArrayList<RecipeComment> userComments = userModel.getRecipeComments();
                for(VBox container : containers) {
                    Label author = (Label) container.getChildren().get(0);
                    Label text = (Label) container.getChildren().get(2);
                    if(author.getText().equals(userModel.getUsername())) {
                        deleteLabel = new Label("Kommentar löschen");
                        deleteLabel.setId("deleteLabel");
                        deleteLabel.getStyleClass().add("deleteLabel");
                        container.getChildren().add(deleteLabel);
                        deleteLabels.add(deleteLabel);
                    }
                    for(Label deleteLabel : deleteLabels) {
                        commentController.deleteComment(deleteLabel, comments);
                    }
                }
            }
        }
        else{
            Notifications note = Notifications.create();
            note.text("Es sind noch keine Kommentare zu diesem Rezept vorhanden.\n Schreibe den ersten Kommentar!");
            note.title("Keine Kommentare vorhanden :(");
            note.show();
        }
    }
}
