package com.grupo5.biblioteca.models;

public class Libro {
    private String titulo;
    private String autor;
    private Boolean estado;
    private String asignadoA; // email del usuario al que se asigna el libro

    public Libro(String titulo, String autor, Boolean estado) {
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
        this.asignadoA = null;
    }

    public Libro(String titulo, String autor, Boolean estado, String asignadoA) {
        this.titulo = titulo;
        this.autor = autor;
        this.estado = estado;
        this.asignadoA = asignadoA;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getAsignadoA() {
        return asignadoA;
    }

    public void setAsignadoA(String asignadoA) {
        this.asignadoA = asignadoA;
    }

    @Override
    public String toString() {
        String estadoStr = estado ? "Disponible" : "Prestado a: " + (asignadoA != null ? asignadoA : "-");
        return "Título: " + titulo + ", Autor: " + autor + ", Estado: " + estadoStr;
    }
}
