/**
 * Sets the format of the conversation display.
 */

package conversation;

import settings.Settings;

public class Conversation {
	
	private static final String ME_COLOR = "A9ED2A";
	private static final String OTHER_COLOR = "00BFFF";
	private static final String SYSTEM_COLOR = "A4A4A4";
	//constant
	public static final int ME = 0;
	public static final int OTHER = 1;
	public static final int SYSTEM = 99;
	
	
	
	private StringBuffer conversation;
	
	public Conversation(){
		conversation = new StringBuffer("<HTML></HTML>");
	}
	
	public void post(String text, int person){

		int offset = conversation.length()-7;
		String nick;
		if(person == ME){
			nick = Settings.getNickname();
			conversation.insert(offset,"<FONT color=\"#"+ME_COLOR+"\"><B>"+nick+": </B></FONT>"+text+"<BR>");	
		}else if(person == OTHER){
			nick = Settings.getOtherNickname();
			conversation.insert(offset,"<FONT color=\"#"+OTHER_COLOR+"\"><B>"+nick+": </B></FONT>"+text+"<BR>");
		}else if(person == SYSTEM){
			conversation.insert(offset,"<CENTER><FONT color=\"#"+SYSTEM_COLOR+"\"><B>"+text+"</B></FONT><BR></CENTER>");
		}
	}
	
	public String toString(){
		return conversation.toString();
	}

}
