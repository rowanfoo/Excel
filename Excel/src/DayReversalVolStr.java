



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
import util.FormatUtil;





public class  DayReversalVolStr  extends MyDatabase {

	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    	
    		stmt.close();
			
	
	}
	
	

		
	public void addData(String date)throws Exception {
		Statement stmt= con.createStatement(); 
	
		//show all that haas fallen below - %;
		
		
		String mysql ="select code,close,open,low,high,changepercent FROM  data  where date=? and  volume > (Avg3mth * 2 )";
		String mysql1 ="INSERT INTO techstr (code,date,mode,close,lowlow) VALUES (?,?,?,?,?)"; 
		
		PreparedStatement ps =
		        con.prepareStatement(mysql);
		
		PreparedStatement ps1 =
		        con.prepareStatement(mysql1);
		
		
		ps.setString(1, date );
		
		ResultSet rs = ps.executeQuery();
	
		
		while(rs.next()){
		
		double close=rs.getDouble("close");
		double low=rs.getDouble("low");
		double open=rs.getDouble("open");
		double high=rs.getDouble("high");
		double changepercent=rs.getDouble("changepercent");
		
		//1. day reversal  from red to green
		//2. reversal , rally from low to near green. , rally 75% from low.
		if( close > ((low+high    )/2 )    ){
					ps1.setString(1, rs.getString("code"));
					ps1.setString(2,FormatUtil.getTodayDateToString() );
					ps1.setInt(3, 11);
					ps1.setDouble(4,close);
					ps1.setString(5,low+"");
					
					ps1.executeUpdate();
			}
				
			
			
		}
			
			
		
		
		
		
		
		
	     
		
		ps.close();
		ps1.close();

		
		
	
	}
		
	      
	
	
	
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());

			System.out.println("1 ");

			logger.info("DayReversalVolStr run 1");
			addData(date);
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("DayReversalVolStr Error run " + e);

		}

	}
	
	
public static void main(String[] args) {

		
	new DayReversalVolStr().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
	LocalDate week = LocalDate.now();
	LocalDate month;
//	for( int x =0;x<15;x++){
	//	month = week.minusMonths(x);
		//System.out.println(" DayReversalVolStr month :"+month.getMonthValue()+":"+month.getYear() );
	//}
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


