package chatConnection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ConnectionClient implements Runnable{

	private static final int TIMEOUT_CONNECTION = 7000;
	private ConnectionDirector connectionDirector;
	private Socket socket;
	private String hostname;
	private int port;
		
	
	public ConnectionClient(ConnectionDirector connectionDirector, String hostname, int port){
		this.connectionDirector = connectionDirector;
		this.hostname = hostname;
		this.port=port;
		this.socket = new Socket();
	}


	@Override
	public void run(){
		try {
			socket.connect(new InetSocketAddress(hostname, port), TIMEOUT_CONNECTION);
			connectionDirector.connectionEstabilished(socket);
			
		} catch (UnknownHostException e){
			connectionDirector.unknownHost();
			e.printStackTrace();
		} catch (SocketTimeoutException e){
			connectionDirector.connectionTimedOut();
			e.printStackTrace();
			
		} catch (IOException e) {
			connectionDirector.connectToServerFailed();
			e.printStackTrace();	
		}
	}
	
	
}
