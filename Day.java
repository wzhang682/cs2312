public class Day implements Cloneable, Comparable<Day> {
	 
	private int year;
	private int month;
	private int day;
	private static final String MonthNames="JanFebMarAprMayJunJulAugSepOctNovDec";
	//Constructor
	public Day(int y, int m, int d) {
		this.year=y;
		this.month=m;
		this.day=d;		
	}
	public void set(String sDay) //Set year,month,day based on a string like 01-Mar-2024 
	{   
	 String[] sDayParts = sDay.split("-"); 
	 this.day = Integer.parseInt(sDayParts[0]); //Apply Integer.parseInt for sDayParts[0]; 
	 this.year =Integer.parseInt(sDayParts[2]);  
	 this.month = MonthNames.indexOf(sDayParts[1])/3+1; 
	} 
	public Day(String Day){
		set(Day);
	}
	@Override
	public String toString(){
		return day+"-"+
		MonthNames.substring((month-1)*3,(month)*3)+"-"+year;
	}
	@Override
	public Day clone(){
		Day copy=null;
		try{
			copy=(Day)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace();
		}
		return copy;

	}
	// check if a given year is a leap year
	static public boolean isLeapYear(int y)
	{
		if (y%400==0)
			return true;
		else if (y%100==0)
			return false;
		else if (y%4==0)
			return true;
		else
			return false;
	}
	
	// check if y,m,d valid
	static public boolean valid(int y, int m, int d)
	{
		if (m<1 || m>12 || d<1) return false;
		switch(m){
			case 1: case 3: case 5: case 7:
			case 8: case 10: case 12:
					 return d<=31; 
			case 4: case 6: case 9: case 11:
					 return d<=30; 
			case 2:
					 if (isLeapYear(y))
						 return d<=29; 
					 else
						 return d<=28; 
		}
		return false;
	}
    @Override
	
    public int compareTo(Day otherDay) {
        if (this.year != otherDay.year) {
            return Integer.compare(this.year, otherDay.year);
        }
        
        if (this.month != otherDay.month) {
            return Integer.compare(this.month, otherDay.month);
        }
        
        return Integer.compare(this.day, otherDay.day);
    }

	@Override
	public boolean equals(Object o){
		if(o==null){return false;}
		if(o.getClass()!=this.getClass()){return false;}
		Day d2=(Day)o;
		return this.year==d2.year&&this.month==d2.month&&this.day==d2.day;
	}
	@Override
	public int hashCode(){
		return year*100000+month*100+day;
	}
}