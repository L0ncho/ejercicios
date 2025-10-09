
package MODELO;

/**
 *
 * @author HP
 */
public class Equipo {
    private int id_equipo;
    private String modelo;
    private String tipo_cpu;
    private int almacenamiento_cantidad;
    private String almacenamiento_unidad;
    private int ram_cantidad;
    private String ram_unidad;
    private double precio;
    private String tipo_equipo;
    private String fuente_poder;
    private String factor_forma;
    private double tamano_pantalla;
    private boolean touch;
    private int puertos_usb;
    
    
    /**
     * Constructor vacio
     */
    public Equipo() {
        
    }
    /**
     * Cosntructor de la clase
     * @param id_equipo
     * @param modelo
     * @param tipo_cpu
     * @param almacenamiento_cantidad
     * @param almacenamiento_unidad
     * @param ram_cantidad
     * @param ram_unidad
     * @param precio
     * @param tipo_equipo
     * @param fuente_poder
     * @param factor_forma
     * @param tamano_pantalla
     * @param touch
     * @param puertos_usb 
     */
    public Equipo(int id_equipo, String modelo, String tipo_cpu, int almacenamiento_cantidad, String almacenamiento_unidad, int ram_cantidad, String ram_unidad, double precio, String tipo_equipo, String fuente_poder, String factor_forma, double tamano_pantalla, boolean touch, int puertos_usb) {
        this.id_equipo = id_equipo;
        this.modelo = modelo;
        this.tipo_cpu = tipo_cpu;
        this.almacenamiento_cantidad = almacenamiento_cantidad;
        this.almacenamiento_unidad = almacenamiento_unidad;
        this.ram_cantidad = ram_cantidad;
        this.ram_unidad = ram_unidad;
        this.precio = precio;
        this.tipo_equipo = tipo_equipo;
        this.fuente_poder = fuente_poder;
        this.factor_forma = factor_forma;
        this.tamano_pantalla = tamano_pantalla;
        this.touch = touch;
        this.puertos_usb = puertos_usb;
    }
    /**
     * Getters y Setters
     *  
     */
    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo_cpu() {
        return tipo_cpu;
    }

    public void setTipo_cpu(String tipo_cpu) {
        this.tipo_cpu = tipo_cpu;
    }

    public int getAlmacenamiento_cantidad() {
        return almacenamiento_cantidad;
    }

    public void setAlmacenamiento_cantidad(int almacenamiento_cantidad) {
        this.almacenamiento_cantidad = almacenamiento_cantidad;
    }

    public String getAlmacenamiento_unidad() {
        return almacenamiento_unidad;
    }

    public void setAlmacenamiento_unidad(String almacenamiento_unidad) {
        this.almacenamiento_unidad = almacenamiento_unidad;
    }

    public int getRam_cantidad() {
        return ram_cantidad;
    }

    public void setRam_cantidad(int ram_cantidad) {
        this.ram_cantidad = ram_cantidad;
    }

    public String getRam_unidad() {
        return ram_unidad;
    }

    public void setRam_unidad(String ram_unidad) {
        this.ram_unidad = ram_unidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getTipo_equipo() {
        return tipo_equipo;
    }

    public void setTipo_equipo(String tipo_equipo) {
        this.tipo_equipo = tipo_equipo;
    }

    public String getFuente_poder() {
        return fuente_poder;
    }

    public void setFuente_poder(String fuente_poder) {
        this.fuente_poder = fuente_poder;
    }

    public String getFactor_forma() {
        return factor_forma;
    }

    public void setFactor_forma(String factor_forma) {
        this.factor_forma = factor_forma;
    }

    public double getTamano_pantalla() {
        return tamano_pantalla;
    }

    public void setTamano_pantalla(double tamano_pantalla) {
        this.tamano_pantalla = tamano_pantalla;
    }

    public boolean isTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    public int getPuertos_usb() {
        return puertos_usb;
    }

    public void setPuertos_usb(int puertos_usb) {
        this.puertos_usb = puertos_usb;
    }
    
    

    
    
    
    
    
    
    
    
    
}
