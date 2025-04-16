package Util;

import Interface.ServicioNotificaciones;
import Usuario.Usuario;

import java.util.*;
import java.util.stream.Collectors;

public class GestorUsuarios extends Gestor {
    private Map<String, Usuario> usuarios;

    public GestorUsuarios(Scanner sc, ServicioNotificaciones notificaciones) {
        super(sc, notificaciones);
        usuarios = new HashMap<>();
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public void crear() {
        System.out.println("\n--- CREAR USUARIO ---");
        String nombre = ip.leerTexto("Nombre: ");
        String email = ip.leerTexto("Email: ");
        String password = ip.leerTexto("Password: ");
        Usuario nuevo = new Usuario(nombre, email, password);
        usuarios.put(nombre, nuevo);
        notificaciones.notificar("Usuario creado: " + nuevo);
    }

    public Map<String, Usuario> busquedaNombre(String nombre) {
        return usuarios.entrySet().stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(nombre))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Usuario> busquedaId(int id) {
        return usuarios.entrySet().stream()
                .filter(entry -> entry.getValue().getId() == id)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
