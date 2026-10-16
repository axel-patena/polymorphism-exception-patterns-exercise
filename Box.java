public class Box extends Parcel {
    public static final double MAX_WEIGHT = 50.0;
    private static final double RATE_PER_KG = 40.0;
    private static final double VOLUMETRIC_DIVISOR = 3500.0;

    private final int length;
    private final int width;
    private final int height;

    public Box(String trackingNumber, String receiverName, double weight, int length, int width, int height)
            throws InvalidInputException {
        this(trackingNumber, receiverName, weight, length, width, height, MAX_WEIGHT);
    }

    protected Box(String trackingNumber, String receiverName, double weight,
                  int length, int width, int height, double maxWeight) throws InvalidInputException {
        super(trackingNumber, receiverName, weight, maxWeight);
        this.length = Validator.dimension(length, "Length");
        this.width = Validator.dimension(width, "Width");
        this.height = Validator.dimension(height, "Height");
    }

    public int getLength() {
        return length;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getVolumetricWeight() {
        return ((double) length * width * height) / VOLUMETRIC_DIVISOR;
    }

    @Override
    public ParcelType getType() {
        return ParcelType.BOX;
    }

    @Override
    public double getChargeableWeight() {
        return Math.max(Math.ceil(getWeight()), Math.ceil(getVolumetricWeight()));
    }

    @Override
    public double calculateBaseFee() {
        return RATE_PER_KG * getChargeableWeight();
    }
}
