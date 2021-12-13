package hft.projekt;

import java.io.Serializable;

public enum Misc implements Serializable{

	VERSION("1.0"),
	NAME("projekt"),
	SAVE_PATH("C:\\Users\\Leon\\Desktop\\save\\Kundenverwaltung.txt");
	
	private String wert;
	
	Misc(String wert){
		this.wert = wert;
	}

	public String getWert() {
		return this.wert;
	}
	
	}

	
	
	