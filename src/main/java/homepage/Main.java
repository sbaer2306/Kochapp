package homepage;

import Clerks.ErrorClerk;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/homepage/homepage.fxml"));

        Scene scene = new Scene(root, 1000, 600, Color.WHITE);
        primaryStage.setTitle("Startseite");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(1016);
        primaryStage.setOnCloseRequest(windowEvent -> {
            ErrorClerk.getInstance().closeTempStage();
            Platform.exit();
        });
    }

    public static void main(String[] args) throws SQLException {
        launch(args);

    }
}