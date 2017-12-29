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
public class ExcelAsx {
	public static void main(String[] args) {
		
		try{
			FileInputStream file = new FileInputStream(new File("C:\\Users\\rowan\\Desktop\\Prospects.xlsx"));
		     
		    //Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(2);
		
		    
		    System.out.println(sheet.getSheetName());
		    
		   
		    Cell cell = sheet.getRow(3).getCell(0);
		    System.out.println("cell:"+cell.toString());
		     cell = sheet.getRow(3).getCell(3);
		     System.out.println("cell:"+cell.getCellType());
		     System.out.println("cell:"+cell.toString());
		    
		     cell = sheet.getRow(3).getCell(5);
		    System.out.println("cell:"+ cell.getHyperlink().getAddress());
		    System.out.println("cell:"+cell.getCellType());
		    workbook.close();
		    
			
			file.close();
			
			
		}catch(Exception e){
			
		}
	}




}
