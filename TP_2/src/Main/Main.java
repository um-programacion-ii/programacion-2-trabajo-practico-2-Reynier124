package Main;

import Interface.ServicioNotificaciones;
import Util.ServicioNotificacionesEmail;

public class Main {
    public static void main(String[] args) {
        ServicioNotificaciones s1 = new ServicioNotificacionesEmail();
        Consola c1 = new Consola(s1, 3);
        c1.iniciar();
    }
}
