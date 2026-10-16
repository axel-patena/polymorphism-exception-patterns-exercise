public class DuplicateParcelException extends ParcelException {
    public DuplicateParcelException(String trackingNumber) {
        super("Tracking number " + trackingNumber + " already exists!");
    }
}
