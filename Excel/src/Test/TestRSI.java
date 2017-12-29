package Test;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Enumeration;
import java.util.Hashtable;

import DB.MyDatabase;
import access.StockAccess;
import factory.DAOFactoryStock;

public class TestRSI  extends MyDatabase{ 
	
	public TestRSI() throws Exception {
			super();
		}
		
		public void run() {
			try {
				logger.info("AdminTest run ");
				
				String mysql1 = "SELECT COUNT(*) AS rowcount FROM fortune.data where date=current_date()  and  rsi is null";
				PreparedStatement ps1 = con.prepareStatement(mysql1);
			
				
				ResultSet r = ps1.executeQuery();
				
				r.next();
				int count = r.getInt("rowcount");
				
				ps1.close();
				
				if(count >0 ){
					mysql1 = "INSERT INTO techstr (code,date ,mode,Notes) VALUES (?,?,0,?)";
					PreparedStatement ps2 = con.prepareStatement(mysql1);
					ps2.setString(1, "aaa.ax");
					ps2.setString(2, LocalDate.now().toString() );
					ps2.setString(3, " Error RSI , need to recalc ");
					
					ps2.executeUpdate();
					ps2.close();
					  
					
				} 
				
				
				logger.info("v success");

				close();

			} catch (Exception e) {
				System.out.println("AdminTest Error run " + e);
				logger.severe("AdminTest Error run " + e);

			}

		}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	      try {
	    	   
	        	new TestRSI().run();
	       	 
			} catch (Exception e) {
				System.out.println("AdminTest for a " +e);
			}

	}

}
