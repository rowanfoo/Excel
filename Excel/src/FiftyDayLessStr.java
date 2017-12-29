






import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import util.ExcelLogger;





public class  FiftyDayLessStr  extends MyDatabase{

	
	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		//System.out.println("ok execute :    ");	
    		
    		stmt.close();
			
	
	}
	
	

		
	public void addData(double percent,String date)throws Exception {
		Statement stmt= con.createStatement(); 
	
		//show all that haas fallen below - %;
		/**
		 * here we want to find all company who have gap down too much ,too far for it 50d average , 
		 * i just AVG out it 3 year distance from 50d.
		 */
		
		String mysql ="SELECT code, close , fifty,   format(fiftychg  *100,2) as fiftyDchg FROM data where fiftychg < -"+percent +"and date='"+date+"' order by fiftychg  ASC";

		
	      ResultSet rs = stmt.executeQuery(mysql);
	      
	      while(rs.next()){
	    	
	    	String  sql="INSERT INTO techstr (code,date,mode,close,fifty,fiftychg) VALUES ("+ "'"+rs.getString("code")+"',"+"'"+date+"',"+ "1 ,"  +""+rs.getString("close")+","+rs.getString("fifty")+","+rs.getString("fiftyDchg") +")"; 
	    //	System.out.println("ok execute sql:    "+sql);	
	    	execute( sql);
	      }
	      
	      
	  	stmt.close();
	}
		
	      
	
	
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());

			System.out.println("1 ");

			logger.info("FiftyDayLessStr run 1");
			addData(percent, date);
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("FiftyDayLessStr Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	new FiftyDayLessStr().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


