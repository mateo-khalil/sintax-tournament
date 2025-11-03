package util;

import model.*;
import java.util.*;

public class RankingCalculator {

    public static void calcularRanking(Campeonato campeonato) {
        for (Participante participante : campeonato.getParticipantes()) {
            int puntajeTotal = 0;

            for (Pronostico pronostico : participante.getPronosticos()) {
                Partido partido = campeonato.getPartidoPorNumero(pronostico.getNumeroPartido());

                if (partido == null) {
                    continue;
                }

                int puntaje = calcularPuntajePronostico(pronostico, partido);

                if (pronostico.isDoblesPuntos()) {
                    puntaje *= 2;
                }

                puntajeTotal += puntaje;
            }

            participante.setPuntajeTotal(puntajeTotal);
        }
    }

    private static int calcularPuntajePronostico(Pronostico pronostico, Partido partido) {
        int puntaje = 0;

        char resultadoReal = partido.getResultado();
        char resultadoPronosticado = pronostico.getResultadoPronosticado();

        if (resultadoReal == resultadoPronosticado) {
            puntaje += 2;
        }

        if (pronostico.getGolesLocalPronosticados() == partido.getGolesLocal()) {
            puntaje += 1;
        }

        if (pronostico.getGolesVisitantePronosticados() == partido.getGolesVisitante()) {
            puntaje += 1;
        }

        if (pronostico.getGolesLocalPronosticados() == partido.getGolesLocal() &&
                pronostico.getGolesVisitantePronosticados() == partido.getGolesVisitante()) {
            puntaje += 2;
        }

        return puntaje;
    }

    public static List<Participante> obtenerRanking(Campeonato campeonato) {
        List<Participante> ranking = new ArrayList<>(campeonato.getParticipantes());
        ranking.sort((p1, p2) -> Integer.compare(p2.getPuntajeTotal(), p1.getPuntajeTotal()));
        return ranking;
    }

    public static void imprimirRanking(Campeonato campeonato) {
        List<Participante> ranking = obtenerRanking(campeonato);

        System.out.println("\n--- RANKING DE PARTICIPANTES ---");

        int posicion = 1;
        for (Participante participante : ranking) {
            int marcasX = participante.contarMarcasDoblesPuntos();
            String marcasInfo = marcasX > 0 ? " (" + marcasX + "X)" : "";

            System.out.printf("%d. %s - %d puntos - %d pronosticos%s\n",
                    posicion,
                    participante.getNombre(),
                    participante.getPuntajeTotal(),
                    participante.getPronosticos().size(),
                    marcasInfo);
            posicion++;
        }
    }

    public static void imprimirDetalleParticipante(Campeonato campeonato, String nombreParticipante) {
        Participante participante = null;
        for (Participante p : campeonato.getParticipantes()) {
            if (p.getNombre().equalsIgnoreCase(nombreParticipante)) {
                participante = p;
                break;
            }
        }

        if (participante == null) {
            System.out.println("Participante no encontrado: " + nombreParticipante);
            return;
        }

        System.out.println("\n--- DETALLE: " + participante.getNombre() + " ---");

        int puntajeTotal = 0;

        for (Pronostico pronostico : participante.getPronosticos()) {
            Partido partido = campeonato.getPartidoPorNumero(pronostico.getNumeroPartido());

            if (partido == null) {
                System.out.printf("Partido %d: NO EXISTE\n", pronostico.getNumeroPartido());
                continue;
            }

            int puntaje = calcularPuntajePronostico(pronostico, partido);
            int puntajeFinal = pronostico.isDoblesPuntos() ? puntaje * 2 : puntaje;
            puntajeTotal += puntajeFinal;

            String marca = pronostico.isDoblesPuntos() ? "(X)" : "   ";

            System.out.printf("%s P%d: %s %d-%d %s | Pron: %d-%d | Pts: %d%s\n",
                    marca,
                    partido.getNumero(),
                    partido.getEquipoLocal().getNombre(),
                    partido.getGolesLocal(),
                    partido.getGolesVisitante(),
                    partido.getEquipoVisitante().getNombre(),
                    pronostico.getGolesLocalPronosticados(),
                    pronostico.getGolesVisitantePronosticados(),
                    puntajeFinal,
                    pronostico.isDoblesPuntos() ? " x2" : "");
        }

        System.out.printf("\nTOTAL: %d puntos\n", puntajeTotal);
    }

    public static void imprimirEstadisticas(Campeonato campeonato) {
        System.out.println("\n--- ESTADISTICAS ---");

        int totalPartidos = campeonato.getTodosLosPartidos().size();
        int totalParticipantes = campeonato.getParticipantes().size();
        int totalEquipos = campeonato.getTodosLosEquipos().size();

        System.out.printf("Series: %d\n", campeonato.getSeries().size());
        System.out.printf("Equipos: %d\n", totalEquipos);
        System.out.printf("Partidos: %d\n", totalPartidos);
        System.out.printf("Participantes: %d\n", totalParticipantes);

        if (!campeonato.getParticipantes().isEmpty()) {
            double puntajePromedio = campeonato.getParticipantes().stream()
                    .mapToInt(Participante::getPuntajeTotal)
                    .average()
                    .orElse(0.0);

            int puntajeMaximo = campeonato.getParticipantes().stream()
                    .mapToInt(Participante::getPuntajeTotal)
                    .max()
                    .orElse(0);

            int puntajeMinimo = campeonato.getParticipantes().stream()
                    .mapToInt(Participante::getPuntajeTotal)
                    .min()
                    .orElse(0);

            System.out.printf("Puntaje promedio: %.2f\n", puntajePromedio);
            System.out.printf("Puntaje maximo: %d\n", puntajeMaximo);
            System.out.printf("Puntaje minimo: %d\n", puntajeMinimo);
        }
    }
}
