package Enum;

public enum Periodicidad {
    SEMANAL,
    MENSUAL,
    BIMESTRAL,
    TRIMESTRAL,
    ANUAL;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
