package es.nexphernandez.buscaminas.controller;

import java.sql.SQLException;
import java.util.ArrayList;

import es.nexphernandez.buscaminas.config.ConfigManager;
import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import es.nexphernandez.buscaminas.model.UsuarioEntity;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author: alejandrosalazargonzalez
 * @version: 1.0.0
 */
public class RegistrarController extends AbstractController {

    @FXML
    public Text userText;
    @FXML
    public Text nombreText;
    @FXML
    private TextField usuarioTextField;
    @FXML
    private TextField nombreTextField;
    @FXML
    public Text emailText1;
    @FXML
    private TextField emailTextField1;
    @FXML
    public Text emailText11;
    @FXML
    private TextField emailTextField11;
    @FXML
    public Text nivelText1;
    @FXML
    private PasswordField passwordField;
    @FXML
    public Text nivelText;
    @FXML
    public PasswordField passwordField2;
    @FXML
    private Button guardarButton;
    @FXML
    public Button regresarButton;
    @FXML
    private Text errorText;

    @FXML
    public void initialize() {
        cambiarIdiomaRegistrar();
    }

    /**
     * mete al usuario en la bbdd
     */
    @FXML
    private void onButtonAceptarRegClick() {
        if (!comprobarRegistrar()) {
            return;
        }

        UsuarioEntity nuevoUsuario = new UsuarioEntity(usuarioTextField.getText(), emailTextField1.getText(),
                nombreTextField.getText(), passwordField.getText());
        ArrayList<UsuarioEntity> usuarioEntityList;
        try {
            usuarioEntityList = getUsuarioServiceModel().obtenerUsarios();
            if (usuarioEntityList.contains(nuevoUsuario)) {
                errorText.setText("Ya hay una cuenta registrada con ese correo");
                return;
            }
            if (getUsuarioServiceModel().obtenerUsuarioPorUsuario(usuarioTextField.getText()) != null) {
                errorText.setText("Ya hay una cuenta registrada con ese usuario");
                return;
            }
            getUsuarioServiceModel().addUsuario(nuevoUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
            errorText.setText("error no controlado");
        }
        registrarToLoginOnClick();
    }

    /**
     * comprueba que los campos sean validos
     * 
     * @return true/false
     */
    private boolean comprobarRegistrar() {
        if (!comprobarTextField(usuarioTextField)) {
            errorText.setText("Usuario no puede estar vacio");
            return false;
        }
        if (!comprobarTextField(nombreTextField)) {
            errorText.setText("Nombre no puede estar vacio");
            return false;
        }
        if (!comprobarTextField(passwordField)) {
            errorText.setText("Contrasenia no puede estar vacio");
            return false;
        }
        if (!comprobarTextField(passwordField2)) {
            errorText.setText("Repetir contrasenia no puede estar vacio");
            return false;
        }
        if (!passwordField.getText().equals(passwordField2.getText())) {
            errorText.setText("La contrasenia repetida debe ser igual");
            return false;
        }
        if (!comprobarTextField(emailTextField1)) {
            errorText.setText("El correo no puede estar vacio");
            return false;
        }
        if (!comprobarTextField(emailTextField11)) {
            errorText.setText("Correo repetir de los valores puede estar vacio");
            return false;
        }
        if (!emailTextField1.getText().equals(emailTextField11.getText())) {
            errorText.setText("Los correos deben ser iguales");
            return false;
        }
        return true;
    }

    /**
     * vuelve a log in
     */
    @FXML
    private void registrarToLoginOnClick() {
        cambiarPantalla(regresarButton, "app-init", "registrar");
    }

    /**
     * cambiar idioma Registrar
     */
    public void cambiarIdiomaRegistrar() {

        usuarioTextField.setPromptText(ConfigManager.ConfigProperties.getProperty("usuarioTextField"));
        nombreText.setText(ConfigManager.ConfigProperties.getProperty("nombreText"));
        nombreTextField.setPromptText(ConfigManager.ConfigProperties.getProperty("nombreTextField"));

    }

}
