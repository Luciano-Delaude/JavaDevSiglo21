package com.userpackage;

import java.util.ArrayList;

import javax.swing.JOptionPane;

/**
 * usersApp
 */
/**
 * usersApp
 */
public class usersApp {

    static class Persona {
        String nombre;
        String apellido;
        String dni;
        String fechaNacimiento;

        Persona(String nombre, String apellido, String dni, String fechaNacimiento) {
            this.nombre = nombre;
            this.apellido = apellido;
            this.dni = dni;
            this.fechaNacimiento = fechaNacimiento;
        }
    }

    static ArrayList<Persona> personas = new ArrayList<>();

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
        }
    }

    static void mostrarMenu() {
        String[] opciones = {"Registrar Persona", "Listar Personas", "Salir"};
        int seleccion = JOptionPane.showOptionDialog(null, "Seleccione una opción", "Registro de Personas",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, opciones, opciones[0]);

        switch (seleccion) {
            case 0:
                registrarPersona();
                break;
            case 1:
                listarPersonas();
                break;
            case 2:
                System.exit(0);
                break;
        }
    }

    static void registrarPersona() {
        String nombre = JOptionPane.showInputDialog("Ingrese el nombre:");
        String apellido = JOptionPane.showInputDialog("Ingrese el apellido:");
        String dni = JOptionPane.showInputDialog("Ingrese el DNI:");
        String fechaNacimiento = JOptionPane.showInputDialog("Ingrese la fecha de nacimiento (dd/mm/yyyy):");

        if (nombre.isEmpty() || apellido.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Nombre y Apellido no pueden estar en blanco.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarDni(dni)) {
            JOptionPane.showMessageDialog(null, "DNI inválido. Debe tener 8 dígitos numéricos.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!validarFecha(fechaNacimiento)) {
            JOptionPane.showMessageDialog(null, "Fecha de Nacimiento inválida. Utilice el formato dd/mm/yyyy.", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Persona persona = new Persona(nombre, apellido, dni, fechaNacimiento);
        personas.add(persona);

        JOptionPane.showMessageDialog(null, "Persona registrada correctamente.", "Éxito",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static void listarPersonas() {
        if (personas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay personas registradas.", "Lista de Personas",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder lista = new StringBuilder();
        for (Persona persona : personas) {
            lista.append(String.format("Nombre: %s %s, DNI: %s, Nacimiento: %s%n", persona.nombre, persona.apellido,
                    persona.dni, persona.fechaNacimiento));
        }

        JOptionPane.showMessageDialog(null, lista.toString(), "Lista de Personas", JOptionPane.INFORMATION_MESSAGE);
    }

    static boolean validarDni(String dni) {
        return dni.matches("\\d{8}");
    }

    static boolean validarFecha(String fecha) {
        return fecha.matches("\\d{2}/\\d{2}/\\d{4}");
    }
}
