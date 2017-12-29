import java.util.Comparator;
import java.util.TreeMap;

public class Test {
	public static void main(String[] args) {
		
		
          
          
          try {
        	  System.out.println("RUN NOW ");
        	//  String path = "C:\\Users\\rowan\\AppData\\Roaming\\Microsoft\\Windows\\Network Shortcuts\\CheckError.bat";
        	
        	  /*
        	  String path = "\\\\GOODFORTUNE-PC\\Java\\Excel\\bin\\CheckError.bat";
              
       	   String[] command = {"cmd.exe", "/C", "Start", path};
       	   
       	   
       	   
                 Process p =  Runtime.getRuntime().exec(command);     
                 System.out.println("RUN  OK !!! ");
		*/
        	  
//TreeMap<String,String> map = new TreeMap<String,String>();
TreeMap<String,String> map = new TreeMap<String,String>( new Comparator (){

	@Override
	public int compare(Object arg0, Object arg1) {
		
		return  ((String)arg1).compareTo((String)arg0)  ;
	} }     );

        	  
map.put("A", "1");
map.put("B", "2");
map.put("C", "3");
map.put("D", "4");
map.put("E", "5");




for(String key : map.keySet()){
	 System.out.println("maps: "+key);
}


          
          } catch (Exception e) {
      	  System.out.println("RUN NOW ERROR: "+e);
		}
	
	}
	

}
