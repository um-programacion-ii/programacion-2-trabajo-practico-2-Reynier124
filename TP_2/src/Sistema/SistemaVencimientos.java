package Sistema;

import Interface.Prestable;
import Interface.RecursoDigital;
import Interface.Renovable;
import Prestamo.Prestamo;
import Usuario.Usuario;
import Util.Input;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class SistemaVencimientos {
    private final BlockingQueue<Prestamo> colaPrestamos;
    private final ExecutorService procesadorVencimientos;
    private Input input;


    public SistemaVencimientos(int hilos) {
        this.colaPrestamos = new LinkedBlockingQueue<>();
        this.procesadorVencimientos = Executors.newFixedThreadPool(hilos);
        this.input = new Input(new Scanner(System.in));
        iniciarProcesamiento();
    }

    private void iniciarProcesamiento(){
        for (int i = 0; i < 5; i++) {
            procesadorVencimientos.submit(()->{
                while (true) {
                    try{
                        Prestamo prestamo = colaPrestamos.take();
                        int vencimiento = comprobacionFechaDevolucion(prestamo.getFechaDevolucion());
                        switch (vencimiento){
                            case 1 -> alertaVencimientos(prestamo);
                            case 2 -> avisoVencimiento(prestamo);
                        }
                    }catch (InterruptedException e){
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            });
        }
    }

    private int comprobacionFechaDevolucion(LocalDateTime fechaDevolucion){
        LocalDateTime now = LocalDateTime.now();
        Duration duration = Duration.between(now, fechaDevolucion);

        long daysDifference = duration.toDays();
        if (daysDifference == 1){
            return 1;
        }else if (daysDifference == 0){
            return 0;
        }else{
            return 2;
        }
    }

    private void alertaVencimientos(Prestamo prestamo){
        Prestable recurso = prestamo.getRecurso();
        Usuario usuario = prestamo.getUsuario();
        System.out.println("****************************************");
        System.out.println("ALERTA: El préstamo vence mañana.");
        System.out.println("Recurso: " + ((RecursoDigital) recurso).getTitulo());
        System.out.println("Fecha de devolución: " + recurso.getFechaDevolucion());
        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("****************************************");

        renovacionPrestamo(prestamo);
    }

    public void renovacionPrestamo(Prestamo prestamo){
        Prestable recurso = prestamo.getRecurso();
        String decision;
        do {
            decision = input.leerTexto("¿Desea renovar el préstamo? (S/N)").toLowerCase();
            switch (decision){
                case "s" -> {
                    ((Renovable) recurso).renovar();
                }
                case "n" -> {
                    System.out.println("Entonces por favor entregar el recurso a la biblioteca en el transcurso de 24 horas");
                }
                default -> System.out.println("Esa opción no existe. Intente de nuevo");
            }
        }while (!decision.equals("s") && !decision.equals("n"));
    }

    public void avisoVencimiento(Prestamo prestamo){
        Prestable recurso = prestamo.getRecurso();
        Usuario usuario = prestamo.getUsuario();
        System.out.println("****************************************");
        System.out.println("ALERTA: Ha vencido");
        System.out.println("Recurso: " + ((RecursoDigital) recurso).getTitulo());
        System.out.println("Fecha de devolución: " + recurso.getFechaDevolucion());
        System.out.println("Usuario: " + usuario.getNombre());
        System.out.println("****************************************");
    }

}
