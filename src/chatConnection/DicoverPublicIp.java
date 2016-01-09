package chatConnection;

import chatEngine.ChatEngine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DicoverPublicIp implements Runnable{
	
	private static final String server = "http://checkip.amazonaws.com/";

	@Override
	public void run() {
		URL serverUrl;
		String myip = "";
		try {
			serverUrl = new URL(server);
			BufferedReader in = new BufferedReader(new InputStreamReader(serverUrl.openStream()));
			String myIp = in.readLine();
			ConnectionDirector.setSourceIp(myIp);
			// We can only post when we have it, thus its from inside this function thread.
			// TODO: Should perhaps be a second call after this function has returned and sync.
			ChatEngine.postPublicIp(myIp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}
	
}
