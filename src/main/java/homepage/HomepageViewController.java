package homepage;

import Datastructures.Recipe;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;

public class HomepageViewController {

    private SearchController searchController;
    private ArrayList<Recipe> displayedRecipe;

    @FXML
    private TextField keywordField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField durationField;
    @FXML
    private MenuButton categoriesMenuButton;
    @FXML
    private HBox difficultyHBox;
    @FXML
    private VBox recipesContainer;

    public HomepageViewController() {
        this.searchController = new SearchController();
    }

    public void initialize() {
        displayMostLikedRecipes();
    }

    public void displayMostLikedRecipes(){
        TopFiveController topFiveController = new TopFiveController();
        displayedRecipe = topFiveController.getMostLikedRecipes();
        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
    }

    private void displayPreview(int count){
        FXMLLoader previewElement = new FXMLLoader(getClass().getResource("/homepage/previewElement.fxml"));
        HBox  previewElementContainer = new HBox();

        try{
            previewElementContainer = (HBox) previewElement.load();
        }catch(IOException e){
            e.fillInStackTrace();
        }

        PreviewViewController pvc = previewElement.getController();
        pvc.setRecipe(displayedRecipe.get(count));
        recipesContainer.getChildren().add(pvc.assemblePreview(previewElementContainer, count));
    }

    private void clearPreview(){
        recipesContainer.getChildren().clear();
    }

    public void loadHomePage(){
        recipesContainer.getChildren().clear();
        displayMostLikedRecipes();
    }

    public void displayKeywordSearchResults() {
        String buzzword = keywordField.getText();
        displayedRecipe = searchController.getKeywordSearchResult(buzzword);
        // TODO Display Recipes..
        clearPreview();
        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
    }

    public void displayExtendedSearchResults() {
        String buzzword = keywordField.getText();

        String price = priceField.getText();
        // Set Default Value of Price Field..
        if(priceField.getText() == null || priceField.getText().trim().isEmpty()) {
            price = "0";
        }

        String duration = durationField.getText();
        // Set Default Value of Duration..
        if(durationField.getText() == null || durationField.getText().trim().isEmpty()) {
            duration = "0";
        }

        ArrayList<String> categories = new ArrayList<String>();
        for(int i = 0; i < categoriesMenuButton.getItems().size(); i++) {
            CustomMenuItem customMenuItem = (CustomMenuItem) categoriesMenuButton.getItems().get(i);
            CheckBox checkBox = (CheckBox) customMenuItem.getContent();
            if(checkBox.isSelected()) {
                categories.add(checkBox.getText());
            }
        }
        // Set Default Value of Categories
        if (categories.size() == 0) {
            categories.add("");
        }

        String difficulty = "";
        for(int i = 0; i < difficultyHBox.getChildren().size(); i++) {
            RadioButton radioButton = (RadioButton) difficultyHBox.getChildren().get(i);
            if (radioButton.isSelected()) {
                difficulty = radioButton.getText();
            }
        }

        displayedRecipe = searchController.getExtendedSearchResult(buzzword, price, Integer.parseInt(duration), difficulty, categories);

        // TODO Display Recipes..
        clearPreview();
        for(int i = 0; i < displayedRecipe.size(); i++) {
            displayPreview(i);
        }
    }

}