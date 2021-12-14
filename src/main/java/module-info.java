module main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    requires java.sql;
    requires java.desktop;
    requires javafx.swing;

    opens main to javafx.fxml;
    exports main;

    opens homepage to javafx.fxml;
    exports homepage;

    opens login to javafx.fxml;
    exports login;

    opens registration to javafx.fxml;
    exports registration;

    opens RecipeCreation to javafx.fxml;
    exports RecipeCreation;

    opens Datastructures to java.sql;
    exports Datastructures;

    opens recipeView to javafx.fxml;
    exports recipeView;
}