

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import util.ExcelLogger;
/**
 * 
 * find all vol * 60%
 * 1. large vol small drop
 * 2.large vol break throug.
 * 3. large vol small gain - dying
 * 
 * 
 * 
 * @author rowan
 *
 */




public class  GreaterVolAvgStr  extends MyDatabase{

	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    
    		stmt.close();
    		
			
	
	}
	
	

		
	public void addData(double percent,String date)throws Exception {
		Statement stmt= con.createStatement(); 
	
		//show all that haas fallen below - %;
		
		
		String mysql ="select code,close,volume ,Avg3mth,changePercent   "+
	     "FROM  data  where date='"+ date+"' and  volume > (Avg3mth * 2 )"; 
	     
	
		
		
		
	      ResultSet rs = stmt.executeQuery(mysql);
	      
	      while(rs.next()){
	    	
	    	 
	    	  
	    	  if( rs.getDouble("Avg3mth")==0    )continue;
	    	 	
	    	  
	    	String  sql="INSERT INTO techstr (code,date,mode,close,volume,3mthVol,changePercent) VALUES ("+ "'"+rs.getString("code")+"',"+"'"+date+"',"+ "3 ,"  +""+rs.getString("close")+","+rs.getString("volume")+","+rs.getString("Avg3mth") +","+rs.getString("changePercent") +")"; 
	    	
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

			logger.info("GreaterVolAvgStr run 1");

			addData(percent, date);
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("GreaterVolAvgStr Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	new GreaterVolAvgStr().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


