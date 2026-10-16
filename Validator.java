import java.util.regex.Pattern;

public final class Validator {
    private static final Pattern TRACKING = Pattern.compile("^ABC\\d{4}$");
    private static final Pattern LETTERS_ONLY = Pattern.compile("^\\p{L}+( \\p{L}+)*$");
    private static final Pattern DECIMAL = Pattern.compile("^(\\d+(\\.\\d*)?|\\.\\d+)$");

    private Validator() {
    }

    public static String trackingNumber(String input) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException("Tracking number cannot be empty!");
        }
        String normalized = input.trim().toUpperCase();
        if (!TRACKING.matcher(normalized).matches()) {
            throw new InvalidInputException("Invalid tracking number! Use \"ABC\" followed by 4 digits (e.g. ABC0001).");
        }
        return normalized;
    }

    public static String nonEmpty(String input, String fieldName) throws InvalidInputException {
        if (input == null || input.trim().isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty!");
        }
        return input.trim();
    }

    public static String receiverName(String input) throws InvalidInputException {
        return lettersOnly(input, "Receiver name");
    }

    public static String handlingNote(String input) throws InvalidInputException {
        return lettersOnly(input, "Handling note");
    }

    private static String lettersOnly(String input, String fieldName) throws InvalidInputException {
        String text = nonEmpty(input, fieldName).replaceAll("\\s+", " ");
        if (!LETTERS_ONLY.matcher(text).matches()) {
            throw new InvalidInputException(fieldName + " must contain letters only!");
        }
        return text;
    }

    public static double parseDecimal(String input, String fieldName) throws InvalidInputException {
        String text = (input == null) ? "" : input.trim();
        if (text.isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty!");
        }
        if (text.startsWith("-")) {
            throw new InvalidInputException(fieldName + " must be greater than 0!");
        }
        if (!DECIMAL.matcher(text).matches()) {
            throw new InvalidInputException(fieldName + " must be a valid number (e.g. 1.5)!");
        }
        double value;
        try {
            value = Double.parseDouble(text);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a valid number (e.g. 1.5)!");
        }
        if (Double.isNaN(value) || Double.isInfinite(value)) {
            throw new InvalidInputException(fieldName + " is too large!");
        }
        return value;
    }

    public static double weight(double weight, double maxWeight) throws InvalidInputException {
        if (Double.isNaN(weight) || Double.isInfinite(weight) || weight <= 0) {
            throw new InvalidInputException("Weight must be greater than 0!");
        }
        if (weight > maxWeight) {
            throw new InvalidInputException("Weight exceeds the maximum of " + format(maxWeight) + " kg for this parcel type!");
        }
        return weight;
    }

    public static int parseInt(String input, String fieldName) throws InvalidInputException {
        String text = (input == null) ? "" : input.trim();
        if (text.isEmpty()) {
            throw new InvalidInputException(fieldName + " cannot be empty!");
        }
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a whole number!");
        }
    }

    public static int range(int value, String fieldName, int min, int max) throws InvalidInputException {
        if (value < min || value > max) {
            throw new InvalidInputException(fieldName + " must be between " + min + " and " + max + "!");
        }
        return value;
    }

    public static int dimension(int value, String fieldName, int maxValue) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be greater than 0!");
        }
        if (value > maxValue) {
            throw new InvalidInputException(fieldName + " cannot exceed " + maxValue + " cm!");
        }
        return value;
    }

    public static String format(double value) {
        return (value == Math.rint(value)) ? String.valueOf((long) value) : String.valueOf(value);
    }
}