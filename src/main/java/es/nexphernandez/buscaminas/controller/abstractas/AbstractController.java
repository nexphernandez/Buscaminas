package es.nexphernandez.buscaminas.controller.abstractas;

import es.nexphernandez.buscaminas.PrincipalApplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public abstract class AbstractController {

    /**
     * Ejecuta el cambio de pantalla.
     * 
     * @param button  boton a ejecutar.
     * @param fxmlPath path del fxml.
     * @return retorna los controladores del fxml.
     */
    private Object changeScreen(Button button, String fxmlPath, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource(fxmlPath));
            Scene scene = new Scene(fxmlLoader.load(), 340, 640);
            Stage stage = (Stage) button.getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            return fxmlLoader.getController();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Cambia a la pantalla iniciar.
     * 
     * @param button boton a ejecutar.
     */
    protected void startScreen(Button button) {
        changeScreen(button, "/view/start.fxml", "Memorama");
    }

    /**
     * Cambia a la pantalla crear.
     * 
     * @param button boton a ejecutar.
     */
    protected void createScreen(Button button) {
        changeScreen(button, "/view/create.fxml", "Crear");
    }

    /**
     * Cambia a la pantalla nivel.
     * 
     * @param button boton a ejecutar.
     */
    protected void levelScreen(Button button) {
        changeScreen(button, "/view/level.fxml", "Nivel");
    }

    /**
     * Cambia a la pantalla perfil.
     * 
     * @param button boton a ejecutar.
     */
    protected void profileScreen(Button button) {
        changeScreen(button, "/view/profile.fxml", "Perfil");
    }

    /**
     * Cambia a la pantalla jugar.
     * 
     * @param button boton a ejecutar.
     */
    protected void playScreen(Button button) {
        changeScreen(button, "/view/play.fxml", "Jugar");
    }

}