import java.util.ArrayList;


public class CmdCancel extends RecordedCommand {
    private Reservation r;
    private ArrayList<Table> tables;
    @Override
    public void execute(String[] cmdStrings) {
        try {
            BookingOffice bo = BookingOffice.getInstance();
            SystemDate Date = SystemDate.getInstance();

            if (cmdStrings.length < 3)
                throw new ExInsufficientArguments();
            if (Date.isDatePassed(cmdStrings[1]))
                throw new ExDatePassed();
            if (!cmdStrings[2].matches("\\d+")) 
                throw new ExWrongNumberFormat();
            if(bo.bookingFind(cmdStrings[1],Integer.parseInt(cmdStrings[2]))==null)
                throw new ExBookingNotFound();

            r=bo.bookingFind(cmdStrings[1],Integer.parseInt(cmdStrings[2]));
            bo.removeReservation(r);
            tables = new ArrayList<>(r.getTables());
        
            for (Table table : tables) {
                r.getTables().remove(table);
            }
            
            System.out.println("Done.");
            addUndoCommand(this); 
            clearRedoList();

            }catch (ExInsufficientArguments e) {
                System.out.println(e.getMessage());
            } catch (ExDatePassed e) {
                System.out.println(e.getMessage());
            } catch (ExWrongNumberFormat e) {
                System.out.println(e.getMessage());
            } catch (ExBookingNotFound e) {
                System.out.println(e.getMessage());}
            }

    @Override
    public void undoMe() {
        BookingOffice bo=BookingOffice.getInstance();
        for (Table table : tables) {
            r.getTables().add(table);
        }
        bo.addReservation(r);
        
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        BookingOffice bo=BookingOffice.getInstance();
        bo.removeReservation(r);
        for (Table table : tables) {
            r.getTables().remove(table);
        }
        addUndoCommand(this);
    }
    
}
