public abstract class Parcel {
    private final String trackingNumber;
    private final String receiverName;
    private final double weight;
    private DeliveryService deliveryService;
    private DeliveryStatus status;

    protected Parcel(String trackingNumber, String receiverName, double weight, double maxWeight)
            throws InvalidInputException {
        this.trackingNumber = Validator.trackingNumber(trackingNumber);
        this.receiverName = Validator.receiverName(receiverName);
        this.weight = Validator.weight(weight, maxWeight);
        this.status = DeliveryStatus.PENDING;
    }

    public abstract ParcelType getType();

    public abstract double getChargeableWeight();

    public abstract double calculateBaseFee();

    public double getDeliveryFee() {
        if (deliveryService == null) {
            throw new IllegalStateException("No delivery service selected.");
        }
        return deliveryService.getStrategy().calculateFee(calculateBaseFee());
    }

    public void setDeliveryService(DeliveryService service) throws SameDayNotAllowedException, InvalidInputException {
        if (service == null) {
            throw new InvalidInputException("Delivery service cannot be empty!");
        }
        if (!service.getStrategy().isAllowedFor(getChargeableWeight())) {
            throw new SameDayNotAllowedException();
        }
        this.deliveryService = service;
    }

    public void markDelivered() {
        this.status = DeliveryStatus.DELIVERED;
    }

    public boolean isDelivered() {
        return status == DeliveryStatus.DELIVERED;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public double getWeight() {
        return weight;
    }

    public DeliveryService getDeliveryService() {
        return deliveryService;
    }

    public DeliveryStatus getStatus() {
        return status;
    }
}