import java.util.HashMap;

public class BookingTicketController {
    
    private static HashMap<Day,Integer> tCtrl= new HashMap<>();
    public static HashMap<Day,Integer> getMap(){
        return tCtrl;

    }
    public static int takeTicket(Day d) {
        Integer t=tCtrl.get(d);
        if(t==null){
            tCtrl.put(d.clone(), 2);
            return 1;
        }else{
            tCtrl.put(d,t+1);
            return t;
        }
    }

}
