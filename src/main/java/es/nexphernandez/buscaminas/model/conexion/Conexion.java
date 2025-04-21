package es.nexphernandez.buscaminas.model.conexion;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String paht_bd = "src/main/resources/db/data.db";
    private static final File file_bd = new File(paht_bd);
    private Connection connection;
    /**
     * Constructor por defecto.
     */
    protected Conexion() {
        try {
            if (!file_bd.exists() || !file_bd.isFile()) {
                throw new SQLException("No existe la base de datos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crea una nueva conexion.
     * 
     * @return retorna la nueva conexion.
     */
    protected Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:" + paht_bd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
