package RecipeCreation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class DescriptionController {

    private boolean descriptionConfirmed;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextArea descriptionField;

    //Bricht die Eingabe und die Rezepterstellung ab
    public void cancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    //Bestätigt die Eingabe und schließt das Fenster
    public void confirmButtonPressed(ActionEvent actionEvent) {
        if(descriptionField.getText().equals(""));
        else{
            descriptionConfirmed = true;
            Stage stage = (Stage)confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    //Prüft, ob die Eingabe bestätigt wurde
    public boolean getDescriptionConfirmed(){
        return descriptionConfirmed;
    }

    //Gibt die Beschreibung des Rezepts zurück
    public String getDescription(){
        return descriptionField.getText();
    }
}
