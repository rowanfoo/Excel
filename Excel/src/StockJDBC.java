import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DB.MyDatabase;
import util.ExcelConfig;
import util.ExcelLogger;

public class StockJDBC  extends MyDatabase{
	FileInputStream file=null;
	
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	
	Statement stmt =null;
	Statement stmt2=null;
	StockJDBC(){
		try {
			
			logger.info("StockJDBC");
			 file = new FileInputStream(new File(ExcelConfig.excelDir+"FinanceSpreadsheet1.xlsm"));
		     
			    //Get the workbook instance for XLS file 
				 workbook = new XSSFWorkbook(file);
			 
			    //Get first sheet from the workbook
				 sheet = workbook.getSheetAt(0);
			
			    
			    System.out.println(sheet.getSheetName());
		
			    
			   //  con=DriverManager.getConnection(  
				//		"jdbc:mysql://localhost:3306/fortune?autoReconnect=true&useSSL=false","root","rowm0ng1");  
				
				 stmt= con.createStatement(); 
				 stmt2=				 con.createStatement();  
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			logger.severe("StockJDBC error :"+e);
		}	
	}
	
	/*
	public void execute()throws Exception {
		
		
			
			Statement stmt=con .createStatement();  	
			stmt.executeUpdate(sql);
    		System.out.println("ok execute :    ");	
    		
			
	
	}
	*/
	public boolean check(String code)throws Exception {
	
		String mysql = "SELECT code FROM fortune.stock where code ='"+code+"'";
		
	      ResultSet rs = stmt.executeQuery(mysql);
	      return rs.next();
	}
	
	
	
	public void excel()throws Exception  {
		  

		
		
		
		
		
		
		    
	//	    int total=0;
		   String mcode;
		   String date;
		   String name;
		   String descp;
		   String shares;
		   String yearwlchg;
		   String yearwhchg;
		   String yearwl;
		   String yearwh;
		   String marketCap;
		   String  fiftydMA;
		   String fiftyMAchg;
		   String twohundreddMA;
		   String twohundredMAchg;
		   String top="top0.5b";
		   String pe;
		   String eps;
		   String ebitda;
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Iterator<Row> rowIterator = sheet.iterator();
		   
		    while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        //For each row, iterate through each columns
		        System.out.println("cell t0:    "+row.getCell(0) );
		        if(row.getCell(0) == null)break;
		  
		        String code = (String)row.getCell(0).toString();
		       
		        if(code !=null ){
		        	if((code.endsWith(".AX")|| code.startsWith("^") ) && !check(code) ){
		        		//System.out.println("cell info:    "+row.getCell(0)+"\n");
		        		//System.out.println("cell name:    "+row.getCell(2)+"\n");
		        		
		        		   mcode=code;
		        		   System.out.println("cell t2:    ");
		       		    date=dateFormat.format(new Date());
		       		 System.out.println("cell t1:Date :    "+date );  
		       		 System.out.println("cell t1:    "+row.getCell(2));
		       		    name=ExcelUtil.cleanString(row.getCell(2).toString());
		       		
		       		    descp="";
		       		    shares=ExcelUtil.cleanStringNumber(row.getCell(6).toString());
		       		    yearwlchg=row.getCell(12).toString();;
		       		
		       		    yearwhchg=row.getCell(13).toString();;
		       		    yearwl=row.getCell(14).toString();;
		       		
		       		    yearwh=row.getCell(15).toString();;
		       		    marketCap=row.getCell(16).toString();;
		       		     fiftydMA=row.getCell(23).toString();;
		       		    fiftyMAchg=row.getCell(24).toString();;
		       		    twohundreddMA=row.getCell(25).toString();;
		       		    twohundredMAchg=row.getCell(26).toString();;
		       		    pe=row.getCell(17).toString();;
		       		    eps=row.getCell(20).toString();;
		       		    ebitda=row.getCell(21).toString();
		       		 ;	
		       	  System.out.println("cell new CODE inserted:    "+mcode);
		       // sql="INSERT INTO stock (code,date,name,descp,shares,yearLowchg,yearHighchg,yearLow,yearHigh,marketCap,fiftyMA,fiftyMAchg,twoHundredMA,twoHundredMchg,top,pe,eps,ebitda) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+yearwlchg+"',"+"'"+yearwhchg+"',"+"'"+yearwl+"',"+"'"+yearwh+"',"+"'"+marketCap+"',"+"'"+fiftydMA+"',"+"'"+fiftyMAchg+"',"+"'"+twohundreddMA+"',"+"'"+twohundredMAchg+"',"+"'"+top+"',"+"'"+pe+"',"+"'"+eps+"',"+"'"+ebitda+"'" +")"; 		
		      
		        sql="INSERT INTO stock (code,date,name,descp,shares,marketCap,top) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+marketCap+"',"+"'"+top+"')"; 		
			      
		        
		     //   sql="INSERT INTO fundamental (code,date,name,descp,shares) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+yearwlchg+"',"+"'"+yearwhchg+"',"+"'"+yearwl+"',"+"'"+yearwh+"',"+"'"+marketCap+"',"+"'"+fiftydMA+"',"+"'"+fiftyMAchg+"',"+"'"+twohundreddMA+"',"+"'"+twohundredMAchg+"',"+"'"+top+"',"+"'"+pe+"',"+"'"+eps+"',"+"'"+ebitda+"'" +")"; 		

		        
		       // yearLowchg,yearHighchg,yearLow,yearHigh,marketCap,fiftyMA,fiftyMAchg,twoHundredMA,twoHundredMchg,top,pe,eps,ebitda
		        
		        
		        		System.out.println("cell info:    "+sql+"\n");	
		        		stmt.executeUpdate(sql);
		        	
		        	
		        		
		        	
		        	}
		        		
		        		
		        		
		        		
		        	
		        	
		        	
		        	
		        	
		        }else{break;}
		   
		   
	}
	}
	
	public void run (){
		try {
			 excel();
			 System.out.println("Commit  ");
			 logger.info("StockJDBC Commit");
			 con.commit();
			 System.out.println("Commit  ok !!");
			 logger.info("StockJDBC Commit  ok!!!!");
			
		} catch (Exception e) {
			System.out.println("Error run "+e);
			logger.severe("Error run:"+e);
			try {
				 System.out.println("RollBack  ");
				 logger.info("StockJDBC RollBack  !!!");
				con.rollback();
				System.out.println("RollBack ok !!!! ");
				 logger.info("StockJDBC RollBack  okkkk!!!");
			} catch (Exception e2) {
				System.out.println("Roll back error  "+e);
				logger.severe("StockJDBC RollBack  error:!!!"+e);
			}
			
		}finally{
			try{
				file.close();
				//FileOutputStream outFile =new FileOutputStream(new File("D:\\Java\\Excel\\bin\\FinanceSpreadsheet300.xlsm"));
				//workbook.write(outFile);
				///outFile.close();
				stmt.close();
				stmt2.close();
				con.close();  
				System.out.println(" finish closing]s :");
				 logger.info(" finish closing]s !!");
			}catch (Exception e){
				System.out.println(" error closing]s :"+e);
				 logger.severe(" error closing]s :!!"+e);
			}
		}
	
	
	
	}
	
	
public static void main(String[] args) {
		
			
	new StockJDBC().run();
		
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
