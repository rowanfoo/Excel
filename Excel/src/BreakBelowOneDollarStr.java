



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import DB.MyDatabase;
import access.StockAccess;
import access.TechStrAccess;
import factory.DAOFactoryDataReport;
import factory.DAOFactoryStock;
import factory.DAOFactoryTechStr;
import util.ExcelLogger;
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




public class  BreakBelowOneDollarStr extends MyDatabase{ 
	/**
	 * 
	 * 
	 * 
	 * if today close price is lesser then last month low 
	 * and last month low is also lesser then last last month low 
	 * and last last last month low , then let me see it.
	 * 
	 * 
	 * 
	 */
//	
//	SELECT code,count(*) as m  FROM fortune.data where  Year(date)=2016 and close <1.70 and close > 1   group by code having m>200

	//codes below 1.70$ added on Dec 13.
String codes[]={
		"AHX.AX",
		"AJD.AX",
		"ALF.AX",
		"APZ.AX",
		"ASB.AX",
		"AST.AX",
		"AUF.AX",
		"AWC.AX",
		"BBG.AX",
		"CDM.AX",
		"CLH.AX",
		"CSV.AX",
		"CVC.AX",
		"CVO.AX",
		"DTL.AX",
		"DWS.AX",
		"EPW.AX",
		"FGG.AX",
		"FGX.AX",
		"FLN.ax",
		"FSA.AX",
		"GEG.AX",
		"GVF.AX",
		"HFR.AX",
		"HHV.AX",
		"IDX.AX",
		"KSC.AX",
		"MYR.AX",
		"NSR.AX",
		"PMC.AX",
		"QMS.AX",
		"QVE.AX",
		"RFF.AX",
		"RIC.AX",
		"RKN.AX",
		"RVA.ax",
		"RWH.AX",
		"SDG.AX",
		"SXL.AX",
		"TFC.AX",
		"TGG.AX",
		"WBA.AX",};

		
		


	public void addDataX() throws Exception {

		// DAOFactoryDataReport

		

		
		String mysql = "SELECT * FROM fortune.data where code=? and date=current_date()";
		
		
		String mysql1 ="INSERT INTO techstr (code,date ,mode,close) VALUES (?,?,?,?)"  ; 
			PreparedStatement ps = con.prepareStatement(mysql);
			PreparedStatement ps1 = con.prepareStatement(mysql1);
			
			for(String code:codes){
			ps.setString(1, code);
			
			
			
		
					ResultSet rs = ps.executeQuery();
					//System.out.println("found:  1:"+ps );
					while (rs.next()) {
						double close =rs.getDouble("close");
						if(close < 1 ){
						ps1.setString(1, rs.getString("code"));
						ps1.setString(2, LocalDate.now().toString());
						ps1.setDouble(3, 21);
						ps1.setDouble(4, rs.getDouble("close"));
						ps1.addBatch();

						}else if(close >1.93 ) {
							ps1.setString(1, rs.getString("code"));
							ps1.setString(2, LocalDate.now().toString());
							ps1.setDouble(3, 22);
							ps1.setDouble(4, rs.getDouble("close"));
							ps1.addBatch();
							
						
						
						}
						
						
					
					}
				
			}
				ps1.executeBatch();
				
				

			ps.close();
			ps1.close();

		
	}



		
	
	
	
	public void run() {
		try {

			logger.info("BreakBelowOneDollarStr run 1");

			addDataX();
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("BreakBelowOneDollarStr Error run " + e);
		}

	}
	
	
public static void main(String[] args) {

		
	/*		
			   //get current date time with Date()
	Date mydate= new Date();
	Calendar cal = Calendar.getInstance();
	cal.setTime(mydate);
	int month = cal.get(Calendar.YEAR);	 
	System.out.println("yr"+month);		   
		    
	System.out.println("yr"+(month-1));		    
		*/  
	new BreakBelowOneDollarStr().run();
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



