package hft.projekt;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Befehle {

	/**
	 * befehlEinlesen() ermöglicht alle Funktionalität des Programms.
	 * Befehle und ihre Parameter werden eingelesen und anschließend eine zuhörige Methode ausgeführt.
	 */
	public static void befehlEinlesen() {
		
		Scanner sc = new Scanner(System.in);
		System.out.print(">>> ");

		
		
		//ganze Zeile wird eingelesen
		String lBefehl = sc.next() + sc.nextLine();

		//Befehl und Parameter werden in einem Array 'lBefehlSplit' hinzugefügt. 
		String[] lBefehlSplit = lBefehl.split("\\s"); // --> Beispiel: listKunden Max --> im Array: = index0:"listKunden", index1:"Max"

		//da der Befehl sich immer ann der Ersten Stelle befinden soll, wird der Befehl aus index 0 genommen.
		String befehl = lBefehlSplit[0];

		//Da mehrere Parameter möglich sind wird eine Liste angelegt in die für die restliche Länge des Arrays, die jeweiligen Einträge hinzugefügt werden: Alle Parameter
		List<String> param = new ArrayList<String>(); 
		for(int i = 1; i<lBefehlSplit.length; i++) {
			param.add(lBefehlSplit[i]);
		}

		//Switch-case wird benutzt um, falls ein passender Befehl existiert, die jeweilige Methode aufzurufen. Die Liste der Parameter kann, falls nötig, auch mitgeben werden.
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
				listLager(param);
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
			case "readScript":
				readScript(param);
				break;
			case "rma":
				removeArtikel(param);
				break;

			//Default case fängt alle unsinnigen Eingaben ab.
			default: 
				System.out.println("Der Befehl '" + befehl + "' konnte nicht gefunden werden.");		
		}
		Projekt.run();
	}
	
	/**
	 * 
	 * @param param, liste der Parameter --> verlangt werden Artikelnummer, oder Artikelname
	 * @return {@code true}, Falls der Artikel existiert und entfernt wurde
	 */
	private static boolean removeArtikel(List<String> param) {
	
		//Wenn die liste der Parameter leer ist sofort abbrechen.
		if(param.isEmpty()) {
			System.out.println("Fehlende Parameter für 'rma'");
			return false;
		}

		//Änderung am Lager werden vorgenommen --> Lager laden
		Lager l = Speicherverwaltung.loadLager();
		
		//Artikelnamem von Artikeln die gelöscht werden. Werden am Ende ausgegeben.
		String gelöscht = "";
		
		//Block wird abhängig von Parameteranzahl n (größe der liste), n-mal ausgeführt. 
		for(int i = 0; i<param.size(); i++) {
			String parameter = "";
			Integer nr = null;
			
			//Check ob es sich um einen Artikelname(String) oder Artikelnummer(int) handelt
			try {
				nr = Integer.parseInt(param.get(i));
			}catch (Exception e){
				//es handelt sich nicht um eine nummer
				parameter = param.get(i);
			}
			
			//Wenn parameter leer bleibt, handelt es sich um eine Artikelnummer. Alle Aktionen werden anhand der Nummer durchgeführt.
			if(parameter.isBlank()) {
				
				//Falls der Artikel existiert wird er entfernt, sonst wird eine Fehlermeldung ausgegeben.
				if(l.artikelExists(nr)){ // NOTE: artikelExists() ist überladen (Akzeptiert sowohl Integers als auch Strings)
					Artikel k = l.getArtikel(nr);
					gelöscht += "'" + k.getArtikelName() + "', "; 
					l.artikelEntfernen(nr);
				}else {
					System.out.println("Operation nicht erfolgreich: Artikel existiert nicht");
					return false;
				}
				
			//variable 'parameter' ist nicht leer --> Es handelt sich um einen Artikelname(String).
			}else {
				
				//Falls der Artikel existiert wird er entfernt, sonst wird eine Fehlermeldung ausgegeben.
				if(l.artikelExists(parameter)){
					Artikel k = l.getArtikel(parameter);
					gelöscht += "'" + k.getArtikelName() + "', "; 
					l.artikelEntfernen(parameter);
				}else {
					System.out.println("Operation nicht erfolgreich: Artikel existiert nicht");
					return false;
				}
			
				
			}

			
		}
		
		//unterschiedliche Ausgaben je nach dem ob mehrere Artikel entfernt werden
		if(param.size() == 1) {
			System.out.println("Artikel " + gelöscht + " wurde entfernt.");
		}else {
			System.out.println("Artikel " + gelöscht + " wurden entfernt.");
		}
		
		//Änderungen im Lager werden gespeichert und true zurückgegeben.
		Speicherverwaltung.saveLager(l);
		return true;
	}
	

	/**
	 * @param param, liste der Parameter --> Verlangt wird ein Dateipfad
	 * @return {@code true} falls die Datei(Skript) richtig eingelesen wurde. 
	 * Liest eine Textdatei mit Befehlen ein. Diese Befehle werden über eine extra Methode 'befehlEinlesenScript()' behandelt und ausgeführt.
	 */
	private static boolean  readScript(List<String> param) {

		//Wenn die Liste der Parameter leer ist sofort abbrechen
		if(param.isEmpty()) {
			System.out.println("Bitte geben sie den Pfad der Datei ein.");
			return false;
		}
		
		//Datei anhand des Dateipfads aus den Parametern initalisieren
		File file = new File(param.get(0));

 
		//Wenn die Datei existiert an Methode 'befehEinlesenScript' weiterleiten, ansonsten Fehlermeldung anzeigen.
		if(file.exists()) {
			befehlEinlesenScript(file);
			return true;
		}
		else {
			System.out.println("Kein Script zum einlesen gefunden.");
			return false;
		}
		
	}

	/**
	 * 
	 * @param param, Liste der Parameter --> Verlangt einen Kundenname oder Kundenr
	 * @return {@code true} falls keine Fehler im Bestellprozess entstehen
	 * 
	 * Erstellt einen Auftrag mit einer Artikelliste der einem Kunde zugeordnet wird.
	 */
	private static boolean bestellen(List<String> param) {
	
		//sowohl die Kunden als auch das Lager werden geändert --> Beides Laden
		Kundenverwaltung kv = Speicherverwaltung.loadKundenverwaltung();
		Lager l = Speicherverwaltung.loadLager();
		Kunde kunde = null;
		
		//Wenn die Liste der Parameter leer ist sofort abbrechen
		if(param.isEmpty()) {
			System.out.println("Fehlende Parameter für 'bestellen'");
			return false;
		}
		
		//Wenn das Lager leer ist kann man auch keine Bestellung tätigen --> abbrechen
		if(l.getBestand().size() == 0) {
			System.out.println("Bestellung kann nicht aufgenommen werden: Das Lager ist leer");
			return false;
		}
				
		

			//entweder es wird eine Nummer übergeben mit der man den Kunde findet, oder einen Namen.
			try{
				int nr = Integer.parseInt(param.get(0));
				kunde = kv.getKunde(nr);
			}

			//Fall wenn es sich um einen Namen handelt
			 catch (Exception e) {
				String name = param.get(0);
				kunde = kv.kundeBestimmen(name); // Methode fängt den Fall für mehrere Kunden mit dem gleichen Namen ab.
			}
		
	

		//nicht Null wenn ein Kunde mit dem Namen/nr existiert
		if(kunde == null) {
			return false;
		}
	
		ArrayList<String> sort = new ArrayList<String>();
		sort.add("-name");
		listLager(sort);

		//flag wird benötigt um nächsten Teil mehrmals auszuführen
		boolean flag = true;
		List<Artikel> artikel = new ArrayList<Artikel>();
		Scanner sc = new Scanner(System.in);
		
		System.out.println("-------------------------------------AUFTRAG--------------------------------------------");
		
		//Solange flag true ist wird der block ausgeführt: Artikel werden zum Auftrag hinzugefügt
		while(flag) {
			
			
			//Erst weitermachen wenn ein Artikel gefunden wurde/ Artikel angegeben wird der im Lager existiert
			System.out.println("Artikelname: ");
			String name = null;
			boolean found = false;
			while(found == false) {
				
				 name = sc.next();
				
				 //Vorgang abrechen falls quit eingegeben wird
				 if(name.equals("quit")) {
					 return false;
				 }
				 
				if(l.artikelExists(name)) {
					found = true;
				}else {
					System.out.println("Artikel nicht gefunden versuche es erneut");
				
				}
			}
			
		
	
			//Menge ermitteln
			System.out.println("Menge: ");
	
			boolean mfound = false;
			int menge = 0;
			
			//erst weitermachen wenn eine Korrekte Menge eingeben wurde
			while(mfound == false) {
				String smenge = sc.next();				
				if(smenge.equals("quit")) {
					return false;
				}
				//überprüfen ob es sich um eine Zahl handelt	
			try {
				menge = Integer.parseInt(smenge);
				if(menge > 0) {
					mfound = true;
				}else {
					System.out.println("Bitte geben sie eine ganzzahlige positive Menge an.");
				}

			}
			//fehlermeldung falls keine ganzahlige positive Zahl
			catch (Exception e) {
				System.out.println("Bitte geben sie eine Menge an.");
			}
		}
			//Preis des Artikels berechnen
			double preis = l.getArtikel(name).getPreis() * menge;
			
			
			//übeprüfe ob Artikel bereits im Auftrag enthalten ist
			boolean duplicate = false;
			for(Artikel k : artikel) {
				if(k.getArtikelName().equals(name)) {
					
					duplicate = true;	
				
					//kontrolle ob die angeforderte Menge den lagervorrat überschreitet
					if(k.getMenge() + menge <= l.getArtikel(name).getMenge()) {

						//Wenn nicht --> erhöhe den Artikel in der artikelliste um den Wert der Menge
						k.setMenge(k.getMenge() + menge);
						k.setPreis(k.getPreis() + l.getArtikel(name).getPreis() * menge);
					
					}else {
						System.out.println("Die menge des angeforderten Artikels ist nicht verfuegbar. Artikel wurde nicht hinzugefuegt.");
					}
				}
			}
			//anderfalls neuen Artikel erstellen und zur Liste hinzufügen
			if(duplicate == false) {
				
				//kontrolle ob die angeforderte Menge den lagervorrat überschreitet
				if(menge <= l.getArtikel(name).getMenge()){
					Artikel neu = new Artikel(name, 0, preis, menge, "");
					//NOTE: Ein Artikel objekt wird lediglich angelegt um eine zwischenspeicherung für die Auftragsübersicht zu ermöglichen.
					//Parameter Artikelnummer, und Kategorie bleiben leer obwohl diese für einen eigentlichen Artikel essentiell wären. In unserm Fall haben sie keine Bedeutung.
					artikel.add(neu);

				
				}else {
					System.out.println("Die menge des angeforderten Artikels ist nicht verfuegbar. Artikel wurde nicht hinzugefuegt.");
				}
				
			}
			

			//Auftragsuebersicht anzeigen, jedoch nur wenn die Artikelliste nicht leer ist
			if(artikel.isEmpty() == false) {
			//alle bisherigen Elemente des Auftrags drucken
			System.out.println("--------------------ÜBERSICHT-----------------------");
			System.out.printf("%-15s %-15s %-15s %n%n", "Artikelname" , "Menge", "Preis");
			
			for(Artikel k : artikel) {
				int total = l.getArtikel(k.getArtikelName()).getMenge();
				String aufmenge = k.getMenge() + "/" + total;
				System.out.printf("%-15s %-15s %-15.2f\u20ac", k.getArtikelName(), aufmenge, k.getPreis());
				System.out.println();
			}
			System.out.println("----------------------------------------------------");
			
			//Gesamtpreis berechnen und anzeigen
			double gesamt = 0;
			for(Artikel k : artikel) {
				gesamt += k.getPreis();
			}
			System.out.printf("%-15s %21.2f\u20ac%n", "Gesamt:", gesamt);
			System.out.println("----------------------------------------------------");
			System.out.println();
			
			//Hinweis anzeigen
			System.out.println("Moechten sie noch einen Artikel hinzufuegen? y/n");
			
			
			//Falls mit n geantwortet wird, wird der Auftrag abgeschickt/dem Kunden hinzugefuegt
			if(sc.next().equals("n")) {
				flag = false;
			}
			
			} //end if artikel.isempty
			
		}
		//ende While schleife --> Auftrag kann abgeschickt werden
		
		//Auftrag wird von dem Kunde erstellt
		if(kunde.bestellen(artikel)) {
			System.out.println("Auftrag wurde übermittelt! \n");
		}else {
			System.out.println("Etwas ist schiefgelaufen, bitte versuchen sie es erneut.");
		}
		//Änderungen werden gespeichert.
		Speicherverwaltung.saveKundenverwaltung(kv);
		
		return true;
	
		
	}
	
	//quit() beendet das Programm
	private static void quit() {
		System.exit(0);
	}

	//help() zeigt eine Liste der Befehlen an.
	private static void help() {
		System.out.println("------------------------------------------------------------------------------------------Befehle--------------------------------------------------------------------------------------------------\n");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "Befehl", "Parameter", "erw. Parameter", "Beschreibung", "Beispiele");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n%n", "^^^^^^", "^^^^^^^^^", "^^^^^^^^^^^^^^", "^^^^^^^^^^^^", "^^^^^^^^^");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "infoKunde", "Kundenname, Kundennr.", "", "Gibt informationen über den Kunden an.", "'infoKunde Max', 'infoKunde 12345'");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "addKunde", "Kundenname", "", "Erstellt einen Kunden mit angegebenen Namen.", "'addKunde Max'");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "rmKunde", "Kundenname, Kundennr." , "-all", "Löscht einen Kunden nach Bestätigung.", "'rmKunde Max', 'rmKunde 12345', 'rmKunde -all'");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "listKunden", "", "-name, -nr, -auft", "Zeigt eine Liste der Kunden an, ggf. sortiert.", "'listKunden -name'");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "listLager", "", "-name, -nr, -menge, -preis, -kategorie", "Zeigt eine Liste der Artikel im Lager, ggf. sortiert.", "'listLager -nr'");
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "readArtikel", "", "", "Fügt den Inhalt von bestand.txt. ins Lager ein", "" );
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "tutorial", "", "", "Interaktive Einfuerung zur Bedienung der Anwendung.", "" );
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "bestellen", "Kundenname", "", "Erstellt einen Auftrag mit übergebenen Artikeln", "'bestellen Max'" );
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n", "readScript", "Dateipfad(txt)", "", "Liest eine Textdatei mit Befehlen ein und führt diese aus.", "'readScript C:\\Users\\Max\\Desktop\\script.txt'" );
		System.out.printf("%-20s%-30s%-40s%-60s%-40s%n%n", "quit", "", "", "Beendet das Programm, oder einen Unterprozess.", " Wenn im Tutorial: quit --> zurück zu Befehlen" );

		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
	}

	//readArtikel() ließt die Textdatei bestand.txt ein (muss sich im gleichen directory befinden)
	private static void readArtikel() {
		
		//Änderungen am Lager --> Lager laden
		Lager lager = Speicherverwaltung.loadLager();

		//falls der Bestand eingelesen wurde und keine Fehler entstanden sind Lager speichern
		if(lager.bestandEinlesen() == true) {
			Speicherverwaltung.saveLager(lager);
			System.out.println("Einlesen erfolgreich. Artikel wurden dem Lager hinzugefügt");
		}else {
			//Fehler beim einlesen -> nicht speichern
		}
		
	}

	//rmAll() ist eine abänderung von rmKunde und wird aufgerufen falls "rmKunde -all" eingeben wird
	//Die Methode löscht alle Kunden aus der Kundenverwaltung nach Rückfrage
	private static void rmAll() {
		Scanner sc = new Scanner(System.in);
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		
		System.out.println("Alle Kunden werden gelöscht. Sind sie sicher? y/n");
		
		if(sc.next().equals("y")) {
			System.out.println("Alle Kunden gelöscht.\n");
			k.clear();
			Speicherverwaltung.saveKundenverwaltung(k);
		}	
	}

	//listLager() ruft lagerAnzeigen() in der Klasse 'Lager' auf, die das Lager anzeigt.
	private static void listLager(List<String> param) {
		Lager l = Speicherverwaltung.loadLager();

		if(param.contains("-name")){
			l.listArtikelByName();
		}else if(param.contains("-nr")){
			l.listArtikelByNr();
		}else if(param.contains("-preis")){
			l.listArtikelByPreis();
		}else if(param.contains("-menge")){
			l.listArtikelByMenge();
		}else if(param.contains("-Kategorie")){
			l.listArtikelByKategorie();
		}else{
			l.lagerAnzeigen();
		}

		
	}

	//Debug method --> nicht in dokumentation von help enthalten
	//Füllt das Lager mit einer Vielzahl von Kunden zu demonstrationszwecken
	private static void fillKunden() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		k.fillKunden();
		
	}

	/**
	 * 
	 * @param parameter, Liste der Parameter --> Verlangt eine Kundennummer oder Kundenname
	 * Zeigt die Informationen zu einem Kunden an.
	 */
	public static boolean infoKunde(List<String> parameter) {
		
		//Falls kein Parameter vorhanden sofort abbrechen
		if(parameter.isEmpty()){
			System.out.println("Fehlende Parameter für 'infoKunde'");
			return false;
		}
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde;
		
		//Unterscheidung ob eine Kundennummer oder Kundenname eingeben wird
		try {
			Integer.parseInt(parameter.get(0));
			kunde = k.getKunde(Integer.valueOf(parameter.get(0)));
		}catch(Exception e){
			//infoKunde wurde ein Namen uebergeben
			 kunde = k.kundeBestimmen(parameter.get(0)); //--> Behandelt auch den Fall von mehreren Kunden mit dem selben Namen
		}
		
		//Wenn ein Kunde gefunden wurde --> informationen über ihn anzeigen, sonst Fehlermeldung
		if(kunde != null) {
			kunde.displayInfo();	
			return true;		
		}else {
			System.out.println("Bitte versuchen sie es erneut");
			return false;
		}
	}
	
	/**
	 * 
	 * @param parameter, Liste der Parameter --> verlangt einen Kundenname(String)
	 * @return {@code true} falls die Methode einen Kunden hinzufügt
	 */
	public static boolean addKunde(List<String> parameter) {
	
		//Falls kein Parameter vorhanden sofort abbrechen
		if(parameter.isEmpty()){
			System.out.println("Fehlende Parameter für 'addKunde'");
			return false;
		}
		
		//Falls die Länge des Namens 15 Zeichen überschreitet sofort abbrechen
		if(parameter.get(0).length() >15) {
			System.out.println("Fehler: Der Name des Kundens darf maximal 15 Zeichen lang sein. Versuchen sie es erneut.");
			return false;
		}
		
		//Kundenverwaltung wird veränder --> Kundenverwaltung laden, Kunde hinzufügen und Speichern
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Kunde kunde = new Kunde(parameter.get(0));
		k.kundeHinzufuegen(kunde);
		Speicherverwaltung.saveKundenverwaltung(k);
		return true;
	
	}
	
	
	/**
	 * 
	 * @param parameter, Liste der parameter --> verlangt Kundennummer oder Kundenname
	 * @return {@code true} falls ein Kunde entfehrnt wurde
	 */
	public static boolean rmKunde(List<String> parameter) {
		
		//Falls keine Parameter vorhanden sofort abbrechen
		if(parameter.isEmpty()){
			System.out.println("Fehlende Parameter für 'rmKunde'");
			return false;
		}
		//Check für erweiterten parameter
		else if(parameter.contains("-all")) {
			rmAll();
			return true;
		}else {
			
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Scanner sc = new Scanner(System.in);
		
		//Unterschiedung zwischen Kundennummer und Kundenname
		try {
		
			Integer.parseInt(parameter.get(0));
			Kunde kunde = k.getKunde(Integer.parseInt(parameter.get(0)));
			
			//Kunde ist null wenn es kein Kunde mit der Nr gibt
			if(kunde != null){

			System.out.println("Kunde "  + kunde.getName() + " wirklich loeschen? y/n");
			if(sc.next().equals("y")) {
				k.kundeEntfernen(Integer.valueOf(parameter.get(0)));
				System.out.println("Kunde wurde entfernt. \n");
				return true;
			}
		}
		
		}
		catch(Exception e) {
			
			Kunde kunde = k.kundeBestimmen(parameter.get(0));
			
			//Kunde ist null wenn es kein Kunde mit dem Namen gibt
			if(kunde != null) {
				
			System.out.println("Kunde "  + kunde.getName() + " wirklich loeschen? y/n");
			if(sc.next().equals("y")) {
				k.kundeEntfernen(parameter.get(0));
				System.out.println("Kunde wurde entfernt. \n");
				return true;
				}
			}
		}
		finally {
			//Kundenverwaltung speichern
			Speicherverwaltung.saveKundenverwaltung(k);
		}
	}
		return true;
		
	}


	/**
	 * 
	 * @param parameter, Liste der Parameter --> opt. -name, -nr, -datum, -auft
	 * Gibt die Kundenliste aus, ggf. Sortiert 
	 * ruft lediglich die Methoden in der Klasse 'Kundenverwaltung' auf
	 */
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
	
	/**
	 * Interaktive Einführung die dem Nutzer zum einstieg als Hilfe dienen soll.
	 * Dem Nutzer werden zuerst erklärt was Befehle und parameter sind und wie man diese eingibt.
	 * Er lernt wie man die Kundenliste unsortiert als auch sortiert ausgibt und wie man Kunden zum System hinzufuegt.
	 */
	public static void tutorial() {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Hallo, willkommen zu der Interaktiven Einführung zu Shoppex. Diese kann einige Minuten in Anspruch nehmen.");
		System.out.println("Möchten sie fortfahren? y/n");
		if(sc.next().equals("y")) {
			
			System.out.println("\n\n\n");
			System.out.println("--1-- Navigation");
			System.out.println("Die Anwendung wird durch Befehle gesteuert, welche man in der Kommandozeile eingeben muss.");
			System.out.println("Parameter sind extra Anweisungen die hinter einem Befehl, mit Leerzeichen separiert, hinzugefügt werden können.");
			System.out.println("Als Hilfe dient der Befehl 'help' der eine Liste der verfuegbaren Befehle anzeigt.");
			System.out.println("Geben sie den Befehl 'help' ein. \n");
			

			//Geht erst weiter falls der Nutzer den Befehl 'help' eingegeben hat, oder bricht das Tutorial ab falls 'quit' eingeben wurde
			boolean correct = false;
			while(correct == false) {
				String answer = sc.next();
				if(answer.equals("help")) {
					correct = true;
					help();
		
				}else if(answer.equals("quit")) {
					befehlEinlesen();
					
				}else {
					System.out.println("Es scheint als wurde der Befehl falsch geschrieben. Versuche es erneut.");
				}
			}
			
			System.out.println("Wie sie sehen, gibt es eine Vielzahl von Befehlen die mit und ohne Parameter verwendet werden können.");
			System.out.println("\n|--2--| Befehle mit Parametern");
			System.out.println("Geben sie zuerst den Befehl 'listKunden' ein.");
			
			//Geht erst weiter wenn der Nutzer den Befehl 'listKunden' eingeben hat, oder bricht das Tutorial ab falls 'quit' eingeben wurde.
			boolean correct2 = false;
			while(correct2 == false) {
				String answer = sc.next() + sc.nextLine();
				if(answer.equals("listKunden")) {
					correct2 = true;
					listKunden(new ArrayList<String>());
				}else if(answer.equals("quit")) {
					befehlEinlesen();
				}else {
					System.out.println("Es scheint als wurde der befehl falsch geschrieben. Versuche es erneut");
				}
			}
			
			System.out.println("Dies gibt eine Liste der Kunden im System an. Eventuell sind jedoch keine Vorhanden");
			System.out.println("Fügen sie mithilfe von 'addKunde' und anschließend einen Namen als Parameter, 3 neue Kunden hinzu.");
			

			//Geht erst weiter wenn der Nutzer mithilfe des Befehls 'addKunde' und Parametern 3 Kunden hinzugefuegt hat, oder bricht das Tutorial ab falls 'quit' eingeben wurde.
			boolean correct3 = false;
			int count = 0;
			while(correct3 == false) {
				String[] answer = (sc.next() + sc.nextLine()).split("\\s");
				ArrayList<String> parameter = new ArrayList<String>();
				for(int i = 1; i<answer.length; i++) {
					parameter.add(answer[i]);
				}
				if(answer[0].equals("addKunde")) {	
					if(addKunde(parameter)) {
						count++;
						System.out.println(count + "/3 Kunden hinzugefuegt!");
						if(count == 3) {
							correct3 = true;
						}
					}
				}else if(answer[0].equals("quit")) {
					befehlEinlesen();
				}else {
						System.out.println("Es scheint als wurde der Befehl falsch geschrieben. Versuche es erneut.");
				}
			}
			System.out.println("Gut gemacht! geben sie sie nun die Kundenliste erneut aus.");
			
			//Geht erst weiter wenn der Nutzer den Befehl 'listKunden' eingeben hat, oder bricht das Tutorial ab falls 'quit' eingeben wurde.
			boolean correct4 = false;
			while(correct4 == false) {
				String answer = sc.next();
				if(answer.equals("listKunden")) {
					correct4 = true;
					listKunden(new ArrayList<String>());
				}else if(answer.equals("quit")) {
					befehlEinlesen();
				}else {
					System.out.println("Es scheint als wurde der Befehl falsch geschrieben. Versuche es erneut");
				}
			}
			
			System.out.println("Die Kunden wurden erfolgreich zur Liste hinzugefuegt.");
			System.out.println("Der Befehl 'listKunde' kann auch erweiterte Parameter haben: z.B. '-name','-nr'");
			System.out.println("Diese Parameter sortieren die Liste und geben sie anschließend aus.");
			System.out.println("Benutzen sie 'listKunden' mit einem erw. Parameter.");
			
			//Geht erst weiter wenn der Nutzer den Befehl 'listKunden' mit einem erw. Parameter eingeben hat, oder bricht das Tutorial ab falls 'quit' eingeben wurde.
			boolean correct5 = false;
			while(correct5 == false) {
				String answer = sc.next();
				if(answer.equals("listKunden")) {
					ArrayList<String> parameter = new ArrayList<String>();
					parameter.add(sc.nextLine().replaceAll("\\s", ""));
					listKunden(parameter);
					correct5 = true;
				}else if(answer.equals("quit")) {
					befehlEinlesen();
				}else {
					System.out.println("Es scheint als wurde der Befehl falsch geschrieben. Versuche es Erneut");
				}
			}
		
			System.out.println("Super! Damit verstehen sie die grundlegende Funktionsweise der Anwendung.");
			System.out.println("Benutzen sie den Befehl 'help' wenn sie weitere Hilfe brauchen.");
		}
		
	}

	/**
	 * 
	 * @param f, Datei die eingelesen werden soll
	 * Methode hat die gleiche Funktion wie auch 'befehlEinlesen' wobei hier die Befehle nicht durch die Eingabe in die Console übergeben werden, sondern durch das auslesen einer Textdatei
	 */
public static void befehlEinlesenScript(File f) {
		
		//Deklariern Eines Scanner Objekts
		Scanner sc;

		//Versuche den Scanner anhand der Datei f zu initalisieren
		try {
			sc = new Scanner(f);
		
			//trennt Befehl von Parametern
			
			while(sc.hasNext()) {
			
			//Ab hier ist die Funktionalität die gleiche
			String lBefehl = sc.next() + sc.nextLine();
			String[] lBefehlSplit = lBefehl.split("\\s");
			String befehl = lBefehlSplit[0];
			List<String> param = new ArrayList<String>(); 
			for(int i = 1; i<lBefehlSplit.length; i++) {
				param.add(lBefehlSplit[i]);
			}
					
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
					listLager(param);
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
				case "readScript":
					readScript(param);
					break;
				default:
					//nothing
			}
			
		}
			befehlEinlesen();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
}
