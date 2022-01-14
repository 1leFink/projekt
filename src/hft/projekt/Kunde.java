package hft.projekt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Kunde implements Serializable{

	protected String name;
	protected int kundennr;
	protected LocalDate beitrittsdatum;
	protected HashMap<Integer, Auftrag> auftraege;
	
	//Kunstruktor: verlangt nur einen Namen für den Kunden
	public Kunde(String name) {
		super();
		this.name = name;
		this.kundennr = kundenNummerErstellen();
		this.beitrittsdatum = LocalDate.now();
		this.auftraege = new HashMap<Integer, Auftrag>();
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKundennr() {
		return kundennr;
	}
	public void setKundennr(int kundennr) {
		this.kundennr = kundennr;
	}
	public HashMap<Integer, Auftrag> getAuftraege() {
		return auftraege;
	}
	public void setAuftraege(HashMap<Integer, Auftrag> auftraege) {
		this.auftraege = auftraege;
	}
	public LocalDate getBeitrittsdatum() {
		return beitrittsdatum;
	}
	public void setBeitrittsdatum(LocalDate beitrittsdatum) {
		this.beitrittsdatum = beitrittsdatum;
	}

	
	//displayInf() gibt informationen über einen Kunden aus
	public void displayInfo() {
		String padded1 = String.format("%1$-20s", "Name:");
		String padded2 = String.format("%1$-20s", "Kundennummer:");
		String padded3 = String.format("%1$-20s", "Kunde seit:");
		String padded4 = String.format("%1$-20s", "Auftraege");
		System.out.println("---------------------INFO--------------------------");
		System.out.print(padded1);
		System.out.printf("%s%n", name);
		System.out.print(padded2);
		System.out.printf("%s%n", kundennr);
		System.out.print(padded3);
		System.out.printf("%s%n", beitrittsdatum.toString());
		System.out.print(padded4);
		System.out.printf("%s%n", auftraege.size());
		System.out.println("---------------------------------------------------");
	}
	
	/**
	 * 
	 * @param artikel, liste der Artikel die zu dem Auftrag gehören
	 * @return {@code true} falls Artikel exisiteren und Auftrag erstellt werden kann
	 */

	public boolean bestellen(List<Artikel> artikel) {
			
		Lager l = Speicherverwaltung.loadLager();
		
		for(Artikel k : artikel) {
			
			//Kontrolle ob Artikel existiert, Wenn true --> Menge des Artikels reduzieren, bei fehlern wird false zurueckgegeben
			if(l.artikelExists(k.getArtikelName())) {
				if(l.mengeReduzieren(k.getArtikelName(), k.getMenge()) == true){
				}else {
					return false;
				}
			}else {
				System.out.println("Operation nicht erfolgreich: Artikel konnte nicht gefunden werden");
				return false;
			}
		}
		
		//Auftrag wird erstellt und dem Kunden hinzugefuegt
		Auftrag auf = new Auftrag(artikel);
		auftraege.put(auf.getAuftragsNr(), auf);

		//Änderungen am Lager werden gespeichert und true zurueckgegeben
		Speicherverwaltung.saveLager(l);
		return true;
		
	}

	//kundenNummerErstellen() erstellt eine zufällige, für jeden Kunden individuelle Kundennummer
	public int kundenNummerErstellen() {
		Random rand = new Random();
		int[] ziffern = {0,1,2,3,4,5,6,7,8,9};
		
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		
		//Block wird erneut ausgeführt wenn bereits ein Kunde mit der erstellten Kundennummer existiert
		while(true) {
		String num = "";
		boolean duplicate = false;
		
		//erste Ziffer wird erstellt und darf nicht 0 sein
		num = num + ziffern[rand.nextInt(9)+1];
		//die restlichen 4 Ziffern werden zufällig ausgewählt und num angehängt
		for(int i = 0; i<4; i++) {
			int index = rand.nextInt(10);
			num = num + ziffern[index];
		}
		//Kontrolle ob einer der Kunden bereits die Kundennummer besitzt, Wenn ja --> duplicate = true -> Schleife wiederholt sich
		for(int i : k.getKunden().keySet()) {
			if(i == Integer.parseInt(num)) {
				duplicate = true;
				
			}
		}
		//Wenn duplicate false geblieben ist, wird die generierte Nummer zurückgegeben
		if(duplicate == false) {
			return Integer.parseInt(num);	
		}
	}
	
	
	}

	//Alle Comperator zur Sortierung der Kunden mit dem Befehl 'listKunde'
	
	public static Comparator<Kunde> compareByName(){
		return new Comparator<Kunde>() {
			@Override
			public int compare(Kunde k1,Kunde k2) {
				return(k1.name.compareTo(k2.name));
			}
		};
	}

	public static Comparator<Kunde> compareByNr(){
		return new Comparator<Kunde>() {
			@Override
			public int compare(Kunde k1,Kunde k2) {
				return(k1.kundennr - k2.kundennr);
			}
		};
	}
	public static Comparator<Kunde> compareByAuftraege(){
		return new Comparator<Kunde>() {
			@Override
			public int compare(Kunde k1,Kunde k2) {
				return(k1.auftraege.size() - k2.auftraege.size());
			}
		};
	}
	
	
}
