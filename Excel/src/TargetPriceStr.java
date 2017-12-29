

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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




public class  TargetPriceStr  extends MyDatabase{
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

	
	public void executeNormandy() throws Exception {

		String mysql = "SELECT stock.code,normandyPrice,close FROM stock join data on stock.code=data.code where wishlist='Y' and normandyPrice>1 and (close >= normandyPrice or   close >= (normandyPrice*0.96) )and  data.date=?";

		PreparedStatement ps = con.prepareStatement(mysql);

		ps.setString(1, LocalDate.now().toString());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			addRecord(rs.getString("code"), rs.getDouble("close"), rs.getDouble("normandyPrice"),15);
		}

		ps.close();

	}

	public void executeBuy() throws Exception {

		String mysql = "SELECT stock.code,whenBuyPrice,close FROM stock join data on stock.code=data.code where wishlist='Y' and (close <= whenBuyPrice or   close <= (whenBuyPrice*0.96) )and  data.date=?";


		PreparedStatement ps = con.prepareStatement(mysql);

		ps.setString(1, LocalDate.now().toString());

		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			addRecord(rs.getString("code"), rs.getDouble("close"), rs.getDouble("whenBuyPrice"),16);
		}

		ps.close();

	}
	
	
	private void addRecord(String code, double close, double triggerPirce,int mode) throws Exception {

		String mysql1 = "INSERT INTO techstr (code,date ,mode,close,sixMonthPrice ) VALUES (?,?,?,?,?)";
		PreparedStatement ps1 = con.prepareStatement(mysql1);
		ps1.setString(1, code);
		ps1.setString(2, LocalDate.now().toString());
		ps1.setInt(3, mode);
		ps1.setDouble(4, close);
		ps1.setDouble(5, triggerPirce);

		// System.out.println("mysql:"+ps1);
		ps1.executeUpdate();
		ps1.close();

	}




	
	
	public void run() {
		try {

			logger.info("TargetPriceStr run 1");
			executeNormandy();
			executeBuy();

			logger.info("TargetPriceStr :   START COMMIT ");
			close();

		} catch (Exception e) {
			System.out.println("Error TargetPriceStr run " + e);
			logger.severe("TargetPriceStr Error run " + e);

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
	new TargetPriceStr().run();
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



