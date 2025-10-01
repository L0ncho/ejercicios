package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase que proporciona la conexion a la base de datos
 *
 * @author alons
 */
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/Cine_DB"; // ruta de mi servidor

    private static final String DB_USER = "root"; // usuario de mi servidor

    private static final String DB_PASS = "root"; // clave de mi servidor

    /**
     * Obtiene la conexion a la bvase de datos
     *
     * @return retonra la conexion realizada
     * @throws SQLException Por si ocurre algun error al intentar conectarse a
     * la base de datos
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            System.out.println("Conexion exitosa a la base de datos");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontro el driver JDBC");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
            throw e; // re-lanza la excepcion para que se maneje en otro lugar
        }finally {
            if (connection != null){
                // si la conexion no se pudo establecer completamente, cierrala
                try{
                    
                }catch (Exception ex){
                    System.out.println("Error al cerrar la conexion: "+ ex.getMessage());
                }
            }
        }
        return connection;
    }

}
