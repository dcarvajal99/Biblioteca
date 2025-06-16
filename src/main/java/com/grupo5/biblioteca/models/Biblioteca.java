package com.grupo5.biblioteca.models;

import com.grupo5.biblioteca.services.LibrosServicio;
import com.grupo5.biblioteca.services.UsuariosServicio;
import com.grupo5.biblioteca.exceptions.LibroNoEncontradoException;
import com.grupo5.biblioteca.exceptions.LibroYaPrestadoException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Biblioteca {
    private ArrayList<Libro> libros = new ArrayList<>();
    private HashMap<String, Usuario> usuarios = new HashMap<>();
    private LibrosServicio librosServicio = new LibrosServicio();
    private UsuariosServicio usuariosServicio = new UsuariosServicio();

    public Biblioteca() {
        cargarLibrosDesdeCSV("src/main/java/com/grupo5/biblioteca/data/libros.csv");
        cargarUsuariosDesdeCSV("src/main/java/com/grupo5/biblioteca/data/usuarios.csv");
    }

    private void cargarLibrosDesdeCSV(String rutaArchivo) {
        List<String[]> datos = librosServicio.leerLibrosCSV(rutaArchivo);
        boolean esPrimera = true;
        for (String[] fila : datos) {
            if (esPrimera) { esPrimera = false; continue; } // Saltar cabecera
            if (fila.length >= 4) {
                String titulo = fila[0];
                String autor = fila[1];
                Boolean estado = Boolean.parseBoolean(fila[2]);
                String asignadoA = fila[3].isEmpty() ? null : fila[3];
                libros.add(new Libro(titulo, autor, estado, asignadoA));
            } else if (fila.length == 3) {
                // Para compatibilidad con datos antiguos
                String titulo = fila[0];
                String autor = fila[1];
                Boolean estado = Boolean.parseBoolean(fila[2]);
                libros.add(new Libro(titulo, autor, estado));
            }
        }
    }

    private void cargarUsuariosDesdeCSV(String rutaArchivo) {
        List<String[]> datos = usuariosServicio.leerUsuariosCSV(rutaArchivo);
        boolean esPrimera = true;
        for (String[] fila : datos) {
            if (esPrimera) { esPrimera = false; continue; } // Saltar cabecera
            if (fila.length >= 4) {
                Usuario usuario = new Usuario(fila[0], fila[1], fila[2], fila[3]);
                usuarios.put(usuario.getEmail(), usuario);
            }
        }
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.put(usuario.getEmail(), usuario);
    }

    public Libro buscarLibro(String titulo) throws LibroNoEncontradoException {
        for (Libro libro : libros) {
            if (libro.getTitulo().equalsIgnoreCase(titulo)) {
                return libro;
            }
        }
        throw new LibroNoEncontradoException("Libro no encontrado: " + titulo);
    }

    public void prestarLibro(String titulo, String emailUsuario) throws LibroNoEncontradoException, LibroYaPrestadoException {
        Libro libro = buscarLibro(titulo);
        if (!libro.getEstado()) {
            throw new LibroYaPrestadoException("El libro ya está prestado: " + titulo);
        }
        libro.setEstado(false);
        libro.setAsignadoA(emailUsuario);
        System.out.println("Libro prestado con éxito: " + titulo + " a " + emailUsuario);
    }

    public Usuario getUsuarioPorEmail(String email) {
        return usuarios.get(email);
    }

    public void guardarLibrosEnCSV(String rutaArchivo) {
        List<String[]> datos = new ArrayList<>();
        datos.add(new String[]{"titulo","autor","estado","asignadoA"});
        for (Libro libro : libros) {
            datos.add(new String[]{libro.getTitulo(), libro.getAutor(), libro.getEstado().toString(), libro.getAsignadoA() == null ? "" : libro.getAsignadoA()});
        }
        librosServicio.escribirLibrosCSV(rutaArchivo, datos);
    }

    public void guardarUsuariosEnCSV(String rutaArchivo) {
        List<String[]> datos = new ArrayList<>();
        datos.add(new String[]{"nombre","apellido","email","telefono"});
        for (Usuario usuario : usuarios.values()) {
            datos.add(new String[]{usuario.getNombre(), usuario.getApellido(), usuario.getEmail(), usuario.getTelefono()});
        }
        usuariosServicio.escribirUsuariosCSV(rutaArchivo, datos);
    }

    public void mostrarLibros() {
        for (Libro libro : libros) {
            System.out.println(libro);
        }
    }

    public void mostrarUsuarios() {
        for (Usuario usuario : usuarios.values()) {
            System.out.println(usuario);
        }
    }
}
