import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import DB.MyDatabase;



public class RSId  extends MyDatabase{ 

	PreparedStatement ps1;
	public RSId() throws Exception{
		
		String mysql1 = "update data set rsid=? where code=? and date=?";
		ps1 = con.prepareStatement(mysql1);
	}
	
	public static String getdate(Date dt){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(dt);	
	}
	
	
	public void addDataX(String code, double rsid, String date) throws Exception {

		ps1.setDouble(1, rsid);
		ps1.setString(2, code);
		ps1.setString(3, date);
		//System.out.println("---------PS1---"+ps1);
		ps1.addBatch();

	}

	
	private  void  getALLPrices(String  start, String end)throws Exception{
    	//and year(date)>=2016 
		String mysql = "SELECT changePercent FROM fortune.data where code=?  and date>=? and date<=?";
		PreparedStatement ps = con.prepareStatement(mysql);
	
		ps.setString(1, "bhp.ax");
		ps.setString(2, start);
		ps.setString(3, end);
		System.out.println("---------PS---"+ps);
		ResultSet rs = ps.executeQuery();
		double gain=0;
		double loss=0;
		double rsid=0;	
		while (rs.next()) {
			double chg=rs.getDouble("changePercent");
			if(chg>0)gain++;
			else if(chg < 0 )loss++;
		}
		rsid=gain-loss;
		if( rsid < 0 )rsid = 0;
		
		
		addDataX("bhp.ax",rsid,end); 
	//	System.out.println("---------gain---"+gain);
		//System.out.println("---------loss---"+loss);
		//System.out.println("---------RSID---"+rsid);
    }
    
	
	
	public void run() {
		try {

			 Calendar cal = Calendar.getInstance();
				
			 cal.set(Calendar.YEAR, 2016);
			 cal.set(Calendar.WEEK_OF_YEAR, 52);        
			 cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	
			 int arr[] = {2009,2010,2011,2012,2013,2014,2015,2016,2017};
			 
			 System.out.println(cal.getTime() );
			 
			for(int z:arr){ 
				 cal.set(Calendar.YEAR, z);
				int x=1;
				if(z==2009)x=2;
				for(;x<=52;x++){
					Date start = null;
					Date end = null;
					
					 cal.set(Calendar.WEEK_OF_YEAR, x);        
					 cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
					 start= cal.getTime();
					
					 cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
					 end= cal.getTime();
					 getALLPrices(getdate( start), getdate(end));
					 //System.out.println("st "+ start );
					 //System.out.println("end: "+ end );
				
				
				
				}

			}
			
			ps1.executeBatch();
			ps1.close();
			
			logger.info("RSIX run 1");

			logger.info("RSIX :   START COMMIT ");
			con.commit();
			logger.info(" RSIX SUCCESS");
		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("RSIX Error run " + e);
			try {
				con.rollback();
			} catch (Exception e2) {
				System.out.println("Error rollback : " + e2);
				logger.severe("RSIX Error rollback : " + e2);

			}
		} finally {
			try {

				con.close();
				System.out.println("RSIX finish closing]s :");

				logger.info("RSIX finish closing]s ");

			} catch (Exception e) {
				System.out.println(" RSIX error closing]s :" + e);
				logger.severe("RSIX finish closing]s error: " + e);
			}
		}

	}
	
	
	public static void main(String[] args) {

		try {

			new RSId().run();
			 
			 
		} catch (Exception e) {
			System.out.println("Err RSIX:" + e);
		}

	}
}
