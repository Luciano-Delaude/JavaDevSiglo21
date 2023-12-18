/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import javax.swing.table.DefaultTableModel;
import libros.Libro;

/**
 *
 * @author lucho
 */
public interface DAOLibro {
    
    public void registrar(Libro libro);
    
    public void modificar(Libro libro);
    
    public DefaultTableModel buscar(Libro libro);
    
    public void eliminar(Libro libro);
    
}
