package Excel;


import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.MyDatabase;
import access.DataAccess;

import factory.DAOFactoryData;
import util.ExcelConfig;
import util.ExcelLogger;
import util.FormatUtil;
/**
 * Read data from Excel 
 * @author rowan
 *
 */
public class  Data  extends MyDatabase{
//	FileInputStream file=null;
	
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	Vector<File> vecfile;
		Statement stmt =null;
		PreparedStatement ps = null;
		ArrayList<DataAccess>arr ;
		Data(){
		
		
		try {
			vecfile = new Vector<File> ();
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet1001.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet2001.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet3001.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet4001.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet5001.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet6001.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheetNew.xlsm"));
				
			arr = new 	ArrayList<DataAccess>();
			
			
			logger = ExcelLogger.getLogger();
			
			System.out.println("Data rUN    ");	
			logger.info("Data");	
			
			    
				 logger.info("Data ok");	    
				 stmt= con.createStatement(); 
				
			   
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			 logger.severe("Data Error initialize:"+e);	    
		}	
	}

	
	public boolean check(String code,String date)throws Exception {
		
	
		String mysql = "SELECT code FROM fortune.data where code ='"+code+"'"+"and date='"+date+"'";
	      ResultSet rs = stmt.executeQuery(mysql);
	  
	      return rs.next();
	
	}
	
	
	
	public void excel()throws Exception  {
		  
		    
		   
		   String mcode;
		   String date;
		 
		   String open;
		   String high;
		   String low;
		   String close;
		  
		   String  closeVol;
		   String vol;
		   String change;
		   String changePercent;
		  String previousClose;
		  
		   String  fiftydMA;
		   String fiftyMAchg;
		   String twohundreddMA;
		   String twohundredMAchg;
		  String avg3mthVol;
		  
		   
		  sql="INSERT INTO data (code,date,open,high,low,close,volume,changes,changePercent,previousClose,Avg3mth,fifty,fiftychg,twohundred,twohundredchg) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )"; 		

		  
		  ps =
		            con.prepareStatement(sql);
		  
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Iterator<Row> rowIterator = sheet.iterator();
		   System.out.println("cell NEW:    ");
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		       // System.out.println("cell t0:    "+row.getCell(0) );
		        if(row.getCell(0) == null)break;
		  
		        String code = (String)row.getCell(0).toString();
		        date=dateFormat.format(new Date());
		        if(code !=null  ){
		        	if((code.endsWith("AX")|| code.startsWith("^") ) && !check(code,date)   ){
		        		//System.out.println("cell info:    "+row.getCell(0)+"\n");
		        		//System.out.println("cell name:    "+row.getCell(2)+"\n"); 
		        		
		        		   mcode=code;
		        		   System.out.println("cell t2:    ");
		       		   
		       		    
		       		 System.out.println("cell t1:    "+row.getCell(2));
		       	/*	  
		       		open=row.getCell(7).toString();;
		       		//System.out.println("cell t2:    ");
		       		high=row.getCell(8).toString();;
		       		low=row.getCell(9).toString();;
		       	//	System.out.println("cell t3:    ");
		       		change=row.getCell(10).toString();;
		       		//System.out.println("cell t4:    ");
		       		changePercent=row.getCell(11).toString();;
		       		close=row.getCell(28).toString();;
		       		//System.out.println("cell t5:    ");
		       		closeVol=row.getCell(29).toString();;
		       		vol=row.getCell(30).toString();;
		       		
		       	//	System.out.println("cell t6:    ");
		       		previousClose	=row.getCell(31).toString();
		       	 fiftydMA= ExcelUtil.cleanStringNumber( row.getCell(23).toString());;
	       		    fiftyMAchg=ExcelUtil.cleanStringNumber(row.getCell(24).toString());;
	       		    twohundreddMA=ExcelUtil.cleanStringNumber(row.getCell(25).toString());;
	       		    twohundredMAchg=ExcelUtil.cleanStringNumber(row.getCell(26).toString());;
	       		 avg3mthVol	=ExcelUtil.cleanStringNumber(row.getCell(27).toString());
	       		 
	       		 
	       		 
	       		 
	       		 
	            	name=row.getCell(2).toString();;
		    		*/
		       		 
		        	open=row.getCell(3).toString();;
		       		
		       		high=row.getCell(4).toString();;
		       		low=row.getCell(5).toString();
		       		close=row.getCell(6).toString();;
		       		
		       		vol=row.getCell(7).toString();;
		       		
		       		change=row.getCell(8).toString();;
		       		
		       		changePercent=row.getCell(9).toString();;
		       		previousClose	=row.getCell(10).toString();
		       		
		       		avg3mthVol	=row.getCell(11).toString();
		       		fiftydMA=  row.getCell(12).toString();;
		       	
		       		fiftyMAchg=row.getCell(13).toString();;
		       		twohundreddMA=row.getCell(14).toString();
		       		
		       		twohundredMAchg=row.getCell(15).toString();;
		       		
		       		double chgPercent = Double.parseDouble(changePercent);
		       		double myvol = Double.parseDouble(vol);
		       		
		       		double avgVol =FormatUtil.convertNumberFormat(avg3mthVol); 
		       		
		       		double fifty=FormatUtil.convertNumberFormat(fiftydMA);		
		       		double fiftychg=FormatUtil.convertNumberFormat(fiftyMAchg);		
		       		double twohund=FormatUtil.convertNumberFormat(twohundreddMA);
		       		double twohundchg=FormatUtil.convertNumberFormat(twohundredMAchg);
		       		
		       		double highh=FormatUtil.convertNumberFormat(high);
		       		double lowl=FormatUtil.convertNumberFormat(low);
		    		double openl=FormatUtil.convertNumberFormat(open);
		       		
		       		
		    		DataAccess dt = new DataAccess();
		    		dt.setCode(mcode);
		    		dt.setDate(new Date());
		    		dt.setClose(Double.parseDouble(close));
		    		arr.add(  dt       );
	       		 
	      
		    ps.setString(1, mcode);
		    ps.setString(2, date);
			ps.setDouble(3,openl);  
			ps.setDouble(4,highh);  
			ps.setDouble(5,lowl);  
			ps.setDouble(6,Double.parseDouble(close) );     
			ps.setDouble(7, myvol );     
			ps.setDouble(8,Double.parseDouble(change) );     
		    ps.setFloat(9, Float.parseFloat(changePercent));    
		    ps.setString(10, previousClose);   
		    ps.setDouble(11, avgVol );
		    
		    ps.setDouble(12,fifty);  
			ps.setDouble(13,fiftychg);  
			ps.setDouble(14,twohund);  
			ps.setDouble(15,twohundchg );  
		        
		        //double dont need quotes
//		        sql="INSERT INTO data (code,date,open,high,low,close,closeVol,volume,changes,changePercent,previousClose,Avg3mth,fifty,fiftychg,twohundred,twohundredchg) VALUES ( '" + mcode+"',"+ "'"+date+"',"+     "'"+open+"',"+
	//					"'"+high+"',"+"'"+low+"',"+"'"+close+"','"+closeVol+"',"+"'"+vol+"',"+"'"+change+"',"+"'"+changePercent+"'"+ ",'"+previousClose+"'"+",'"+avg3mthVol+"'"+",'"+fiftydMA+"'"+",'"+fiftyMAchg+"'"+",'"+twohundreddMA+"'"+",'"+twohundredMAchg+"'"           +"    )"; 		
	        
		        
		        		//System.out.println("cell info:    "+ps+"\n");	
			System.out.println("cell info:    "+mcode+":"+mcode.length()+              "\n");	
		        		ps.addBatch();
		        		
		        	
		        	
		        		
		        	
		        	}
		        		
		        		
		        		
		        		
		        	
		        	
		        	
		        	
		        	
		        }else{break;}
		   
		   
	}
			
	}
	
	public void run (){
		try {
			
			for(int x=0;x< vecfile.size();x++  ){
				File file=(File)vecfile.elementAt(x);
				System.out.println("1 ");
				logger.info("Data run file: "+file.getName());	
				System.out.println("1.1 "+file.getName()+":"+file.getAbsolutePath());	
				//Get the workbook instance for XLS file 
				workbook = new XSSFWorkbook(file);
				System.out.println("2 ");
				//Get first sheet from the workbook
				 sheet = workbook.getSheetAt(0);
				 System.out.println(sheet.getSheetName());
				 logger.info("Data run 1");	
				 excel();
				 logger.info("Data run 2");	
				 ps.executeBatch();
				
				 con.commit();
				 workbook.close();
			
				 logger.info(" DATA SUCCESS");
			}
		     //updates the 20d and 75d MA
			try(DAOFactoryData dt= new DAOFactoryData()) {
				logger.info(" DATA AVERAGE RUN");
				System.out.println("  DATA AVERAGE RUN");
				 dt.updateDataNewAverages(arr);
				System.out.println("  DATA AVERAGE SUCCESS");
				 logger.info(" DATA AVERAGE SUCCESS");
				 
			} catch (Exception e) {
				System.out.println("Error run update new AVG "+e);
				 logger.severe("Data Error run update new AVG "+e);	
			}  
			   
				 
				 
			
			
		
			
			
		} catch (Exception e) {
			System.out.println("Error run "+e);
			 logger.severe("Data Error run "+e);	
			 try {
				con.rollback();
			} catch (Exception e2) {
				System.out.println("Error rollback : "+e2);
				 logger.severe("Data Error rollback : "+e2);	
				
			}
		}finally{
			try{
				//file.close();
				//FileOutputStream outFile =new FileOutputStream(new File("D:\\Java\\Excel\\bin\\FinanceSpreadsheet300.xlsm"));
				//workbook.write(outFile);
				///outFile.close();
				stmt.close();
				ps.close();
				con.close();  
				System.out.println(" finish closing]s :");
				
				 logger.info("Data finish closing]s ");	
				 
			}catch (Exception e){
				System.out.println(" error closing]s :"+e);
				logger.severe("Data finish closing]s error: " +e);	
			}
		}
	
	
	
	}
	
	
public static void main(String[] args) {
		
			
	new Data().run();
		
			   //get current date time with Date()

		
		  }
	
	
	
}

