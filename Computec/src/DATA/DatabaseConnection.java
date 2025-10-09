import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
/**
 * Clase para gestionar la conexion a la base de datos y realizar operaciones CRUD
 * @author HP
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/computec";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private static Connection connection;
    
    private PreparedStatement insertClienteStmt;
    private PreparedStatement getClienteStmt;
    private PreparedStatement updateClienteStmt;
    private PreparedStatement deleteClienteStmt;
    /**
     * Constructor de la calse, perpara las consultas SQL para CRUD
     */
    public DatabaseConnection() {
        try {
            connection = getConnection();
            String insertClienteSQL = "INSERT INTO Clientes (rut, nombre_completo, direccion, comuna, correo_electronico, telefono) VALUES (?, ?, ?, ?, ?, ?)";
            insertClienteStmt = connection.prepareStatement(insertClienteSQL);
            String getClienteSQL = "SELECT rut, nombre_completo, direccion, comuna, correo_electronico, telefono FROM Clientes WHERE id_cliente = ?";
            getClienteStmt = connection.prepareStatement(getClienteSQL);
            String updateClienteSQL = "UPDATE Clientes SET rut = ?, nombre_completo = ?, direccion = ?, comuna = ?, correo_electronico = ?, telefono = ? WHERE id_cliente = ?";
            updateClienteStmt = connection.prepareStatement(updateClienteSQL);
            String deleteClienteSQL = "DELETE FROM Clientes WHERE id_cliente = ?";
            deleteClienteStmt = connection.prepareStatement(deleteClienteSQL);
        } catch (SQLException e) {
            System.out.println("Error al preparar las consultas: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                //Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexion exitosa a la base de datos!");
            } catch ( SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexion cerrada.");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexion: " + e.getMessage());
            }
        }
    }

    /**
     * Inserta un nuevo cliente en la base de datos.
     * @param cliente El objeto Cliente a insertar.
     * @return true si la insercion fue exitosa, false si no lo fue
     */
    public boolean insertCliente(MODELO.Cliente cliente) {
        try {
            insertClienteStmt.setString(1, cliente.getRut());
            insertClienteStmt.setString(2, cliente.getNombre_completo());
            insertClienteStmt.setString(3, cliente.getDireccion());
            insertClienteStmt.setString(4, cliente.getComuna());
            insertClienteStmt.setString(5, cliente.getEmail());
            insertClienteStmt.setString(6, cliente.gettelefono());
            insertClienteStmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene un cliente por su ID.
     * @param idCliente El ID del cliente a buscar.
     * @return Un objeto Cliente con los datos del cliente correspondiente al ID, o null si no se encuentra.
     */
    public MODELO.Cliente getCliente(int idCliente) {
        MODELO.Cliente cliente = null;
        try {
            getClienteStmt.setInt(1, idCliente);
            ResultSet rs = getClienteStmt.executeQuery();
            if (rs.next()) {
                cliente = new MODELO.Cliente();
                cliente.setId_cliente(idCliente);
                cliente.setRut(rs.getString("rut"));
                cliente.setNombre_completo(rs.getString("nombre_completo"));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setComuna(rs.getString("comuna"));
                cliente.setEmail(rs.getString("correo_electronico"));
                cliente.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cliente: " + e.getMessage());
        }
        return cliente;
    }

    /**
     * Actualiza la informacion de un cliente existente en la base de datos.
     * @param cliente El objeto Cliente con la informacion actualizada.
     * @return true si la actualizacion fue exitosa, false si no lo fue.
     */
    public boolean updateCliente(MODELO.Cliente cliente) {
        try {
            updateClienteStmt.setString(1, cliente.getRut());
            updateClienteStmt.setString(2, cliente.getNombre_completo());
            updateClienteStmt.setString(3, cliente.getDireccion());
            updateClienteStmt.setString(4, cliente.getComuna());
            updateClienteStmt.setString(5, cliente.getEmail());
            updateClienteStmt.setString(6, cliente.gettelefono());
            updateClienteStmt.setInt(7, cliente.getId_cliente());
            int rowsAffected = updateClienteStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un cliente de la base de datos.
     * @param idCliente El ID del cliente a eliminar.
     * @return true si la eliminacion fue exitosa, false si no lo fue.
     */
    public boolean deleteCliente(int idCliente) {
        try {
            deleteClienteStmt.setInt(1, idCliente);
            int rowsAffected = deleteClienteStmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }
}
