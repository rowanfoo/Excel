
import java.io.File;
import java.sql.Connection;
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
import access.StockAccess;

import factory.DAOFactoryStock;
import util.ExcelConfig;
import util.ExcelLogger;
public class  ImportCategory  extends MyDatabase{
	
	
	XSSFWorkbook workbook=null;
	XSSFSheet sheet=null ;
	String sql;
	Vector vecfile;
	
		
		
		ImportCategory(){
		try {
			vecfile = new Vector ();
		
			vecfile.add(new File(ExcelConfig.excelDir+"Category.xlsx"));
			
		
			logger.info("Fund");
				
			   //  con=DriverManager.getConnection(  
				//		"jdbc:mysql://localhost:3306/fortune?autoReconnect=true&useSSL=false","root","rowm0ng1");  
				
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			logger.severe("Fund error :"+e);
		}	
	}
	
	
	
	
	
	public void excel()throws Exception  {
		  
		    
		//    int total=0;
		   String mcode;
		   String date;
		 
		   String category;
		   String subcategory;
		   String ebitda;
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		   Iterator<Row> rowIterator = sheet.iterator();
		   System.out.println("cell NEW:    ");
		   try(DAOFactoryStock dao = new DAOFactoryStock()){
		   while(rowIterator.hasNext()) {
		        Row row = rowIterator.next();
		         
		        if(row.getCell(0) == null)break;
		  
		        String code = (String)row.getCell(0).toString();
		        date=dateFormat.format(new Date());
		        if(code !=null ){
		        	if((code.endsWith(".AX")|| code.startsWith("^") )   ){
		   
		        		category=row.getCell(3).toString();;
		        		subcategory=row.getCell(4).toString();;
		       
		  
		       	
		       			StockAccess stk = new StockAccess();
		       			stk.setCode(code);
		       			stk.setCategory( category);
		       			stk.setSubcategory(subcategory);
		       			dao.updateStockCategory(stk);;
		       			dao.updateStockSubCategory(stk);
		       			
		       			
		       	
		       		
		       		
		       		
		       		
		       		
		        	
		        		
		        	
		        	
		        		
		        		
		        		
		        		
		        	
		        	
		        	} 	
		        	
		        	
		        }else{break;}
			}
			}catch(Exception e){
	       		 System.out.println(" Error : "+e);
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
		
			
	new ImportCategory().run();
		
		        	
		
		
		
		  }
	
	
	
}
