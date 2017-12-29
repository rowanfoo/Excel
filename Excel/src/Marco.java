
public class Marco {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String cmd = "wscript D:\\Java\\Excel\\bin\\MultipleStock.vbs"; 
		try{
			Runtime.getRuntime().exec(cmd); 

		}catch(Exception e){
			System.out.println("Error"+e);
		}
		
	}

}
