package es.nexphernandez.buscaminas.controller;


import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *   @author nexphernandez
 *   @version 1.0.0
 */
public class TituloController extends AbstractController {
    
    @FXML Button entrarButton;

    @FXML
    void tituloToLoginClick(){
        cambiarPantalla(entrarButton, "app-init", "titulo");
    }
}
