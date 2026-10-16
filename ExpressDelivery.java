public class ExpressDelivery implements DeliveryStrategy {
    private static final double MULTIPLIER = 1.5;

    @Override
    public String getName() {
        return "Express";
    }

    @Override
    public double calculateFee(double baseFee) {
        return baseFee * MULTIPLIER;
    }

    @Override
    public boolean isAllowedFor(double chargeableWeight) {
        return true;
    }
}
