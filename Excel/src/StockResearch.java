import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

import access.QuotesAccess;
import access.StockAccess;
import access.WorkSheetAccess;
import dao.QuotesDAO;
import dao.WorkSheetDAO;
import factory.DAOFactoryStock;

public class StockResearch {

	private JFrame frame;
	private JTextField textField;
	ScheduledExecutorService executor ;
	ScheduledFuture scheduledFuture ;
	Connection con;
	QuotesDAO quo ;
	TextToSpeech tts;
	private JTextField textField_1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockResearch window = new StockResearch();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "ERROR !!!!! ", "InfoBox: " + "Start   ERROR :"+e, JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
	}


	/**
	 * Create the application.
	 */
	public StockResearch() throws Exception {
		initialize();
		setSechdule(20 );
		Class.forName("com.mysql.jdbc.Driver");
		con=DriverManager.getConnection("jdbc:mysql://rowanfoo.ddns.net:3306/fortune?autoReconnect=true&useSSL=false","rowanf","rowm0ng1");
		
		// "jdbc:mysql://localhost:3306/fortune?autoReconnect=true&useSSL=false","root","rowm0ng1");
		
		quo = new QuotesDAO(con);
		 tts = new TextToSpeech();
			tts.setVoice("dfki-poppy-hsmm");

			tts.setVoice("cmu-slt-hsmm");
			
		
			
		
		
		
	}
	
	private void setSechdule(int time ){
		executor = Executors.newScheduledThreadPool(2);
		scheduledFuture = executor.scheduleAtFixedRate (new Action(),
			    0,time, 
			    TimeUnit.MINUTES);	
		
		executor.schedule(new Runnable() {
		    public void run() {
		    	scheduledFuture.cancel(true);
		    	executor.shutdown();
		    }
		},time+1, TimeUnit.MINUTES);
	}
	
	
	private ArrayList <String>  getStockCodes(){
		 try(DAOFactoryStock dao = new DAOFactoryStock()) {
			 Hashtable <String ,StockAccess> table =  dao.getAllHash();
			  return new ArrayList<String> (  table.keySet());
			 
			 
				} catch (Exception e) {	
					System.out.println("StockJSP getStockCodes ERROR :"+e); 
				}
		 return null;
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		JTextPane textPane = new JTextPane();
		JTextPane textPane_1 = new JTextPane(); 
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				super.windowClosed(arg0);
				 System.out.println("someone close me :");
				
				
			
			}
			@Override
			public void windowDeactivated(WindowEvent e) {
				System.out.println("someone deactiveate me :"+e);
			}
		
		public void windowClosing(WindowEvent env){
			super.windowClosing(env);
            System.out.println( "closing" );
            try {
				 con.close();
			} catch (Exception e) {
				System.out.println("someoneclose me :"+e);
			}
            
		}
		
		
		});
		frame.setBounds(100, 100, 936, 799);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblCode = new JLabel("Code");
		lblCode.setBounds(10, 11, 46, 14);
		frame.getContentPane().add(lblCode);
		
		textField = new JTextField();
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				 System.out.println("someone prese me :"); 	
				 textPane.requestFocus();
			}
		});
		textField.setBounds(66, 8, 198, 20);
		frame.getContentPane().add(textField);
	
		
		textField.setColumns(10);
		
		
		 ArrayList <String> code= getStockCodes();
		
		 System.out.println("codes:"+code.size()); 	
		 System.out.println("codes:"+code ); 	
		
		 AutoCompleteDecorator.decorate(textField, code, false);
		 
		 
		
		 
		 
		
		JLabel lblReason = new JLabel("Reason");
		lblReason.setBounds(10, 58, 46, 14);
		frame.getContentPane().add(lblReason);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(66, 52, 390, 230);
		frame.getContentPane().add(scrollPane_1);
		
		scrollPane_1.setViewportView(textPane);
	
		
		
		
		
		JLabel lblYes = new JLabel("Yes");
		lblYes.setBounds(10, 321, 46, 14);
		frame.getContentPane().add(lblYes);
		
		JButton btnStart = new JButton("Commit");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			// start 
				try{
					WorkSheetDAO dao= new WorkSheetDAO(con);
					WorkSheetAccess obj = new WorkSheetAccess();
					obj.setReason(textPane.getText() );
					obj.setYes(textPane_1.getText() );
					obj.setDate(new Date());
					obj.setCode(textField.getText());
				 	 dao.createWorkSheet( obj);
					
					
				
					
				}catch(Exception e){
					System.out.println("SAVE   ERROR :"+e); 
					JOptionPane.showMessageDialog(null, "ERROR !!!!! ", "InfoBox: " + "SAVE   ERROR :"+e, JOptionPane.INFORMATION_MESSAGE);
				}
			
			}
		});
		btnStart.setBounds(10, 637, 89, 23);
		frame.getContentPane().add(btnStart);
		
		JButton btnMinsMore = new JButton("10 mins More");
		btnMinsMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			//10  mins more
				setSechdule(10 );
			}
		});
		btnMinsMore.setBounds(109, 637, 89, 23);
		frame.getContentPane().add(btnMinsMore);
		
		JButton btnMinMore = new JButton("20 min more");
		btnMinMore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//20 mins more.
				setSechdule(20 );
			}
		});
		btnMinMore.setBounds(208, 637, 89, 23);
		frame.getContentPane().add(btnMinMore);
		
		JScrollBar scrollBar_1 = new JScrollBar();
		scrollBar_1.setBounds(439, 207, 17, 48);
		frame.getContentPane().add(scrollBar_1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(66, 321, 390, 251);
		frame.getContentPane().add(scrollPane);
		 
		scrollPane.setViewportView(textPane_1);
		
		
	}

	 class Action implements Runnable{
			public void run() {	
				 playMusic();
			}
	 
			public void playMusic() {
				final File dir = new File("D:/Java/Project/TTS/bin/music");
				int size = 10;
				File[] files = dir.listFiles();
				int idx = (int) (Math.random() * files.length);

				try {
					Clip clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(files[idx]));
					clip.start();
					Thread.sleep(clip.getMicrosecondLength() / 1000);

					System.out.println("finish");
					QuotesAccess quote = quo.getRandomQuotes();
					tts.speak(" Quote for the day  ", 0.8f, false, true);
					tts.speak("Weekly code target:    "+quote.getNote() , 0.8f, false, false);
					System.out.println("ERROR getQuotes  report:"+quote.getNote() );
					
				} catch (Exception e) {
					System.out.println("playMusic:" + e);
				}

			}
	 
	 }
}
