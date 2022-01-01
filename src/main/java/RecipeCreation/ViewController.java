package RecipeCreation;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class ViewController {

    private boolean recipeConfirmed = false;

    @FXML
    private TextField titleField, portionsField, durationField, priceField;
    @FXML
    private Label titleNotificationLabel, ingredientsNotificationLabel, descriptionNotificationLabel,
            remaindersNotificationLabel, imageNotificationLabel;
    @FXML
    private VBox ingredientsVBox;
    @FXML
    private TextArea descriptionArea;
    @FXML
    private Button removeIngredientButton, addIngredientButton, addImageButton,
            confirmButton, cancelButton;
    @FXML
    private ChoiceBox<String> difficultyChoiceBox;
    @FXML
    private GridPane categoriesGridPane;

    private String title, ingredients, description, portions, duration, price, difficulty;
    private ArrayList<String> categories;
    private File image;

    @FXML
    public void initialize() { //Erstellt die ChoiceBox für die Schwierigkeit
        difficultyChoiceBox.getItems().addAll("Leicht", "Mittel", "Schwer");
        difficultyChoiceBox.setValue("Leicht");
    }


    public void removeIngredientButtonPressed(ActionEvent actionEvent) { //Entfernt die Letzte Zutat aus der Zutatenliste
        int listSize = ingredientsVBox.getChildren().size();
        if(listSize > 0) ingredientsVBox.getChildren().remove(listSize-1);
    }

    public void addIngredientButtonPressed(ActionEvent actionEvent) { //Fügt eine Zutat in die Zutatenliste hinzu
        ingredientsVBox.getChildren().add(buildIngredientHBox());
    }

    public void addImageButtonPressed(ActionEvent actionEvent) { //Lässt den Nutzer ein Bild für das Rezept auswählen
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ef =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.jpeg");
        fc.getExtensionFilters().add(ef);

        image = fc.showOpenDialog((Stage)addImageButton.getScene().getWindow());

        validateImage();
    }

    public void cancelButtonPressed(ActionEvent actionEvent) { //Bricht die Rezepterstellung ab
        Stage stage = (Stage)cancelButton.getScene().getWindow();
        stage.close();
    }

    public void confirmButtonPressed(ActionEvent actionEvent) { //Überprüft alle Eingaben und bestätigt sie, wenn keine Fehler auftreten
        boolean noValidationErrors = true;
        if(!validateTitle()) noValidationErrors = false;
        if(!validateDescription()) noValidationErrors = false;
        if(!validateIngredients()) noValidationErrors = false;
        if(!validateRemainders()) noValidationErrors = false;
        if(!validateImage()) noValidationErrors = false;

        if(noValidationErrors){

            setCategories();
            recipeConfirmed = true;
            Stage stage = (Stage)confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    private HBox buildIngredientHBox(){ //Erstellt eine HBox für die Zutatenliste
        HBox ingredientHBox = new HBox();
        ingredientHBox.setPrefWidth(363);

        TextField amountField = new TextField();
        amountField.setPromptText( "z.B. 100");
        amountField.setPrefWidth(60);

        ChoiceBox<String> unitBox = new ChoiceBox<>();
        unitBox.getItems().addAll(" ", " mg ", " g ", " kg ", " ml ", " dl ", " l ",
                " EL ", " TL ", " Stück(e) ", " Packung(en) ", " Dose(n) ", " Priese(n) ");
        unitBox.setValue(" g ");
        unitBox.setPrefWidth(93);

        TextField ingredientField = new TextField();
        ingredientField.setPromptText("z.B. Mehl");
        ingredientField.setPrefWidth(210);

        ingredientHBox.getChildren().addAll(amountField, unitBox, ingredientField);

        return ingredientHBox;
    }

    private boolean validateImage(){ //Überprüft, ob ein Bild eingefügt wurde und das nicht das Speicherlimit überschreitet
        if(image == null){
            imageNotificationLabel.setText("Sie haben noch kein Bild hinzugefügt");
            imageNotificationLabel.setTextFill(Paint.valueOf("red"));
            return false;
        }
        else if(image.length() > 1_500_000L){
            imageNotificationLabel.setText("Die ausgewählte Datei ist zu groß (>1,5MB)");
            imageNotificationLabel.setTextFill(Paint.valueOf("red"));
            return false;
        }
        else {
            imageNotificationLabel.setText("Du hast " + image.getName() + " ausgewählt");
            imageNotificationLabel.setTextFill(Paint.valueOf("grey"));
            return true;
        }
    }

    private boolean validateIngredients(){ //Überprüft, ob die Zutaten richtig angegeben wurden
        StringBuilder ingredientBuilder =  new StringBuilder();
        for (int i = 0; i < ingredientsVBox.getChildren().size(); i++){
            HBox ingredient = (HBox)ingredientsVBox.getChildren().get(i);
            TextField ingredientAmountField = (TextField)ingredient.getChildren().get(0);
            ChoiceBox<String> unitBox = (ChoiceBox)ingredient.getChildren().get(1);
            TextField ingredientNameField = (TextField)ingredient.getChildren().get(2);
            String amount = ingredientAmountField.getText().strip();
            String unit = unitBox.getValue();
            String name = ingredientNameField.getText().strip();
            if(amount.equals("") || name.equals("")){
                ingredientsNotificationLabel.setText("Bitte alle Felder ausfüllen");
                return false;
            }
//            if(!checkForDigit(amount)){
//                ingredientsNotificationLabel.setText("Nur Zahlen in der Mengenangabe erlaubt");
//                return false;
//            }
            ingredientBuilder.append(amount);
            ingredientBuilder.append(unit);
            ingredientBuilder.append(name);
            if(i != ingredientsVBox.getChildren().size()-1) ingredientBuilder.append('\n');
        }
        int overLimit = ingredientBuilder.length() - 800;
        if (overLimit > 0){
            ingredientsNotificationLabel.setText("Zutatenliste zu lang: " + overLimit + " Zeichen zu viel");
            return false;
        }
        ingredients = ingredientBuilder.toString();
        ingredientsNotificationLabel.setText("");
        return true;
    }

    private boolean validateTitle(){ //Überprüft, ob der Titel richtig eingegeben wurde
        String title = titleField.getText().strip();
        if(title.equals("")){
            titleNotificationLabel.setText("Bitte einen Titel angeben");
            return false;
        }
        if (title.length() > 120){
            titleNotificationLabel.setText("Titel ist zu lang");
            return false;
        }
        this.title = title;
        titleNotificationLabel.setText("");
        return true;
    }

    private boolean validateDescription(){ //Überprüft, ob die Beschreibung richtig eingegeben wurde
        String description = descriptionArea.getText().strip();
        if(description.equals("")){
            descriptionNotificationLabel.setText("Bitte eine Beschreibung angeben");
            return false;
        }
        if (description.length() > 65_535){
            descriptionNotificationLabel.setText("Beschreibung ist zu lang");
            return false;
        }
        this.description = description;
        descriptionNotificationLabel.setText("");
        return true;
    }

    private boolean validateRemainders(){ //Überprüft, ob Portionen, Dauer und Preis richtig eingegeben wurde
        String portions = portionsField.getText().strip();
        String duration = durationField.getText().strip();
        String price = priceField.getText().strip();
        if(portions.equals("") || duration.equals("") || price.equals("")){
            remaindersNotificationLabel.setText("Bitte alle Felder ausfüllen");
            return false;
        }
        if (!checkForDigit(portions) || !checkForDigit(duration)){
            remaindersNotificationLabel.setText("Bitte nur ganze Zahlen für Portionen und Dauer angeben");
            return false;
        }
        if (Integer.parseInt(duration) > 65535 ){
            remaindersNotificationLabel.setText("die angegebene Dauer ist zu lang");
            return false;
        }
        if(!validatePrice(price)){
            remaindersNotificationLabel.setText("Bitte nur einen Preis von 0 bis 999,99 angeben");
            return false;
        }

        this.portions = portions;
        this.duration = duration;
        this.difficulty = difficultyChoiceBox.getValue();
        remaindersNotificationLabel.setText("");
        return true;
    }

    private void setCategories(){ //Speichert die angehakten Kategorien in einem StringArray
        ArrayList<String> categories = new ArrayList<>();
        for (int i = 0; i < categoriesGridPane.getChildren().size(); i++){
            CheckBox category = (CheckBox)categoriesGridPane.getChildren().get(i);
            if(category.isSelected()) categories.add(category.getText());
        }
        this.categories = categories;
    }

    private boolean validatePrice(String price) { //Überprüft, ob der Preis das richtige Format hat und tauscht gegebenenfalls ',' mit '.'
        int comma = -1;
        for (int i = 0; i < price.length(); i++){
            Character c = price.charAt(i);
            if(c ==',' || c == '.'){
                if (comma == -1) comma = i;
                else return false;
            }
            else if (!Character.isDigit(c)) return false;
        }
        if(comma == -1 && price.length() > 3 || comma >= price.length()-1 || comma == 0
                || (comma > 0 && (comma-3 > 0 || comma < price.length()-3))) return false;

        StringBuilder convertedPrice = new StringBuilder();
        for (int i = 0; i < price.length(); i++){
            if(i == comma) convertedPrice.append('.');
            else convertedPrice.append(price.charAt(i));
        }
        this.price = convertedPrice.toString();
        return true;
    }

    private boolean checkForDigit(String s){ //Überprüft, ob der angegebene String nur Ziffern enthält
        for (int i = 0; i < s.length(); i++){
            if(!Character.isDigit(s.charAt(i))) return false;
        }
        return true;
    }

    public boolean isRecipeConfirmed() {
        return recipeConfirmed;
    }

    public String getTitle() {
        return title;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getDescription() {
        return description;
    }

    public String getPortions() {
        return portions;
    }

    public String getDuration() {
        return duration;
    }

    public String getPrice() {
        return price;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public ArrayList<String> getCategories() {
        return categories;
    }

    public File getImage() {
        return image;
    }
}
