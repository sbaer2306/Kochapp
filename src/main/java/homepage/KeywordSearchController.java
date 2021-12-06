package homepage;

import DBController.DBSearchController;
import Datastructures.Recipe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import login.Login;
import registration.Registration;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class KeywordSearchController {

    @FXML
    public TextField keywordField;
    @FXML
    public TextField maxPrice;
    @FXML
    public TextField duration;
    @FXML
    public ToggleGroup difficultyToggleGroup;
    @FXML
    public MenuButton showExtendedSearchMenu;
    @FXML
    public MenuButton categories;
    @FXML
    public MenuButton extensionID;

    @FXML
    public ArrayList<Recipe> getSearchResults(){
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        String query = keywordField.getText();
        keywordField.clear();
        BorderPane bp = (BorderPane) keywordField.getScene().getRoot();

        CustomMenuItem customMenuItem1 = (CustomMenuItem) extensionID.getItems().get(0);
        HBox one = (HBox) customMenuItem1.getContent();
        TextField priceField = (TextField) one.getChildren().get(1);
        String price = priceField.getText();
        // Set Default Value of Price Field..
        if(priceField.getText() == null || priceField.getText().trim().isEmpty()) {
            price = "0";
        }

        CustomMenuItem customMenuItem2 = (CustomMenuItem) extensionID.getItems().get(1);
        HBox two = (HBox) customMenuItem2.getContent();
        TextField durationField = (TextField) two.getChildren().get(1);
        String duration = durationField.getText();
        // Set Default Value of Duration..
        if(durationField.getText() == null || durationField.getText().trim().isEmpty()) {
            duration = "0";
        }

        CustomMenuItem customMenuItem3 = (CustomMenuItem) extensionID.getItems().get(2);
        MenuButton categories = (MenuButton) customMenuItem3.getContent();
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < categories.getItems().size(); i++) {
            CustomMenuItem customMenuItem = (CustomMenuItem) categories.getItems().get(i);
            CheckBox checkBox = (CheckBox) customMenuItem.getContent();
            if(checkBox.isSelected()) {
                list.add(checkBox.getText());
            }
        }
        // Set Default Value of Categories
        if(list.size() == 0) {
            list.add("");
        }

        CustomMenuItem customMenuItem4 = (CustomMenuItem) extensionID.getItems().get(3);
        VBox radios = (VBox) customMenuItem4.getContent();
        HBox buttons = (HBox) radios.getChildren().get(2);
        String difficulty = "";
        for(int i = 0; i < buttons.getChildren().size(); i++) {
            RadioButton radioButton = (RadioButton) buttons.getChildren().get(i);
            if(radioButton.isSelected()) {
                difficulty += radioButton.getText();
            }
        }
        // Set Default Value of Difficulty
        difficulty += "";

        try {
            DBSearchController dbSearchController = new DBSearchController();
            ViewController rpc= new ViewController(bp);
            if(price.equals("0") && duration.equals("0") && difficulty.equals("") && list.get(0).equals("")) {
                recipes = dbSearchController.searchQuery(query, 100);
                rpc.assemblePreview(recipes);
            }
            else {
                recipes = dbSearchController.extendedSearchQuery(query, null, price,
                        0, Integer.parseInt(duration), difficulty, list);
                rpc.assemblePreview(recipes);
            }
        } catch (SQLException | IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    public void clearExtension() {
        maxPrice.clear();
        duration.clear();
        for(int i = 0; i < categories.getItems().size(); i++) {
            CustomMenuItem customMenuItem = (CustomMenuItem) categories.getItems().get(i);
            CheckBox checkBox = (CheckBox) customMenuItem.getContent();
            if(checkBox.isSelected()) {
                checkBox.setSelected(false);
            }
        }
        RadioButton selectedRadioButton = (RadioButton) difficultyToggleGroup.getSelectedToggle();
        if(selectedRadioButton != null) {
            selectedRadioButton.setSelected(false);
        }
        showExtendedSearchMenu.hide();
    }

    @FXML
    public void loadHomePage(Event event){
        Button btn = (Button) event.getSource();
        BorderPane bp = (BorderPane) btn.getParent().getParent();
        ScrollPane sp = (ScrollPane) bp.getCenter();
        VBox list = (VBox) sp.getContent();

        list.getChildren().remove(1, list.getChildren().size());

        ViewController vC = new ViewController(bp);
        vC.displayMostLikedRecipes();
    }


    @FXML
    public void loginButtonPress(ActionEvent actionEvent) throws SQLException {
        new Login();
    }

    @FXML
    public void registrationButtonPress(){
        new Registration();
    }
}