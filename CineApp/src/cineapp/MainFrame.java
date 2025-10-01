package cineapp;

import CRUD.*;
import java.awt.BorderLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase que reprenseta la ventana principal de la aplicacion
 *
 * @author alons
 */
public class MainFrame extends JFrame {

    /**
     * Construcotr de la calase, con parametros como el tamaño de la ventana,
     * opcion para terminar el proceso de ejecucion del codigo al cerrar la
     * ventana
     */
    public MainFrame() {
        setTitle("Cine - Gestion de Peliculas");
        setSize(400, 300); // tamaño de la ventana
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

        //Añadir botones a la barra de herramientas
        toolbar.add(addButton);
        toolbar.add(editButton);
        toolbar.add(listarButton);
        toolbar.add(deleteButton);

        /**
         * Este metodo, añade un ActionListener al boton agregrar para mostrar
         * la leyenda, agregarpelicula
         */
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AgregarPelicula agregarPelicula = new AgregarPelicula(MainFrame.this);
                //hacer visible el dialogo
                agregarPelicula.setVisible(true);
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModificarPelicula modificarPelicula = new ModificarPelicula(MainFrame.this);
                modificarPelicula.setVisible(true);
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EliminarPelicula eliminarPelicula = new EliminarPelicula(MainFrame.this);
                eliminarPelicula.setVisible(true);
            }
        });

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
}
