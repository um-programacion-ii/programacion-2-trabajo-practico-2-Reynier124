package Util;

import Interface.ServicioNotificaciones;
import Usuario.Usuario;

import java.util.*;

public class GestorUsuarios {
    //private List<Usuario> usuarios;
    private Map<String, Usuario> usuarios;
    private final Scanner sc;
    private final Input ip;
    private final ServicioNotificaciones notificaciones;

    public GestorUsuarios(Scanner sc, ServicioNotificaciones notificaciones) {
        usuarios = Collections.emptyMap();
        this.sc = sc;
        this.ip = new Input(sc);
        this.notificaciones = notificaciones;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void crearUsuario() {
        System.out.println("\n--- CREAR USUARIO ---");
        String nombre = ip.leerTexto("Nombre: ");
        String email = ip.leerTexto("Email: ");
        String password = ip.leerTexto("Password: ");
        Usuario nuevo = new Usuario(nombre, email, password);
        usuarios.put(nombre, nuevo);
        //usuarios.add(nuevo);;
        notificaciones.notificar("Usuario creado: " + nuevo);
    }
}
