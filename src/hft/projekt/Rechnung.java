package hft.projekt;

import java.time.LocalDate;
import java.util.List;

public class Rechnung {
	
	private double gesamtpreis;
	private int rechnungsnr;
	private LocalDate rechnungsdatum;
	private List<Artikel> artikel;
	
	public Rechnung(List<Artikel> artikel) {
		super();
		this.gesamtpreis = gesamtpreisBerechnen();
		this.rechnungsdatum = LocalDate.now();
	}
	
	public double gesamtpreisBerechnen() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		Lager l = Speicherverwaltung.loadLager();
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
	
	String s1 = String.format("-------------------------------------------------------------------------");
	String s21 = String.format("%1$-40s", " ____ _  _  __ ____ ____ ____ _  _");
	String s22 = String.format("%1$-40s", "/ ___/ )( \\/  (  _ (  _ (  __( \\/ )");
	String s23 = String.format("%1$-40s", "\\___ ) __ (  O ) __/) __/) _) )  ( ");
	String s24 = String.format("%1$-40s", "(____\\_)(_/\\__(__) (__) (____(_/\\_)");
	String s31 = String.format("%1$-20s", "Name:");
	String s32 = String.format("%1$-20s", "Kundennummer:");
	String s33 = String.format("%1$-20s", "Rechnungsdatum:");
	String s34 = String.format("%1$-20s", "Rechnungsnummer:");
	String s41 = String.format("%s \r\n", "Florian");
	String s42 = String.format("%s \r\n", "46284");
	String s43 = String.format("%s \r\n", rechnungsdatum.toString());
	String s44 = String.format("%s \r\n", "9231");

	
	@Override
	public String toString() {
		return s1 + "\n" + s21 + s31 + s41 + s22 + s32 + s42 + s23 + s33 + s43 + s24 + s34 + s44 + "\n\n\n" + s1;
	}
	
	

}
