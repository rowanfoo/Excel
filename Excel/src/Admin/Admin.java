package Admin;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import access.DataAccess;
import access.StockAccess;
import factory.DAOFactoryData;
import factory.DAOFactoryStock;
import util.MovingAverage;

public class Admin {  

	public static void main(String[] args) {
		
		// redo all moving average for all stocks
		
		
		try(DAOFactoryStock ds = new DAOFactoryStock()) {
			System.out.println("Redo all moving averages" );
			
			ArrayList <StockAccess>code =    ds.getAllList();
			
			ds.getAllList().forEach( (data)->{
					
					
					
				try(DAOFactoryData dao = new DAOFactoryData()) {
					
					
					
					 ArrayList <DataAccess> arr= dao.getStock( data.getCode()    );
					 
						
					
							
					 new  MovingAverage(50,   arr).run() ;
					 new  MovingAverage(200,   arr).run() ;
					 new  MovingAverage(20,   arr).run() ;
					 new  MovingAverage(75,   arr).run() ;
					 new  MovingAverage(40,   arr).run() ;
					 new  MovingAverage(150,   arr).run() ;
		
					 
					 
					 
						
					 dao.updateDataAveragesImport(arr);
					 dao.updateDataNewAveragesImport(arr);
					 dao.updateDataNewAveragesImportNew(arr);
					
					
					
					
					
				} catch (Exception e) {
					System.out.println("Error data :"+e );
				}
					
					
					
					
			});
			
			
			
			
			
			
			
	
		} catch (Exception e) {
			System.out.println("get stock error :" +e);
		}
		
		
		
		
		
	}

}
