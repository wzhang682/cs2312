public class CmdlistTableAllocations implements Command{
    
    public void execute(String[] cmdparts) {
        try{if (cmdparts.length < 2)
                throw new ExInsufficientArguments();
            System.out.println("Allocated tables: ");
            BookingOffice bo=BookingOffice.getInstance();
            bo.listTableAllocations(cmdparts[1]);
        }catch (ExInsufficientArguments e) {
            System.out.println(e.getMessage());}
        
    }

  
       
}


