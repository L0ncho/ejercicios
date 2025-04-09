package exp1_s5_alonso_basualdo_ejercicios;

import java.util.*; // importa todas las librerias, pero en el ejercicio final, puedes poner solo las que necesitas 

public class EXP1_S5_ALONSO_BASUALDO_EJERCICIOS {

    static int totalEntradasVendidas = 0;
    static double totalIngresos = 0.0;
    static int contadorDeEntradas = 1;

    //Variable para almacenar las entradas vendidas
    static List<Entrada> entradasVendidas = new Arraylist<>();

    static class Entrada {  // Esta clase interna sirve para crear objetos con la informacion de una entrada vendida
        // variables que cada entrada va a tener

        int numero;
        String ubicacion;
        String tipoCliente;
        double precioFinal;

        Entrada(int numero, String ubicacion, String tipoCliente, double precioFinal) {  // Constructor: Se usa para crear una nueva entrada
            this.numero = numero;     // las lineas con this, se refieren a la variable de la clase, la comparacion con el = y el nombre, es el parametro que viene del constructor 
            this.ubicacion = ubicacion;
            this.tipoCliente = tipoCliente;   // Se utiliza dentro de la clase publica, antes de la clase statica Constructor = Metodo especial que se una para crear objetos de una clase, su funcion es asignar valores iniciales a los atributos (variables) de ese objeto
            this.precioFinal = precioFinal;  // el constructor se ejecuta automaticamente cuando se usa "new", // tiene el mismo nombre que la clase, si la clase se llama Entrada o Salida el constructor se llama Entrada o salida
        }                                   // ejemplo: 

        /*      Entrada e = new Entrada (1, "VIP","Estudiante, 9000.0")
                                                    Se crea un nuevo objeto de tipo Entrada (e)
                                                    se llama al constructor (Entrada) y se pasan 4 variables
                                                    numero = 1  
                                                    ubicacion = "VIP"
                                                    tipoCliente= "Estudiante"
                                                    precioFinal= "9000.0"
         */
        // En java, todas las clases heredan una clase base llamada Object, que tiene un metodo llamado "toString"
        //Si no se escribe, se obtendran la direccion de la memoria
        //Si se escribe mal el codigo y no se coloca esto, java noa visa, si se coloca y nos equivocamos, java da error de compilcacion, lo que protege de errores sutiles
        @Override // Es una anotacion, que le dice al compilador de java " Estoy sobreescribiendo (redefiniendo) un metodo que viene de una clase padre(CAMBIENDO EL COMPORTAMIENTO DE UN METODO QUE YA EXISTE)  
        public String toString() {
            return "Numero:" + numero + ", Ubicacion:" + ubicacion + ", Tipo: " + tipoCliente + "Precio: $" + precioFinal;
        }

    }

    public static void main(String[] args) {

        // Variables locales
        String nombreTeatro = "Teatro Moro";
        int capacidadDeVenta = 100;
        double precioBase = 10000;
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n ====" + nombreTeatro + " Sistema de entradas =====");
            System.out.println("1. Venta de entradas");
            System.out.println("2. Ver promociones");
            System.out.println("3. Busqueda de entradas");
            System.out.println("4. Eliminar entradas");
            System.out.println("5.- Salir");
            System.out.println("Seleccione una opcion");
            opcion = scanner.nextInt();
            scanner.nextLine(); // limpia el buffer

            switch (opcion) {
                case 1:
                    venderEntrada(scanner, precioBase);
                    break;
                case 2:
                    mostrarPromociones();
                    break;
                case 3:
                    buscarEntradas(scanner);
                case 4:
                    eliminaEntradas(scanner);
                case 5:
                    System.out.println("Gracias por visitarnos!");
                    break;
                default:
                    System.out.println("Opcion no valida ");
                    break;
            }
        } while (opcion != 0);
        
        

    static void venderEntrada(Scanner scanner, double precioBase) {
        System.out.println("Ingrese ubicacion (VIP/Platea/General)");
        String ubicacion = scanner.nextLine().toUpperCase();

    }
}

}
