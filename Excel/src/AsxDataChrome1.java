
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import DB.MyDatabase;
import io.reactivex.Observable;

public class AsxDataChrome1 extends MyDatabase {
	public class Temp {
		String code;
		String price;
		String change;
		String changepercent;
		String open;
		String high;
		String low;
		String volume;

		public Temp(String code, String price, String change, String changepercent, String open, String high,
				String low, String volume) {
			super();
			this.code = code;
			this.price = price;
			this.change = change;
			this.changepercent = changepercent;
			this.open = open;
			this.high = high;
			this.low = low;
			this.volume = volume;
		}

		public Temp() {

		}

		@Override
		public String toString() {
			return "Temp [code=" + code + ", price=" + price + ", change=" + change + ", changepercent=" + changepercent
					+ ", open=" + open + ", high=" + high + ", low=" + low + ", volume=" + volume + "]";
		}

	}

	WebDriver driver;

	ArrayList<Temp> sqltoexec;
	ArrayList<String> allcodes;

	// Queue<String> fifo = new LinkedList<String>();
	PreparedStatement ps = null;
	WebDriverWait wait;
	boolean torunagain = true;
	int index = 0;

	public AsxDataChrome1() throws Exception {
		sqltoexec = new ArrayList<Temp>();
		// File src = new File("E:\\Java\\lib3\\chromedriver.exe");
		// File src = new File("C:\\Java\\Excel\\bin\\chromedriver.exe");

		// File src = new File("%PROGRAMFILES%\\Mozilla Firefox\\firefox.exe");
		// File src = new File("E:\\Java\\lib3\\geckodriver.exe");
		File src = new File("C:\\Java\\lib\\geckodriver.exe");

		// System.setProperty("webdriver.chrome.driver", src.getAbsolutePath());
		System.setProperty("webdriver.gecko.driver", src.getAbsolutePath());
		System.out.println("src : " + src.getAbsolutePath());

		//
		// ChromeOptions options = new ChromeOptions();
		// DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		// capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		// capabilities.setCapability("pageLoadStrategy", "none");
		//
		// driver = new ChromeDriver(capabilities);
		// driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		//

		driver = new FirefoxDriver();
		// driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

		System.out.println("done initialization: ");

		getAllCodeFileToRun();

	}

	private void getAllCodeFileToRun() throws Exception {
		allcodes = new ArrayList<String>();
		Scanner scanner = new Scanner(
				new File(AsxDataChrome1.class.getClassLoader().getResource("").getPath() + "ASXCodes.txt"));
		scanner.useDelimiter(",");
		while (scanner.hasNext()) {
			allcodes.add(scanner.next().replaceAll("\\r|\\n", "").trim().toUpperCase());
		}
		scanner.close();
	}

	private Observable<String> getObservableData() throws Exception {

		ArrayList<String> dbcode = getStockDataToday();
		System.out.println("EgetObservableData :" + dbcode.size());

		if (dbcode.size() == allcodes.size())
			return Observable.empty();
		return Observable.fromIterable(allcodes).filter((s) -> !dbcode.contains(s + ".AX"))
				.switchIfEmpty(Observable.fromIterable(allcodes));

	}

	private void getData(String codes) throws Exception {
		WebElement table = null;
		try {
			table = driver.findElement(By.className("datatable"));
		} catch (Exception e3) {
			System.out.println("wait element  time out  : ");
			TimeUnit.MINUTES.sleep(1);
			driver.navigate().refresh();
			table = driver.findElement(By.className("datatable"));

		}

		Queue<String> mycodesasx = new LinkedList<String>();

		ArrayList<WebElement> th = (ArrayList<WebElement>) table.findElements(By.xpath("//th[@class='row']"));

		th.forEach(x -> {

			// System.out.println(x);
			WebElement ele = x.findElement(By.tagName("a"));
			// System.out.println("ele -> "+ele.getText());
			mycodesasx.add(ele.getText() + ".AX");

		});

		ArrayList<WebElement> tags = (ArrayList<WebElement>) table.findElements(By.tagName("tr"));

		int count = 0;
		ArrayList<AsxDataChrome1.Temp> mydata = new ArrayList<AsxDataChrome1.Temp>();
		for (WebElement tr : tags) {

			AsxDataChrome1.Temp temp = new Temp();
			// if(!fifo.isEmpty()) temp.code = fifo.remove();
			// else temp.code="XX";

			ArrayList<WebElement> td = (ArrayList<WebElement>) tr.findElements(By.tagName("td"));

			for (WebElement tds : td) {

				// System.out.println("Loop : " );
				// System.out.println("xx "+ tds.getText());

				if (count == 0) {
					temp.price = tds.getText();
					// temp.code = fifo.remove() + ".AX";
					temp.code = mycodesasx.remove();

				} // price
				if (count == 1)
					temp.change = tds.getText(); // changes
				if (count == 2)
					temp.changepercent = tds.getText().replace("%", ""); // changes%
				if (count == 5)
					temp.open = tds.getText(); // open
				if (count == 6)
					temp.high = tds.getText(); // high
				if (count == 7)
					temp.low = tds.getText(); // low
				if (count == 8)
					temp.volume = tds.getText().replace(",", ""); // volume

				count++;
				if (count == 15) {
					count = 0;
					System.out.println("ADD *** : " + temp);
					mydata.add(temp);
					// System.out.println("done *** : " );

				}

			}

		}
		// if (fifo.size() != 0) {
		// System.out.println("************************ FIFO NOT EMPTY ->
		// *****************************");
		// fifo.forEach(s -> System.out.println("************************ FIFO
		// NOT EMPTY -> " + s));
		// torunagain = false;
		// throw new Exception("******************** FIFO is not empty :" +
		// codes);// asx listed but either our code is wrong or delisted
		// } else
		sqltoexec.addAll(mydata);

	}

	private void addData() throws Exception {
		String sql = "INSERT INTO data (code,date,open,high,low,close,volume,changes,"
				+ "changePercent) VALUES (?,?,?,?,?,?,?,?,? )";
		ps = con.prepareStatement(sql);
		for (Temp x : sqltoexec) {
			ps.setString(1, x.code);
			// ps.setString(2, LocalDate.of(2017, 11, 10) .toString());
			ps.setString(2, LocalDate.now().toString());

			ps.setDouble(3, Double.parseDouble(x.open));
			ps.setDouble(4, Double.parseDouble(x.high));
			ps.setDouble(5, Double.parseDouble(x.low));
			ps.setDouble(6, Double.parseDouble(x.price));
			ps.setDouble(7, Double.parseDouble(x.volume));
			ps.setDouble(8, Double.parseDouble(x.change));
			ps.setDouble(9, Double.parseDouble(x.changepercent));
			// System.out.println("Add data get from ps :"+ps);
			ps.addBatch();
			index++;
		}

	}

	private ArrayList<String> getStockDataToday() {
		ArrayList<String> dbcode = new ArrayList<String>();

		String sqldb = "select code from data where date=?";

		try {
			PreparedStatement psdb = con.prepareStatement(sqldb);
			;
			psdb.setString(1, LocalDate.now().toString());

			// psdb.setString(1,LocalDate.of(2017, 11, 10) .toString());

			// System.out.println("Add data get from psdb :"+psdb);
			ResultSet set = psdb.executeQuery();

			while (set.next()) {
				dbcode.add(set.getString(1).toUpperCase());
			}

		} catch (Exception e) {

			System.out.println("Err get from db :" + e);
		}
		return dbcode;
	}

	public void rundata() throws InterruptedException {
		run();
		if (!torunagain) {
			System.out.println("I'm Done!");
			Thread.sleep(20000);
			driver.quit();
			close();

		} else
			run();

	}

	private void confirmdata() throws Exception {
		System.out.println("CLOSED : ");

		System.out.println("******Add DATA : ");

		addData();
		ps.executeBatch();
		con.commit();

		System.out.println("****** DATA  COMMIT : ");
		System.out.println("****** DATA  COMMIT : INDEX RUN :" + index);
		System.out.println("****** GOODBYE - vs total 571 :");

	}

	public void run() {

		System.out.println("Err get from allcodes :" + allcodes.size());
		AtomicInteger runtwo = new AtomicInteger();
		try {
			getObservableData().buffer(10).flatMap((m) -> {
				String code = "";
				// fifo.clear();
				for (String data : m) {
					// fifo.add(data);
					code = code + data + "+";
				}
				return Observable.just(code);
			})

					.repeatUntil(() -> {
						confirmdata();
						if (index == allcodes.size() || !torunagain || runtwo.incrementAndGet() > 3)
							return true;
						index = 0;
						torunagain = true;
						// sqltoexec.clear();

						TimeUnit.MINUTES.sleep(4);
						System.out.println(" Time to  repeat ");
						return false;

					}).subscribe((s) -> {
						try {
							driver.navigate()
									.to("http://www.asx.com.au/asx/markets/equityPrices.do?by=asxCodes&asxCodes=" + s);
							getData(s);
							TimeUnit.MINUTES.sleep(2);
						} catch (Exception e) {
							System.out.println("*******subscribe error : " + s + " --  " + e);
						}

					}, e -> System.out.println(e.getMessage()), () -> {
						System.out.println("I'm Done!");
						Thread.sleep(20000);
						driver.quit();
						close();
					}

			);

		} catch (Exception e) {
			System.out.println("Err : " + e);
		}

		System.out.println("FINISH : ");

		System.out.println("FINISH   allcodes: " + allcodes.size() + " :sqlcommit :  " + sqltoexec.size());

		List<Temp> notmatch = sqltoexec.stream().filter(a -> {
			return allcodes.contains(a.code.toUpperCase().replaceAll(".AX", ""));

		}).collect(Collectors.toList());

		notmatch.forEach(c -> {

			System.out.println("NOT MATCH  : " + c.code);
		});

	}

	// public void test(){
	//
	// System.out.println("TEST : ");
	// ArrayList<Temp> sqltemp = new ArrayList<Temp>();
	//
	// sqltemp.add(new Temp("ABC.AX","","", "", "", "", "","") );
	// sqltemp.add(new Temp("AGL.AX","","", "", "", "", "","") );
	//
	//
	// List<Temp> notmatch = sqltemp.stream().filter(a->{
	// return allcodes.contains(a.code.toUpperCase().replaceAll(".AX", ""));
	//
	// } ) .collect(Collectors.toList());
	//
	// notmatch.forEach(c->{
	//
	// System.out.println("NOT MATCH : "+ c.code);
	// });
	//
	// }

	public static void main(String[] args) {

		// String arr[]= new String[7];
		// arr[0]=
		String config = null;
		for (int i = 0; i < args.length; i++) {
			config = args[0];
		}
		AsxDataChrome1 asx;
		try {
			asx = new AsxDataChrome1();
			if (config != null)
				asx.index = Integer.parseInt(config.trim());
			asx.run();
			// asx.test();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("main  : " + e);

		}

	}
}