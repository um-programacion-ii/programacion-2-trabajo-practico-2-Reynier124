package Gestor;

import Interface.ServicioNotificaciones;
import Interface.SistemaGestion;
import Util.Input;

import java.util.Scanner;

public abstract class Gestor implements SistemaGestion {
    protected final Scanner sc;
    protected final Input ip;
    protected final ServicioNotificaciones notificaciones;

    public Gestor(Scanner sc, ServicioNotificaciones notificaciones) {
        this.sc = sc;
        this.ip = new Input(sc);
        this.notificaciones = notificaciones;
    }

    @Override
    public void crear() {

    }

    @Override
    public void buscar() {

    }

    @Override
    public void ordenar() {

    }
}
