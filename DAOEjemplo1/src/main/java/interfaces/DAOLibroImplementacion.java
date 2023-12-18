/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import Dao.Main;
import libros.Libro;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lucho
 */
public class DAOLibroImplementacion implements DAOLibro {
    
    //Establecer una instancia de la clase Main
    
    Main main = new Main();
    
    public DAOLibroImplementacion(){}
    
    @Override
    public void registrar (Libro libro){
        try{
            Connection conectar = main.establecerConeccion();
            
            PreparedStatement insertar = conectar.prepareStatement("INSERT INTO libros (titulo, autor, genero, estaDisponible) VALUES (?,?,?,?)");
            
            insertar.setString(1, libro.getTitulo());
            insertar.setString(2, libro.getAutor());
            insertar.setString(3, libro.getGenero());
            insertar.setBoolean(4, libro.estaDisponible());
            insertar.executeUpdate();
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Override
public void modificar(Libro libro) {
    try (Connection conectar = main.establecerConeccion()) {
        // Get the ID of the book based on its details
        String getIdQuery = "SELECT id FROM libros WHERE titulo = ? AND autor = ? AND genero = ? AND estaDisponible = ?";
        try (PreparedStatement getId = conectar.prepareStatement(getIdQuery)) {
            getId.setString(1, libro.getTitulo());
            getId.setString(2, libro.getAutor());
            getId.setString(3, libro.getGenero());
            getId.setBoolean(4, libro.estaDisponible());

            try (ResultSet consulta = getId.executeQuery()) {
                if (consulta.next()) {
                    // Update the book details using the obtained ID
                    int bookId = consulta.getInt("id");

                    String updateQuery = "UPDATE libros SET titulo = ?, autor = ?, genero = ?, estaDisponible = ? WHERE id = ?";
                    try (PreparedStatement modificar = conectar.prepareStatement(updateQuery)) {
                        modificar.setString(1, libro.getTitulo());
                        modificar.setString(2, libro.getAutor());
                        modificar.setString(3, libro.getGenero());
                        modificar.setBoolean(4, libro.estaDisponible());
                        modificar.setInt(5, bookId);

                        modificar.executeUpdate();
                        System.out.println("Libro con ID " + bookId + " actualizado satisfactoriamente.");
                    }
                } else {
                    System.out.println("Libro no encontrado en la base de datos.");
                }
            }
        }
    } catch (Exception e) {
        System.out.println("Error al modificar el libro: " + e);
    }
}


    @Override
    public void eliminar (Libro libro){
        try{
            Connection conectar = main.establecerConeccion();

            // Get the ID of the book based on its details
            PreparedStatement getId = conectar.prepareStatement("SELECT id FROM libros WHERE titulo = ? AND autor = ? AND genero = ? AND estaDisponible = ?");
            getId.setString(1, libro.getTitulo());
            getId.setString(2, libro.getAutor());
            getId.setString(3, libro.getGenero());
            getId.setBoolean(4, libro.estaDisponible());
            ResultSet consulta = getId.executeQuery();
            if (consulta.next()) {
                // Update the book details using the obtained ID
                int bookId = consulta.getInt("id");

                PreparedStatement eliminar = conectar.prepareStatement("DELETE FROM empleados WHERE id = ?");
                eliminar.setInt(1, bookId);
                eliminar.executeUpdate();
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    @Override
    public DefaultTableModel buscar(Libro libro) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titulo");
        model.addColumn("Autor");
        model.addColumn("Genero");
        model.addColumn("Disponible");

            // Set the table model to the JTable
        try {
            Connection conectar = main.establecerConeccion();

            // Retrieve all books from the database
            PreparedStatement getAllBooks = conectar.prepareStatement("SELECT * FROM libros");
            ResultSet resultSet = getAllBooks.executeQuery();

            while (resultSet.next()) {
                // Add each book to the table model
                int id = resultSet.getInt("id");
                String title = resultSet.getString("titulo");
                String author = resultSet.getString("autor");
                String genre = resultSet.getString("genero");
                boolean available = resultSet.getBoolean("estaDisponible");

                model.addRow(new Object[]{id, title, author, genre, available});
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return model;
    }

    
}
