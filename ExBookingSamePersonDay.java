
public class ExBookingSamePersonDay extends Exception {
    public ExBookingSamePersonDay(){
        super("Booking by the same person for the dining date already exists!");
    }
}
