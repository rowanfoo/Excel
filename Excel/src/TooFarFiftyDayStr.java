

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import access.StockAccess;

import factory.DAOFactoryDataReport;
import factory.DAOFactoryStock;
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




public class  TooFarFiftyDayStr extends MyDatabase {
	/**
	 * 
	 * 
	 * 
	 * Find when price rally so much , then we wait for it pull back.
	 * 
	 * 
	 * 
	 */

	 

	
	private void addRecord(String code,double close,double fifty,double fiftychg) throws Exception{
	
		String mysql1 ="INSERT INTO techstr (code,date ,mode,close,fifty,fiftychg) VALUES (?,?,17,?,?,?)"  ; 
		PreparedStatement ps1 =
		        con.prepareStatement(mysql1);
		ps1.setString(1, code);
		ps1.setString(2, LocalDate.now().toString());
		ps1.setDouble(3, close);
		ps1.setDouble(4, fifty);
		ps1.setDouble(5, fiftychg);
		

		
		//System.out.println("mysql:"+ps1);
		ps1.executeUpdate();
		ps1.close();
	
	}
	




	public void addDataX() throws Exception {

		// DAOFactoryDataReport

		int yes = 0;
		try (DAOFactoryDataReport dr = new DAOFactoryDataReport()) {

			ArrayList<StockAccess> stock=null;

	
			try (DAOFactoryStock st = new DAOFactoryStock()) {

				stock = st.getAllList();
			} catch (Exception e) {

			}
			
			String mysql = " SELECT * FROM fortune.data where fiftychg>0.20 and date = current_date()";
			
			Statement stmt = con.createStatement();
		
			ResultSet rs =stmt.executeQuery (mysql);
	  while (rs.next()){
		  
	  
		  addRecord(rs.getString("code"),rs.getDouble("close"),rs.getDouble("fifty"),rs.getDouble("fiftychg"));
	  
	  
	  }
 


				
				
			stmt.close();	
	
			

		} catch (Exception e) {
			System.out.println("TooFarFiftyDayStr addDataX Error :" + e);
		}

	}



	
	
	
	
	
	public void run (){
		try {
			
			
			double percent=0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			 String date=dateFormat.format( new Date());	
				System.out.println("1 ");
				
				
				 logger.info("TooFarFiftyDayStr run 1");	
				
				// addData(date);
				 addDataX();
				 close();
					
		
			
			
		} catch (Exception e) {
			System.out.println("TooFarFiftyDayStr Error run "+e);
			 logger.severe("TooFarFiftyDayStr Error run "+e);	
			
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
	new TooFarFiftyDayStr().run();
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



