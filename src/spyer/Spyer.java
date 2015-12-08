package spyer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import chatEngine.ChatEngine;

public class Spyer implements ActionListener, KeyListener{

	//ActionListener
	@Override
	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		
		if(actionCommand == "info"){
			ChatEngine.openInfoWindow();
		}
		else if(actionCommand == "send"){
			ChatEngine.send();
		}
		else if(actionCommand == "connect"){
			ChatEngine.connectToServer();
		}
		else if(actionCommand == "connectMenu"){
			ChatEngine.openConnectWindow();
		}else if(actionCommand == "settingsMenu"){
			ChatEngine.openSettingsWindow();
		}
			
		
	}

	//KeyListener
	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
				
		if(keyCode == KeyEvent.VK_ENTER){
			ChatEngine.send();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
}