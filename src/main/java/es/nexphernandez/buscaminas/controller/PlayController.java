package es.nexphernandez.buscaminas.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
/**
 * @author nexphernandez
 * @version 1.0.0
 */
import java.util.Random;

import es.nexphernandez.buscaminas.controller.abstractas.AbstractController;

public class PlayController extends AbstractController {
    private int filas;
    private int columnas;
    private int minas;

    @FXML
    private GridPane grid;

    @FXML
    private Button nuevoJuegoBtn;

    @FXML
    private Button atrasButon;

    @FXML
    private Label mensajeLabel;

    private int[][] tablero;
    private boolean[][] descubiertas;
    private int celdasDescubiertas = 0;

    public void configurarPartida(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;
    
        // Crear el tablero
        crearTablero(filas, columnas);
    
        // Ajustar el tamaño de la ventana según el tablero
        Platform.runLater(() -> ajustarTamañoVentana());
    }
    
   private void ajustarTamañoVentana() {
        // Tamaño de cada celda
        int tamañoCelda = 30;
    
        // Calcular tamaño total del GridPane
        int anchoGrid = columnas * tamañoCelda;
        int altoGrid = filas * tamañoCelda;
    
        // Ajustar el tamaño del GridPane
        grid.setPrefSize(anchoGrid, altoGrid);
    
        // Obtener el Stage desde el GridPane
        Stage stage = (Stage) grid.getScene().getWindow();
    
        // Ajustar el tamaño del Stage
        stage.setWidth(anchoGrid + 100); // +100 para márgenes adicionales
        stage.setHeight(altoGrid + 250); // +150 para botones, etiquetas, etc.
    }
    

    @FXML
    void nuevoJuego() {
        mensajeLabel.setText("Nuevo juego iniciado!");
        crearTablero(filas, columnas);
    }

    @FXML
    protected void onAtrasClick() {
        cambiarPantalla(atrasButon, "inicio", "play");
    }

    private void crearTablero(int filas, int columnas) {
        grid.getChildren().clear();
        tablero = new int[filas][columnas];
        descubiertas = new boolean[filas][columnas];
        celdasDescubiertas = 0;
    
        colocarMinas(minas);
        contarMinasAlrededor();
    
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button btn = new Button();
                btn.setPrefSize(30, 30); // Tamaño fijo para cada celda
                final int fila = i;
                final int columna = j;
                btn.setOnAction(e -> manejarBoton(btn, fila, columna));
                grid.add(btn, j, i);
            }
        }
    
        // Establecer alineación del GridPane
        grid.setAlignment(Pos.CENTER);
    }

    private void colocarMinas(int cantidadMinas) {
        Random random = new Random();
        for (int i = 0; i < cantidadMinas; i++) {
            int fila;
            int col;
            do {
                fila = random.nextInt(filas);
                col = random.nextInt(columnas);
            } while (tablero[fila][col] == -1);
            tablero[fila][col] = -1;
        }
    }

    private void contarMinasAlrededor() {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == -1)
                    continue;
                int contador = 0;

                for (int x = -1; x <= 1; x++) {
                    for (int y = -1; y <= 1; y++) {
                        if (x == 0 && y == 0)
                            continue;
                        int nuevaFila = i + x;
                        int nuevaCol = j + y;

                        if (nuevaFila >= 0 && nuevaFila < tablero.length &&
                                nuevaCol >= 0 && nuevaCol < tablero[0].length &&
                                tablero[nuevaFila][nuevaCol] == -1) {
                            contador++;
                        }
                    }
                }
                tablero[i][j] = contador;
            }
        }
    }

    private void manejarBoton(Button btn, int fila, int columna) {
        // Si la celda ya fue descubierta, no hacemos nada
        if (descubiertas[fila][columna]) {
            return;
        }

        // Marcamos la celda como descubierta
        descubiertas[fila][columna] = true;

        // Verificamos si la celda contiene una mina
        if (tablero[fila][columna] == -1) {
            btn.setStyle("-fx-background-color: red;"); // Cambiamos el color para indicar la mina
            btn.setText("💣"); // Mostramos una mina
            mensajeLabel.setText("¡Has perdido! Pulsa 'Nuevo Juego' para reiniciar.");
            deshabilitarTablero(); // Finalizamos el juego
            return;
        }

        // Si no es una mina, mostramos el número de minas adyacentes
        int minasCercanas = tablero[fila][columna];
        if (minasCercanas > 0) {
            btn.setText(String.valueOf(minasCercanas));
            btn.setDisable(true); // Deshabilitamos el botón
        } else {
            // Descubrimos celdas adyacentes si la celda está vacía
            btn.setDisable(true);
            descubrirAdyacentes(fila, columna);
        }

        // Actualizamos el conteo de celdas descubiertas
        celdasDescubiertas++;
        if (celdasDescubiertas == (filas * columnas - minas)) {
            mensajeLabel.setText("¡Felicidades, has ganado!");
            deshabilitarTablero();
        }
    }

    private void descubrirAdyacentes(int fila, int columna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                // Ignoramos la celda actual
                if (i == 0 && j == 0)
                    continue;

                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;

                // Validamos que la celda esté dentro del tablero
                if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas) {
                    if (!descubiertas[nuevaFila][nuevaColumna]) {
                        Button btn = (Button) getNodeFromGridPane(grid, nuevaFila, nuevaColumna);
                        manejarBoton(btn, nuevaFila, nuevaColumna);
                    }
                }
            }
        }
    }

    private void deshabilitarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button btn = (Button) getNodeFromGridPane(grid, i, j);
                btn.setDisable(true);
            }
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int fila, int columna) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getRowIndex(node) == fila && GridPane.getColumnIndex(node) == columna) {
                return node;
            }
        }
        return null;
    }
}