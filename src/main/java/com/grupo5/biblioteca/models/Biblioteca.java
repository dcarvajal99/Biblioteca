package com.grupo5.biblioteca.models;

import com.grupo5.biblioteca.services.LibrosServicio;
import com.grupo5.biblioteca.services.UsuariosServicio;
import com.grupo5.biblioteca.exceptions.LibroNoEncontradoException;
import com.grupo5.biblioteca.exceptions.LibroYaPrestadoException;

import java.util.*;

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
            // Asegurarse de que siempre haya 5 columnas
            String[] normalizado = new String[5];
            for (int i = 0; i < 5; i++) {
                normalizado[i] = (i < fila.length) ? fila[i] : "";
            }
            String id = normalizado[0];
            String titulo = normalizado[1];
            String autor = normalizado[2];
            Boolean estado = Boolean.parseBoolean(normalizado[3]);
            String asignadoA = normalizado[4];
            libros.add(new Libro(id, titulo, autor, estado, asignadoA));
        }
    }

    private void cargarUsuariosDesdeCSV(String rutaArchivo) {
        List<String[]> datos = usuariosServicio.leerUsuariosCSV(rutaArchivo);
        boolean esPrimera = true;
        for (String[] fila : datos) {
            if (esPrimera) { esPrimera = false; continue; } // Saltar cabecera
            if (fila.length == 5) {
                // fila[0]=id, fila[1]=nombre, fila[2]=apellido, fila[3]=email, fila[4]=telefono
                Usuario usuario = new Usuario(fila[1], fila[2], fila[3], fila[4]);
                usuarios.put(usuario.getEmail(), usuario);
            }
        }
    }

    public void agregarLibro(Libro libro) {
        libros.add(libro);
    }

    public void registrarLibro(String titulo, String autor, String asignadoA) {
        // Buscar el id más alto actual
        int maxId = 0;
        for (Libro libro : libros) {
            try {
                int idLibro = Integer.parseInt(libro.getId());
                if (idLibro > maxId) maxId = idLibro;
            } catch (NumberFormatException e) {
                // Ignorar ids no numéricos
            }
        }
        String nuevoId = String.valueOf(maxId + 1);
        Boolean estadoBool = true; // Siempre disponible al registrar
        String asignadoFinal = (asignadoA != null && !asignadoA.isEmpty()) ? asignadoA : "";
        if (!asignadoFinal.isEmpty()) {
            estadoBool = false;
        }
        Libro libro = new Libro(nuevoId, titulo, autor, estadoBool, asignadoFinal);
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
        datos.add(new String[]{"id","titulo","autor","estado","asignadoA"});
        for (Libro libro : libros) {
            datos.add(new String[]{libro.getId(), libro.getTitulo(), libro.getAutor(), libro.getEstado().toString(), libro.getAsignadoA() == null ? "" : libro.getAsignadoA()});
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

        TreeSet<Libro> librosOrdenados = new TreeSet<>(Comparator.comparing(Libro::getId));
        librosOrdenados.addAll(libros);
        for (Libro libro : librosOrdenados) {
            System.out.println(libro);
        }
    }

    public void mostrarUsuarios() {

        HashSet<String> emails = new HashSet<>();
        for (Usuario usuario : usuarios.values()) {
            if (!emails.contains(usuario.getEmail())) {
                System.out.println(usuario);
                emails.add(usuario.getEmail());
            }
        }
    }
}
