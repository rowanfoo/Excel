

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Logger;

import DB.MyDatabase;
import access.StockAccess;
import access.TechStrAccess;
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




public class  DownGreaterFortyPercent  extends MyDatabase{

	
	public void addTechStr(String code,double max , double min,long percent ) throws Exception {
		//System.out.println("--------- MDownGreaterFortyPercent code  :"+code);
		String mysql1 = "INSERT INTO techstr (code,date ,mode,Notes) VALUES (?,?,28,?)";
		PreparedStatement ps = con.prepareStatement(mysql1);
		ps.setString(1, code);
		ps.setString(2, LocalDate.now().toString());
		ps.setString(3, "fall from "+max+" to "+min +" % fall "+ percent );
		ps.executeUpdate();
		ps.close();
	}
	
	private void find(String code)throws Exception{
		String mysql =" select (select low from data where code=?  and date=curdate() ) as t,(SELECT max(low) as z FROM fortune.data where date > curdate()-interval 12 month  and  code=?) as y"  ; 
		PreparedStatement ps =     con.prepareStatement(mysql);
		ps.setString(1, code);
		ps.setString(2, code);
		ResultSet rs = ps.executeQuery();
		 while(rs.next()){
			 double max = rs.getDouble("y");
			 double min = rs.getDouble("t");
			 double result = (max-min)/max;
			 if(result>0.3)addTechStr(code,max,min,Math.round(result*100));
			 
			 
		    		
		    		
		}
		 
		 ps.close();
	}
	
	public void execute()throws Exception {
		
		 try (DAOFactoryStock dt = new DAOFactoryStock()) {
			 Hashtable  <String ,StockAccess> stocks =  dt.getAllHash();
	       	
	       	  for (Enumeration<String> e = stocks .keys();	 e.hasMoreElements();){
	       		find (e.nextElement() );
	       	  }
	       	      

	    
	    		
			} catch (Exception e) {
				System.out.println("Err:"+e);
			}
		 

    		
    		
	}
	
	

	
		
	      
	
	

	
	
	public void run() {
		try {

		

			execute();
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("DownSixMonthBelowFifty Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	new DownGreaterFortyPercent().run();	    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



