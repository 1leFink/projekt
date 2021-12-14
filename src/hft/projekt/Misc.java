package hft.projekt;

import java.io.Serializable;

public enum Misc implements Serializable{

	VERSION("1.0"),
	NAME("projekt"),
//	SAVE_PATH("C:\\Users\\Leon\\Desktop\\save\\Kundenverwaltung.txt"),
	SAVE_PATH("C:\\Users\\flova\\Desktop\\save\\Kundenverwaltung.txt"),
	BANNER("_____/\\\\\\\\\\\\\\\\\\\\\\____/\\\\\\_______________________________________________________________________________________________/\\\\\\___________/\\\\\\\\\\\\\\____        \r\n"
			+ " ___/\\\\\\/////////\\\\\\_\\/\\\\\\___________________________________________________________________________________________/\\\\\\\\\\\\\\_________/\\\\\\/////\\\\\\__       \r\n"
			+ "  __\\//\\\\\\______\\///__\\/\\\\\\_________________________/\\\\\\\\\\\\\\\\\\____/\\\\\\\\\\\\\\\\\\_________________________________________\\/////\\\\\\________/\\\\\\____\\//\\\\\\_      \r\n"
			+ "   ___\\////\\\\\\_________\\/\\\\\\_____________/\\\\\\\\\\_____/\\\\\\/////\\\\\\__/\\\\\\/////\\\\\\_____/\\\\\\\\\\\\\\\\___/\\\\\\____/\\\\\\_______________\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_     \r\n"
			+ "    ______\\////\\\\\\______\\/\\\\\\\\\\\\\\\\\\\\____/\\\\\\///\\\\\\__\\/\\\\\\\\\\\\\\\\\\\\__\\/\\\\\\\\\\\\\\\\\\\\____/\\\\\\/////\\\\\\_\\///\\\\\\/\\\\\\/________________\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_    \r\n"
			+ "     _________\\////\\\\\\___\\/\\\\\\/////\\\\\\__/\\\\\\__\\//\\\\\\_\\/\\\\\\//////___\\/\\\\\\//////____/\\\\\\\\\\\\\\\\\\\\\\____\\///\\\\\\/__________________\\/\\\\\\_______\\/\\\\\\_____\\/\\\\\\_   \r\n"
			+ "      __/\\\\\\______\\//\\\\\\__\\/\\\\\\___\\/\\\\\\_\\//\\\\\\__/\\\\\\__\\/\\\\\\_________\\/\\\\\\_________\\//\\\\///////______/\\\\\\/\\\\\\_________________\\/\\\\\\_______\\//\\\\\\____/\\\\\\__  \r\n"
			+ "       _\\///\\\\\\\\\\\\\\\\\\\\\\/___\\/\\\\\\___\\/\\\\\\__\\///\\\\\\\\\\/___\\/\\\\\\_________\\/\\\\\\__________\\//\\\\\\\\\\\\\\\\\\\\__/\\\\\\/\\///\\\\\\_______________\\/\\\\\\__/\\\\\\__\\///\\\\\\\\\\\\\\/___ \r\n"
			+ "        ___\\//////////_____\\///____\\///_____\\/////_____\\///__________\\///____________\\//////////__\\///____\\///________________\\///__\\///_____\\///////_____\r\n"
			+ "");
	
	private String wert;
	
	Misc(String wert){
		this.wert = wert;
	}

	public String getWert() {
		return this.wert;
	}
	
	}
