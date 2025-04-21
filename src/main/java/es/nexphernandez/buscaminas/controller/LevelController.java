package es.nexphernandez.buscaminas.controller;



import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class LevelController extends AbstractController {

    @FXML
    private Text textUser;
    @FXML
    private Text textAnswers;
    @FXML
    private Text textHits;
    @FXML
    private ComboBox<String> comboBoxLevel;
    @FXML
    private Button buttonPlay;
    @FXML
    private Button buttonProfile;
    @FXML
    private Button buttonStart;

    @FXML
    public void initialize() {
        comboBoxLevel.getItems().addAll("Easy", "Medium", "Hard");
        comboBoxLevel.setValue("Medium");
    }

    /**
     * Selecciona la dificultad.
     */
    @FXML
    public void comboBoxLevelClick() {
    }

    /**
     * Cambia a la pantalla jugar.
     */
    @FXML
    public void buttonPlayClick() {
        playScreen(buttonPlay);
    }

    /**
     * Cambia a la pantalla perfil.
     */
    @FXML
    public void buttonProfileClick() {
        profileScreen(buttonProfile);
    }

    /**
     * Cambia a la pantalla iniciar.
     */
    @FXML
    public void buttonStartClick() {
        startScreen(buttonStart);
    }

}