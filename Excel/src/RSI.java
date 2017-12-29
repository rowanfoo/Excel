
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import DB.MyDatabase;
import access.DataAccess;
import access.StockAccess;
import factory.DAOFactoryData;
import factory.DAOFactoryStock;

public class RSI  extends MyDatabase{ 
    private int periodLength;
    
    private  ArrayList<Price> prices;
String code;
PreparedStatement ps1 ;

    public RSI(int periodLength, String symbol) throws ParseException, IOException {
        super();
        this.periodLength = periodLength;
       
        //prices = YahooFeederUtil.getPrices(symbol);
        code = symbol;
        prices = new ArrayList<Price>();
    
    }
 
    /*
    private void  getALLPrices(){
    	
    	try (DAOFactoryData dt = new DAOFactoryData()) {
    		ArrayList  <DataAccess> arr  =  dt.getStock(code);
    		
    		for(DataAccess p:arr){
    			//System.out.println("getPrices for a 1"+p.getDate() );
    		//	System.out.println("getPrices for a o"+p.getOpen() );
    		//	System.out.println("getPrices for a h"+p.getHigh() );
    		//	System.out.println("getPrices for a l"+p.getLow() );
    		//	System.out.println("getPrices for a c"+p.getClose() );
    			prices.add(new Price (p.getDate(),p.getOpen(),p.getHigh(),p.getLow(),p.getClose()      ));
    			
    			String mysql1 ="update data set avgUp=?,avgDown=?,rsi=? where code=? and date=?"   ; 
    			ps1 = con.prepareStatement(mysql1);
    			
    		}
    		
    		
    		
		} catch (Exception e) {
			System.out.println("Err:"+e);
		}
    	
    }
*/
    
    private void  getALLPrices()throws Exception{
    	
		String mysql = "SELECT * FROM fortune.data where code=?  order by date  asc  ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
		ResultSet rs = ps.executeQuery();
		
		
		while (rs.next()) {
			prices.add(new Price (rs.getDate("date"), 0,0,0,rs.getDouble("close"),rs.getDouble("changes")      ));
		}
	
		System.out.println("---------OK");
    }
    
 
    
    private void  getTodayPrices()throws Exception {
    	

    	
    			
    			
    			
    			String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc limit "+periodLength;
    			PreparedStatement ps = con.prepareStatement(mysql);
    			ps.setString(1, code);
    			ResultSet rs = ps.executeQuery();
    			
    			
    			while (rs.next()) {
    				prices.add(new Price (rs.getDate("date"), 0,0,0,rs.getDouble("close")  ,rs.getDouble("changes")      ));
    			}
    		
    			System.out.println("---------OK");

    	
    }
    

    class RSID{
    	double rsi;
    	Date date;
    	RSID(double rsi, Date date){
    		this.rsi=rsi;
    		this.date=date;
    	}
    }
  
    public  ArrayList <RSID> calculateALL()throws Exception {
    	System.out.println("RSI for a end calculateALL" );	
    	  ArrayList <RSID> arr= new ArrayList();
    		int pricesSize = prices.size();
			int lastPrice = pricesSize - 1;
			int firstPrice = lastPrice - periodLength ;
			
		while (true) {
			double value = 0;
			// lastPrice = pricesSize - 1;
			
			if (firstPrice < 0)return arr;
				
			double gains = 0;
			double losses = 0;
			double avgUp = 0;
			double avgDown = 0;
			System.out.println("start "+lastPrice+" : "+ prices.get(lastPrice).getClose() +" :: "+prices.get(lastPrice).getDate()  );
			System.out.println("end "+firstPrice+" : "+prices.get(firstPrice).getClose() +" :: " +prices.get(firstPrice).getDate());	
	

			
		//System.out.println("RSI for a  DELTA change "+delta+" : "+ prices.get(lastPrice).getDate());
				for (int bar = lastPrice ; bar >= firstPrice; bar--) {
					//double change = prices.get(bar).getClose() - prices.get(bar - 1).getClose();
					System.out.println("for "+prices.get(bar).getDate() +" :: " + prices.get(bar).getChange());	
					double change  = prices.get(bar).getChange();
					gains += Math.max(0, change);
					losses += Math.max(0, -change);
				//	System.out.println("RSI for a change "+change+" : "+ prices.get(bar).getDate());
					
				}
//				System.out.println("RSI for a gain "+gains);
	//			System.out.println("RSI for a losses "+losses);
				avgUp = gains / periodLength;
				avgDown = losses / periodLength;
				//avgList.push(new Averages(avgUp, avgDown));
		//		System.out.println("RSI for a gaavgUpin "+avgUp);
			//	System.out.println("RSI for a avgDown "+avgDown);
			//	System.out.println("RSI for a --------------------");		

			
			
			value = 100 - (100 / (1 + (avgUp / avgDown)));
			if (Double.isNaN(value)) {
				value=0;
			}
			System.out.println("RSI for a date "+prices.get(lastPrice).getDate()+"  ;: "+value  );
			arr.add(new RSID(Math.round(value),prices.get(lastPrice).getDate()    ));
			
			//addDataX(avgUp,avgDown,value, prices.get(lastPrice).getDate().toString() );
			
//			return Math.round(value);
			 //pricesSize = firstPrice;
			lastPrice = lastPrice-1;
			 firstPrice = lastPrice - periodLength + 1;

		}
        
       
        
        // stop when last 14
        //last price top
        //first price top -14 +1, = begin.
        
        
    }
    
    public  void calculateToday()throws Exception {
    	System.out.println("RSI for a end calculateToday" );	
    	  ArrayList <RSID> arr= new ArrayList();
    	  System.out.println("RSI for a end ---------: "+prices );	
    	  int pricesSize = prices.size();
			int lastPrice = pricesSize - 1;
			int firstPrice = lastPrice - periodLength + 1;
			
		
			double value = 0;
			// lastPrice = pricesSize - 1;
			
		
				
			double gains = 0;
			double losses = 0;
			double avgUp = 0;
			double avgDown = 0;
			System.out.println("RSI for a lastPrice "+lastPrice+" : "+ prices.get(lastPrice).getClose() );
			System.out.println("RSI for a firstPrice "+firstPrice+" : "+prices.get(firstPrice).getClose() );	
	

			
		//System.out.println("RSI for a  DELTA change "+delta+" : "+ prices.get(lastPrice).getDate());
				for (int bar = firstPrice + 1; bar <= lastPrice; bar++) {
					double change = prices.get(bar).getClose() - prices.get(bar - 1).getClose();
					gains += Math.max(0, change);
					losses += Math.max(0, -change);
				//	System.out.println("RSI for a change "+change+" : "+ prices.get(bar).getDate());
					
				}
//				System.out.println("RSI for a gain "+gains);
	//			System.out.println("RSI for a losses "+losses);
				avgUp = gains / periodLength;
				avgDown = losses / periodLength;
				//avgList.push(new Averages(avgUp, avgDown));
		//		System.out.println("RSI for a gaavgUpin "+avgUp);
			//	System.out.println("RSI for a avgDown "+avgDown);
			//	System.out.println("RSI for a --------------------");		

			
			
			value = 100 - (100 / (1 + (avgUp / avgDown)));
			if (Double.isNaN(value)) {
				value=0;
			}
			//System.out.println("RSI for a date "+prices.get(lastPrice).getDate()  );
			arr.add(new RSID(Math.round(value),prices.get(lastPrice).getDate()    ));
			System.out.println("RSI for a date  RIS "+ value  );
			
			//addDataX(avgUp,avgDown,value, prices.get(lastPrice).getDate().toString() );
			

			lastPrice = lastPrice-1;
			 firstPrice = lastPrice - periodLength + 1;

		
        
       
        
        
    }
    

	public void calcToday(String code) throws Exception {

		String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc limit 2 ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
		ResultSet rs = ps.executeQuery();
		
		int x=1;
		double todayprice =0;
		double yesterdayprice =0;
		double avgUp=0 ;
		double avgDown=0; 
		while (rs.next()) {
		if(x==1)todayprice=rs.getDouble("close");
		if(x==2){
			avgUp=rs.getDouble("avgUp");
			avgDown=rs.getDouble("avgDown");
			yesterdayprice=rs.getDouble("close");
		}
		x++;
		
		}
		
		//double delta = prices.get(lastPrice).getClose()
          //      - prices.get(lastPrice - 1).getClose();
		double delta = todayprice
              - yesterdayprice;
		System.out.println("RSI for a end todayprice: "+todayprice+" :yest: "+ yesterdayprice);	
		System.out.println("RSI for a end avgUp: "+avgUp+" :avgDown: "+ avgDown);	
		double gains = Math.max(0, delta);
		double losses = Math.max(0, -delta);
		 avgUp = ((avgUp * (periodLength - 1)) + gains) / (periodLength);
         avgDown = ((avgDown * (periodLength - 1)) + losses)
                 / (periodLength);
         System.out.println("RSI for a today  avgUp: "+avgUp+" :avgDown: "+ avgDown);	
          double rsi = 100 - (100 / (1 + (avgUp / avgDown)));
          System.out.println("RSI for a end today rsi: "+rsi);	
	}


	public void addDataX(double avgup,double down,double rsi,String date) throws Exception {

		// DAOFactoryDataReport

		//System.out.println("addDataX code:"+code+" : avgup: "+avgup+" : avgdown :"+down+" : rsi: "+rsi +" :date: "+date );	
		//System.out.println("RSI for a end avgup+"+avgup );		
	//	String mysql1 ="update data set avgUp=?,avgDown=?,rsi=? where code=? and date=?"   ; 
			
		//	PreparedStatement ps1 = con.prepareStatement(mysql1);
		
		//	System.out.println("RSI for a end ps1+"+ps1 );		
			ps1.setDouble(1,avgup);
			ps1.setDouble(2, down);
			ps1.setDouble(3, rsi);
			ps1.setString(4, code);
			ps1.setString(5, date);
			ps1.addBatch();
				
				
		

		
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
    
    
	
	
	public void run (){
		try {
	
			getALLPrices();
			calculateALL();
			//ps1.executeBatch();
			//ps1.close();
// only used when updating all database.
			
			//calcToday("bhp.ax");
		//	getTodayPrices();
		//	calculateALL();
		//	calculateToday();
				 logger.info("TenMonthAverageFiveStr run 1");	
			
				
						logger.info("TenMonthAverageFiveStr :   START COMMIT ");		
				 con.commit();
			 logger.info(" TenMonthAverageFiveStr SUCCESS");
		} catch (Exception e) {
			System.out.println("Error run "+e);
			 logger.severe("TenMonthAverageFiveStr Error run "+e);	
			 try {
				con.rollback();
			} catch (Exception e2) {
				System.out.println("Error rollback : "+e2);
				 logger.severe("TenMonthAverageFiveStr Error rollback : "+e2);	
				
			}
		}finally{
			try{
			
				con.close();  
				System.out.println("TenMonthAverageFiveStr finish closing]s :");
				
				 logger.info("TenMonthAverageFiveStr finish closing]s ");	
				 
			}catch (Exception e){
				System.out.println(" TenMonthAverageFiveStr error closing]s :"+e);
				logger.severe("TenMonthAverageFiveStr finish closing]s error: " +e);	
			}
		}
	
	
	
	}
	
    
    public static void main(String[] args) {
    
    
    	
         try {
        	// new RSI(15, "ede.AX").run();
        	// RSI rsi = new RSI(15, "");
          //   System.out.println("RSI for a " + rsi.getPeriodLength()
            //         + " days period is: " + rsi.calculate());
        	 
        	// ArrayList <RSID> arr= rsi.calculateALL();
        	////	for(RSID p:arr){
        	//		System.out.println(p.date+" :: "+p.rsi);
        	//	}
        	 new RSI(14,"tpm.ax").run();
        	 
		} catch (Exception e) {
			System.out.println("RSI for a " +e);
		}
         
         try (DAOFactoryStock dt = new DAOFactoryStock()) {
        	 Hashtable  <String ,StockAccess> arr  =  dt.getAllHash();
        	
        	//  for (Enumeration<String> e = arr.keys();	 e.hasMoreElements();){
        	//	  System.out.println(e.nextElement());  
        		//  new RSI(15, e.nextElement()).run();
        	//  }
        	      

     		
     		
 		} catch (Exception e) {
 			System.out.println("Err:"+e);
 		}
         

    
    
    }
    
    
}
