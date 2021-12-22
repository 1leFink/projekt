package hft.projekt;

import java.io.File;

public class Projekt{

	static boolean opened = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run();
		
		
	}

	public static void run() {
		
		if(opened == false) {
			System.out.println(Misc.BANNER.getWert());
			opened = true;

		
			System.out.println("F�r eine liste der Befehle: help");

		}
		
		Kundenverwaltung verwaltung;
		Lager lager;
		
		if(Speicherverwaltung.firstStart() == true) {
			verwaltung = new Kundenverwaltung();
			Speicherverwaltung.saveKundenverwaltung(verwaltung);
			
		}else {
			verwaltung = Speicherverwaltung.loadKundenverwaltung();
		}
		
		if(Speicherverwaltung.lagerExists() == true) {
			lager = Speicherverwaltung.loadLager();
		}else {
			lager = new Lager();
			Speicherverwaltung.saveLager(lager);
		}
		
		
		Befehle.befehlEinlesen();
	
	}
	
}
