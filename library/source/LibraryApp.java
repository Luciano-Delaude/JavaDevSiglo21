package source;

import java.util.List;

import javax.swing.JOptionPane;

import interfaces.IBiblioteca;

public class LibraryApp {
    public static void main(String[] args) {
        IBiblioteca biblioteca = new Biblioteca();

        while (true) {
            try {
                String opcion = JOptionPane.showInputDialog(
                        "Menú de la Biblioteca:\n" +
                                "1. Agregar libro a la biblioteca\n" +
                                "2. Registrar nuevo usuario\n" +
                                "3. Tomar prestado un libro\n" +
                                "4. Devolver un libro\n" +
                                "5. Salir");

                if (opcion == null || opcion.equals("5")) {
                    // Salir del programa si el usuario hace clic en Cancelar o elige salir
                    break;
                }

                switch (opcion) {
                    case "1":
                        // Agregar libro a la biblioteca
                        String titulo = JOptionPane.showInputDialog("Ingrese el título del libro:");
                        String autor = JOptionPane.showInputDialog("Ingrese el autor del libro:");
                        String genero = JOptionPane.showInputDialog("Ingrese el género del libro:");
                        Libro nuevoLibro = new Libro(titulo, autor, genero);
                        biblioteca.agregarLibro(nuevoLibro);
                        break;

                    case "2":
                        // Registrar nuevo usuario
                        String nombreUsuario = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
                        String emailUsuario = JOptionPane.showInputDialog("Ingrese el correo electrónico del usuario:");
                        Usuario nuevoUsuario = new Usuario(nombreUsuario, emailUsuario);
                        biblioteca.agregarUsuario(nuevoUsuario);
                        break;

                    case "3":
                        // Tomar prestado un libro
                        Libro libroP = null;
                        String usuarioPrestamo = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
                        String criterio = JOptionPane.showInputDialog("Ingrese titulo, autor o genero del libro a alquilar:");
                        Usuario usuarioP = biblioteca.getUsuarioByName(usuarioPrestamo);
                        List<Libro> librosDisponibles = biblioteca.buscarLibrosCatalogo(criterio);
                        if (librosDisponibles.size() > 0) {
                             StringBuilder mensaje = new StringBuilder("Libros encontrados:\n");
                            for (int i = 0; i < librosDisponibles.size(); i++) {
                                mensaje.append(i + 1).append(". ").append(librosDisponibles.get(i).getTitulo()).append("\n");
                            }
                            int opcionSeleccionada = Integer.parseInt(JOptionPane.showInputDialog(mensaje.toString() + "Seleccione el número del libro para alquilar:"));
                             if (opcionSeleccionada > 0 && opcionSeleccionada <= librosDisponibles.size()) {
                            libroP = librosDisponibles.get(opcionSeleccionada - 1);
                            } else {
                                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, seleccione un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            if (usuarioP != null && libroP != null) {
                                biblioteca.alquilarLibro(usuarioP,libroP);
                            } else {
                                JOptionPane.showMessageDialog(null, "ERROR: Datos ingresados invalidos.", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        break;

                    case "4":
                        // Devolver un libro
                        String usuarioDevolucion = JOptionPane.showInputDialog("Ingrese el nombre del usuario:");
                        String criterioD = JOptionPane.showInputDialog("Ingrese titulo, autor o genero del libro a devolver:");
                        Usuario usuarioD = biblioteca.getUsuarioByName(usuarioDevolucion);
                        if (usuarioD != null && criterioD != null) {
                            biblioteca.devolverLibro(usuarioD, criterioD);
                        } else {
                            JOptionPane.showMessageDialog(null, "ERROR: Datos ingresados invalidos.", "Datos invalidos", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida. Por favor, ingrese un número entre 1 y 5.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e, "Error inesperado", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
