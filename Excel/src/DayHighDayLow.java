



import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Logger;

import DB.MyDatabase;
import util.ExcelLogger;
public class  DayHighDayLow  extends MyDatabase{

	
	
	ArrayList <String> arr;
	String years[]={"2009","2010","2011","2012","2013","2014","2015","2016"};
	 DayHighDayLow(){
		
		
		try {
			
			arr = new ArrayList();
			System.out.println("Data rUN    ");	
			logger.info("Data");	
			
			    
			   
			
		} catch (Exception e) {
			System.out.println("Error initialize "+e);
			 logger.severe("Data Error initialize:"+e);	    
		}	
	}
	
	public void execute(String sql)throws Exception {
		
		
	
			Statement stmt=				 con.createStatement();  	
			stmt.executeUpdate(sql);
    	
			
	
	}
	
	
	
	public void  getCode()throws Exception {
		Statement stmt= con.createStatement(); 
		
		String mysql = "SELECT code FROM fortune.stock";;
	      ResultSet rs = stmt.executeQuery(mysql);
	    
	      while(rs.next()){
	    	// arr.add(rs.getString(1));
	      
	      }
	      arr.add("AGL.AX");
	      
	
	}

	
	
	public double   getCount(String sql)throws Exception {
		Statement stmt= con.createStatement(); 
		
		
	      ResultSet rs = stmt.executeQuery(sql);
	    String result=null;
	      while(rs.next()){
	    	result =  rs.getString(1);
	      
	      }
	    
	     // return Integer.parseInt(result)  ;
	return Double.parseDouble(result);
	}

	
	
	
	
	public void excel()throws Exception  {
		  
		    
		   
		   String mcode;
		   String date;
		 
		   String open;
		   String high;
		   String low;
		   String close;
		  
		   String  closeVol;
		   String vol;
		   String change;
		   String changePercent;
		  String previousClose;
		   
		   DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		  
		   System.out.println("cell NEW:    ");
		   
		 double upday;
		 double  downday;
		 
		   for(String codes:arr){
			  for(String year: years  ){
				 
				  for(int x=1; x<13;x++){
					  
				  String sql ="SELECT count(*) FROM data WHERE 	YEAR(date)="+year+" AND MONTH(date) = "+x+ " AND code = '"+codes+"' and   changePercent>0";
				//  System.out.println("sql;"+ sql);
				  upday=getCount( sql) ;  
				  String sql2 ="SELECT count(*) FROM data WHERE 	YEAR(date)="+year+" AND MONTH(date) = "+x+ " AND code = '"+codes+"'  and changePercent<0";
				  downday=getCount( sql2) ;  	     
				  
				  System.out.println("up/down    :"+"M:"+x+" : "      +upday+" / "+ downday);
				  
					  if(  upday !=0 & ( (upday/downday)!=1  ) ){
					  String dateis = year+"-"+x+"-1";
					  String sql3 ="INSERT INTO daylowdayhigh (code,date,green,red)VALUES ( '" + codes+"','" + dateis +"','" + upday+ "'" + downday +"')"    ;  
					  System.out.println("sql   :"+sql3);
					  execute(sql3);
					  }
				  
			  
				  }
			  
			  
			  
			  }
			   
			   
			   
			   
			   
			   
			   
		   }
		   
		   
		   
		   
		   
		  /*       
		        //For each row, iterate through each columns
		       // System.out.println("cell t0:    "+row.getCell(0) );
		  
		        String code = (String)row.getCell(0).toString();
		        date=dateFormat.format(new Date());
		        	if((code.endsWith(".AX")|| code.startsWith("^") )   ){
		        		//System.out.println("cell info:    "+row.getCell(0)+"\n");
		        		//System.out.println("cell name:    "+row.getCell(2)+"\n"); 
		        		
		        		   mcode=code;
		        		   System.out.println("cell t2:    ");
		       		   
		       		    
		       		 System.out.println("cell t1:    "+row.getCell(2));
		       		  
		       		open=row.getCell(7).toString();;
		       		//System.out.println("cell t2:    ");
		       		high=row.getCell(8).toString();;
		       		low=row.getCell(9).toString();;
		       		//System.out.println("cell t3:    ");
		       		change=row.getCell(10).toString();;
		       		//System.out.println("cell t4:    ");
		       		changePercent=row.getCell(11).toString();;
		       		close=row.getCell(28).toString();;
		       		//System.out.println("cell t5:    ");
		       		closeVol=row.getCell(29).toString();;
		       		vol=row.getCell(30).toString();;
		       		
		       	//	System.out.println("cell t6:    ");
		       		previousClose	=row.getCell(31).toString();
		        
		        
		        sql="INSERT INTO data (code,date,open,high,low,close,closeVol,volume,changes,changePercent,previousClose) VALUES ( '" + mcode+"',"+ "'"+date+"',"+     "'"+open+"',"+
		        								"'"+high+"',"+"'"+low+"',"+"'"+close+"','"+closeVol+"',"+"'"+vol+"',"+"'"+change+"',"+"'"+changePercent+"'"+ ",'"+previousClose+"'"   +")"; 		

		        
		        
		        		System.out.println("cell info:    "+sql+"\n");	
		        		 execute();	
		        	
		        	
		        		*/
		        	
		        		
		        		
		        		
		        		
		        	
		        	
		        	
		        	
		        	
		   
		   
		   
	
	}
	
	public void run() {
		try {

			getCode();
			excel();
			close();

			logger.info("Data run 2");

		} catch (Exception e) {
			System.out.println("Error run " + e);
			logger.severe("Data Error run " + e);

		}

	}
	
public static void main(String[] args) {
		
			
	new DayHighDayLow().run();
		
			   //get current date time with Date()
			 
			   
		    
		    
		  
		  
		   
	
		        	
		        	
		        	
		        	
		   
		    
	

	
		
		
		  }
	
	
	
}

