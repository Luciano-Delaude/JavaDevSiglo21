package source;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import exceptions.LibroInexistenteException;
import exceptions.LibroNoDisponibleException;
import interfaces.IBiblioteca;

public class Biblioteca implements IBiblioteca {
    private List<Libro> catalogo;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.catalogo = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    private boolean isValid(Libro libro) {
        return isValidTitle(libro.getTitulo()) && 
               isValidAuthor(libro.getAutor()) &&
               isValidGender(libro.getGenero());
    }
    
    private boolean isValidTitle(String title) {
        return title != null && !title.isEmpty();
    }

    private boolean isValidGender(String gender) {
        return gender != null && !gender.isEmpty();
    }
    
    private boolean isValidAuthor(String author) {
        return author != null && !author.isEmpty();
    }

    @Override
    public void agregarLibro(Libro libro) {
        try {
            if (isValid(libro)) 
                catalogo.add(libro);
            else
                throw new LibroNoDisponibleException("ERROR: Los datos del libro son invalidos:\n" +
                "Titulo: " + libro.getTitulo() + "\n" +
                "Autor: " + libro.getAutor() + "\n" +
                "Genero: " + libro.getGenero() + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Datos invalidos", JOptionPane.ERROR_MESSAGE);
        }
    } 
    @Override
    public void eliminarLibro(Libro libro) {
        try {
            if (catalogo.contains(libro)) {
                catalogo.remove(libro);
            } else {
                throw new LibroInexistenteException("ERROR: El libro " + libro.getTitulo() + " no existe en el catalago");
            }
        } catch (LibroInexistenteException e) {
            JOptionPane.showMessageDialog(null, e, "Libro inexistente", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public boolean estaLibroDisponible(Libro libro)  {
        try {
            if (catalogo.contains(libro)) {
                return libro.estaDisponible();
            } else{
                throw new LibroNoDisponibleException("ERROR: El libro '" + libro.getTitulo() + "' no está disponible.");
            }
        } catch (LibroNoDisponibleException e) {
            JOptionPane.showMessageDialog(null, e, "Libro no disponible", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    @Override
    public List<Libro> buscarLibrosCatalogo(String criterio) {
        List<Libro> librosCoincidentes = new ArrayList<>();
        try {
            for (Libro libro : catalogo) {
                
                if (libro.getTitulo().toLowerCase().contains(criterio.toLowerCase()) ||
                libro.getAutor().toLowerCase().contains(criterio.toLowerCase()) ||
                libro.getGenero().toLowerCase().contains(criterio.toLowerCase())) {
                    librosCoincidentes.add(libro);
                } 
            }
    
            if (librosCoincidentes.isEmpty()) {
                throw new Exception("ERROR: Criterio inexistente en la biblioteca");
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Criterio incoincidente", JOptionPane.ERROR_MESSAGE);
        }
        return librosCoincidentes;
    }    

    @Override
    public List<Libro> buscarLibrosUsuario(Usuario usuario, String criterio) {
        List<Libro> librosCoincidentes = new ArrayList<>();
        try {
            for (Libro libro : usuario.getLibrosAlquilados()) {
                
                if (libro.getTitulo().toLowerCase().contains(criterio.toLowerCase()) ||
                libro.getAutor().toLowerCase().contains(criterio.toLowerCase()) ||
                libro.getGenero().toLowerCase().contains(criterio.toLowerCase())) {
                    librosCoincidentes.add(libro);
                } 
            }
    
            if (librosCoincidentes.isEmpty()) {
                throw new Exception("ERROR: Libro no alquilado pro el usuario");
            }
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Criterio incoincidente", JOptionPane.ERROR_MESSAGE);
        }
        return librosCoincidentes;
    }    

    @Override
    public void agregarUsuario (Usuario usuario){
        try {
            if (!usuarios.contains(usuario)) {
                usuarios.add(usuario);
                usuario.notificar("Usuario " + usuario.getNombre() + " añadido satisfactoriamente");
            } else {
                throw new Exception("ERROR: Usuario " + usuario.getNombre()+ " ya existente");
            }
        } catch (Exception e){
                JOptionPane.showMessageDialog(null, e, "Usuario registrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void eliminarUsuario (Usuario usuario){
        try {
            if (usuarios.contains(usuario)) {
                usuarios.remove(usuario);
                usuario.notificar("Usuario " + usuario.getNombre() + " eliminado satisfactoriamente");
            } else {
                throw new Exception("ERROR: Usuario " +usuario.getNombre() + " inexistente");
            }
        } catch (Exception e){
                JOptionPane.showMessageDialog(null, e, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void alquilarLibro (Usuario usuario, Libro libro){
        try {
            if (usuarios.contains(usuario)) {
                if (catalogo.contains(libro)) {
                    usuario.alquilarLibro(libro);
                    usuario.notificar("Usuario " + usuario.getNombre() + " alquiló el libro "+ libro.getTitulo());
                    this.eliminarLibro(libro);
                    JOptionPane.showMessageDialog(null, "Libro prestado exitosamente.");
                } else {
                    throw new LibroInexistenteException("ERROR: El libro " + libro.getTitulo() + " no existe en el catalago");
                }
            } else {
                throw new Exception("ERROR: El usuario " + usuario.getNombre() + " no existe en el sistema");
            }
        } catch (LibroInexistenteException e) {
            JOptionPane.showMessageDialog(null, e, "Libro inexistente", JOptionPane.ERROR_MESSAGE);
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void devolverLibro (Usuario usuario, String criterio){
        Libro libroP = null;
        try {
            if (usuarios.contains(usuario)) {
                List<Libro> librosUsuario = buscarLibrosUsuario(usuario, criterio);
                if (librosUsuario.size() > 0) {
                    StringBuilder mensaje = new StringBuilder("Libros encontrados:\n");
                   for (int i = 0; i < librosUsuario.size(); i++) {
                       mensaje.append(i + 1).append(". ").append(librosUsuario.get(i).getTitulo()).append("\n");
                   }
                   int opcionSeleccionada = Integer.parseInt(JOptionPane.showInputDialog(mensaje.toString() + "Seleccione el número del libro para alquilar:"));
                    if (opcionSeleccionada > 0 && opcionSeleccionada <= librosUsuario.size()) {
                   libroP = librosUsuario.get(opcionSeleccionada - 1);
                   } else {
                       JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, seleccione un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                   }
                   if (usuario != null && libroP != null) {
                        usuario.devolverLibro(libroP);
                        usuario.notificar("Usuario " + usuario.getNombre() + " devolvió el libro "+ libroP.getTitulo());
                        this.agregarLibro(libroP);
                        JOptionPane.showMessageDialog(null, "Libro devuelto exitosamente.");
                   } else {
                       JOptionPane.showMessageDialog(null, "ERROR: Datos ingresados invalidos.", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
                   }
                }
            } else {
                throw new Exception("ERROR: El usuario " + usuario.getNombre() + " no existe en el sistema");
            }
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, e, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public Usuario getUsuarioByName(String name) {
        try {
            for (Usuario usuario : usuarios) {
                if (usuario.getNombre().equalsIgnoreCase(name)) {
                    return usuario;
                } else {
                    throw new Exception("ERROR: El usuario " + usuario.getNombre() + " no existe en el sistema");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Usuario no registrado", JOptionPane.ERROR_MESSAGE);
        }
        
        return null; 
    }
}
