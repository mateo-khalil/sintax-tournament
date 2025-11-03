package validation;

import model.*;
import util.ErrorHandler;
import java.util.*;

/* Valida las restricciones del fixture del campeonato */
public class FixtureValidator {

    public static void validar(Campeonato campeonato) {
        validarCantidadSeries(campeonato);
        validarEquiposPorSerie(campeonato);
        validarNumerosPartidosUnicos(campeonato);
        validarFechas(campeonato);
        validarEquiposEnPartidos(campeonato);
    }

    private static void validarCantidadSeries(Campeonato campeonato) {
        int cantidad = campeonato.getSeries().size();
        if (cantidad != 4 && cantidad != 8 && cantidad != 12) {
            ErrorHandler.addError(
                String.format("El campeonato debe tener 4, 8 o 12 series (tiene %d)", cantidad)
            );
        }
    }

    private static void validarEquiposPorSerie(Campeonato campeonato) {
        for (Serie serie : campeonato.getSeries()) {
            if (!serie.tieneEquiposValidos()) {
                ErrorHandler.addError(
                    String.format("La serie '%s' debe tener exactamente 4 equipos (tiene %d)",
                        serie.getNombre(), serie.getEquipos().size())
                );
            }
        }
    }

    private static void validarNumerosPartidosUnicos(Campeonato campeonato) {
        Set<Integer> numerosVistos = new HashSet<>();
        List<Partido> todosLosPartidos = campeonato.getTodosLosPartidos();

        for (Partido partido : todosLosPartidos) {
            int numero = partido.getNumero();
            if (numerosVistos.contains(numero)) {
                ErrorHandler.addError(
                    String.format("Numero de partido duplicado: %d", numero)
                );
            }
            numerosVistos.add(numero);
        }
    }

    private static void validarFechas(Campeonato campeonato) {
        String formatoEsperado = "\\d{4}/\\d{2}/\\d{2}";
        List<Partido> todosLosPartidos = campeonato.getTodosLosPartidos();

        for (Partido partido : todosLosPartidos) {
            String fecha = partido.getFecha();
            if (!fecha.matches(formatoEsperado)) {
                ErrorHandler.addError(
                    String.format("Formato de fecha invalido en partido %d: '%s' (esperado YYYY/MM/DD)",
                        partido.getNumero(), fecha)
                );
                continue;
            }

            String[] partes = fecha.split("/");
            int anio = Integer.parseInt(partes[0]);
            int mes = Integer.parseInt(partes[1]);
            int dia = Integer.parseInt(partes[2]);

            if (mes < 1 || mes > 12) {
                ErrorHandler.addError(
                    String.format("Mes invalido en partido %d: %d (debe estar entre 1 y 12)",
                        partido.getNumero(), mes)
                );
            }

            if (dia < 1 || dia > 31) {
                ErrorHandler.addError(
                    String.format("Dia invalido en partido %d: %d (debe estar entre 1 y 31)",
                        partido.getNumero(), dia)
                );
            }

            if (anio < 1900 || anio > 2100) {
                ErrorHandler.addWarning(
                    String.format("Año inusual en partido %d: %d", partido.getNumero(), anio)
                );
            }
        }
    }

    private static void validarEquiposEnPartidos(Campeonato campeonato) {
        for (Serie serie : campeonato.getSeries()) {
            List<Equipo> equiposSerie = serie.getEquipos();
            
            for (Partido partido : serie.getPartidos()) {
                Equipo local = partido.getEquipoLocal();
                Equipo visitante = partido.getEquipoVisitante();

                if (!equiposSerie.contains(local)) {
                    ErrorHandler.addError(
                        String.format("El equipo '%s' del partido %d no pertenece a la serie '%s'",
                            local.getNombre(), partido.getNumero(), serie.getNombre())
                    );
                }

                if (!equiposSerie.contains(visitante)) {
                    ErrorHandler.addError(
                        String.format("El equipo '%s' del partido %d no pertenece a la serie '%s'",
                            visitante.getNombre(), partido.getNumero(), serie.getNombre())
                    );
                }

                if (local.equals(visitante)) {
                    ErrorHandler.addError(
                        String.format("Un equipo no puede jugar contra si mismo en el partido %d",
                            partido.getNumero())
                    );
                }
            }
        }
    }
}
