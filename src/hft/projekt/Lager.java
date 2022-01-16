package hft.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

public class Lager implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	//Objektattribute
	private HashMap<Integer, Artikel> bestand;
	
	//Konstruktor
	public Lager() {
		this.bestand = new HashMap<Integer, Artikel>();
	}

	
	//Getter / Setter
	public HashMap<Integer, Artikel> getBestand() {
		return bestand;
	}

	public void setBestand(HashMap<Integer, Artikel> bestand) {
		this.bestand = bestand;
	}

	//lagerAnzeigen() gibt eine Übersicht über alle Artikel im Lager aus
	public void lagerAnzeigen() {
		
		System.out.println("-------------------------------------LAGER----------------------------------------------");
		System.out.printf("%-15s%-15s%-15s%-15s%-15s%n%n", "Name", "Nr.", "Preis", "Menge", "Kategorie");
		for(Artikel k : bestand.values()) {
			
			System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", k.getArtikelName(), Integer.toString(k.getArtikelNr()), k.getPreis() +"\u20AC", k.getMenge(), k.getKategorie());
	
		}
		System.out.println("----------------------------------------------------------------------------------------");
		
	}
	
	//bestandEinlesen(), liest eine Textdatei ein die ein Format entsprechen muss
	public boolean bestandEinlesen() {

		//File Objekt wird angelegt, mit festgelegten Dateipfad. Dies kann auch geändert werden, Momentan muss sich die (Txt) Datei im selben directory befinden.
		File f = new File(Misc.READ_PATH.getWert());
		
		//Versuche die File an Scanner zu übergeben --> möglicher Fehler, Datei nicht gefunden wird abgefangen
		try {
			Scanner sc = new Scanner(f);
			
			//Solange sc eine Nächste Zeile als input hat
			while(sc.hasNext()) {
				

				String line;
				String[] werte;
				//alle Attribute die ein Artikel haben muss (sollte)
				String name;
				int nr;
				double preis;
				int menge;
				String kategorie;
				
				//Versuche die Werte aus jeder Zeile anhand der Formatierung zu lesen. Falls hier ein Fehler passiert liegt das hächstwahrscheinlich an einem falschem Format.
				try {
					
					 line = sc.nextLine();
	
					 werte = line.split("[|]");
				
					 nr = Integer.valueOf(werte[0]);
					 name = werte[1];
					 preis = Double.parseDouble(werte[2]);
					 menge = Integer.parseInt(werte[3]);
					 kategorie = werte[4];
				
					 //Negative Mengen, preise, oder Nummern werden nicht zugelassen --> Exception wird geworfen
					 if(nr < 0 || preis < 0 || menge < 0) {
						 throw new Exception();
					 }
				
				//Exception Falsches Format
				}catch(Exception InvalidFormat) {
					System.out.println("Fehler beim einlesen. Stellen sie sicher, dass die Datei das richtige Format besitzt.");
					return false;
				}

				//Falls Ein Artikel mit dem Name im Lager schon existiert wird lediglich die Menge erhöht
				if(artikelExists(name)) {
					mengeErhoehen(name, menge);

				//Andernfalls wird ein Neuer Artikel erstellt und dem Lager hinzugefuegt.
				}else {
					Artikel k = new Artikel(name, nr, preis, menge, kategorie);
					bestand.put(nr, k);
				}

			}
		//Exception Datei nicht gefunden
		} catch (FileNotFoundException e) {
			System.out.println("Fehler: Datei wurde nicht gefunden.");
			
		}
		return true;
	}
	
	//Methoden die helfen das Lager zu verwalten


	/**
	 * @param nr, Artikelnummer des Artikels dessen Menge erhoeht werden soll
	 * @param anzahl, Menge um die erhoeht werden soll
	 * @return {@code true} falls ein Artikel mit der nr existiert und die Menge erhoeht werden konnte
	 */
	public boolean mengeErhoehen(int nr, int anzahl) {
	
		for(int i : bestand.keySet()) {
			Artikel k = bestand.get(i);
			if(k.getArtikelNr() == nr) {
				k.setMenge(k.getMenge() + anzahl);
				return true;
			}
		}
		return false;
		
	}

	/**
	 * @param name, Name des Artikels dessen Menge erhoeht werden soll
	 * @param anzahl, Menge um die erhoeht werden soll
	 * @return {@code true} falls ein Artikel mit dem Name existiert und die Menge erhoeht werden konnte
	 */
	public boolean mengeErhoehen(String name, int anzahl) {
		
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name)) {
				k.setMenge(k.getMenge() + anzahl);
				return true;
			}	
		}
		return false;
	}

	/**
	 * @param name, Name des Artikels dessen Menge reduziert werden soll
	 * @param anzahl, Menge um die reduziert werden soll
	 * @return {@code true} falls ein Artikel mit dem Name existiert und die Menge reduziert werden konnte
	 */
	public boolean mengeReduzieren(String name, int anzahl) {
		
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name) && k.getMenge() - anzahl > 0) {
				k.setMenge(k.getMenge() - anzahl);
				return true;
			}else if(k.getArtikelName().equals(name) && k.getMenge() - anzahl == 0) {
				bestand.remove(k.getArtikelNr());
				return true;
			}
		}
		
		System.out.println("Die angeforderte Menge �berschreitet den Vorrat an Artikeln. "  );
		return false;
	}
	

	/**
	 * @param nr, Nr des Artikels dessen Menge reduziert werden soll
	 * @param anzahl, Menge um die reduziert werden soll
	 * @return {@code true} falls ein Artikel mit der Nr existiert und die Menge reduziert werden konnte
	 */
	public boolean mengeReduzieren(int nr, int anzahl) {
		
		for(int i : bestand.keySet()) {
			Artikel k = bestand.get(i);
			if(k.getArtikelNr() == nr) {
				k.setMenge(k.getMenge() - anzahl);
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @param name, Name des Artikels der entfernt werden soll
 	 * @return {@code true} falls ein Artikel mit dem Namen existiert und dieser entfernt wurde.
	 */
	public boolean artikelEntfernen(String name) {
		
			for(Artikel k : bestand.values()) {
				if(k.getArtikelName().equals(name)) {
					bestand.remove(k.getArtikelNr());
					return true;
				}
			}	

		System.out.println("Operation nicht erfolgreich: Artikel konnte nicht gefunden werden.");
		return false;
	}
	
	/**
	 * @param artikelnr, nr des Artikels der entfernt werden soll
 	 * @return {@code true} falls ein Artikel mit der artikelnr existiert und dieser entfernt wurde
	 */
	public boolean artikelEntfernen(int artikelnr) {
	
		for(int nr : bestand.keySet()) {
			if(nr == artikelnr){
				bestand.remove(nr);
				return true;
			}
		}	
	System.out.println("Operation nicht erfolgreich: Artikel konnte nicht gefunden werden.");
	return false;
}
	

	/**
	 * @param name, Name des Artikels auf den geprüft werden soll ob er im Lager existiert
	 * @return {@code true} falls ein Artikel mit dem Name existiert
	 */	
	public boolean artikelExists(String name) {
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * @param artikelnr, Artikelnr des Artikels auf den geprüft werden soll ob er im Lager existiert
	 * @return {@code true} falls ein Artikel mit dem Name existiert
	 */	
	public boolean artikelExists(int artikelnr) {
		for(int nr : bestand.keySet()) {
			if(nr == artikelnr) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param name, Name des Artikels der zurueckgegeben werden soll
	 * @return Artikel, Artikel mit dem Namen
	 */
	public Artikel getArtikel(String name) {
		
		boolean found = false;
		
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name)) {
				return k;
			}
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Artikel konnte nicht gefunden werden");
		}
		return null;
	}
	
	/**
	 * 
	 * @param artikelnr, Artikelnummer des Artikels der zurueckgegeben werden soll
	 * @return Artikel, Artikel mit der Artikelnummer
	 */
	public Artikel getArtikel(int artikelnr) {
		
		boolean found = false;
		
		for(int nr : bestand.keySet()) {
			if(nr == artikelnr) {
				return bestand.get(nr);
			}
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Artikel konnte nicht gefunden werden");
		}
		return null;
	}

	//listArtikelByName() gibt eine eine Uebersichtliche Liste der Artikel auf der Konsole aus, sortiert nach Name
	public void listArtikelByName(){

		List<Artikel> artikel = new ArrayList<Artikel>(bestand.values());
		
		Collections.sort(artikel, Artikel.compareByName());

		HashMap<Integer, Artikel> sortedMap = new LinkedHashMap<Integer, Artikel>();
		
		for(Artikel k : artikel){
			sortedMap.put(k.getArtikelNr(), k);
		}
		
		lagerAnzeigenSorted(sortedMap);

	}

	//listArtikelByNr() gibt eine eine Uebersichtliche Liste der Artikel auf der Konsole aus, sortiert nach Nr
	public void listArtikelByNr(){

		
		List<Artikel> artikel = new ArrayList<Artikel>(bestand.values());
		
		Collections.sort(artikel, Artikel.compareByNr());

		HashMap<Integer, Artikel> sortedMap = new LinkedHashMap<Integer, Artikel>();
		
		for(Artikel k : artikel){
			sortedMap.put(k.getArtikelNr(), k);
		}
		
		lagerAnzeigenSorted(sortedMap);

	}
	//listArtikelByPreis() gibt eine eine Uebersichtliche Liste der Artikel auf der Konsole aus, sortiert nach Preis
	public void listArtikelByPreis(){

		
		List<Artikel> artikel = new ArrayList<Artikel>(bestand.values());
		
		Collections.sort(artikel, Artikel.compareByPreis());

		HashMap<Integer, Artikel> sortedMap = new LinkedHashMap<Integer, Artikel>(); 
		
		for(Artikel k : artikel){
			sortedMap.put(k.getArtikelNr(), k);
		}
		
		lagerAnzeigenSorted(sortedMap);

	}
	//listArtikelByMenge() gibt eine eine Uebersichtliche Liste der Artikel auf der Konsole aus, sortiert nach Menge
	public void listArtikelByMenge(){

		
		List<Artikel> artikel = new ArrayList<Artikel>(bestand.values());
		
		Collections.sort(artikel, Artikel.compareByMenge());

		HashMap<Integer, Artikel> sortedMap = new LinkedHashMap<Integer, Artikel>();
		
		for(Artikel k : artikel){
			sortedMap.put(k.getArtikelNr(), k);
		}
		
		lagerAnzeigenSorted(sortedMap);

	}
	//listArtikelByKategorie() gibt eine eine Uebersichtliche Liste der Artikel auf der Konsole aus, sortiert nach Kategorie
	public void listArtikelByKategorie(){

		
		List<Artikel> artikel = new ArrayList<Artikel>(bestand.values());
		
		Collections.sort(artikel, Artikel.compareByKategorie());

		HashMap<Integer, Artikel> sortedMap = new LinkedHashMap<Integer, Artikel>();
		
		for(Artikel k : artikel){
			sortedMap.put(k.getArtikelNr(), k);
		}
		
		lagerAnzeigenSorted(sortedMap);

	}
	
	
	
		//lagerAnzeigenSorted() gibt eine Übersicht über alle Artikel im Lager aus, sortiert (falls sorteMap sortiert ist)
		public void lagerAnzeigenSorted(HashMap<Integer, Artikel> sortedMap) {
		
			System.out.println("-------------------------------------LAGER----------------------------------------------");
			System.out.printf("%-15s%-15s%-15s%-15s%-15s%n%n", "Name", "Nr.", "Preis", "Menge", "Kategorie");
			for(Artikel k : sortedMap.values()) {
				
				System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", k.getArtikelName(), Integer.toString(k.getArtikelNr()), k.getPreis() +"\u20AC", k.getMenge(), k.getKategorie());
		
			}
			System.out.println("----------------------------------------------------------------------------------------");
			
		}
	
}
