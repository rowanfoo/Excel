
public class ExcelUtil {

	public static String  cleanStringNumber (String name ){
		
		if ( name.equals("N/A") )return "0";
		if ( name.equals("+inf%") )return "0";
		
		return name;
		
		
	}
		

	public static String  cleanString(String name ){
		
		if ( name.equals("N/A") )return "";
		if ( name.equals("+inf%") )return "";
		
		return name;
		
		
	}
}
