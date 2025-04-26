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


        if (grid.getScene() != null) {
            grid.getScene().getStylesheets().add(getClass().getResource("@../styles/stylePlay.css").toExternalForm());
        }
    }

    /**
     * Configura la partida con el número de filas, columnas y minas.
     *
     * @param filas    Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     * @param minas    Número de minas en el tablero.
     */
    public void configurarPartida(int filas, int columnas, int minas) {
        this.filas = filas;
        this.columnas = columnas;
        this.minas = minas;

        crearTablero(filas, columnas);

        Platform.runLater(() -> ajustarTamañoVentana());
    }

    /**
     * Ajusta el tamaño de la ventana del juego según el número de filas y columnas.
     */
    private void ajustarTamañoVentana() {
        
        int tamañoCelda = 30;
        int anchoGrid = columnas * tamañoCelda;
        int altoGrid = filas * tamañoCelda;
        grid.setPrefSize(anchoGrid, altoGrid);

        Stage stage = (Stage) grid.getScene().getWindow();

        stage.setWidth(anchoGrid + 100); 
        stage.setHeight(altoGrid + 250); 
    }

    /**
     * Reinicia el juego y limpia el mensaje de la etiqueta.
     */
    @FXML
    void nuevoJuego() {
        mensajeLabel.setText(""); 
        crearTablero(filas, columnas);
    }

    /**
     * Cambia a la pantalla de inicio.
     */
    @FXML
    protected void onAtrasClick() {
        cambiarPantalla(atrasButon, "inicio", "play");
    }

    /**
     * Crea el tablero del juego con el número de filas y columnas especificadas.
     *
     * @param filas    Número de filas del tablero.
     * @param columnas Número de columnas del tablero.
     */
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
                btn.setPrefSize(30, 30); 
                btn.setMinSize(30, 30); 
                btn.setMaxSize(30, 30); 
                final int fila = i;
                final int columna = j;

                
                btn.setOnMouseClicked(e -> {
                    if (e.getButton().name().equals("PRIMARY")) { 
                        manejarBoton(btn, fila, columna);
                    } else if (e.getButton().name().equals("SECONDARY")) { 
                        colocarBandera(btn, fila, columna);
                    }
                });

                grid.add(btn, j, i);
            }
        }

       
        grid.setAlignment(Pos.CENTER);
    }

    /**
     * Coloca o quita una bandera en la celda seleccionada.
     *
     * @param btn     Botón que representa la celda.
     * @param fila    Fila de la celda.
     * @param columna Columna de la celda.
     */
    private void colocarBandera(Button btn, int fila, int columna) {
        if (descubiertas[fila][columna]) {
            return;
        }

        if (btn.getGraphic() == null) {
            Image banderaImagen = new Image(
                    getClass().getResourceAsStream("/img/bandera.png"));
            ImageView banderaView = new ImageView(banderaImagen);
            banderaView.setFitWidth(20); 
            banderaView.setFitHeight(20);
            btn.setGraphic(banderaView); 
        } else {
            btn.setGraphic(null); 
        }
    }

    /**
     * Coloca minas aleatoriamente en el tablero.
     *
     * @param cantidadMinas Número de minas a colocar.
     */
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

    /**
     * Cuenta el número de minas adyacentes a cada celda del tablero.
     */
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

    /**
     * Maneja el clic en un botón del tablero.
     *
     * @param btn     Botón que representa la celda.
     * @param fila    Fila de la celda.
     * @param columna Columna de la celda.
     */
    private void manejarBoton(Button btn, int fila, int columna) {
        if (descubiertas[fila][columna]) {
            return;
        }

        descubiertas[fila][columna] = true;

        if (tablero[fila][columna] == -1) {
            btn.setStyle("-fx-background-color: red;"); 

            Image bombaImagen = new Image(
                    getClass().getResourceAsStream("/img/bomba.png"));
            ImageView bombaView = new ImageView(bombaImagen);
            bombaView.setFitWidth(20); 
            bombaView.setFitHeight(20);
            btn.setGraphic(bombaView); 

            mensajeLabel.setText(ConfigManager.ConfigProperties.getProperty("mensajeLabel"));

            deshabilitarTablero(); 
            return;
        }

        int minasCercanas = tablero[fila][columna];
        if (minasCercanas > 0) {
            btn.setText(String.valueOf(minasCercanas));
            btn.setDisable(true); 

            btn.getStyleClass().removeIf(style -> style.startsWith("button-number-"));
            btn.getStyleClass().add("button-number-" + minasCercanas);
        } else {
            btn.setDisable(true);
            descubrirAdyacentes(fila, columna);
        }

        celdasDescubiertas++;
        if (celdasDescubiertas == (filas * columnas - minas)) {
            mensajeLabel.setText("¡Felicidades, has ganado!");
            deshabilitarTablero();
        }
    }

    /**
     * Descubre las celdas adyacentes a la celda seleccionada.
     *
     * @param fila    Fila de la celda seleccionada.
     * @param columna Columna de la celda seleccionada.
     */
    private void descubrirAdyacentes(int fila, int columna) {
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0)
                    continue;

                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;

                if (nuevaFila >= 0 && nuevaFila < filas && nuevaColumna >= 0 && nuevaColumna < columnas) {
                    if (!descubiertas[nuevaFila][nuevaColumna]) {
                        Button btn = (Button) getNodeFromGridPane(grid, nuevaFila, nuevaColumna);
                        manejarBoton(btn, nuevaFila, nuevaColumna);
                    }
                }
            }
        }
    }

    /**
     * Deshabilita todos los botones del tablero.
     */
    private void deshabilitarTablero() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                Button btn = (Button) getNodeFromGridPane(grid, i, j);
                btn.setDisable(true);
            }
        }
    }

    /**
     * Obtiene un nodo específico del GridPane según su fila y columna.
     *
     * @param gridPane El GridPane del que se desea obtener el nodo.
     * @param fila     La fila del nodo.
     * @param columna  La columna del nodo.
     * @return El nodo correspondiente a la fila y columna especificadas, o null si no se encuentra.
     */
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

        if (!mensajeLabel.getText().isEmpty()) {
            mensajeLabel.setText(ConfigManager.ConfigProperties.getProperty("mensajeLabel"));
        }
    }
}