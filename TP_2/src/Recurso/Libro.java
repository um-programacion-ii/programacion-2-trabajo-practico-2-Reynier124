package Recurso;

import Enum.EstadoRecurso;
import Enum.Categoria;

public class Libro extends RecursoBase {
    private int cant_paginas;

    public Libro(EstadoRecurso estado, String titulo, Categoria categoria, int cant_paginas) {
        super(estado, titulo, categoria);
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
