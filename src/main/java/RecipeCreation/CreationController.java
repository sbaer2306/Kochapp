package RecipeCreation;

import DBController.RecipeDBController;
import Datastructures.Recipe;
import Session.UserSession;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.controlsfx.control.Notifications;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class CreationController {

    //Führt direkt bei initialisierung die notwendigen Methoden aus, um das Rezept zu erstellen
    public CreationController(){
        createRecipe();
        Notifications note = Notifications.create();
        if(recipeSet) {
            try {
                new RecipeDBController().InsertRecipe(recipe);
                note.title("Rezepterstellung abgeschlossen");
                note.text("Dein Rezept wird verarbeitet und gespeichert!");
            } catch (SQLException | FileNotFoundException throwables) {
                throwables.printStackTrace();
                note.title("Fehler bei Rezepterstellung");
                note.text("Es ist ein Fehler beim Speichern deinen Rezeptes passiert!\nDein Rezept wurde nicht erstellt!");
            }
        }
        else{
            note.title("Rezepterstellung abgebrochen");
            note.text("Du hast die Rezepterstellung abgebrochen!");
        }
        note.show();
    }
    boolean recipeSet = false;
    private Recipe recipe = new Recipe();

    //Öffnet das Fenster für die Rezepterstellung und speichert die eingegebenen Werte in ein Rezept, wenn Eingabe bestätigt ist
    public void createRecipe(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("recipeCreation.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Erstelle dein Rezept");

        try{
            Parent root = loader.load();
            ViewController vc = loader.getController();
            stage.setScene(new Scene(root));
            stage.showAndWait();
            if(vc.isRecipeConfirmed()){
                recipe.setTitle(vc.getTitle());
                recipe.setIngredients(vc.getIngredients());
                recipe.setDescription(vc.getDescription());
                recipe.setPortions(vc.getPortions());
                recipe.setDuration(vc.getDuration());
                recipe.setIngredientsCost(vc.getPrice());
                recipe.setDifficulty(vc.getDifficulty());
                recipe.setCategories(vc.getCategories());
                recipe.setImageFile(vc.getImage());
                recipe.setAuthor(new UserSession().getUserSession().getUsername());

                recipeSet=true;
            }
        } catch (IOException | InterruptedException ignored) {}
    }
}
