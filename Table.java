import java.util.ArrayList;
import java.util.Comparator;

public class Table {
    private String code;
    private Reservation reservation; 
    public Table(String code, Reservation reservation) {
        this.code = code;
        this.reservation = reservation;
    }
    public Table(String code) {
        this.code = code;
    }
    public String getTableCode() {
        return code;
    }
    
    public int getTicketCode() {
        if (reservation != null) {
            return reservation.getTicketCode();
        } else {
            return -1; 
        }
    }

    public static ArrayList<Table> getAllTablesAvailable(ArrayList<Table>t1, ArrayList<Table>t2){
        for (int i = 1; i <= 10; i++) {
            t1.add(new Table("T" + String.format("%02d", i)));
        }
        for (int i = 1; i <= 6; i++) {
            t1.add(new Table("F" + String.format("%02d", i)));
        }
        for (int i = 1; i <= 3; i++) {
            t1.add(new Table("H" + String.format("%02d", i)));
        }
        for (Table t  : t2) {
            for (int i = 0; i < t1.size(); i++) {
                Table t3 = t1.get(i);
                if (t.getTableCode().equals(t3.getTableCode())) {
                   t1.remove(i);
                    i--; 
                }
            }
        }
        return t1;
    } 
    public static boolean hasTTable(ArrayList<Table> tables){
        for(Table t: tables){
            if(t.getTableCode().charAt(0)=='T')
                return true;
        }
        return false;
    }
    public static boolean hasFTable(ArrayList<Table> tables){
        for(Table t: tables){
            if(t.getTableCode().charAt(0)=='F')
                return true;
        }
        return false;
    }
    public static boolean hasHTable(ArrayList<Table> tables){
        for(Table t: tables){
            if(t.getTableCode().charAt(0)=='H')
                return true;
        }
        return false;
    }
    public static int getTotal(ArrayList<Table> Tables) {
        int count=0;
        for(Table t: Tables){
            if(t.getTableCode().charAt(0)=='T')
                count+=2;
            if(t.getTableCode().charAt(0)=='F')
                count+=4;
            if(t.getTableCode().charAt(0)=='H')
                count+=8;
    }
    return count;
    }
    
    public static ArrayList<Table> sortTables(ArrayList<Table> t) {
        Comparator<Table> tableTypeComparator = new Comparator<Table>() {
            @Override
            public int compare(Table t1, Table t2) {
                String type1 = t1.getTableCode();
                String type2 = t2.getTableCode();
        
                char prefix1 = type1.charAt(0);
                char prefix2 = type2.charAt(0);
        
                if (prefix1 != prefix2) {
                    return prefix1 == 'T' ? -1 : (prefix2 == 'T' ? 1 : (prefix1 == 'F' ? -1 : (prefix2 == 'F' ? 1 : 0)));
                }
                String numericPart1 = type1.substring(1);
                String numericPart2 = type2.substring(1);
        
                return Integer.compare(Integer.parseInt(numericPart1), Integer.parseInt(numericPart2));
            }
        };
        t.sort(tableTypeComparator);
        return t;
    }
}
