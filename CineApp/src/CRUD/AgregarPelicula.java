package CRUD;

import DB.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Clase que representa el dialogo para agregar una nueva pelicula a la base de
 * datos No se necesita evitar duplicidad del ID, ya que al ser
 * autoincrementable, MySQL agrega un numero automatico al agregarlo a la base
 * de datos
 */
public class AgregarPelicula extends JDialog {

    // Textos para identificar parametros
    private JTextField tituloField;
    private JTextField directorField;
    private JTextField anioField;
    private JTextField duracionField;
    private JComboBox<String> generoComboBox; // usamos JCombonox para el genero, ya que tiene varios tipos

    /**
     * Constructor de la clase
     *
     * @param parent El JFrame padre del dialogo
     */
    public AgregarPelicula(JFrame parent) {
        super(parent, "Agregar nueva pelicula", true); // el true lo hace modal

        setSize(700, 300);
        setLocationRelativeTo(parent); // CENTRAR EL DIALOGO RELATIVO AL PADRE
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Añadir un espacio de 5 pixeles alrederod de cada componente

        // crear etiquetas y campos de texto
        JLabel tituloLabel = new JLabel("Titulo: ");
        tituloField = new JTextField(20);
        JLabel directorLabel = new JLabel("Director: ");
        directorField = new JTextField(20);
        JLabel anioLabel = new JLabel("Año: ");
        anioField = new JTextField(20);
        JLabel duracionLabel = new JLabel("Duracion: ");
        duracionField = new JTextField(20);
        JLabel generoLabel = new JLabel("Genero: ");
        generoComboBox = new JComboBox<>(new String[]{"Comedia", "Drama", "Romance", "Terror", "Ciencia Ficcion", "Historia"}); // opciones de genero

        //creamos lo botones
        JButton guardarButton = new JButton("Guardar");
        JButton cancelarButton = new JButton("Cancelar");
        JButton limpiarButton = new JButton("Limpiar");

        //Añadir los componentes al diaglogo
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(tituloLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(tituloField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(directorLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(directorField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(anioLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(anioField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(duracionLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(duracionField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(generoLabel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(generoComboBox, gbc);

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        buttonPanel.add(guardarButton);
        buttonPanel.add(cancelarButton);
        buttonPanel.add(limpiarButton);
        add(buttonPanel, gbc);

        /**
         * Añade un ActionListener al boton "Guardar" para guardar la info
         * ingesada en la base de datos
         */
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = tituloField.getText();
                String director = directorField.getText();
                String anio = anioField.getText();
                String duracion = duracionField.getText();
                String genero = (String) generoComboBox.getSelectedItem();

                if (titulo.isEmpty() || director.isEmpty() || anio.isEmpty() || duracion.isEmpty()) {
                    JOptionPane.showMessageDialog(AgregarPelicula.this, "Por favor, complete todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // validar un año valido y tamaño razonable
                try {
                    int anioInt = Integer.parseInt(anio);
                    if (anioInt < 1896 || anioInt > 2025) {
                        JOptionPane.showMessageDialog(AgregarPelicula.this, "Por favor ingrese un año valido (entre 1896 y 2025)", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AgregarPelicula.this, "Por favor ingrese un numero valido para el año", "Error", JOptionPane.ERROR_MESSAGE);
                    anioField.setText("");// limipiamos el campo año debido a que si presionamos ok al error, y tratamos de agregar nuevamente, genera error
                    return;
                }

                // Conectar a la base de datos
                String query = "INSERT INTO Cartelera_1 (titulo, director, anio, duracion, genero) VALUES (?, ?, ?, ?, ?)";
                try (Connection connection = DatabaseConnection.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                    preparedStatement.setString(1, titulo);
                    preparedStatement.setString(2, director);
                    preparedStatement.setString(3, anio);
                    preparedStatement.setString(4, duracion);
                    preparedStatement.setString(5, genero);

                    // Ejecutar la consulta
                    int rowsAffected = preparedStatement.executeUpdate();

                    // Mostrar mensaje de éxito
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(AgregarPelicula.this, "Pelicula agregada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(AgregarPelicula.this, "No se pudo agregar la pelicula.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(AgregarPelicula.this, "Error al agregar la pelicula: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        

        /**
         * Añade un ActionListener al boton cancelar, para cerrar el dialogo sin
         * guardar
         */
        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // cerrar el dialogo sin guardar
                dispose();
            }
        });
        /**
         * Añadimos un ActionListener al boton limpiar para borrar el contenido
         * de todos los campos del formulario
         */
        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tituloField.setText(""); // Limpiar el campo "Título"
                directorField.setText(""); // Limpiar el campo "Director"
                anioField.setText(""); // Limpiar el campo "Año"
                duracionField.setText(""); // Limpiar el campo "Duración"
                generoComboBox.setSelectedIndex(0); // Seleccionar el primer elemento del ComboBox "Género"
            }
        });

    }
}
