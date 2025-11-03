package validation;

import model.*;
import util.ErrorHandler;
import java.util.*;

/* Valida las restricciones de los pronosticos de los participantes */
public class PronosticoValidator {

    public static void validar(Campeonato campeonato) {
        validarMarcasDoblesPuntos(campeonato);
        validarEquiposExistentes(campeonato);
        validarNumerosPartidos(campeonato);
        validarEmailsUnicos(campeonato);
    }

    private static void validarMarcasDoblesPuntos(Campeonato campeonato) {
        for (Participante participante : campeonato.getParticipantes()) {
            int marcas = participante.contarMarcasDoblesPuntos();
            if (marcas > 4) {
                ErrorHandler.addError(
                    String.format("Participante '%s' tiene %d marcas (X) (maximo: 4)",
                        participante.getNombre(), marcas)
                );
            }
        }
    }

    private static void validarEquiposExistentes(Campeonato campeonato) {
        Set<Equipo> equiposFixture = new HashSet<>();
        for (Serie serie : campeonato.getSeries()) {
            equiposFixture.addAll(serie.getEquipos());
        }

        for (Participante participante : campeonato.getParticipantes()) {
            for (Pronostico pronostico : participante.getPronosticos()) {
                Equipo local = pronostico.getEquipoLocal();
                Equipo visitante = pronostico.getEquipoVisitante();

                if (!equiposFixture.contains(local)) {
                    ErrorHandler.addError(
                        String.format("Participante '%s': El equipo '%s' en el pronostico del partido %d no existe en el fixture",
                            participante.getNombre(), local.getNombre(), pronostico.getNumeroPartido())
                    );
                }

                if (!equiposFixture.contains(visitante)) {
                    ErrorHandler.addError(
                        String.format("Participante '%s': El equipo '%s' en el pronostico del partido %d no existe en el fixture",
                            participante.getNombre(), visitante.getNombre(), pronostico.getNumeroPartido())
                    );
                }
            }
        }
    }

    private static void validarNumerosPartidos(Campeonato campeonato) {
        for (Participante participante : campeonato.getParticipantes()) {
            for (Pronostico pronostico : participante.getPronosticos()) {
                int numeroPartido = pronostico.getNumeroPartido();
                Partido partidoFixture = campeonato.getPartidoPorNumero(numeroPartido);

                if (partidoFixture == null) {
                    ErrorHandler.addError(
                        String.format("Participante '%s': El partido %d no existe en el fixture",
                            participante.getNombre(), numeroPartido)
                    );
                    continue;
                }

                Equipo localPronostico = pronostico.getEquipoLocal();
                Equipo visitantePronostico = pronostico.getEquipoVisitante();
                Equipo localFixture = partidoFixture.getEquipoLocal();
                Equipo visitanteFixture = partidoFixture.getEquipoVisitante();

                if (!localPronostico.equals(localFixture)) {
                    ErrorHandler.addError(
                        String.format("Participante '%s': El equipo local del partido %d no coincide (pronostico: '%s', fixture: '%s')",
                            participante.getNombre(), numeroPartido,
                            localPronostico.getNombre(), localFixture.getNombre())
                    );
                }

                if (!visitantePronostico.equals(visitanteFixture)) {
                    ErrorHandler.addError(
                        String.format("Participante '%s': El equipo visitante del partido %d no coincide (pronostico: '%s', fixture: '%s')",
                            participante.getNombre(), numeroPartido,
                            visitantePronostico.getNombre(), visitanteFixture.getNombre())
                    );
                }
            }
        }
    }

    private static void validarEmailsUnicos(Campeonato campeonato) {
        Set<String> emailsVistos = new HashSet<>();

        for (Participante participante : campeonato.getParticipantes()) {
            String email = participante.getEmail();
            if (emailsVistos.contains(email)) {
                ErrorHandler.addError(
                    String.format("Email duplicado: '%s'", email)
                );
            }
            emailsVistos.add(email);
        }
    }
}
