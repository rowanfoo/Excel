
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import DB.MyDatabase;
import util.FormatUtil;
/**
 * 
 * Find twenty cross over fourty
 *
 */

public class  TwoHundredCrossFourHundred  extends MyDatabase {
	
	public void addData() throws Exception {
		Statement stmt = con.createStatement();

		// show all that haas fallen below - %;

		String mysql = "SELECT * FROM fortune.data where date=curdate() and ((twohundred - fourhundred )/fourhundred ) between 0 and 0.05";

		String mysql1 = "INSERT INTO techstr (code,date,mode,twohundredchg) VALUES (?,?,32,?)";

		PreparedStatement ps = con.prepareStatement(mysql);
		PreparedStatement ps1 = con.prepareStatement(mysql1);

	

	
			ResultSet rs = ps.executeQuery();

			int count = 0;
			while (rs.next()) {

				String code= rs.getString("code");
				double percent= ((rs.getDouble("twohundred")-rs.getDouble("fourhundred")) /rs.getDouble("fourhundred"))*100;
			//	double percent= ((rs.getDouble("twohundred")-rs.getDouble("")) /rs.getDouble("fourhundred"))*100;	
				
				
				ps1.setString(1, code);
				ps1.setString(2, FormatUtil.getTodayDateToString());
				ps1.setDouble(3, percent);
				ps1.addBatch();
 
			
		
		}

		ps1.executeBatch();
		ps.close();
		ps1.close();

	}
	      
	
	
	
	
	
	public void run() {
		try {

			logger.info("TwoHundredCrossFourHundred run 1");
			addData();

			close();

		} catch (Exception e) {
			System.out.println("Error TwoHundredCrossFourHundred run " + e);
			logger.severe("TwoHundredCrossFourHundred Error run " + e);

		}

	}
	
public static void main(String[] args) {
	new TwoHundredCrossFourHundred().run();
			
	
		  }
	
	
	
}





