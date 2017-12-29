package Excel;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Logger;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.MyDatabase;
import util.ExcelLogger;
public class  ChangePercentExcel  extends MyDatabase{
//	FileInputStream file=null;
	
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	
	ArrayList<String> codes;
	 ChangePercentExcel(){
		
		
		try {
			
			codes=new ArrayList<String>();
			System.out.println("Data rUN    ");	
			logger.info("Data");	
			
			   
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			 logger.severe("Data Error initialize:"+e);	    
		}	
	}
	
	public void execute()throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		System.out.println("ok execute :    ");	
    		
			
	
	}
	
	
	
	public void  getCode()throws Exception {
		Statement stmt= con.createStatement(); 
	
		String mysql = "SELECT code FROM fortune.stock where top='top1b'";;
	      ResultSet rs = stmt.executeQuery(mysql);
	    
	      while(rs.next()){
	    	  codes.add(rs.getString(1));
	      }
	      
	      
	
	}

	
	public void  excell()throws Exception {
		Statement stmt= con.createStatement(); 
	String date;
	String code ;
	double volume;
	double close;
	//319
		//for (int x=200; x<319    ;x++){
		//for (int x=100; x<200    ;x++){
		//for (int x=0; x<100    ;x++){
	int count =70;
	
	int name=0;
			for (int x=0; x<codes.size()    ;x++){
			 code =(String) codes.get(x);
			
			 if(x > count ){
				 closeNoteBook(name);
				 
				 openNoteBook(++name );
				 count=count+50;
			 }
			 
			 
			 XSSFSheet mySheet =  workbook.createSheet(code);
			String mysql = "SELECT * FROM fortune.data where code='"+code+"'" ;
			
			 System.out.println("my mysql:"+mysql);
	    	
			
			
			ResultSet rs = stmt.executeQuery(mysql);
			int rowi=0;
			  while(rs.next()){
				  System.out.println("my mysql:");
				  XSSFRow row = mySheet.createRow(rowi++);
		    	 date=rs.getString("date");
		    	// System.out.println("my date:"+date);
		    	 volume=rs.getDouble("volume");
		    	 
		    	 close=rs.getDouble("close");
		    	 
		    	 row.createCell(0).setCellValue(code);
		    	 row.createCell(1).setCellValue(date);
		    	 row.createCell(2).setCellValue(close);
		         row.createCell(3).setCellValue(volume);
		      
		         
		         
		         
			  
			  
			  
			  }
		
		}
		
			 closeNoteBook(name);
		
		
	      
	
	}
	
	FileInputStream file ;
	public void openNoteBook(int x )throws Exception{
		 file = new FileInputStream(new File("d:\\Java\\Excel\\bin\\changePercent"+x+".xlsx"));
		
			//Get the workbook instance for XLS file 
			workbook = new XSSFWorkbook(file);	
			
	}
	
	public void closeNoteBook(int x )throws Exception{
		file.close();
		
		 FileOutputStream outFile =new FileOutputStream(new File("d:\\Java\\Excel\\bin\\changePercent"+x+".xlsx"));
			workbook.write(outFile);
			outFile.close();	
			 workbook.close();	
	}
	
	public void run (){
		try {
			
			
				//File file= new File ("d:\\Java\\Excel\\bin\\changePercent.xlsx");
				//FileInputStream file = new FileInputStream(new File("d:\\Java\\Excel\\bin\\changePercent2.xlsx"));
			//	System.out.println("1 ");
				
			 openNoteBook(0 );
				//Get the workbook instance for XLS file 
				//workbook = new XSSFWorkbook(file);
				System.out.println("2 ");
				//Get first sheet from the workbook
				// sheet = workbook.getSheetAt(0);
				 //System.out.println(sheet.getSheetName());
				 logger.info("Data run 1");	
				  getCode();
					System.out.println("code size: "+codes.size());
				// excel();
					excell();
				 logger.info("Data run 2");	
				 con.commit();
				// file.close();
					
				
			
				 logger.info(" DATA SUCCESS");
		
		     
			  
			   
				 
				 
			
			
		
			
			
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
		
			
	new ChangePercentExcel().run();
		
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		   
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}

