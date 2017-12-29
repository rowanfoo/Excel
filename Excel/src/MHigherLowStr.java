



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
import access.TechStrAccess;
import factory.DAOFactoryTechStr;
import util.ExcelLogger;
import util.FormatUtil;





public class  MHigherLowStr  extends MyDatabase{

	
	public void execute(String sql)throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		System.out.println("ok execute :    ");	
    		
    		
    		
    		
    		
    		
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

		String mysql = "SELECT *  from (select code, min(low) as low1 from data where month(date)=? and code=? and  date >  date_sub(current_Date(),INTERVAL 10 MONTH  ))a,(select min(low) as low2 from data where month(date)=?  and code=? and  date >  date_sub(current_Date(),INTERVAL 10 MONTH  ))b,"
				+ "(select min(low) as low3 from data where month(date)=?  and code=? and  date >  date_sub(current_Date(),INTERVAL 10 MONTH  ))c ,(select min(low) as low4 from data where month(date)=?  and code=? and  date >  date_sub(current_Date(),INTERVAL 10 MONTH  ))d,"
				+ "(select min(low) as low5 from data where month(date)=?  and code=? and  date >  date_sub(current_Date(),INTERVAL 10 MONTH  ))e,  (select min(low) as low6 from data where month(date)=?  and code=? and  date >  date_sub(current_Date(),INTERVAL 10 MONTH  ))f ";

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

				double low1 = rs.getDouble("low1");
				double low2 = rs.getDouble("low2");
				double low3 = rs.getDouble("low3");
				double low4 = rs.getDouble("low4");
				double low5 = rs.getDouble("low5");
				double low6 = rs.getDouble("low6");
				String lowlow = low1 + "," + low2 + "," + low3 + "," + low4 + "," + low5 + "," + low5 + "," + low6;

				if (low1 > low2)
					count = count + 2;
				if (low2 > low3)
					count = count + 2;
				if (low3 >= low4)
					count++;
				if (low4 >= low5)
					count++;
				if (low5 >= low6)
					count++;

				if (count > 5)
					System.out.println("FOUND:" + code);

				ps1.setString(1, code);
				ps1.setString(2, FormatUtil.getTodayDateToString());
				ps1.setString(3, lowlow);
			//	ps1.addBatch();

			}
			count = 0;
		}

		//ps1.executeBatch();
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

		
	new MHigherLowStr().run();
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


