import java.lang.Runtime;
import java.io.*;  
import java.net.*; 
import java.awt.*; 
import javax.swing.*;
import java.util.Enumeration;
import java.util.*;
import java.lang.*;

public class ServerUtil{
	
	public String IPAddress;
	public int xRes, yRes;
	public ServerUtil(){
		createHotspot();
		try {
			getIpAddress();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getResolution();
		getGui();
		
	}
	private void getIpAddress() throws SocketException{
		String s = "<html>";
		CharSequence wifi = "Wi-Fi";
		CharSequence hotspot = "Hosted"; 
		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
		for (NetworkInterface i : Collections.list(interfaces)) {
			if (i.isUp()) {
				String p = i.getDisplayName();
				Enumeration<InetAddress> ip;
				if(p.contains(wifi)){
				s = s + "Wifi" +"<br>";    
				//s = s + i.getDisplayName() +"<br>";  
				 ip = i.getInetAddresses();
					for (InetAddress ia: Collections.list(ip)) {
						if (ia instanceof Inet6Address)
							continue;
						s = s + ia.getHostAddress() + "<br>";
					}
				}
				if(p.contains(hotspot)){
				s = s + "Hotspot" +"<br>";            
			    ip = i.getInetAddresses();
					for (InetAddress ia: Collections.list(ip)) {
						if (ia instanceof Inet6Address)
							continue;
						s = s + ia.getHostAddress() + "<br>";
					}
				}
					 
			}
     
		} 
		s = s + "</html>";
		
		IPAddress = s;
		
		
    
    }
	
	private void getResolution(){
			Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
			xRes = (int)size.getWidth();
			 yRes = (int)size.getHeight();
	
	}
	
	public void getGui(){
		JFrame f = new JFrame("server");
		f.setVisible(true);
		f.setSize(1000,400);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p = new JPanel();
		
		Font font = new Font("TimesRoman", Font.PLAIN, 36);
		
		JLabel iplabel = new JLabel("Ip-Address: ");
		iplabel.setFont(font);	
		p.add(iplabel);
		
		JLabel ip_value = new JLabel("",JLabel.CENTER);
		ip_value.setText(IPAddress);
		ip_value.setFont(font);
		p.add(ip_value);
		
		f.add(p);
		
	}
	public void createHotspot(){
		
		String path="cmd /c start /res/ip.bat";
		Runtime r = Runtime.getRuntime();
		
		try{
		 r.exec(path);
		
		}catch (IOException e){
			System.out.println("sorry");
			System.exit(-1);
		}
		
	}
	
	
}

		