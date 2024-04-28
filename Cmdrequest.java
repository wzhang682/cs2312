public class Cmdrequest extends RecordedCommand {
    private Reservation r;

    @Override
    public void execute(String[] cmdStrings){
        try{
            if (cmdStrings.length<5) 
                throw new ExInsufficientArguments();
            if (!cmdStrings[3].matches("\\d+")) 
                throw new ExWrongNumberFormat();
            SystemDate Date=SystemDate.getInstance();
            if (Date.isDatePassed(cmdStrings[4]))
                throw new ExDatePassed();
            BookingOffice bo=BookingOffice.getInstance();
            if(isDuplicateReservation(cmdStrings[1],cmdStrings[4]))
                throw new ExBookingSamePersonDay();
            r=bo.addReservation(cmdStrings[1],cmdStrings[2],Integer.parseInt(cmdStrings[3]),cmdStrings[4]);
            addUndoCommand(this);
            clearRedoList();
            System.out.printf("Done. Ticket code for %s: %d\n",r.getDate(), r.getTicketCode());
        } catch (ExInsufficientArguments e) { 
            System.out.println(e.getMessage()); }
            catch (ExWrongNumberFormat e) { 
                System.out.println(e.getMessage()); }
                catch (ExDatePassed e) { 
                    System.out.println(e.getMessage()); }
                    catch (ExBookingSamePersonDay e) { 
                        System.out.println(e.getMessage()); }
        
    }
   private boolean isDuplicateReservation(String guestName, String requestedDate) {
        BookingOffice bo = BookingOffice.getInstance();
        for (Reservation reservation : bo.findCustomer(guestName))    
                if (reservation.getDate().equals(requestedDate)) {
                    return true; // Duplicate reservation found
                }
        return false; // No duplicate reservation found
    }

    @Override
    public void undoMe() {
        BookingOffice bo=BookingOffice.getInstance();
        bo.removeReservation(r);
        Day dateDine = new Day(r.getDate());
        decrementTicketCount(dateDine);
        addRedoCommand(this);
    }
    private void decrementTicketCount(Day dateDine) {
        Integer count = BookingTicketController.getMap().get(dateDine);
        if (count != null) {
            // Decrement the count by 1 and update the HashMap
            BookingTicketController.getMap().put(dateDine, count - 1);
        }
    }
    @Override
    public void redoMe() {
        BookingOffice bo=BookingOffice.getInstance();
        bo.addReservation(r);
        Day dateDine = new Day(r.getDate());
        incrementTicketCount(dateDine);
        addUndoCommand(this);
    }
    private void incrementTicketCount(Day dateDine) {
        Integer count = BookingTicketController.getMap().get(dateDine);
        
        BookingTicketController.getMap().put(dateDine, count + 1);
        }
}

