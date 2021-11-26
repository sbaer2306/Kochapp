package login;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Login {

    public Login() throws SQLException {
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
