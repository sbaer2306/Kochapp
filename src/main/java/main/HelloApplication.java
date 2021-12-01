package main;

import DBController.DBSearchController;
import Datastructures.Recipe;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import javax.swing.text.Element;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //TESTDBsearch
        DBSearchController dbSearchController = new DBSearchController();
        //ArrayList<Recipe> recipeArrayList = dbSearchController.searchQuery("Apfelblechkuchen",1);
        //ArrayList<Recipe> recipeArrayList = dbSearchController.getTopFiveRecipes();
        /*ArrayList<String> categories = new ArrayList<>();
        categories.add("Fisch");
        categories.add("Hauptspeise");
        categories.add("Kochen");
        ArrayList<Recipe> recipeArrayList = dbSearchController.extendedSearchQuery("Lachs","5.00","50.00",0,100,"Leicht",categories);
        for (Recipe recipe : recipeArrayList){
            System.out.println(recipe);
        }*/
        //ImageView imageView = new ImageView(recipeArrayList.get(0).getImage());
        //HBox hbox = new HBox(imageView);
        //cene scene = new Scene(hbox, 320, 240);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}