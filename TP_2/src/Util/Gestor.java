package Util;

import Interface.ServicioNotificaciones;

import java.util.Scanner;

public abstract class Gestor {
    protected final Scanner sc;
    protected final Input ip;
    protected final ServicioNotificaciones notificaciones;

    public Gestor(Scanner sc, ServicioNotificaciones notificaciones) {
        this.sc = sc;
        this.ip = new Input(sc);
        this.notificaciones = notificaciones;
    }

}
