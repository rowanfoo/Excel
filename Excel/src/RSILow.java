

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import DB.MyDatabase;
/**
 * Calculate RSI for today
 * @author rowan
 *
 */
public class RSILow extends MyDatabase{ 
    PreparedStatement ps1 ;
    PreparedStatement ps2 ;

	public RSILow() throws Exception {
		super();
		
		

	}


	public void run() {
		try {
			logger .info("RSILow run ");
			String mysql2= "INSERT INTO techstr (code,date ,mode,changePercent) VALUES (?,?,24,?)";
			String mysql1 = "select * from data where date=curdate() and rsi < 28";
			ps1 = con.prepareStatement(mysql1);
			
			ps2 = con.prepareStatement(mysql2);

			
			 
			 try{
				 ResultSet rs = ps1.executeQuery();		
					while (rs.next()) {
						String code = rs.getString("code");
						String rsi = rs.getString("rsi");
		

						ps2.setString(1, code);
						ps2.setString(2, LocalDate.now().toString());
						ps2.setString(3, rsi);
						ps2.addBatch();
					
					
					
					}
				 
				 
				 
				 
		    		
				} catch (Exception e) {
					System.out.println("Err RSIToday:"+e);
				}
			 
			 
			 
			ps2.executeBatch();
			ps2.close();
			

			logger.info("RSILow success");

			close();

		} catch (Exception e) {
			System.out.println("RSILow Error run " + e);
			logger.severe("RSILow Error run " + e);

		}

	}
    public static void main(String[] args) {
        try {
   
        	new RSILow().run();
       	 
		} catch (Exception e) {
			System.out.println("RSILow for a " +e);
		}
   }
    
    
}