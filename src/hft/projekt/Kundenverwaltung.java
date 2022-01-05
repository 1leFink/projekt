package hft.projekt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	

	public void kundeHinzufuegen(Kunde k) {
		kunden.put(k.getKundennr(), k);
		System.out.println("Kunde '" + k.name + "' wurde hinzugef�gt");
	}

	
	
	public void kundeEntfernen(int kundennr) {
		
		boolean found = false;
		for(int i : kunden.keySet()) {
			if(i == kundennr) {
				kunden.remove(i);
				found = true;
				
			}
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
	}
	
public void kundeEntfernen(String kundenname) {
		
		boolean found = false;
		for(Kunde k : kunden.values()) {
			if(k.getName().equals(kundenname)) {
				kunden.remove(k.getKundennr());
				found = true;
				break;
				
			}
		
		}
		if(found == false) {
			System.out.println("Operation nicht erfolgreich: Kunde konnte nicht gefunden werden");
		}
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
	
	//
	public void fillKunden(){
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		try {
			Scanner sc = new Scanner(new FileInputStream(Misc.NAMES_PATH.getWert()));
			
			while(sc.hasNext()) {
				Kunde kunde = new Kunde(sc.next());
				k.kundeHinzufuegen(kunde);
			
			}
			
			sc.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
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
				
		}
		System.out.println("------------------------------------------------------------------");
	}
	
}
