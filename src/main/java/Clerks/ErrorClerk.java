package Clerks;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.util.List;
import java.util.stream.Stream;

public class ErrorClerk {
    private static final ErrorClerk errorClerk = new ErrorClerk();
    private Notifications notification;
    private Stage tempHiddenStage;

    private ErrorClerk() {

    }
    private void checkForExistingStage(){
        Stream<Window> open = Stage.getWindows().stream().filter(Window::isShowing);
        Window[] windowArray = open.toArray(Window[]::new);
        if(windowArray.length==0){
            Stage owner = new Stage(StageStyle.TRANSPARENT);
            StackPane root = new StackPane();
            root.setStyle("-fx-background-color: TRANSPARENT");
            Scene scene = new Scene(root, 1, 1);
            scene.setFill(Color.TRANSPARENT);
            owner.setScene(scene);
            owner.setWidth(1);
            owner.setHeight(1);
            owner.toBack();
            owner.show();
            tempHiddenStage=owner;
            this.notification=Notifications.create();
        }
        else{
            this.notification=Notifications.create();
        }
    }
    public void showErrorMessage(String errorMessage) {
        checkForExistingStage();
        notification.text(errorMessage);
        notification.show();
    }

    public static ErrorClerk getInstance() {
        return errorClerk;
    }
    public void closeTempStage(){
        if (tempHiddenStage==null){
            System.out.println(tempHiddenStage);
            tempHiddenStage.close();
        }
    }
}
