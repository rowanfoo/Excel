

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
import access.TechStrAccess;

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




public class  MDownSixMonthBelowFifty  extends MyDatabase{
	/**
	 * 
	 * 
	 * 
	 * if today close price is lesser then last month low 
	 * and last month low is also lesser then last last month low 
	 * and last last last month low , then let me see it.
	 * down 6 month out of 8 months.
	 * 
	 * 
	 * 
	 */

	
	public void execute()throws Exception {
		//************************ THINGS to Do
		// 1. 6month + 1 month , always lead by 1 month .
		// if today > 140 , then 308 - 140 then should be 168d
		//if 0 , then 160-20d
		//if-ve then if -1 , then cover to 120-20 , until it is >-20 , convert to +ve.
		//if now is Nov then the down day should be up to Oct 1.
		//if now is jun , 6 month then just remove 1 month off
		//if now is mar , then discard all and just use 3 mths
		


Date mydate= new Date();
Calendar cal = Calendar.getInstance();
cal.setTime(mydate);

	
		int currday =0;
		// i want to find 6 month down but with 1 month spare.


    		String mysql =" select date,code,count(code) as no,fiftychg from data where fiftychg<=0 and  date>daysaddnowk(current_date(),160) group by code having no>=120"  ; 
    		PreparedStatement ps =
    		        con.prepareStatement(mysql);
    		
    	
    		
    		
    		//System.out.println("ok preparedStatement :    "+ps);	
    		ResultSet rs = ps.executeQuery();
    		
    		boolean run =false;
    		String mysql1 ="INSERT INTO techstr (code,date ,fiftycount,mode) VALUES (?,?,?,10)"  ; 
    		PreparedStatement ps1 =
    		        con.prepareStatement(mysql1);
    		String date = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-1"; // i just want save day as same , run this monthly , prevent this by running twice in 1 month , as run second will trigger constraint error
    		
    		 while(rs.next()){
    		    	run=true;
    					
    		    			
    		    		ps1.setString(1, rs.getString("code"));
    		    		ps1.setString(2, date);
    		    		ps1.setLong(3, rs.getLong("no") );
    					ps1.addBatch();
    		    		
    		    		
    		    		
    				}
    				if(run)ps1.executeBatch();
    				ps1.close();
    			
    			
    		
    		
    	    ps.close();
	
	}
	
	

	
		
	      
	
	

	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			System.out.println("1 ");

			logger.info("DownSixMonthBelowFifty run 1");

			execute();



			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("DownSixMonthBelowFifty Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	new MDownSixMonthBelowFifty().run();	    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



