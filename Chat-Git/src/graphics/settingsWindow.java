package graphics;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class settingsWindow extends JFrame{
	private static final String title = "Settings";
	private static final JLabel nicknameLabel = new JLabel("Nickname:");
	private static final JTextField nickname = new JTextField(15);
	private static final JLabel serverPortLabel = new JLabel("Server port:");
	private static final JTextField serverPort = new JTextField(6);
	private static final JButton save = new JButton("Save");

	
	public settingsWindow(){
		super(title);
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//content pane
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		this.pack();
	}

}
