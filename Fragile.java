public class Fragile extends Box {
    public static final double MAX_WEIGHT = 30.0;
    private static final double HANDLING_FEE = 100.0;

    private final String handlingNote;

    public Fragile(String trackingNumber, String receiverName, double weight,
                   int length, int width, int height, String handlingNote) throws InvalidInputException {
        super(trackingNumber, receiverName, weight, length, width, height, MAX_WEIGHT);
        this.handlingNote = Validator.nonEmpty(handlingNote, "Handling note");
    }

    public String getHandlingNote() {
        return handlingNote;
    }

    @Override
    public ParcelType getType() {
        return ParcelType.FRAGILE;
    }

    @Override
    public double calculateBaseFee() {
        return super.calculateBaseFee() + HANDLING_FEE;
    }
}
