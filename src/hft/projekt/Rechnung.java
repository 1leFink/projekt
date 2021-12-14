package hft.projekt;

import java.util.List;

public class Rechnung {
	
	private double gesamtpreis;
	private int rechnungsnr;
	private List<Artikel> artikel;
	
	public Rechnung(List<Artikel> artikel) {
		super();
		this.gesamtpreis = gesamtpreisBerechnen();
	}
	
	public double gesamtpreisBerechnen() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		
		double i = 0;
		for (int j = 0; j<artikel.size(); j++) {
			i += artikel.get(j).getPreis();
		}
		return i;
	}

	public double getGesamtpreis() {
		return gesamtpreis;
	}

	public void setGesamtpreis(double gesamtpreis) {
		this.gesamtpreis = gesamtpreis;
	}

	public int getRechnungsnr() {
		return rechnungsnr;
	}

	public void setRechnungsnr(int rechnungsnr) {
		this.rechnungsnr = rechnungsnr;
	}

	public List<Artikel> getArtikel() {
		return artikel;
	}

	public void setArtikel(List<Artikel> artikel) {
		this.artikel = artikel;
	}
	
	

}
