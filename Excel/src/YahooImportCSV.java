

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import DB.MyDatabase;
import io.reactivex.Observable;

public class YahooImportCSV extends MyDatabase{
	String arr1[]= new String[]{"ABC","AGL","ALL","ALQ","AMC","AMP","ANN","ANZ","APA","AST","ASX","AWC","AZJ","BEN","BHP","BKL","BLD","BOQ","BSL","BXB","CAR","CBA",
			"CCL","CGF","CIM","COH","CPU","CSL","CSR","CTX","CWN","CYB","DLX","DMP","DOW","DXS","FLT","FMG","FXJ","GMG","GNC","GPT","JHG","HSO",
			"HVN","IAG","IFL","ILU","INM","IOF","IPL","JBH","JHX","LLC","MFG","MGR","MPL","MQG","NAB","NCM","NST","NVT","ORA","ORG","ORI","OSH","PPT","PRY",
			"QAN","QBE","QUB","REA","RHC","RIO","RMD","S32","SCG","SEK","SGP","SGR","SHL","SKI","SRX","STO","SUN","SYD","TAH","TCL","TLS","TPM","TTS","TWE","VCX","VOC","WBC","WES","WFD","WOW",
			"WPL"};


	String arr2[]=new String[]{"A2M","AAC","AAD","ABP","ACX","AHG","AHY","ALU","AOG","API",
			"HT1","APO","ARB","BAL","BAP","BGA","BKW","BPT","BRG","BTT","BWP","CCP","CGC",
			"CHC","CMW","CQR","CTD","CWY","ECX","EHE","EVN","FBU","FPH","FXL","GEM","GMA","GOZ","GTY",
			"GUD","GWA","GXL","GXY","IFN","IGO","IPH","IRE","ISD","IVC","JHC","LNK","MIN","MMS","MND","MQA","MTR","MTS","MYO","MYR","MYX","NEC","NSR","NUF","NWS","NXT","OFX","ORE","OZL","PGH","PMV","PTM","REG","RFG","RRL","RSG","RWC","SAR",
			"SBM","SCP","SDF","SFR","SGM","SIG","SKC","SKT","SPK","SPO","SUL","SVW","SWM","SXL","SYR","TGR","TME","TNE","VRT","WEB","WHC","WOR","WSA"};
	String arr3[]=new String[]{"ADH","AGI","AIA","AJX","AMA","AQG","ARF","ASB","AVN","AWE","AYS","BBN","BDR","BKY","BLA","BMN","BWX","CAB",
			 "CCV","CDD","CKF","CL1","CNU","CSV","CWP","CXU","DCN","DNA","DRM","DYL","ELD","EML","EPW","EQT","EWC","FAR","FET","FNP",
			 "FSF","GBT","GDI","GOR","HFA","HFR","HPI","HSN","HUO","IDR","IEL","IFM","IMF","INA","IPD","ISU","KAR","KGN","KMD","LNG","LYC","MGC",
			 "MLD","MLX","MNS","MOC","MSB","MVF","NAN","NHF","NTC","OGC","OML","PDN","PEN","PLS","PRU","RCG","RCR","RFF","RIC","SDA","SEH","SGF","SHV",
			 "SIQ","SIV","SLK","SPL","SSM","SXY","TEN","TGA","TOE","TOX","TRS","UEQ","VLW","VRL","VTG","WBA","WPP","WTC"};
			 
	String arr4[]=new String[]{"AKP","AUB","AUI","CBL","CDP","CEN","CIN","CVW","DJW","DUI","FLN","GTN","IXI","LEP","MFF","MGE","PME","RVA","SRV","SST","URF","VAF","ZIM","TGH","SCO","PSI","HLO","PPC","OHE","NCK","SLF","PEP","VGL","MIR","PLG","CUV","WLE","FGX","ALF","DFM","YAL","IRI","PPH","ASL","LIC","MPP","SLC","QMS","DDR","PMC","LOV","MYS","WHF",
			"BLX","AGG","MGX","IXP","PSQ","CDM","TBR","HHV","MNF","SLR","UOS","PNI","QIP","APL","SFY","PGF","MVP","FGG","ALK","AOF","AFP","NEA","AXP","PWH","TGG","APX","PAI","QVE","HUB","RAP","EXP",
			"VLA","RMS","AFG","VGS","WAX","PHI"};
	String arr5[]=new String[]{"CAT","8IH","MAQ","PMP","CII","CMA","ALI","EDE","CDA","SDG","RHL","MOY","BBG","GNG","MNY","CGL","ONE","IDX",
			 "AVJ","GTK","PIC","AMH","VAS","DTL","TIL","TGP","PEA","SHJ","ADA","SOM","ABA","BLK","WLL","SMN","FRI","IMD","SEA","CLQ","CVC","MLB","MUA","NMT","CDV","ENN","IGL","FLC","GOW","BFG","PPS","CTN","COE","RCT","RBL","NWH",
			 "RKN","HOM","EMF","CLH","DCG","BNO","TRY","OVH","KSC","CDU","AJD","AUF","WTP","CZZ","KSL","EZL","PNV","EGH","ONT","OCL","TZN","IBK","EVO","KDR","DWS","ATU","WAF","MTO","SMR","AJM","MRN","FSA","RDV","GCY","88E","BSE","3PL",
			 "COG","UPD","IIL","RFX","MP1","TPE","BAF","ELX","TWR","PNR","LOM","NZK","GZL","BSA","CMP","UBA","AHX","HIL","AVB","PGC","AJL","EGS","AFA","RND","FLK","MVW","MML","SSG","NBL","SEN","RHP","APD","ADJ","OBJ","GRR","1AL","MEA","SGH",
			 "BLY","ZML","NZM","OTW","RXP","BCK","TIG","SHM","CGS","VGB","CVN","HAV","YOW","WIC","BFC","LAU","FWD","CD2","AGO","CDC","AQC","TOP","NNW","TTC","FND","MJP","SDI","GAP","MBE","IOZ","BLG","XIP","LHB","MCP","PHG","AVG","MXI","APZ","GVF","TNG",
			 "AGD","RUL","MAH","OPT","IAA","AWN","PPG","TAM","RAN","LYL","EAI","S2R","PRT","RCB","ORL","OSP","MRG","VRS"};
	
	 ArrayList<String > allcodes;
	
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
	ArrayList<Temp> filedata = new ArrayList<Temp>();
	 String  sql="INSERT INTO data (code,date,open,high,low,close,volume"
				+ ") VALUES (?,?,?,?,?,?,? )"; 		
	PreparedStatement ps = null;
		 
	public class Temp{
		String code;
		public String date;
		public double price;
		public double open;
		public double high;
		public double low;
		public double volume;
		public Temp(String code,String date, String price, String open, String high, String low,
				String volume) {
			super();
			this.code=code;
			this.date=date;
			this.price = Double.parseDouble(price);
			this.open = Double.parseDouble(open);
			this.high = Double.parseDouble(high);
			this.low = Double.parseDouble(low);
			this.volume = Double.parseDouble(volume);
		}
		public Temp(){
			
		}
		@Override
		public String toString() {
			return "Temp [code=" + code + ", date=" + date + ", price=" + price + ", open=" + open + ", high=" + high
					+ ", low=" + low + ", volume=" + volume + "]";
		}
				
		
		
	}
//	String torunfile[]=new String[]{
//		"RVA",	"ZIM",	"OHE",	"VGL",	"MPP",	"AGG",	"PSQ",	"UOS",	"AFP",	"PHI ",	"8IH",	"EMF",	"AUF",	"EVO",	"AFA",	"BCK",	"LAU",	"LHB",	"MJP","SDI","LHB"};
//	
	String torunfile[]=new String[]{
	"CII","NZK","ONT","VRS"};
//	
	
	String filename = "";
	String code="";
	YahooImportCSV() throws Exception{
		allcodes = new ArrayList<String>();
//		allcodes.addAll(Arrays.asList(arr1));
//		allcodes.addAll(Arrays.asList(arr2));
//		allcodes.addAll(Arrays.asList(arr3));
//		allcodes.addAll(Arrays.asList(arr4));
//		allcodes.addAll(Arrays.asList(arr5));
	//	allcodes.addAll(Arrays.asList(torunfile));
		String myfile = "E:/TEMP/";
		 filename = myfile;
		for(String name :torunfile ){
		
			code= name+".AX";
			filename = filename+code+".csv";
			System.out.println("file :"+filename);
			 addData();
			 filename = myfile;
			 
		}
		
		ps = con.prepareStatement(sql);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public void addData()throws Exception{
		 try {
			 	AtomicInteger atom = new AtomicInteger();
	            br = new BufferedReader(new FileReader(filename));
	            while ((line = br.readLine()) != null) {
	            	
	            	if(atom.getAndIncrement()==0) continue;
	            	
	                // use comma as separator
	                String[] country = line.split(cvsSplitBy);
	             // System.out.println("Country [code= " + country[1] + " , name=" + country[2] + "]");
	              if(country[1].contains("null")) continue;

	              String date = LocalDate.parse(country[0], DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString();	
	                filedata.add(new Temp(code,date, country[4],country[1], country[2], country[3],
	                		country[6]));
	                
	                
	              //  System.out.println("Country [code= " + country[4] + " , name=" + country[5] + "]");

	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
		// System.out.println("err data "+ filedata.size());
	//	filedata.forEach( (s)->{ System.out.println(s);} );
	}
	
	
	
	
//	private ArrayList<String>  getStockDataToday(){
//		ArrayList<String> dbcode = new ArrayList<String>();
////		db = LocalDate.of(2017, 11, 3);// date to change
//
//		 String  sqldb ="select code from data where date=?"; 
//		 ArrayList<String> torun = new ArrayList<String>();
//		 
//		 try {
//			 PreparedStatement psdb = 	con.prepareStatement(sqldb);  ;
//			 psdb.setString(1,LocalDate.now()  .toString());
//			 ResultSet set  = psdb.executeQuery();
//			 while(set.next()){
//				 dbcode.add(set.getString(1).toUpperCase());
//			 }
//			 
//		} catch (Exception e) {
//
//			System.out.println("Err get from db :"+e);
//		}
//		 return 	dbcode;
//		
//		
//	}
	
	
	
	 PreparedStatement psdb;
	public void run() throws Exception{
		 System.out.println("START ");
		 String  sqldb ="select code from data where date=? and code=?"; 
		 psdb = 	con.prepareStatement(sqldb);  ;
	
		 Observable
		  .fromIterable(filedata)
		  .filter((s)->{
			  
			  psdb.setString(1,s.date);
			  psdb.setString(2,s.code);

			 // System.out.println("psdb :"+psdb );
			  ResultSet set  = psdb.executeQuery();
			  return !set.next();
			  
		  } )
		  .doOnNext((x)->{
				
//			System.out.println("subscribe error :"+s);
				ps.setString(1, x.code);
				ps.setString(2, x.date);
				ps.setDouble(3, x.open);
				ps.setDouble(4, x.high);
				ps.setDouble(5, x.low);
				ps.setDouble(6, x.price);
				ps.setDouble(7, x.volume);

				System.out.println("**********db INSERT :"+ps );
				ps.addBatch();
			
			
	
		  })
		  .onErrorResumeNext((t)->System.out.println("on error resume :"+t ))
		  
		  
		  .subscribe(
				  (x)->{
							System.out.println("subscribs :"+x );
						},
				  
				  (x)->{
						System.out.println("Error :"+x );
					},
				  ()->{
						System.out.println("complete :" );
						 ps.executeBatch();
							con.commit();
							System.out.println("out :" );

				  }
			
				  
  				);
		
		 System.out.println("DONE ");
		
	}
	public void close(){
		
		try {
			psdb.close();
			ps.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.close();
	}
	
	public static void main(String[] args) {
//		LocalDate fromCustomPattern = LocalDate.parse("20171103", DateTimeFormatter.ofPattern("yyyyMMdd"));
//	System.out.println( "custom date "+ fromCustomPattern);
		
try {
	YahooImportCSV yah = new YahooImportCSV() ;
	yah.run();
	yah.close();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
	
	}
}
