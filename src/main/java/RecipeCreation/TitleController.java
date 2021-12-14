package RecipeCreation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TitleController {

    private boolean titleConfirmed = false;

    @FXML
    private TextField titleField;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    //Bricht die Eingabe und die Rezepterstellung ab
    public void cancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    //Bestätigt die Eingabe und schließt das Fenster
    public void confirmButtonPressed(ActionEvent actionEvent) {
        if (getTitle().equals(""));
        else {
            titleConfirmed = true;
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    //Gibt den Titel für das Rezept zurück
    public String getTitle() {
        return titleField.getText();
    }

    //Prüft, ob die Eingabe bestätigt wurde
    public boolean getTitleConfirmed(){
        return titleConfirmed;
    }
}
