public interface DeliveryStrategy {
    String getName();

    double calculateFee(double baseFee);

    boolean isAllowedFor(double chargeableWeight);
}
