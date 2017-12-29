



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import util.ExcelLogger;





public class  FiftyDayDistance  extends MyDatabase{

	
	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		stmt.close();
    		
			
	
	}
	
	

		
	public void addData(double percent,String date)throws Exception {
		Statement stmt= con.createStatement(); 
	
		//show all that haas fallen below - %;
		
		
		String mysql ="SELECT code,close,fifty, format(fiftychg*100,2) as fiftyDchg "+
	     "FROM  data as dd  where date='"+ date+"'" + 
	     "and fiftychg < ( (SELECT avg(fiftychg) as fiftychg  FROM fortune.data where code=dd.code and  date >= curdate() - interval 3 year and fiftychg < 0 ) *2 ) ";
		
		
		

		
	      ResultSet rs = stmt.executeQuery(mysql);
	      
	      while(rs.next()){
	    	
	    	String  sql="INSERT INTO techstr (code,date,mode,close,fifty,fiftychg) VALUES ("+ "'"+rs.getString("code")+"',"+"'"+date+"',"+ "2 ,"  +""+rs.getString("close")+","+rs.getString("fifty")+","+rs.getString("fiftyDchg") +")"; 
	    	 //logger.info("FiftyDayDistance sql 1"+sql);	
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

			logger.info("FiftyDayDistance run 1");
			addData(percent, date);

			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("FiftyDayDistance Error run " + e);

		}

	}
	
	
public static void main(String[] args) {

		
	new FiftyDayDistance().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


