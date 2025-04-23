package Comparadores;

import Prestamo.Prestamo;

import java.util.Comparator;

public class ComparadorPrestamoFechaDevolucion implements Comparator<Prestamo> {
    @Override
    public int compare(Prestamo o1, Prestamo o2) {
        return o1.getFechaDevolucion().compareTo(o2.getFechaDevolucion());
    }
}
