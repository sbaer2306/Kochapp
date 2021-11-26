package homepage;

import Clerks.HashingClerk;
import DBController.UserDBController;
import Datastructures.UserModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/homepage/homepage.fxml"));
        Scene scene = new Scene(root, 1000, 600, Color.WHITE);
        primaryStage.setTitle("Startseite");
        primaryStage.setScene(scene);
        primaryStage.show();
        // ExtendedSearchController extendedSearchController = new ExtendedSearchController();
        // root.setController(extendedSearchController);
    }

    public static void main(String[] args) throws SQLException {
//        launch(args);
        UserDBController udb = new UserDBController();
        UserModel user= new UserModel("nichtmarkus","mail","Bier24/7");
        boolean loggedin=udb.validateLogin(user);
        System.out.println("Log in successful:"+loggedin);


    }
}