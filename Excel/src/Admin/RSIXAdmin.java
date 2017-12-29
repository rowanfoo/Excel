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

public class RSIXAdmin extends MyDatabase{ 
    private int periodLength;
    private final Stack<Averages> avgList;
    private final ArrayList<Price> prices;
    PreparedStatement ps1 ;
String code;
/**
 * Caluclate RSI , for WHOLE database
 * @param periodLength
 * @param symbol
 * @throws Exception
 */
	public RSIXAdmin(int periodLength, String symbol) throws Exception {
		super();
		System.out.println("---RSIX:"+symbol);
		this.periodLength = periodLength;
		avgList = new Stack<Averages>();
		
		code = symbol;
		prices = new ArrayList<Price>();
		getALLPrices();
		String mysql1 = "update data set avgUp=?,avgDown=?,rsi=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);

	}

	public void addDataX(double avgup, double down, double rsi, String date) throws Exception {
		//System.out.println("---addDataX:"+date+" code: "+code+" rsi :"+rsi);
		ps1.setDouble(1, avgup);
		ps1.setDouble(2, down);
		ps1.setDouble(3, rsi);
		ps1.setString(4, code);
		ps1.setString(5, date);
		ps1.executeUpdate();
		ps1.addBatch();

	}

    private void  getALLPrices()throws Exception{
    	//and year(date)>=2016 
		String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc  ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
		ResultSet rs = ps.executeQuery();
		
		
		while (rs.next()) {
	//		System.out.println("---date:"+rs.getDate("date"));
			prices.add(new Price (rs.getDate("date"), 0,0,0,rs.getDouble("close"),0    ));
		}
	
		//System.out.println("---------OK");
    }
    
       

    public void calculate() throws Exception{
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
	            addDataX(avgUp, avgDown, value,prices.get(firstPrice-1).getDate().toString());
	            
	            
	
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
	            addDataX(avgUp, avgDown, value,prices.get(lastPrice-1).getDate().toString());
	        }
	    //    value = 100 - (100 / (1 + (avgUp / avgDown)));
	        lastPrice=firstPrice-1;
	        firstPrice =lastPrice;
	        //firstPrice = lastPrice - periodLength;
	       
   //     Math.round(value);
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
			logger.info("RSIX run ");
			calculate();
			
			ps1.executeBatch();
			ps1.close();

			logger.info("RSIX success");

			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("RSIX Error run " + e);

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
      		  new RSIXAdmin(14, e.nextElement()).run();
       	  }
       	      
       	System.out.println("RSIX FINISH :");
    		
    		
		} catch (Exception e) {
			System.out.println("Err RSIX:"+e);
		}
        

   
   
   }
    
    
}