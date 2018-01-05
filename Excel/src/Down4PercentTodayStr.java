



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import org.apache.poi.util.SystemOutLogger;

import com.mysql.jdbc.PreparedStatement;

import DB.MyDatabase;
import util.ExcelLogger;
import util.FormatUtil;





public class  Down4PercentTodayStr  extends MyDatabase{

	
	String  sql="INSERT INTO techstr (code,date,mode,close,fifty,fiftychg,changePercent) VALUES (?,?,?,?,?,?,?)"; 
	
	java.sql.PreparedStatement ps;
	
	Down4PercentTodayStr() throws SQLException{
		ps=	con.prepareStatement(sql);	
	}

	
	

		
	public void addData(String date)throws Exception {
		Statement stmt= con.createStatement(); 
	
		//show all that haas fallen below - %;
		
		
		String mysql ="SELECT code,close,format(fifty,2) as fiftyd,changePercent , format(fiftychg*100,2) as fiftyDchg   "+
	     "FROM  data  where date='"+ date+"' and changePercent < -0.04"; 
	     
		
	      ResultSet rs = stmt.executeQuery(mysql);
	      
	      while(rs.next()){
	    	ps.setString(1, rs.getString("code"));
	    	ps.setString(2, date);
	    	ps.setInt(3, 9);

	    	ps.setDouble(4, rs.getDouble("close"));
	    	ps.setDouble(5, rs.getDouble("fiftyd"));
	    	ps.setDouble(6, rs.getDouble("fiftyDchg") );
	    	ps.setDouble(7, rs.getDouble("changePercent") * 100);
	    	
	   
	    	ps.addBatch();
	      }
	      
	      System.out.println("DONE :    ");	
	  	stmt.close();  
	      System.out.println("FINISH :    ");	
	
	}
		
	      
	
	
	
	
	
	
	public void run() {
		try {

		
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());

			System.out.println("1 ");

			logger.info("FiftyDayDistance run 1");
			addData(date);
			ps.executeBatch();
			close();

			// addData(percent,"2016-10-21");

			// test on one sheet firts
			// sheet = workbook.getSheetAt(0);
			// excel();

			// workbook.getNumberOfSheets()

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("Down4PercentTodayStr Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	try {
		new Down4PercentTodayStr().run();
	
	//double d = 0.6613636363636364;
	
		//	System.out.println("Down4PercentTodayStr :    "+FormatUtil.roundDouble(  d));	
	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		System.out.println("Down4PercentTodayStr :    "+e);	
		
	}
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


