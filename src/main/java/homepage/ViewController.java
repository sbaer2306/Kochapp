package homepage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ViewController {

    @FXML
    private BorderPane bp;

    public void initialize() throws Exception{
        BorderPane searchBar = (BorderPane) FXMLLoader.load(getClass().getResource("/homepage/SearchBar.fxml"));
        ScrollPane previewTop5 = (ScrollPane) FXMLLoader.load(getClass().getResource("/homepage/previewtop5_2.fxml"));
        bp.setTop(searchBar);
        //((ScrollPane)(bp.getCenter())).setContent(previewTop5);
        bp.setCenter(previewTop5);
    }

}