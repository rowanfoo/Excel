

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import access.NewsAccess;
/**
 * import ASX news from ASX website
 * @author rowan
 *
 */
public class ASXNews {

	
	public void run(){
		String url = "http://www.asx.com.au/asx/statistics/todayAnns.do";
					  
		System.out.println("RUNB");
		
		try {
			int count = 0;
			
			Response response = Jsoup.connect(url).followRedirects(true).execute();     
		    url = response.url().toString();
			
			System.out.println("CORRECT url2:" + url);
			
			Document doc = Jsoup.connect(url).get();

			Elements table = doc.select("table");
			System.out.println("CORRECT doc:" + doc);
			System.out.println("CORRECT TABLE:" + table);
			
			System.out.println("RUNB 12");
			String asxUrl="http://www.asx.com.au/";
			HashSet codes = new HashSet();
			ArrayList<NewsAccess> myarr = new ArrayList<NewsAccess>();
			System.out.println("RUNB 13");
			for (Element tb : table) {
				String t = tb.attr("class");
				String pattern = ".*announcements.*";
				// boolean matches = Pattern.matches(pattern, t)
			

				System.out.println("CORRECT TABLE:" + t);
				if (Pattern.matches(pattern, t)) {
					// System.out.println("CORRECT TABLE");
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

							Elements td = etr.select("td");
							count = 0;
							for (Element etd : td) {

								if (count == 2)
									title = etd.text();
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
/*
			try (DAOFactoryNews dc = new DAOFactoryNews()) {
				dc.insertNewNews(myarr);

			} catch (Exception e) {
				System.out.println("Error:" + e);

			}
*/
			System.out.println("FINISH ");

		} catch (Exception e) {
			System.out.println("Error:" + e);

		}
	
	}
	/*
	public void pain(){
		 String RedirectedUrl=null;
		 Document doc = Jsoup.connect(url).get();

			Elements table = doc.select("table");
		 Elements meta = page.select("html head meta");
		    if (meta.attr("http-equiv").contains("REFRESH")) {
		        RedirectedUrl = meta.attr("content").split("=")[1];
		    } else {
		        if (page.toString().contains("window.location.href")) {
		            meta = page.select("script");
		            for (Element script:meta) {
		                String s = script.data();
		                if (!s.isEmpty() && s.startsWith("window.location.href")) {
		                    int start = s.indexOf("=");
		                    int end = s.indexOf(";");
		                    if (start>0 && end >start) {
		                        s = s.substring(start+1,end);
		                        s =s.replace("'", "").replace("\"", "");        
		                        RedirectedUrl = s.trim();
		                        break;
		                    }
		                }
		            }
		        }
		    }
	}
	*/
	
	
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
		new ASXNews().run();
			    
		
			        	
			        	
			        	
			        	
			   
			    
		

		
			
			
			  }
		
		
		

	

}
