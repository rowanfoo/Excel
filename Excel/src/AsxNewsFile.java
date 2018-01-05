


import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import access.NewsAccess;
import factory.DAOFactoryNews;
/**
   .
 * @author rowan
 *
 */
public class AsxNewsFile {

	
	public void run(){
		File filerun = null; 
		Path directoryPath = Paths.get("C:", "/Java/Excel/bin");
		
		 try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath,"*.htm")) {
		        for (Path path : stream) {
		            System.out.println(path);
		            filerun = path.toFile();
		        }
		    } catch (IOException e) {
		        throw new RuntimeException(e);
		    }
			
		 
		
		
		
		
		
					  
		System.out.println("RUNB");
		
		try {
			int count = 0;
			if (filerun == null )throw new Exception ("No file found ");
	
			
			Document doc = Jsoup.parse(filerun, "UTF-8");
			
			

			Elements table = doc.select("table");
			//System.out.println("CORRECT doc:" + doc);
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
				System.out.println("AsxNewsFile matches:  "+ matches);


			//	System.out.println("CORRECT TABLE:" + t);
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
							System.out.println("AsxNewsFile :  "+ code +" :  "+ title);
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
			
			try (DAOFactoryNews dc = new DAOFactoryNews()) {
			//	dc.insertNewNews(myarr);
				dc.insertNewNews(myarr);

			} catch (Exception e) {
				System.out.println("Error:" + e);

			}

			
			 filerun.delete();
			System.out.println("FINISH ");

		} catch (Exception e) {
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
		new AsxNewsFile().run();
		/*	    
		
		String workingDir = System.getProperty("user.dir");
		   System.out.println("Current working directory : " + workingDir);
		   File dir = new File(workingDir);      	
		   FileFilter fileFilter = new WildcardFileFilter(".*");      	
		   File[] files = dir.listFiles(fileFilter);	        	
			   
			for(File f:files){
				 System.out.println("Current file : " + f.getName());
			}
		

			 System.out.println("Done : " );
			
			
			 
			 Path directoryPath = Paths.get("D:", "/Java/Excel/bin");
			 try (DirectoryStream<Path> stream = Files.newDirectoryStream(directoryPath,"*.htm")) {
			        for (Path path : stream) {
			            System.out.println(path);
			        }
			    } catch (IOException e) {
			        throw new RuntimeException(e);
			    }
				
			 
			 
*/			 
			  }

		

	

}
