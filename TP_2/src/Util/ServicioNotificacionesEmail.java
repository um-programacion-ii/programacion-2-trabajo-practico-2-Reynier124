package Util;

import Interface.ServicioNotificaciones;

public class ServicioNotificacionesEmail implements ServicioNotificaciones {

    @Override
    public void notificar(String msg) {
        synchronized (System.out) {
            System.out.println("Notificado al email el siguiente mensaje -> " + msg);
        }

    }
}
