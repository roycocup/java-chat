package chatConnection;

import java.io.IOException;
import java.net.Socket;

import settings.Settings;

import chatEngine.ChatEngine;

public class ConnectionDirector 
{
	private ConnectionServer connectionServer;
	private VirtualChat vchat;
	
	//server
	public void startServer()
	{
		int portInt = Integer.parseInt(ChatEngine.getPort());
		connectionServer = new ConnectionServer(this, portInt);
		Thread serverThread = new Thread(connectionServer);
		serverThread.start();
	}
	public void checkPublicIp(){
		Thread discoverIpThread = new Thread(new DicoverPublicIp());
		discoverIpThread.start();
	}
	
	public void serverStarted()
	{
		ChatEngine.serverStarted();
	}
	public void serverStopped(){
		if(vchat != null && !vchat.isOnline()){
			ChatEngine.setOfflineStatus();
		}
	}
	public void serverFailedToStart(){
		ChatEngine.serverFailedToStart();
	}
	
	
	//connect to server
	public void connectToServer(String hostname, String port)
	{
		if(vchat != null && vchat.isOnline()){
			vchat.close();
		}
		int portInt = Integer.parseInt(port);
		ConnectionClient connectionClient = new ConnectionClient(this, hostname, portInt);
		Thread clientThread = new Thread(connectionClient);
		clientThread.start();
		
	}
	public void unknownHost(){
		ChatEngine.unknownHost();
	}
	public void connectToServerFailed() {
		ChatEngine.connectToServerFailed();	
	}
	public void connectionTimedOut() {
		ChatEngine.connectionTimedOut();
	}
	
	
	public void  connectionEstabilished(Socket socket)
	{
		connectionServer.close();
		ChatEngine.connectionEstabilished(socket.getInetAddress().getHostAddress());
		vchat = new VirtualChat(this, socket);
		Thread chatThread = new Thread(vchat);
		chatThread.start();
	}
	
	public void connectionBroken(){
		ChatEngine.connectionBroken();
		startServer();
		
	}
	
		
	// in/out message
	public void sendMessage(String mex) throws IOException{
		if(vchat == null)
			throw new IOException("connection not estabilished yet");
		else
			vchat.sendMessage(mex);
	}
	
	public void incomingMessage(String mex){
		ChatEngine.incomingMessage(mex);
	}
	
	public void incomingChatElements(ChatElements chatElements){
		ChatEngine.incomingChatElements(chatElements.getNickname());
	}
	


	
}
