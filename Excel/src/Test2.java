import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import DB.MyDatabase;



public class Test2 extends MyDatabase  {
	String arr1[]= new String[]{"ABC","AGL","ALL","ALQ","AMC","AMP","ANN","ANZ","APA","AST","ASX","AWC","AZJ","BEN","BHP","BKL","BLD","BOQ","BSL","BXB","CAR","CBA",
			"CCL","CGF","CIM","COH","CPU","CSL","CSR","CTX","CWN","CYB","DLX","DMP","DOW","DXS","FLT","FMG","FXJ","GMG","GNC","GPT","JHG","HSO",
			"HVN","IAG","IFL","ILU","INM","IOF","IPL","JBH","JHX","LLC","MFG","MGR","MPL","MQG","NAB","NCM","NST","NVT","ORA","ORG","ORI","OSH","PPT","PRY",
			"QAN","QBE","QUB","REA","RHC","RIO","RMD","S32","SCG","SEK","SGP","SGR","SHL","SKI","SRX","STO","SUN","SYD","TAH","TCL","TLS","TPM","TTS","TWE","VCX","VOC","WBC","WES","WFD","WOW",
			"WPL"};

	
	
	String arr2[]=new String[]{"A2M","AAC","AAD","ABP","ACX","AHG","AHY","ALU","AOG","API",
			"HT1","APO","ARB","BAL","BAP","BGA","BKW","BPT","BRG","BTT","BWP","CCP","CGC",
			"CHC","CMW","CQR","CTD","CWY","ECX","EHE","EVN","FBU","FPH","FXL","GEM","GMA","GOZ","GTY",
			"GUD","GWA","GXL","GXY","IFN","IGO","IPH","IRE","ISD","IVC","JHC","LNK","MIN","MMS","MND","MQA","MTR","MTS","MYO","MYR","MYX","NEC","NSR","NUF","NWS","NXT","OFX","ORE","OZL","PGH","PMV","PTM","REG","RFG","RRL","RSG","RWC","SAR",
			"SBM","SCP","SDF","SFR","SGM","SIP","SKC","SKT","SPK","SPO","SUL","SVW","SWM","SXL","SYR","TGR","TME","TNE","VRT","WEB","WHC","WOR","WSA"};
	String arr3[]=new String[]{"ADH","AGI","AIA","AJX","AMA","AQG","ARF","ASB","AVN","AWE","AYS","BBN","BDR","BKY","BLA","BMN","BWX","CAB",
			 "CCV","CDD","CKF","CL1","CNU","CSV","CWP","CXU","DCN","DNA","DRM","DYL","ELD","EML","EPW","EQT","EWC","FAR","FET","FNP",
			 "FSF","GBT","GDI","GOR","HFA","HFR","HPI","HSN","HUO","IDR","IEL","IFM","IMF","INA","IPD","ISU","KAR","KGN","KMD","LNG","LYC","MGC",
			 "MLD","MLX","MNS","MOC","MSB","MVF","NAN","NHF","NTC","OGC","OML","PDN","PEN","PLS","PRG","PRU","RCG","RCR","RFF","RIC","SDA","SEH","SGF","SHV",
			 "SIQ","SIV","SLK","SPL","SSM","SXY","TEN","TGA","TOE","TOX","TRS","UEQ","VLW","VRL","VTG","WBA","WPP","WTC"};
			 
	String arr4[]=new String[]{"AKP","AUB","AUI","CBL","CDP","CEN","CIN","CVW","DJW","DUI","FLN","GTN","IXI","LEP","MFF","MGE","PME","RVA","SRV","SST","URF","VAF","ZIM","TGH","SCO","PSI","HLO","PPC","OHE","NCK","SLF","PEP","VGL","MIR","PLG","CUV","WLE","FGX","ALF","DFM","YAL","IRI","PPH","ASL","LIC","MPP","SLC","QMS","DDR","PMC","LOV","MYS","WHF",
			"BLX","AGG","MGX","IXP","PSQ","CDM","TBR","HHV","MNF","SLR","UOS","PNI","QIP","APL","SFY","PGF","MVP","FGG","ALK","AOF","AFP","NEA","AXP","PWH","TGG","APX","PAI","QVE","HUB","RAP","EXP",
			"VLA","RMS","AFG","VGS","WAX","PHI"};
	String arr5[]=new String[]{"CAT","8IH","MAQ","PMP","CII","CMA","ALI","EDE","CDA","SDG","RHL","MOY","BBG","GNG","MNY","CGL","ONE","IDX",
			 "AVJ","GTK","PIC","AMH","VAS","DTL","TIL","TGP","PEA","SHJ","ADA","SOM","ABA","BLK","WLL","SMN","FRI","IMD","SEA","CLQ","CVC","MLB","MUA","NMT","CDV","ENN","IGL","FLC","GOW","BFG","PPS","CTN","COE","RCT","RBL","NWH",
			 "RKN","HOM","EMF","CLH","DCG","BNO","TRY","OVH","KSC","CDU","AJD","AUF","WTP","CZZ","KSL","EZL","PNV","EGH","ONT","OCL","TZN","IBK","EVO","KDR","DWS","ATU","WAF","MTO","SMR","AJM","MRN","FSA","RDV","GCY","88E","BSE","3PL",
			 "AIK","UPD","IIL","RFX","MP1","TPE","BAF","ELX","TWR","PNR","LOM","NZK","GZL","BSA","RWH","CMP","UBA","AHX","HIL","AVB","PGC","AJL","EGS","AFA","RND","FLK","MVW","MML","SSG","NBL","SEN","RHP","APD","ADJ","OBJ","GRR","1AL","MEA","SGH",
			 "BLY","ZML","NZM","OTW","RXP","BCK","TIG","SHM","CGS","VGB","CVN","HAV","YOW","WIC","BFC","LAU","USG","FWD","AGO","CDC","AQC","TOP","NNW","TTC","FND","MJP","SDI","GAP","MBE","IOZ","BLG","XIP","LHB","MCP","PHG","AVG","MXI","APZ","GVF","TNG",
			 "AGD","RUL","MAH","OPT","IAA","AWN","PPG","TAM","RAN","LYL","EAI","S2R","PRT","RCB","SMX","ORL","OSP","MRG","VRS"};
	 String  sql="INSERT INTO data (code,date,open,high,low,close,volume,changes,"
				+ "changePercent) VALUES (?,?,?,?,?,?,?,?,? )"; 		
		
		 PreparedStatement ps = null;	
		 ArrayList<String []> allcodes;
	Test2(){
		allcodes = new ArrayList<String []>();
		allcodes.add(arr1);
		allcodes.add(arr2);
		allcodes.add(arr3);
		allcodes.add(arr4);
		allcodes.add(arr5);
	}
	
	
	private void addData() throws Exception {
		ps = con.prepareStatement(sql);
		for(String arr[] : allcodes){
			
		
		
		for (String codes : arr) {
			ps.setString(1,codes+".AX");
//			
			//System.out.println("code: "+ codes);
			ps.setString(2, LocalDate.now() .toString());
			
			ps.setDouble(3,0 );
			ps.setDouble(4, 0);
			ps.setDouble(5,0 );
			ps.setDouble(6,0 );
			ps.setDouble(7,0 );
			ps.setDouble(8,0 );
			ps.setDouble(9,0 );
			System.out.println("sql "+ ps );

			ps.execute();
			System.out.println("done");

		}
		}

	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		try {
			new Test2().addData();
		} catch (Exception e) {
			System.out.println(" xxx err "+e );
		}
	}

}
