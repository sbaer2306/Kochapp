package registration;

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

    private UserModel registeredUser = null; //Hier wird der Nutzer gespeichert, der dann in die Datenbank geschrieben wird

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


    private boolean checkUserExists(UserModel user){ //Überprüft, ob ein Account mit den angegebenen Nutzernamen und E-Mail bereits existiert
        if(dbController.checkUserExists(user.getUsername())){ //Überprüft ob Nutzername schon existiert
            notificationLabel.setText("Nutzername bereits vergeben!");
            return false;
        }
        else if(dbController.checkEmailExists(user.getEmail())){ //Überprüft ob Email schon existiert
            notificationLabel.setText("E-Mail-Adresse bereits vergeben!");
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

    public boolean checkEmailInput(String email){
        boolean valid = isValidEmailAddress(email);
        if(!valid) {
            notificationLabel.setText("E-Mail-Adresse ist ungültig");
            return false;
        }
        else return true;
    } //Informiert Benutzer ob Email adresse valide ist

    public boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    } //validiert Email Adresse

    private boolean checkMatchingPasswords(){ //Überprüft, ob die beiden Passwortfelder dieselbe Eingabe haben.
        if(passwordField.getText().equals(passwordVerificationField.getText())) return true;
        else {
            notificationLabel.setText("Passwörter stimmen nicht überein!");
            return false;
        }
    }

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
        if(checkForEmptyTextFields() && checkEmailInput(emailField.getText()) && checkMatchingPasswords()){
            UserModel user = createUser();

            if(checkUserExists(user)){
                registeredUser = user;
                Stage stage = (Stage)confirmButton.getScene().getWindow();
                stage.close();
            }
        }
    }

    public boolean register() {
        if(registeredUser != null){
            note.text("Es wurde erfolgreich ein Account für " + registeredUser.getUsername() + " erstellt!\n" +
                    "Sie  können sich jetzt auf diesem Account anmelden!");
            dbController.insertNewUser(registeredUser);
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
