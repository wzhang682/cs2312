public class ExBookingAssigned extends Exception {
    public ExBookingAssigned(){
        super("Table(s) already assigned for this booking!");
    }
    
    public ExBookingAssigned(String format, Object... args) {
        super(String.format(format, args));
    }
}
