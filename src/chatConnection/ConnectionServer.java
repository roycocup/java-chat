package chatConnection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *A thread. When executed it waits for incoming connections. When a connection is received, he opens a client connection to the sender at the same port.
 *Then, he opens a new VirtualChat (instance variable) associated with the connection.
 * 
 * @author uncleman11
 *
 */

public class ConnectionServer implements Runnable {
	
	
	private ConnectionDirector connectionDirector;
	private ServerSocket serverSocket;
	private Socket transmission;
	private int port;
	
	
	public ConnectionServer(ConnectionDirector connectionDirector, int port){
		this.connectionDirector = connectionDirector;
		this.port=port;
	}
		
	public void run()
	{
		try {
			this.serverSocket = new ServerSocket(this.port);
			connectionDirector.serverStarted();
		} catch(IOException ioe) {
			//server failed to start
			connectionDirector.serverFailedToStart();
			ioe.printStackTrace();
		}

		try {
			this.transmission = serverSocket.accept();
			connectionDirector.connectionEstabilished(transmission);
		} catch (Exception e) {
			connectionDirector.serverStopped();
			e.printStackTrace();
		}
	}

	public void close() {
			try {
				serverSocket.close();
			} catch (IOException e) {
				//failed to close server
				System.out.println("failed to close server");
				e.printStackTrace();
			}
	}
	public boolean isOnline(){
		return !serverSocket.isClosed();
	}

}
