package Sistema;

import Enum.NivelUrgencia;
import Gestor.GestorPrestamos;
import Gestor.GestorReservas;
import Interface.RecursoDigital;
import Observer.RecursoObserver;
import Reserva.Reservas;
import Util.Input;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SistemaDisponibilidad implements RecursoObserver {
    private final SistemaNotificaciones notificaciones;
    private final GestorPrestamos gestorPrestamos;
    private final GestorReservas gestorReservas;
    private final Input input;
    private Lock lock;

    public SistemaDisponibilidad(SistemaNotificaciones notificaciones, GestorPrestamos gestorPrestamos, GestorReservas gestorReservas) {
        this.notificaciones = notificaciones;
        this.gestorPrestamos = gestorPrestamos;
        this.gestorReservas = gestorReservas;
        this.input = new Input(new Scanner(System.in));
        this.lock = new ReentrantLock();
    }

    public void alertaDisponibilidad(RecursoDigital recurso) {
        notificaciones.notificarInfo("La reserva del recurso '" + recurso.getTitulo() + "' ahora esta disponible.");
        List<Reservas> reservas = gestorReservas.buscarReservas(null, recurso.getTitulo());
        for (Reservas res : reservas) {
            gestorReservas.getSistemaReservas().getColaReservas().add(res);
        }

    }

    public  void realizarPrestamo(Reservas reserva) {
        lock.lock();
        try {
            String decision;
            do {
                decision = input.leerTexto("¿Le gustaría al usuario " + reserva.getUsuario().getNombre() + " realizar un prestamo a este recurso? (S/N)").toLowerCase();
                switch (decision) {
                    case "s" -> gestorPrestamos.solicitarPrestamo((RecursoDigital) reserva.getRecurso(), reserva.getUsuario());
                    case "n" -> gestorReservas.eliminarReserva(reserva);
                    default -> System.out.println("Esa opcion no existe. Por favor intente de nuevo.");
                }
            } while (!decision.equals("s") && !decision.equals("n"));
        }finally {
            lock.unlock();
        }

    };

    @Override
    public void actualizar(RecursoDigital recurso) {
        alertaDisponibilidad(recurso);

    }

}
