import java.util.List;

public class ParcelTable {
    private static final String ROW_FORMAT = "%-10s %-9s %-20s %12s %-9s %12s %-10s%n";
    private static final String SEPARATOR = "-".repeat(10 + 1 + 9 + 1 + 20 + 1 + 12 + 1 + 9 + 1 + 12 + 1 + 10);
    private static final int RECEIVER_WIDTH = 20;

    public void print(List<Parcel> parcels) {
        if (parcels.isEmpty()) {
            System.out.println("No parcels to display.");
            return;
        }
        System.out.println(SEPARATOR);
        System.out.printf(ROW_FORMAT, "Tracking", "Type", "Receiver", "Chg. Wt (kg)", "Service", "Fee (Php)", "Status");
        System.out.println(SEPARATOR);
        for (Parcel parcel : parcels) {
            printRow(parcel);
        }
        System.out.println(SEPARATOR);
        System.out.println("Total parcels: " + parcels.size());
    }

    private void printRow(Parcel parcel) {
        System.out.printf(ROW_FORMAT,
                parcel.getTrackingNumber(),
                parcel.getType(),
                truncate(parcel.getReceiverName()),
                String.format("%.2f", parcel.getChargeableWeight()),
                parcel.getDeliveryService().getName(),
                String.format("%.2f", parcel.getDeliveryFee()),
                parcel.getStatus());
    }

    private String truncate(String text) {
        return text.length() <= RECEIVER_WIDTH ? text : text.substring(0, RECEIVER_WIDTH - 3) + "...";
    }
}
