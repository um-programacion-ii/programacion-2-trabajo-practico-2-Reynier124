package Util;

import Interface.RecursoDigital;
import Reserva.Reservas;
import Usuario.Usuario;
import Enum.EstadoRecurso;

import java.util.concurrent.PriorityBlockingQueue;

public class SistemaReservas {
    private PriorityBlockingQueue<Reservas> colaReservas;

    public SistemaReservas(PriorityBlockingQueue<Reservas> colaReservas) {
        this.colaReservas = colaReservas;
    }

    public void agregarReserva(Usuario usuario, RecursoDigital recurso, int prioridad) {
        Reservas reserva = new Reservas(usuario, recurso, prioridad);
        colaReservas.put(reserva);
        System.out.println("Reserva agregada: " + reserva);
    }

    public void procesarReservas() {
        while (!colaReservas.isEmpty()) {
            Reservas reserva = colaReservas.poll();
            if (reserva != null) {
                RecursoDigital recurso = reserva.getRecurso();
                if (recurso.getEstado() == EstadoRecurso.DISPONIBLE) {
                    recurso.setEstado(EstadoRecurso.RESERVADO);
                    System.out.println("Reserva procesada: " + reserva);
                } else {
                    System.out.println("El recurso no está disponible para la reserva: " + reserva);
                }
            }
        }
    }
}
