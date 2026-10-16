public class ParcelDeliveryApp {
    private static final int ADD = 1;
    private static final int VIEW = 2;
    private static final int CHANGE = 3;
    private static final int DELIVER = 4;
    private static final int EXIT = 5;

    private final ParcelManager manager = ParcelManager.getInstance();
    private final ConsoleInput input = new ConsoleInput();
    private final ParcelFactory factory = new ParcelFactory(input, manager);
    private final ParcelTable table = new ParcelTable();

    public static void main(String[] args) {
        new ParcelDeliveryApp().run();
    }

    public void run() {
        try {
            boolean running = true;
            while (running) {
                printMenu();
                int choice = input.readMenuChoice("Enter choice: ", ADD, EXIT);
                System.out.println();
                running = handle(choice);
                System.out.println();
            }
        } catch (ConsoleInput.InputClosedException e) {
            System.out.println("\nInput closed. Exiting. Goodbye!");
        }
    }

    private boolean handle(int choice) {
        try {
            switch (choice) {
                case ADD -> addParcel();
                case VIEW -> table.print(manager.getAllParcels());
                case CHANGE -> changeDeliveryService();
                case DELIVER -> markAsDelivered();
                default -> {
                    System.out.println("Thank you for using the Parcel Delivery System. Goodbye!");
                    return false;
                }
            }
        } catch (ParcelException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void printMenu() {
        System.out.println("===== PARCEL DELIVERY SYSTEM =====");
        System.out.println("1. Add Parcel");
        System.out.println("2. View All Parcels");
        System.out.println("3. Change Delivery Service");
        System.out.println("4. Mark as Delivered");
        System.out.println("5. Exit");
    }

    private void addParcel() throws ParcelException {
        ParcelType type = input.readParcelType();
        Parcel parcel = factory.create(type);
        selectServiceUntilAllowed(parcel);
        manager.addParcel(parcel);
        System.out.printf("Parcel added successfully! Delivery fee: Php %.2f%n", parcel.getDeliveryFee());
    }

    private void selectServiceUntilAllowed(Parcel parcel) throws InvalidInputException {
        while (true) {
            try {
                parcel.setDeliveryService(input.readDeliveryService());
                return;
            } catch (SameDayNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void changeDeliveryService() throws ParcelNotFoundException, InvalidInputException {
        Parcel parcel = manager.findParcel(input.readLine("Enter Tracking Number: "));
        if (parcel.isDelivered()) {
            System.out.println("Parcel already delivered!");
            return;
        }
        try {
            parcel.setDeliveryService(input.readDeliveryService());
            System.out.printf("Delivery service changed! New fee: Php %.2f%n", parcel.getDeliveryFee());
        } catch (SameDayNotAllowedException e) {
            System.out.println(e.getMessage());
        }
    }

    private void markAsDelivered() throws ParcelNotFoundException {
        Parcel parcel = manager.findParcel(input.readLine("Enter Tracking Number: "));
        if (parcel.isDelivered()) {
            System.out.println("Parcel was already delivered.");
            return;
        }
        parcel.markDelivered();
        System.out.println("Parcel delivered successfully!");
    }
}
