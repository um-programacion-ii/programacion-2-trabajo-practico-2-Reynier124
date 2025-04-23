package Comparadores;

import Recurso.RecursoBase;

import java.util.Comparator;

public class ComparadorConteoRecursoReserva implements Comparator<RecursoBase> {
    @Override
    public int compare(RecursoBase o1, RecursoBase o2) {
        return Integer.compare(o2.getConteoReservas(), o1.getConteoReservas());
    }
}
