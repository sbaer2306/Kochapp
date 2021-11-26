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

    opens main to javafx.fxml;
    exports main;

    opens homepage to javafx.fxml;
    exports homepage;

    opens login to javafx.fxml;
    exports login;

    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    opens database to java.sql;
    exports database;
    exports Datastructures;
    opens Datastructures to java.sql;
}