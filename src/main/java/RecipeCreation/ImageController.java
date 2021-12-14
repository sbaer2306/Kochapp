package RecipeCreation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;

public class ImageController {

    private boolean imageConfirmed = false;
    private File image;


    @FXML
    private Button cancelButton, confirmButton, chooseButton;
    @FXML
    private Label notificationLabel;

    //Bricht die Auswahl und die Rezepterstellung ab
    public void cancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    //Bestätigt das ausgewählte Bild und schließt das Fenster, sofern ein Bild ausgewählt wurde
    public void confirmButtonPressed(ActionEvent actionEvent) {
        if(image != null){
            imageConfirmed = true;
            Stage stage = (Stage)confirmButton.getScene().getWindow();
            stage.close();
        }
        else notificationLabel.setText("Wähle zum Bestätigen ein Bild aus!");
    }

    //Lässt den Nutzer im Filebrowser eine .jpg Datei für das Rezept auswählen
    public void chooseButtonPressed(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ef = new FileChooser.ExtensionFilter("Image Files (.jpg)", "*.jpg");
        fc.getExtensionFilters().add(ef);

        image = fc.showOpenDialog((Stage)chooseButton.getScene().getWindow());

        if(image == null) notificationLabel.setText("Es wurde kein Bild ausgewählt!");
        else notificationLabel.setText(image.getName() + " ist ausgewählt!");
    }

    //Prüft ob die Auswahl bestätigt wurde
    public boolean getImageConfirmed(){
        return imageConfirmed;
    }
    //Gibt die .jpg Datei für das Rezept zurück
    public File getImage(){
        return image;
    }
}
