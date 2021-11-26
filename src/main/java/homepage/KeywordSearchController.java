package homepage;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import login.Login;

public class KeywordSearchController {
    @FXML
    public TextField keywordField;
    @FXML
    public MenuButton extension;

    @FXML
    public String getSearchResults(){
        String query = keywordField.getText();
        keywordField.clear();
        //DBSearchController dbS = new DBSearchController();
        //dbS.searchQuery(query); -> ArrayList aus Recipe

        return query; //ArrayList<Recipe>
    }

    @FXML
    public void initialize() throws Exception{
        MenuButton extendedSearch = (MenuButton) FXMLLoader.load(getClass().getResource("/homepage/extendedSearch.fxml"));
        HBox hbox = (HBox) extension.getParent();
        hbox.getChildren().add(extendedSearch);
        hbox.getChildren().remove(2);

    }

    public void startLogin(ActionEvent actionEvent) {
        new Login();
    }

    /*
    @FXML
    public void loginButtonPress(){
        new Login().login();
    }

    @FXML
    public void registrationButtonPress(){
        new Registration().register();
    }
     */
}