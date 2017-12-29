




import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

import DB.MyDatabase;
import access.StockAccess;
import access.TechStrAccess;
import factory.DAOFactoryDataReport;
import factory.DAOFactoryStock;





public class  MutlipleTechnical  extends MyDatabase {
	
		private void addRecord(String code,String modes,int mode) throws Exception{
			String mysql1 ="INSERT INTO techstr (code,date ,mode,Notes) VALUES (?,?,?,?)"  ; 
			PreparedStatement ps1 =
			        con.prepareStatement(mysql1);
			ps1.setString(1, code);
			ps1.setString(2, LocalDate.now().toString());
			ps1.setInt(3, mode);

			ps1.setString(4, modes);

			
			
			//System.out.println("mysql:"+ps1);
			ps1.executeUpdate();
			ps1.close();
		
		}

	public void addDataX() throws Exception {



			String mysql = "SELECT code,count(*) as no  FROM fortune.techstr where date=curdate()  and mode not in(1,2) group by code having count(*) >1";
			String mysql1 = " SELECT code,mode FROM fortune.techstr where code=? and  date=curdate()";

			PreparedStatement ps = con.prepareStatement(mysql);
			PreparedStatement ps1 = con.prepareStatement(mysql1);

			ResultSet rs = ps.executeQuery();
			System.out.println("Start:");
			logger.info(" MutlipleTechnical Start");
			while (rs.next()) {
				String code = rs.getString("code");
				String modes = "";
				ps1.setString(1, code);
				
			
				logger.info(" MutlipleTechnical code:"+code);
				ResultSet rs1 = ps1.executeQuery();
				boolean mode24=false;
				boolean mode6=false;
				while (rs1.next()) {
					modes = modes + " . " + TechStrAccess.getModeString(rs1.getInt("mode"));
					if(rs1.getInt("mode") == 24 )mode24=true;
					if(rs1.getInt("mode") == 6 )mode6=true;
				
				}

					if (mode24 & mode6 ) {
						addRecord(code, modes, 101);
					} else {
						addRecord(code, modes, 100);
					}

			}
		//	addRecord("tet.ax", "test", 100);
			ps.close();
			ps1.close();
	
	}
	
	
	
	
	
	
	public void run() {
		try {

			double percent = 0.05;
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String date = dateFormat.format(new Date());
			System.out.println("1 ");

			logger.info("MutlipleTechnical run 1");

			addDataX();
			close();
			logger.info(" MutlipleTechnical SUCCESS");

		} catch (Exception e) {
			logger.severe("MutlipleTechnical Error run: " + e);
		} 

	}
	
	
public static void main(String[] args) {

	new MutlipleTechnical().run();

		  }
	
	
	
}



