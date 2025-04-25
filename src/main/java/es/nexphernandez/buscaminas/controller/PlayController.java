package es.nexphernandez.buscaminas.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
import java.util.Random;

import es.nexphernandez.buscaminas.config.ConfigManager;
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

    @FXML
    public void initialize() {
        mensajeLabel.setText(""); 
        crearTablero(filas, columnas); 
        cambiarIdiomaPlay();
    }

    public void configurarPartida(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;

        crearTablero(filas, columnas);

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
        mensajeLabel.setText(""); // Limpia el mensaje
        crearTablero(filas, columnas); // Reinicia el tablero
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
                btn.setMinSize(30, 30); // Tamaño mínimo
                btn.setMaxSize(30, 30); // Tamaño máximo
                final int fila = i;
                final int columna = j;

                // Manejar clics del mouse
                btn.setOnMouseClicked(e -> {
                    if (e.getButton().name().equals("PRIMARY")) { // Clic izquierdo
                        manejarBoton(btn, fila, columna);
                    } else if (e.getButton().name().equals("SECONDARY")) { // Clic derecho
                        colocarBandera(btn, fila, columna);
                    }
                });

                grid.add(btn, j, i);
            }
        }

        // Establecer alineación del GridPane
        grid.setAlignment(Pos.CENTER);
    }

    private void colocarBandera(Button btn, int fila, int columna) {
        // Si la celda ya está descubierta, no hacemos nada
        if (descubiertas[fila][columna]) {
            return;
        }

        // Alternar entre colocar y quitar la bandera
        if (btn.getGraphic() == null) {
            // Cargar la imagen de la banderita
            Image banderaImagen = new Image(
                    getClass().getResourceAsStream("/img/bandera.png"));
            ImageView banderaView = new ImageView(banderaImagen);
            banderaView.setFitWidth(20); // Ajusta el tamaño de la imagen
            banderaView.setFitHeight(20);
            btn.setGraphic(banderaView); // Colocar la banderita
        } else {
            btn.setGraphic(null); // Quitar la banderita
        }
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
        
            // Cargar la imagen de la bomba
            Image bombaImagen = new Image(
                    getClass().getResourceAsStream("/img/bomba.png"));
            ImageView bombaView = new ImageView(bombaImagen);
            bombaView.setFitWidth(20); // Ajusta el tamaño de la imagen
            bombaView.setFitHeight(20);
            btn.setGraphic(bombaView); // Establece la imagen en el botón
        
            // Establecer el mensaje de "Has perdido" dinámicamente
            mensajeLabel.setText(ConfigManager.ConfigProperties.getProperty("mensajeLabel"));
        
            deshabilitarTablero(); // Finalizamos el juego
            return;
        }

        // Si no es una mina, mostramos el número de minas adyacentes
        int minasCercanas = tablero[fila][columna];
        if (minasCercanas > 0) {
            btn.setText(String.valueOf(minasCercanas));
            btn.setDisable(true); // Deshabilitamos el botón

            // Cambiar el color del texto según el número
            switch (minasCercanas) {
                case 1:
                    btn.setStyle("-fx-text-fill:rgb(0, 183, 255);"); // Azul para 1
                    break;
                case 2:
                    btn.setStyle("-fx-text-fill: rgb(0, 255, 64);"); // Verde para 2
                    break;
                case 3:
                    btn.setStyle("-fx-text-fill: rgb(255, 0, 0);"); // Rojo para 3
                    break;
                case 4:
                    btn.setStyle("-fx-text-fill: rgb(174, 0, 255);"); // Morado para 4
                    break;
                case 5:
                    btn.setStyle("-fx-text-fill: rgb(255, 94, 0);"); // Marrón para 5
                    break;
                case 6:
                    btn.setStyle("-fx-text-fill: rgb(255, 0, 251);"); // Turquesa para 6
                    break;
                case 7:
                    btn.setStyle("-fx-text-fill: rgb(3, 94, 29);"); // Negro para 7
                    break;
                case 8:
                    btn.setStyle("-fx-text-fill: rgb(246, 255, 0);"); // Gris para 8
                    break;
            }
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

    /**
     * cambiar idioma de la pantalla play
     */
    public void cambiarIdiomaPlay() {
        nuevoJuegoBtn.setText(ConfigManager.ConfigProperties.getProperty("nuevoJuegoBtn"));
        atrasButon.setText(ConfigManager.ConfigProperties.getProperty("atrasButon"));
    
        // Si el mensajeLabel no está vacío, actualízalo con el mensaje traducido
        if (!mensajeLabel.getText().isEmpty()) {
            mensajeLabel.setText(ConfigManager.ConfigProperties.getProperty("mensajeLabel"));
        }
    }
}