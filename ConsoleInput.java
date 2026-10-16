import java.util.Scanner;

public class ConsoleInput {
    private final Scanner scanner = new Scanner(System.in);

    public static class InputClosedException extends RuntimeException {
        public InputClosedException() {
            super("Input stream closed.");
        }
    }

    private interface Parser<T> {
        T parse(String raw) throws InvalidInputException;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        if (!scanner.hasNextLine()) {
            throw new InputClosedException();
        }
        return scanner.nextLine();
    }

    private <T> T readValid(String prompt, Parser<T> parser) {
        while (true) {
            try {
                return parser.parse(readLine(prompt));
            } catch (InvalidInputException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int readMenuChoice(String prompt, int min, int max) {
        return readValid(prompt, raw -> Validator.range(Validator.parseInt(raw, "Choice"), "Choice", min, max));
    }

    public ParcelType readParcelType() {
        System.out.println("Parcel Type:");
        System.out.println("  1. Document");
        System.out.println("  2. Box");
        System.out.println("  3. Fragile");
        return readValid("Choose type (1-3): ",
                raw -> ParcelType.fromChoice(Validator.parseInt(raw, "Choice")));
    }

    public String readNewTrackingNumber(ParcelManager manager) {
        return readValid("Tracking Number (e.g. ABC0001): ", raw -> {
            String trackingNumber = Validator.trackingNumber(raw);
            if (manager.exists(trackingNumber)) {
                throw new InvalidInputException("Tracking number " + trackingNumber + " already exists! Enter a different one.");
            }
            return trackingNumber;
        });
    }

    public String readReceiverName() {
        return readValid("Receiver Name: ", Validator::receiverName);
    }

    public double readWeight(double maxWeight) {
        return readWeight("Weight in kg (max " + Validator.format(maxWeight) + "): ", maxWeight, Integer.MAX_VALUE);
    }

    public double readWeight(double maxWeight, int maxDecimalPlaces) {
        return readWeight("Weight in kg (max " + Validator.format(maxWeight)
                + ", up to " + maxDecimalPlaces + " decimal places): ", maxWeight, maxDecimalPlaces);
    }

    private double readWeight(String prompt, double maxWeight, int maxDecimalPlaces) {
        return readValid(prompt, raw -> {
            double weight = Validator.parseDecimal(raw, "Weight");
            Validator.decimalPlaces(raw, maxDecimalPlaces, "Weight");
            return Validator.weight(weight, maxWeight);
        });
    }

    public int readPages() {
        return readValid("Number of Pages (1-100): ",
                raw -> Validator.range(Validator.parseInt(raw, "Number of pages"), "Number of pages", 1, 100));
    }

    public int readDimension(String name) {
        return readValid(name + " in cm (max " + Box.MAX_DIMENSION + "): ",
                raw -> Validator.dimension(Validator.parseInt(raw, name), name, Box.MAX_DIMENSION));
    }

    public String readHandlingNote() {
        return readValid("Handling Note: ", Validator::handlingNote);
    }

    public DeliveryService readDeliveryService() {
        System.out.println("Delivery Service:");
        System.out.println("  1. Standard");
        System.out.println("  2. Express");
        System.out.println("  3. Same Day");
        return readValid("Choose service (1-3): ",
                raw -> DeliveryService.fromChoice(Validator.parseInt(raw, "Choice")));
    }
}