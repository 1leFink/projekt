package hft.projekt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
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
		System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n%n", "Name", "Nr.", "Preis", "Menge", "Haltbarkeit", "Kategorie");
		for(Artikel k : bestand.values()) {
			
			System.out.printf("%-15s%-15s%-15s%-15s%-15s%-15s%n", k.getArtikelName(), Integer.toString(k.getArtikelNr()), k.getPreis() +"€", k.getMenge(), k.getHaltbarkeit(), k.getKategorie());
	
		}
		System.out.println("----------------------------------------------------------------------------------------");
		
	}
	
	public void bestandEinlesen() {
		
		//C:\\Users\\Leon\\Desktop\\save\\bestand.txt
		File f = new File("bestand.txt");
		try {
			Scanner sc = new Scanner(f);
			
			while(sc.hasNext()) {
				String line = sc.nextLine();
				
				String[] werte = line.split("[|]");
				
				
				String name = werte[1];
				int nr = Integer.valueOf(werte[0]);
				double preis = Double.parseDouble(werte[2]);
				int menge = Integer.parseInt(werte[3]);
				String haltbarkeit = werte[4];
				String kategorie = werte[5];
				
				Artikel k = new Artikel(name, nr, preis, menge, haltbarkeit, kategorie);
				
				bestand.put(Integer.valueOf(werte[0]), k);
				
			}
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
