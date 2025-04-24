package es.nexphernandez.buscaminas.controller.abstractas;


import java.io.IOException;
import java.util.Properties;
import es.nexphernandez.buscaminas.PrincipalApplication;
import es.nexphernandez.buscaminas.config.ConfigManager;
import es.nexphernandez.buscaminas.model.UsuarioServiceModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public abstract class AbstractController {

    private final String pathFichero = "src/main/resources/";
    private final String ficheroStr = "idioma-";
    private static String idiomaActual = "es";

    private String pantallaAnterior;

    static final String PATH_DB = "src/main/resources/db/usuarios.db";

    private UsuarioServiceModel usuarioServiceModel;

    private Properties propertiesIdioma;

    /**
     * Constructor
     */
    protected AbstractController() {
        try {
            usuarioServiceModel = new UsuarioServiceModel(PATH_DB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * setea el properties
     * 
     * @param properties
     */
    public void setpropertiesIdioma(Properties properties) {
        propertiesIdioma = properties;
    }

    /**
     * retorna el properties
     * 
     * @return Properties
     */
    public Properties getPropertiesIdioma() {
        return propertiesIdioma;
    }

    public static String getIdioma() {
        return idiomaActual;
    }

    public static void setIdioma(String idioma) {
        idiomaActual = idioma;
    }

    /**
     * Funcion para cargar el idioma
     * 
     * @param idioma a cargar
     */
    protected String cargarIdiomaActual() {
        if (idiomaActual == null || idiomaActual.isEmpty()) {
            idiomaActual = "es";
        }

        String path = pathFichero + ficheroStr + idiomaActual + ".properties";
        ConfigManager.ConfigProperties.setPath(path);
        return idiomaActual;
    }

    /**
     * retorna el usuarioServiceModel
     * 
     * @return UsuarioServiceModel
     */
    public UsuarioServiceModel getUsuarioServiceModel() {
        return this.usuarioServiceModel;
    }

    /**
     * comprueba que los textField sean correctos
     * 
     * @param campo
     * @return true/false
     */
    @FXML
    public boolean comprobarTextField(TextField campo) {
        if (campo.getText() == null || campo.getText().isEmpty()) {
            return false;
        }
        return campo.getText() != null || !(campo.getText().isEmpty());
    }

    /**
     * cambia a la pantalla indicada usando el boton que se le pasa como referencia
     * 
     * @param botton
     * @param pantalla
     */
    @FXML
    public void cambiarPantalla(Button botton, String pantalla, String pantallaAnterior) {
        try {
            this.pantallaAnterior = pantallaAnterior;
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("/view/" +pantalla + ".fxml"));
            Stage stage = (Stage) botton.getScene().getWindow();
            Scene scene;
            scene = new Scene(fxmlLoader.load());
            stage.setResizable(false);
            stage.setTitle("Pantalla Princial");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        /**
     * cambia a la pantalla indicada usando el boton que se le pasa como referencia
     * 
     * @param botton
     * @param pantalla
     */
    @FXML
    public void cambiarPantalla(Hyperlink botton, String pantalla, String pantallaAnterior) {
        try {
            this.pantallaAnterior = pantallaAnterior;
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("/view/" +pantalla + ".fxml"));
            Stage stage = (Stage) botton.getScene().getWindow();
            Scene scene;
            scene = new Scene(fxmlLoader.load());
            stage.setResizable(false);
            stage.setTitle("Pantalla Princial");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * cambia a la pantalla anterior usando el boton que se le pasa como referencia
     * 
     * @param botton
     * @param pantalla
     */
    @FXML
    public void cambiarPantalla(Button botton) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource("/view/" +pantallaAnterior + ".fxml"));
            Stage stage = (Stage) botton.getScene().getWindow();
            Scene scene;
            scene = new Scene(fxmlLoader.load());
            stage.setResizable(false);
            stage.setTitle("Pantalla Princial");
            stage.setScene(scene);
            stage.sizeToScene();
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}