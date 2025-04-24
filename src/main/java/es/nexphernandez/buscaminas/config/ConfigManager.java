package es.nexphernandez.buscaminas.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author nexphernandez
 * @version 1.0.0
 */

public class ConfigManager {

    public static class ConfigProperties {

        static String path;
        private static final Properties properties = new Properties();

        /**
         * Metodo estatico para obtener una propiedad
         **/
        public static String getProperty(String key) {
            return properties.getProperty(key);
        }

        public static void setPath(String rutaPath) {
            System.out.println("Dentro del setPath");
            try (InputStream input = ConfigManager.class.getResourceAsStream("/" + rutaPath)) {
                if (input == null) {
                    System.out.println("Archivo no encontrado en el classpath: " + rutaPath);
                    return;
                }
                properties.load(new InputStreamReader(input, "UTF-8"));
                System.out.println("Archivo de idioma cargado correctamente: " + rutaPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
