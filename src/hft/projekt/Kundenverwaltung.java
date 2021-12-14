package hft.projekt;

import java.io.Serializable;
import java.util.HashMap;

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

	
	
	
	
}
