public class SameDayNotAllowedException extends ParcelException {
    public SameDayNotAllowedException() {
        super("Same Day delivery is only for parcels up to 10 kg!");
    }
}
