package model;

public class Partido {
    private int numero;
    private String fecha;
    private String estadio;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocal;
    private int golesVisitante;

    public Partido(int numero, String fecha, String estadio,
            Equipo equipoLocal, int golesLocal,
            Equipo equipoVisitante, int golesVisitante) {
        this.numero = numero;
        this.fecha = fecha;
        this.estadio = estadio;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    public int getNumero() {
        return numero;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEstadio() {
        return estadio;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public char getResultado() {
        if (golesLocal > golesVisitante)
            return 'L';
        if (golesLocal < golesVisitante)
            return 'V';
        return 'E';
    }

    @Override
    public String toString() {
        return String.format("Partido %d: %s (%s) - %s %d - %s %d",
                numero, fecha, estadio,
                equipoLocal.getNombre(), golesLocal,
                equipoVisitante.getNombre(), golesVisitante);
    }
}
