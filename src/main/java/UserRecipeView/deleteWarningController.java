package UserRecipeView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class deleteWarningController {

    boolean confirmed = false;

    @FXML
    Label notificationLabel;
    @FXML
    Button cancelButton, confirmButton;

    public void setLabel(String title){
        if (title.length() > 15){
            title = title.substring(0, 12);
            title = title.concat("...");
        }
        notificationLabel.setText("Möchten sie " + title + " endgültig löschen?");
    }

    public void cancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void confirmButtonPressed(ActionEvent actionEvent) {
        confirmed = true;
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    public boolean isConfirmed() {
        return confirmed;
    }
}
