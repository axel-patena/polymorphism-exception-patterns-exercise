public class ParcelFactory {
    private final ConsoleInput input;
    private final ParcelManager manager;

    public ParcelFactory(ConsoleInput input, ParcelManager manager) {
        this.input = input;
        this.manager = manager;
    }

    public Parcel create(ParcelType type) throws InvalidInputException {
        String trackingNumber = input.readNewTrackingNumber(manager);
        String receiverName = input.readReceiverName();
        return switch (type) {
            case DOCUMENT -> createDocument(trackingNumber, receiverName);
            case BOX -> createBox(trackingNumber, receiverName);
            case FRAGILE -> createFragile(trackingNumber, receiverName);
        };
    }

    private Document createDocument(String trackingNumber, String receiverName) throws InvalidInputException {
        double weight = input.readWeight(Document.MAX_WEIGHT, Document.MAX_DECIMAL_PLACES);
        int pages = input.readPages();
        return new Document(trackingNumber, receiverName, weight, pages);
    }

    private Box createBox(String trackingNumber, String receiverName) throws InvalidInputException {
        double weight = input.readWeight(Box.MAX_WEIGHT);
        int length = input.readDimension("Length");
        int width = input.readDimension("Width");
        int height = input.readDimension("Height");
        return new Box(trackingNumber, receiverName, weight, length, width, height);
    }

    private Fragile createFragile(String trackingNumber, String receiverName) throws InvalidInputException {
        double weight = input.readWeight(Fragile.MAX_WEIGHT);
        int length = input.readDimension("Length");
        int width = input.readDimension("Width");
        int height = input.readDimension("Height");
        String handlingNote = input.readHandlingNote();
        return new Fragile(trackingNumber, receiverName, weight, length, width, height, handlingNote);
    }
}