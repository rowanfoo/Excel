
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import util.ExcelLogger;
import util.FormatUtil;
/**
 * 
 * find all vol * 60%
 * 1. large vol small drop
 * 2.large vol break throug.
 * 3. large vol small gain - dying
 * 
 * 
 * 
 * @author rowan
 *
 */








public class  NearTwentyDayAvgStr  extends MyDatabase {
//	FileInputStream file=null;
	
	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		
    		
    		stmt.close();
			
	
	}
	
	
	public ArrayList<String> getAllCode() throws Exception {
		ArrayList<String> arr = new ArrayList<String>();

		String sql = "select code from stock";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sql);

		while (rs.next()) {
			arr.add(rs.getString("code"));
		}

		stmt.close();
		return arr;

	}

	
	
	
	
	public void addData() throws Exception {
		Statement stmt = con.createStatement();

		// show all that haas fallen below - %;

		String mysql = "SELECT *  from (select code, avg(changePercent) as avg1 from data where month(date)=? and code=? and  YEAR(date) = YEAR(CURDATE()))a,(select avg(changePercent) as avg2 from data where month(date)=?  and code=? and  YEAR(date) = YEAR(CURDATE()))b,"
				+ "(select avg(changePercent) as avg3 from data where month(date)=?  and code=? and  YEAR(date) = YEAR(CURDATE()))c ,(select avg(changePercent) as avg4 from data where month(date)=?  and code=? and  YEAR(date) = YEAR(CURDATE()))d,"
				+ "(select avg(changePercent) as avg5 from data where month(date)=?  and code=? and  YEAR(date) = YEAR(CURDATE()) )e,  (select avg(changePercent) as avg5 from data where month(date)=?  and code=? and  YEAR(date) = YEAR(CURDATE()))f ";

		String mysql1 = "INSERT INTO techstr (code,date,mode,lowlow) VALUES (?,?,12,?)";

		PreparedStatement ps = con.prepareStatement(mysql);
		PreparedStatement ps1 = con.prepareStatement(mysql1);

		ArrayList<String> arr = getAllCode();

		for (String code : arr) {

			LocalDate date = LocalDate.now();
			int month = date.getMonth().getValue();

			ps.setInt(1, month);
			ps.setString(2, code);

			ps.setInt(3, month - 1);
			ps.setString(4, code);

			ps.setInt(5, month - 2);
			ps.setString(6, code);

			ps.setInt(7, month - 3);
			ps.setString(8, code);

			ps.setInt(9, month - 4);
			ps.setString(10, code);

			ps.setInt(11, month - 5);
			ps.setString(12, code);

			// System.out.println("MySql:"+ps);
			ResultSet rs = ps.executeQuery();

			int count = 0;
			while (rs.next()) {

				double avg1 = rs.getDouble("avg1");
				double avg2 = rs.getDouble("avg2");
				double avg3 = rs.getDouble("avg3");
				double avg4 = rs.getDouble("avg4");
				double avg5 = rs.getDouble("avg5");
				
				String lowlow = avg1 + "," + avg2 + "," + avg3 + "," + avg4 + "," + avg5  ;

				if (avg1 < 0.05 ||avg1 > -0.05 )
					count = count + 2;
				if (avg2 < 0.05 ||avg2 > -0.05 )
					count = count + 2;
				if (avg3 < 0.05 ||avg3 > -0.05 )
					count++;
				if (avg4 < 0.05 ||avg4 > -0.05 )
					count++;
				if (avg5 < 0.05 ||avg5 > -0.05 )
					count++;

				if (count > 5 )
					System.out.println("FOUND:" + code);

				ps1.setString(1, code);
				ps1.setString(2, FormatUtil.getTodayDateToString());
				ps1.setString(3, lowlow);
			//	ps1.addBatch();

			}
			count = 0;
		}

	//	ps1.executeBatch();
		ps.close();
		ps1.close();

	}
	      
	
	
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());

			System.out.println("1 ");

			logger.info("HigherLowStr run 1");
			addData();

			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("DayReversalVolStr Error run " + e);

		}

	}
	
public static void main(String[] args) {

		
	new NearTwentyDayAvgStr().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}





