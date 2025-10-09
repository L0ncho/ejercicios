package DATA;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/computec";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                //cargar el driver JDBC de MySQL,  usamos este comando para asegurar la compatibilidad con versiones anteriores de JDBC
                //Class.forName("com.mysql.cj.jdbc.Driver");
                //establecemos la conexion con la base de datos
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexion exitosa con la base de datos!");

            } catch ( SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }
    
    public static void closeConnection(){
        if (connection != null){
            try{
                connection.close();
                System.out.println("Conexion cerrada");
            }catch (SQLException e){
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }
}
