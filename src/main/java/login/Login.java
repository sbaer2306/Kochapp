package login;

import Session.UserSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.sql.SQLException;

public class Login {

    public Login() throws SQLException {

        if(new UserSession().sessionExists()){
            Notifications note = Notifications.create();
            note.title("Login Fehlgeschlagen");
            note.text("Sie sind bereits angemeldet als "  + new UserSession().getUserSession().getUsername() + "!");
            note.show();
        }
        else{
            Stage loginInterface = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            loginInterface.setTitle("Login");

            try{
                loginInterface.setScene(new Scene(loader.load()));
            }
            catch (IOException ignored){}

            loginInterface.showAndWait();
            LoginController lc = loader.getController();
            lc.login();
        }
    }
}
