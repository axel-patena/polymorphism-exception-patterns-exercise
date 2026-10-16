public enum DeliveryService {
    STANDARD(new StandardDelivery()),
    EXPRESS(new ExpressDelivery()),
    SAME_DAY(new SameDayDelivery());

    private final DeliveryStrategy strategy;

    DeliveryService(DeliveryStrategy strategy) {
        this.strategy = strategy;
    }

    public DeliveryStrategy getStrategy() {
        return strategy;
    }

    public String getName() {
        return strategy.getName();
    }

    public static DeliveryService fromChoice(int choice) throws InvalidInputException {
        DeliveryService[] all = values();
        if (choice < 1 || choice > all.length) {
            throw new InvalidInputException("Invalid choice! Enter a number from 1 to " + all.length + ".");
        }
        return all[choice - 1];
    }

    @Override
    public String toString() {
        return getName();
    }
}
