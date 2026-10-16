public enum ParcelType {
    DOCUMENT("Document"),
    BOX("Box"),
    FRAGILE("Fragile");

    private final String label;

    ParcelType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static ParcelType fromChoice(int choice) throws InvalidInputException {
        ParcelType[] all = values();
        if (choice < 1 || choice > all.length) {
            throw new InvalidInputException("Invalid choice! Enter a number from 1 to " + all.length + ".");
        }
        return all[choice - 1];
    }

    @Override
    public String toString() {
        return label;
    }
}
