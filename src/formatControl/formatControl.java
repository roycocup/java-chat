package formatControl;

public class formatControl {
	
	public static boolean estCorrectPortFormat(String port){
		try {
			int portInt = Integer.parseInt(port);
			return ( portInt>1024 && portInt<65535 );
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean estCorrectNickFormat(String nick){
		if(
			//non e' nulla
			nick != null &&
			//e' composta da lettere, numeri
			nick.matches("[(\\w)(\\d)]+")
		)
			return true;
		else
			return false;
	}
	
	public static boolean estCorrectMessageFormat(String message){
		if(
				//non e' nulla
				message != null &&
				//e' composta da lettere, numeri
				!message.matches("\\s*|.*<.*>.*")
			)
				return true;
			else
				return false;
	}

}
