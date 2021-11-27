package homepage;

import Datastructures.Recipe;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;

public class ExtendedSearchController {

    private ArrayList<String> extendedSearch = new ArrayList<String>();
    private ArrayList<String> categoriesList = new ArrayList<String>();

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
    public ArrayList<Recipe> getSearchResults(Event event) {

        for(int i = 0; i < categories.getItems().size(); i++) {
            CustomMenuItem customMenuItem = (CustomMenuItem) categories.getItems().get(i);
            CheckBox checkBox = (CheckBox) customMenuItem.getContent();
            if(checkBox.isSelected()) {
                categoriesList.add(checkBox.getText());
            }
        }

        RadioButton selectedRadioButton = (RadioButton) difficultyToggleGroup.getSelectedToggle();

        if(selectedRadioButton != null){
            String radioButtonValue = selectedRadioButton.getText();
            extendedSearch.add(radioButtonValue);
        }

        extendedSearch.add(maxPrice.getText());
        extendedSearch.add(duration.getText());

        clearExtension(event);
        return null;
    }

    public void clearExtension(Event event) {
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

}