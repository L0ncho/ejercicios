package CRUD;

import DB.DatabaseConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que representa el dialogo para eliminar una pelicula de la base de datos.
 * Permite buscar una pelicula por titulo y luego eliminarla con confirmacion.
 *
 * @author alons
 */
public class EliminarPelicula extends JDialog {

    private JTextField buscarField; // Campo de texto para ingresar el titulo de la pelicula a buscar
    private JButton buscarButton; // Boton para iniciar la busqueda de la pelicula
    private JButton eliminarButton; // Boton para eliminar la pelicula (habilitado solo despues de la busqueda)
    private JButton limpiarButton; // Boton para limpiar el campo de busqueda
    private JButton cancelarButton; // Boton para cerrar el dialogo
    private Connection connection; // Conexión a la base de datos

    /**
     * Constructor de la clase EliminarPelicula.
     *
     * @param parent El JFrame padre del dialogo.
     */
    public EliminarPelicula(JFrame parent) {
        super(parent, "Eliminar Pelicula", true); // Llama al constructor de la clase JDialog
        setSize(400, 200); // Establece el tamaño del dialogo
        setLocationRelativeTo(parent); // Centra el dialogo con respecto al padre
        setLayout(new GridBagLayout()); // Establece el layout del dialogo como GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints(); // Crea un objeto GridBagConstraints para configurar el layout
        gbc.insets = new Insets(5, 5, 5, 5); // Establece los margenes de los componentes

        try {
            connection = DatabaseConnection.getConnection(); // Obtiene la conexión a la base de datos
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar a la base de datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            dispose(); // cerramos el dialofo si no se puede conectar a la base de datos
            return;
        }

        initComponents(gbc); // Inicializa los componentes de la interfaz
    }

    /**
     * Inicializa los componentes de la interfaz y los agrega al dialogo.
     *
     * @param gbc El objeto GridBagConstraints para configurar el layout.
     */
    private void initComponents(GridBagConstraints gbc) {
        JLabel buscaJLabel = new JLabel("Buscar titulo: "); // Etiqueta para el campo de busqueda
        buscarField = new JTextField(40); // Campo de texto para ingresar el titulo de la pelicula a buscar
        buscarButton = new JButton("Buscar"); // Boton para iniciar la busqueda de la pelicula
        eliminarButton = new JButton("Eliminar"); // Boton para eliminar la pelicula
        limpiarButton = new JButton("Limpiar"); // Boton para limpiar el campo de busqueda
        cancelarButton = new JButton("Cancelar"); // Boton para cerrar el dialogo
        eliminarButton.setEnabled(false); // Deshabilita el boton "Eliminar" inicialmente

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buscaJLabel, gbc); // Agrega la etiqueta al dialogo

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // EXPANDEMOS EL CAMPO DEL TEXTO HORIZONTALEMTEN
        add(buscarField, gbc); // Agrega el campo de texto al dialogo

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE; // restablecemos el valor de fill para los siguientes componentes
        add(buscarButton, gbc); // Agrega el boton "Buscar" al dialogo

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Crea un panel para los botones
        buttonPanel.add(eliminarButton); // Agrega el boton "Eliminar" al panel
        buttonPanel.add(limpiarButton); // Agrega el boton "Limpiar" al panel
        buttonPanel.add(cancelarButton); // Agrega el boton "Cancelar" al panel
        add(buttonPanel, gbc); // Agrega el panel al dialogo

        configurarListeners(); // Configura los listeners de los botones
    }

    /**
     * Configura los listeners de los botones.
     */
    private void configurarListeners() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloBuscar = buscarField.getText().trim(); // Obtiene el titulo de la pelicula a buscar

                if (tituloBuscar.isEmpty()) {
                    JOptionPane.showMessageDialog(EliminarPelicula.this, "Favor ingrese un titulo a buscar: ", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // conectar a la base de datos y buscar la pelicula
                String query = "SELECT titulo FROM Cartelera_1 WHERE titulo = ?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Usar la conexión existente

                    preparedStatement.setString(1, tituloBuscar);
                    ResultSet resultSet = preparedStatement.executeQuery();

                    if (resultSet.next()) {
                        // si s encuenta la pelicual, habilkitar el boton eliminar
                        eliminarButton.setEnabled(true);
                    } else {
                        JOptionPane.showMessageDialog(EliminarPelicula.this, "No se encontro ninguna pelicula con el titulo asociado", "Error", JOptionPane.ERROR_MESSAGE);
                        eliminarButton.setEnabled(false);
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(EliminarPelicula.this, "Error al buscar la pelicula: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloEliminar = buscarField.getText().trim(); // Obtiene el titulo de la pelicula a eliminar

                // Mostrar un cuadro de dialogo de confirmacion
                int opcion = JOptionPane.showConfirmDialog(EliminarPelicula.this, "Esta seguro de que desea eliminar la pelicula \"" + tituloEliminar + "\"?", "Confirmar eliminacion", JOptionPane.YES_NO_OPTION);

                if (opcion == JOptionPane.YES_OPTION) {
                    // Eliminar la pelicula de la base de datos
                    String query = "DELETE FROM Cartelera_1 WHERE titulo = ?";
                    try (PreparedStatement preparedStatement = connection.prepareStatement(query)) { // Usar la conexión existente

                        preparedStatement.setString(1, tituloEliminar);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            JOptionPane.showMessageDialog(EliminarPelicula.this, "Pelicula eliminada correctamente.", "Exito", JOptionPane.INFORMATION_MESSAGE);
                            buscarField.setText(""); // Limpiar el campo de busqueda despues de eliminar
                            eliminarButton.setEnabled(false); // Deshabilitar el boton "Eliminar" despues de eliminar
                        } else {
                            JOptionPane.showMessageDialog(EliminarPelicula.this, "No se pudo eliminar la pelicula.", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(EliminarPelicula.this, "Error al eliminar la pelicula: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        limpiarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logica para limpiar los campos
                buscarField.setText("");
                eliminarButton.setEnabled(false);
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logica para cerrar el dialogo
                dispose();
            }
        });
    }

    /**
     * Cierra la conexión a la base de datos al cerrar el dialogo.
     */
    @Override
    public void dispose() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error el cerrar la conexion: " + e.getMessage());
        } finally {
            super.dispose();
        }
    }
}
