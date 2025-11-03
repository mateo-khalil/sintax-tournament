package model;

import java.util.ArrayList;
import java.util.List;

public class Serie {
    private String nombre;
    private List<Equipo> equipos;
    private List<Partido> partidos;

    public Serie(String nombre) {
        this.nombre = nombre;
        this.equipos = new ArrayList<>();
        this.partidos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void addEquipo(Equipo equipo) {
        this.equipos.add(equipo);
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void addPartido(Partido partido) {
        this.partidos.add(partido);
    }

    public boolean tieneEquiposValidos() {
        return equipos.size() == 4;
    }

    public boolean contieneEquipo(Equipo equipo) {
        return equipos.contains(equipo);
    }

    @Override
    public String toString() {
        return String.format("Serie %s: %d equipos, %d partidos",
                nombre, equipos.size(), partidos.size());
    }
}
