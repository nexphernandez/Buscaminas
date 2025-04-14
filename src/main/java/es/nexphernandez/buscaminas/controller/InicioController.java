package es.nexphernandez.buscaminas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class InicioController {
    @FXML
    private Button miBoton;

    @FXML
    public void initialize() {
        miBoton.setText("¡Hola, Mundo!");
    }
}