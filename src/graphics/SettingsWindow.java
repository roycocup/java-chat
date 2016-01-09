package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import settings.Settings;

public class SettingsWindow extends JFrame implements ActionListener{
	private static final String title = "Settings";
	
	private static final JPanel mainPanel = new JPanel();
	private static final JPanel portPanel = new JPanel();
	private static final JPanel nicknamePanel = new JPanel();
	private static final JPanel buttonPanel = new JPanel();
	
	private static final JLabel nicknameLabel = new JLabel("Nickname:   ");
	private static final JTextField nicknameText = new JTextField(15);
	private static final JButton resetNicknameButton = new JButton();
	
	private static final JLabel serverPortLabel = new JLabel("Server port:");
	private static final JTextField serverPortText = new JTextField(15);
	private static final JButton resetPortButton = new JButton();
	
	private static final JButton cancelButton = new JButton("Cancel");
	private static final JButton saveButton = new JButton("Save");
	

	
	public SettingsWindow(){
		super(title);
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//content pane
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		//load icon
		ImageIcon resetIcon = new ImageIcon(MainWindow.class.getResource("icons/reset16.png"));
		
		//nikcnamePanel
		nicknamePanel.add(nicknameLabel);
		nicknameText.setText(Settings.getNickname());
		nicknamePanel.add(nicknameText);
		resetNicknameButton.setIcon(resetIcon);
		resetNicknameButton.setActionCommand("resetNickname");
		resetNicknameButton.addActionListener(this);
		nicknamePanel.add(resetNicknameButton);
		mainPanel.add(nicknamePanel);
		
		//portPanel
		portPanel.add(serverPortLabel);
		serverPortText.setText(Settings.getPort());
		portPanel.add(serverPortText);
		resetPortButton.setIcon(resetIcon);
		resetPortButton.setActionCommand("resetPort");
		resetPortButton.addActionListener(this);
		portPanel.add(resetPortButton);
		mainPanel.add(portPanel);
		
		//buttonPanel
		cancelButton.setActionCommand("cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		saveButton.setActionCommand("save");
		saveButton.addActionListener(this);
		buttonPanel.add(saveButton);
		mainPanel.add(buttonPanel);
		
		cp.add(mainPanel);
		
		this.pack();
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		
		if(command.equals("save")){
			Settings.setNickname(nicknameText.getText());
			nicknameText.setText(Settings.getNickname());
			Settings.setPort(serverPortText.getText());
			serverPortText.setText(Settings.getPort());
			this.setVisible(false);
		}else if (command.equals("cancel")){
			nicknameText.setText(Settings.getNickname());
			serverPortText.setText(Settings.getPort());
			this.setVisible(false);
		}else if (command.equals("resetPort")){
			serverPortText.setText(Settings.getDefaultPort());
		}else if (command.equals("resetNickname")){
			nicknameText.setText(Settings.getDefaultNickname());
		}else{
			
		}
		
	}

}
