

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import DB.MyDatabase;

public class AsxNewsChromeBrowser  extends MyDatabase {

	AsxNewsChromeBrowser(){
		
		logger.info("AsxNewsChromeBrowser run 1");

		run();
		
		close();
	}
	
	public void run() {
		File src = new File("E:\\Java\\lib3\\chromedriver.exe");
		//File src = new File("C:\\Java\\Excel\\bin\\chromedriver.exe");
		System.out.println("START  AsxNewsChromeBrowser: ");
		System.setProperty("webdriver.chrome.driver", src.getAbsolutePath());

		WebDriver driver = new ChromeDriver();
		String html = "";
		try {
			//String url = "http://www.asx.com.au/asx/statistics/todayAnns.do"; // today 
			 
			String url ="http://www.asx.com.au/asx/statistics/prevBusDayAnns.do"; // yesterday
			driver.get(url);

			try {
				Thread.sleep(30000);
				// System.out.println( "out: "+driver.getPageSource() );
				System.out.println("  AsxNewsChromeBrowser get page: ");
				html = driver.getPageSource();
				driver.quit();

			} catch (Exception e) {
				System.out.println("driver page : " + e);
				logger.severe("AsxNewsChromeBrowser driver fail to get page  " + e);

			}

		} catch (Exception e) {
			logger.severe("AsxNewsChromeBrowser Err  " + e);

		}

		finally {
			// driver.quit();
		}
		System.out.println("Run news parser : ");
		new AsxNewsString(html);

		System.out.println("FINISH : ");
		logger.info("AsxNewsChromeBrowser FINISH ");
	}
	
	public static void main(String[] args) {
		new AsxNewsChromeBrowser();
		
	}

}
