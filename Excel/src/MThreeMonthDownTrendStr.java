



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




public class  MThreeMonthDownTrendStr extends MyDatabase{ 
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

	public void execute(String code,String price )throws Exception {
		
		
			
		//	Statement stmt=				 con.createStatement();  	
			//stmt.executeUpdate(sql);
    		//System.out.println("ok execute :    ");	


Date mydate= new Date();
Calendar cal = Calendar.getInstance();
cal.setTime(mydate);
int month = cal.get(Calendar.MONTH);

double month1=0;
double month2=0;
double month3=0;
double myprice = Double.parseDouble(price)  ;  		
int count =0;
    		String mysql ="SELECT date, min(close)as closeM  FROM fortune.data where MONTH(date) in( ?,?,?) and code=? and Year(date)=?  group by month(date) order by date desc"  ; 
    		PreparedStatement ps =
    		        con.prepareStatement(mysql);
    		
    		ps.setInt(1, month);
    		ps.setInt(2, month-1);
    		ps.setInt(3, month-2);
    		ps.setString(4, code);
    		ps.setInt(5,cal.get(Calendar.YEAR));
    		
    		System.out.println("ok preparedStatement :    "+ps);	
    		ResultSet rs = ps.executeQuery();
    		
    		 while(rs.next()){
    		    	
    			//System.out.println("ok ok result :    "+code+":"+rs.getString("date")+rs.getString("closeM"));	  	
   	    	 if(count ==0)month1=Double.parseDouble(rs.getString("closeM"));
   	    	if(count ==1)month2=Double.parseDouble(rs.getString("closeM"));
   	    	if(count ==2)month3=Double.parseDouble(rs.getString("closeM"));
   		 count++;
    	    	 
   	    	 	
   	    	  
   	      }
   	      
    		// System.out.println("ok preparedStatement :1  M1   "+month1);	
    		 //System.out.println("ok preparedStatement :1  M2  "+month2);	
    			System.out.println("REALLY !!!!!!FOUND !!!!! myprice   "+myprice);
    			System.out.println("REALLY !!!!!!FOUND !!!!! month1   "+month1);
    		if(myprice < month1 ){
    			System.out.println("REALLY !!!!!!FOUND !!!!! ok   ");
    			if( month1 < month2){
    				//System.out.println("YES YES FOUND !!!!!  "+code);	
    				
    				if(month2<month3){
    					System.out.println("REALLY !!!!!!FOUND !!!!!  "+code);
    					String date = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-1"; // i just want save day as same , run this monthly , prevent this by running twice in 1 month , as run second will trigger constraint error
    					String mysql1 ="INSERT INTO techstr (code,date ,mode) VALUES (?,?,8)"  ; 
    		    		PreparedStatement ps1 =
    		    		        con.prepareStatement(mysql1);
    		    		ps1.setString(1, code);
    		    		ps1.setString(2, date);
    		    		System.out.println("mysql:"+ps1);
    		    		ps1.executeUpdate();
    		    		ps1.close();
    		    		
    		    		
    		    		
    				}
    				
    			}
    			
    			
    		}
    		
    	    ps.close();
	
	}
	
	private void addRecord(String code) throws Exception{
		Calendar cal = Calendar.getInstance();
		String date = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH)+1)+"-1"; // i just want save day as same , run this monthly , prevent this by running twice in 1 month , as run second will trigger constraint error
		String mysql1 ="INSERT INTO techstr (code,date ,mode) VALUES (?,?,8)"  ; 
		PreparedStatement ps1 =
		        con.prepareStatement(mysql1);
		ps1.setString(1, code);
		ps1.setString(2, date);
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
			String mysql = "SELECT date, min(close)as closeM  FROM fortune.data where MONTH(date) in(?) and code=? and Year(date)=? ";
			PreparedStatement ps = con.prepareStatement(mysql);
			LocalDate week = LocalDate.now();
			LocalDate month;
			double d1 = 0;
			double d2 = 0;

			double[] price;
			
			for (StockAccess code : stock) {
				price = new double[8];
				
				for (int x = 0; x < 8; x++) {
					month = week.minusMonths(x);
					ps.setInt(1, month.getMonth().getValue());
					ps.setString(2, code.getCode());
					ps.setInt(3, month.getYear());

					ResultSet rs = ps.executeQuery();
					//System.out.println("found:  1:"+ps );
					while (rs.next()) {
						price[x] = rs.getDouble("closeM");
					//	System.out.println("price :"+price[x] );

					}
				
				
				
				}
				
				
				
				
				//System.out.println("found:  process" );
				int count = 0;
			//	System.out.println("found:  process:"+price.length );
					for (int y = 0; y < price.length; y++) {
						count = y + 1;
						if (count >= price.length)
							break;
					//	System.out.println("found:  process:"+y+":"+count );
						if (price[y] < price[count])
							yes++;

					}
					//System.out.println("found:" + code+"::"+ yes);
					if (yes > 6) {
						System.out.println("found:" + code);
						 addRecord(code.getCode() );
					}

					yes = 0;

				}

			ps.close();

		} catch (Exception e) {
			System.out.println("Error :" + e);
		}

	}



		
	public void addData(String date)throws Exception {
		
	
		//show all that haas fallen below - %;
		
		
		String mysql ="select code,close FROM  data  where date=?";
	     
	
		
		

		PreparedStatement ps =
		        con.prepareStatement(mysql);
		ps.setString(1, date);
		

		
		//System.out.println("ok preparedStatement :    "+ps);	
				
	      ResultSet rs = ps.executeQuery();
	  
	      while(rs.next()){
	    	
	    	
	    	 
	    	 	
	    	  execute (rs.getString("code"), rs.getString("close") );
	    	  
	      }
	      
	      
	      ps.close();
	}
		
	      /**
	       * check if user has previously added data , if yes then delete and update with new data.
	       * @throws Exception
	       */
	
	public void checkData() throws Exception {
		//refresh data
		try(DAOFactoryTechStr dao = new DAOFactoryTechStr()) {
			ArrayList<TechStrAccess> arr =  new ArrayList<TechStrAccess>() ;
	         arr = dao.getTechStrListByMonthMode(LocalDate.now().getMonthValue() );
	         if(arr.size()>0)dao.deleteThisMonthTechStrListByMonthMode(); 

	      } catch (Exception e) {	
	    	  System.out.println("SpecialModeJSP Refesh ERROR :"+e); 
	    	  throw new Exception (e);
	    	  
	    	  }

	}
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			System.out.println("1 ");

			logger.info("ThreeMonthDownTrendStr run 1");
			checkData();
			// addData(date);
			addDataX();
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("ThreeMonthDownTrendStr Error run " + e);

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
	new MThreeMonthDownTrendStr().run();
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



