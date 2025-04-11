package Main;

import Class.Usuario;
import Class.Consola;
import Class.ServicioNotificaciones;
import Class.ServicioNotificacionesEmail;

public class Main {
    public static void main(String[] args) {
        ServicioNotificaciones s1 = new ServicioNotificacionesEmail();
        Consola c1 = new Consola(s1);
        c1.iniciar();
    }
}
