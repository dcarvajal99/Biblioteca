package com.grupo5.biblioteca;

import com.grupo5.biblioteca.models.Libro;
import com.grupo5.biblioteca.models.Usuario;
import com.grupo5.biblioteca.models.Biblioteca;
import com.grupo5.biblioteca.exceptions.LibroNoEncontradoException;
import com.grupo5.biblioteca.exceptions.LibroYaPrestadoException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author grupo5
 */
public class App {

    private static final String DATA_DIR = "src/main/java/com/grupo5/biblioteca/data/";

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        Scanner scanner = new Scanner(System.in);
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- Menú Biblioteca DUOC UC ---");
            System.out.println("1. Mostrar libros");
            System.out.println("2. Mostrar usuarios");
            System.out.println("3. Buscar libro");
            System.out.println("4. Prestar libro");
            System.out.println("5. Registrar usuario");
            System.out.println("7. Registrar libro");
            System.out.println("8. Guardar y salir");
            System.out.print("Seleccione una opción: ");
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine(); // Limpiar buffer
                switch (opcion) {
                    case 1:
                        biblioteca.mostrarLibros();
                        break;
                    case 2:
                        biblioteca.mostrarUsuarios();
                        break;
                    case 3:
                        System.out.print("Ingrese el título del libro: ");
                        String titulo = scanner.nextLine();
                        try {
                            Libro libro = biblioteca.buscarLibro(titulo);
                            System.out.println("Encontrado: " + libro);
                        } catch (LibroNoEncontradoException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 4:
                        System.out.print("Ingrese el título del libro a prestar: ");
                        String tituloPrestar = scanner.nextLine();
                        System.out.print("Ingrese el email del usuario que lo retira: ");
                        String emailUsuario = scanner.nextLine();
                        if (biblioteca.getUsuarioPorEmail(emailUsuario) == null) {
                            System.out.println("Error: El email ingresado no corresponde a un usuario registrado.");
                            break;
                        }
                        try {
                            biblioteca.prestarLibro(tituloPrestar, emailUsuario);
                        } catch (LibroNoEncontradoException | LibroYaPrestadoException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case 5:
                        String nombre, apellido, email, telefono;
                        nombre = pedirCampo(scanner, "Nombre");
                        apellido = pedirCampo(scanner, "Apellido");
                        email = pedirCampo(scanner, "Email");
                        telefono = pedirCampo(scanner, "Teléfono");
                        Usuario usuario = new Usuario(nombre, apellido, email, telefono);
                        biblioteca.registrarUsuario(usuario);
                        System.out.println("Usuario registrado correctamente.");
                        break;
                    case 7:
                        String tituloLibro, autorLibro, asignadoA;
                        tituloLibro = pedirCampo(scanner, "Título");
                        autorLibro = pedirCampo(scanner, "Autor");
                        System.out.print("Asignado a (email, dejar vacío si disponible): ");
                        asignadoA = scanner.nextLine().trim();
                        if (!asignadoA.isEmpty() && biblioteca.getUsuarioPorEmail(asignadoA) == null) {
                            System.out.println("Error: El email ingresado no corresponde a un usuario registrado. El libro se registrará como disponible.");
                            asignadoA = "";
                        }
                        biblioteca.registrarLibro(tituloLibro, autorLibro, asignadoA);
                        System.out.println("Libro registrado correctamente.");
                        break;
                    case 8:
                        biblioteca.guardarLibrosEnCSV(DATA_DIR+"libros.csv");
                        biblioteca.guardarUsuariosEnCSV(DATA_DIR+"usuarios.csv");
                        System.out.println("Datos guardados. Saliendo...");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opción no válida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Debe ingresar un número.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }

    private static String pedirCampo(Scanner scanner, String campo) {
        String valor;
        do {
            System.out.print(campo + ": ");
            valor = scanner.nextLine();
            if (valor.contains("|")) {
                System.out.println("Error: El " + campo.toLowerCase() + " no puede contener el carácter '|'.");
            }
        } while (valor.contains("|"));
        return valor;
    }
}
