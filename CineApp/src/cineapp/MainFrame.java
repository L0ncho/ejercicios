package cineapp;

import CRUD.AgregarPelicula;
import CRUD.ModificarPelicula;
import CRUD.EliminarPelicula;
import CRUD.ListarPeliculas;
import DB.DatabaseConnection;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.*;
import javax.swing.BoxLayout;

/**
 * Clase que representa la ventana principal de la aplicacion
 *
 * @author alons
 */
public class MainFrame extends JFrame {

    private Connection connection;
    private JLabel labelTotalPeliculas;
    private JLabel labelAnioComun;

    /**
     * Constructor de la clase, con parametros como el tamano de la ventana,
     * opcion para terminar el proceso de ejecucion del codigo al cerrar la
     * ventana
     */
    public MainFrame(Connection connection) {
        this.connection = connection;
        setTitle("Cine - Gestion de Peliculas");
        setSize(400, 300); // tamano de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // cerrar la aplicacion al cerrar la ventana
        setLocationRelativeTo(null); // centrar la ventana en la pantalla

        // crear la barra de herramientas
        JToolBar toolbar = new JToolBar();
        add(toolbar, BorderLayout.NORTH);

        // CREAR BOTONOES PAR ALA BARRA DE HERRAMIENTAS
        JButton addButton = new JButton("Agregar");
        JButton editButton = new JButton("Editar");
        JButton listarButton = new JButton("Listar Peliculas");
        JButton deleteButton = new JButton("Eliminar");

        //Anadir botones a la barra de herramientas
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(listarButton);
        toolbar.add(deleteButton);

        // Area central para mostrar estadisticas
        JPanel panelEstadisticas = new JPanel();
        panelEstadisticas.setLayout(new BoxLayout(panelEstadisticas, BoxLayout.Y_AXIS));
        labelTotalPeliculas = new JLabel("Total peliculas registradas: ", SwingConstants.LEFT);
        labelAnioComun = new JLabel("Anio de lanzamiento mas comun: ", SwingConstants.LEFT);
        panelEstadisticas.add(labelTotalPeliculas);
        panelEstadisticas.add(labelAnioComun);
        add(panelEstadisticas, BorderLayout.CENTER);
        actualizarEstadisticas(); // llamamos al metodo, para actualzar las estadisticas al iniciar la app

        /**
         * Este metodo, anade un ActionListener al boton agregrar para mostrar
         * la leyenda, agregarpelicula
         */
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarPelicula agregarPelicula = new AgregarPelicula(MainFrame.this, connection);
                //hacer visible el dialogo
                agregarPelicula.setVisible(true);
            }
        });

        /**
         * Este metodo, anade un ActionListener al boton editar para mostrar la
         * leyenda, modificarPelicula
         */
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificarPelicula modificarPelicula = new ModificarPelicula(MainFrame.this, connection);
                modificarPelicula.setVisible(true);
            }
        });

        /**
         * Este metodo, anade un ActionListener al boton eliminar para mostrar
         * la leyenda, eliminarPelicula
         */
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarPelicula eliminarPelicula = new EliminarPelicula(MainFrame.this, connection);
                eliminarPelicula.setVisible(true);
            }
        });

        /**
         * Este metodo, anade un ActionListener al boton listar para mostrar la
         * leyenda, listarPeliculas
         */
        listarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ListarPeliculas listarPeliculas = new ListarPeliculas(MainFrame.this);
                listarPeliculas.setVisible(true);
            }

        });
        // hacer visible
        setVisible(true);
    }

    /**
     * Metodo que actualiza las estadisticas de la aplicacion
     */
    private void actualizarEstadisticas() {
        try {
            int totalPeliculas = obtenerTotalPeliculas();
            int anioComun = obtenerLanzamientoComun();
            labelTotalPeliculas.setText("Total peliculas: " + totalPeliculas);
            labelAnioComun.setText("Anio mas comun visitado: " + anioComun);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this, "Error al obtener las estadisticas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * Metodo que obtiene el total de peliculas registradas en la base de datos
     *
     * @return retorna un entero, con el total de peliculas
     * @throws SQLException si ocurre un error al realizar la query
     */
    private int obtenerTotalPeliculas() throws SQLException {
        int total = 0;
        String query = "SELECT COUNT(*) FROM Cartelera_1";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                total = resultSet.getInt(1);
            }
        }
        return total;
    }

    /**
     * Metodo que obtiene el anio de lanzamiento mas comun de las peliculas
     *
     * @return retorna un entero con el anio
     * @throws SQLException si ocurre un error al realizar la query
     */
    private int obtenerLanzamientoComun() throws SQLException {
        int anio = 0;
        String query = "SELECT anio FROM Cartelera_1 GROUP BY anio ORDER BY COUNT(*) DESC LIMIT 1";
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                anio = resultSet.getInt(1);
            }
        }
        return anio;
    }

    /**
     * Metodo para realizar obtener la conexion de la base de datos
     *
     * @return
     */
    public Connection getConnection() {
        return this.connection;
    }

    /**
     * metodo para acceder desde las diferentes clases
     */
    public void actualizar() {
        this.actualizarEstadisticas();
    }
}
