

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.Database;
import util.ExcelLogger;
public class  Tech {
//	FileInputStream file=null;
	Connection con=null;
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	Vector vecfile;
	 Logger logger = null;
		Statement stmt =null;
		Statement stmt2=null;
		
	Tech(){
		
		
		try {
			vecfile = new Vector ();
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet100.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet200.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet300.xlsm"));
			
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet1.xlsm"));
			logger = ExcelLogger.getLogger();
					
			logger.info("Tech");	
				 con= Database.getConnection();
			    
			    
				 con.setAutoCommit(false);
				 stmt= con.createStatement(); 
				 stmt2=				 con.createStatement();  
			   //  con=DriverManager.getConnection(  
				//		"jdbc:mysql://localhost:3306/fortune?autoReconnect=true&useSSL=false","root","rowm0ng1");  
				
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			logger.severe("Tech error :"+e);	
		}	
	}
	
	/*
	
	public void execute()throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    		System.out.println("ok execute :    ");	
    		stmt.close();
    		
			
	
	}
	*/
	public boolean check(String code,String date)throws Exception {
		
	
		String mysql = "SELECT code FROM fortune.technical where code ='"+code+"'"+"and date='"+date+"'";
	      ResultSet rs = stmt.executeQuery(mysql);
	    
	      return rs.next();
	}
	
	
	
	public void excel()throws Exception  {
		  
		    
		   
		   String mcode;
		   String date;
		 
		   String yearwlchg;
		   String yearwhchg;
		   String yearwl;
		   String yearwh;
		  
		   String  fiftydMA;
		   String fiftyMAchg;
		   String twohundreddMA;
		   String twohundredMAchg;
		  String avg3mthVol;
		   
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
		        System.out.println("cell t1:Date :    "+date );  
		        if(code !=null ){
		        	if((code.endsWith(".AX")|| code.startsWith("^") )  && !check(code,date)   ){
		        		//System.out.println("cell info:    "+row.getCell(0)+"\n");
		        		//System.out.println("cell name:    "+row.getCell(2)+"\n"); 
		        		
		        		   mcode=code;
		        		   System.out.println("cell t2:    ");
		       		   
		       		    
		       		 System.out.println("cell t1:    "+row.getCell(2));
		       		  
		       	    yearwlchg=row.getCell(12).toString();;
	       		
	       		    yearwhchg=row.getCell(13).toString();;
	       		    yearwl=row.getCell(14).toString();;
	       		
	       		    yearwh=row.getCell(15).toString();;
	       		    
	       		     fiftydMA=row.getCell(23).toString();;
	       		    fiftyMAchg=row.getCell(24).toString();;
	       		    twohundreddMA=row.getCell(25).toString();;
	       		    twohundredMAchg=row.getCell(26).toString();;
	       				
	       		 avg3mthVol	=row.getCell(27).toString();
		        
		        
		        sql="INSERT INTO technical (code,date,yearLowchg,yearHighchg,yearLow,yearHigh,fiftyD,fiftyDchg,twoHundredD,twoHundredDchg,avg3mthVol) VALUES ( '" + mcode+"',"+ "'"+date+"',"+     "'"+yearwlchg+"',"+
		        								"'"+yearwhchg+"',"+"'"+yearwl+"',"+"'"+yearwh+"','"+fiftydMA+"',"+"'"+fiftyMAchg+"',"+"'"+twohundreddMA+"',"+"'"+twohundredMAchg+"'"+ ",'"+avg3mthVol+"'"   +")"; 		

		        
		        
		        		System.out.println("cell info:    "+sql+"\n");	
		        		stmt.executeUpdate(sql);
		        	
		        	
		        		
		        	
		        	}
		        		
		        		
		        		
		        		
		        	
		        	
		        	
		        	
		        	
		        }else{break;}
		   
		   
	}
	}
	
	public void run (){
		try {
			
			for(int x=0;x< vecfile.size();x++  ){
				File file=(File)vecfile.elementAt(x);
				System.out.println("1 ");
				logger.info("Tech run ");	
				logger.info("Tech run file: "+file.getName());	
				//Get the workbook instance for XLS file 
				workbook = new XSSFWorkbook(file);
				System.out.println("2 ");
				//Get first sheet from the workbook
				 sheet = workbook.getSheetAt(0);
				 System.out.println(sheet.getSheetName());
				 excel();System.out.println("Commit Tech  ");
				 logger.info("Tech commit ");	
				 con.commit();
				 logger.info("Tech commit ok");	
				 System.out.println("Commit  ok !!");
				 workbook.close();
				 logger.info(" TECH SUCCESS");
			
			}
		     
			  
			   
				 
				 
			
			
		
			
			
		} catch (Exception e) {
			System.out.println("Error run "+e);
			try {
				 System.out.println("RollBack Tech ");
				 logger.info("Tech RollBack ");	
				con.rollback();
				System.out.println("RollBack ok !!!! ");
				logger.info("Tech RollBack  ok");	
			} catch (Exception e2) {
				System.out.println("Roll back error  "+e);
				logger.severe("Tech RollBack  error:"+e);	
			}
		}finally{
			try{
				//file.close();
				//FileOutputStream outFile =new FileOutputStream(new File("D:\\Java\\Excel\\bin\\FinanceSpreadsheet300.xlsm"));
				//workbook.write(outFile);
				///outFile.close();
				stmt.close();
				stmt2.close();
				con.close();  
				System.out.println(" finish closing]s :");
				logger.info("Tech close  ok");
			}catch (Exception e){
				System.out.println(" error closing]s :"+e);
				logger.severe("Tech close  error "+e);
			}
		}
	
	
	
	}
	
	
public static void main(String[] args) {
		
			
	new Tech().run();
		
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		   
	
		        	
		        	
		        	
		        	
		   
		    
	

		    /*
		    int totalz=sheet.getPhysicalNumberOfRows();
		    System.out.println(sheet.getPhysicalNumberOfRows());	
		    for (int i =1 ; i< totalz;i++){
		    	 System.out.println("cell info:    "+sheet.getRow(i).getCell(1)      +"\n");	
		    }
		    
		    */
		    /*
		    Iterator<Row> rowIterator = sheet.iterator();
		    
		    int total=0;
		    
		  
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        Iterator<Cell> cellIterator = row.cellIterator();
		        System.out.println("cell info:    "+row.getCell(1)+"\n");	
		        total++;
		     /*
		        
		        while(cellIterator.hasNext()) {
		             
		            Cell cell = cellIterator.next();
		             
		            switch(cell.getCellType()) {
		                case Cell.CELL_TYPE_BOOLEAN:
		                    System.out.print(cell.getBooleanCellValue() + "\t\t");
		                    break;
		                case Cell.CELL_TYPE_NUMERIC:
		                    System.out.print(cell.getNumericCellValue() + "\t\t");
		                    break;
		                case Cell.CELL_TYPE_STRING:
		                    System.out.print(cell.getStringCellValue() + "\t\t");
		                    break;
		            }
		        }
		        
		        System.out.println("");
		        
		    }*/
		   
		    
		    //System.out.println("total is :"+total);
		
		
		  }
	
	
	
}
