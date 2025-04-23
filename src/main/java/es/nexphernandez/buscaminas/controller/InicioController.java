package es.nexphernandez.buscaminas.controller;

import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class InicioController extends AbstractController{

    @FXML
    public Text userText;
    @FXML
    private TextField userTextField;
    @FXML
    public ComboBox<String> dificultadBox;
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
        dificultadBox.getItems().addAll("Fácil", "Medio", "Difícil", "Personalizada");
        dificultadBox.setValue("Fácil");
        onDificultadChange();

    }

    /**
     * cambia las columnas a las personalizadas
     */
    @FXML
    private void onDificultadChange() {
        String seleccion = (String) dificultadBox.getValue();
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

        String seleccion = (String) dificultadBox.getValue();
        int filas = 0;
        int columnas = 0;
        int minas = 0;

        switch (seleccion) {
            case "Fácil":
                filas = 8;
                columnas = 8;
                minas = 10;
                break;
            case "Medio":
                filas = 12;
                columnas = 12;
                minas = 20;
                break;
            case "Difícil":
                filas = 16;
                columnas = 16;
                minas = 40;
                break;
            case "Personalizada":
                try {
                    filas = Integer.parseInt(filasField.getText());
                    columnas = Integer.parseInt(columnasField.getText());
                    minas = Integer.parseInt(minasField.getText());

                    if (filas < 1 || columnas < 1 || minas < 1 || minas >= filas * columnas) {
                        mensajeError("Valores inválidos. Asegúrate de que haya al menos 1 mina y espacio suficiente.");
                        return;
                    }
                } catch (NumberFormatException e) {
                    mensajeError("Introduce valores numéricos válidos.");
                    return;
                }
                break;
            default:
                mensajeError("Selecciona una dificultad.");
                return;
        }

        ConfiguracionDePartida.set(filas, columnas, minas);
        cambiarPantalla(jugarButton, "play", "app-init");
    }

    private void mensajeError(String mensaje) {
        errorText.setText(mensaje);
    }

    @FXML
    private void inicioToLoginOnClick() {
        cambiarPantalla(regresarButton, "app-init", "app-init");
    }

}
