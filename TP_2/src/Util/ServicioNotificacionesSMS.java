package Util;

import Interface.ServicioNotificaciones;

public class ServicioNotificacionesSMS implements ServicioNotificaciones {
    @Override
    public void notificar(String msg) {
        synchronized (System.out) {
            System.out.println("Notificado al SMS el siguiente mensaje -> " + msg);
        }
    }
}
