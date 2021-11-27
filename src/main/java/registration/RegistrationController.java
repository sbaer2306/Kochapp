package registration;

import Clerks.HashingClerk;
import DBController.UserDBController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import Datastructures.*;

public class RegistrationController {

    private Notifications note = Notifications.create();

    private UserDBController dbController = new UserDBController();

    private UserModel registeredUser = null;

    @FXML
    private TextField emailField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField passwordVerificationField;
    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label notificationLabel;

    //TODO: Wäre es nicht besser, ein UserModel an den DBController zu übergeben?
    // Es dürfen schließlich Nutzername und E-Mail nicht mehrmals vorkommen
    // -> dann müssten wir für Nutzername und E-Mail nur ein mal durch die DB durch zum prüfen!
    // (Passwort kann bei der Überprüfung ignoriert werden)
    private boolean checkUserExists(UserModel user){ //Überprüft, ob ein Account mit den angegebenen Nutzernamen und E-Mail bereits existiert
        if(dbController.checkUserExists(user.getUsername())){
            notificationLabel.setText("Nutzername oder E-Mail-Adresse bereits vergeben!");
            return false;
        }
        else return true;
    }

    private boolean checkForEmptyTextFields(){ //Überprüft, ob alle Textfelder ausgefüllt wurden
        if(usernameField.getText().equals("") || emailField.getText().equals("") ||
                passwordField.getText().equals("") || passwordVerificationField.getText().equals("")){
            notificationLabel.setText("Bitte alle Felder ausfüllen!");
            return false;
        }
        else return true;
    }

    //TODO: bessere Überprüfung der E-Mail
    private boolean validateEmail(){ //Überprüft, ob eine gültige E-Mail eingegeben wurde
        if(emailField.getText().contains("@")) return true;
        else {
            notificationLabel.setText("E-Mail-Adresse existiert nicht!");
            return false;
        }
    }

    private boolean checkMatchingPasswords(){ //Überprüft, ob die beiden Passwortfelder dieselbe Eingabe haben.
        if(passwordField.getText().equals(passwordVerificationField.getText())) return true;
        else {
            notificationLabel.setText("Passwörter stimmen nicht überein!");
            return false;
        }
    }

    //TODO: ist die Methode hier noch nötig?
    // Laut Klassendiagramm soll die hier implementiert werden, aber der DBController hat die bereits eingebaut
    /*private String hashPassword(){ //Verschlüsselt das Passwort
        return new HashingClerk().hash(passwordField.getText());
    }*/

    private UserModel createUser(){ //erstellt einen Nutzer, um ihn später mit der DB zu vergleichen und dann dort einzufügen
        return new UserModel(usernameField.getText(), emailField.getText(), passwordField.getText());
    }

    @FXML
    public void cancelButtonPressed(ActionEvent actionEvent) {
        note.text("Die Registrierung wurde abgebrochen!");
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void confirmButtonPressed(ActionEvent actionEvent) {
        if(checkForEmptyTextFields() && validateEmail() && checkMatchingPasswords()){
            UserModel user = createUser();

            /*
            if(checkUserExists(user){
            */
                registeredUser = user;
                Stage stage = (Stage)confirmButton.getScene().getWindow();
                stage.close();
            /*
            }
            */
        }
    }
    //TODO: Überprüfungen fertig machen
    public boolean register() {
        if(registeredUser != null){
            note.text("Es wurde erfolgreich ein Account für " + registeredUser.getUsername() + " erstellt!\n" +
                    "Sie  können sich jetzt auf diesem Account anmelden!");
            //dbController.insertNewUser(registeredUser);
            note.show();
            return true;
        }
        else {
            note.show();
            return false;
        }
    }

    @FXML
    public void initialize(){
        note.title("Registrierung");
        note.text("Die Registrierung ist fehlgeschlagen oder wurde abgebrochen!");
    }
}
