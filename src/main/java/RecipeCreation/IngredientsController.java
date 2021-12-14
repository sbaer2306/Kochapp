package RecipeCreation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IngredientsController {

    private boolean ingredientsConfirmed = false;

    @FXML
    private VBox ingredientsVBox;
    @FXML
    private Button cancelButton;
    @FXML
    private Button confirmButton;

    //lädt gleich 3 ingredient Boxen
    @FXML
    public void initialize(){
        for (int i=0; i<3; i++){
            addButtonPressed();
        }
    }

    //"baut" eine HBox --> <Menge> <Einheit> <Zutat>
    private HBox createIngredientInputBox() {
        HBox node = new HBox();

        TextField amount = new TextField();
        amount.setPrefWidth(85);

        ChoiceBox<String> unit= new ChoiceBox<String>();

        unit.getItems().add("mg");
        unit.getItems().add("g");
        unit.getItems().add("kg");
        unit.getItems().add("ml");
        unit.getItems().add("l");
        unit.getItems().add("EL");
        unit.getItems().add("TL");
        unit.getItems().add("Stück(e)");
        unit.getItems().add("Dose(n)");
        unit.getItems().add("Packung(en)");
        unit.getItems().add("Priese(n)");

        TextField ingredient = new TextField();
        ingredient.setPrefWidth(170);

        node.getChildren().addAll(amount,unit,ingredient);
        HBox.setMargin(node, new Insets(10));
        node.setPadding(new Insets(5));

        return node;
    }

    //fügt eine neue und leere Box zum Ausfüllen für eine neue Zutat hinzu
    public void addButtonPressed(){
        HBox ingredientInputBox = createIngredientInputBox();
        ingredientsVBox.getChildren().add(ingredientInputBox);
    }

    // Bestätigt die Eingegebenen Zutaten und schließt das Fenster
    public void confirmButtonPressed(){
        ingredientsConfirmed = true;
        Stage stage = (Stage)confirmButton.getScene().getWindow();
        stage.close();
    }

    //erzeugt aus den Ingredients einen String (dynamisch, hier müsst ihr nichts ändern, wenn ihr die UI so übernehmt)
    public String getIngredients(){
        StringBuilder res = new StringBuilder();

        int count= ingredientsVBox.getChildren().size();

        for (int i = 0; i < count; i++) {

            HBox box = (HBox) ingredientsVBox.getChildren().get(i);

            int innerCount = box.getChildren().size();

            String appendString=null;

            for (int j = 0; j < innerCount; j++) {

                if(j==1){
                    ChoiceBox<String> c= (ChoiceBox) box.getChildren().get(j);
                    appendString = c.getValue();
                }
                else {
                    TextField field = (TextField) box.getChildren().get(j);
                    appendString= field.getText();
                }

                if(appendString!= null){
                    res.append(appendString);
                    res.append(" ");
                }

            }
            res.append("\n");
        }
        return res.toString();
    }

    //Bricht die Eingabe und die Rezepterstellung ab
    public void cancelButtonPressed(ActionEvent actionEvent) {
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    //Prüft, ob die Eingabe bestätigt wurde
    public boolean getIngredientsConfirmed(){
        return ingredientsConfirmed;
    }
}