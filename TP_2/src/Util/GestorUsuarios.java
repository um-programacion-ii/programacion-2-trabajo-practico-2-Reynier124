package Util;

import Interface.ServicioNotificaciones;
import Usuario.Usuario;
import Util.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GestorUsuarios {
    private List<Usuario> usuarios;
    private Scanner sc;
    private Input ip;
    private final ServicioNotificaciones notificaciones;

    public GestorUsuarios(Scanner sc, ServicioNotificaciones notificaciones) {
        usuarios = new ArrayList<>();
        this.sc = sc;
        this.ip = new Input(sc);
        this.notificaciones = notificaciones;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void crearUsuario() {
        System.out.println("\n--- CREAR USUARIO ---");
        String nombre = ip.leerTexto("Nombre: ");
        String email = ip.leerTexto("Email: ");
        String password = ip.leerTexto("Password: ");
        Usuario nuevo = new Usuario(nombre, email, password);
        usuarios.add(nuevo);;
        notificaciones.notificar("Usuario creado: " + nuevo);
    }
}
