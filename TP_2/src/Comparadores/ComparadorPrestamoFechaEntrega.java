package Comparadores;

import Prestamo.Prestamo;

import java.util.Comparator;

public class ComparadorPrestamoFechaEntrega implements Comparator<Prestamo> {
    @Override
    public int compare(Prestamo o1, Prestamo o2) {
        return o1.getFechaEntrega().compareTo(o2.getFechaEntrega());
    }
}
