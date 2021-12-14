package RecipeCreation;

import DBController.DBInsertController;
import Datastructures.Recipe;
import Session.UserSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CreationController {

    //Führt direkt bei initialisierung die notwendigen Methoden aus, um das Rezept zu erstellen
    public CreationController(){
        createRecipe();
        if(recipeSet) {
            try {
                new DBInsertController().InsertRecipe(recipe);
            } catch (SQLException | FileNotFoundException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    private Recipe recipe = new Recipe();
    private boolean recipeSet = false, recipeCancelled = false;


    //Lässt den Nutzer den Rezepttitel eintragen
    private void setTitle(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("title.fxml"));

        try{
            stage.setTitle("Titel eingeben");
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            TitleController tc = loader.getController();
            stage.showAndWait();

            if(tc.getTitleConfirmed()){
                recipe.setTitle(tc.getTitle());
            }
            else recipeCancelled = true;
        }
        catch(IOException ignored){}
    }

    //Lässt den Nutzer die Zutaten eingeben
    private void setIngredients(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ingredients.fxml"));

        try{
            stage.setTitle("Zutaten hinzufügen");
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            IngredientsController ic = loader.getController();
            stage.showAndWait();

            if(ic.getIngredientsConfirmed()){
                recipe.setIngredients(ic.getIngredients());
            }
            else recipeCancelled = true;
        }
        catch(IOException ignored){}
    }

    //Lässt den Nutzer die Beschreibung eingeben
    private void setDescription(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("description.fxml"));

        try{
            stage.setTitle("Beschreibung hinzufügen");
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            DescriptionController dc = loader.getController();
            stage.showAndWait();

            if(dc.getDescriptionConfirmed()){
                recipe.setDescription(dc.getDescription());
            }
            else recipeCancelled = true;
        }
        catch(IOException ignored){}
    }

    //Lässt den Nutzer Portionen, Dauer, Preis, Schwierigkeit und Kategorien eintragen
    private void setRemainders(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("remainders.fxml"));

        try{
            stage.setTitle("weitere Informationen hinzufügen");
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            RemaindersController rc = loader.getController();
            stage.showAndWait();

            if(rc.getRemaindersConfirmed()){
                recipe.setPortions(rc.getPortions());
                recipe.setDuration(rc.getDuration());
                recipe.setIngredientsCost(rc.getPrice());
                recipe.setDifficulty(rc.getDifficulty());
                recipe.setCategories(rc.getCategories());
            }
            else recipeCancelled = true;
        }
        catch(IOException ignored){}
    }

    //Lässt den Nutzer eine .jpg Datei als Bild auswählen
    private void setImage(){
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("image.fxml"));

        try{
            stage.setTitle("Bild hinzufügen");
            Parent root = loader.load();
            stage.setScene(new Scene(root));
            ImageController ic = loader.getController();
            stage.showAndWait();

            if(ic.getImageConfirmed()){
                recipe.setImageFile(ic.getImage());
            }
            else recipeCancelled = true;
        }
        catch(IOException ignored){}
    }

    //Führt alle Methoden nacheinander aus, um das Rezept zu erstellen
    public void createRecipe(){
        if(!(new UserSession().sessionExists())) return;
        recipe.setAuthor(new UserSession().getUserSession().getUsername());

        setTitle();
        if(recipeCancelled) return;

        setIngredients();
        if(recipeCancelled) return;

        setDescription();
        if(recipeCancelled) return;

        setRemainders();
        if(recipeCancelled) return;

        setImage();
        if(recipeCancelled) return;

        recipeSet = true;
    }
}
