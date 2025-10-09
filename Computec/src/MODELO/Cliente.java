
package MODELO;

/**
 * Clase que representa a un cliente en el sistema
 * @author HP
 */
public class Cliente {
    
    private int id_cliente;
    private String rut;
    private String nombre_completo;
    private String direccion;
    private String comuna;
    private String email;
    private String telefono;
    
    /**
     * Constructo vacio de la clase
     */
    public Cliente() {
    }
    
    /**
     * Constructor completo de la clase
     * @param id_cliente
     * @param rut
     * @param nombre_completo
     * @param direccion
     * @param comuna
     * @param email
     * @param telefono 
     */
    public Cliente(int id_cliente, String rut, String nombre_completo, String direccion, String comuna, String email, String telefono) {
        this.id_cliente = id_cliente;
        this.rut = rut;
        this.nombre_completo = nombre_completo;
        this.direccion = direccion;
        this.comuna = comuna;
        this.email = email;
        this.telefono = telefono;
    }
    /**
     * Getter y setters
     * @return 
     */

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String gettelefono() {
        return telefono;
    }

    public void setTelefono(String relefono) {
        this.telefono = relefono;
    }
    
    
    
    
    
    
    
    
    
    
    
}
