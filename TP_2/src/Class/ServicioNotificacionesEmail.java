package Class;

public class ServicioNotificacionesEmail implements ServicioNotificaciones {

    @Override
    public void notificar(String msg) {
        System.out.println("Notificado al email el siguiente mensaje: " + msg);
    }
}
