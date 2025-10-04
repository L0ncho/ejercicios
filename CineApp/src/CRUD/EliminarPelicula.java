package CRUD;

import DB.DatabaseConnection;
import cineapp.MainFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alons
 */
public class EliminarPelicula extends JDialog {

    private JTextField buscarField;
    private JButton buscarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    private JButton cancelarButton;
    private Connection connection;

    public EliminarPelicula(JFrame parent, Connection connection) {
        super(parent, "Eliminar Pelicula", true);
        this.connection = connection;
        setSize(400, 200);
        setLocationRelativeTo(parent);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        initComponents(gbc);
    }

    private void initComponents(GridBagConstraints gbc) {
        JLabel buscarLabel = new JLabel("Buscar titulo: ");
        buscarField = new JTextField(30); // Aumentar el tamano del campo de texto
        buscarButton = new JButton("Buscar");
        eliminarButton = new JButton("Eliminar");
        limpiarButton = new JButton("Limpiar");
        cancelarButton = new JButton("Cancelar");
        eliminarButton.setEnabled(false);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(buscarLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Expandir el campo de texto horizontalmente
        add(buscarField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE; // Restablecer el valor de fill para los siguientes componentes
        add(buscarButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(eliminarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(cancelarButton);
        add(buttonPanel, gbc);

        configurarListeners();
    }

    private void configurarListeners() {
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloBuscar = buscarField.getText().trim();

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
                String tituloEliminar = buscarField.getText().trim();

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
                            ((MainFrame) getParent()).actualizar();//Actualizar estadisticas
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
}
