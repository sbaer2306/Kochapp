package homepage;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ArrayList;

public class ExtendedSearchController {

    private ArrayList<String> extendedSearch;
    private ArrayList<String> categoriesList;

    @FXML
    public TextField maxPrice;
    @FXML
    public TextField duration;
    @FXML
    public ToggleGroup difficlutyToggleGroup;
    @FXML
    public MenuButton showExtendedSearchMenu;
    @FXML
    public MenuButton categories;

    @FXML
    public void save(Event event) {
        extendedSearch = new ArrayList<>();
        categoriesList = new ArrayList<String>();

        for(int i = 0; i < categories.getItems().size(); i++) {
            CustomMenuItem customMenuItem = (CustomMenuItem) categories.getItems().get(i);
            CheckBox checkBox = (CheckBox) customMenuItem.getContent();
            if(checkBox.isSelected()) {
                // extendedSearch.add(checkBox.getText());
                categoriesList.add(checkBox.getText());
            }
        }

        RadioButton selectedRadioButton = (RadioButton) difficlutyToggleGroup.getSelectedToggle();

        if(selectedRadioButton != null){
            String radioButtonValue = selectedRadioButton.getText();
            extendedSearch.add(radioButtonValue);
        }

        extendedSearch.add(maxPrice.getText());
        extendedSearch.add(duration.getText());


        /*for(int i = 0; i < extendedSearch.size(); i++) {
            System.out.println(extendedSearch.get(i));
        }
        System.out.println(categoriesList.get(0));*/

        clearExtension(event);
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
        RadioButton selectedRadioButton = (RadioButton) difficlutyToggleGroup.getSelectedToggle();
        selectedRadioButton.setSelected(false);
        hidePopup();
    }

    public void hidePopup(){
        showExtendedSearchMenu.hide();
    }
}
