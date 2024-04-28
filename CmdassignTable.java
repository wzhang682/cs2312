import java.util.ArrayList;

public class CmdassignTable extends RecordedCommand {
    private Reservation reservation;
    private ArrayList<Table> assignedTables = new ArrayList<>();
    private  int totalPerson=0;
    public void execute(String[] cmdStrings) {
        try {
            BookingOffice bo = BookingOffice.getInstance();
            SystemDate Date = SystemDate.getInstance();

            if (cmdStrings.length < 4)
                throw new ExInsufficientArguments();
            if (Date.isDatePassed(cmdStrings[1]))
                throw new ExDatePassed();
            if (!cmdStrings[2].matches("\\d+")) 
                throw new ExWrongNumberFormat();
            for (int i = 3; i < cmdStrings.length; i++){
                if (bo.isTableAssignedOnSameDay(cmdStrings[1], cmdStrings[i])) {
                    throw new ExBookingAssigned("Table %s is already reserved by another booking!", cmdStrings[i]);
                }
            }            
            if(bo.isBookingHasTableAssignedOnSameDay(cmdStrings[1],Integer.parseInt(cmdStrings[2])))
                throw new ExBookingAssigned();
            if(bo.bookingFind(cmdStrings[1],Integer.parseInt(cmdStrings[2]))==null)
                throw new ExBookingNotFound();
            for (int i = 3; i < cmdStrings.length; i++){
                if(cmdStrings[i].charAt(0)=='T'&&Integer.parseInt(cmdStrings[i].substring(1))>Integer.parseInt("10"))
                   throw new ExTableCodeNotFound( "Table code "+cmdStrings[i]+" not found!");
                else  if(cmdStrings[i].charAt(0)=='F'&&Integer.parseInt(cmdStrings[i].substring(1))>Integer.parseInt("6"))
                   throw new ExTableCodeNotFound( "Table code "+cmdStrings[i]+" not found!");
                else  if(cmdStrings[i].charAt(0)=='H'&&Integer.parseInt(cmdStrings[i].substring(1))>Integer.parseInt("3"))
                   throw new ExTableCodeNotFound( "Table code "+cmdStrings[i]+" not found!");
            }
            ArrayList<Reservation> reservations = bo.findReservations(cmdStrings[1]);
            for (Reservation r : reservations) {
                if (r.getTicketCode() == Integer.parseInt(cmdStrings[2])) {
                    for (int j = 3; j < cmdStrings.length; j++) {
                        char type = cmdStrings[j].charAt(0);
                        if(type=='T')
                            totalPerson+=2;
                        if(type=='F')
                            totalPerson+=4;
                        if(type=='H')
                            totalPerson+=8;
                    }
                    if(totalPerson>=r.getTotPersons()){
                        for (int j = 3; j < cmdStrings.length; j++) {
                            Table table = new Table(cmdStrings[j],r);
                            r.getTables().add(table);
                            assignedTables.add(table);
                            reservation=r;
                        }
                    }else{throw new ExNotEnoughSeats();}
                }
            }
           
            addUndoCommand(this);
            clearRedoList();
            System.out.println("Done.");
        } catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());
        } catch (ExDatePassed e) {
            System.out.println(e.getMessage());
        } catch(ExBookingAssigned e){
            System.out.println(e.getMessage());
        }catch(ExBookingNotFound e){
            System.out.println(e.getMessage());
        } catch (ExWrongNumberFormat e) {
            System.out.println(e.getMessage());
        } catch (ExNotEnoughSeats e) {
            System.out.println(e.getMessage());
        }catch (ExTableCodeNotFound e) {
            System.out.println(e.getMessage());}
    }

    @Override
    public void undoMe() {
       
        for (Table table : assignedTables) {
            reservation.getTables().remove(table);
        }
        reservation.setStatePending();
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        for (Table table : assignedTables) {
            reservation.getTables().add(table);
        }
        reservation.setStateAllocated(assignedTables);
        addUndoCommand(this);
    }
}
