package cube_cheat;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JTextField;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class cheat {
	static boolean done = false;
	public cheat(){
		JFrame frame = new JFrame("cube_cheat");
		JMenuBar mb = new JMenuBar();
		frame.setJMenuBar(mb);
		
		
		final JTextField search = new JTextField(getSearchedPhrase());
		JLabel sF = new JLabel("Szukana fraza");
		final JTextField open = new JTextField(getMusic());
		JLabel oF = new JLabel("Otwórz kiedy znajdziesz");
		JLabel searchedSite = new JLabel("Strona");
		final JTextField searchedSite1 = new JTextField(getSite());
		
		
		JButton save = new JButton("Save");
		JButton start = new JButton("Start");
		JButton stop = new JButton("Stop");
		JButton gunwo = new JButton("LEL");
		
		
		frame.add(sF);
		frame.add(search);
		frame.add(oF);
		frame.add(open);
		frame.add(searchedSite);
		frame.add(searchedSite1);
		frame.add(save);
		frame.add(start);
		frame.add(stop);
		frame.add(gunwo);
		
		
		frame.setLayout(new GridLayout(5,5));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(640,400);
		frame.setVisible(true);
		
		ActionListener saVe= new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("\nProba zapisu");
				saveDetails(search.getText(),open.getText(),searchedSite1.getText());
				
			}
			
		};
		
		ActionListener run = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				done = false;
				
				Thread t = new Thread(new Runnable(){

					@Override
					public void run() {
						try {
							get();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						while(done!=true){
							try {
								find_price(get(),search.getText());
							} catch (InterruptedException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
							
						}
						
					}
					
				});
				t.start();
			}
		};
		
		ActionListener st0p = new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				done = true;
			}
		};
		save.addActionListener(saVe);
		start.addActionListener(run);
		stop.addActionListener(st0p);
	}
	static Preferences preferences = Preferences
			.userNodeForPackage(cheat.class);
	
	
	
	public void saveDetails(String phrase, String link,String muza){
		preferences.put("key",phrase);
		preferences.put("link",link);
		preferences.put("muza", muza);
		
		System.out.println("Dane zapisane pomyślnie");
	}
	
	
	
	public static String get() throws InterruptedException{
		String url = getSite();
		Document document = null;
		try {
			document = Jsoup.connect(url).get();
			Thread.sleep(5000);
			System.out.println("wait 5 sec");
		} catch (IOException e) {
			e.printStackTrace();
		}

		String question = document.select("body").text();

		
		return question;
	
	}
	public static void openWebpage(String urlString){
		try{
			Desktop.getDesktop().browse(new URL(urlString).toURI());
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String getSearchedPhrase(){
		return preferences.get("key",null);
		
	}
	public static String getSite(){
		return preferences.get("link",null);
		
	}
	public static String getMusic(){
		return preferences.get("muza",null);
		
	}
	
	public static void find_price(String text, String price){
		if(text.contains(getSearchedPhrase())){
			System.out.println("znaleziono cenę "+price);
			openWebpage(getSite());
			openWebpage(getMusic());
			done = true;
		}
		else{
			System.out.println("Dupga nie znaleziono ceny");
		}
		
	}
	
}
