
package MODELO;

import java.util.Date;

/**
 *
 * @author HP
 */
public class Venta {
    private int id_venta;
    private Date fecha_hora;
    private int id_cliente;
    private int id_equipo;
    /**
     * Constructor vaciio
     */
    public Venta() {
    }
    /**
     * Constructor compuesto de la clase
     * @param id_venta
     * @param fecha_hora
     * @param id_cliente
     * @param id_equipo 
     */
    public Venta(int id_venta, Date fecha_hora, int id_cliente, int id_equipo) {
        this.id_venta = id_venta;
        this.fecha_hora = fecha_hora;
        this.id_cliente = id_cliente;
        this.id_equipo = id_equipo;
    }
    
    /**
     * Getters y setters
     *  
     */
    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public Date getFecha_hora() {
        return fecha_hora;
    }

    public void setFecha_hora(Date fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }
    
    
    
    
}
