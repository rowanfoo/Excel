




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




public class  SixMonthLowStr extends MyDatabase{
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


	
	
	
	 SixMonthLowStr(){
		
		

	}
	
	 

	 

	
	private void addRecord(String code,double close,double sixMonth ) throws Exception{
		Calendar cal = Calendar.getInstance();
		String date = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-1"; // i just want save day as same , run this monthly , prevent this by running twice in 1 month , as run second will trigger constraint error
		String mysql1 ="INSERT INTO techstr (code,date ,mode,close,sixMonthPrice) VALUES (?,?,13,?,?)"  ; 
		PreparedStatement ps1 =
		        con.prepareStatement(mysql1);
		ps1.setString(1, code);
		ps1.setString(2, LocalDate.now().toString());
		ps1.setDouble(3, close);
		ps1.setDouble(4, sixMonth);

		
		System.out.println("mysql:"+ps1);
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
			
			String mysql = "   select * from "+
					"  ( SELECT  min(close)as closeM  FROM fortune.data where date between subdate(current_date   , Interval 5 month)  and subdate(current_date   , Interval 1 day)  and code=? ) as t,"
					+ " (SELECT  close  FROM fortune.data where date = current_date and code=? ) as s "
					+" where close < closeM";
  
  
 
			PreparedStatement ps = con.prepareStatement(mysql);
	

	
			for (StockAccess code : stock) {
			
				
					ps.setString(1, code.getCode());
					ps.setString(2, code.getCode());
			
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
					
						addRecord(code.getCode(),rs.getDouble("close"),rs.getDouble("closeM"));

						
						
					}
				
				
				
				}
				
				
				
			ps.close();	
	
			

		} catch (Exception e) {
			System.out.println("SixMonthLowStr addDataX Error :" + e);
		}

	}



	
	
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			System.out.println("1 ");

			logger.info("SixMonthLowStr run 1");

			// addData(date);
			addDataX();
			// addData("2016-10-24");
			close();

		} catch (Exception e) {
			System.out.println("SixMonthLowStr Error run " + e);
			logger.severe("SixMonthLowStr Error run " + e);

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
	//new INIT();
	
	new SixMonthLowStr().run();
		    
	
	
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



