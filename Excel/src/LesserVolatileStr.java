

import java.math.BigDecimal;
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
import access.DataReport;

import factory.DAOFactoryDataReport;
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








public class  LesserVolatileStr  extends MyDatabase{

	
	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    	
    		stmt.close();
			
	
	}
	
	
	public ArrayList<String> getAllCode() throws Exception {
		ArrayList<String> arr = new ArrayList<String>();

		String sql = "select code from stock";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			arr.add(rs.getString("code"));
		}

		stmt.close();
		return arr;

	}
	private static BigDecimal truncateDecimal(double x,int numberofDecimals)
	{
	    if ( x > 0) {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
	    } else {
	        return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
	    }
	}
	public void addDataX() throws Exception {
	
		//DAOFactoryDataReport
		int count=0;
		int yes=0;
		String mysql1 = "INSERT INTO techstr (code,date,mode,lowlow) VALUES (?,?,18,?)";
		PreparedStatement ps1 = con.prepareStatement(mysql1);

		String vars ="";
		try (DAOFactoryDataReport dr = new DAOFactoryDataReport()  ){
			 ArrayList<String> arr = getAllCode();
			 
			 for(String code:arr){
				 ArrayList<DataReport> reports = dr.getPriceVariance(code);
				 if(reports.size()<7  ) break;

				 for(int x=0;x<7;x++){
					 count=x+1;
					 if( count >7)break;
					 
					 DataReport dt1 = reports.get(x);
					 DataReport dt2 = reports.get(count);
					 //System.out.println("codes dt1 :"+dt1.getMonthVariance() +":"+dt2.getMonthVariance());
					 vars= vars + dt1.getPriceVariance()+":"+ dt2.getPriceVariance();  
					 if(dt1.getPriceVariance() < dt2.getPriceVariance()  ){
						yes++; 
						//System.out.println("codes :"+code+":"+yes);
						
//						vars = vars+Math.floor((dt1.getPriceVariance()*1000)/1000) +","+ Math.floor((dt2.getPriceVariance()*1000/100))  ;
						 
						 vars = vars+truncateDecimal(dt1.getPriceVariance(),2)+","+truncateDecimal(dt2.getPriceVariance(),2);
						 
						
					 }
					 
					 
				 }
				
				 if(yes>5 ){
					 System.out.println("found:"+code+":"+vars);
					 
						ps1.setString(1, code);
						ps1.setString(2,LocalDate.now().toString()  );
						ps1.setString(3, vars);
						ps1.addBatch();
					
					 
				 }
				 //System.out.println("codes :"+arr.size());
				 yes=0;
				 vars="";
			 }
			
				ps1.executeBatch();	
			
		} catch (Exception e) {
			System.out.println("Error :"+e);
		}
		
		
	}
	
	
	public void addData() throws Exception {
		Statement stmt = con.createStatement();

		// show all that haas fallen below - %;

		String mysql = "select code ,date, VARIANCE(changePercent)as var from data where month(date) in(7,8,9,10,11) and code=?   group by Month(date)  order by  date asc";
		String mysql1 = "INSERT INTO techstr (code,date,mode,lowlow) VALUES (?,?,18,?)";

		PreparedStatement ps = con.prepareStatement(mysql);
		PreparedStatement ps1 = con.prepareStatement(mysql1);

		ArrayList<String> arr = getAllCode();
		String lowlow="";
		int mycount=0;
		for (String code : arr) {

			LocalDate date = LocalDate.now();
			int month = date.getMonth().getValue();
			ps.setString(1, code);
			
			/*
			ps.setInt(1, month);
			ps.setString(2, code);

			ps.setInt(3, month - 1);
			ps.setString(4, code);

			ps.setInt(5, month - 2);
			ps.setString(6, code);

			ps.setInt(7, month - 3);
			ps.setString(8, code);

			ps.setInt(9, month - 4);
			ps.setString(10, code);

			ps.setInt(11, month - 5);
			ps.setString(12, code);
*/
			mycount++;
			
			// System.out.println("MySql:"+ps);
			
			ResultSet rs = ps.executeQuery();

			int count = 0;
			double month1 = 0;
			double month2= 0;
			double month3 = 0;
			double month4 = 0;
			double month5 = 0;
			while (rs.next()) {

			
				
				
				lowlow = "code:"+code+":"+month1 + "," + month2 + "," + month3 + "," + month4 + "," + month5  ;
				if(count==0  ){
					month1 =rs.getDouble("var");
					count++;
				}
				if(count==1  ){
					month2 =rs.getDouble("var");
					count++;
				}
				if(count==2  ){
					month3 =rs.getDouble("var");
					count++;
				}
				if(count==3  ){
					month4 =rs.getDouble("var");
					count++;
				}
				if(count==4  ){
					month5 =rs.getDouble("var");
				}
				
				
				
	

			}
			count = 0;
			int variant=0;
			if(month1<month2){
				variant++;
			}
			if(month2<month3){
				variant++;
			}
		
			if(month3<month4){
				variant++;
			}
			
			
			
			
			//System.out.println("FOUND: lowlow" + lowlow);
			variant=0;
			if (variant > 1 )
				System.out.println("FOUND:" + code);
			
		}
/*
		if (avg1 < 0.05 ||avg1 > -0.05 )
			count = count + 2;
		if (avg2 < 0.05 ||avg2 > -0.05 )
			count = count + 2;
		if (avg3 < 0.05 ||avg3 > -0.05 )
			count++;
		if (avg4 < 0.05 ||avg4 > -0.05 )
			count++;
		if (avg5 < 0.05 ||avg5 > -0.05 )
			count++;

		if (count > 5 )
			System.out.println("FOUND:" + code);

		ps1.setString(1, code);
		ps1.setString(2, FormatUtil.getTodayDateToString());
		ps1.setString(3, lowlow);
	*/
		//	ps1.addBatch();
		
	//	ps1.executeBatch();
		ps.close();
		ps1.close();
		System.out.println("FOUND:" + mycount);

	}
	      
	
	
	
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());

			System.out.println("1 ");

			logger.info("LesserVolatileStr run 1");
			// addData();
			addDataX();

			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("LesserVolatileStr Error run " + e);

		}

	}

public static void main(String[] args) {

		
	new LesserVolatileStr().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}





