package RecipeCreation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class RemaindersController {

    private boolean remaindersConfirmed = false;

    @FXML
    private TextField portionsField, durationField, priceField;
    @FXML
    private Button cancelButton, confirmButton;
    @FXML
    private Label notificationLabel;
    @FXML
    private ChoiceBox<String> difficultyBox;
    @FXML
    private MenuButton categoriesMenu;

    //Setzt die Choices für die Auswahl der Schwierigkeit
    @FXML
    public void initialize(){
        difficultyBox.getItems().addAll("Leicht", "Mittel", "Schwer");
        difficultyBox.setValue("Leicht");
    }

    //Prüft, ob in allen Feldern eine richtige Eingabe gemacht wurde
    private boolean validateInput(){
        String portions = getPortions();
        if(portions.equals("")) return false;
        String duration = getDuration();
        if(duration.equals("")) return false;
        String price = getPrice();
        if(price.equals("")) return false;

        for (int i = 0; i < portions.length(); i++){
            if(!Character.isDigit(portions.charAt(i))) return false;
        }
        for (int i = 0; i < duration.length(); i++){
            if(!Character.isDigit(duration.charAt(i))) return false;
        }
        boolean commaSet = false;
        for (int i = 0; i < price.length(); i++){
            if(price.charAt(i) == ',' && !commaSet) commaSet = true;
            else if (Character.isDigit(price.charAt(i)));
            else return false;
        }
        return true;
    }

    //Bricht die Eingabe und die Rezepterstellung ab
    public void cancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    //Bestätigt die Eingabe und schließt das Fenster, sofern eine gültige Eingabe gemacht wurde
    public void confirmButtonPressed(ActionEvent actionEvent) {
        if(validateInput()){
            remaindersConfirmed = true;
            Stage stage = (Stage)confirmButton.getScene().getWindow();
            stage.close();
        }
        else notificationLabel.setText("Bitte gebe gültige Zahlen ein!");
    }

    //Prüft, ob die Eingabe bestätigt wurde
    public boolean getRemaindersConfirmed(){
        return remaindersConfirmed;
    }

    //Gibt die Eingetragenen Werte als Strings zurück
    public String getPortions(){
        return portionsField.getText();
    }
    public String getDuration(){
        return durationField.getText();
    }
    public String getPrice(){
        return priceField.getText();
    }
    public String getDifficulty(){
        return difficultyBox.getValue();
    }

    //Geht durch das categories-Menü durch und gibt alle ausgewählten Kategorien in einer ArrayList zurück
    public ArrayList<String> getCategories(){
        ArrayList<String> categories = new ArrayList<>();

        for (int i = 0; i < categoriesMenu.getItems().size(); i++){
            CheckMenuItem cmi = (CheckMenuItem)categoriesMenu.getItems().get(i);
            if (cmi.isSelected()) categories.add(cmi.getText());
        }
        return categories;
    }
}
