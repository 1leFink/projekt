package hft.projekt;

import java.util.Arrays;
import java.util.Scanner;




public class Befehle {



	
	public static void befehlEinlesen() {
		
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		
		Scanner sc = new Scanner(System.in);
		System.out.print(">>> ");
		
		/*
		 * Ein befehl besteht aus dem Befehlsnamen und der richtigen Anzahl an Parametern.
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
		befehl = befehl +"|"+ sc.nextLine();
		
		befehl = befehl.replaceAll("\\s+", "");
		
		String[] b = befehl.split("[|]");
	
		param = new String[b.length-1];
		
		for(int i = 1; i<b.length; i++) {
			param[i-1] = b[i];
		}
		befehl = b[0];
		

		System.out.println("DEBUG: " + befehl);
		System.out.println(Arrays.toString(param));
		
		switch(befehl) {
			case "infoKunde":
				infoKunde(param);
				break;
			case "addKunde":
				addKunde(param);
				break;
			case "rmKunde":
				
				switch(param[0]) {
				case "-all":
					rmAll();
					break;
				default:
					rmKunde(param);
					break;
				}
				break;
			case "listKunden":
				listKunden();
				break;
			case "#fillKunden":
				fillKunden();
				break;
			case "listLager":
				listLager();
				break;
			default:
				System.out.println("Der Befehl '" + befehl + "' konnte nicht gefunden werden.");
				
		}
	
		Projekt.run();
	}
	
	private static void rmAll() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		k.clear();
		Speicherverwaltung.saveKundenverwaltung(k);
		
	}

	private static void listLager() {
		// TODO Auto-generated method stub
		Lager l = Speicherverwaltung.loadLager();
		l.lagerAnzeigen();
	}

	//Debug method
	private static void fillKunden() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		k.fillKunden();
		
	}

	public static void infoKunde(String[] parameter) {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde;
		
		try {
			Integer.parseInt(parameter[0]);
			kunde = k.getKundeByNr(Integer.valueOf(parameter[0]));
		}catch(Exception e){
			//infoKunde wurde ein Namen �bergeben
			 kunde = k.getKundeByName(parameter[0]);
		}
		
		if(kunde != null) {
			kunde.displayInfo();			
		}else {
			System.out.println("bitte versuchen sie es erneut");
		}

	
	}
	
	public static void addKunde(String[] parameter) {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde = new Kunde(parameter[0]);
		k.kundeHinzufuegen(kunde);
		Speicherverwaltung.saveKundenverwaltung(k);
		
		
		
	}
	
	public static void rmKunde(String[] parameter) {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Scanner sc = new Scanner(System.in);
		
		try {
			Integer.parseInt(parameter[0]);
			Kunde kunde = k.getKundeByNr(Integer.valueOf(parameter[0]));
			
			System.out.println("Kunde "  + kunde.getName() + " wirklich l�schen? y/n");
			if(sc.next().equals("y")) {
				k.kundeEntfernen(Integer.valueOf(parameter[0]));
				System.out.println("Kunde wurde entfernt. \n");
			}
		
		}
		catch(Exception e) {
			Kunde kunde = k.getKundeByName(parameter[0]);
			
			//Kunde ist null wenn es kein Kunde mit dem Namen gibt
			if(kunde != null) {
				
			System.out.println("Kunde "  + kunde.getName() + " wirklich loeschen? y/n");
			if(sc.next().equals("y")) {
				k.kundeEntfernen(parameter[0]);
				System.out.println("Kunde wurde entfernt. \n");
				}
			
			}
		}
		finally {
			Speicherverwaltung.saveKundenverwaltung(k);
		}
		
	
	}
	
	public static void listKunden() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		k.listKunden();
		
	}
}
