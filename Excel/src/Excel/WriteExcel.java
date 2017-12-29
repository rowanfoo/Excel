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
public class WriteExcel {
	static XSSFSheet sheet;
public static void main(String[] args) {
		
		try{
			FileInputStream file = new FileInputStream(new File("C:\\Users\\rowan\\Desktop\\Prospects.xlsx"));
		     
		    //Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
			 sheet = workbook.getSheetAt(4);
			 System.out.println("RUN");
		    
		    System.out.println(sheet.getSheetName());
		    System.out.println(sheet.getPhysicalNumberOfRows());
		    Cell cell = sheet.getRow(1).getCell(1);
		    System.out.println("cell:"+cell);
		    int emptyRow=getNextEmptyRow();
		    System.out.println("empty cell:"+emptyRow);
		    
		    
		    cell = sheet.getRow(emptyRow).getCell(0);
		    cell.setCellValue( Calendar .getInstance());
		    
		   /*
		    Cell cell = sheet.getRow(3).getCell(0);
		    System.out.println("cell:"+cell.toString());
		     cell = sheet.getRow(3).getCell(3);
		     System.out.println("cell:"+cell.getCellType());
		     System.out.println("cell:"+cell.toString());
		    
		     cell = sheet.getRow(3).getCell(5);
		    System.out.println("cell:"+ cell.getHyperlink().getAddress());
		    System.out.println("cell:"+cell.getCellType());
		   
		    
			*/
			file.close();
			FileOutputStream outFile =new FileOutputStream(new File("C:\\Users\\rowan\\Desktop\\Prospects.xlsx"));
			workbook.write(outFile);
			outFile.close();
			 workbook.close();
			
		}catch(Exception e){
			System.out.println("err:"+e);
		}
	}


	private static  int getNextEmptyRow()throws Exception {
		for (int x=1; x <sheet.getPhysicalNumberOfRows();x++){
			if(isCellEmpty(sheet.getRow(x).getCell(1) )){
				return x;
			}
		}
		throw new Exception("Error cant find row");
		//System.out.println("Error cant find row");
		//return -1;
	}
	
	private static boolean isCellEmpty(Cell cell){
		 if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
		        return true;
		    }

		    if (cell.getCellType() == Cell.CELL_TYPE_STRING && cell.getStringCellValue().isEmpty()) {
		        return true;
		    }

		    return false;
	}

}
