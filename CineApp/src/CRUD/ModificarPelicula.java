package CRUD;

import DB.DatabaseConnection;
import UTILS.NumberDocumentFilter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import javax.swing.text.AbstractDocument;

/**
 * {@code ModificarPelicula} es un diálogo que permite al usuario buscar y modificar
 * la información de una película existente en la base de datos.
 *
 * @author alons
 */
public class ModificarPelicula extends JDialog {

    // componentes de la interfaz
    private JTextField buscarField;
    private JButton buscarButton;
    private JTextField tituloField;
    private JTextField directorField;
    private JTextField anioField;
    private JTextField duracionField;
    private JComboBox<String> generoComboBox;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton cancelarButton;

    /**
     * Crea un nuevo diálogo {@code ModificarPelicula}.
     *
     * @param parent El {@code JFrame} padre del diálogo.
     */
    public ModificarPelicula(JFrame parent) {
        super(parent, "Modificar Pelicula", true);
        setSize(700, 400);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        initComponents(gbc);
    }

    /**
     * Inicializa los componentes de la interfaz y los agrega al diálogo.
     *
     * @param gbc El {@code GridBagConstraints} que se utiliza para controlar el diseño.
     */
    public void initComponents(GridBagConstraints gbc) {

        JLabel buscarLabel = new JLabel("Buscar titulo: ");
        buscarField = new JTextField(20);
        buscarButton = new JButton("Buscar");

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buscarLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(buscarField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        add(buscarButton, gbc);

        // Componentes de informacion de la pelicula
        JLabel tituloLabel = new JLabel("Titulo: ");
        tituloField = new JTextField(20);
        JLabel directorLabel = new JLabel("Director: ");
        directorField = new JTextField(20);
        JLabel anioLabel = new JLabel("Año: ");
        anioField = new JTextField(20);
        ((AbstractDocument) anioField.getDocument()).setDocumentFilter(new NumberDocumentFilter(4));

        JLabel duracionLabel = new JLabel("Duracion: ");
        duracionField = new JTextField(20);
        ((AbstractDocument) duracionField.getDocument()).setDocumentFilter(new NumberDocumentFilter(3));

        JLabel generoLabel = new JLabel("Genero: ");
        generoComboBox = new JComboBox<>(new String[]{"Comedia", "Drama", "Romance", "Terror", "Ciencia Ficcion", "Accion", "Historia"});

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(tituloLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(tituloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(directorLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(directorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(anioLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(anioField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(duracionLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(duracionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(generoLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(generoComboBox, gbc);

        // Btones
        guardarButton = new JButton("Guardar");
        limpiarButton = new JButton("Limpiar");
        cancelarButton = new JButton("Cancelar");

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(cancelarButton);
        add(buttonPanel, gbc);

        configurarListeners();

    }

    /**
     * Configura los {@code ActionListener} para los botones del diálogo.
     */
    private void configurarListeners() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloBuscar = buscarField.getText().trim(); // trim() elimina espacios en blanco al principio y al final
                try {
                    if (tituloBuscar.isEmpty()) {
                        throw new IllegalArgumentException("Por favor, ingrese un título para buscar.");
                    }

                    // Conectar a la base de datos y buscar la película
                    String query = "SELECT titulo, director, anio, duracion, genero FROM Cartelera_1 WHERE titulo = ?";
                    try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                        preparedStatement.setString(1, tituloBuscar);
                        ResultSet resultSet = preparedStatement.executeQuery();

                        if (resultSet.next()) {
                            // Si se encuentra la película, llenar los campos
                            tituloField.setText(resultSet.getString("titulo"));
                            directorField.setText(resultSet.getString("director"));
                            Integer anio = null;
                            Integer duracion = null;
                            String anioStr = resultSet.getString("anio");
                            String duracionStr = resultSet.getString("duracion");
                            if (anioStr != null && !anioStr.isEmpty()) {
                                anio = Integer.parseInt(anioStr);
                            }
                            if (duracionStr != null && !duracionStr.isEmpty()) {
                                duracion = Integer.parseInt(duracionStr);
                            }
                            anioField.setText(String.valueOf(anio));
                            duracionField.setText(String.valueOf(duracion));
                            String genero = resultSet.getString("genero");
                            generoComboBox.setSelectedItem(genero);

                        } else {
                            // Si no se encuentra la película, mostrar un mensaje de error
                            JOptionPane.showMessageDialog(ModificarPelicula.this, "No se encontró ninguna película con ese título.", "Error", JOptionPane.ERROR_MESSAGE);
                            tituloField.setText("");
                            directorField.setText("");
                            anioField.setText("");
                            duracionField.setText("");
                            generoComboBox.setSelectedIndex(0);
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ModificarPelicula.this, "Error al buscar la película: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ModificarPelicula.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Obtener los valores de los campos
                    String titulo = tituloField.getText().trim();
                    String director = directorField.getText().trim();
                    Integer anio = null;
                    Integer duracion = null;
                     String anioStr = anioField.getText();
                    String duracionStr = duracionField.getText();
                    if (anioStr != null && !anioStr.isEmpty()) {
                        anio = Integer.parseInt(anioStr);
                    }
                    if (duracionStr != null && !duracionStr.isEmpty()) {
                        duracion = Integer.parseInt(duracionStr);
                    }
                    String genero = (String) generoComboBox.getSelectedItem();

                    // Validar que los campos no estén vacíos
                    if (titulo == null || titulo.isEmpty() || director == null || director.isEmpty() || anio == null || duracion == null) {
                        throw new IllegalArgumentException("Todos los campos son obligatorios.");
                    }

                    // Validar que el año esté dentro de un rango razonable
                    if (anio < 1895 || anio > Calendar.getInstance().get(Calendar.YEAR)) {
                        throw new IllegalArgumentException("El año debe estar entre 1895 y " + Calendar.getInstance().get(Calendar.YEAR));
                    }

                    // Conectar a la base de datos y actualizar la película
                    String query = "UPDATE Cartelera_1 SET titulo = ?, director = ?, anio = ?, duracion = ?, genero = ? WHERE titulo = ?";
                    try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                        preparedStatement.setString(1, titulo);
                        preparedStatement.setString(2, director);
                        preparedStatement.setInt(3, anio);
                        preparedStatement.setInt(4, duracion);
                        preparedStatement.setString(5, genero);
                        preparedStatement.setString(6, buscarField.getText().trim()); // Usar el título original para la condición WHERE

                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(ModificarPelicula.this, "Película actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(ModificarPelicula.this, "No se pudo actualizar la película.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(ModificarPelicula.this, "Error al actualizar la película: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(ModificarPelicula.this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica de limpieza aquí
                tituloField.setText("");
                directorField.setText("");
                anioField.setText("");
                duracionField.setText("");
                generoComboBox.setSelectedIndex(0);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Cierra el diálogo
            }
        });

    }
}
