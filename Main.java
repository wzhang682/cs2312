import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

	public static void main(String [] args) throws FileNotFoundException
    {   
  
    Scanner in = new Scanner(System.in);
    System.out.print("Please input the file pathname: ");
    String filepathname = in.nextLine();
    Scanner inFile=null;
    try {
			inFile = new Scanner(new File(filepathname));
			String cmdLine1 = inFile.nextLine();
			String[] cmdLine1Parts = cmdLine1.split("\\|");
			System.out.println("\n> "+cmdLine1);
			SystemDate.createTheInstance(cmdLine1Parts[1]);

			while (inFile.hasNext()) {
			
				String cmdLine = inFile.nextLine().trim();
				if(cmdLine.equals(""))
					continue;
				System.out.println("\n> " + cmdLine);
				String[] cmdparts=cmdLine.split("\\|");
				if(cmdparts[0].equals("startNewDay"))
					(new CmdStartNewDay()).execute(cmdparts);
				else if(cmdparts[0].equals("request"))
					(new Cmdrequest()).execute(cmdparts);
				else if(cmdparts[0].equals("cancel"))
					(new CmdCancel()).execute(cmdparts);
				else if(cmdparts[0].equals("suggestTable"))
					(new CmdSuggestTables()).execute(cmdparts);
				else if(cmdparts[0].equals("listTableAllocations"))
					(new CmdlistTableAllocations()).execute(cmdparts);
				else if(cmdparts[0].equals("listReservations"))
					(new CmdlistReservations()).execute(cmdparts);
				else if(cmdparts[0].equals("redo"))
					RecordedCommand.redoOneCommand();
				else if(cmdparts[0].equals("undo"))
					RecordedCommand.undoOneCommand();
				else if(cmdparts[0].equals("assignTable"))
					(new CmdassignTable()).execute(cmdparts);
				else
					throw new ExUnknownCommand(); 
			}
       } catch (ExUnknownCommand e) { 
        	System.out.println(e.getMessage());    
       } finally { 
        if (inFile!=null) //If it has been opened successfully, close it 
         inFile.close();    
         in.close(); 
       }
		
		in.close();
		inFile.close();
	}
}
