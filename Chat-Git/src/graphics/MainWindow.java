package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.text.DefaultCaret;

import spyer.Spyer;


public class MainWindow extends JFrame {

	//window data
	private static final String title = "Chat";
	private static final Dimension minimumSize = new Dimension(150, 200);
	private static final Dimension preferredSize = new Dimension(400, 500);
	//main text area
	private static final JEditorPane textArea = new JEditorPane();
	private static final JPanel centerPanel = new JPanel(new BorderLayout());
	//bottomPanel (write and send)
	private static final JPanel spaced = new JPanel();
	private static final JTextField writeArea = new JTextField();
	private static final JScrollPane scrollPane = new JScrollPane();
	private static final JButton sendButton = new JButton("Send");
	private static final JPanel bottomPanel = new JPanel(new BorderLayout());
	//menu Area
	private static final JMenuItem connect = new JMenuItem("Connect");
	private static final JMenuItem settings = new JMenuItem("Settings");
	private static final JMenu menu = new JMenu("Menu");
	private static final JMenuItem about = new JMenuItem("About");
	private static final JMenu info = new JMenu("Info");
	private static final JLabel connectionStatus = new JLabel("Status:");
	private static final JLabel statusIconLabel = new JLabel();
	private static final JMenuBar menuBar = new JMenuBar();
	//Listener
	private static final ActionListener actionSpyer= new Spyer();
	private static final KeyListener keySpyer= (KeyListener) actionSpyer;
	//status
	public static final int ONLINE = 1;
	public static final int OFFLINE = 0;
	public static final int NONE = 2;
	public static final int SERVER_RUNNING = 3;
	public static final int CONNECTING = 4;
	

	public MainWindow(){
		super(title);
		this.setMinimumSize(minimumSize);;
		this.setPreferredSize(preferredSize);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(true);
		
		//content pane
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		//menu bar
			//menu
			connect.setActionCommand("connectMenu");
			connect.addActionListener(actionSpyer);
			menu.add(connect);
			settings.setActionCommand("settingsMenu");
			settings.addActionListener(actionSpyer);
			menu.add(settings);
			menuBar.add(menu);
			//info
			about.setActionCommand("info");
			about.addActionListener(actionSpyer);
			info.add(about);
			menuBar.add(info);
			//connection Status
				menuBar.add(Box.createHorizontalGlue());
				menuBar.add(connectionStatus);
				//icon
				setConnectionStatus(OFFLINE);
				menuBar.add(statusIconLabel);			
			
		this.setJMenuBar(menuBar);
		
		
		//center Panel
		textArea.setEditable(false);
		textArea.setContentType("text/html");
		scrollPane.setViewportView(textArea);
		scrollPane.setBorder(BorderFactory.createEtchedBorder());
		DefaultCaret caret = (DefaultCaret) textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		centerPanel.add(scrollPane, BorderLayout.CENTER);
		cp.add(centerPanel, BorderLayout.CENTER);
		
		//bottom Panel
		writeArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
		writeArea.addKeyListener(keySpyer);
		bottomPanel.add(spaced, BorderLayout.NORTH);
		bottomPanel.add(writeArea, BorderLayout.CENTER);
		
		sendButton.setActionCommand("send");
		sendButton.addActionListener(actionSpyer);
		bottomPanel.add(sendButton, BorderLayout.EAST);
		cp.add(bottomPanel, BorderLayout.SOUTH);
		
		
		this.pack();
	}
	
	
	/**
	 * post text in the text Area
	 * @param s string to post
	 */
	public void setTextDisplayed(String text){
		textArea.setText(text);
	}
	
	/**
	 * read text from the write area
	 * @return string from the write area
	 */
	public String read(){
		String result = writeArea.getText();
		writeArea.setText("");
		return result;		
	}
	
	/**Set connection status on the menuBar.
	 * Constant for icon selection are ONLINE,OFFLINE,AWAY,NONE.
	 * @param text text to display
	 * @param constant MainWindow constant that rapresent possible icons
	 */
	public void setConnectionStatus(int constant){
		String iconName;
		String text;
		if(constant == ONLINE){
			iconName = "online.png";
			text = "Status: connect ";
		}else if(constant == OFFLINE){
			iconName = "offline.png";
			text = "Status: offline ";
		}else if(constant == NONE){
			iconName = "none.png";
			text = "Status: error ";
		}else if(constant == SERVER_RUNNING){
			iconName = "away.png";
			text = "Status: server running";
		}else if(constant == CONNECTING){
			iconName = "loading.gif";
			text = "Status: connecting ";
		}else{
			//lanciare eccezione se non e' una costante
			return;
		}
		URL statusIconURL = MainWindow.class.getResource("icons/"+iconName);
		statusIconLabel.setIcon(new ImageIcon(statusIconURL));
		connectionStatus.setText(text);
	}
	
}