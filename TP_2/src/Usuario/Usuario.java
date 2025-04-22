package Usuario;

import Util.IdGenerator;

public class Usuario {
    private String nombre;
    private final int id;
    private String email;
    private String password;
    private int conteoActividad;

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.id = IdGenerator.generateUniqueId(Usuario.class);
        this.email = email;
        this.password = password;
        this.conteoActividad = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getConteoActividad() {
        return conteoActividad;
    }

    public void reporte(){
        conteoActividad++;
    }

    @Override
    public String toString() {
        return "nombre=" + nombre +
                ", id=" + String.format("%06d", id) +
                ", email=" + email +
                ", password=" + password;
    }



}