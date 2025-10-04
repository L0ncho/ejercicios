package CRUD;

import DB.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Clase que representa el dialogo para listar peliculas con filtros
 *
 * @author HP
 */
public class ListarPeliculas extends JDialog {

    private JTable tablaPeliculas;
    private JComboBox<String> comboGenero;
    private JTextField txtAnioInicio;
    private JTextField txtAnioTermino;
    private JButton botonBuscar;

    /**
     * Constructor de la clase ListarPeliculas
     *
     * @param parent El JFrame padre del dialogo
     */
    public ListarPeliculas(JFrame parent) {
        super(parent, "Listar Peliculas", true);
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        initComponents();
    }

    /**
     * Metodo para inicializar y agregar los componentes a la interfaz
     */
    private void initComponents() {
        // panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // crear jtable
        tablaPeliculas = new JTable();
        tablaPeliculas.setModel(new DefaultTableModel(null, new String[]{"Titulo", "Director", "Anio", "Duracion", "Genero"}));
        JScrollPane scrollPane = new JScrollPane(tablaPeliculas);
        panel.add(scrollPane, BorderLayout.CENTER);

        // PANEL PARA LOS FILTROS
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));

        //Crearemos el comboBox para los tipos de generos
        JLabel labelGenero = new JLabel("Genero: ");
        comboGenero = new JComboBox<>(new String[]{"Todos", "Comedia", "Drama", "Romance", "Terror", "Ciencia Ficcion", "Historia"});
        panelFiltros.add(labelGenero);
        panelFiltros.add(comboGenero);

        //Creamos los campos de texto para el rango de anios
        JLabel labelAnioInicio = new JLabel("Anio inicio: ");
        txtAnioInicio = new JTextField(5);
        JLabel labelAnioTermino = new JLabel("Anio fin: ");
        txtAnioTermino = new JTextField(5);
        panelFiltros.add(labelAnioInicio);
        panelFiltros.add(txtAnioInicio);
        panelFiltros.add(labelAnioTermino);
        panelFiltros.add(txtAnioTermino);

        // creamos el boton de buscar
        botonBuscar = new JButton("Buscar");
        panelFiltros.add(botonBuscar);
        panel.add(panelFiltros, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        // Accion listener del boton buscar
        botonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String genero = (String) comboGenero.getSelectedItem();
                String anioInicioStr = txtAnioInicio.getText();
                String anioFinStr = txtAnioTermino.getText();

                // Validar los anios
                int anioInicio = 0;
                int anioFin = 0;
                try {
                    anioInicio = Integer.parseInt(anioInicioStr);
                    anioFin = Integer.parseInt(anioFinStr);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ListarPeliculas.this, "Por favor, ingrese anios validos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Construir la consulta SQL
                String query = "SELECT titulo, director, anio, duracion, genero FROM Cartelera_1 WHERE 1=1";

                if (!genero.equals("Todos")) {
                    query += " AND genero = '" + genero + "'";
                }

                query += " AND anio >= " + anioInicio + " AND anio <= " + anioFin;

                try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query); ResultSet resultSet = preparedStatement.executeQuery()) {

                    // Crear un modelo de tabla para el JTable
                    String[] columnNames = {"Titulo", "Director", "Anio", "Duracion", "Genero"};
                    MiTableModel model = new MiTableModel(columnNames, 0);

                    // Llenar el modelo de tabla con los datos del ResultSet
                    while (resultSet.next()) {
                        String titulo = resultSet.getString("titulo");
                        String director = resultSet.getString("director");
                        int anio = resultSet.getInt("anio");
                        String duracion = resultSet.getString("duracion");
                        String generoPelicula = resultSet.getString("genero");
                        Object[] row = {titulo, director, anio, duracion, generoPelicula};
                        model.addRow(row);
                    }

                    // Asignar el modelo de tabla al JTable
                    tablaPeliculas.setModel(model);

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ListarPeliculas.this, "Error al obtener los datos de la base de datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(panelFiltros, BorderLayout.NORTH);

        add(panel, BorderLayout.CENTER);

    }

    /**
     * Clase interna para crear un modelo de tabla no editable
     */
    private class MiTableModel extends DefaultTableModel {

        public MiTableModel(Object[] columnNames, int rowCount) {
            super(columnNames, rowCount);
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return false; // esto hace que toodas las celdas no sean editables
        }
    }
}
