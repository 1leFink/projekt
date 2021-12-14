package hft.projekt;

public class Artikel {
	
	private String artikelName;
	private int artikelNr;
	private double preis;
	private int menge;
	private boolean verfuegbarkeit;
	
	public Artikel(String artikelName, int artikelNr, double preis,int menge, boolean verfuegbarkeit) {
		super();
		this.artikelName = artikelName;
		this.artikelNr = artikelNr;
		this.preis = preis;
		this.menge = menge;
		this.verfuegbarkeit = verfuegbarkeit;
	}

	public String getArtikelName() {
		return artikelName;
	}

	public void setArtikelName(String artikelName) {
		this.artikelName = artikelName;
	}

	public int getArtikelNr() {
		return artikelNr;
	}

	public void setArtikelNr(int artikelNr) {
		this.artikelNr = artikelNr;
	}

	public double getPreis() {
		return preis;
	}

	public void setPreis(double preis) {
		this.preis = preis;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public boolean isVerfuegbarkeit() {
		return verfuegbarkeit;
	}

	public void setVerfuegbarkeit(boolean verfuegbarkeit) {
		this.verfuegbarkeit = verfuegbarkeit;
	}
	
	

}
