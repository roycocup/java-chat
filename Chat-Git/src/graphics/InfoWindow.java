package graphics;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class InfoWindow extends JFrame implements MouseListener{
	
	private static final String title = "info";
	private static final JLabel linkLabel = new JLabel();
	private static final JLabel textArea = new JLabel(); 
	private static final String projectWebPage = "http://code.google.com/p/java-chat/";
	
	public InfoWindow(){
		super(title);
		this.setResizable(false);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		//content pane
		Container cp = this.getContentPane();
		cp.setLayout(new BorderLayout());
		
		//textArea
		textArea.setText("Credits:   Uncleman11  &  Ael");
		textArea.setBackground(cp.getBackground());
		textArea.setHorizontalAlignment(SwingConstants.CENTER);
		textArea.setBorder(BorderFactory.createEmptyBorder(30, 30, 20, 30));
		cp.add(textArea, BorderLayout.CENTER);
		
		//linkButton
		linkLabel.setText("<HTML><FONT color=\"#000099\"><U>Project WebPage</U></FONT></HTML>");
		linkLabel.addMouseListener(this);
		linkLabel.setHorizontalAlignment(SwingConstants.CENTER);
		linkLabel.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
		cp.add(linkLabel, BorderLayout.SOUTH);
		
		this.pack();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		try {
			URI linkWebPage = new URI(projectWebPage);
			Desktop.getDesktop().browse(linkWebPage);
		} catch (URISyntaxException e1) {
			System.out.println("link error in infoWindow");
			e1.printStackTrace();
		} catch (IOException e1) {
			System.out.println("open link error in infoWindow");
			e1.printStackTrace();
		}	
	}

	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
}
