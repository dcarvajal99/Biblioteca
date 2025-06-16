package com.grupo5.biblioteca.services;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

public class LibrosServicio {

    public void escribirLibrosCSV(String rutaArchivo, java.util.List<String[]> datos) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            for (String[] fila : datos) {
                writer.write(String.join("|", fila));
                writer.newLine();
            }
            System.out.println("Libros escritos correctamente en formato CSV en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

    public java.util.List<String[]> leerLibrosCSV(String rutaArchivo) {
        java.util.List<String[]> datos = new java.util.ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] fila = linea.split("\\|");
                datos.add(fila);
            }
            System.out.println("Libros leídos correctamente desde el archivo CSV " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return datos;
    }

}
