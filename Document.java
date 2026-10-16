public class Document extends Parcel {
    public static final double MAX_WEIGHT = 0.5;
    private static final double FLAT_FEE = 50.0;
    private static final int MIN_PAGES = 1;
    private static final int MAX_PAGES = 100;

    private final int numberOfPages;

    public Document(String trackingNumber, String receiverName, double weight, int numberOfPages)
            throws InvalidInputException {
        super(trackingNumber, receiverName, weight, MAX_WEIGHT);
        this.numberOfPages = Validator.range(numberOfPages, "Number of pages", MIN_PAGES, MAX_PAGES);
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    @Override
    public ParcelType getType() {
        return ParcelType.DOCUMENT;
    }

    @Override
    public double getChargeableWeight() {
        return getWeight();
    }

    @Override
    public double calculateBaseFee() {
        return FLAT_FEE;
    }
}
