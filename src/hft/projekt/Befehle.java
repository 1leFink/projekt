package hft.projekt;

import java.util.Arrays;
import java.util.Scanner;




public class Befehle {



	
	public static void befehlEinlesen() {
		
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		
		Scanner sc = new Scanner(System.in);
		System.out.print(">>>");
		
		/*
		 * Ein befehl besteht aus dem Befehlsnamen und der richtigen Anzhal an Parametern.
		 * Beispiele:
		 * 
		 * infoKunde (kundennummer/name) 
		 * addKunde (name)
		 * rmkunde (kundennummer/name)
		 * rmartikel (artikelnummer/artikelname)
		 * 
		 * 
		 * 
		 */
		
		String befehl = sc.next();
		String[] param;
		befehl = befehl +"-"+ sc.nextLine();
		
		befehl = befehl.replaceAll("\\s+", "");
		
		String[] b = befehl.split("-");
	
		param = new String[b.length-1];
		
		for(int i = 1; i<b.length; i++) {
			param[i-1] = b[i];
		}
		befehl = b[0];
		

		
		
		switch(befehl) {
			case "infoKunde":
				infoKunde(param);
				break;
			case "addKunde":
				addKunde(param);
				break;
			default:
				System.out.println("Der Befehl '" + befehl + "' konnte nicht gefunden werden.");
		}
	
		Projekt.run();
	}
	
	public static void infoKunde(String[] parameter) {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde;
		try {
			Integer.parseInt(parameter[0]);
			 kunde = k.getKundeByNr(Integer.valueOf(0));
		}catch(Exception e){
			//infoKunde wurde ein Namen übergeben
			 kunde = k.getKundeByName(parameter[0]);
		}
		
		if(kunde != null) {
			kunde.displayInfo();			
		}else {
			System.out.println("bitte versuchen sie es erneut");
		}

	//test
	}
	
	public static void addKunde(String[] parameter) {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde = new Kunde(parameter[0]);
		k.kundeHinzufuegen(kunde);
		Speicherverwaltung.saveKundenverwaltung(k);
		
		
		
	}
	
}
