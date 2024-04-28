import java.util.ArrayList;

public class RStateAllocated implements RState {
    private ArrayList<Table> tables;

    public RStateAllocated(ArrayList<Table> tables) {
        this.tables = tables;
    }
    public ArrayList<Table> getTables(){
        return tables;
    }

    @Override
    public String getStatus() {
        String assignTables="";
        for (int i = 0; i < tables.size(); i++) {
            assignTables+=tables.get(i).getTableCode()+" ";
        }
        return "Table assigned: " + assignTables;
    }
}