package util;

import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {
    private static List<String> errors = new ArrayList<>();
    private static List<String> warnings = new ArrayList<>();

    public static void addError(int line, int column, String message) {
        String error = String.format("[ERROR] Línea %d, Columna %d: %s", line, column, message);
        errors.add(error);
    }

    public static void addError(String message) {
        errors.add("[ERROR] " + message);
    }

    public static void addWarning(String message) {
        warnings.add("[WARNING] " + message);
    }

    public static boolean hasErrors() {
        return !errors.isEmpty();
    }

    public static List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public static List<String> getWarnings() {
        return new ArrayList<>(warnings);
    }

    public static void printErrors() {
        if (!errors.isEmpty()) {
            System.out.println("\n ERRORES");
            for (String error : errors) {
                System.out.println(error);
            }
        }

        if (!warnings.isEmpty()) {
            System.out.println("\nADVERTENCIAS");
            for (String warning : warnings) {
                System.out.println(warning);
            }
        }

        if (errors.isEmpty() && warnings.isEmpty()) {
            System.out.println("\n✓ No se encontraron errores");
        }
    }

    public static void clear() {
        errors.clear();
        warnings.clear();
    }

    public static int getErrorCount() {
        return errors.size();
    }

    public static int getWarningCount() {
        return warnings.size();
    }
}