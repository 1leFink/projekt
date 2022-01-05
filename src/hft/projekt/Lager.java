package hft.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Lager implements Serializable{

	private HashMap<Integer, Artikel> bestand;
	
	public Lager() {
		this.bestand = new HashMap<Integer, Artikel>();
	}
	
	public void lagerAnzeigen() {
		
		System.out.println("-------------------------------------LAGER----------------------------------------------");
		System.out.printf("%-15s%-15s%-15s%-15s%-15s%n%n", "Name", "Nr.", "Preis", "Menge", "Kategorie");
		for(Artikel k : bestand.values()) {
			
			System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", k.getArtikelName(), Integer.toString(k.getArtikelNr()), k.getPreis() +"\u20AC", k.getMenge(), k.getKategorie());
	
		}
		System.out.println("----------------------------------------------------------------------------------------");
		
	}
	
	public boolean bestandEinlesen() {
		
		
		//C:\\Users\\Leon\\Desktop\\save\\bestand.txt
		File f = new File(Misc.READ_PATH.getWert());
		
		try {
			Scanner sc = new Scanner(f);
			
			while(sc.hasNext()) {
				
				String line;
				String[] werte;
				String name;
				int nr;
				double preis;
				int menge;
				String kategorie;
				
				try {
					
					 line = sc.nextLine();
					
					 werte = line.split("[|]");
				
					 name = werte[1];
					 nr = Integer.valueOf(werte[0]);
					 preis = Double.parseDouble(werte[2]);
					 menge = Integer.parseInt(werte[3]);
					 kategorie = werte[4];
					 
				}catch(Exception InvalidFormat) {
					System.out.println("Fehler beim einlesen. Stellen sie sicher, dass die Datei das richtige Format besitzt.");
					return false;
				}

				if(this.artikelExists(name)) {
					this.mengeErhoehen(name, menge);

				}else {
					Artikel k = new Artikel(name, nr, preis, menge, kategorie);
					bestand.put(Integer.valueOf(werte[0]), k);
				}

			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return true;
	}
	
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
	
	public boolean mengeErhoehen(String name, int anzahl) {
		
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name)) {
				k.setMenge(k.getMenge() + anzahl);
				return true;
			}	
		}
		return false;
	}
	
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
		
		System.out.println("Die angeforderte Menge überschreitet den Vorrat an Artikeln. "  );
		return false;
	}
	
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
	
	public boolean artikelExists(String name) {
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	public Artikel getArtikelByName(String name) {
		
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
	
	
	
}
