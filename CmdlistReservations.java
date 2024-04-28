public class CmdlistReservations implements Command {
    
    public void execute(String[] cmdStrings){
        BookingOffice bo=BookingOffice.getInstance();
        bo.listReservations();
    }
    

}
