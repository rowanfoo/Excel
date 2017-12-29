package Excel;






import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.MyDatabase;
import util.ExcelLogger;




public class  ChangePercentExcelInput  extends MyDatabase{
//	FileInputStream file=null;
	
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	
	 
	
	 ChangePercentExcelInput(){
		
		
		try {
			
			
			
			System.out.println("Data rUN    ");	
			logger.info("Data");	
			
				
			    
				 logger.info("Data ok");	    
				 con.setAutoCommit(false);
			   
			
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
	
	

		
		
	      
	
	
	
	
	
	
	
	public void excel()throws Exception  {
		  
		
		
		
		
		
		
	    
		 
		   String mcode;
		   String date;
		   double  fiftydAvg;
		   double fiftydAvgPercent; 
		   double  twohddAvg;  
		   double twohdAvgPercent;
		  
		   double vol;
		   
		 //  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Iterator<Row> rowIterator = sheet.iterator();
		   System.out.println("cell t000:    " );
		    
		   while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        System.out.println("cell t0:    "+row.getCell(0) );
		        if(row.getCell(0) == null)break;
		  
		        String code = (String)row.getCell(0).toString();
		       
		        if(code !=null ){
		        	if((code.endsWith(".AX")|| code.startsWith("^") )){
		        		
		        		
		        		//System.out.println("cell info:    "+row.getCell(0)+"\n");
		        		//System.out.println("cell name:    "+row.getCell(2)+"\n");
		        		
		        		 
		        		   
		       		    date=row.getCell(1).toString();
		       		// System.out.println("cell date :    "+date );
		        		
		       		
		       		 //System.out.println("t1 :    ");
		       		   if(row.getCell(4)==null|| (row.getCell(4).getCellType()==Cell.CELL_TYPE_ERROR ) )fiftydAvg=0;
		       		   else fiftydAvg=row.getCell (4).getNumericCellValue() ;
		       	//	System.out.println("t2 :    ");  
		       		if(row.getCell(5)==null || (row.getCell(5).getCellType()==Cell.CELL_TYPE_ERROR )) fiftydAvgPercent=0;
		       		   else{
		       			
		       			   fiftydAvgPercent=row.getCell (5).getNumericCellValue() ;
		       		   }
		       		
		       	//	System.out.println("t3 :    "); 
		       		if(row.getCell(6)==null|| (row.getCell(6).getCellType()==Cell.CELL_TYPE_ERROR)  )twohddAvg=0;
		       		   else twohddAvg=row.getCell (6).getNumericCellValue() ;
		       	//	System.out.println("t4 :    "); 
		       		if(row.getCell(7)==null|| (row.getCell(7).getCellType()==Cell.CELL_TYPE_ERROR)  )twohdAvgPercent=0;
		       		   else twohdAvgPercent=row.getCell (7).getNumericCellValue() ;
		       //		System.out.println("t5 :    ");   
		       		if(row.getCell(8)==null|| (row.getCell(8).getCellType()==Cell.CELL_TYPE_ERROR)  )vol=0;
		       		   else vol=row.getCell (8).getNumericCellValue() ;
		       		   
		       //		System.out.println("t6 :    ");
		       		   
		       		   
		       		   
		       		   
		       		// System.out.println("vol :    "+vol);
		       		   
		       		 //System.out.println("cell t1:    "+row.getCell(2));
		       		
		       		 
		       		 
		       // sql="INSERT INTO stock (code,date,name,descp,shares,marketCap,top) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+marketCap+"',"+"'"+top+"')"; 		
			      
		        
		     //   sql="INSERT INTO fundamental (code,date,name,descp,shares) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+yearwlchg+"',"+"'"+yearwhchg+"',"+"'"+yearwl+"',"+"'"+yearwh+"',"+"'"+marketCap+"',"+"'"+fiftydMA+"',"+"'"+fiftyMAchg+"',"+"'"+twohundreddMA+"',"+"'"+twohundredMAchg+"',"+"'"+top+"',"+"'"+pe+"',"+"'"+eps+"',"+"'"+ebitda+"'" +")"; 		

		        
		       // yearLowchg,yearHighchg,yearLow,yearHigh,marketCap,fiftyMA,fiftyMAchg,twoHundredMA,twoHundredMchg,top,pe,eps,ebitda
		        
		       		
		        
		        sql=" UPDATE data SET Avg3mth='"+vol+"',"+"50dAvg="+fiftydAvg+", 50dAvgPercent="+fiftydAvgPercent+" ,200dAvg= " + twohddAvg+" , 200dAvgPercent=" +twohdAvgPercent+"                   WHERE code='"+code+"' and date='"+date+"'"; 
		        
		        		System.out.println("cell info:    "+sql+"\n");	
		        		 execute();	
		        	
		        	
		        		
		        	
		        	}
		        		
		        		
		        		
		        		
		        	
		        	
		        	
		        	
		        	
		        }else{break;}
		   
		   
	}
	}
	
	public void run (String index){
		try {
			
			
			System.out.println("INDEX: "+index);
				//File file= new File ("d:\\Java\\Excel\\bin\\changePercent.xlsx");
				FileInputStream file = new FileInputStream(new File("d:\\Java\\Excel\\bin\\changePercent"+index+".xlsx"));
				 logger.info("File to Open: "+"d:\\Java\\Excel\\bin\\changePercent"+index+".xlsx");	
				System.out.println("1 ");
				
				
				//Get the workbook instance for XLS file 
				workbook = new XSSFWorkbook(file);
				System.out.println("2 ");
				//Get first sheet from the workbook
				//sheet = workbook.getSheetAt(0);
				 //System.out.println(sheet.getSheetName());
				 logger.info("Data run 1");	
				 
				 
				 //test on one sheet firts 
				//sheet = workbook.getSheetAt(0);
				 //excel();
					
					//workbook.getNumberOfSheets()
					
					for(int x =0 ; x < workbook.getNumberOfSheets();x++ ){
						 sheet = workbook.getSheetAt(x);
				 		excel();
					
					}
					
					
					logger.info("ChangePercentInput :   START COMMIT ");		
				 con.commit();
				 logger.info("ChangePercentInput :   COMMIT OK !!!!");	
				 file.close();
					

				 logger.info("File done: "+"d:\\Java\\Excel\\bin\\changePercent"+index+".xlsx");	

					
				 
				 workbook.close();
			
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

			if(args.length > 0  )new ChangePercentExcelInput().run(args[0]);
			else new ChangePercentExcelInput().run("1");
			
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		    
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}


