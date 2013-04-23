package chatConnection;
import java.io.*;
import java.net.Socket;

import settings.Settings;

public class VirtualChat implements Runnable
{
       
		private Socket socket;
        private ConnectionDirector connectionDirector;
        private OutputStream output;
        private InputStream input;
              
        public VirtualChat(ConnectionDirector connectionDirector, Socket socket)
        {
        	this.socket=socket;
        	this.connectionDirector = connectionDirector;
        	try {
        		this.input=socket.getInputStream();
        		this.output=socket.getOutputStream();
			} catch (IOException e) {
				//if socket has been closed in the mean-time
				this.connectionDirector.connectionBroken();
				e.printStackTrace();
			}
        	
        }
        
        @Override
		public void run() 
        {
        	
			sendChatElements();
        	while(!socket.isClosed())
        	{
        		try {
        			ObjectInputStream objectInput = new ObjectInputStream(input);
					read(objectInput.readObject());
				} catch (IOException e) {
					//il close e' stato chiamato mentre era nel ciclo;
					if(!socket.isClosed())
						connectionDirector.connectionBroken();
					close();
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
        		
    			
    		
        	}

		}
        
        public void sendMessage(String message) throws IOException
        {
        	ObjectOutputStream objectOutput = new ObjectOutputStream(output);
			objectOutput.writeObject(message);
        }
        
        private void sendChatElements() 
        {
        	ChatElements chatElements = new ChatElements();
    		chatElements.setNickname(Settings.getNickname());
        	try
        	{
        		ObjectOutputStream objectOutput = new ObjectOutputStream(output);
        		objectOutput.writeObject(chatElements);
        	}
        	catch(IOException e)
        	{
        		connectionDirector.connectionBroken();
				close();
        		e.printStackTrace();
        	}
        }
		
        private void read(Object object)
        {
        	if(object.getClass().equals(ChatElements.class))
        	{
        		ChatElements chatElements = (ChatElements) object;
        		connectionDirector.incomingChatElements(chatElements);
        	}
        	
        	
        	else if(object.getClass().equals(String.class))
        	{
        		String message = (String) object;
        		connectionDirector.incomingMessage(message);
        	}
        	//se ci sono altri oggetti fai altro
        }
        
        public void close(){
        	try {
				output.close();
				input.close();
        	} catch (IOException e) {
				System.out.println("errore io chiusura stream");
				e.printStackTrace();
			}
        	
        }
        public boolean isOnline(){
        	return socket.isConnected();
        }
        public String getHost(){
        	return socket.getInetAddress().getHostAddress();
        }

		
        
        
}		