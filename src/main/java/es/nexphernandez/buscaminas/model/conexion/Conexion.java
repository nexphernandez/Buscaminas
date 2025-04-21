package es.nexphernandez.buscaminas.model.conexion;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String DATABASE_PATH = "src/main/resources/db/data.db";
    private static final File DATABASE_FILE = new File(DATABASE_PATH);

    /**
     * Constructor por defecto.
     */
    protected Conexion() {
        try {
            if (!DATABASE_FILE.exists() || !DATABASE_FILE.isFile()) {
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
            return DriverManager.getConnection("jdbc:sqlite:" + DATABASE_PATH);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
