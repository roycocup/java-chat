 package chatEngine;

import java.io.IOException;
import javax.swing.SwingUtilities;

import settings.Settings;
import conversation.Conversation;
import chatConnection.ConnectionDirector;
import formatControl.formatControl;
import graphics.ConnectWindow;
import graphics.InfoWindow;
import graphics.MainWindow;

public class ChatEngine {
    
    //windows
    private static MainWindow mainWindow;
    private static ConnectWindow connectWindow;
    private static InfoWindow infoWindow;
    private static ConnectionDirector connectionDirector;
    //conversation
    private static Conversation conversation;
    //settings
    private static Settings settings;
    
    
    public ChatEngine(){
    	this.settings=new Settings();
        connectWindow = new ConnectWindow();
        conversation = new Conversation();
        infoWindow = new InfoWindow();
        mainWindow = new MainWindow();
        
        
        mainWindow.setVisible(true);
        
        connectionDirector = new ConnectionDirector();
        connectionDirector.checkPublicIp();
        connectionDirector.startServer();
    }
    
    
    
    //manage windows
    public synchronized static void openConnectWindow(){
        connectWindow.setVisible(true);
    }
    public synchronized static void closeConnectWindow(){
        connectWindow.setVisible(false);
    }
    public synchronized static void openInfoWindow(){
        infoWindow.setVisible(true);
    }
    public synchronized static void closeInfoWindow(){
        infoWindow.setVisible(false);
    }
    
    
    //connect to server
    public synchronized static void connectToServer(){
        String host = connectWindow.getHost();
        String port = connectWindow.getPort();
        connectWindow.setVisible(false);
        mainWindow.setConnectionStatus(MainWindow.CONNECTING);
        connectionDirector.connectToServer(host,port);
    }
    public synchronized static void connectToServerFailed(){
        mainWindow.setConnectionStatus(MainWindow.SERVER_RUNNING);
        post("Failed to create link", Conversation.SYSTEM);
    }
    public synchronized static void unknownHost(){
        mainWindow.setConnectionStatus(MainWindow.SERVER_RUNNING);
        post("Unknown host", Conversation.SYSTEM);
    }
	public static void connectionTimedOut() {
		mainWindow.setConnectionStatus(MainWindow.SERVER_RUNNING);
		post("Connection timed out", Conversation.SYSTEM);
		
	}
    public synchronized static void connectionEstabilished(String ip){
        mainWindow.setConnectionStatus(MainWindow.ONLINE);
        if(ip.equals("127.0.0.1"))
            post("you are chatting with yourself ("+ip+")", Conversation.SYSTEM);
        else
            post("Connected to "+ ip, Conversation.SYSTEM);
    }
    
    
    //server
    public synchronized static void serverStarted() {
        mainWindow.setConnectionStatus(MainWindow.SERVER_RUNNING);
        post("server started on port "+Settings.getPort(), Conversation.SYSTEM);
    }
    public synchronized static void serverFailedToStart() {
        post("server failed to start", Conversation.SYSTEM);
    }
    public synchronized static void setOfflineStatus(){
        mainWindow.setConnectionStatus(MainWindow.OFFLINE);
    }    
    public synchronized static void connectionBroken() {
        mainWindow.setConnectionStatus(MainWindow.OFFLINE);
        post("connection lost", Conversation.SYSTEM);
    }
    
    public synchronized static void postPublicIp(String ip){
    	post("your public ip: "+ip, Conversation.SYSTEM);
    }
    
    public static String getPort(){
    	return Settings.getPort();
    }
    
    
    // IO method
    public synchronized static void send(){
        String textToSend = mainWindow.read();
        if(formatControl.estCorrectMessageFormat(textToSend)){
            try {
                connectionDirector.sendMessage(textToSend);
                post(textToSend, Conversation.ME);
            } catch (IOException e) {
                post("no connected yet", Conversation.SYSTEM);
            }
        }
    }
    public synchronized static void incomingMessage(String message){
        post(message, Conversation.OTHER);
    }
    
    private synchronized static void post(String message, int who){
        conversation.post(message, who);
        //delegate work to UI thread
       	SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					mainWindow.setTextDisplayed(conversation.toString());
				}
			});
    }



	public synchronized static void incomingChatElements(String otherNickname) {
		settings.setOtherNickname(otherNickname);		
	}






}

