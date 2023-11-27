package interfaces;

import java.util.List;

import source.Libro;
import source.Usuario;

public interface IBiblioteca {
    void agregarLibro(Libro libro);
    boolean estaLibroDisponible(Libro libro);
    void eliminarLibro(Libro libro);
    List<Libro> buscarLibrosCatalogo(String criterio);
    List<Libro> buscarLibrosUsuario(Usuario usuario, String criterio);
    void agregarUsuario(Usuario usuario);
    void eliminarUsuario(Usuario usuario);
    void alquilarLibro(Usuario usuario, Libro libro);
    void devolverLibro(Usuario usuario, String criterio);
    Usuario getUsuarioByName(String name);
}
