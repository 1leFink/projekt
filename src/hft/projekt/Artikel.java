package hft.projekt;

public class Artikel {
	
	private String artikelName;
	private int artikelNr;
	private double preis;
	private int menge;
	private boolean verf�gbarkeit;
	
	public Artikel(String artikelName, int artikelNr, double preis,int menge, boolean verf�gbarkeit) {
		super();
		this.artikelName = artikelName;
		this.artikelNr = artikelNr;
		this.preis = preis;
		this.menge = menge;
		this.verf�gbarkeit = verf�gbarkeit;
	}
	
	

}
