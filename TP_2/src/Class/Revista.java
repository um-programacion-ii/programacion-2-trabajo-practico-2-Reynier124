package Class;

public class Revista extends RecursoDigital{
    private String periodicidad;

    public Revista(String estado, String titulo, String periodicidad) {
        super(estado, titulo);
        this.periodicidad = periodicidad;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    @Override
    public String toString() {
        return super.toString() + ", periocidad=" + periodicidad;
    }
}
