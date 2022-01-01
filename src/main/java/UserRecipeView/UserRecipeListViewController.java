package UserRecipeView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class UserRecipeListViewController {

    @FXML
    Button closeButton;
    @FXML
    VBox recipeVBox;

    public void addPreview(GridPane preview){
        recipeVBox.getChildren().add(preview);
    }


    public void closeButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage)closeButton.getScene().getWindow();
        stage.close();
    }
}
