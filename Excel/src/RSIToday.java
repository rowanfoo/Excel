

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import DB.MyDatabase;
import access.StockAccess;
import factory.DAOFactoryStock;
/**
 * Calculate RSI for today
 * @author rowan
 *
 */
public class RSIToday extends MyDatabase{ 
    PreparedStatement ps1 ;
    PreparedStatement ps2 ;
    private int periodLength;
	public RSIToday() throws Exception {
		super();
		periodLength=14;
		String mysql1 = "update data set avgUp=?,avgDown=?,rsi=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);
	
		mysql1 = "INSERT INTO techstr (code,date ,mode,changePercent) VALUES (?,?,24,?)";
		ps2 = con.prepareStatement(mysql1);
		
		

	}

	public void addDataX(String code,double avgup, double down, double rsi, String date) throws Exception {
		ps1.setDouble(1, avgup);
		ps1.setDouble(2, down);
		ps1.setDouble(3, rsi);
		ps1.setString(4, code);
		ps1.setString(5, date);
		ps1.executeUpdate();
		ps1.addBatch();

	}

  
	public void calcToday(String code) throws Exception {

		String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc limit 2 ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
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
		 
		 if(rsi>0 && rsi<25 ){
			 addTechStr(code, rsi);
		 }
		 
		 
	}
	
	
	public void addTechStr(String code,double rsi) throws Exception {
			ps2.setString(1, code);
			ps2.setString(2, LocalDate.now().toString());
			ps2.setDouble(3, rsi);
			ps2.addBatch();
	}
	
   
    public int getPeriodLength() {
        return periodLength;
    }

	public void run() {
		try {
			logger.info("RSIToday run ");
			
			 
			 try (DAOFactoryStock dt = new DAOFactoryStock()) {
		       	 Hashtable  <String ,StockAccess> arr  =  dt.getAllHash();
		       	
		       	  for (Enumeration<String> e = arr.keys();	 e.hasMoreElements();){
		       		 calcToday( e.nextElement()) ;
		       	  }
		       	      
		       	System.out.println("RSIToday FINISH :");
		    		
		    		
				} catch (Exception e) {
					System.out.println("Err RSIToday:"+e);
				}
			 
			 
			 
			ps1.executeBatch();
			ps1.close();
			ps2.executeBatch();
			ps2.close();


			logger.info("RSIToday success");

			close();

		} catch (Exception e) {
			System.out.println("RSIToday Error run " + e);
			logger.severe("RSIToday Error run " + e);

		}

	}
    public static void main(String[] args) {
        try {
   
        	new RSIToday().run();
       	 
		} catch (Exception e) {
			System.out.println("RSIToday for a " +e);
		}
   }
    
    
}