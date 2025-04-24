
package es.nexphernandez.buscaminas.controller;


import es.nexphernandez.buscaminas.config.ConfigManager;
import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class RecuperarController extends AbstractController{

    @FXML
    public Text userEmailText;
    @FXML
    private TextField emailTextField;
    @FXML
    public Button enviarButton;
    @FXML
    private Text errorText;
    @FXML
    private Button regresarButton;

    @FXML
    public void initialize() {
        cambiarIdiomaRecuperar();
    }

    /**
     * va a la pantalla del perfil
     */
    @FXML
    public void enviarOnClick() {
    }
    
    /**
     * va a la pantalla de las opciones
     */
    @FXML
    public void recuperarToLoginOnClick(){
        cambiarPantalla(regresarButton, "app-init", "recuperar" );
    }

    /**
     * cambiar idioma de la pantalla recuperar
     */
    public void cambiarIdiomaRecuperar() {
        userEmailText.setText(ConfigManager.ConfigProperties.getProperty("correoText"));
        enviarButton.setText(ConfigManager.ConfigProperties.getProperty("buttonAceptarRegistrar"));
        regresarButton.setText(ConfigManager.ConfigProperties.getProperty("buttonAtras"));
    }
}
