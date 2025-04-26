package es.nexphernandez.buscaminas.controller;

import java.io.IOException;

import es.nexphernandez.buscaminas.config.ConfigManager;
import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class InicioController extends AbstractController {

    @FXML
    private TextField userTextField;
    @FXML
    private ComboBox<String> dificultadComboBox;
    @FXML
    private Text minasText;
    @FXML
    private TextField nivelTextField;
    @FXML
    private Button regresarButton;
    @FXML
    public Button jugarButton;
    @FXML
    public Text errorText;
    @FXML
    public Text filasText;
    @FXML
    public Text columnasText;
    @FXML
    private TextField filasField;
    @FXML
    private TextField columnasField;
    @FXML
    private TextField minasField;

    @FXML
    public void initialize() {
        dificultadComboBox.getItems().clear();
        dificultadComboBox.getItems().addAll(
                ConfigManager.ConfigProperties.getProperty("dificultadFacil"),
                ConfigManager.ConfigProperties.getProperty("dificultadMedia"),
                ConfigManager.ConfigProperties.getProperty("dificultadDificil"),
                ConfigManager.ConfigProperties.getProperty("Personalizada"));
        dificultadComboBox.setValue(ConfigManager.ConfigProperties.getProperty("dificultadFacil"));

        onDificultadChange();
        cambiarIdiomaInicio();
    }

    /**
     * cambia las columnas a las personalizadas
     */
    @FXML
    private void onDificultadChange() {
        String seleccion = dificultadComboBox.getValue();
        boolean personalizada = "Personalizada".equals(seleccion);
        filasText.setDisable(!personalizada);
        columnasText.setDisable(!personalizada);
        minasText.setDisable(!personalizada);
        filasField.setDisable(!personalizada);
        columnasField.setDisable(!personalizada);
        minasField.setDisable(!personalizada);
    }

    /**
     * Cambia la pantalla a la partida y configura la dificultad
     * según lo que haya seleccionado el usuario del usuario.
     */
    @FXML
    private void inicioToPartidaOnClick() {
        String seleccion = dificultadComboBox.getValue();
        int filas = 0;
        int columnas = 0;
        int minas = 0;

        try {
            switch (seleccion) {
                case "Fácil":
                case "Easy":
                    filas = 8;
                    columnas = 8;
                    minas = 10;
                    break;
                case "Media":
                case "Medium":
                    filas = 12;
                    columnas = 12;
                    minas = 20;
                    break;
                case "Difícil":
                case "Hard":
                    filas = 16;
                    columnas = 16;
                    minas = 40;
                    break;
                case "Personalizada":
                    filas = Integer.parseInt(filasField.getText());
                    columnas = Integer.parseInt(columnasField.getText());
                    minas = Integer.parseInt(minasField.getText());

                    if (filas < 1 || columnas < 1 || minas < 1 || minas >= filas * columnas) {
                        mensajeError("Valores inválidos. Asegúrate de que haya al menos 1 mina y espacio suficiente.");
                        return;
                    }
                    break;
                default:
                    mensajeError("Selecciona una dificultad.");
                    return;
            }
        } catch (NumberFormatException e) {
            mensajeError("Introduce valores numéricos válidos.");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/play.fxml"));
        try {
            Stage stage = (Stage) jugarButton.getScene().getWindow();
            Scene scene = new Scene(loader.load());
            PlayController playController = loader.getController();
            playController.configurarPartida(filas, columnas, minas);
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mensajeError(String mensaje) {
        errorText.setText(mensaje);
    }

    @FXML
    private void inicioToLoginOnClick() {
        cambiarPantalla(regresarButton, "app-init", "app-init");
    }

    /**
     * cambiar idioma de la pantalla inicio
     */
    public void cambiarIdiomaInicio() {
        jugarButton.setText(ConfigManager.ConfigProperties.getProperty("jugarButton"));
        regresarButton.setText(ConfigManager.ConfigProperties.getProperty("regresarButton"));

        dificultadComboBox.getItems().clear();
        dificultadComboBox.getItems().addAll(
                ConfigManager.ConfigProperties.getProperty("dificultadFacil"),
                ConfigManager.ConfigProperties.getProperty("dificultadMedia"),
                ConfigManager.ConfigProperties.getProperty("dificultadDificil"),
                ConfigManager.ConfigProperties.getProperty("Personalizada"));
        dificultadComboBox.setPromptText(ConfigManager.ConfigProperties.getProperty("dificultadBoxPrompt"));
    }
}
