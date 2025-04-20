package Util;

import Comparadores.ComparadorConteoRecursoPrestamo;
import Comparadores.ComparadorConteoRecursoReserva;
import Comparadores.ComparadorConteoUsuarioActividad;
import Recurso.RecursoBase;
import Usuario.Usuario;
import Enum.Categoria;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReporteGenerator {
    private final Map<Categoria, Integer> categoriaUsoCount = new HashMap<>();



    public void generarReporteRecursosMasPrestados(List<RecursoBase> recursos) {
        List<RecursoBase> recursosOrdenados = recursos.stream()
                .sorted(new ComparadorConteoRecursoPrestamo())
                .collect(Collectors.toList());

        System.out.println("Reporte de Recursos Más Prestados:");
        for (RecursoBase recurso : recursosOrdenados) {
            System.out.println("Recurso: " + recurso.getTitulo() + ", Prestamos: " + recurso.getConteoPrestamos());
        }
    }

    public void generarReporteRecursosMasReservados(List<RecursoBase> recursos) {
        List<RecursoBase> recursosOrdenados = recursos.stream()
                .sorted(new ComparadorConteoRecursoReserva())
                .collect(Collectors.toList());

        System.out.println("Reporte de Recursos Más Reservados:");
        for (RecursoBase recurso : recursosOrdenados) {
            System.out.println("Recurso: " + recurso.getTitulo() + ", Prestamos: " + recurso.getConteoPrestamos());
        }
    }

    public void generarReporteUsuariosMasActivos(List<Usuario> usuarios) {
        List<Usuario> usuariosOrdenados = usuarios.stream()
                .sorted(new ComparadorConteoUsuarioActividad())
                .collect(Collectors.toList());

        System.out.println("Reporte de Usuarios Más Activos:");
        for (Usuario usuario : usuariosOrdenados) {
            System.out.println("Usuario: " + usuario.getNombre() + ", Actividad: " + usuario.getConteoActividad());
        }
    }

    public void generarEstadisticasUsoPorCategoria(List<RecursoBase> recursos) {
        Map<Categoria, Integer> usoPorCategoria = recursos.stream()
                .collect(Collectors.groupingBy(
                        RecursoBase::getCategoria,
                        Collectors.summingInt(recurso -> recurso.getConteoPrestamos() + recurso.getConteoReservas())
                ));

        List<Map.Entry<Categoria, Integer>> categoriasOrdenadas = usoPorCategoria.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .collect(Collectors.toList());

        System.out.println("Estadísticas de Uso por Categoría:");
        for (Map.Entry<Categoria, Integer> entry : categoriasOrdenadas) {
            System.out.println("Categoría: " + entry.getKey() + ", Uso: " + entry.getValue());
        }
    }
}

