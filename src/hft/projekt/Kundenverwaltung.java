package hft.projekt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.HashMap;
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
		System.out.println("-------------Kunden--------------");
		System.out.printf("%-15s%-15s%-15s%n%n", "Name", "Nr.", "Beitrittsdatum");
		for(Kunde k : kunden.values()) {
			
			System.out.printf("%-15s%-15s%-15s%n", k.getName(), Integer.toString(k.getKundennr()), k.getBeitrittsdatum().toString());
			
			
		}
		System.out.println("----------------------------------");
	}
	
	
	//debug Methods / Test Methods
	
	//
	public void fillKunden(){
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		try {
			Scanner sc = new Scanner(new FileInputStream("names"));
			
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
	
	
}
