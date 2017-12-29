
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import util.ExcelLogger;
import util.FormatUtil;;

/**
 * Find shares falling more than 4 days
 * 
 * @author rowan
 *
 */

public class ConsequitveDayFallStr extends MyDatabase {

	public void execute(String sql) throws Exception {

		Statement stmt = con.createStatement();
		stmt.executeUpdate(sql);
		
		stmt.close();

	}

	public void addData(double percent, String date) throws Exception {
		// Statement stmt= con.createStatement();

		// show all that haas fallen below - %;

		// System.out.println("ok going sql: ");
	//	String mysql = "SELECT code, count(*) count FROM fortune.data where date >= (DAYSADDNOWK(CURDATE(), 7)) and changePercent<0 group by code  having count >=6 ";
		String mysql = "SELECT code, count(*) count, max(close) as mymax ,min(close)as mymin FROM fortune.data where date >=? and changePercent<0 group by code  having count >=6";
		
		//
		LocalDate mydate = FormatUtil.getWorkDay(LocalDate.now(), 7);

		PreparedStatement stmt = con.prepareStatement(mysql);

		stmt.setString(1, mydate.toString());
		
		 System.out.println("ok finid : "+stmt);
		
		// System.out.println("ok done : sql ");

		// CallableStatement cstmt = con.prepareCall("call conseqDownDay()");

		 ResultSet rs = stmt.executeQuery();
		// ResultSet rs = cstmt.executeQuery(mysql);
		// cstmt.execute();
		// ResultSet rs = cstmt.getResultSet();

		// System.out.println("ok finid : ");
		while (rs.next()) {

			String sql = "INSERT INTO techstr (code,date,mode,close,fifty,fiftychg) VALUES (" + "'" + rs.getString("code") + "'," + "'"
					+ date + "'," + "6 ," + rs.getString("count") +"," +rs.getDouble("mymax") +"," +rs.getDouble("mymin")+     ")";
			//System.out.println("ok execute :    " + sql);
			execute(sql);
			// System.out.println("ok execute : "+rs.getString("code"));

		}

		//cstmt.close();
		stmt.close();
	}

	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());

			logger.info("FiftyDayDistance run 1");
			addData(percent, date);
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("ConsequitveDayFallStr Error run " + e);
		}
	}

	public static void main(String[] args) {

		new ConsequitveDayFallStr().run();

		// get current date time with Date()

	}

}
