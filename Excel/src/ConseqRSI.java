
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Hashtable;

import DB.MyDatabase;
import access.StockAccess;
import factory.DAOFactoryStock;

public class ConseqRSI  extends MyDatabase{ 
    private int periodLength;
    
   
String code;
PreparedStatement ps1 ;
Hashtable  <String ,StockAccess> stocks;
    public ConseqRSI()  {
        super();
       
        
    }

    
private void  checkRSI (String code )throws Exception{
    	int count = 8;
		String mysql = "SELECT * FROM fortune.data where   code=? order by date  desc limit "+count;
		PreparedStatement ps = con.prepareStatement(mysql);
		ps.setString(1, code);
	//	System.out.println("---------today rs "+ ps);

		ResultSet rs = ps.executeQuery();
		
		int x =1;
		int rsiNow =0;
		int rsiYst =0;
		int down =0;
		int max =0;
		int min=0;
		while (rs.next()) {
			rsiNow = rs.getInt("rsi");
			//System.out.println("---------today  "+ rs.getString("date")+" :: "+ rsiNow);
			if (max==0)max=rsiNow;
			if (min==0)min=rsiNow;
			
			if(rsiNow > max  )max=rsiNow;
			if(rsiNow < min )min =rsiNow;
			
			
			if(x++<count){
				rs.next();
				rsiYst = rs.getInt("rsi");
			//	System.out.println("--------- Yesterday  "+ rs.getString("date")+" :: "+ rsiYst);

				rs.previous();
			
			
			}
		if(rsiYst > rsiNow  )down++;
			
		}
	
		if ( down >5)addTechStr(code, max ,min ); 
	//	System.out.println("---------OK");
		//System.out.println("---------down :"+ down );
    }
    
public void addTechStr(String code,int max , int min ) throws Exception {
	System.out.println("--------- RSI fall conseq days code :"+code);
	String mysql1 = "INSERT INTO techstr (code,date ,mode,Notes) VALUES (?,?,27,?)";
	PreparedStatement ps = con.prepareStatement(mysql1);
	ps.setString(1, code);
	ps.setString(2, LocalDate.now().toString());
	ps.setString(3, "fall from "+max+" to "+min );
	ps.executeUpdate();
	ps.close();
}
	
	public void run (){
		try {
			 logger.info("ConseqRSI run 1");	
			 try (DAOFactoryStock dt = new DAOFactoryStock()) {
		        	stocks =  dt.getAllHash();
		       	
		       	  for (Enumeration<String> e = stocks .keys();	 e.hasMoreElements();){
		       	//	  System.out.println(e.nextElement());  
		       		//  new RSI(15, e.nextElement()).run();
		       		checkRSI (e.nextElement() );
		       	  }
		       	      

		        //	checkRSI ("tpm.ax" );
		    		
				} catch (Exception e) {
					System.out.println("Err:"+e);
				}
		        
		        
				
			
				
						logger.info("ConseqRSI :   START COMMIT ");		
				 con.commit();
			 logger.info(" ConseqRSI SUCCESS");
		} catch (Exception e) {
			System.out.println("ConseqRSI Error run "+e);
			 logger.severe("ConseqRSI Error run "+e);	
			 try {
				con.rollback();
			} catch (Exception e2) {
				System.out.println("ConseqRSI Error rollback : "+e2);
				 logger.severe("ConseqRSI  Error rollback : "+e2);	
				
			}
		}finally{
			try{
			
				con.close();  
				System.out.println("ConseqRSI finish closing]s :");
				
				 logger.info("ConseqRSI finish closing]s ");	
				 
			}catch (Exception e){
				System.out.println(" ConseqRSI error closing]s :"+e);
				logger.severe("ConseqRSI finish closing]s error: " +e);	
			}
		}
	
	
	
	}
	
    
    public static void main(String[] args) {
    
    
    	
         try {
        	 new ConseqRSI().run();
        	 
		} catch (Exception e) {
			System.out.println(" ConseqRSI RSI for a " +e);
		}
         
        
         

    
    
    }
    
    
}
