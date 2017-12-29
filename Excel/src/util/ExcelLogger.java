package util;

import java.util.*;
import java.util.logging.*;



public class ExcelLogger {
	static Logger logger = null;
	
	
	public static Logger getLogger(){
		if (logger == null ){
			try {
				
				logger = Logger.getLogger("ExcelLoggers");
				
				Handler fh = new FileHandler(ExcelConfig.info , true);  // append is true
				fh.setFormatter(new SimpleFormatter());
				
				fh.setLevel(Level.INFO);
				 logger.addHandler(fh);
				Handler fh1 = new FileHandler(ExcelConfig.severe , true);  // append is true
				 fh1.setFormatter(new SimpleFormatter());
				 fh1.setLevel(Level.SEVERE);
				
					logger.addHandler(fh1);
				
				
				
				
				
				
				
			} catch (Exception e) {
				System.out.println("error log:"+e);
			}
			
			
			
		
			 
			 logger.setLevel(Level.INFO);
		}
		return logger;
		
	}
	
	public static void close(){
		Handler fh = logger.getHandlers()[0];
		fh.flush();
		fh.close();
	}
	
}
