

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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




public class  FallWithLowVolumeStr  extends MyDatabase {

	
	public void execute(String code,String date,String changePercent,String volume,String Avg3mth )throws Exception {
		
		
			
		//	Statement stmt=				 con.createStatement();  	
			//stmt.executeUpdate(sql);
    		//System.out.println("ok execute :    ");	
    		
    		
    		String mysql ="INSERT INTO techstr (code,date,mode,volume,3mthVol,changePercent) VALUES (?,?,7,?,?,?)"  ; 
    		PreparedStatement ps =
    		        con.prepareStatement(mysql);
    		ps.setString(1, code);
    		ps.setString(2, date);
    		ps.setDouble(3, Double.parseDouble(volume));
    		ps.setDouble(4, Double.parseDouble(Avg3mth));
    		ps.setDouble(5, Double.parseDouble(changePercent));
    		
    	//	System.out.println("ok preparedStatement :    "+ps);	
    		ps.executeUpdate();
    	    ps.close();
	
	}
	
	

		
	public void addData(double percent,String date)throws Exception {
//	public void addData(double percent,java.sql.Date date)throws Exception {
		
	
		//show all that haas fallen below - %;
		
		
		String mysql ="select code,changePercent,volume ,Avg3mth FROM  data  where date=? and  volume < (Avg3mth * 0.6 ) and changePercent < -0.04";
	     
	
		
		

		PreparedStatement ps =
		        con.prepareStatement(mysql);
		ps.setString(1, date);
		
	//	preparedStatement.setDate(1,date);
		
		System.out.println("ok preparedStatement :    "+ps);	
				
	      ResultSet rs = ps.executeQuery();
	  
	      while(rs.next()){
	    	
	    	
	    	  if( rs.getDouble("Avg3mth")==0    )continue;
	    	 	
	    	  execute(rs.getString("code"),date,rs.getString("changePercent"),rs.getString("volume"),rs.getString("Avg3mth"));
	    	  
	      }
	      
	      
	      ps.close();
	}
		
	      
	
	
	
	
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			System.out.println("1 ");

			logger.info("FallWithLowVolumeStr run 1");

			addData(percent, date);
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("FallWithLowVolumeStr Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	new FallWithLowVolumeStr().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


