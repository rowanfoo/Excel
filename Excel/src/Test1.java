
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * import ASX news from ASX website
 * @author rowan
 *
 */
public class Test1 {

	
	public void run(){
		String url = "http://bigcharts.marketwatch.com/advchart/frames/frames.asp?show=&insttype=&symb=au%3Ahgg&x=0&y=0&time=7&startdate=1%2F4%2F1999&enddate=11%2F24%2F2016&freq=1&compidx=aaaaa%3A0&comptemptext=&comp=none&ma=1&maval=20&uf=0&lf=268435456&lf2=2&lf3=0&type=1&style=320&size=4&timeFrameToggle=false&compareToToggle=false&indicatorsToggle=false&chartStyleToggle=false&state=11";
		System.out.println("RUNB");

		try {
			int count = 0;
			Document doc = Jsoup.connect(url).get();

			Elements img = doc.select("img");

			
			for (Element tb : img) {
				String t = tb.attr("src");
				String pattern = ".*nosettings?.*";
				// boolean matches = Pattern.matches(pattern, t)
			

			//	System.out.println(" TABLE:" + t);
				if (Pattern.matches(pattern, t)) {
					 System.out.println("CORRECT TABLE"+t);

					 
					 BufferedImage  image = null;
					
					 try {
						 System.out.println("image t:"+t);
					     URL urli = new URL(t);
					     image = ImageIO.read(urli);
					     
					     ImageIO.write(image,"jpg",new File("d:\\test.gif"));
					     
					 } catch (IOException e) {
						 System.out.println("error immage. "+e);
					 }
					 
					 
					 
				
			}
			

			
			System.out.println("FINISH ");

		} 
		}catch (Exception e) {
			System.out.println("Error:" + e);

		}
	
	}
	
	
	
	public static void main(String[] args) {

		
		/*		
				   //get current date time with Date()
		Date mydate= new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(mydate);
		int month = cal.get(Calendar.YEAR);	 
		System.out.println("yr"+month);		   
			    
		System.out.println("yr"+(month-1));		    
			*/  
		new Test1().run();
			    
		
			        	
			        	
			        	
			        	
			   
			    
		

		
			
			
			  }
		
		
		

	

}
