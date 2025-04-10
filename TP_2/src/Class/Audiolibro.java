package Class;

public class Audiolibro extends RecursoDigital{
    private String duracion;

    public Audiolibro(int id, String estado, String titulo) {
        super(id, estado, titulo);
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
