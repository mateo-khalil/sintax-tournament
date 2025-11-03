package model;

/* Pronostico de un participante para un partido */
public class Pronostico {
    private int numeroPartido;
    private Equipo equipoLocal;
    private Equipo equipoVisitante;
    private int golesLocalPronosticados;
    private int golesVisitantePronosticados;
    private boolean doblesPuntos;

    public Pronostico(int numeroPartido, 
                      Equipo equipoLocal, int golesLocalPronosticados,
                      Equipo equipoVisitante, int golesVisitantePronosticados,
                      boolean doblesPuntos) {
        this.numeroPartido = numeroPartido;
        this.equipoLocal = equipoLocal;
        this.equipoVisitante = equipoVisitante;
        this.golesLocalPronosticados = golesLocalPronosticados;
        this.golesVisitantePronosticados = golesVisitantePronosticados;
        this.doblesPuntos = doblesPuntos;
    }

    public int getNumeroPartido() {
        return numeroPartido;
    }

    public Equipo getEquipoLocal() {
        return equipoLocal;
    }

    public Equipo getEquipoVisitante() {
        return equipoVisitante;
    }

    public int getGolesLocalPronosticados() {
        return golesLocalPronosticados;
    }

    public int getGolesVisitantePronosticados() {
        return golesVisitantePronosticados;
    }

    public boolean isDoblesPuntos() {
        return doblesPuntos;
    }

    public char getResultadoPronosticado() {
        if (golesLocalPronosticados > golesVisitantePronosticados) return 'L';
        if (golesLocalPronosticados < golesVisitantePronosticados) return 'V';
        return 'E';
    }

    @Override
    public String toString() {
        String marca = doblesPuntos ? "(X) " : "";
        return String.format("%s%d: %s %d - %s %d",
                marca, numeroPartido,
                equipoLocal.getNombre(), golesLocalPronosticados,
                equipoVisitante.getNombre(), golesVisitantePronosticados);
    }
}
