package es.nexphernandez.buscaminas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
/**
 * @author nexphernandez
 * @version 1.0.0
 */
import java.util.Random;

import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;

public class PlayController extends AbstractController{

    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private static final int MINAS = 10; 
    
    @FXML
    private GridPane grid;

    @FXML
    private Button nuevoJuegoBtn;

    @FXML
    private Button atrasButon;

    @FXML
    private Label mensajeLabel;

    private int[][] tablero;

    @FXML
    void initialize() {
        crearTablero(FILAS, COLUMNAS); 
    }

    @FXML
    void nuevoJuego() {
        mensajeLabel.setText("Nuevo juego iniciado!");
        crearTablero(FILAS, COLUMNAS);
    }

    @FXML
    protected void onAtrasClick(){
        cambiarPantalla(atrasButon, "inicio", "play");
    }

    private void crearTablero(int filas, int columnas) {
        grid.getChildren().clear(); 
        tablero = new int[filas][columnas];
        colocarMinas(MINAS);
        contarMinasAlrededor();

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button btn = new Button();
                btn.setPrefSize(30, 30);
                final int fila = i;
                final int columna = j;
                btn.setOnAction(e -> manejarBoton(btn, fila, columna));
                grid.add(btn, j, i);
            }
        }
    }

    private void colocarMinas(int cantidadMinas) {
        Random random = new Random();
        for (int i = 0; i < cantidadMinas; i++) {
            int fila;
            int col;
            do {
                fila = random.nextInt(FILAS);
                col = random.nextInt(COLUMNAS);
            } while (tablero[fila][col] == -1); 
            tablero[fila][col] = -1; 
        }
    }

    private void contarMinasAlrededor() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == -1) continue; 
                int contador = 0;

               
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) continue; 
                        int nuevaFila = i + x;
                        int nuevaCol = j + y;
                        
                        if (nuevaFila >= 0 && nuevaFila < tablero.length && nuevaCol >= 0 && nuevaCol < tablero[0].length) {
                            if (tablero[nuevaFila][nuevaCol] == -1) contador++;
                        }
                    }
                }
                tablero[i][j] = contador; 
            }
        }
    }

    private void manejarBoton(Button btn, int fila, int columna) {
        btn.setDisable(true);
        if (tablero[fila][columna] == -1) {
            btn.setText("💣"); 
            mensajeLabel.setText("¡Perdiste!"); 
            
        } else {
            btn.setText(String.valueOf(tablero[fila][columna])); 
        }
    }
    

}