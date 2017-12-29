
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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




public class  CloseVol  extends MyDatabase{
    PreparedStatement ps1 ;
	CloseVol()throws Exception  {
		String mysql1 = "INSERT INTO techstr (code,date ,mode,fiftycount,volume) VALUES (?,?,26,?,?)";
		ps1 = con.prepareStatement(mysql1);
	}
	public void execute()throws Exception {
		
		String sql ="select code,volume ,closevol FROM  data  where date=curdate() and  (closevol/volume) > 0.3";
			
			Statement stmt=				 con.createStatement();  
			System.out.println("mysql:"+stmt);
			ResultSet st = stmt.executeQuery(sql);
			while(st.next()){
				addDataX(st.getString("code"),LocalDate.now().toString(),st.getDouble("closevol"), st.getDouble("volume"));
				
			}
			
    		stmt.close();
    		
			
	
	}
	
	

	public void addDataX( String date , String code,double closevol, double vol) throws Exception {
		System.out.println("add datax ");
		ps1.setString(1, code);
		ps1.setString(2, date);
		ps1.setDouble(3, closevol);
		ps1.setDouble(4, vol);
	
		
		System.out.println("mysql   ps1:"+ps1);
		ps1.addBatch();

	}
	
	
	
	
	
	
	public void run() {
		try {
			logger.info("CloseVol :   run ");
			execute();
			ps1.executeBatch();
			ps1.close();
			close();

		} catch (Exception e) {
			System.out.println("CloseVol Error run " + e);
			logger.severe("CloseVol Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	
			
			   //get current date time with Date()
			 
			   
		    
		    try {
		    	new CloseVol().run();
			} catch (Exception e) {
				// TODO: handle exception
			}
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


