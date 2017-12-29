
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DB.MyDatabase;
import dao.Database;

import org.jsoup.nodes.Attribute;
/**
 * import ASX news from ASX website
 * @author rowan
 *
 */
public class CloseVolume extends MyDatabase{

	int vol;
	PreparedStatement ps1 ;
	PreparedStatement ps2 ;
	ArrayList<temp> ls = new ArrayList<>();
	ArrayList<temp> ls1 = new ArrayList<>();
	
	public CloseVolume() throws Exception {
		super();
	

	}
	class temp{
		public String code;
		public String mydate = LocalDate.now().toString();
		public String fiftycount;
		public double volume;
		public double closeVol;

		temp( String code, String fiftycount, double volume, double closeVol){
			  this.code=code;
			  mydate = LocalDate.now().toString();
			  this.fiftycount=fiftycount;
			  this.volume=volume;
			  this.closeVol=closeVol;
		}
	
		
		
	}
	 
	public void addDataX( String date , String code,double closevol, double vol) throws Exception {
		System.out.println("add datax ");
		ls1.add(new temp(  code, "", vol, closevol));
	
	}
	
	private void  mytable(Element tb ){
		System.out.println("mytable");
		Elements ele= tb.select("tr");
		//System.out.println("ele:"+ele );
	
		vol=0;
		for(Element rows : ele  ){
			
			boolean ok = false;
			int count =0;
			
			for(Element e : rows.select("td")  ){
				if(e.text().endsWith("pm")  ){
					//System.out.println("time:"+e.text().substring(0, e.text().indexOf(":")));
					//  e.text().substring(0, e.text().indexOf(":"));
					int hr = Integer.parseInt(e.text().substring(0, e.text().indexOf(":")) );
					if(hr >= 4 & hr < 12  ){
							System.out.println("time:"+e.text() );
							ok=true;
									 
					}
					
				
				}
				
				if(ok & count==3){
			
				
					int num = Integer.parseInt(e.text().replaceAll(",",""));
					//System.out.println("num"+ num );
					vol += num;
					//System.out.println("vol: "+ vol );
					
				}
				//3
				count++;	
			}
		
			
		}
	
	}
	
	public void execute()throws Exception {
		
		String sql ="select code,volume  FROM  data  where date=curdate() ";
		//String sql ="select code,volume  FROM  data  where date=curdate() and code in ('bhp.ax','wfd.ad','rio.ax')  ";
			
			Statement stmt=				 con.createStatement();  
			System.out.println("mysql:"+stmt);
			ResultSet st = stmt.executeQuery(sql);
		

			while(st.next()){
				String code = st.getString("code");
				System.out.println("RUNB :" + code);
				if (code.indexOf(".")> -1  )addDataX(code, st.getDouble("volume"));
				
			}
			
    		stmt.close();
    		
		//addDataX("bhp.ax",10.000);
			
	
	}
	
	
	public void addDataX(String code,double volume){
		String mycode = code.substring(0,code.indexOf(".")    );
		
		String url = "http://stocknessmonster.com/stock-trades?S="+mycode+"&E=ASX";
		System.out.println("RUNB :" + url);

		try {
			int count = 0;
			Document doc = Jsoup.connect(url).get();

			Elements table = doc.select("table");
		//	System.out.println("RUNB 12:"+table.size());
			//System.out.println("RUNB 12");
			int x=0;
			for(Element tb :  table  ){
			
				//System.out.println("RUNB tb:"+);
		/*
				tb.attributes().forEach( (Attribute s)->{
					int x=0;
					//System.out.println("Attribute tb:"+s.getKey()+":"+s.getValue());
					if(s.getKey().equalsIgnoreCase("width")&&s.getValue().equalsIgnoreCase("100%")   ){
						if(x==0){
							//System.out.println("tb:"+  tb );
							mytable( tb );
						}
						x++;
					}
				}
						
						
						);
				*/
				
				
				
				for(Attribute ab:tb.attributes()  ){
//					System.out.println("Attribute tb:"+ab.getKey()+":"+ab.getValue());
					if(ab.getKey().equalsIgnoreCase("width")&&ab.getValue().equalsIgnoreCase("100%")   ){
					//	System.out.println("Attribute tb:"+ab.getKey()+":"+ab.getValue());
						if(x==2){
							//System.out.println("tb:"+  tb );
							mytable( tb );
							
						}
						x++;
					}
				
				}
				
				
		}
			update(code);
			int time = 60000 + (int)(Math.random() * ((18000- 60000) + 60000));
			if( (((double)vol)/ volume)>0.3   ){
				addDataX(  LocalDate.now().toString() ,code, (double)vol, volume);
			}
			
			
			Thread.sleep(time);
			
		} catch (Exception e) {
			System.out.println("Error:" + e);

		}
	
	}
	public void update(String code)throws Exception {
		//System.out.println("update:" );
//		ps1.setDouble(1, (double)vol );
	//	ps1.setString(2, code);
		//ps1.setString(3, LocalDate.now().toString());
	//	System.out.println("update:" + ps1);
		
		
		ls.add(new temp(  code, "", 0, vol));
		
		//ps1.executeUpdate();
	///	ps1.addBatch();
	
	}
	class Techstrx {
		
	
	
	}
	public void run() {
		Connection mycon=null;
		try {
			System.out.println("CloseVolume update  run: ");
			logger.info("CloseVolume :   run ");
			
			execute();
			logger.info("CloseVolume execute:   ");
			logger.info("CloseVolume execute : ");
			
			close();
			logger.info("CloseVolume close  : ");
			Thread.sleep(30000);
			mycon= Database.getConnection();
			logger.info("CloseVolume mycon open   : ");
			
			mycon.setAutoCommit(false);;
			
			String sql = "update data set closeVol=? where code=? and date=?";
			ps1 = mycon.prepareStatement(sql);
			
			String mysql1 = "INSERT INTO techstr (code,date ,mode,fiftycount,volume) VALUES (?,?,26,?,?)";
			ps2 = mycon.prepareStatement(mysql1);
			logger.info("CloseVolume mycon first insert   : ");
			ls.forEach((temp )->{
				try {

					ps1.setDouble(1, temp.closeVol );
					ps1.setString(2, temp.code);
					ps1.setString(3, temp.mydate);
					//System.out.println("update:" + ps1);
					ps1.addBatch();
				} catch (Exception e) {
					System.out.println("CloseVolume update  Data: "+ e);
					logger.severe("CloseVolume update  Data: "+ e);
				}
			}
					
					
					);
			
			logger.info("CloseVolume mycon second insert   : ");
			ls1.forEach((temp )->{
				try {

					ps2.setString(1,  temp.code);
					ps2.setString(2, temp.mydate);
					ps2.setDouble(3, temp.closeVol);
					ps2.setDouble(4, temp.volume);
					//System.out.println("mysql   ps2:"+ps2);
					ps2.addBatch();
				} catch (Exception e) {
					System.out.println("CloseVolume Insert TechStr: "+ e);
					logger.severe("CloseVolume Insert TechStr: "+ e);
				}
			}
					
					
					);
	
			
			
			ps1.executeBatch();
			ps2.executeBatch();
			ps1.close();
			ps2.close();
			mycon.commit();
			logger.info("CloseVolume :   finsih ");
		} catch (Exception e) {
			System.out.println("CloseVol Error run " + e);
			logger.severe("CloseVol Error run " + e);

		}finally {
			try {

				mycon.close();
				System.out.println(this.getClass().getName()+ " finish closing]s :");

				logger.info(this.getClass().getName()+ " finish closing]s ");

			} catch (Exception e) {
				System.out.println(this.getClass().getName()+ "  error closing]s :" + e);
				logger.severe(this.getClass().getName()+ " finish closing]s error: " + e);
			}
		}


	}
	
	public static void main(String[] args) {

		
	try {
		new CloseVolume().run();
	} catch (Exception e) {
		System.out.println("CloseVol Error  " + e);
	}
	
			   
		
		
			       	
		
		//for(int xx =0;xx<5;xx++){
		//	int x = 60000 + (int)(Math.random() * ((18000- 60000) + 60000));
		//	System.out.println(x);	
		//}
			       	
			  
			   
		

		
			
			
			 }
		
		
		

	

}