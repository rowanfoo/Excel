
package Calc;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import DB.MyDatabase;
import io.reactivex.Observable;

public class CalcChangePercent2 extends MyDatabase{


	


	
	
	PreparedStatement ps = null;
	 
//String sql ="SELECT code,date,close FROM data WHERE   date in(?,?) and code=? order by date desc";

String sql ="SELECT code,date,close FROM data WHERE  code=? order by date desc limit 2";



PreparedStatement psu = null;

String sql1 ="update data set changes =?, changepercent=?  where    date =? and code=? ";

ArrayList<String > allcodes;
LocalDate date 	;



public CalcChangePercent2(String date )throws Exception{
		allcodes = new ArrayList<String>();
				ps = con.prepareStatement(sql);
		psu = con.prepareStatement(sql1);
		this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		 getAllCodes();
		
	}

	
public CalcChangePercent2(LocalDate date )throws Exception{
		this(date.toString() );
	}

	
	
	
	

	private void getAllCodes()throws Exception{
		String sql1 ="select code from data  where  date =? ";
		PreparedStatement ps =  con.prepareStatement(sql1);  ;
		ps.setString(1, date.toString()  );
		
		 ResultSet rs  = ps.executeQuery();
		
		 while(rs.next()){
			 
			 allcodes .add(rs.getString("code"));

			 
		 }
		 
		
		
		
	}
	 
	 

	
	
	 private void addData(String code)throws Exception{
		 System.out.println("addData :"+code );
//		 ps.setString(1,date.toString());
		
//		  
//		  if(date.getDayOfWeek() == DayOfWeek.MONDAY ){
//				ps.setString(2 ,date.minusDays(3) .toString());
//			}else{
//				  ps.setString(2 ,date.minusDays(1) .toString());
//			}
//			
		  
//		  ps.setString(3,code+".AX");
		  ps.setString(1,code);
		  
		  
		  
		 System.out.println("-------------- addData " + ps);
		  
			 ResultSet set  = ps.executeQuery();
			 int count =0;
			 double closenow=0;
			 double closeyest=0;

			 while(set.next()){
				if(count == 0){
					//set.getString(1).
					closenow = set.getDouble(3);
					count++;
					
				}else{
					closeyest = set.getDouble(3);
				count=0;
				}
			 
			 
			 
			 }
			 update(  closenow,  closeyest , code);
	//System.out.println("-------------- code " + closenow);
	//System.out.println("-------------- code " + closeyest);
	 
	 }
	 private double getDouble(double value){
		 
		 DecimalFormat df = new DecimalFormat("0.000");      
		 return  Double.parseDouble(df.format(value));
	 }
	 private void update( double closenow, double closeyest , String code)throws Exception{
		  
		 double change =  closenow - closeyest;
		 change = getDouble(change);
		// System.out.println("-------------- change " + change);
		 
		 psu.setDouble(1, change);
		 psu.setDouble(2, getDouble(change/closeyest) );
	 	 psu.setString(3,date.toString());
	 	// psu.setString(4,code+".AX");
	 	 psu.setString(4,code);
	 	 
	 	  System.out.println("-------------- UPDATE xx " + psu);
		  
	 	// psu.executeUpdate();
	 	  psu.addBatch();
	 	 
	 }	 
	 
	

	public void run() throws Exception{
		 System.out.println("START ");
	
	
		 Observable
		  .fromIterable(allcodes)

		  .doOnNext((x)->{
			  addData(x);
//			  ps.setString(1,date.toString());
//			  ps.setString(2 ,date.minusDays(1) .toString());
//			  ps.setString(3,x+".AX");
//   			 
//			  ps.addBatch();
//			
//			
	
		  })
		  //.onErrorResumeNext((t)->System.out.println("on error resume :"+t ))
		  .doOnError((t)->close())
		  
		  .subscribe(
				  (x)->{
							System.out.println("subscribs :"+x );
						},
				  
				  (x)->{
						System.out.println("Error :"+x );
					},
				  ()->{
						System.out.println("complete :" );
						 psu.executeBatch();
							con.commit();
							System.out.println("out :" );

				  }
			
				  
  				);
		
		 System.out.println("DONE ");
		 close();
		
	}
	public void close(){
		
		try {
		
			ps.close();
			psu.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.close();
	}
	
	public static void main(String[] args) {
//		LocalDate fromCustomPattern = LocalDate.parse("20171103", DateTimeFormatter.ofPattern("yyyyMMdd"));
//	System.out.println( "custom date "+ fromCustomPattern);
		
try {
	
	
	String arr1[]= new String[]{"10","13","14","15","16","17","20","21","22","23","24","21","22","23","24","27"};
	//String arr1[]= new String[]{"27"};
	for(String days : arr1){
		CalcPriceChangePercent yah = new CalcPriceChangePercent("2017-11-"+days) ;
		yah.run();
		yah.close();
		
	
	}
	
	
	
} catch (Exception e) {
	// TODO Auto-generated catch block
	System.out.println("ERRR RUN "+ e);
	e.printStackTrace();
}
	
	}
}
