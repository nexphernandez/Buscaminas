module es.nexphernandez.buscaminas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;

    opens es.nexphernandez.buscaminas to javafx.fxml;
    exports es.nexphernandez.buscaminas;
    exports es.nexphernandez.buscaminas.controller;
    exports es.nexphernandez.buscaminas.model;
    opens es.nexphernandez.buscaminas.controller to javafx.fxml;
}