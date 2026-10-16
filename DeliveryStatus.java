public enum DeliveryStatus {
    PENDING("Pending"),
    DELIVERED("Delivered");

    private final String label;

    DeliveryStatus(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
