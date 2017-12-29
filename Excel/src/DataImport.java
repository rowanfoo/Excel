


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.MyDatabase;
import access.DataAccess;
import factory.DAOFactoryData;
import util.ExcelLogger;
import util.MovingAverage;

public class  DataImport  extends MyDatabase{
//	FileInputStream file=null;
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	//HashSet<String> codes;	
	 Statement statement = null;
	 
	
	DataImport(){
		
		
		try {
			
			//codes = new HashSet<String>();	
			System.out.println("Data rUN    ");	
			logger.info("DataImport");		
			
				 statement =  con.createStatement();  	
			logger.info("DataImport  ok ");	   
			    
		
			   
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			logger.severe("DataImport  err: "+e);	   
		}	
	}
	
	public void execute()throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		System.out.println("ok execute :    ");	
    		
			
	
	}
	
	public boolean check(String code,String date)throws Exception {
		Statement stmt= con.createStatement(); 
	
		String mysql = "SELECT code FROM fortune.data where code ='"+code+"'"+"and date='"+date+"'";
	      ResultSet rs = stmt.executeQuery(mysql);
	      return rs.next();
	}
	
	
	public void excel()throws Exception  {
		  
		    
		   
		   String mcode=sheet.getSheetName(); 
		   String date;
		 
		   String open;
		   String high;
		   String low;
		   String close;
		  
		  
		   String vol;
		  
		   
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   DateFormat dateFormat2 = new SimpleDateFormat("dd-MMM-yyyy");
		   
		   Iterator<Row> rowIterator = sheet.iterator();
		   System.out.println("cell NEW:    ");
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		       // System.out.println("cell t0:    "+row.getCell(0) );
		        if(row.getCell(0) == null)break;
		        System.out.println("cell NEW:   herer1  ");
		      
		       
		        
		        
		        //String mydate = (String)row.getCell(0).toString();
		      //  date=dateFormat.format(new Date());
		      // Date mdate = new Date ( Long.parseLong(row.getCell(0).toString() )     );
		    //    System.out.println("cell 0:    "+row.getCell(0) );
		       /// System.out.println("cell 0:    "+row.getCell(0).getCellType() );
		      //  Date dt = dateFormat2.parse(row.getCell(0).toString());
		       // System.out.println("cell 1:    "+dateFormat.format(dt ) );
		        System.out.println("cell NEW:   herer1 22 "+row.getCell(0).getCellType());
		        
		        if(row.getCell(0).getCellType() == 0  ){
		        //	System.out.println("cell NEW:   herer1 22 ");
		        	
		        	
		        	date=dateFormat.format(dateFormat2.parse(row.getCell(0).toString()));
		        	//if(!check(mcode,date)){
		        	if(true){
		        		//System.out.println("cell info:    "+row.getCell(0)+"\n");
		        		//System.out.println("cell name:    "+row.getCell(2)+"\n"); 
		        		
		        		
		        		   System.out.println("cell t2:    ");
		       		   
		       		    
		       		 System.out.println("cell t1:    "+row.getCell(2));
		       		  
		       		open=row.getCell(1).toString();;
	       		
		       		high=row.getCell(2).toString();;
		       		low=row.getCell(3).toString();;
	       		
		       		close=row.getCell(4).toString();;
		       		
		       		vol=row.getCell(5).toString();;
	       				
		       	
		        
		        
		        sql="INSERT INTO data (code,date,open,high,low,close,volume) VALUES ( '" + mcode+"',"+ "'"+date+"',"+     "'"+open+"',"+
		        								"'"+high+"',"+"'"+low+"',"+"'"+close+"',"+"'"+vol+"'" +")"; 		

		        
		        
		        		System.out.println("cell info:    "+sql+"\n");	
		        		 statement.addBatch(sql);
		       // 		 execute();	
		        		
		        	
		        	
		        	}
		        	
		        	
		        		
		        		
		        		
		        		
		        	
		        	
		        	
		        	
		        	
		        }
		        //else{break;}
		   
		   
	}
	}
	
	
	public HashSet<String> getCode()throws Exception {
		Statement stmt= con.createStatement(); 
		HashSet <String> setcode= new HashSet<String>();
		
		//String mysql = "SELECT code FROM fortune.stock where date=curdate() ";
		String mysql = "SELECT code FROM fortune.stock ";// temp
	      ResultSet rs = stmt.executeQuery(mysql);
	      while(rs.next()){
	    	  setcode.add(rs.getString("code" )  );
	    	  
	      }
	
	      return setcode;
	      
	}
	
	
	public void run (){
		try {
			//note this OUTDATED !!!!!!!
			/*
				//File file= new File("d:\\Java\\Excel\\bin\\FinanceSpreadsheet-New.xlsm");
				File file= new File(ExcelConfig.excelDir+"MultipleStock600.xlsm");
				//System.out.println("okk: "+ExcelConfig.excelDir+"FinanceSpreadsheet100.xlsm");
				System.out.println("1.1 ");  
				logger.info("DataImport  run1 ");	  
				
				//Get the workbook instance for XLS file 
				workbook = new XSSFWorkbook(file);
				System.out.println("2 ");
				logger.info("DataImport  run2 ");	
				
				//Get first sheet from the workbook
				 int sheetno =   workbook.getNumberOfSheets();
				 HashSet<String> coderun=   getCode();
				 ArrayList<String>mycodes=new ArrayList<String>();
				 
				 System.out.println("CODES:"+coderun );
				 for(int x=1 ; x<sheetno;x++   ){
				
					 sheet = workbook.getSheetAt(x);
					// System.out.println(sheet.getSheetName());
					 if(coderun.contains(sheet.getSheetName().toUpperCase() ) ){
						 System.out.println("INSERT THIS CODE "+sheet.getSheetName());
						 mycodes.add(sheet.getSheetName().toUpperCase() );
						excel();
						 
					 }
					
							 
					 
					 
					 
					 
				 }
				 statement.executeBatch();
				 System.out.println("Insert all date done ,");
				 System.out.println("Insert all date done will calc 50d avg");
				 
			
				 
				 
				 logger.info("DataImport  COMMIT ");	
				 con.commit();
				 System.out.println("COMMITED");
				 logger.info("DataImport  COMMIT OK !!!! ");	
				 workbook.close();
				 logger.info(" DATAIMPORT SUCCESS");
			*/
			
			
			 HashSet<String> mycodes=  getCode();
			 
			
	
				 
				 //calc average for all codes.
				 try(DAOFactoryData dao = new DAOFactoryData()) {
						
						
					 for(String key:mycodes   ){
					 
					 ArrayList <DataAccess> arr= dao.getStock(key);
					 
							
					 System.out.println("DAOFactoryData arr :"+arr.size());		
							
					 new  MovingAverage(50,   arr).run() ;
					 new  MovingAverage(200,   arr).run() ;
					 new  MovingAverage(60,   arr).run() ;
					// new  MovingAverage(20,   arr).run() ;
					 new  MovingAverage(75,   arr).run() ;
					 
					 System.out.println("DAOFactoryData over ");		
					 dao. updateDataAveragesImport(arr);
					
					 dao.updateDataNewAveragesImport(arr);
					 
					 }
					 
					System.out.println("DataImport FINISH");
				 
				 } catch (Exception e) {
					System.out.println("ERROR :"+e);  
				}
				 
			  
			    
			  
			   
				 
				 
			
			
		
			
			
		} catch (Exception e) {
			System.out.println("Error run "+e);
			
			try {
				System.out.println("ROLL BACK ");
				logger.info("DataImport  ROLL BACK ");	
				con.rollback();
				System.out.println("ROLL BACK OK !!!!");
				logger.info("DataImport  ROLL BACK OK!!!!");	
			} catch (Exception e2) {
				System.out.println("Cant roll back "+e);
				logger.severe("DataImport  ROLL BACK EROOR: "+e);	
			}
			
			
		}finally{
			try{
				//file.close();
				//FileOutputStream outFile =new FileOutputStream(new File("D:\\Java\\Excel\\bin\\FinanceSpreadsheet300.xlsm"));
				//workbook.write(outFile);
				///outFile.close();
				con.close();  
				System.out.println(" finish closing]s :");
				logger.info("DataImport  finish closing ");	
			}catch (Exception e){
				System.out.println(" error closing]s :"+e);
				logger.severe("DataImport  close error: "+e);	
			}
		}
	
	
	
	}
	
	
public static void main(String[] args) {
		
			
	new DataImport().run();
		
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		   
	
		        	
		        	

		
		
		  }
	
	
	
}

