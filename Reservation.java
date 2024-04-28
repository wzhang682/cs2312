import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class Reservation implements Comparable<Reservation> {
    private String guestName; 
    private String phoneNumber; 
    private int totPersons; 
    private Day dateDine; 
    private Day dateRequest;
    private RState state;
    private int ticketCode;
    private ArrayList<Table> tables;
    public Reservation(String guestName, String phoneNumber, int totPersons, String sDateDine) { 
        this.guestName=guestName;
        this.phoneNumber=phoneNumber;
        this.totPersons=totPersons;
        this.dateDine=new Day(sDateDine);
        this.dateRequest=SystemDate.getInstance().clone();
        this.ticketCode=BookingTicketController.takeTicket(dateDine);
        this.state=new RStatePending();
        tables=new ArrayList<>();
        } 
       
    public String getName(){return guestName;}
    public String getDate(){return dateDine.toString();}
    public String getPhoneNumber(){ return phoneNumber;}
    public ArrayList<Table> getTables() {return tables;}
    public int getTotPersons() {return totPersons;}
    
    public static void list(ArrayList<Reservation> allReservations) { 

        Collections.sort(allReservations, reservationComparator);

        System.out.printf("%-13s%-11s%-14s%-25s%-11s%s",
        "Guest Name", "Phone", "Request Date", "Dining Date and Ticket", "#Persons", "Status");
        System.out.println();

        for (Reservation r: allReservations) { 
            if(r.getTables().size()>0)
                r.setStateAllocated();
            System.out.printf("%-13s%-11s%-14s%-25s%4d       %s", r.guestName, 
            r.phoneNumber, r.dateRequest, r.dateDine+String.format(" (Ticket %d)",
            r.ticketCode), r.totPersons, r.state.getStatus()); 
            System.out.println();
            }
    }
    
    public RState setStateAllocated(ArrayList<Table> table){
        state=new RStateAllocated(table);
        return state;
    }
    public RState setStateAllocated(){
        state=new RStateAllocated(tables);
        return state;
    }
    public RState setStatePending(){
        state=new RStatePending();
        return state;
    }
    @Override
    
    public int compareTo(Reservation another) {
        return this.guestName.compareTo(another.guestName);
    }
    public static Comparator<Reservation> reservationComparator = new Comparator<Reservation>() {
        @Override
        public int compare(Reservation r1, Reservation r2) {
            // First, compare guest names
            int nameComparison = r1.guestName.compareTo(r2.guestName);
            if (nameComparison != 0) {
                return nameComparison;
            } else {
                int numComparison = Integer.compare(Integer.parseInt(r1.getPhoneNumber()), Integer.parseInt(r2.getPhoneNumber()));
                if (numComparison != 0) {
                    return numComparison;
                } else {
                    return r1.dateDine.compareTo(r2.dateDine);
                }
            }
        }
    };
    public int getTicketCode() {
        return ticketCode;
    }
    public void listTableAllocations(){
        for(int i=0;i<tables.size();i++){
            System.out.printf("%s (Ticket %d)",tables.get(i).getTableCode(),ticketCode);
            System.out.println();
        }
    }
    public boolean haveAllocations(ArrayList<Table> table){
        if (table.isEmpty())
            return false;
        return true;
    }
    public void incrementTicketCount(Day dateDine) {
        Integer count = BookingTicketController.getMap().get(dateDine);
        
        BookingTicketController.getMap().put(dateDine, count + 1);
        }
    public void decrementTicketCount(Day dateDine) {
        Integer count = BookingTicketController.getMap().get(dateDine);
        if (count != null) {
            BookingTicketController.getMap().put(dateDine, count - 1);
        }
    }
}

