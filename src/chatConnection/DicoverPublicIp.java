package chatConnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import chatEngine.ChatEngine;

public class DicoverPublicIp implements Runnable{
	
	private static final String server = "http://checkip.amazonaws.com/";

	@Override
	public void run() {
		URL serverUrl;
		try {
			serverUrl = new URL(server);
			BufferedReader in = new BufferedReader(new InputStreamReader(serverUrl.openStream()));
			String myIp = in.readLine();
			ChatEngine.postPublicIp(myIp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
}
