package DB;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import dao.Database;
import util.ExcelLogger;
public class MyDatabase {
	public Connection con=null;
	public Logger logger = null;
	public MyDatabase(){
	
		

	 try {
			
			logger = ExcelLogger.getLogger();
			 con= Database.getConnection();
			 con.setAutoCommit(false);
			
			
		
			   
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			 logger.severe(" Error initialize:"+e);	    
		}	
	}

	
	public void close() {
		try {

		

			logger.info(this.getClass().getName()+ " :   START COMMIT ");
			con.commit();
			logger.info(this.getClass().getName()+ " :   COMMIT OK !!!!");

			logger.info(this.getClass().getName()+ "  SUCCESS");

		} catch (Exception e) {
			System.out.println(this.getClass().getName()+ "  Error run " + e);
			logger.severe(this.getClass().getName()+ " Error run " + e);
			
			try {
				logger.info(this.getClass().getName()+ " :   ROLL BACK ");
				con.rollback();
			} catch (Exception e2) {
				System.out.println(this.getClass().getName()+ " Error rollback : " + e2);
				logger.severe(this.getClass().getName()+  " Error rollback : " + e2);

			}
		} finally {
			try {

				con.close();
				System.out.println(this.getClass().getName()+ " finish closing]s :");

				logger.info(this.getClass().getName()+ " finish closing]s ");

			} catch (Exception e) {
				System.out.println(this.getClass().getName()+ "  error closing]s :" + e);
				logger.severe(this.getClass().getName()+ " finish closing]s error: " + e);
			}
		}

	}

	
	
	
	
}
