package login;

import DBController.UserDBController;
import Datastructures.UserModel;
import Session.UserSession;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

public class LoginController {

    public LoginController(){
    }
    private Notifications note = Notifications.create();

    private UserDBController dbController =new UserDBController();

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

    private UserModel user;

    @FXML
    private void cancelLogin(ActionEvent actionEvent) { //Funktion vom cancelButton → Bricht Login ab.
        note.text("Der Login wurde abgebrochen!");
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void confirmLogin(ActionEvent actionEvent) { //Funktion vom confirmButton → Überprüft Eingabe und löst den Login aus, wenn Eingabe richtig ist.
        if(checkForEmptyTextFields() && checkUserExists()){
            loginConfirmed = true;
            Stage stage = (Stage)confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    private boolean checkForEmptyTextFields(){ //Überprüft, ob eine Eingabe in den Textfeldern gemacht wurde.
        if(usernameField.getText().equals("") || passwordField.getText().equals("")){
            notificationLabel.setText("Alle Felder müssen ausgefüllt sein!");
            return false;
        }
        return true;
    }

    private boolean checkUserExists() {
        UserModel user = new UserModel(usernameField.getText(),passwordField.getText());
        if (dbController.validateLogin(user)){
            this.user = user;
            return true;
        }
        else{
            notificationLabel.setText("Nutzer oder Passwort falsch!");
            return false;
         }
    }

    public void login() {    //Öffnet das UI und wartet bis der Nutzer den Login eingibt.
        if(loginConfirmed){
            new UserSession().loginSession(user);
            note.text("Sie wurden erfolgreich als " + usernameField.getText() + " angemeldet!");
            note.show();
        }
        else {
            note.show();
        }
    }

    @FXML
    public void initialize(){
        note.title("Login");
        note.text("Der Login ist fehlgeschlagen oder wurde abgebrochen!");
    }
}
