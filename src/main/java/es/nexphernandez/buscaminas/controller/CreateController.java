package es.nexphernandez.buscaminas.controller;




import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import es.nexphernandez.buscaminas.model.UsuarioEntity;
import es.nexphernandez.buscaminas.model.UsuarioServiceModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class CreateController extends AbstractController {

    @FXML
    private TextField textFieldUser;
    @FXML
    private PasswordField passwordFieldPassword;
    @FXML
    private PasswordField passwordFieldRepeatPassword;
    @FXML
    private Text textMessage;
    @FXML
    private Button buttonAccept;
    @FXML
    private Button buttonStart;

    /**
     * Crea un nuevo usuario.
     */
    @FXML
    public void buttonAcceptClick() {
        if (!validateCredentials()) {
            textMessage.setText("Credenciales invalidas");
            return;
        }
        if (!passwordFieldPassword.getText().equals(passwordFieldRepeatPassword.getText())) {
            textMessage.setText("Las contraseñas deben coincidir");
            return;
        }
        UsuarioEntity user = new UsuarioEntity(textFieldUser.getText(), passwordFieldPassword.getText());
        UsuarioServiceModel userModel = new UsuarioServiceModel();
        if (userModel.createUser(user)) {
            textMessage.setText("Usuario creado con exito");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText(null);
            alert.setContentText("Sesión creada con éxito. Redirigiendo a la pantalla de inicio...");
            alert.showAndWait();

            startScreen(buttonStart);
            return;
        }
        textMessage.setText("Usuario no pudo crearse");
    }

    /**
     * Cambia a la pantalla iniciar.
     */
    @FXML
    public void buttonStartClick() {
        startScreen(buttonStart);
    }

    /**
     * Valida las credenciales.
     * 
     * @return retorna true si estas son validas.
     */
    private boolean validateCredentials() {
        return (textFieldUser != null && textFieldUser.getText() != null && !textFieldUser.getText().trim().isBlank() &&
                passwordFieldPassword != null && passwordFieldPassword.getText() != null
                && !passwordFieldPassword.getText().trim().isBlank() &&
                passwordFieldRepeatPassword != null && passwordFieldRepeatPassword.getText() != null
                && !passwordFieldRepeatPassword.getText().trim().isBlank());
    }

}