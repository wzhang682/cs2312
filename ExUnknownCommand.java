public class ExUnknownCommand extends Exception { 
    public ExUnknownCommand() { super("Unknown Command."); }  
     
    public ExUnknownCommand(String message) { super(message); } }