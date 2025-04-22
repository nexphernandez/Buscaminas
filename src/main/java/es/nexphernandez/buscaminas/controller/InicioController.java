package es.nexphernandez.buscaminas.controller;

import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class InicioController extends AbstractController{

    @FXML
    public Text userText;
    @FXML
    private TextField userTextField;
    @FXML
    public ComboBox dificultadBox;
    @FXML
    private Text minasText;
    @FXML
    private TextField nivelTextField;
    @FXML
    private Button regresarButton;
    @FXML
    public Button jugarButton;
    
    @FXML
    private void inicioToPartidaOnClick(){
        cambiarPantalla(jugarButton, "play", "app-init");
    }

    @FXML
    private void inicioToLoginOnClick(){
        cambiarPantalla(regresarButton, "app-init", "app-init");
    }

}
