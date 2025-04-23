package es.nexphernandez.buscaminas.controller;
/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class ConfiguracionDePartida {
    public static int filas = 10;
    public static int columnas = 10;
    public static int minas = 10;

    public static void set(int f, int c, int m) {
        filas = f;
        columnas = c;
        minas = m;
    }
}
