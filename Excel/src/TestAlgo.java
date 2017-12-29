import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import DB.MyDatabase;
import access.DataAccess;
import factory.DAOFactoryData;
import io.reactivex.Observable;

public class TestAlgo extends MyDatabase{
	
	//low rsi
	
	//down 6d our 7
	//down 8%,9 ,>10%
	//asx 300
	
	// sell
	// rally 5% sell
	
	//stop  loss
	// down 5% sell
	// must sell in next max 5 days
	

//	
//	 String  sql="select * from data where code =  ?  and date > '2010-01-01'; "; 		
//		
		 PreparedStatement ps = null;
		 ArrayList <DataAccess> dataprice;
		 Deque<Double> deque = new LinkedList<>();
		 
		public TestAlgo() throws Exception {
//				ps = con.prepareStatement(sql);
//			
				try(DAOFactoryData data = new DAOFactoryData() ){
					
					dataprice =data.getStockbyYear("TPM.AX", "2010");
					System.out.println(" dataprice size :" + dataprice.size());
				for(int x =0; x < 7;x++){
		
					deque.add(new Double(dataprice.remove(x).getChangePercent()));
				}
				
				
				} catch (Exception e) {
					System.out.println(" Err get Data :" + e);
				}
		}			
//			
//			
		double buy ;	
		int toskip=0;
		int skip=0;
		int count=0;
		private void buyNow(){
			buy = deque.getLast();
			
			
			
			
			
		}
		
		public Observable<DataAccess> getData(int index  ){
			return Observable.fromIterable(dataprice)
			.skip(index)
			.cache();
		}
		
		public void run(){

				
				
				
			    try {
		        	
			//    	Observable.fromIterable(dataprice)
			    	//.skip(count)
			    	
			    	/**
			    	 * map2var - false
			    	 * getdata
			    	 * of skip is true got to map 2
			    	 * keep adding into queue'
			    	 * keep checking whether >5d loss ,map2 - true
			    	 * No - continue
			    	 * yes
			    	 * now set some var skip to true
			    	 * map2
			    	 * set map to true
			    	 * keep count
			    	 * once count =5 
			    	 * then set map2 false  and goto sell.
			    	 * then dequeu is empty
			    	 * 
			    	 */
		        	while (true){
		             if( count > dataprice.size()){
		            	 break;
		             }
		             
		             getData(count)
		             .subscribe(
		    				(s)->{
		    					toskip++;
		    					count++;
		    					 deque.add(new Double(s.getChangePercent() ));
		    					 System.out.println( " RX run: ");
		    					 long count =  deque.stream().filter( a->{
		    				        	return a.doubleValue() <0;
		    				        	
		    				        }).count();
		    					 
		    					 if( count >5)  {
		    						 deque.pop();
		    						 System.out.println( "got buys :  " + s.getDate());
		    					 }
		    					 
		    					
		    				});
		        	}
		        	 System.out.println( "end RX : ");
		           
		        } catch (Exception e) {
		            	 System.out.println( "Err : "+e );
					}

		        
		        System.out.println( "FINISH : " );
			}
		
	
	
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
new TestAlgo().run();
//		Deque<Double> deque = new LinkedList<>();
//        deque.add(new Double(-0.01));
//        deque.add(new Double(-0.05));
//        deque.add(new Double(-0.06));
//        deque.add(new Double(-0.03));
//        deque.add(new Double(0.01));
//        deque.add(new Double(-0.08));
//        deque.add(new Double(-0.02));
//        
////        System.out.println("total  size :"+ deque.size()  );
////       long count =  deque.stream().filter( a->{
////        	return a.doubleValue() <0;
////        	
////        }).count();
////        
////       System.out.println("count size :"+ count  );
//        
////        System.out.println("deq size :"+ deque.size()  );
////        System.out.println("deq :"+ deque.pop() );
////        System.out.println("deq size :"+ deque.size()  );
////             
//
//        
//        System.out.println("deq size :"+ deque.getLast()  );
//        
        
	
	
	}

}
