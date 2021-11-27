package homepage;

import Datastructures.Recipe;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import login.Login;
import registration.Registration;

import java.sql.SQLException;
import java.util.ArrayList;

public class KeywordSearchController {
    @FXML
    public TextField keywordField;
    @FXML
    public MenuButton extension;

    @FXML
    public ArrayList<Recipe> getSearchResults(Event event){
        String query = keywordField.getText();
        keywordField.clear();
        return null; //ArrayList<Recipe>
    }

    @FXML
    public void initialize() throws Exception{
        MenuButton extendedSearch = (MenuButton) FXMLLoader.load(getClass().getResource("/homepage/extendedSearch.fxml"));
        HBox hbox = (HBox) extension.getParent();
        hbox.getChildren().add(extendedSearch);
        hbox.getChildren().remove(2);
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