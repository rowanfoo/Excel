package util;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import access.DataAccess;
public class MovingAverage {
   
	private final Queue<Double> window = new LinkedList<Double>();
    private final int period;
    private double sum;
    ArrayList<DataAccess> data;
    public  ArrayList <DataAccess > arr;
    
    

    public MovingAverage(int period ,   ArrayList<DataAccess> data) {
        assert period > 0 : "Period must be a positive integer";
        this.period = period;
        arr= new ArrayList<DataAccess>();
        this.data=data;
        System.out.println("MovingAverage "+period );
    }

    public void newNum(DataAccess dt) {
    //	System.out.println("here:0"  );
    //	System.out.println("here:"+dt.getClose()  );

    	double num = 0;

    	if(period==60){
    		num = dt.getVolume();
    	}else{
    		num = dt.getClose();
    	}
    	
    	
    	sum += num;
        window.add(num);
        if (window.size() > period) {
            sum -= window.remove();
        }
        if(window.size()==period){
        	
           // arr.add((sum / window.size())+"");
            if(period==50){
            //	System.out.println("Error here:"+dt.getDate()+"::"  );
            	
            	//dt.setMyAvg(FormatUtil.formatDouble(sum / window.size(), 2)   );
            //	System.out.println("Error ok :");
            	dt.setFifty( FormatUtil.formatDouble(sum / window.size(), 2)  );
            	
            	
            }else if(period==200){
                //	System.out.println("Error here:"+dt.getDate()+"::"  );
                	
                	//dt.setMyAvg(FormatUtil.formatDouble(sum / window.size(), 2)   );
                //	System.out.println("Error ok :");
                	dt.setTwohundred( FormatUtil.formatDouble(sum / window.size(), 2)  );
                	
                	
            }else if(period==60){
            	dt.setAvg3mth( FormatUtil.formatDouble(sum / window.size(), 2)  );
            	
            	
            }else if(period==75){
            	dt.setSevenfive(FormatUtil.formatDouble(sum / window.size(), 2)  );
            }else if(period==20){
            	
            	dt.setTwenty(FormatUtil.formatDouble(sum / window.size(), 2)  );
            //	System.out.println("DataDAO SQL "+dt.getDate() + "::avg "+dt.getTwenty());
            }else if(period==40){
            	
            	dt.setFourty(FormatUtil.formatDouble(sum / window.size(), 2)  );
            //	System.out.println("DataDAO SQL "+dt.getDate() + "::avg "+dt.getTwenty());
            }else if(period==150){
            	
            	dt.setOnehundredfifty(FormatUtil.formatDouble(sum / window.size(), 2)  );
            //	System.out.println("DataDAO SQL "+dt.getDate() + "::avg "+dt.getTwenty());
            }else if(period==400){
            	
            	dt.setOnehundredfifty(FormatUtil.formatDouble(sum / window.size(), 2)  );
            	dt.setFourhundred(FormatUtil.formatDouble(sum / window.size(), 2)   );
            //	System.out.println("DataDAO SQL "+dt.getDate() + "::avg "+dt.getTwenty());
            }
            
            
            
            
            
        }
    }
    public void run(){
    	for (DataAccess x : data) {
                 newNum(x);
                
        }
    }

    public double getAvg() {
        if (window.isEmpty()) return 0; // technically the average is undefined
        return sum / window.size();
    }

    public static void main(String[] args) {
        double[] testData = {1,2,3,4,5,5,4,3,2,1};
        int[] windowSizes = {3,5};
    
   //     for (int windSize : windowSizes) {
         //   MovingAverage ma = new MovingAverage(windSize);
        
        //    for (double x : testData) {
          //      ma.newNum(x);
                //System.out.println("Next number = " + x + ", SMA = " + ma.getAvg());
        //    }
            
        //    System.out.println("Arr:"+ma.arr);
       // }
    }
}