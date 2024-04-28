import java.util.ArrayList; 
import java.util.Collections; //Provides sorting 

public class BookingOffice{
    private ArrayList<Reservation> allReservations;

    private static BookingOffice instance = new BookingOffice();   

    private BookingOffice() { allReservations = new ArrayList<>();}
    public static BookingOffice getInstance() {return instance; } 

    public void listReservations() {Reservation.list(allReservations);}

    public Reservation addReservation(String guestName,String  phoneNumber,int totPersons,String sDateDine ){
        Reservation r = new Reservation(guestName,phoneNumber,totPersons,sDateDine); 
        allReservations.add(r); 
        Collections.sort(allReservations);
        return r;
    }
    public void removeReservation(Reservation r){
        allReservations.remove(r);
        Collections.sort(allReservations);
    }
    public void addReservation(Reservation r){
        allReservations.add(r);
        Collections.sort(allReservations);
    }
    public ArrayList<Reservation> findCustomer(String name){
        ArrayList<Reservation> cuntomers=new ArrayList<>();
        for(Reservation r:allReservations){
            if(r.getName().equals(name))
                cuntomers.add(r);
        }
        return cuntomers;
    }
    public boolean findDate(String date){
        for(Reservation r:allReservations){
            if(r.getDate().equals(date))
                return true;
        }
        return false;
    }
    public ArrayList<Reservation> findReservations(String date){
        ArrayList<Reservation> rList=new ArrayList<>();
        for(Reservation r:allReservations){
            if(r.getDate().equals(date))
                rList.add(r);
        }
        return rList;
    }
    
    public  ArrayList<Reservation> getReservations() {
        return allReservations;
    }

    public boolean isTableAssignedOnSameDay(String date , String tableId) {
        for (Reservation r : allReservations) {
            for(Table table:r.getTables())
                if (table.getTableCode().equals(tableId) && r.getDate().equals(date)) {
                    return true; 
                }
        }
        return false;
    }
    public boolean isBookingHasTableAssignedOnSameDay(String date, int ticketCode) {
        for (Reservation r : allReservations) {
            if (r.getDate().equals(date) && r.getTicketCode() == ticketCode) {
                if (r.getTables().size() > 0) {
                    return true; 
                }
            }
        }
        return false; 
    }
    public Reservation bookingFind( String date, int ticketCode) {
        for (Reservation r : allReservations) {
            if (r.getDate().equals(date) && r.getTicketCode() == ticketCode)
                return r;
        }
    return null;
    }
    public void listTableAllocations(String string) {
        boolean anyAllocations = false;
        int requestNum = 0;
        int totalPerson = 0;
        ArrayList<Table> allAllocatedTables = new ArrayList<>();
        ArrayList<Table> allAvailableTables=new ArrayList<>();
        ArrayList<Reservation>reservations=findReservations(string);
        for(Reservation r:reservations){
          if (r.haveAllocations(r.getTables())) {
                allAllocatedTables.addAll(r.getTables());
                anyAllocations = true;
            } else {
            requestNum++;
            totalPerson += r.getTotPersons();
            }
        }
        if (!anyAllocations) {
            System.out.println("[None]");
        }else{ 
            Table.sortTables(allAllocatedTables);
            for (Table table : allAllocatedTables) {
                System.out.printf("%s (Ticket %d)\n", table.getTableCode(), table.getTicketCode());
            }

        }
        System.out.println("Available tables:");
       
       
        allAvailableTables=Table.getAllTablesAvailable(allAvailableTables,allAllocatedTables);
        Table.sortTables(allAvailableTables);
        for (Table t : allAvailableTables) {
            System.out.print(t.getTableCode()+" ");
        }
        System.out.println();
        System.out.printf("Total number of pending requests = %d (Total number of persons = %d)\n"
        , requestNum, totalPerson);


    }
    
}
 