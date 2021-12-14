package hft.projekt;

public class Rechnung {
	
	private double gesamtpreis;
	private int rechnungsnr;
	
	public Rechnung() {
		super();
		this.gesamtpreis = gesamtpreisBerechnen();
	}
	
	public double gesamtpreisBerechnen() {
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		double i = 0;
		for (int j = 0; ; j++) {
			
		}
	}
	
	

}
