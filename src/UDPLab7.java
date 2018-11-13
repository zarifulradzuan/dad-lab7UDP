import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class UDPLab7 {

	private JFrame frame;
	private JTextField txtPartnerIP;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UDPLab7 window = new UDPLab7();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public UDPLab7() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 342);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblEnterYourText = new JLabel("Enter your text:");
		lblEnterYourText.setBounds(10, 27, 140, 14);
		frame.getContentPane().add(lblEnterYourText);
		
		JTextArea txtrSendtextarea = new JTextArea();
		txtrSendtextarea.setBounds(10, 52, 307, 92);
		frame.getContentPane().add(txtrSendtextarea);
		
		JLabel lblResponseFromPartner = new JLabel("Response from partner:");
		lblResponseFromPartner.setBounds(10, 155, 140, 14);
		frame.getContentPane().add(lblResponseFromPartner);
		
		JTextArea txtrResponsetextarea = new JTextArea();
		txtrResponsetextarea.setBounds(10, 180, 307, 92);
		frame.getContentPane().add(txtrResponsetextarea);
		
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Thread sendThread = new Thread() {
					public void run() {
						byte varBuffer[] = new byte[10000];
						String strWord = txtrSendtextarea.getText();
						
						InetAddress varAdd = null;
						try {
							varAdd = InetAddress.getByName(txtPartnerIP.getText());
						} catch (UnknownHostException e) {
							JOptionPane.showMessageDialog(null, "Invalid address!");
							e.printStackTrace();
						}
						
						varBuffer = strWord.getBytes();
						
						DatagramPacket varDP = new DatagramPacket(varBuffer, strWord.getBytes().length, varAdd, 8080);
						
						DatagramSocket varDS = null;
						try {
							varDS = new DatagramSocket();
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							varDS.send(varDP);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						JOptionPane.showMessageDialog(null, "Data Sent!");
						
						varDS.close();
					}
				};
				sendThread.start();
			}
		});
		btnSend.setBounds(338, 53, 89, 23);
		frame.getContentPane().add(btnSend);
		
		txtPartnerIP = new JTextField();
		txtPartnerIP.setBounds(338, 24, 86, 20);
		frame.getContentPane().add(txtPartnerIP);
		txtPartnerIP.setColumns(10);
		
		JLabel lblPartnerAddress = new JLabel("Partner address:");
		lblPartnerAddress.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPartnerAddress.setBounds(207, 27, 126, 14);
		frame.getContentPane().add(lblPartnerAddress);
		
		Thread receiveThread = new Thread() {
			public void run() {
					txtrResponsetextarea.setText("Standing by receiving data...");
					while(true) {
						DatagramSocket varDS = null;
						try {
							varDS = new DatagramSocket(8080);
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						byte arrBuffer[]= new byte[10000];
						
						DatagramPacket varDPfromSender = new DatagramPacket(arrBuffer, arrBuffer.length);
						
						
						
						try {
							varDS.receive(varDPfromSender);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						arrBuffer = varDPfromSender.getData();
						String strWord = new String(arrBuffer);
						txtrResponsetextarea.setText(strWord);
						varDS.close();
					}
				}
		};
		receiveThread.start();
	}
}
