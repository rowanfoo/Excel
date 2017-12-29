package Calc;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import DB.MyDatabase;
import access.DataAccess;
import factory.DAOFactoryData;
import util.FormatUtil;

public class CalcDataAverage extends MyDatabase{
	 
/**
 * update averages
 * 20,40,75,150,200,400
 * Also average 3 months volume
 * 	
 */
	
	
		String sql="";
		LocalDate  date;
		 ArrayList<String > allcodes;
		public CalcDataAverage(String  date)throws Exception {
			//ps = con.prepareStatement(sql);	
			//this.date=date;
			this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));;
			
			
		}

		public CalcDataAverage()throws Exception {
			this.date = LocalDate.now();
			
		}

				
		
			
		
		
		
		
		
		
		
		
		
		
		
		
		PreparedStatement ps1 =null;
		PreparedStatement ps =null;
		public void updateDataNewAverages(ArrayList<DataAccess>arr)throws Exception {
			
			
			
			//System.out.println("DataDAO updateDataNewAverages" );
			String mysql="UPDATE data SET sevenfive=?, sevenfivechg=?, twenty=?, twentychg=?,fourty=?,fourtychg=?,onehundredfifty=?,onehundredfiftychg=? ,fourhundred=?,fourhundredchg=?, "
					+ " twohundred=?,twohundredchg=?, "
					+ " fifty=?,fiftychg=?,Avg3mth=? "
					+ " WHERE code=? and date=?  ";
			 ps =     con.prepareStatement(mysql);
			
			
			LocalDate date75 = FormatUtil.getWorkDay(date, 75);
			LocalDate date20 = FormatUtil.getWorkDay(date, 20);
			LocalDate date40 = FormatUtil.getWorkDay(date, 40);
			LocalDate date150 = FormatUtil.getWorkDay(date, 150);
			LocalDate date400 = FormatUtil.getWorkDay(date, 400);	
			LocalDate date200 = FormatUtil.getWorkDay(date, 200);	
			LocalDate date50 = FormatUtil.getWorkDay(date, 50);	
			LocalDate date60 = FormatUtil.getWorkDay(date, 60);	
					
			//System.out.println("DataDAO updateDataNewAverages done" );
		

//			String mysql1="sELECT Avg(close)as average1   FROM fortune.data where date >= ? and code=? union all  sELECT Avg(close)as average2  FROM fortune.data where date >= ? and code=? "+
//					 " union all  sELECT Avg(close)as average3  FROM fortune.data where date >= ? and code=?  union all  sELECT Avg(close)as average4  FROM fortune.data where date >= ? and code=? " +
//					 "union all  sELECT Avg(close)as average4  FROM fortune.data where date >= ? and code=?  ";
			
			String mysql1="sELECT Avg(close)as average1   FROM fortune.data where date >= ? and code=? union all  sELECT Avg(close)as average2  FROM fortune.data where date >= ? and code=? "+
					 " union all  sELECT Avg(close)as average3  FROM fortune.data where date >= ? and code=?  union all  sELECT Avg(close)as average4  FROM fortune.data where date >= ? and code=? " +
					 "union all  sELECT Avg(close)as average4  FROM fortune.data where date >= ? and code=?  union all  sELECT Avg(close)as average4  FROM fortune.data where date >= ? and code=? "+
					" union all  sELECT Avg(close)as average4  FROM fortune.data where date >= ? and code=?  union all  sELECT Avg(volume)as average4  FROM fortune.data where date >= ? and code=?";
					 
			
			
			
			 ps1 =
			        con.prepareStatement(mysql1);
		
			

			
			for(DataAccess obj:arr){
		
				
				ps1.setString(1,date20.toString()    );	
				ps1.setString(2, obj.getCode() );	
				
				ps1.setString(3, date75.toString());	
				ps1.setString(4, obj.getCode() );	
		
				
				ps1.setString(5, date40.toString());	
				ps1.setString(6, obj.getCode() );	
		
				ps1.setString(7, date150.toString());	
				ps1.setString(8, obj.getCode() );	
		
				ps1.setString(9, date400.toString());	
				ps1.setString(10, obj.getCode() );	
				
				ps1.setString(11, date200.toString());	
				ps1.setString(12, obj.getCode() );	
				
				ps1.setString(13, date50.toString());	
				ps1.setString(14, obj.getCode() );	
				
				ps1.setString(15, date60.toString());	
				ps1.setString(16, obj.getCode() );	
				
				
				double close = obj.getClose();
				//System.out.println("DataDAO updateDataNewAverages: code"+ obj.getCode() );
				//System.out.println("DataDAO preSQL updateDataNewAverages:"+ps1 );
				ResultSet rs = ps1.executeQuery();
				double twenty=0;
				double seventy=0;
				double fourty=0;
				double oneHundredfifty=0;
				double fourhundred=0;
				double twohundred=0;
				double fifty=0;
				double volume=0;
				int count =0;
				while(rs.next()){
					if(count==0){
						twenty=rs.getDouble("average1");
						
					}else if(count==1){
						seventy=rs.getDouble("average1");
					}else if(count==2){
						fourty=rs.getDouble("average1");
					}else if(count==3){
						oneHundredfifty=rs.getDouble("average1");
					}else if(count==4){
						fourhundred=rs.getDouble("average1");
					}else if(count==5){
						twohundred=rs.getDouble("average1");
					}else if(count==6){
						fifty=rs.getDouble("average1");
					}else if(count==7){
						volume=rs.getDouble("average1");
					}
					
					count++;
			    	 	 
			    	  
			     }
				
				
			
				
				ps.setDouble(1,seventy );
				ps.setDouble(2,FormatUtil.formatDouble((close-seventy)/seventy  ,2) );
				ps.setDouble(3,twenty );
				ps.setDouble(4, FormatUtil.formatDouble((close-twenty)/twenty  ,2) );
				
				ps.setDouble(5,fourty );
				ps.setDouble(6, FormatUtil.formatDouble((close-fourty)/fourty  ,2) );
				ps.setDouble(7,oneHundredfifty );
				ps.setDouble(8, FormatUtil.formatDouble((close-oneHundredfifty)/oneHundredfifty  ,2) );
				
				ps.setDouble(9,fourhundred );
				ps.setDouble(10, FormatUtil.formatDouble((close-fourhundred)/fourhundred  ,2) );
				
				
				ps.setDouble(11,twohundred );
				ps.setDouble(12, FormatUtil.formatDouble((close-twohundred)/twohundred  ,2) );
				
				
				ps.setDouble(13,fifty );
				ps.setDouble(14, FormatUtil.formatDouble((close-fifty)/fifty  ,2) );
				
				
				ps.setDouble(15,volume );
				
				
				
				
				ps.setString(16  ,obj.getCode()  );	
				ps.setString(17  ,obj.changeStringToDate()) ;	
				
				//System.out.println("DataDAO updateDataNewAverages:"+ps );
				
			ps.addBatch();
				
				
				
			}
			
			
		}
		
		public void getData()throws Exception {
			try(DAOFactoryData dt= new DAOFactoryData()) {
				//System.out.println("get Data "+dt.getDate("2017-11-02" ));
		//	updateDataNewAverages(dt.getDate(date.toString()));
				updateDataNewAverages(dt.getDate(date.toString()));
			
				 ps.executeBatch();
				 con.commit();
					ps1.close();
					ps.close();
					System.out.println("finish ");
					 
			} catch (Exception e) {
				System.out.println("Error HttpImport run update new AVG "+e);
			}  
		}
		
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//LocalDate.of(2017, 11, 10) 
		try {
		//	String arr1[]= new String[]{"10","13","14","15","16","17","20","21","22","23","24","21","22","23","24","27"};			
//			String arr1[]= new String[]{"02","03","06","07","08","09","10","13","14","15","16","17","20","21","22","23","24","20"};
//			String arr1[]= new String[]{"28"};
//			for(String days : arr1){
//				new CalcDataAverage("2017-11-"+days).getData();
//			}
			
			
			new CalcDataAverage().getData();
			
			System.out.println("Exit ");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error HistoricDataAverageRSI "+e);
		}
	}

}
