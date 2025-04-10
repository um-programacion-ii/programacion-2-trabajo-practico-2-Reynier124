package Class;

public class Revista extends Audiolibro{
    private String periodicidad;

    public Revista(int id, String estado, String titulo) {
        super(id, estado, titulo);
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
