package Gestor;

import Excepciones.RecursoNoDisponibleException;
import Excepciones.UsuarioNoEncontradoException;
import Interface.ServicioNotificaciones;
import Sistema.SistemaNotificaciones;
import Usuario.Usuario;
import Comparadores.ComparadorUsuarioNombre;

import java.util.*;
import java.util.stream.Collectors;

public class GestorUsuarios extends Gestor {
    private Map<String, Usuario> usuarios;
    private final SistemaNotificaciones sistemaNotificaciones;

    public GestorUsuarios(Scanner sc, ServicioNotificaciones notificaciones, SistemaNotificaciones sistemaNotificaciones) {
        super(sc, notificaciones);
        usuarios = new LinkedHashMap<>();
        this.sistemaNotificaciones = sistemaNotificaciones;
    }

    public Map<String, Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Map<String, Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Override
    public void crear(){
        System.out.println("\n--- CREAR USUARIO ---");
        String nombre = ip.leerTexto("Nombre: ");
        try{
            if (usuarios.values().stream().anyMatch(r -> r.getNombre().equalsIgnoreCase(nombre))) {
                throw new RecursoNoDisponibleException("El usuario con el nombre '" + nombre + "' ya existe.");
            }
            String email = ip.leerTexto("Email: ");
            if (usuarios.values().stream().anyMatch(r -> r.getEmail().equalsIgnoreCase(email))) {
                throw new RecursoNoDisponibleException("El usuario con el email '" + email + "' ya existe.");
            }
            String password = ip.leerTexto("Password: ");
            Usuario nuevo = new Usuario(nombre, email, password);
            usuarios.put(nombre, nuevo);
            sistemaNotificaciones.notificarCreacionUsuario(nuevo);
        }catch (RecursoNoDisponibleException e){
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado:" + e.getMessage());
        }
    }
    @Override
    public void buscar(){
        String nombre = null;
        Integer id = null;
        String email = null;
        String password = null;

        System.out.println("\n--- BUSCAR USUARIO ---");
        System.out.println("Introduzca los criterios de búsqueda (deje en blanco si no desea filtrar por ese criterio):");

        nombre = ip.leerTexto("Nombre: ");
        id = ip.leerEnteroOpcional("ID: ");
        email = ip.leerTexto("Email: ");
        password = ip.leerTexto("Password: ");

        try {
            Map<String, Usuario> resultados = buscarUsuarios(nombre, id, email, password);
            listarUsuarios(resultados);
        } catch (UsuarioNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ocurrió un error inesperado:" + e.getMessage());
        }

    };

    public Map<String, Usuario> buscarUsuarios(String nombre, Integer id, String email, String password) throws UsuarioNoEncontradoException {
        Map<String, Usuario> resultados =  usuarios.entrySet().stream()
                .filter(entry -> (nombre == null || nombre.isEmpty() || entry.getKey().equalsIgnoreCase(nombre)) &&
                        (id == null || entry.getValue().getId() == id) &&
                        (email == null || email.isEmpty() || entry.getValue().getEmail().equalsIgnoreCase(email)) &&
                        (password == null || password.isEmpty() || entry.getValue().getPassword().equals(password)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        if (resultados.isEmpty()) {
            throw new UsuarioNoEncontradoException("No se encontró ningún usuario con el nombre: " + nombre);
        }
        return resultados;
    };

    public void listarUsuarios(Map<String, Usuario> usuarios) {
        System.out.println("\n--- LISTA DE USUARIOS ---");
        for (Usuario usuario : usuarios.values()) {
            System.out.println(usuario.toString());
        }
    };

    @Override
    public void ordenar(){
        int opcion;
        do {
            System.out.println("\n--- ORDENAR DE USUARIOS ---");
            System.out.println("1. Ordenar Nombre de forma ascendente");
            System.out.println("2. Ordenar Nombre de forma descendente");
            System.out.println("0. Salir");
            opcion = ip.leerEntero("Opcion: ");
            switch(opcion){
                case 0 -> System.out.println("Volviendo...");
                case 1 -> ordenarPorNombreAscendente();
                case 2 -> ordenarPorNombreDescendente();
                default -> System.out.println("Esa opción no existe. Intente de nuevo");
            }
        }while (opcion != 0);
    }

    public void ordenarPorNombreAscendente() {
        List<Usuario> usuariosList = new ArrayList<>(usuarios.values());
        System.out.println("prueba 1: "+usuariosList);
        Collections.sort(usuariosList, new ComparadorUsuarioNombre());
        System.out.println("prueba 2: "+usuariosList);
        pasarAlMapa(usuariosList);
        System.out.println("Se ordenó de forma ascendente de manera exitosa");
    }

    public void ordenarPorNombreDescendente() {
        List<Usuario> usuariosList = new ArrayList<>(usuarios.values());
        Collections.sort(usuariosList, Collections.reverseOrder(new ComparadorUsuarioNombre()));
        pasarAlMapa(usuariosList);
        System.out.println("Se ordenó de forma descendente de manera exitosa");
    }

    public void pasarAlMapa(List<Usuario> usuariosList) {
        Map<String , Usuario> usuariosMap = new LinkedHashMap<>();
        for (Usuario usuario : usuariosList) {
            usuariosMap.put(usuario.getNombre(), usuario);
        }
        setUsuarios(usuariosMap);
    }
}
