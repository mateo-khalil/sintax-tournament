package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import util.ErrorHandler;

public class Campeonato {
    private String nombre;
    private List<Serie> series;
    private List<Participante> participantes;
    private Map<Integer, Partido> partidosPorNumero;

    public Campeonato(String nombre) {
        this.nombre = nombre;
        this.series = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.partidosPorNumero = new HashMap<>();
    }

    public String getNombre() {
        return nombre;
    }

    public List<Serie> getSeries() {
        return series;
    }

    public void addSerie(Serie serie) {
        this.series.add(serie);
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void addParticipante(Participante participante) {
        this.participantes.add(participante);
    }

    public void registrarPartido(Partido partido) {
        if (partidosPorNumero.containsKey(partido.getNumero())) {
            ErrorHandler.addError(
                    String.format("Numero de partido duplicado: %d", partido.getNumero()));
            return;
        }
        partidosPorNumero.put(partido.getNumero(), partido);
    }

    public Partido getPartidoPorNumero(int numero) {
        return partidosPorNumero.get(numero);
    }

    public boolean tieneCantidadSeriesValida() {
        int cantidad = series.size();
        return cantidad == 4 || cantidad == 8 || cantidad == 12;
    }

    public List<Partido> getTodosLosPartidos() {
        return partidosPorNumero.values().stream()
                .sorted(Comparator.comparingInt(Partido::getNumero))
                .collect(Collectors.toList());
    }

    public List<Equipo> getTodosLosEquipos() {
        List<Equipo> todosLosEquipos = new ArrayList<>();
        for (Serie serie : series) {
            todosLosEquipos.addAll(serie.getEquipos());
        }
        return todosLosEquipos;
    }

}