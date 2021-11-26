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

    opens main to javafx.fxml;
    exports main;

    opens homepage to javafx.fxml;
    exports homepage;

    opens login to javafx.fxml;
    exports login;
}