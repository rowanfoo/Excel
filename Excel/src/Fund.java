
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

import DB.MyDatabase;
import util.ExcelConfig;
import util.ExcelLogger;
public class  Fund  extends MyDatabase{
	
	
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	Vector vecfile;
	 
		Statement stmt =null;
		Statement stmt2=null;
	Fund(){
		try {
			vecfile = new Vector ();
			//vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet100.xlsm"));
			//vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet200.xlsm"));
			//vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet300.xlsm"));
			vecfile.add(new File(ExcelConfig.excelDir+"FinanceSpreadsheet1.xlsm"));
			
			
			logger.info("Fund");
				 
				 stmt= con.createStatement(); 
				 stmt2=	 con.createStatement();  
			   //  con=DriverManager.getConnection(  
				//		"jdbc:mysql://localhost:3306/fortune?autoReconnect=true&useSSL=false","root","rowm0ng1");  
				
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			logger.severe("Fund error :"+e);
		}	
	}
	
	/*
	public void execute()throws Exception {
		
		
			
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
			stmt.close();
    		System.out.println("ok execute :    ");	
    		
			
	
	}
	*/
	public boolean check(String code,String date)throws Exception {
		Statement stmt= con.createStatement(); 
		String mysql = "SELECT code FROM fortune.fundamental where code ='"+code+"'";
		
	      ResultSet rs = stmt.executeQuery(mysql);
	     
	      return rs.next();
	}
	
	
	
	public void excel()throws Exception  {
		  
		    
		 //   int total=0;
		   String mcode;
		   String date;
		 
		   String pe;
		   String eps;
		   String ebitda;
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Iterator<Row> rowIterator = sheet.iterator();
		   System.out.println("cell NEW:    ");
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        System.out.println("cell t0:    "+row.getCell(0) );
		        if(row.getCell(0) == null)break;
		  
		        String code = (String)row.getCell(0).toString();
		        date=dateFormat.format(new Date());
		        if(code !=null ){
		        	if((code.endsWith(".AX")|| code.startsWith("^") )&& !check(code,date)   ){
		        		//System.out.println("cell info:    "+row.getCell(0)+"\n");
		        		System.out.println("cell name:    "+row.getCell(2)+"\n");
		        		
		        		   mcode=code;
		        		//   System.out.println("cell t2:    ");
		       		    
		       		    
		       		 System.out.println("cell t1:    "+row.getCell(2));
		       		  
		       		    pe=ExcelUtil.cleanStringNumber(row.getCell(17).toString());;
		       		    eps=ExcelUtil.cleanStringNumber(row.getCell(20).toString());;
		       		    ebitda=ExcelUtil.cleanStringNumber(row.getCell(21).toString());
		       		 ;	
		        		
		       
		        
		        sql="INSERT INTO fundamental (code,date,Pe,eps,ebitda) VALUES ("+ "'"+mcode+"',"+"'"+date+"',"+   "'"+pe+"',"+"'"+eps+"',"+"'"+ebitda+"'" +")"; 		

		        
		       // yearLowchg,yearHighchg,yearLow,yearHigh,marketCap,fiftyMA,fiftyMAchg,twoHundredMA,twoHundredMchg,top,pe,eps,ebitda
		        
		        
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
				 System.out.println(file.getName());
				//Get the workbook instance for XLS file 
				workbook = new XSSFWorkbook(file);
				 //Get first sheet from the workbook
				 sheet = workbook.getSheetAt(0);
				 System.out.println(sheet.getSheetName());
				 excel();
				 System.out.println("Commit Fund  ");
				 logger.info("Fund Commit Fund ");
				 con.commit();
				 System.out.println("Commit  ok !!");
				 logger.info("Fund Commit Fund ok !!!");
				 workbook.close();
				 System.out.println(" FUND SUCCESS");
				 logger.info(" FUND SUCCESS");
			}
			
		} catch (Exception e) {
			System.out.println("Error run "+e);
			 logger.severe("Fund run error:"+e);
			try {
				 System.out.println("RollBack Fund ");
				 logger.info("Fund RollBack Fund ");
				con.rollback();
				System.out.println("RollBack ok !!!! ");
				logger.info("Fund RollBack Fund ok !!!!");
			} catch (Exception e2) {
				System.out.println("Roll back error  "+e);
				 logger.severe("Fund RollBack Fund error:"+e);
			}
		}finally{
			try{
				
				//FileOutputStream outFile =new FileOutputStream(new File("D:\\Java\\Excel\\bin\\FinanceSpreadsheet300.xlsm"));
				//workbook.write(outFile);
				///outFile.close();
				stmt.close();
				stmt2.close();
				con.close();  
				System.out.println(" finish closing]s :");
				logger.info("Fund finish closing]s");
			}catch (Exception e){
				System.out.println(" error closing]s :"+e);
				logger.severe("Fund finish closing]s:"+e);
			}
		}
	
	
	
	}
	
	
public static void main(String[] args) {
		
			
	new Fund().run();
		
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
