import generated.CupScanner;
import generated.parser;
import model.*;
import util.ErrorHandler;
import util.RankingCalculator;
import util.SimpleSymbolFactory;
import validation.FixtureValidator;
import validation.PronosticoValidator;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {

    public static void main(String[] args) {
        configurarSalidaUtf8();
        System.out.println("ANALIZADOR DE PRONOSTICOS DE FUTBOL\n");

        if (args.length == 0) {
            System.out.println("Uso: java Main <archivo_entrada>");
            System.exit(1);
        }

        String archivoEntrada = args[0];
        ErrorHandler.clear();

        try {
            System.out.println("Leyendo: " + archivoEntrada);
            Campeonato campeonato = procesarArchivo(archivoEntrada);

            if (ErrorHandler.hasErrors()) {
                System.out.println("\nEl archivo contiene errores.\n");
                ErrorHandler.printErrors();
                System.exit(1);
            }

            System.out.println("Analisis completado");
            System.out.println("Validando...\n");
            
            validarCampeonato(campeonato);

            if (ErrorHandler.hasErrors()) {
                System.out.println("\nErrores de validacion.\n");
                ErrorHandler.printErrors();
                System.exit(1);
            }

            System.out.println("Validacion completada\n");
            ErrorHandler.printErrors();
            mostrarResultados(campeonato);

        } catch (Exception e) {
            System.err.println("\nError: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Campeonato procesarArchivo(String nombreArchivo) throws Exception {
        try (Reader reader = new InputStreamReader(new FileInputStream(nombreArchivo), StandardCharsets.UTF_8)) {
            CupScanner scanner = new CupScanner(reader);
            SimpleSymbolFactory symbolFactory = new SimpleSymbolFactory();
            parser parser = new parser(scanner, symbolFactory);
            parser.setScanner(scanner);

            try {
                Object resultado = parser.parse().value;

                if (resultado instanceof Campeonato) {
                    return (Campeonato) resultado;
                } else {
                    throw new Exception("El resultado del parsing no es un Campeonato valido");
                }
            } catch (Exception e) {
                throw new Exception("Error durante el analisis: " + e.getMessage(), e);
            }
        }
    }

    private static void configurarSalidaUtf8() {
        try {
            PrintStream utf8Out = new PrintStream(new FileOutputStream(FileDescriptor.out), true, "UTF-8");
            PrintStream utf8Err = new PrintStream(new FileOutputStream(FileDescriptor.err), true, "UTF-8");
            System.setOut(utf8Out);
            System.setErr(utf8Err);
        } catch (UnsupportedEncodingException e) {
            System.out.println("UTF-8 no soportado, usando salida por defecto.");
         }
    }

    private static void validarCampeonato(Campeonato campeonato) {
        FixtureValidator.validar(campeonato);
        PronosticoValidator.validar(campeonato);
        if (!ErrorHandler.hasErrors()) {
            RankingCalculator.calcularRanking(campeonato);
        }
    }

    private static void mostrarResultados(Campeonato campeonato) {
        imprimirInformacionCampeonato(campeonato);
        RankingCalculator.imprimirRanking(campeonato);
        RankingCalculator.imprimirEstadisticas(campeonato);
    }

    private static void imprimirInformacionCampeonato(Campeonato campeonato) {
        System.out.println("\n--- INFORMACION DEL CAMPEONATO ---");
        System.out.println("Nombre: " + campeonato.getNombre());
        System.out.println("Series: " + campeonato.getSeries().size());
        System.out.println("Participantes: " + campeonato.getParticipantes().size());
        System.out.println();

        for (Serie serie : campeonato.getSeries()) {
            System.out.println("Serie: " + serie.getNombre());
            System.out.println("  Equipos: " + obtenerNombresEquipos(serie));
            System.out.println("  Partidos: " + serie.getPartidos().size());
        }
    }

    private static String obtenerNombresEquipos(Serie serie) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < serie.getEquipos().size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(serie.getEquipos().get(i).getNombre());
        }
        return sb.toString();
    }
}