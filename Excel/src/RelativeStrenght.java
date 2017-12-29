
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import DB.MyDatabase;
import access.DataAccess;
import access.StockAccess;
import factory.DAOFactoryStock;
import util.MovingAverage;
/**
 * measure relative strenght cs ASX
 * @author rowan
 *
 */
public class RelativeStrenght extends MyDatabase{ 
  
    private  ArrayList<Price> prices;
    private  ArrayList<Price> compareprices= new ArrayList<Price>();
    
    PreparedStatement ps1 ;
    
    String compareCcode;
    String code;
    ArrayList<DataAccess> data = new 	 ArrayList<DataAccess>();

	public RelativeStrenght( String symbol,String compareCcode) throws Exception {
		super();
		
		this.compareCcode=compareCcode;
		code = symbol;
		System.out.println("Code: " + symbol+ " compare  "+ compareCcode);
		prices = new ArrayList<Price>();
		String mysql1 = "update data set rsichg=?,rsiasx=?,relativestrenght=? where code=? and date=?";
		
		ps1 = con.prepareStatement(mysql1);
		
		
	}
	public RelativeStrenght() throws Exception {
		super();
		String mysql1 = "update data set rsichg=?,rsiasx=?,relativestrenght=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);
		
	}
	public void addData(double rsichg,double rsiax,double relativestrenght,String mcode, String date) throws Exception {
		//System.out.println("addData  rsichg"+ rsichg+" rsiax: "+rsiax+" relativestrenght: "+relativestrenght+"  : "+code+" :: "+date);
		
		ps1.setDouble(1, rsichg);
		ps1.setDouble(2, rsiax);
		ps1.setDouble(3, relativestrenght);
		ps1.setString(4, mcode );
		ps1.setString(5, date);
		
		ps1.addBatch();

	}


	
    private void  getALLPrices()throws Exception{
    	//this is because both set of data dont match , some got extra day .
		String mysql = "SELECT * FROM fortune.data where code=? and date  in (SELECT date FROM fortune.data where code=?)  order by date  asc  ";
		PreparedStatement ps = con.prepareStatement(mysql);
		
		ps.setString(1, code);
		ps.setString(2, compareCcode);
		ResultSet rs = ps.executeQuery();
		
		
		while (rs.next()) {
			
			prices.add(new Price (rs.getDate("date"), 0,0,0,0,rs.getDouble("changePercent")      ));
		}

		String comsql = "SELECT * FROM fortune.data where code=? and date  in (SELECT date FROM fortune.data where code=?)  order by date  asc  ";

		ps = con.prepareStatement(comsql);
		ps.setString(1, compareCcode);
		ps.setString(2, code);
		rs = ps.executeQuery();
		while (rs.next()) {
			compareprices.add(new Price (rs.getDate("date"), 0,0,0,0,rs.getDouble("changePercent")      ));
		}
		
		
		
		if(prices.size() != compareprices.size())throw new Exception("data dont match price "+prices.size()+" vs compareprices "+compareprices.size() );
		ps.close();
		
		//System.out.println("---------OK");
    }
    
       

    public void calculate() throws Exception{
		Date firstdate=null;
		double firstchange=0.0;
		double secondchange=0.0;
		double result=0.0;
		 
    
		for(int x =0;x<prices.size();x++){
    		Price y = compareprices.get(x);
    		Price p =prices.get(x);
    		
    		if(x==0){
	    		p.setClose( p.getChange()+100  );	
	    		y.setClose( y.getChange()+100  );	
	    	}else{
	    		Price previous = prices.get(x-1);
	    		Price previousy=compareprices.get(x-1);;
	    		p.setClose( p.getChange()+previous.getClose()   );	
	    		y.setClose( y.getChange()+previousy.getClose()  );	
	    	    
	    		
	    	}
    		//addDataX(y.getClose(), 0.0, compareCcode,  y.getDate().toString() );
    		///addDataCode(double rsichg,double rsiax,double relativestrenght,String mcode, String date) 
    		
    		p.setOpen(    (p.getClose()/  y.getClose()  )*100                 );
    		//System.out.println("date: " + p.getDate() + " compare  "+ p.getClose());
    	//	System.out.println("date: " + y.getDate() + " compare  "+ y.getClose());
    		System.out.println("date: " + p.getDate() + " :  "+ p.getClose()+ " vs "+ y.getClose()+"  == "+ p.getOpen()+"    -->  "+y.getDate());
    		DataAccess dt = new DataAccess();	
        	dt.setDate( p.getDate());
        	dt.setVolume( p.getClose() );
        	dt.setLow( y.getClose() );
        	dt.setClose(p.getOpen());
        	
        	data.add(dt);
        	
    	}
    	
		
		
		new MovingAverage(20 , data).run(); 
		for(DataAccess dtc : data ){
			addData(dtc.getVolume(),dtc.getLow(), dtc.getTwenty(), code, dtc.getDate().toString() );
			
		}
		
    }

	public void calcToday(String code,String compareCcode ) throws Exception {

		String mysql = "SELECT * FROM fortune.data where code=?  order by date  desc limit 2 ";
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
		//System.out.println("calcToday code: " + code);
		ResultSet rs = ps.executeQuery();
		double chg =0.0; 
		double rsi =0.0; 
		
		double comparechg =0.0; 
		double comparersi =0.0; 
		double result =0.0; 
		
		while (rs.next()) {
			chg = rs.getDouble("changePercent");
			rs.next();
			rsi= chg + rs.getDouble("rsichg");
			comparersi=rs.getDouble("rsiasx");
		//	System.out.println("today tpm rsichg "+ rsi);
			//System.out.println("today tpm rsiasx "+ comparersi);

		}
		String mycomp = "SELECT * FROM fortune.data where code=?  order by date  desc limit 1 ";
		ps = con.prepareStatement(mycomp);
		ps.setString(1, compareCcode);
		rs = ps.executeQuery();
		
		while (rs.next()) {
			comparechg = rs.getDouble("changePercent");
			//System.out.println("comparechg "+ comparechg);
			comparersi = comparersi+comparechg;
		}
		//System.out.println("comparersi "+ comparersi);

		 
		result= (rsi/comparersi)*100;
		// System.out.println("result  "+ result);
		 
		 
		
		 String sql = "SELECT * FROM fortune.data where code=? order by date  desc limit 20  ";
			ps = con.prepareStatement(sql);
			ps.setString(1, code);
			
			rs = ps.executeQuery();
			
			double relativestrenght=0.0;
			 
			  while (rs.next()) {
				relativestrenght += rs.getDouble("relativestrenght");
			//	System.out.println("relativestrenght "+rs.getString("date")   +" : "+ rs.getDouble("relativestrenght"));
					
			}
			relativestrenght=relativestrenght+result;
			
						
			relativestrenght=relativestrenght/20;
		// System.out.println("date20 "+ relativestrenght);
		 
		 addData(rsi,comparersi,relativestrenght,code, LocalDate.now().toString() );

		 
		 
		 ps.close();
	}
	
	
	public void addTechStr(double rsi) throws Exception {
	
		String mysql1 = "INSERT INTO techstr (code,date ,mode,changePercent) VALUES (?,?,24,?)";
		PreparedStatement ps = con.prepareStatement(mysql1);
		ps.setString(1, code);
		ps.setString(2, LocalDate.now().toString());
		ps.setDouble(3, rsi);
		ps.executeUpdate();
		ps.close();
	}

	public void run() {
		try {
			//getALLPrices();
		  	//calculate();
			logger.info("RelativeStrenght run  ");
			//run eceryday 
			
			 try (DAOFactoryStock dt = new DAOFactoryStock()) {
               	 Hashtable  <String ,StockAccess> arr  =  dt.getAllHash();
               	  for (Enumeration<String> e = arr.keys();	 e.hasMoreElements();){
              		calcToday(e.nextElement(),"^AORD");
               	  }
            		
        		} catch (Exception e) {
        			System.out.println("RelativeStrenght RSIX:"+e);
        			logger.severe("RelativeStrenght DAO run "+code+" ::"   + e);
        		}
        		
				ps1.executeBatch();
				ps1.close();
                
		  	
				close();
				
		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("RelativeStrenght Error run "+code+" ::"   + e);
			
		} 
		logger.info("RelativeStrenght finsih  ");

	}
    public static void main(String[] args) {
        
        
    	
        try {
        	//new  RelativeStrenght().run() ;
        	
        	new  RelativeStrenght("tpm.ax","^AORD").run() ;
  
        	/*
        	 try (DAOFactoryStock dt = new DAOFactoryStock()) {
               	 Hashtable  <String ,StockAccess> arr  =  dt.getAllHash();
               	
               	  for (Enumeration<String> e = arr.keys();	 e.hasMoreElements();){
               	//	  System.out.println(e.nextElement());  
              		  
              		new  RelativeStrenght( e.nextElement(),"^AORD").run() ;
                	
               	  }
               	      

            		
            		
        		} catch (Exception e) {
        			System.out.println("RelativeStrenght RSIX:"+e);
        		}
                
*/
        	 System.out.println("RelativeStrenght RSIX FINISH----------------!!!!!!!!:");	
        	
       	 
		} catch (Exception e) {
			System.out.println("RelativeStrenght for a " +e);
			
		}
        
      
    		

   
   
   }
    
    
}