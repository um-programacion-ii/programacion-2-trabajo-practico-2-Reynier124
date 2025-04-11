package Class;

public class Audiolibro extends RecursoDigital{
    private String duracion;

    public Audiolibro(String estado, String titulo, String duracion) {
        super(estado, titulo);
        this.duracion = duracion;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    @Override
    public String toString() {
        return super.toString() + ", duracion=" + duracion;
    }
}
