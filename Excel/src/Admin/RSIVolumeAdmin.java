package Admin;


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

public class RSIVolumeAdmin extends MyDatabase{ 
    private int periodLength;
    private final Stack<Averages> avgList;
    private final ArrayList<Price> prices;
    PreparedStatement ps1 ;
String mycode;

	public RSIVolumeAdmin(int periodLength) throws Exception {
		super();
		this.periodLength = periodLength;
		avgList = new Stack<Averages>();
		
	
		prices = new ArrayList<Price>();
		
		String mysql1 = "update data set avgUpvol=?,avgDownvol=?,rsivol=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);

	}
	
	public RSIVolumeAdmin(int periodLength,String code) throws Exception {
		super();
		this.periodLength = periodLength;
		avgList = new Stack<Averages>();
		
	
		prices = new ArrayList<Price>();
		
		String mysql1 = "update data set avgUpvol=?,avgDownvol=?,rsivol=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);
		mycode = code;

	}
	
	public void addDataX(double avgup, double down, double rsi, String date,String code) throws Exception {

		ps1.setDouble(1, avgup);
		ps1.setDouble(2, down);
		ps1.setDouble(3, rsi);
		ps1.setString(4, code);
		ps1.setString(5, date);
		
		ps1.addBatch();

	}

    private void  getALLPrices(String code)throws Exception{
    	//and year(date)>=2016 
		String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc  ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
		ResultSet rs = ps.executeQuery();
		
		
		while (rs.next()) {
	//		System.out.println("---date:"+rs.getDate("date"));
			prices.add(new Price (rs.getDate("date"), 0,0,0,rs.getDouble("volume"),0  ));
		}
	
		//System.out.println("---------OK");
    }
    
       

    public void calculate(String code) throws Exception{
        double value = 0;
        int pricesSize = prices.size();
    	//System.out.println("--------SIZE:"+pricesSize);
        int lastPrice = pricesSize -1;
        int firstPrice = lastPrice - periodLength + 1;

        double gains = 0;
        double losses = 0;
        double avgUp = 0;
        double avgDown = 0;
      
      
        while(true){
	    	if(firstPrice<1)break;
	        if (avgList.isEmpty()) {

	          	for (int bar = lastPrice ; bar >= firstPrice; bar--) {
	          	
	          		double change = prices.get(bar-1).getClose()
	                        - prices.get(bar ).getClose();
	              //  System.out.println("today  "+" : "+change +" :: " +prices.get(bar).getDate());	
	              //  System.out.println("MAX  "+" : "+" :: " +Math.max(0, change) +"   :  "+Math.max(0, -change) );	
		               
	                
	                gains += Math.max(0, change);
	                losses += Math.max(0, -change);
	               // System.out.println("today  "+" : "+prices.get(bar).getClose() +" :: " +prices.get(bar-1).getDate());	
	               // System.out.println("yesterday "+" : "+prices.get(bar-1).getClose() +" :: " +prices.get(bar).getDate());	
	              //  System.out.println("--------GAIN  "+" : "+change +" :: " +prices.get(bar).getDate());	
	          	}
	            avgUp = gains / periodLength;
	            avgDown = losses / periodLength;
	            avgList.push(new Averages(avgUp, avgDown));
	            value = 100 - (100 / (1 + (avgUp / avgDown)));
	          //  System.out.println("end RSI: "+" : "+value +" :: " +prices.get(firstPrice-1).getDate());	
	        	if (Double.isNaN(value)) {
					value=0;
				}
	            addDataX(avgUp, avgDown, value,prices.get(firstPrice-1).getDate().toString(),code);
	            
	            
	
	        } else {
	          
	        	  double delta = prices.get(lastPrice-1).getClose()
	                      - prices.get(lastPrice).getClose();
	              gains = Math.max(0, delta);
	              losses = Math.max(0, -delta);
	              
	             // System.out.println("----------today  "+" : "+prices.get(lastPrice-1).getClose() +" :: " +prices.get(lastPrice-1).getDate());	
	             //System.out.println("----------today  "+" : "+prices.get(lastPrice).getClose() +" :: " +prices.get(lastPrice).getDate());	
	            //System.out.println("----------today delta  "+" : "+delta);	
	            Averages avg = avgList.pop();
	            avgUp = avg.getAvgUp();
	            avgDown = avg.getAvgDown();
	            avgUp = ((avgUp * (periodLength - 1)) + gains) / (periodLength);
	            avgDown = ((avgDown * (periodLength - 1)) + losses)
	                    / (periodLength);
	            value = 100 - (100 / (1 + (avgUp / avgDown)));
	         //   System.out.println("----------today avgUp  "+" : "+avgUp);
	          //  System.out.println("----------today avgDown  "+" : "+avgDown);
	            //System.out.println("----------today RSI  "+" : "+value);	
	            avgList.add(new Averages(avgUp, avgDown));
	         //   System.out.println("-----POP RSI: "+" : "+value +" :: " +prices.get(lastPrice-1).getDate());
	        	if (Double.isNaN(value)) {
					value=0;
				}
	            addDataX(avgUp, avgDown, value,prices.get(lastPrice-1).getDate().toString(),code);
	        }
	    //    value = 100 - (100 / (1 + (avgUp / avgDown)));
	        lastPrice=firstPrice-1;
	        firstPrice =lastPrice;
	        //firstPrice = lastPrice - periodLength;
	       
   //     Math.round(value);
		}
    }

	public void calcToday(String mycode) throws Exception {
		
		String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc limit 2 ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, mycode);
		ResultSet rs = ps.executeQuery();

		int x = 1;
		double todayprice = 0;
		double yesterdayprice = 0;
		double avgUp = 0;
		double avgDown = 0;
		String date=null;
		while (rs.next()) {
			if (x == 1){
				todayprice = rs.getDouble("volume");
				date =rs.getString("date");
			}
			if (x == 2) {
				avgUp = rs.getDouble("avgUpvol");
				avgDown = rs.getDouble("avgDownvol");
				yesterdayprice = rs.getDouble("volume");
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
		if(date != null) addDataX(avgUp, avgDown, rsi,date,mycode);
		 
		 if(rsi > 70 ){
			 addTechStr( rsi,mycode);
		 }
		 
		ps.close(); 
	}
	
	
	public void addTechStr(double rsi, String code) throws Exception {
		String mysql1 = "INSERT INTO techstr (code,date ,mode,changePercent) VALUES (?,?,25,?)";
		PreparedStatement ps = con.prepareStatement(mysql1);

		try {
			ps.setString(1, code);
			ps.setString(2, LocalDate.now().toString());
			ps.setDouble(3, rsi);
			ps.executeUpdate();
			ps.close();
		} catch (Exception e) {
			logger.severe("RSIV Error addTechStr " + ps);
			throw new Exception(e);
		}

	}
	
    private class Averages {

        private final double avgUp;
        private final double avgDown;

        public Averages(double up, double down) {
            this.avgDown = down;
            this.avgUp = up;
        }

        public double getAvgUp() {
            return avgUp;
        }

        public double getAvgDown() {
            return avgDown;
        }
    }

    public int getPeriodLength() {
        return periodLength;
    }
	
	public void run() {
		try {
			logger.info("RSIV run new new ");
			System.out.println("RSIV run :" );
			//  getALLPrices(mycode);
			// calculate(mycode);
			

			try (DAOFactoryStock dt = new DAOFactoryStock()) {
				Hashtable<String, StockAccess> arr = dt.getAllHash();

				for (Enumeration<String> e = arr.keys(); e.hasMoreElements();) {
					calcToday(e.nextElement());
				}

			} catch (Exception e) {
				System.out.println("Err RSIV:" + e);
				logger.severe("RSIV DAO run "   + e);
			}

			ps1.executeBatch();
			ps1.close();
			System.out.println("RSIV done:" );

			logger.info("RSIV run done");

			logger.info("RSIV :   START COMMIT ");
			con.commit();
			logger.info(" RSIV SUCCESS");
		} catch (Exception e) {
			System.out.println("Error RSIVrun " + e);
			logger.severe("RSIV Error run " + e);
			try {
				con.rollback();
			} catch (Exception e2) {
				System.out.println(" RSIV Error rollback : " + e2);
				logger.severe("RSIV Error rollback : " + e2);

			}
		} finally {
			try {

				con.close();
				System.out.println("RSIV finish closing]s :");

				logger.info("RSIV finish closing]s ");

			} catch (Exception e) {
				System.out.println(" RSIV error closing]s :" + e);
				logger.severe("RSIV finish closing]s error: " + e);
			}
		}
		
	}

    public static void main(String[] args) {
        
        
    	
        try {
   
       	//	}
       	//RSIX x = new RSIX(14,"tpm.ax");
        	//x.run();
     
        	
        	
        	
        	
       	 
		} catch (Exception e) {
			System.out.println("RSI for a " +e);
		}
        
        try (DAOFactoryStock dt = new DAOFactoryStock()) {
       	 Hashtable  <String ,StockAccess> arr  =  dt.getAllHash();
       	
       	  for (Enumeration<String> e = arr.keys();	 e.hasMoreElements();){
       	//	  System.out.println(e.nextElement());  
      		  new RSIVolumeAdmin(14, e.nextElement()).run();
       	  }
       	      

    		
       	 System.out.println("RSIV finish  ");  
		} catch (Exception e) {
			System.out.println("Err RSIV:"+e);
		}
        

   
   
   }
    
    
}