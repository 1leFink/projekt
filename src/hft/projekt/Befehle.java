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
		
		
//		//Debug nur für uns
//		System.out.println("DEBUG: " + befehl);
//		System.out.println(param.toString());
		
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
			case "tutorial":
				tutorial();
				break;
			case "bestellen":
				bestellen(param);
				break;
			default:
				System.out.println("Der Befehl '" + befehl + "' konnte nicht gefunden werden.");
				
		}
		
		Projekt.run();
	}
	
	
	private static boolean bestellen(List<String> param) {
	
		//bestellen (Kundenname/Kundennr)
		
		Kundenverwaltung kv = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde = null;
		if(param.isEmpty()) {
			System.out.println("Fehlende Parameter für 'bestellen'");
			return false;
		}
		
		if(param.size() == 1) {
			kunde = kv.getKundeByName(param.get(0));
			}
	
		//nicht Null wenn ein Kunde mit dem Namen existiert
		if(kunde == null) {
			return false;
		}
	
		
		boolean flag = true;
		List<Artikel> artikel = new ArrayList<Artikel>();
		Scanner sc = new Scanner(System.in);
		Lager l = Speicherverwaltung.loadLager();
		System.out.println("-----------------------------------Auftrag----------------------------------");
		
		while(flag) {
			
			
			System.out.println("Artikelname: ");
			String name = sc.next();
			
			if(l.artikelExists(name)) {
				Artikel k = l.getArtikelByName(name);	
			}else {
				System.out.println("Artikel nicht gefunden versuche es erneut");
				return false;
			}
			
			System.out.println("Menge: ");
			String smenge = sc.next();
			
			//überprüfen ob es sich um eine Zahl handelt
			int menge;
			try {
				menge = Integer.parseInt(smenge);
			}
			catch (Exception e) {
				System.out.println("Bitte geben sie eine Menge an");
				return false;
			}
			
			//Preis des Artikels berechnen
			double preis = l.getArtikelByName(name).getPreis() * menge;
			
			
			//übeprüfe ob Artikel bereits im Auftrag enthalten ist
			boolean duplicate = false;
			for(Artikel k : artikel) {
				if(k.getArtikelName().equals(name)) {
					k.setMenge(k.getMenge() + menge);
					k.setPreis(k.getPreis() + l.getArtikelByName(name).getPreis() * menge);
					duplicate = true;
				}
			}
			//anderfalls neuen Artikel erstellen und zur Liste hinzufügen
			if(duplicate == false) {
				Artikel neu = new Artikel(name, 0, preis, menge, "");
				artikel.add(neu);
			}
			

			
			//alle bisherigen Elemente des Auftrags drucken
			System.out.println("----------------Auftrag--------------------------");
			System.out.printf("%-15s %-15s %-15s %n%n", "Artikelname" , "Menge", "Preis");
			
			for(Artikel k : artikel) {
				System.out.printf("%-15s %-15s %-15.2f\u20ac", k.getArtikelName(), k.getMenge(), k.getPreis());
				System.out.println();
			}
			System.out.println("--------------------------------------------------");
			double gesamt = 0;
			for(Artikel k : artikel) {
				gesamt += k.getPreis();
			}
			System.out.printf("%-15s %21.2f\u20ac%n", "Gesamt:", gesamt);
			System.out.println("--------------------------------------------------");
			System.out.println();
			
		
			
			System.out.println("Möchten sie noch einen Artikel hinzüfügen? y/n");
			
			if(sc.next().equals("n")) {
				flag = false;
			}
			
		}
		//ende While schleife --> Auftrag kann abgeschickt werden
		
		//Auftrag wird von dem Kunde erstellt
		if(kunde.bestellen(artikel)) {
			System.out.println("Auftrag wurde übermittelt! \n");
		}else {
			System.out.println("Etwas ist schiefgelaufen, bitte versuchen sie es erneut.");
		}
		Speicherverwaltung.saveKundenverwaltung(kv);
		
		return true;
	
		
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
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "listKunden", "", "-name, -nr, -datum, -auft", "Zeigt eine Liste der Kunden an, ggf. sortiert.", "'listKunden -name'");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "listLager", "", "-name, -nr, -menge", "Zeigt eine Liste der Artikel im Lager, ggf. sortiert.", "'listLager -nr'");
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "readArtikel", "", "", "Fügt den Inhalt von bestand.txt. ins Lager ein", "" );
		System.out.printf("%-30s%-30s%-30s%-60s%-30s%n", "tutorial", "", "", "Interaktive Einfuerung zur Bedienung der Anwendung.", "" );
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
		}else if(parameter.contains("-nr")) {
			k.listKundenByNr();
		}else if(parameter.contains("-auft")) {
			k.listKundenbyAuftraege();
		}
		
	}
	
	
	public static void tutorial() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Hallo, willkommen zu der Interaktiven Einführung zu Shoppex. Diese kann einige Minuten in Anspruch nehmen.");
		System.out.println("Möchten sie fortfahren? y/n");
		if(sc.next().equals("y")) {
			System.out.println("\n\n\n");
			System.out.println("--1-- Navigation");
			System.out.println("Die Anwendung wird durch Befehle gesteuert, welche man in der Kommandozeile eingeben muss.");
			System.out.println("Parameter sind extra Anweisungen die hinter einem Befehl mit Leerzeichen separiert hinzugefügt werden können.");
			System.out.println("Als Hilfe dient der Kommand 'help' der eine Liste der verfügbaren Befehle anzeigt.");
			System.out.println("Geben sie den Befehl 'help' ein.");
			
			boolean correct = false;
			while(correct == false) {
				if(sc.next().equals("help")) {
					correct = true;
					help();
				}else {
					System.out.println("Es scheint als wurde der befehl falsch geschrieben. Versuche es erneut");
				}
			}
			
			System.out.println("Wie sie sehen, gibt es eine Vielzahl von Befehlen die mit und ohne Parameter verwendet werden können.");
			
			System.out.println("\n|--2--| Befehle mit Parametern");
			System.out.println("Geben sie zuerst den Befehl 'listKunden' ein.");
			
			boolean correct2 = false;
			while(correct2 == false) {
				if(sc.next().equals("listKunden")) {
					correct2 = true;
					rmAll();
					listKunden(new ArrayList<String>());
				}else {
					System.out.println("Es scheint als wurde der befehl falsch geschrieben. Versuche es erneut");
				}
			}
			
			System.out.println("Dies gibt eine Liste der Kunden im System an. Noch sind jedoch keine Vorhanden");
			System.out.println("Fügen sie mithilfe von 'addKunde' und anschließend einen Namen als Parameter, 3 neue Kunden hinzu.");
			
			boolean correct3 = false;
			int count = 0;
			while(correct3 == false) {
				if(sc.next().equals("addKunde")) {
					ArrayList<String> parameter = new ArrayList<String>();
					parameter.add(sc.nextLine().replaceAll("\\s", ""));
					
					addKunde(parameter);
					count++;
					System.out.println(count + "/3 Kunden hinzugefügt!");
					if(count == 3) {
						correct3 = true;
					}
				}else {
					System.out.println("Es scheint als wurde der befehl falsch geschrieben. Versuche es erneut");
				}
			}
			System.out.println("Gut gemacht! geben sie sie nun die Kundenliste erneut aus.");
			
			boolean correct4 = false;
			while(correct4 == false) {
				if(sc.next().equals("listKunden")) {
					correct4 = true;
					listKunden(new ArrayList<String>());
				}else {
					System.out.println("Es scheint als wurde der befehl falsch geschrieben. Versuche es erneut");
				}
			}
			
			System.out.println("Die Kunden wurden erfolgreich zur Liste hinzugefügt.");
			System.out.println("Der Befehl 'listKunde' kann auch erweiterte Parameter haben: z.B. '-name','-nr'");
			System.out.println("Diese Parameter sortieren die Liste und geben sie anschließend aus.");
			System.out.println("Benutzen sie 'listKunden' mit einem erw. Parameter.");
			
			boolean correct5 = false;
			
			while(correct5 == false) {
				if(sc.next().equals("listKunden")) {
					ArrayList<String> parameter = new ArrayList<String>();
					parameter.add(sc.nextLine().replaceAll("\\s", ""));
					listKunden(parameter);
					correct5 = true;
				}else {
					System.out.println("Es scheint als wurde der befehl falsch geschrieben. Versuche es erneut");
				}
			}
		
			System.out.println("Super! Damit verstehen sie die grundlegende Funktionsweise der Anwendung.");
			System.out.println("Benutzen sie den befehl 'help' wenn sie weitere Hilfe brauchen.");
		}
		
	}
	
}
