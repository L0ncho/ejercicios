
package DATA;
import java.sql.Connection;

/**
 *
 * @author HP
 */
public class mainprueba {
    public static void main(String[] args) {
        Connection connection = DatabaseConnection.getConnection();

        if (connection != null) {
            System.out.println("Conexión exitosa!");
            DatabaseConnection.closeConnection(); // Cierra la conexión después de usarla
        } else {
            System.out.println("Error al conectar a la base de datos.");
        }
    }
}