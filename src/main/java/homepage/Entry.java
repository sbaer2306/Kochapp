package homepage;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ResourceBundle;

public class Entry extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/homepage/homepage.fxml"));

        Scene scene = new Scene(root, 1000, 600, Color.WHITE);
        primaryStage.setTitle("Startseite");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMinWidth(1016);
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/appIcon/Food Hub icon.png"))));
    }

    public static void launchApplication(String[] args) {
        launch(args);
    }
}