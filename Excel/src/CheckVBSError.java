import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class CheckVBSError {
	public static void main(String[] args) {
		try {
			/*
			ArrayList <String> arr = new ArrayList<String>();
			arr.add("R:\\FinanceSpreadsheet100.xlsm");
			arr.add("R:\\FinanceSpreadsheet200.xlsm" );
			arr.add("R:\\FinanceSpreadsheet300.xlsm" );
			arr.add("R:\\FinanceSpreadsheet1.xlsm" );
			
			
			
			
			
			
			
			for(String name:arr){
				
				File file = new File(name);
				
				Date dt=new Date(  file.lastModified() );
				
				LocalDate date =
					    Instant.ofEpochMilli(file.lastModified()).atZone(ZoneId.systemDefault()).toLocalDate();
				
				LocalDate today= LocalDate.now();
				
				
				System.out.println(" yo  ");
				System.out.println(" yo  "+date.getDayOfMonth() );
				System.out.println(" yo today"+today.getDayOfMonth());
				if( date.getDayOfMonth()!=today.getDayOfMonth()  ){
					JOptionPane.showMessageDialog(null, "YO HO", "InfoBox: " + "XLS ERROR", JOptionPane.INFORMATION_MESSAGE);
					return ;	
				}
	
				
				
			
			}
			*/
			
			File file = new File("R:\\warn.log");
			double size = file.length();
			System.out.println(" size:  "+size);
			if(size>0)JOptionPane.showMessageDialog(null, "ERROR !!!!! ", "InfoBox: " + "ERROR CHECK LOG NOW!!!!!!", JOptionPane.INFORMATION_MESSAGE);

			System.out.println(" ALL OK GOOD NIGHT  ");
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con=DriverManager.getConnection(  
							"jdbc:mysql://192.168.1.102:3306/fortune?autoReconnect=true&useSSL=false","rowanf","rowm0ng1");  
				
				  String mysql = "SELECT count(*) as count FROM fortune.data where date=curdate()";
				  PreparedStatement ps = con.prepareStatement(mysql);
				  ResultSet rs = ps.executeQuery();
				  
				  while(rs.next()){
					int  count = rs.getInt("count");
					if(count==0)JOptionPane.showMessageDialog(null, "ERROR !!!!! ", "InfoBox: " + "NO DATA LOADED!!!!!!!", JOptionPane.INFORMATION_MESSAGE);
				  
				  
				  }
				  ps.close();
				  con.close();
				  
				  
			} catch (Exception e) {
				System.out.println("Error DTBS CheckVBSError "+e);
			//	 logger.severe("DAOFactoryStock Error initialize:"+e);	 
				
			}
			 
			 
			 
			 
			
		} catch (Exception e) {
			System.out.println(" CheckVBSError  "+e);
			 JOptionPane.showMessageDialog(null, "Error in checking"+e, "InfoBox: " + " XLS ERROR ", JOptionPane.INFORMATION_MESSAGE);
		}
	
		JOptionPane.showMessageDialog(null, "YO HO", "InfoBox: " + "ALL OK , Have a nice day", JOptionPane.INFORMATION_MESSAGE);
	
	}
	
}
