package Comparadores;

import Usuario.Usuario;

import java.util.Comparator;

public class ComparadorConteoUsuarioActividad implements Comparator<Usuario> {
    @Override
    public int compare(Usuario o1, Usuario o2) {
        return Integer.compare(o2.getConteoActividad(), o1.getConteoActividad());
    }
}
