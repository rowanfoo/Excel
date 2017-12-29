


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import access.StockAccess;
import access.TechStrAccess;
import factory.DAOFactoryDataReport;
import factory.DAOFactoryStock;
import factory.DAOFactoryTechStr;
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




public class  TenMonthAverageFiveStr extends MyDatabase{ 
	/**
	 * 
	 * 
	 * 
	 * if today close price is lesser then last month low 
	 * and last month low is also lesser then last last month low 
	 * and last last last month low , then let me see it.
	 * 
	 * 
	 * 
	 */




	public void addDataX() throws Exception {

		// DAOFactoryDataReport

		int yes = 0;

		
		String mysql = "SELECT * FROM fortune.data where date=current_date() and twohundredchg between -0.05 and 0.05 ";
		String mysql1 ="INSERT INTO techstr (code,date ,mode,close,twohundredchg) VALUES (?,?,19,?,?)"  ; 
			PreparedStatement ps = con.prepareStatement(mysql);
			PreparedStatement ps1 = con.prepareStatement(mysql1);
		
					ResultSet rs = ps.executeQuery();
					//System.out.println("found:  1:"+ps );
					while (rs.next()) {
						ps1.setString(1, rs.getString("code"));
						ps1.setString(2, LocalDate.now().toString());
						ps1.setDouble(3, rs.getDouble("close"));
						ps1.setDouble(4, rs.getDouble("twohundredchg"));
						ps1.addBatch();
					
					}
				
				
				ps1.executeBatch();
				
				

			ps.close();
			ps1.close();

		
	}



		
	
	
	
	public void run() {
		try {

			logger.info("TenMonthAverageFiveStr run 1");

			addDataX();

			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("TenMonthAverageFiveStr Error run " + e);

		}

	}

public static void main(String[] args) {

		
	/*		
			   //get current date time with Date()
	Date mydate= new Date();
	Calendar cal = Calendar.getInstance();
	cal.setTime(mydate);
	int month = cal.get(Calendar.YEAR);	 
	System.out.println("yr"+month);		   
		    
	System.out.println("yr"+(month-1));		    
		*/  
	new TenMonthAverageFiveStr().run();
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



