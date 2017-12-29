
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

public class StockJDBCTemp  extends MyDatabase{
	FileInputStream file=null;
	
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	
	StockJDBCTemp(){
		try {
			
			logger.info("StockJDBCTemp");
			 file = new FileInputStream(new File(ExcelConfig.excelDir+"FinanceSpreadsheet1.xlsm"));
		     
			    //Get the workbook instance for XLS file 
				 workbook = new XSSFWorkbook(file);
			 
			    //Get first sheet from the workbook
				 sheet = workbook.getSheetAt(0);
			
			    
			    System.out.println(sheet.getSheetName());
		
			    
			   //  con=DriverManager.getConnection(  
				//		"jdbc:mysql://localhost:3306/fortune?autoReconnect=true&useSSL=false","root","rowm0ng1");  
				
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			logger.severe("StockJDBCTemp error :"+e);
		}	
	}
	
	public void execute()throws Exception {
		
		
			
			Statement stmt=con .createStatement();  	
			stmt.executeUpdate(sql);
    		System.out.println("ok execute :    ");	
    		
			
	
	}
	
	public boolean check(String code)throws Exception {
		Statement stmt=con.createStatement(); 
		String mysql = "SELECT code FROM fortune.stock where code ='"+code+"'";
		
	      ResultSet rs = stmt.executeQuery(mysql);
	      return rs.next();
	}
	
	
	
	public void excel()throws Exception  {
		  

		
		
		
		
		
		
		    
		    int total=0;
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
		   String top="300";
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
		        	if((code.endsWith(".AX")|| code.startsWith("^") ) ){
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
		        		
		       // sql="INSERT INTO stock (code,date,name,descp,shares,yearLowchg,yearHighchg,yearLow,yearHigh,marketCap,fiftyMA,fiftyMAchg,twoHundredMA,twoHundredMchg,top,pe,eps,ebitda) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+yearwlchg+"',"+"'"+yearwhchg+"',"+"'"+yearwl+"',"+"'"+yearwh+"',"+"'"+marketCap+"',"+"'"+fiftydMA+"',"+"'"+fiftyMAchg+"',"+"'"+twohundreddMA+"',"+"'"+twohundredMAchg+"',"+"'"+top+"',"+"'"+pe+"',"+"'"+eps+"',"+"'"+ebitda+"'" +")"; 		
		      
		     // sql="INSERT INTO stock (code,date,name,descp,shares,marketCap,top) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+marketCap+"',"+"'"+top+"')"; 		
			     
		        sql="UPDATE stock SET shares="+shares+" where code='"+code+"'";
		        
		        
		        
		        
		     //   sql="INSERT INTO fundamental (code,date,name,descp,shares) VALUES ('"+ mcode+"',"+ "'"+date+"',"+"'"+name+"',"+"'" +descp+"',"+"'"+shares+"',"+"'"+yearwlchg+"',"+"'"+yearwhchg+"',"+"'"+yearwl+"',"+"'"+yearwh+"',"+"'"+marketCap+"',"+"'"+fiftydMA+"',"+"'"+fiftyMAchg+"',"+"'"+twohundreddMA+"',"+"'"+twohundredMAchg+"',"+"'"+top+"',"+"'"+pe+"',"+"'"+eps+"',"+"'"+ebitda+"'" +")"; 		

		        
		       // yearLowchg,yearHighchg,yearLow,yearHigh,marketCap,fiftyMA,fiftyMAchg,twoHundredMA,twoHundredMchg,top,pe,eps,ebitda
		        
		        
		        		System.out.println("cell info:    "+sql+"\n");	
		        		 execute();	
		        	
		        	
		        		
		        	
		        	}
		        		
		        		
		        		
		        		
		        	
		        	
		        	
		        	
		        	
		        }else{break;}
		   
		   
	}
	}
	
	public void run (){
		try {
			 excel();
			 System.out.println("StockJDBCTemp  ");
			 logger.info("StockJDBCTemp Commit");
			 con.commit();
			 System.out.println("StockJDBCTemp  ok !!");
			 logger.info("StockJDBCTemp Commit  ok!!!!");
			
		} catch (Exception e) {
			System.out.println("Error run "+e);
			logger.severe("Error run:"+e);
			try {
				 System.out.println("RollBack  ");
				 logger.info("StockJDBCTemp RollBack  !!!");
				con.rollback();
				System.out.println("RollBack ok !!!! ");
				 logger.info("StockJDBCTemp RollBack  okkkk!!!");
			} catch (Exception e2) {
				System.out.println("Roll back error  "+e);
				logger.severe("StockJDBCTemp RollBack  error:!!!"+e);
			}
			
		}finally{
			try{
				file.close();
				//FileOutputStream outFile =new FileOutputStream(new File("D:\\Java\\Excel\\bin\\FinanceSpreadsheet300.xlsm"));
				//workbook.write(outFile);
				///outFile.close();
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
		
			
	new StockJDBCTemp().run();
		
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
