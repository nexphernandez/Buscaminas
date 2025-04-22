module es.nexphernandez.buscaminas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;
    requires javafx.graphics;

    opens es.nexphernandez.buscaminas to javafx.fxml;
    exports es.nexphernandez.buscaminas;
    exports es.nexphernandez.buscaminas.config;
    exports es.nexphernandez.buscaminas.controller;
    exports es.nexphernandez.buscaminas.controller.abstractas;
    exports es.nexphernandez.buscaminas.model;
    exports es.nexphernandez.buscaminas.model.conexion;
    opens es.nexphernandez.buscaminas.controller to javafx.fxml;
}