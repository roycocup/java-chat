package settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import formatControl.formatControl;

public class Settings {
	private static String settingsPath;
	private static Properties properties;
	private static Properties defaultProperties;
	private static String otherNickname;
	
	public Settings(){
		
		properties = new Properties();
		defaultProperties = getDefaultProperties();
		settingsPath = getLocalSettingsPath();
		
		File settingsFile = new File(settingsPath);
		
		
		if(settingsFile.exists()){
			try { 
				//load existing settings
				FileReader reader = new FileReader(settingsFile);
				properties.load(reader);
				controlSettings();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{
			//create missing directories
			(settingsFile.getParentFile()).mkdirs();
			try {
				//create conf file
				settingsFile.createNewFile();
				properties = defaultProperties;
				saveSettings();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private static Properties getDefaultProperties(){
		Properties defaultProperties = new Properties();
		try{
			defaultProperties.load(Settings.class.getResourceAsStream("defaults.conf"));
		} catch (FileNotFoundException e) {
			//non succede
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		//set user system name as nickname 
		defaultProperties.setProperty("nickname", System.getProperty("user.name"));
		return defaultProperties;
	}
	private static String getLocalSettingsPath(){
		String osName = System.getProperty("os.name");
		String userDir = System.getProperty("user.home");
		
		
		if(osName.matches(".*[Ww]indows.*")){
			userDir = userDir.concat("\\AppData\\Roaming\\ChatJava\\chat.conf");
		}else if(osName.matches(".*[Ll]inux.*")){
			userDir = userDir.concat("/.chatJava/chat.conf");
		}else{
			userDir = userDir.concat(File.separator+"ChatJava"+File.separator+"chat.conf");
		}
		return userDir;
	}
	
	public static boolean saveSettings(){
		//save config
		try {
			properties.store(new FileWriter(settingsPath),null);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static void controlSettings(){
		//nickname errato
		if(!formatControl.estCorrectNickFormat(properties.getProperty("nickname"))){
			resetNickname();
			saveSettings();
		}
		if(!formatControl.estCorrectPortFormat(properties.getProperty("port"))){
			resetPort();
			saveSettings();
		}
	}
	
	//nickname
	public static String getNickname(){
		return properties.getProperty("nickname");
	}
	public static String getDefaultNickname(){
		return defaultProperties.getProperty("nickname");
	}
	public static void setNickname(String nickname){
		if(formatControl.estCorrectNickFormat(nickname)){
			properties.setProperty("nickname", nickname);
			saveSettings();
		}
	}
	public static void resetNickname(){
		properties.setProperty("nickname", defaultProperties.getProperty("nickname"));
		saveSettings();
	}
	//port
	public static String getPort(){
		return properties.getProperty("port");
	}
	public static String getDefaultPort(){
		return defaultProperties.getProperty("port");
	}
	public static void setPort(String port){
		if(formatControl.estCorrectPortFormat(port)){
			properties.setProperty("port", port);
			saveSettings();
		}
	}
	public static void resetPort(){
		properties.setProperty("port", defaultProperties.getProperty("port"));
		saveSettings();
	}
	
	//other Nickname temp
	public static String getOtherNickname(){
		if(otherNickname==null || otherNickname.matches("\\s+"))
			return "other";
		else
			return otherNickname;
	}
	public static void setOtherNickname(String nickname){
		otherNickname = nickname;
	}
}
