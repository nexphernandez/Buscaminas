package es.nexphernandez.buscaminas.model.conexion;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 * @author nexphernandez
 * @version 1.0.0
 */
public abstract class Conexion {

    private String rutaArchivoBD;
    private Connection connection;

    /**
     * Constructor vacio
     */
    protected Conexion(){}

    /**
     * Constructor con path de conexion
     * 
     * @param unaRutaArchivoBD ruta de la bbdd
     * @throws SQLException error controlado
     */
    protected Conexion(String unaRutaArchivoBD) throws SQLException {
        if (unaRutaArchivoBD == null || unaRutaArchivoBD.isEmpty()) {
            throw new SQLException("El fichero es nullo o vacio");
        }
        File file = new File(unaRutaArchivoBD);
        if (!file.exists()) {
            throw new SQLException("No exise la bbdd:" + unaRutaArchivoBD);
        }

        rutaArchivoBD = unaRutaArchivoBD;
    }

    //Getters y Setters
    public String getRutaArchivoBD() {
        return this.rutaArchivoBD;
    }

    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection("jdbc:sqlite:" + rutaArchivoBD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return this.connection;
    }

    /**
     * Funcion que abre la conexion a la bbdd
     * @return
     * @throws SQLException
     */
    public Connection conectar() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + rutaArchivoBD);
        }
        return connection;
    }

    /**
     * Funcion que cierra la conexion de bbdd
     * @throws SQLException
     */
    public void cerrar() throws SQLException {
        if (connection != null || !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }
}
