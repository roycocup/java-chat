package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import chatEngine.ChatEngine;

import spyer.Spyer;

public class ConnectWindow extends JFrame implements KeyListener, ChangeListener{
	
	private static final String title = "Connect";
	private static final JPanel northPanel = new JPanel();
	private static final JPanel centerPanel = new JPanel();
	private static final JPanel rightPanel = new JPanel();
	private static final JPanel mainPanel = new JPanel(new BorderLayout());
	private static final JLabel hostNameLabel = new JLabel("Hostname:");
	private static final JTextField hostName = new JTextField(15);
	private static final JCheckBox checkBox = new JCheckBox("default port ");
	private static final JTextField hostPort = new JTextField(4);
	private static final JButton connectButton = new JButton("Connect");
	private static final ActionListener actionSpyer = new Spyer();
	
	public ConnectWindow(){
		super(title);
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//content pane
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());

		hostName.addKeyListener(this);
		northPanel.add(hostNameLabel);
		northPanel.add(hostName);
		checkBox.addChangeListener(this);
		checkBox.setSelected(true);
		centerPanel.add(checkBox);
		centerPanel.add(hostPort);
		mainPanel.add(northPanel, BorderLayout.NORTH);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		connectButton.setActionCommand("connect");
		connectButton.addActionListener(actionSpyer);
		cp.add(mainPanel, BorderLayout.CENTER);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.add(Box.createVerticalGlue());
		rightPanel.add(connectButton);
		rightPanel.add(Box.createVerticalGlue());
		cp.add(rightPanel,BorderLayout.EAST );
		
		;		
		this.pack();
	}
	
	/**Take String in the host field
	 * @return
	 */
	public String getHost(){
		return hostName.getText(); 
	}
	/**Take String in the port field
	 * @return
	 */
	public String getPort(){
		return hostPort.getText();
	}
	
	
	
	

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		
		if(keyCode == KeyEvent.VK_ENTER){
			ChatEngine.connectToServer();
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}

	@Override
	public void stateChanged(ChangeEvent e) {
		if(checkBox.isSelected()){
			hostPort.setEditable(false);
			hostPort.setText(ChatEngine.getPort());
			hostPort.setBackground(new Color(199,199,199));
		}else{
			hostPort.setEditable(true);
			hostPort.setBackground(Color.WHITE);
		}
		
	}
}