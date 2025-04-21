package es.nexphernandez.buscaminas.controller;


import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import es.nexphernandez.buscaminas.model.UsuarioEntity;
import es.nexphernandez.buscaminas.model.UsuarioServiceModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class ProfileController extends AbstractController {

    @FXML
    private Text textUser;
    @FXML
    private TextField textFieldUser;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private Text textMessage;
    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonClose;
    @FXML
    private Button buttonReturn;
    @FXML
    private Button buttonDelete;

    @FXML
    public void initialize() {

    }

    /**
     * Actualiza los datos del usuario.
     */
    @FXML
    public void buttonUpdateClick() {
        if (!validateCredentials()) {
            textMessage.setText("Credenciales invalidas");
            return;
        }
        UsuarioServiceModel userModel = new UsuarioServiceModel();
        UsuarioEntity user = new UsuarioEntity(textFieldUser.getText(), passwordFieldPassword.getText(),
                SessionModel.getUser().getAnswers(), SessionModel.getUser().getHits());
        userModel.updateUser(SessionModel.getUser(), user);
        textMessage.setText("Usuario actualizado");
    }

    /**
     * Cierra la sesion del usuario.
     */
    @FXML
    public void buttonCloseClick() {
        cambiarPantalla(buttonClose, "start");
    }

    /**
     * Vuelve a la pantalla de nivel.
     */
    @FXML
    public void buttonReturnClick() {
        cambiarPantalla(buttonReturn, "level");
    }

    /**
     * Elimina la cuenta del usuario.
     */
    @FXML
    public void buttonDeleteClick() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar tu cuenta?");
        alert.setContentText("Esta acción no se puede deshacer.");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                UsuarioServiceModel userModel = new UsuarioServiceModel();
                userModel.deleteUser(SessionModel.getUser());
                startScreen(buttonDelete); 
            } else {
                alert.close();
            }
        });
    }

    /**
     * Valida las credenciales.
     * @return retorna true si estas son validas.
     */
    private boolean validateCredentials() {
        return (textFieldUser != null && textFieldUser.getText() != null && !textFieldUser.getText().isBlank() &&
                passwordFieldPassword != null && passwordFieldPassword.getText() != null
                && !passwordFieldPassword.getText().isBlank());
    }

}