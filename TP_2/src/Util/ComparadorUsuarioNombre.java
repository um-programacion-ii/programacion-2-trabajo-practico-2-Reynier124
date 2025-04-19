package Util;

import Usuario.Usuario;

import java.util.Comparator;

public class ComparadorUsuarioNombre implements Comparator<Usuario> {
    @Override
    public int compare(Usuario o1, Usuario o2) {
        return o1.getNombre().compareTo(o2.getNombre());
    }
}
