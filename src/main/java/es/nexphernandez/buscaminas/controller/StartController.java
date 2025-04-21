package es.nexphernandez.buscaminas.controller;


import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import es.nexphernandez.buscaminas.model.UsuarioEntity;
import es.nexphernandez.buscaminas.model.UsuarioServiceModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class StartController extends AbstractController {

    @FXML
    private TextField textFieldUser;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Text textMessage;
    @FXML
    private Button buttonStart;
    @FXML
    private Button buttonCreate;

    /**
     * Inicia la sesion del usuario.
     */
    @FXML
    public void buttonStartClick() {
    if (!validarCredenciales()) {
        textMessage.setText("Credenciales invalidas");
        return;
    }
    UsuarioServiceModel userModel = new UsuarioServiceModel();
    UsuarioEntity user = new UsuarioEntity(textFieldUser.getText(), passwordFieldPassword.getText());
    UsuarioEntity startUser = userModel.readUser(user);
    if (startUser == null) {
        textMessage.setText("Credenciales incorrectas");
        return;
    }
    textMessage.setText("");
    setUsuarioActual(startUser);
    cambiarPantalla(buttonStart, "level");
    }

    /**
     * Cambia a la pantalla crear.
     */
    @FXML
    public void buttonCreateClick() {
        cambiarPantalla(buttonCreate, "create");
    }

    /**
     * Valida las credenciales.
     * 
     * @return retorna true si estas son validas.
     */
    private boolean validarCredenciales() {
        return (textFieldUser != null && textFieldUser.getText() != null && !textFieldUser.getText().trim().isBlank() &&
                passwordFieldPassword != null && passwordFieldPassword.getText() != null
                && !passwordFieldPassword.getText().trim().isBlank());
    }

}