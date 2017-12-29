package Admin;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import Calc.CalcPriceChangePercent;
import Calc.CalcRSI;
import DB.MyDatabase;
import io.reactivex.Observable;
import Calc.CalcChangePercent2;
import Calc.CalcDataAverage;
public class ASXCSVImport extends MyDatabase{

	
	 ArrayList<String > allcodes;
	String csvFile = "E:/Java/Project/Selenium/bin/";
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = " ";
	ArrayList<Temp> filedata = new ArrayList<Temp>();

	PreparedStatement ps = null;
		 
	public class Temp{
		String code;
		public String date;
		public double price;
		public double change;
		public double changepercent;
		public double open;
		public double high;
		public double low;
		public double volume;
		public Temp(String code,String date, String price,String change, String changepercent, String open, String high, String low,
				String volume) {
			super();
			this.code=code;
			this.date=date;
			this.price = Double.parseDouble(price);
			this.change =Double.parseDouble( change);
			this.changepercent = Double.parseDouble(changepercent);
			this.open = Double.parseDouble(open);
			this.high = Double.parseDouble(high);
			this.low = Double.parseDouble(low);
			this.volume = Double.parseDouble(volume);
		}
		public Temp(){
			
		}
		@Override
		public String toString() {
			return "Temp [code=" + code + ", price=" + price + ", change=" + change + ", changepercent=" + changepercent
					+ ", open=" + open + ", high=" + high + ", low=" + low + ", volume=" + volume + "]";
		}
		public boolean equals(Object o)
	    {
	        if (o == null) return false;
	        if (o == this) return true; //if both pointing towards same object on heap

	        Temp a = (Temp) o;
	        return this.code.equals(a.code);
	    }
		
		
		
	}
	public ASXCSVImport(String file) throws Exception{
		csvFile = csvFile+file;
		csvFile = file;
//		allcodes = new ArrayList<String>();
//		allcodes.addAll(Arrays.asList(arr1));
//		allcodes.addAll(Arrays.asList(arr2));
//		allcodes.addAll(Arrays.asList(arr3));
//		allcodes.addAll(Arrays.asList(arr4));
//		allcodes.addAll(Arrays.asList(arr5));
		getAllCodeFileToRun();
	}
	
	public ASXCSVImport() throws Exception{
		getAllCodeFileToRun();
	}
	   
	
	private void getAllCodeFileToRun()throws Exception{
		allcodes = new ArrayList<String>();

		

		System.out.println("ASXCSVImport  getAllCodeFileToRun " + ASXCSVImport.class.getClassLoader().getResource("").getPath() +"ASXCodes.txt" );
		Scanner scanner = new Scanner(new File( ASXCSVImport.class.getClassLoader().getResource("").getPath() +"ASXCodes.txt"  ));
		scanner.useDelimiter(",");
        while(scanner.hasNext()){
        	allcodes.add( scanner.next().replaceAll("\\r|\\n", "").trim().toUpperCase() );
        }
        scanner.close();
        System.out.println(" getAllCodeFileToRun codes " +allcodes.size() );
 //       new File( ASXCSVImport.class.getClassLoader().getResource("").getPath() +"ASXCodes.txt"  ).renameTo(dest)
	}
	
	
//	
//	public void addData() throws Exception {
//		try {
//
//			br = new BufferedReader(new FileReader(csvFile));
//			while ((line = br.readLine()) != null) {
//
//				// use comma as separator
//				String[] country = line.split(cvsSplitBy);
//				String date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
//				filedata.add(new Temp(country[0], date, country[5], "0", "0", country[2], country[3], country[4],
//						country[6]));
//				System.out.println(country[0] +" , "+  country[5]);
//
//			}
//
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (br != null) {
//				try {
//					br.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//
////	
//
//	
//	
//	
//	}

	public void setFileData()throws Exception {
	
		String content = new String(Files.readAllBytes(Paths.get(csvFile)));
		addData( content);
	}
	
	public void addData(String content) throws Exception {
		
		Scanner scanner = new Scanner(content);
		while (scanner.hasNextLine()) {

				// use comma as separator
				String[] country = scanner.nextLine().split(cvsSplitBy);
			
				String date = LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyyyMMdd")).toString();
				filedata.add(new Temp(country[0], date, country[5], "0", "0", country[2], country[3], country[4],
						country[6]));
			//	System.out.println("ASXCSVImport  addData : " + country[0] +" , "+  country[5]);

			}

			scanner.close();

	
	
	
	}

	 
	
	Observable<Temp> allData;

	public void run() throws Exception {
		System.out.println("START ");
		 setFileData();
		allData = Observable.fromIterable(filedata)
				.filter((s) -> allcodes.contains(s.code));

	}

	/**
	 * add data into system
	 * @throws Exception 
	 */
	private void insertAll() throws Exception {
		AtomicInteger atom = new AtomicInteger();
		String sql = "INSERT INTO data (code,date,open,high,low,close,volume,changes,"
				+ "changePercent) VALUES (?,?,?,?,?,?,?,?,? )";
		String sql1 = "select * from data where  date = ? and code =?";

		PreparedStatement ps1 = con.prepareStatement(sql1);
		ps = con.prepareStatement(sql);

		allData.subscribe(

				(x) -> {
					String code = x.code + ".AX";
					ps1.setString(1, x.date);
					ps1.setString(2, code);

					if (!ps1.executeQuery().next()) {// if data already exsits
														// dont do anything
						ps.setString(1, code);
						ps.setString(2, x.date);

						ps.setDouble(3, x.open);
						ps.setDouble(4, x.high);
						ps.setDouble(5, x.low);
						ps.setDouble(6, x.price);
						ps.setDouble(7, x.volume);
						ps.setDouble(8, x.change);
						ps.setDouble(9, x.changepercent);

						// System.out.println("subscribs :"+ps );
						ps.addBatch();
				
						atom.getAndIncrement();
					}

				});

		ps.executeBatch();
		con.commit();
		
		ps.close();
		ps1.close();
		
		System.out.println("DONE insertAll COUNT :" + atom.incrementAndGet());
		System.out.println("DONE insertAll FILE : " + csvFile);
		
		
		changePricePercent();
		System.out.println("DONE insertAll ");
		System.out.println("DONE insertAll for date :" + filedata.get(0).date);
		
		
		File file  = new File (csvFile);
		String newname = csvFile.substring(0,  csvFile.lastIndexOf("."));
		System.out.println("File change ");
		
		file.renameTo(new File ( newname+"-"+filedata.get(0).date+".txt"));
		
		System.out.println("DONE File change  ");
		
		
		

	}
	
	private void changePricePercent() throws Exception{
		System.out.println("changePricePercent ");
		CalcChangePercent2 yah = new CalcChangePercent2(filedata.get(0).date ) ;
		yah.run();
		System.out.println("DONE changePricePercent ");

		new CalcRSI(filedata.get(0).date).run();
		new CalcDataAverage(filedata.get(0).date).getData();
		
		
		System.out.println("DONE RSI done ");
	}

	
	/**
	 * used when got error in data , then just update all data 
	 * @throws Exception 
	 */
	private void updateAll() throws Exception {
		AtomicInteger atom = new AtomicInteger();
		String sql = "update data set open=?,high=?,low=?,close=?,volume=?,changes=?,"
				+ "changePercent=? where code=? and date=? ";
		ps = con.prepareStatement(sql);
		allData.subscribe(

				(x) -> {
					String code = x.code + ".AX";
					ps.setDouble(1, x.open);
					ps.setDouble(2, x.high);
					ps.setDouble(3, x.low);
					ps.setDouble(4, x.price);
					ps.setDouble(5, x.volume);
					ps.setDouble(6, x.change);
					ps.setDouble(7, x.changepercent);

					ps.setString(8, code);
					ps.setString(9, x.date);
					ps.addBatch();
					atom.getAndIncrement();
				});

		ps.executeBatch();
		con.commit();
		ps.close();
		System.out.println("DONE UPDATEALL COUNT :" + atom.incrementAndGet());
		System.out.println("DONE UPDATEALL FILE : " + csvFile);
		changePricePercent() ;
	
		System.out.println("DONE UPDATEALL for date :" + filedata.get(0).date);

	

	}
	

	
	
	
	
	public static void main(String[] args) {
		// LocalDate fromCustomPattern = LocalDate.parse("20171103",
		// DateTimeFormatter.ofPattern("yyyyMMdd"));
		// System.out.println( "custom date "+ fromCustomPattern);

		try {

//			String arr1[] = new String[] { "10", "13", "14", "15", "16", "17", "20", "21", "22", "23", "24", "21", "22",
//					"23", "24", "27" };
//			// String arr1[]= new String[]{"27"};
//			for (String days : arr1) {
//				ASXCSVImport axs = new ASXCSVImport("201711" + days + ".txt");
//				axs.run();
//				axs.updateAll();
//				
//			}
//			ASXCSVImport axs = new ASXCSVImport("HistoricalData.txt");
			//MetaStock
			ASXCSVImport axs = new ASXCSVImport("E://HistoricalData.txt");
			axs.run();
			axs.insertAll();
			
			System.out.println("END GOODYBE");

		} catch (Exception e) {
			System.out.println("ERROR START :" + e);
			e.printStackTrace();
		}

	}
}
