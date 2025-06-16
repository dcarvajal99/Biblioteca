package com.grupo5.biblioteca.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UsuariosServicio {

    public List<String[]> leerUsuariosCSV(String rutaArchivo) {
        List<String[]> datos = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] fila = linea.split("\\|");
                datos.add(fila);
            }
            System.out.println("Usuarios leídos correctamente desde el archivo CSV " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo CSV: " + e.getMessage());
        }
        return datos;
    }

    public void escribirUsuariosCSV(String rutaArchivo, List<String[]> datos) {
        try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(rutaArchivo))) {
            for (String[] fila : datos) {
                writer.write(String.join("|", fila));
                writer.newLine();
            }
            System.out.println("Usuarios escritos correctamente en formato CSV en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo CSV: " + e.getMessage());
        }
    }

}
