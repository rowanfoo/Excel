package Calc;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import DB.MyDatabase;
import access.DataAccess;
import access.StockAccess;
import factory.DAOFactoryData;
import factory.DAOFactoryStock;
/**
 * Calculate RSI for today
 * @author rowan
 *
 */
public class CalcRSI extends MyDatabase{ 
    PreparedStatement ps1 ;
    
    private int periodLength;
    LocalDate  date;
	public CalcRSI(String  date) throws Exception {
		super();
		periodLength=14;
		String mysql1 = "update data set avgUp=?,avgDown=?,rsi=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);
		this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	
	

	}

	
	public CalcRSI() throws Exception {
		super();
		periodLength=14;
		String mysql1 = "update data set avgUp=?,avgDown=?,rsi=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);
		this.date = LocalDate.now();
	
	

	}
	
	
	
	public void addDataX(String code,double avgup, double down, double rsi, String date) throws Exception {
		ps1.setDouble(1, avgup);
		ps1.setDouble(2, down);
		ps1.setDouble(3, rsi);
		ps1.setString(4, code);
		ps1.setString(5, date);
		//System.out.println("EaddDataX "+ps1);
		ps1.executeUpdate();
		ps1.addBatch();

	}
//	String mysql ="SELECT * FROM fortune.data where code=?  and date in(?,?)order by date  desc ";

	String mysql ="SELECT * FROM data WHERE  code=? order by date desc limit 2";
	public void calcToday(String code) throws Exception {

		//String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc limit 2 ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
		//ps.setString(2,date.toString());
		//date.minusDays(1).
		
		
//		
//		if(date.getDayOfWeek() == DayOfWeek.MONDAY ){
//			ps.setString(3 ,date.minusDays(3) .toString());
//		}else{
//			ps.setString(3 ,date.minusDays(1) .toString());
//		}
		
		ResultSet rs = ps.executeQuery();

		int x = 1;
		double todayprice = 0;
		double yesterdayprice = 0;
		double avgUp = 0;
		double avgDown = 0;
		String date=null;
		while (rs.next()) {
			if (x == 1){
				todayprice = rs.getDouble("close");
				date =rs.getString("date");
			}
			if (x == 2) {
				avgUp = rs.getDouble("avgUp");
				avgDown = rs.getDouble("avgDown");
				yesterdayprice = rs.getDouble("close");
			}
			x++;

		}

		// double delta = prices.get(lastPrice).getClose()
		// - prices.get(lastPrice - 1).getClose();
		double delta = todayprice - yesterdayprice;
		//System.out.println("RSI for a end todayprice: " + todayprice + " :yest: " + yesterdayprice);
		//System.out.println("RSI for a end todayprice date : " + date);
		//System.out.println("RSI for a end avgUp: " + avgUp + " :avgDown: " + avgDown);
		double gains = Math.max(0, delta);
		double losses = Math.max(0, -delta);
		avgUp = ((avgUp * (periodLength - 1)) + gains) / (periodLength);
		avgDown = ((avgDown * (periodLength - 1)) + losses) / (periodLength);
		//System.out.println("RSI for a today  avgUp: " + avgUp + " :avgDown: " + avgDown);
		double rsi = 100 - (100 / (1 + (avgUp / avgDown)));
		//System.out.println("RSI for a end today rsi: " + rsi);
		if (Double.isNaN(rsi)) {
			rsi=0;
		}
		if(date !=null) addDataX(code,avgUp, avgDown, rsi,date); // this is when data table is empty (dont have price info)but have the stock record.
		 
		
	}
	
	
	
   
    public int getPeriodLength() {
        return periodLength;
    }

	public void run() {
		try {
			logger.info("RSIToday run ");
			
			 
			try(DAOFactoryData dt= new DAOFactoryData()) {
				
			
				ArrayList<DataAccess>arr = dt.getDate(date.toString());
				arr.forEach((d)->{
					
					try {
						calcToday(d.getCode());
					} catch (Exception e) {
						System.out.println("Error calcToday  "+e);
					}
					
					
				}) ;	
				
//				 ps1.executeBatch();
//				 con.commit();
//				ps1.close();
				
				
					 
			} catch (Exception e) {
				System.out.println("Error HttpImport run update new AVG "+e);
			}  
			 
			 
			ps1.executeBatch();
			ps1.close();
			con.commit();

			logger.info("RSIToday success");

			close();

		} catch (Exception e) {
			System.out.println("RSIToday Error run " + e);
			logger.severe("RSIToday Error run " + e);

		}

	}
    public static void main(String[] args) {
        try {
//   
//        	String arr1[]= new String[]{"02","03","06","07","08","09","10","13","14","15","16","17","20","21","22","23","24","20"};
        	//        	//String arr1[]= new String[]{"06"};
//        	String arr1[]= new String[]{"10","13","14","15","16","17","20","21","22","23","24","21","22","23","24","27","28"};			
//
//			
//        	for(String days : arr1){
//				
//				new CalcRSI("2017-11-"+days).run();
//			}
    	new CalcRSI().run();
			System.out.println("EXIT ");
        	
        	
        	
        	
        	
       	 
		} catch (Exception e) {
			System.out.println("RSIToday for a " +e);
		}
   }
    
    
}