package hft.projekt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class Auftrag implements Serializable{

	protected int auftragsNr;
	protected LocalDate auftragsdatum;
	protected List<Artikel> artikelListe;
	private Rechnung rechnung;
	
	public Auftrag(List<Artikel> artikelListe) {
		super();
		this.artikelListe = artikelListe;
		this.auftragsdatum = LocalDate.now();
		this.auftragsNr = auftragsNummerErstellen();
	}
	
	public void gesamtpreisBerechnen() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		double i = 0;
		for (int j = 0; ; j++) {
			
		}
	}
	
	public void bestellen(List<Artikel> artikel) {
		Rechnung rechnung = new Rechnung(artikel);
		
	}
	
	
	public int getAuftragsNr() {
		return auftragsNr;
	}

	public LocalDate getAuftragsdatum() {
		return auftragsdatum;
	}

	public List<Artikel> getArtikelListe() {
		return artikelListe;
	}

	public void setAuftragsNr(int auftragsNr) {
		this.auftragsNr = auftragsNr;
	}

	public void setAuftragsdatum(LocalDate auftragsdatum) {
		this.auftragsdatum = auftragsdatum;
	}

	public void setArtikelListe(List<Artikel> artikelListe) {
		this.artikelListe = artikelListe;
	}

	public int auftragsNummerErstellen() {
		
		Random r = new Random();
		int[] ziffern = {0,1,2,3,4,5,6,7,8,9};
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		String numRan = "";
		boolean flag = false;
		
		while(true) {
			for(int i = 0; i<4; i++) {
				int index = r.nextInt(10);
				numRan = numRan +""+ ziffern[index];
			}
			for(int i : k.getKunden().keySet()) {
				if(i == Integer.valueOf(numRan)) {
					flag = true;
				}
			}
			if(flag == false) {
			return Integer.valueOf(numRan);
			}
		}
	
	}
	
}
