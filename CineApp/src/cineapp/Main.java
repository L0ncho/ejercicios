package cineapp;

import DB.DatabaseConnection; // IMPORTAMOS LA CLASE
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Clase principal del proyecto
 *
 * @author alons
 */
public class Main {

    private static Connection connection;

    /**
     * Metodo principiapal de la aplicacion
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnection.getConnection();
            if (connection != null) {
                System.out.println("Conexion a la base de datos exitosa");
                connection.close(); // cierra la conexion despues de la prueba
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos; " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return; // sale de la aplicacion si no s epuede conectar a la base de datos
        }

        new MainFrame(connection);
    }

    public static Connection getConnection() {
        return connection;
    }

}
