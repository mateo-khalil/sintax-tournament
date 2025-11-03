package model;

import java.util.ArrayList;
import java.util.List;

public class Participante {
    private String nombre;
    private String email;
    private List<Pronostico> pronosticos;
    private int puntajeTotal;

    public Participante(String nombre, String email) {
        this.nombre = nombre;
        this.email = email;
        this.pronosticos = new ArrayList<>();
        this.puntajeTotal = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void addPronostico(Pronostico pronostico) {
        this.pronosticos.add(pronostico);
    }

    public int getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(int puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public int contarMarcasDoblesPuntos() {
        return (int) pronosticos.stream()
                .filter(Pronostico::isDoblesPuntos)
                .count();
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d pronosticos - %d puntos",
                nombre, email, pronosticos.size(), puntajeTotal);
    }
}
