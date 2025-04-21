package es.nexphernandez.buscaminas.controller.abstractas;

import java.io.IOException;

import es.nexphernandez.buscaminas.PrincipalApplication;
import es.nexphernandez.buscaminas.model.UsuarioEntity;
import es.nexphernandez.buscaminas.model.UsuarioServiceModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public abstract class AbstractController {

    private UsuarioServiceModel usuarioServiceModel;

    private static UsuarioEntity usuarioActual;
    /**
     * Constructor
     */
    protected AbstractController() {
        usuarioServiceModel = new UsuarioServiceModel();
    }

    /**
     * retorna el usuarioservicemodel para poder trabajar con el
     * @return UsuarioServiceModel
     */
    public UsuarioServiceModel getUsuarioServiceModel() {
        return this.usuarioServiceModel;
    }

    /**
     * setea al usuario actual
     * 
     * @param usuario a ser el actual
     */
    public void setUsuarioActual(UsuarioEntity usuario){
        this.usuarioActual = usuario;
    }

    /**
     * retorna el usuario actual
     * @return UsuarioEntity
     */
    public UsuarioEntity getUsuarioActual(){
        return usuarioActual;
    }
    /**
     * cambia a la pantalla indicada usando el boton que se le pasa como referencia
     * @param botton
     * @param pantalla
     */
    @FXML
    public void cambiarPantalla( Button botton, String pantalla){
        try {
            Stage stage = (Stage) botton.getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader(PrincipalApplication.class.getResource(pantalla+".fxml"));
            Scene scene;
            scene = new Scene(fxmlLoader.load(), 350, 500);
            stage.setResizable(false);
            stage.setTitle("Pantalla Princial");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * comprueba que los textField sean correctos
     * @param campo
     * @return true/false
     */
    @FXML
    public boolean comprobarTextField( TextField campo){
        if (campo.getText() == null || campo.getText().isEmpty()) {
            return false;
        }
        return true;
    }

}