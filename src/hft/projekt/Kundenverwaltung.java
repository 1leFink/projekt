package hft.projekt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;


public class Kundenverwaltung implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private HashMap<Integer, Kunde> kunden;
	
	
	public Kundenverwaltung() {
		this.kunden = new HashMap<Integer, Kunde>();
	}
	
	public HashMap<Integer, Kunde> getKunden() {
		return kunden;
	}

	/**
	 * @param k, Kunde der zur Kundenverwaltung hinzugefuegt werden soll
	 * Die Methode speichert den Kunden zusammen mit seiner Nummer in der HashMap kunden ab
	 */
	public void kundeHinzufuegen(Kunde k) {
		kunden.put(k.getKundennr(), k);
		System.out.println("Kunde '" + k.name + "' wurde hinzugefuegt");
	}

	/**
	 * 
	 * @param name, Kundenname des Kundens
	 * @return Kunde der entweder anhand seines Namens gefunden oder anhand einer Auswahl gewählt wird
	 */
	public Kunde kundeBestimmen(String name){

		Scanner sc = new Scanner(System.in);
		
		//HashMap wird angelegt die Alle Kunden speichert anhand eines Indexes mit dem der Nutzer später die Auswahl treffen soll
		HashMap<Integer, Kunde> all = new HashMap<>();
		int count = 1;
		for(Kunde k : kunden.values()){
			if(k.getName().equals(name)){
				all.put(count, k);
				count++;
			}
		}
		System.out.println(all.size());
		//Wenn mehr als ein Kunde gefunden wurde (all.size > 1) ist, alle Kunden mit dem Namen zur Auswahl präsentieren
		if(all.size() > 1){
			System.out.println("Mehrere Kunden mit dem Name: " + name + " gefunden. Welchen Kunde wollen sie wählen?");
		
		//Anzeigen der Kunden
		System.out.println("---------------------------------------------------------------------------------");
		for(int nr : all.keySet()){
			System.out.printf("(%d) name: %s \t Kundennummer: %d %n", nr, all.get(nr).getName(), all.get(nr).getKundennr());
		}

		//Versuche aus der nächsten Zeile einen Integer wert zu bekommen. Nutzer soll den entsprechenden Kunden anhand von dessen Index auswählen
		//Falls der Input keine Zahl ist kann keine Auswahl getroffen werden, die Auswahl wird abgebrochen
		try{
			int selection = Integer.parseInt(sc.next());
			return all.get(selection);
		}
		catch (Exception notnr){
			System.out.println("Fehler: Bitte benutzen sie die Nummer am anfang der Zeile.");
			Befehle.befehlEinlesen();
			return null;
		}

	//Falls nur ein Kunde mit dem Namen gefunden wurde muss keine Auswahl getroffen werden --> sofort zurueckgeben
	}else{
		return all.get(1);
	}
		
	}
	
	/**
	 * 
	 * @param kundennr, Nr des Kundens der entfernt werden soll
	 */
	public void kundeEntfernen(int kundennr) {
		
		boolean found = false;
		//Sobald ein Kunde mit entsprechender Nummer gefunden wurde, diesen entfernen
		for(int i : kunden.keySet()) {
			if(i == kundennr) {
				kunden.remove(i);
				found = true;
				
			}
		}
		//falls kein Kunde gefunden wurde Fehlermeldung anzeigen
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
	}
	

	/**
	 * 
	 * @param kundenname, Name des Kundens der entfernt werden soll
	 */
public void kundeEntfernen(String kundenname) {
		
		boolean found = false;
		//Sobald ein Kunde mit entsprechendem Name gefunden wurde, diesen entfernen
		for(Kunde k : kunden.values()) {
			if(k.getName().equals(kundenname)) {
				kunden.remove(k.getKundennr());
				found = true;
				break;
				
			}
		
		}
		//falls kein Kunde gefunden wurde Fehlermeldung anzeigen
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
	}
	
	public Kunde getKunde(int nr){
		boolean found = false;
		for(int i : kunden.keySet()) {
			if(i == nr) {
				found = true;
				return kunden.get(i);
			}
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
		return null;
	}

	public Kunde getKunde(String name){
		boolean found = false;
		for(Kunde k : kunden.values()) {
			if(k.getName().equals(name)) {
				found = true;
				return k;
			}
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
		return null;
	}

	public Kunde getKundeByNr(int nr) {
		boolean found = false;
		for(int i : kunden.keySet()) {
			if(i == nr) {
				found = true;
				return kunden.get(i);
			}
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
		return null;
	}
	
	public Kunde getKundeByName(String name) {
		boolean found = false;
		for(Kunde k : kunden.values()) {
			if(k.getName().equals(name)) {
				found = true;
				return k;
			}
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
		return null;
	}

	public void listKunden() {
		
		System.out.println("---------------------------Kunden---------------------------------");
		System.out.printf("%-15s%-15s%-15s%15s%n%n", "Name", "Nr.", "Beitrittsdatum", "Auftraege");
		for(Kunde k : kunden.values()) {
					
			System.out.printf("%-15s%-15s%-15s%15d%n", k.getName(), Integer.toString(k.getKundennr()), k.getBeitrittsdatum().toString(), k.getAuftraege().size());
			
			
		}
		System.out.println("------------------------------------------------------------------");
	}
	
	
	//debug Methods / Test Methods
	public void fillKunden(){
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		try {

			Scanner sc = new Scanner(new FileInputStream("names.txt"));			
			
			while(sc.hasNext()) {
				Kunde kunde = new Kunde(sc.next());
				k.kundeHinzufuegen(kunde);
			
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		Speicherverwaltung.saveKundenverwaltung(k);
		
	}
	
	public void clear() {
		kunden.clear();
	}

	public void listKundenByName() {
		
		List<Kunde> kHash = new ArrayList<Kunde>(kunden.values());
		
		Collections.sort(kHash, Kunde.compareByName());
		
		HashMap<Integer, Kunde> sortedMap = new LinkedHashMap<Integer, Kunde>();
		
		for(Kunde k : kHash) {
			sortedMap.put(k.getKundennr(), k);
		}
		
		printSortedMap(sortedMap);
		
	}

	public void listKundenByNr() {
		
		List<Kunde> kHash = new ArrayList<Kunde>(kunden.values());
		
		Collections.sort(kHash, Kunde.compareByNr());
		
		HashMap<Integer, Kunde> sortedMap = new LinkedHashMap<Integer, Kunde>();
		
		for(Kunde k : kHash) {
			sortedMap.put(k.getKundennr(), k);
		}
		
		printSortedMap(sortedMap);
		
	}

	public int getLongestName() {
		int length = 1;
		for(Kunde k : kunden.values()) {
			if(k.getName().length() > length) {
				length = k.getName().length();
			}
		}
		return length;
	}

	public void listKundenbyAuftraege() {
		
List<Kunde> kHash = new ArrayList<Kunde>(kunden.values());
		
		Collections.sort(kHash, Kunde.compareByAuftraege());
		
		HashMap<Integer, Kunde> sortedMap = new LinkedHashMap<Integer, Kunde>();
		
		for(Kunde k : kHash) {
			sortedMap.put(k.getKundennr(), k);
		}
		
		printSortedMap(sortedMap);
		
	}
	
	public void printSortedMap(HashMap<Integer, Kunde> map) {
		System.out.println("----------------------------Kunden--------------------------------");
		System.out.printf("%-15s%-15s%-15s%15s%n%n", "Name", "Nr.", "Beitrittsdatum", "Auftraege");
		for(Kunde k : map.values()) {
			
			System.out.printf("%-15s%-15s%-15s%15d%n", k.getName(), Integer.toString(k.getKundennr()), k.getBeitrittsdatum().toString(), k.getAuftraege().size());
			//s	
		}
		System.out.println("------------------------------------------------------------------");
	}
	
}
