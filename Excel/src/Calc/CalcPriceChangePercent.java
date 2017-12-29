package Calc;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

import DB.MyDatabase;
import io.reactivex.Observable;

public class CalcPriceChangePercent extends MyDatabase{


	

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
			 "CCV","CDD","CKF","CL1","CNU","CSV","CWP","DCN","DNA","DRM","DYL","ELD","EML","EPW","EQT","EWC","FAR","FET","FNP",
			 "FSF","GBT","GDI","GOR","NGI","HFR","HPI","HSN","HUO","IDR","IEL","IFM","IMF","INA","IPD","ISU","KAR","KGN","KMD","LNG","LYC","MGC",
			 "MLD","MLX","MNS","MOC","MSB","MVF","NAN","NHF","NTC","OGC","OML","PDN","PEN","PLS","PRU","RCG","RCR","RFF","RIC","SDA","SEH","SGF","SHV",
			 "SIQ","SIV","SLK","SPL","SSM","SXY","TGA","TOE","TOX","TRS","UEQ","VLW","VRL","VTG","WBA","WPP","WTC"};
			 
	String arr4[]=new String[]{"AKP","AUB","AUI","CBL","CDP","CEN","CIN","CVW","DJW","DUI","FLN","GTN","IXI","LEP","MFF","MGE","PME","RVA","SRV","SST","URF","VAF","ZIM","TGH","SCO","PSI","HLO","PPC","OHE","NCK","SLF","PEP","MIR","PLG","CUV","WLE","FGX","ALF","DFM","YAL","IRI","PPH","ASL","LIC","MPP","SLC","QMS","DDR","PMC","LOV","MYS","WHF",
			"BLX","AGG","MGX","IXP","PSQ","CDM","TBR","HHV","MNF","SLR","UOS","PNI","QIP","APL","SFY","PGF","MVP","FGG","ALK","AOF","AFP","NEA","AXP","PWH","TGG","APX","PAI","QVE","HUB","RAP","EXP",
			"VLA","RMS","AFG","VGS","WAX","PHI"};
	String arr5[]=new String[]{"CAT","8IH","MAQ","PMP","CII","CMA","ALI","EDE","CDA","SDG","RHL","MOY","BBG","GNG","MNY","CGL","ONE","IDX",
			 "AVJ","GTK","PIC","AMH","VAS","DTL","TIL","TGP","PEA","SHJ","ADA","SOM","ABA","BLK","WLL","SMN","FRI","IMD","SEA","CLQ","CVC","MLB","MUA","NMT","CDV","ENN","IGL","FLC","GOW","BFG","PPS","CTN","COE","RCT","RBL","NWH",
			 "RKN","HOM","EMF","CLH","DCG","BNO","TRY","OVH","KSC","CDU","AJD","AUF","WTP","CZZ","KSL","EZL","PNV","EGH","ONT","OCL","TZN","IBK","EVO","KDR","DWS","ATU","WAF","MTO","SMR","AJM","MRN","FSA","RDV","GCY","88E","BSE","3PL",
			 "COG","UPD","IIL","RFX","MP1","TPE","BAF","ELX","TWR","PNR","LOM","NZK","GZL","BSA","CMP","UBA","AHX","HIL","AVB","PGC","AJL","EGS","AFA","RND","FLK","MVW","MML","SSG","NBL","SEN","RHP","APD","ADJ","OBJ","GRR","1AL","MEA","SGH",
			 "BLY","ZML","NZM","OTW","RXP","BCK","TIG","SHM","CGS","VGB","CVN","HAV","YOW","WIC","BFC","LAU","FWD","AGO","CDC","AQC","TOP","NNW","TTC","FND","MJP","SDI","GAP","MBE","IOZ","BLG","XIP","LHB","MCP","AVG","MXI","APZ","GVF","TNG",
			 "AGD","RUL","MAH","OPT","IAA","AWN","PPG","TAM","RAN","LYL","EAI","S2R","PRT","RCB","ORL","OSP","MRG","VRS"};

	
	
	PreparedStatement ps = null;
	 
String sql ="SELECT code,date,close FROM data WHERE   date in(?,?) and code=? order by date desc";
PreparedStatement psu = null;

String sql1 ="update data set changes =?, changepercent=?  where    date =? and code=? ";
	
	CalcPriceChangePercent(String date )throws Exception{
		allcodes = new ArrayList<String>();
		allcodes.addAll(Arrays.asList(arr1));
		allcodes.addAll(Arrays.asList(arr2));
		allcodes.addAll(Arrays.asList(arr3));
		allcodes.addAll(Arrays.asList(arr4));
		allcodes.addAll(Arrays.asList(arr5));
		ps = con.prepareStatement(sql);
		psu = con.prepareStatement(sql1);
//		LocalDate.parse(country[1], DateTimeFormatter.ofPattern("yyyyMMdd")).toString();	

		this.date = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		
		
	}
	
	
	
	 ArrayList<String > allcodes;
	
	
	 LocalDate date 	;
	
	
	
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
	
	
	 private void addData(String code)throws Exception{
		 System.out.println("addData :"+code );
		 ps.setString(1,date.toString());
		
		  
		  if(date.getDayOfWeek() == DayOfWeek.MONDAY ){
				ps.setString(2 ,date.minusDays(3) .toString());
			}else{
				  ps.setString(2 ,date.minusDays(1) .toString());
			}
			
		  
		  ps.setString(3,code+".AX");
		  
		  
		  
		 System.out.println("-------------- addData " + ps);
		  
			 ResultSet set  = ps.executeQuery();
			 int count =0;
			 double closenow=0;
			 double closeyest=0;

			 while(set.next()){
				if(count == 0){
					//set.getString(1).
					closenow = set.getDouble(3);
					count++;
					
				}else{
					closeyest = set.getDouble(3);
				count=0;
				}
			 
			 
			 
			 }
			 update(  closenow,  closeyest , code);
	//System.out.println("-------------- code " + closenow);
	//System.out.println("-------------- code " + closeyest);
	 
	 }
	 private double getDouble(double value){
		 
		 DecimalFormat df = new DecimalFormat("0.00");      
		 return  Double.parseDouble(df.format(value));
	 }
	 private void update( double closenow, double closeyest , String code)throws Exception{
		  
		 double change =  closenow - closeyest;
		 change = getDouble(change);
		// System.out.println("-------------- change " + change);
		 
		 psu.setDouble(1, change);
		 psu.setDouble(2, getDouble(change/closeyest) );
	 	 psu.setString(3,date.toString());
	 	 psu.setString(4,code+".AX");
	 	 
	 	  System.out.println("-------------- UPDATE xx " + psu);
		  
	 	// psu.executeUpdate();
	 	  psu.addBatch();
	 	 
	 }	 
	 
	

	public void run() throws Exception{
		 System.out.println("START ");
	
	
		 Observable
		  .fromIterable(allcodes)

		  .doOnNext((x)->{
			  addData(x);
//			  ps.setString(1,date.toString());
//			  ps.setString(2 ,date.minusDays(1) .toString());
//			  ps.setString(3,x+".AX");
//   			 
//			  ps.addBatch();
//			
//			
	
		  })
		  //.onErrorResumeNext((t)->System.out.println("on error resume :"+t ))
		  .doOnError((t)->close())
		  
		  .subscribe(
				  (x)->{
							System.out.println("subscribs :"+x );
						},
				  
				  (x)->{
						System.out.println("Error :"+x );
					},
				  ()->{
						System.out.println("complete :" );
						 psu.executeBatch();
							con.commit();
							System.out.println("out :" );

				  }
			
				  
  				);
		
		 System.out.println("DONE ");
		
	}
	public void close(){
		
		try {
		
			ps.close();
			psu.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.close();
	}
	
	public static void main(String[] args) {
//		LocalDate fromCustomPattern = LocalDate.parse("20171103", DateTimeFormatter.ofPattern("yyyyMMdd"));
//	System.out.println( "custom date "+ fromCustomPattern);
		
try {
	
	
	String arr1[]= new String[]{"10","13","14","15","16","17","20","21","22","23","24","21","22","23","24","27"};
	//String arr1[]= new String[]{"27"};
	for(String days : arr1){
		CalcPriceChangePercent yah = new CalcPriceChangePercent("2017-11-"+days) ;
		yah.run();
		yah.close();
		
	
	}
	
	
	
} catch (Exception e) {
	// TODO Auto-generated catch block
	System.out.println("ERRR RUN "+ e);
	e.printStackTrace();
}
	
	}
}
