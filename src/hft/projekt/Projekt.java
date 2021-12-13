package hft.projekt;

import java.io.Serializable;

public class Projekt{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
	
		
		run();
		
		
	}

	public static void run() {
		
		if(Speicherverwaltung.firstStart() == true) {
			Kundenverwaltung verwaltung = new Kundenverwaltung();
			Speicherverwaltung.saveKundenverwaltung(verwaltung);
		}else {
			Kundenverwaltung verwaltung = Speicherverwaltung.loadKundenverwaltung();
		}
		
		Befehle.befehlEinlesen();
		
		
		
	}
	
}
