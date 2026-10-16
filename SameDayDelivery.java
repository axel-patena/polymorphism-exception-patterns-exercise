public class SameDayDelivery implements DeliveryStrategy {
    private static final double MULTIPLIER = 2.0;
    private static final double SURCHARGE = 100.0;
    private static final double MAX_CHARGEABLE_WEIGHT = 10.0;

    @Override
    public String getName() {
        return "Same Day";
    }

    @Override
    public double calculateFee(double baseFee) {
        return (baseFee * MULTIPLIER) + SURCHARGE;
    }

    @Override
    public boolean isAllowedFor(double chargeableWeight) {
        return chargeableWeight <= MAX_CHARGEABLE_WEIGHT;
    }
}
