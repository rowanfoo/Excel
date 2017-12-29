


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




public class  BreakAboveOneDollarStr extends MyDatabase{ 
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
//	SELECT code,count(*) as m  FROM fortune.data where  Year(date)=2016 and close <1 and close >0.5 group by code having m>200

	//codes below 1$ added on Dec 13.
String codes[]={
		"8IH.AX",
		"AJX.AX",
		"AMH.AX",
		"AVJ.AX",
		"AWE.AX",
		"BFG.AX",
		"BGL.AX",
		"BPT.AX",
		"CDD.AX",
		"CGS.AX",
		"DRM.AX",
		"EAI.AX",
		"EGH.AX",
		"EVO.AX",
		"EZL.AX",
		"FLK.AX",
		"FRI.AX",
		"FXJ.AX",
		"GDI.AX",
		"IFM.AX",
		"LNG.AX",
		"MML.AX",
		"MRN.AX",
		"OVH.AX",
		"PAI.AX",
		"PGC.AX",
		"PGF.AX",
		"RXP.AX",
		"SFH.AX",
		"SPL.AX",
		"TGP.AX",
		"TOP.AX",
		"TTC.AX",
		"WIC.AX",
		"WTP.AX",
		"YOW.AX"};

		
		


	public void addDataX() throws Exception {

		// DAOFactoryDataReport

		int yes = 0;

		
		String mysql = "SELECT * FROM fortune.data where code=? and date=current_date()";
		
		
		String mysql1 ="INSERT INTO techstr (code,date ,mode,close) VALUES (?,?,20,?)"  ; 
			PreparedStatement ps = con.prepareStatement(mysql);
			PreparedStatement ps1 = con.prepareStatement(mysql1);
			
			for(String code:codes){
			ps.setString(1, code);
			
			
			
		
					ResultSet rs = ps.executeQuery();
					//System.out.println("found:  1:"+ps );
					while (rs.next()) {
						double close =rs.getDouble("close");
						if(close >= 0.96 ){
							//System.out.println("found:  1:"+rs.getString("code") );
							
							ps1.setString(1, rs.getString("code"));
						ps1.setString(2, LocalDate.now().toString());
						ps1.setDouble(3, rs.getDouble("close"));
						ps1.addBatch();

						}
						
					
					}
				
			}
			System.out.println("found:  FINISH :" );
				ps1.executeBatch();
				System.out.println("found:  FINISH  1:" );	
				

			ps.close();
			ps1.close();

		
	}



		
	
	
	
	public void run() {
		try {

			logger.info("BreakAboveOneDollarStr run 1");
			System.out.println("fBreakAboveOneDollarStr  1:");
			addDataX();
			close();

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("BreakAboveOneDollarStr Error run " + e);

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
	new BreakAboveOneDollarStr().run();
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}



