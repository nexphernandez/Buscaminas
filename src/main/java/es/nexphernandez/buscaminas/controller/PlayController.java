package es.nexphernandez.buscaminas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;

import java.util.Random;

public class PlayController {

    private static final int FILAS = 10;
    private static final int COLUMNAS = 10;
    private static final int MINAS = 10; // Cantidad de minas en el tablero
    
    @FXML
    private GridPane grid;

    @FXML
    private Button nuevoJuegoBtn;

    @FXML
    private Label mensajeLabel;

    private int[][] tablero;

    @FXML
    void initialize() {
        crearTablero(FILAS, COLUMNAS); // Crea un tablero de 10x10
    }

    @FXML
    void nuevoJuego() {
        mensajeLabel.setText("Nuevo juego iniciado!");
        crearTablero(FILAS, COLUMNAS);
    }

    private void crearTablero(int filas, int columnas) {
        grid.getChildren().clear(); // Limpia el grid antes de crear uno nuevo
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
            } while (tablero[fila][col] == -1); // Asegúrate de que no haya una mina ya allí
            tablero[fila][col] = -1; // Usa -1 para representar una mina
        }
    }

    private void contarMinasAlrededor() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == -1) continue; // Si es una mina, la saltamos
                int contador = 0;

                // Verifica las celdas adyacentes
                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0) continue; // Ignora la celda actual
                        int nuevaFila = i + x;
                        int nuevaCol = j + y;
                        // Verificar si la nueva posición está dentro del tablero
                        if (nuevaFila >= 0 && nuevaFila < tablero.length && nuevaCol >= 0 && nuevaCol < tablero[0].length) {
                            if (tablero[nuevaFila][nuevaCol] == -1) contador++;
                        }
                    }
                }
                tablero[i][j] = contador; // Guarda el conteo de minas
            }
        }
    }

    private void manejarBoton(Button btn, int fila, int columna) {
        // Aquí va la lógica para descubrir la casilla
        btn.setDisable(true); // Desactiva el botón después de hacer clic
        if (tablero[fila][columna] == -1) {
            btn.setText("💣"); // Representar la mina
            mensajeLabel.setText("¡Perdiste!"); // Mensaje de pérdida
            // Aquí podrías desactivar todos los botones o revelar todas las minas
        } else {
            btn.setText(String.valueOf(tablero[fila][columna])); // Muestra el número de minas adyacentes
        }
    }
}