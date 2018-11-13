import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

public class UDPSender {
	public static void main(String []args) throws Exception{
		byte varBuffer[] = new byte[10000];
		String strWord = "This data from sender..";
		
		InetAddress varAdd = InetAddress.getByName("127.0.0.1");
		
		varBuffer = strWord.getBytes();
		
		DatagramPacket varDP = new DatagramPacket(varBuffer, strWord.getBytes().length, varAdd, 8080);
		
		DatagramSocket varDS = new DatagramSocket();
		varDS.send(varDP);
		
		System.out.println("Data Send!");
		
		byte arrBuffer2[] = new byte[10000];
		
		DatagramPacket varDP2 = new DatagramPacket(arrBuffer2, arrBuffer2.length);
		varDS.receive(varDP2);
		arrBuffer2 = varDP2.getData();
		
		JOptionPane.showMessageDialog(null, new String(arrBuffer2));
		
		varDS.close();
	}
}
