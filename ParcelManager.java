import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ParcelManager {
    private final Map<String, Parcel> parcels = new LinkedHashMap<>();

    private ParcelManager() {
    }

    private static final class Holder {
        private static final ParcelManager INSTANCE = new ParcelManager();
    }

    public static ParcelManager getInstance() {
        return Holder.INSTANCE;
    }

    public synchronized boolean exists(String trackingNumber) {
        return trackingNumber != null && parcels.containsKey(normalize(trackingNumber));
    }

    public synchronized void addParcel(Parcel parcel) throws DuplicateParcelException, InvalidInputException {
        if (parcel == null) {
            throw new InvalidInputException("Parcel cannot be null!");
        }
        if (parcel.getDeliveryService() == null) {
            throw new InvalidInputException("A delivery service must be selected first!");
        }
        if (parcels.containsKey(parcel.getTrackingNumber())) {
            throw new DuplicateParcelException(parcel.getTrackingNumber());
        }
        parcels.put(parcel.getTrackingNumber(), parcel);
    }

    public synchronized Parcel findParcel(String trackingNumber) throws ParcelNotFoundException {
        Parcel parcel = (trackingNumber == null) ? null : parcels.get(normalize(trackingNumber));
        if (parcel == null) {
            throw new ParcelNotFoundException();
        }
        return parcel;
    }

    public synchronized List<Parcel> getAllParcels() {
        return Collections.unmodifiableList(new ArrayList<>(parcels.values()));
    }

    private String normalize(String trackingNumber) {
        return trackingNumber.trim().toUpperCase();
    }
}
