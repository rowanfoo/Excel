



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

import DB.MyDatabase;
import access.StockAccess;
import factory.DAOFactoryStock;
import util.FormatUtil;





public class  BreakRoundNumber  extends MyDatabase {

	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		
    		
    		stmt.close();
			
	
	}
	
	ArrayList <StockAccess> arr;
	
	public void getStocks()throws Exception {
		
		try(DAOFactoryStock d = new DAOFactoryStock()) {
			arr=d.getAllList();
		} catch (Exception e) {
			throw new Exception (e);
		}

	}
	
		
	public void addData(String code)throws Exception {
		Statement stmt= con.createStatement(); 
	
		//show all that haas fallen below - %;
		
		
		String mysql =" SELECT A.CLOSE as today, B.CLOSE as yesterday  FROM  ( SELECT floor(close) as close from data where date = curdate()  and code=? ) AS A, (select floor(close) as close from data where date = curdate()-1  and code=? ) AS B  WHERE A.CLOSE < B.CLOSE ";
		String mysql1 ="INSERT INTO techstr (code,date,mode,close,open) VALUES (?,?,?,?,?)"; 
		
		PreparedStatement ps =
		        con.prepareStatement(mysql);
		
		PreparedStatement ps1 =
		        con.prepareStatement(mysql1);
		
		
		ps.setString(1, code );
		ps.setString(2, code );
		
		ResultSet rs = ps.executeQuery();
	
		
		while(rs.next()){
		
		double today=rs.getDouble("today");
		double yesterday=rs.getDouble("yesterday");
		//System.out.println("CODE "+code);
	
		
					ps1.setString(1, code);
					ps1.setString(2,FormatUtil.getTodayDateToString() );
					ps1.setInt(3,23);
					ps1.setDouble(4,today);
					ps1.setDouble(5,yesterday);
					
					ps1.executeUpdate();
				
			
			
		}
			
			
		
		
		
		
		
		
	     
		
		ps.close();
		ps1.close();

		
		
	
	}
		
	      
	
	
	
	
	
	
	
	public void run() {
		try {
			logger.info("BreakRoundNumber run 1");
			getStocks();
			for (StockAccess obj : arr) {

				addData(obj.getCode());

			}

			close();

			logger.info(" BreakRoundNumber SUCCESS");

		} catch (Exception e) {
			System.out.println("Error BreakRoundNumber run " + e);
			logger.severe("BreakRoundNumber Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	new BreakRoundNumber().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
	LocalDate week = LocalDate.now();
	LocalDate month;
//	for( int x =0;x<15;x++){
	//	month = week.minusMonths(x);
		//System.out.println(" DayReversalVolStr month :"+month.getMonthValue()+":"+month.getYear() );
	//}
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


