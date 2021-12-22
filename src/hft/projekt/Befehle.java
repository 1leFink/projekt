package hft.projekt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
		 */	
		
		
		
//		String befehl = sc.next();
//		String[] param;
//		befehl = befehl +"|"+ sc.nextLine();
//		
//		befehl = befehl.replaceAll("\\s+", "");
//		
//		String[] b = befehl.split("[|]");
//	
//		param = new String[b.length-1];
//		
//		for(int i = 1; i<b.length; i++) {
//			param[i-1] = b[i];
//		}
//		befehl = b[0];
		
		//trennt Befehl von Parametern
		String lBefehl = sc.next() + sc.nextLine();
		String[] lBefehlSplit = lBefehl.split("\\s");
		String befehl = lBefehlSplit[0];
		List<String> param = new ArrayList<String>(); 
		for(int i = 1; i<lBefehlSplit.length; i++) {
			param.add(lBefehlSplit[i]);
		}
		
		
		//Debug nur für uns
		System.out.println("DEBUG: " + befehl);
		System.out.println(param.toString());
		
		switch(befehl) {
			case "infoKunde":
				infoKunde(param);
				break;
			case "addKunde":
				addKunde(param);
				break;
			case "rmKunde":
				rmKunde(param);
				break;
			case "listKunden":
				listKunden(param);
				break;
			case "#fillKunden":
				fillKunden();
				break;
			case "listLager":
				listLager();
				break;
			case "readArtikel":
				readArtikel();
				break;
			case "help":
				help();
				break;
			case "quit":
				quit();
				break;
			default:
				System.out.println("Der Befehl '" + befehl + "' konnte nicht gefunden werden.");
				
		}
	
		Projekt.run();
	}
	
	private static void quit() {
		// TODO Auto-generated method stub
		System.exit(0);
		
	}

	private static void help() {
		
		System.out.println("------------------------------------------------------------------------------------------Befehle--------------------------------------------------------------------------------------------------");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n%n", "Befehl", "Parameter", "erw. Parameter", "Beschreibung", "Beispiele");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "infoKunde", "Kundenname, Kundennr.", "", "Gibt informationen über den Kunden an.", "'infoKunde Max', 'infoKunde 12345'");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "addKunde", "Kundenname", "", "Erstellt einen Kunden mit angegebenen Namen.", "'addKunde Max'");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "rmKunde", "Kundenname, Kundennr." , "-all", "Löscht einen Kunden nach Bestätigung.", "'rmKunde Max', 'rmKunde 12345', 'rmKunde -all'");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "listKunden", "", "-name, -nr, -datum", "Zeigt eine Liste der Kunden an, ggf. sortiert.", "'listKunden -name'");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "listLager", "", "-name, -nr, -menge", "Zeigt eine Liste der Artikel im Lager, ggf. sortiert.", "'listLager -nr'");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "readArtikel", "", "", "Fügt den Inhalt von bestand.txt. ins Lager ein", "" );
		System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		//		
//		System.out.println("infoKunde (name/nr) \t - Gibt Informationen über den Kunden an.");
//		System.out.println("addKunde (name) \t - Erstellt einen Kunden mit angegebenen Namen.");
//		System.out.println("rmKunde (name/nr/-all)");
		
	}

	private static void readArtikel() {
		// TODO Auto-generated method stub
		
		Lager lager = Speicherverwaltung.loadLager();
		lager.bestandEinlesen();
		Speicherverwaltung.saveLager(lager);
		
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

	public static void infoKunde(List<String> parameter) {
		
		if(parameter.isEmpty()){
			System.out.println("Fehlende Parameter für 'infoKunde'");
			befehlEinlesen();
		}
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde;
		
		try {
			Integer.parseInt(parameter.get(0));
			kunde = k.getKundeByNr(Integer.valueOf(parameter.get(0)));
		}catch(Exception e){
			//infoKunde wurde ein Namen uebergeben
			 kunde = k.getKundeByName(parameter.get(0));
		}
		
		if(kunde != null) {
			kunde.displayInfo();			
		}else {
			System.out.println("bitte versuchen sie es erneut");
		}

	
	}
	
	public static void addKunde(List<String> parameter) {
	
		if(parameter.isEmpty()){
			System.out.println("Fehlende Parameter für 'addKunde'");
			befehlEinlesen();
		}
		
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde = new Kunde(parameter.get(0));
		k.kundeHinzufuegen(kunde);
		Speicherverwaltung.saveKundenverwaltung(k);
		
		
		
	}
	
	public static void rmKunde(List<String> parameter) {
		
		if(parameter.isEmpty()){
			System.out.println("Fehlende Parameter für 'infoKunde'");
			befehlEinlesen();
		}
		else if(parameter.contains("-all")) {
			rmAll();
		}else {
			
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Scanner sc = new Scanner(System.in);
		
		try {
			Integer.parseInt(parameter.get(0));
			Kunde kunde = k.getKundeByNr(Integer.valueOf(parameter.get(0)));
			
			System.out.println("Kunde "  + kunde.getName() + " wirklich loeschen? y/n");
			if(sc.next().equals("y")) {
				k.kundeEntfernen(Integer.valueOf(parameter.get(0)));
				System.out.println("Kunde wurde entfernt. \n");
			}
		
		}
		catch(Exception e) {
			Kunde kunde = k.getKundeByName(parameter.get(0));
			
			//Kunde ist null wenn es kein Kunde mit dem Namen gibt
			if(kunde != null) {
				
			System.out.println("Kunde "  + kunde.getName() + " wirklich loeschen? y/n");
			if(sc.next().equals("y")) {
				k.kundeEntfernen(parameter.get(0));
				System.out.println("Kunde wurde entfernt. \n");
				}
			
			}
		}
		finally {
			Speicherverwaltung.saveKundenverwaltung(k);
		}
	
	}
		
	}
	
	public static void listKunden(List<String> parameter) {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		if(parameter.isEmpty()) {
			k.listKunden();	
		}else if(parameter.contains("-name")) {
			k.listKundenByName();
		}
		
	}
}
