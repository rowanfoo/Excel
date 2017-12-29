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


public class MyExcel {

	public static void main(String[] args) {
		
		try{
			
			FileInputStream file = new FileInputStream(new File("C:\\Users\\rowan\\Desktop\\Prospects.xlsx"));
		     
		    //Get the workbook instance for XLS file 
			XSSFWorkbook workbook = new XSSFWorkbook(file);
		 
		    //Get first sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(1);
		
		    
		    System.out.println(sheet.getSheetName());
		    
		   
		    Cell cell = sheet.getRow(1).getCell(10);
		    System.out.println("cell:"+cell);
		   
		    if(cell == null){
		    	 Cell cellt  = sheet.getRow(1).createCell(10);
		    	cellt.setCellValue(Calendar .getInstance());
		    }else{
		    	cell.setCellValue(Calendar .getInstance());
		    }
		    
		    //System.out.println("cell:"+sheet.getRow(1).getCell(5));
		    //System.out.println("cell:"+cell);
		    //cell.setCellValue( Calendar .getInstance());
			
			file.close();
			FileOutputStream outFile =new FileOutputStream(new File("C:\\Users\\rowan\\Desktop\\Prospects.xlsx"));
			workbook.write(outFile);
			outFile.close();
			
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
		    file.close();
		    
		    workbook.close();
		    //System.out.println("total is :"+total);
		
		}catch (Exception e){
		System.out.println("error"+e);	
		}
		
		
		
		
		  }
	
	
	
	
}
