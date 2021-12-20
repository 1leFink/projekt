package hft.projekt;

public class Artikel {
	
	private String artikelName;
	private int artikelNr;
	private double preis;
	private int menge;
	private String haltbarkeit;
	private boolean verfuegbarkeit;
	private String kategorie;
	
	public Artikel(String artikelName, int artikelNr, double preis,int menge, String haltbarkeit, String kategorie) {
		super();
		this.artikelName = artikelName;
		this.artikelNr = artikelNr;
		this.preis = preis;
		this.menge = menge;
		this.haltbarkeit = haltbarkeit;
		this.kategorie = kategorie;
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

	public String getHaltbarkeit() {
		return haltbarkeit;
	}

	public void setHaltbarkeit(String haltbarkeit) {
		this.haltbarkeit = haltbarkeit;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}
	
	

}
