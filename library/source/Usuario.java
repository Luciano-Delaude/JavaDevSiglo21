package source;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import exceptions.LibroNoDisponibleException;

public class Usuario {
    private String nombre;
    private String email;
    private List<Libro> librosAlquilados;


    public Usuario(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.librosAlquilados = new ArrayList<>();
    }

    public String getNombre(){
        return this.nombre;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void notificar(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Notificación", JOptionPane.INFORMATION_MESSAGE);
    }

    public List<Libro> getLibrosAlquilados (){
        return this.librosAlquilados;
    }
    
    public void alquilarLibro(Libro libro) {
        try {
            if (libro.estaDisponible()) {
            librosAlquilados.add(libro);
            libro.setDisponible(false);
        } else 
            throw new LibroNoDisponibleException("ERROR: El libro " + libro.getTitulo() + " no está disponible en la biblioteca");
        } catch (LibroNoDisponibleException e) {
            JOptionPane.showMessageDialog(null, e, "Libro no disponible", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    public void devolverLibro(Libro libro) {
        try {
             if (librosAlquilados.contains(libro)) {
                librosAlquilados.remove(libro);
                libro.setDisponible(true);
            } else
                throw new Exception("ERROR: El libro aún no ha sido alquilado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Libro no alquilado", JOptionPane.ERROR_MESSAGE);
        }
    } 
}
