
package es.nexphernandez.buscaminas.controller;


import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public class PostsController extends AbstractController{

    @FXML
    private Button imageProfileButton;
    @FXML
    private Button optionButton;

    /**
     * va a la pantalla del perfil
     */
    @FXML
    public void postToProfileOnCliclk() {
        cambiarPantalla(imageProfileButton,"perfil","posts");
    }
    
    /**
     * va a la pantalla de las opciones
     */
    @FXML
    public void optionButtonOnClick(){

    }
}
