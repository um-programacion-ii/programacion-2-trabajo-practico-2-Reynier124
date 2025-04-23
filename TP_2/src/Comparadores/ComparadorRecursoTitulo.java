package Comparadores;

import Interface.RecursoDigital;

import java.util.Comparator;

public class ComparadorRecursoTitulo implements Comparator<RecursoDigital> {
    @Override
    public int compare(RecursoDigital o1, RecursoDigital o2) {
        return o1.getTitulo().compareToIgnoreCase(o2.getTitulo());
    }
}
