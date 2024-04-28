import java.util.ArrayList;



public class CmdSuggestTables implements Command {
    private ArrayList<Table> allAvailableTables=new ArrayList<>();
    private ArrayList<Table> allAllocatedTables=new ArrayList<>();
    private Reservation r;
    
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
            if(bo.isBookingHasTableAssignedOnSameDay(cmdStrings[1],Integer.parseInt(cmdStrings[2])))
                throw new ExBookingAssigned();
            r=bo.bookingFind(cmdStrings[1],Integer.parseInt(cmdStrings[2]));
            int totalPerson=r.getTotPersons();
            ArrayList<Reservation> reservations = bo.findReservations(cmdStrings[1]);
            for(Reservation reservation:reservations){
                allAllocatedTables.addAll(reservation.getTables());
            }
            allAvailableTables=Table.getAllTablesAvailable(allAvailableTables,allAllocatedTables);
            System.out.println("Suggestion for "+totalPerson+" persons: "+suggestTables(totalPerson));


            }catch (ExInsufficientArguments e) {
                System.out.println(e.getMessage());
            } catch (ExDatePassed e) {
                System.out.println(e.getMessage());
            } catch (ExBookingNotFound e) {
                System.out.println(e.getMessage());
            } catch (ExWrongNumberFormat e) {
                System.out.println(e.getMessage());
            } catch(ExBookingAssigned e){
                    System.out.println(e.getMessage());
                }

}
    private String suggestTables(int totalPerson) {
        
        String tableSug = "";
        Table.sortTables(allAvailableTables);
        if(totalPerson>Table.getTotal(allAvailableTables))
            return "Not enough tables";
        if (0<totalPerson&&totalPerson <= 2 && Table.hasTTable(allAvailableTables)) {
            tableSug += suggestTtable();
            return tableSug;
        }else if (0<totalPerson&&totalPerson <= 2 && !(Table.hasTTable(allAvailableTables))&& Table.hasFTable(allAvailableTables)) {
            tableSug += suggestFtable();
            return tableSug;
        }else if (0<totalPerson&&totalPerson <= 2 && !(Table.hasFTable(allAvailableTables))&&!(Table.hasTTable(allAvailableTables))&&Table.hasHTable(allAvailableTables)) {
            tableSug = suggestHtable();
            return tableSug;
        } else if (totalPerson > 2 && totalPerson <= 4 && Table.hasFTable(allAvailableTables)) {
            tableSug += suggestFtable();
            return tableSug;
        }else if (totalPerson > 2 && totalPerson <= 4 && !(Table.hasFTable(allAvailableTables))&& Table.hasTTable(allAvailableTables)) {
            tableSug = suggestTtable()+suggestTtable();
            return tableSug;
        }else if (totalPerson > 2 && totalPerson <= 4 && !(Table.hasFTable(allAvailableTables))&&!(Table.hasTTable(allAvailableTables))&&Table.hasHTable(allAvailableTables)) {
            tableSug = suggestHtable();
            return tableSug;
        } else if (4 < totalPerson && totalPerson <= 8 && Table.hasHTable(allAvailableTables)) {
            tableSug += suggestHtable();
            return tableSug;
        }else if (4 < totalPerson && totalPerson <= 8 && !(Table.hasHTable(allAvailableTables))&&Table.hasFTable(allAvailableTables)) {
            return tableSug = suggestFtable()+suggestFtable();
        }else if (4 < totalPerson && totalPerson <= 8 && !(Table.hasHTable(allAvailableTables))&& !(Table.hasFTable(allAvailableTables))&&Table.hasTTable(allAvailableTables)) {
                if(totalPerson==5) 
                    return tableSug +=  suggestTtable()+suggestTtable();
                if(totalPerson==6) 
                    return tableSug +=  suggestTtable()+suggestTtable()+suggestTtable();
                else
                    return tableSug +=  suggestTtable()+suggestTtable()+suggestTtable()+suggestTtable();
        }else if (totalPerson > 8) {
            while (totalPerson > 8 && Table.hasHTable(allAvailableTables)) {
                totalPerson -= 8;
                tableSug += suggestHtable();
            }
    
            while (totalPerson >=4 && !(Table.hasHTable(allAvailableTables))&& Table.hasFTable(allAvailableTables)) {
                totalPerson -= 4;
                tableSug += suggestFtable();
            }
            while (totalPerson > 0&&!(Table.hasHTable(allAvailableTables))&& !(Table.hasFTable(allAvailableTables))&&Table.hasTTable(allAvailableTables)){
                totalPerson -= 2;
                tableSug += suggestTtable();
            }
            if(totalPerson>0)
                return tableSug + suggestTables(totalPerson);
            if(totalPerson==0)
                return tableSug;
            
        }
        
        return null;
    }
        
    private String suggestTtable(){
        for(Table t:allAvailableTables){
            if(t.getTableCode().charAt(0)=='T'){
                allAvailableTables.remove(t);
                return t.getTableCode()+" ";
            }
        }
        return "null";  
    }
    private String suggestFtable(){
        for(Table t:allAvailableTables){
            if(t.getTableCode().charAt(0)=='F'){
                allAvailableTables.remove(t);
                return t.getTableCode()+" ";
            }
        }
        return "null";
    }
    private String suggestHtable(){
        for(Table t:allAvailableTables){
            if(t.getTableCode().charAt(0)=='H'){
                allAvailableTables.remove(t);
                return t.getTableCode()+" ";
            }
        }
        return "null";
    }
}