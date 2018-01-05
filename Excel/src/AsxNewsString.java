
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import DB.MyDatabase;
import access.NewsAccess;
import factory.DAOFactoryNews;
/**
 * import ASX news from ASX website but ASX change it website on May12 , to use some javascript encryption
 * So now got to use ChromeBrowser first to get Data then feed it into here.
 * AsxNewsChromeBrowser
 * @author rowan
 *
 */
public class AsxNewsString  extends MyDatabase {

	String htmlToParse;
	AsxNewsString(String html){
		htmlToParse=html;
		System.out.println("AsxNewsString");
		logger.info("AsxNewsString run 1");
		
		run();
		close();
	}
	
	
	
	public void run(){
					  
		System.out.println("RUNB");
		
		try {
			int count = 0;
			
			
			
			Document doc = Jsoup.parse(htmlToParse)  ;

			Elements table = doc.select("table");
		//	System.out.println("CORRECT doc:" + doc);
		//	System.out.println("CORRECT TABLE:" + table);
			
			System.out.println("RUNB 12");
			String asxUrl="http://www.asx.com.au/";
			HashSet codes = new HashSet();
			ArrayList<NewsAccess> myarr = new ArrayList<NewsAccess>();
			System.out.println("RUNB 13");
			for (Element tb : table) {
				String t = tb.attr("class");
				String pattern = ".*announcements.*";
				 boolean matches = Pattern.matches(pattern, t);
			

				System.out.println("AsxNewsString MATCHES:" + matches);
				
				if (Pattern.matches(pattern, t)) {
					 System.out.println("CORRECT TABLE");
					boolean firsttime = false;

					Elements tr = tb.select("tr");
					// int count=0;
					String code = "";
					String link = "";
					String title = "";

					for (Element etr : tr) {

						if (firsttime) {
							Elements th = etr.select("th");
							 //System.out.println("CORRECT
							// CODE:"+((Element)th.get(0)).text());
							code = ((Element) th.get(0)).text();
							 System.out.println("CORRECT code : "+ code);
							Elements td = etr.select("td");
							count = 0;
							for (Element etd : td) {

								if (count == 2)
									title = etd.text();
								 System.out.println("CORRECT TABLE  title : "+ title);
								
								if (count == 4) {
									// System.out.println("CORRECT
									// URL:"+etd.val() );
									// System.out.println("CORRECT
									// URL:"+etd.attributes() );
									// System.out.println("CORRECT
									// URL:"+etd.childNodes() );
									// System.out.println("CORRECT
									// URL:"+etd.children() );
									//link = etd.children().toString();
									link = etd.child(0).attr("href") ;
							//		System.out.println("HREF: "+etd.child(0).attr("href")   );
									
									
								}
								count++;

							}
							System.out.println("AsxNewsString :"+code +" :: "+ title);		   
							if (!codes.contains(code + title)) {
								myarr.add(new NewsAccess(code, new Date(), asxUrl+link, title));
								codes.add(code + title);
							}

						}
						firsttime = true;

					}

				}

			}
			System.out.println("FINISH :" + myarr.size());
			if(myarr.isEmpty())logger.severe("AsxNewsString NO NEWS " );

			
			try (DAOFactoryNews dc = new DAOFactoryNews()) {
				dc.insertNewNews(myarr);
			} catch (Exception e) {
				logger.severe("AsxNewsString Error insertNews " + e);


			}

			System.out.println("FINISH ");

		} catch (Exception e) {
			logger.severe("AsxNewsString Error  " + e);

		}
		logger.info("AsxNewsString FINISH ");
		
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
		//new ASXNews().run();
			    
		
			        	
			        	
			        	
			        	
			   
			    
		

		
			
			
			  }
		
		
		

	

}
