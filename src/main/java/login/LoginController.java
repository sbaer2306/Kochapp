package login;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import javax.net.ssl.SSLSession;
import java.io.IOException;

public class LoginController {

    public LoginController(){
    }
    public Notifications note = Notifications.create();

    //TODO: DBAccountController fehlt noch!
    //private DBController dbController = new DBController();


    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label notificationLabel;

    private boolean loginConfirmed = false;

    @FXML
    private void cancelLogin(ActionEvent actionEvent) { //Funktion vom cancelButton → Bricht Login ab.
        note.text("Der Login wurde abgebrochen!");
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void confirmLogin(ActionEvent actionEvent) { //Funktion vom confirmButton → Überprüft Eingabe und löst den Login aus, wenn Eingabe richtig ist.
        if(validateInput() && checkUserExists()){
            loginConfirmed = true;
            Stage stage = (Stage)confirmButton.getScene().getWindow();
            stage.close();
        }
    }
    private boolean validateInput(){ //Überprüft, ob eine richtige Eingabe in den Textfeldern ist.
        if(usernameField.getText().equals("") || passwordField.getText().equals("")){
            notificationLabel.setText("Alle Felder müssen ausgefüllt sein!");
            return false;
        }
        return true;
    }

    //TODO: DBAccountController fehlt noch!
    private boolean checkUserExists() { //Überprüft, ob der Nutzername und das Passwort mit einem Nutzer in der DB übereinstimmen.
        //if(dbController.userExists(usernameField.getText(), passwordField.getText()){
            return true;
        //}
        /*else{
            notificationLabel.setText("Nutzer oder Passwort falsch!");
            return false;
         }*/
    }

    //TODO: DBAccountController fehlt noch! -> return value muss noch auf SSLSession geändert werden!
    public void login(){    //Öffnet das UI und wartet bis der Nutzer den Login eingibt.
                            //Soll SSLSession zurückgeben, wenn Eingabe richtig war.
                            //Gibt null zurück, wenn Login abgebrochen oder Fehler auftritt.
        if(loginConfirmed){
            note.text("Sie wurden erfolgreich als " + usernameField.getText() + " angemeldet!");
            note.show();
            // return dbController.getUserSession(usernameField.getText(), passwordField.getText());
        }
        else {
            note.show();
            //return null;
        }

    }

    @FXML
    public void initialize(){
        note.title("Login");
        note.text("Der Login ist fehlgeschlagen oder wurde abgebrochen!");
    }
}
