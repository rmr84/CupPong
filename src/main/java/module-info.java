module com.example.cuppong {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens com.example.cuppong to javafx.fxml;
    exports com.example.cuppong;
    exports com.example.cuppong.controllers;
    opens com.example.cuppong.controllers to javafx.fxml;
}