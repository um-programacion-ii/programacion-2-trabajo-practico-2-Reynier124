package Class;

public class Libro extends RecursoDigital{
    private int cant_paginas;

    public Libro(String estado, String titulo, int cant_paginas) {
        super(estado, titulo);
        this.cant_paginas = cant_paginas;
    }

    public int getCant_paginas() {
        return cant_paginas;
    }

    public void setCant_paginas(int cant_paginas) {
        this.cant_paginas = cant_paginas;
    }

    @Override
    public String toString() {
        return super.toString() + ", cant_paginas=" + cant_paginas;
    }
}
