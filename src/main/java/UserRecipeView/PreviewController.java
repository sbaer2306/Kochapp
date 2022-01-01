package UserRecipeView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;


public class PreviewController {


    private int childID;
    private UserRecipeListController controller;

    @FXML
    Button titleButton, deleteButton;



    public void setChildID(int i){
        childID = i;
    }

    public void setListController(UserRecipeListController c){
        controller = c;
    }

    public void setTitle(String title){
        titleButton.setText(title);
    }

    private String getTitle(){
        return titleButton.getText();
    }

    public void deleteButtonPressed(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("deleteWarning.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Rezept löschen");
        try {
            stage.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        deleteWarningController dwc = loader.getController();
        dwc.setLabel(getTitle());
        stage.showAndWait();
        if(dwc.isConfirmed()){
            controller.deleteRecipe(childID);
            GridPane previewGrid = (GridPane) deleteButton.getParent();
            VBox previewVBox = (VBox) previewGrid.getParent();
            previewVBox.getChildren().remove(previewGrid);
        }
    }

    public void titleButtonPressed(ActionEvent actionEvent) {
        controller.showFullRecipe(childID);
    }
}
