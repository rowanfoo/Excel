package Excel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.io.*;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.*;


public class ExcelRemoveDuplicate {
	static Vector vec;
static 	int 		mycount=1;
static String code;
	static XSSFSheet sheet;
	
	
	private static void  contain(){
		
		if(mycount<sheet.getPhysicalNumberOfRows() ){
			code= sheet.getRow(mycount).getCell(0).toString() ;
			if(vec.contains(code) ){
				mycount++;
				contain();
			}else{
				vec.add(code);
				//System.out.println("cell info:"+mycount+"  :   :"+code);
				System.out.println(code);
			}
		
		
		}
		
	}
	
	
	
	
public static void main(String[] args) {
	XSSFWorkbook workbook =null;
		try{
			vec = new Vector();
			FileInputStream file = new FileInputStream(new File("C:\\Users\\rowan\\Desktop\\Prospects.xlsx"));
		     
		    //Get the workbook instance for XLS file 
			 workbook = new XSSFWorkbook(file);
			 sheet = workbook.getSheetAt(2);
			 int totalz=sheet.getPhysicalNumberOfRows();
			    System.out.println(sheet.getPhysicalNumberOfRows());	
			    for (; mycount< totalz;mycount++){
			    	// System.out.println("cell info:    "+i+" :  " +sheet.getRow(i).getCell(0)      +"\n");	
			    	contain();
			    }
			
			
			
		}catch (Exception e){
			System.out.println("error"+e);	
			}finally{
			   
			}
			
			
			
			
			  }
		
		
		
		
	}
