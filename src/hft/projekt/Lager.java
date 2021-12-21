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
			
			System.out.printf("%-15s%-15s%-15s%-15s%-15s%n", k.getArtikelName(), Integer.toString(k.getArtikelNr()), k.getPreis() +"€", k.getMenge(), k.getKategorie());
	
		}
		System.out.println("----------------------------------------------------------------------------------------");
		
	}
	
	public void bestandEinlesen() {
		
		
		//C:\\Users\\Leon\\Desktop\\save\\bestand.txt
		File f = new File(Misc.READ_PATH.getWert());
		
		try {
			Scanner sc = new Scanner(f);
			
			while(sc.hasNext()) {
				String line = sc.nextLine();
				
				String[] werte = line.split("[|]");
				
				
				String name = werte[1];
				int nr = Integer.valueOf(werte[0]);
				double preis = Double.parseDouble(werte[2]);
				int menge = Integer.parseInt(werte[3]);
				String kategorie = werte[4];
				
				
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
			
			
		
		
	}
	
	public void mengeErhoehen(int nr, int anzahl) {
	
		for(int i : bestand.keySet()) {
			Artikel k = bestand.get(i);
			if(k.getArtikelNr() == nr) {
				k.setMenge(k.getMenge() + anzahl);
				
			}
		}
		
		
	}
	
	public void mengeErhoehen(String name, int anzahl) {
		
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name)) {
				k.setMenge(k.getMenge() + anzahl);
				
			}	
		}
	}
	
	public boolean artikelExists(String name) {
		for(Artikel k : bestand.values()) {
			if(k.getArtikelName().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	
	
	
}
