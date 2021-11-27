package registration;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import login.LoginController;

import java.io.IOException;

public class Registration {

    public Registration(){
        Stage registrationInterface = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("registration.fxml"));

        registrationInterface.setTitle("Registrierung");
        try{
            registrationInterface.setScene(new Scene(loader.load()));
        }
        catch (IOException ignored){}

        registrationInterface.showAndWait();
        RegistrationController rc = loader.getController();
        rc.register();
    }
}
