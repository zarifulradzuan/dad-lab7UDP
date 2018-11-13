import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPReceiver {
	public static void main(String [] args) throws Exception{
		DatagramSocket varDS = new DatagramSocket(8080);
		byte arrBuffer[]= new byte[10000];
		
		DatagramPacket varDPfromSender = new DatagramPacket(arrBuffer, arrBuffer.length);
		
		System.out.println("Standing by receiving data...");
		
		varDS.receive(varDPfromSender);
		
		arrBuffer = varDPfromSender.getData();
		
		String strWord = new String(arrBuffer);
		
		System.out.println(strWord);
		
		InetAddress senderAddresss = varDPfromSender.getAddress();
		int senderPort = varDPfromSender.getPort();
		byte arrBuffer2[] = ("This is response from listener/receiver...").getBytes();
		
		DatagramPacket varDPtoSend = new DatagramPacket(arrBuffer2, arrBuffer2.length, senderAddresss, senderPort);
		varDS.send(varDPtoSend);
		varDS.close();
	}

}
