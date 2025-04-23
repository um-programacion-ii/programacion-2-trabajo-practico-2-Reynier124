package Util;

import Enum.EstadoRecurso;
import Interface.RecursoVisitor;
import Recurso.Libro;
import Recurso.Revista;
import Recurso.Audiolibro;

public class RecursoSearchVisitor implements RecursoVisitor {
    private String titulo;
    private String categoria;
    private EstadoRecurso estado;
    private Integer cantPaginas;
    private String periodicidad;
    private String duracion;
    private boolean match;

    public RecursoSearchVisitor(String titulo, String categoria, EstadoRecurso estado, Integer cantPaginas, String periodicidad, String duracion) {
        this.titulo = titulo;
        this.categoria = categoria;
        this.estado = estado;
        this.cantPaginas = cantPaginas;
        this.periodicidad = periodicidad;
        this.duracion = duracion;
        this.match = false;
    }
    public boolean isMatch() {
        return match;
    }
    @Override
    public void visit(Libro libro) {
        match = (titulo == null || titulo.isEmpty() || libro.getTitulo().equalsIgnoreCase(titulo)) &&
                (categoria == null || categoria.isEmpty() || libro.getCategoria().toString().equalsIgnoreCase(categoria)) &&
                (estado == null || libro.getEstado().equals(estado)) &&
                (cantPaginas == null || libro.getCant_paginas() == cantPaginas);
    }

    @Override
    public void visit(Revista revista) {
        match = (titulo == null || titulo.isEmpty() || revista.getTitulo().equalsIgnoreCase(titulo)) &&
                (categoria == null || categoria.isEmpty() || revista.getCategoria().toString().equalsIgnoreCase(categoria)) &&
                (estado == null || revista.getEstado().equals(estado)) &&
                (periodicidad == null || periodicidad.isEmpty() || revista.getPeriodicidad().toString().equalsIgnoreCase(periodicidad));
    }

    @Override
    public void visit(Audiolibro audiolibro) {
        match = (titulo == null || titulo.isEmpty() || audiolibro.getTitulo().equalsIgnoreCase(titulo)) &&
                (categoria == null || categoria.isEmpty() || audiolibro.getCategoria().toString().equalsIgnoreCase(categoria)) &&
                (estado == null || audiolibro.getEstado().equals(estado)) &&
                (duracion == null || duracion.isEmpty() || audiolibro.getDuracion().equalsIgnoreCase(duracion));
    }
}
