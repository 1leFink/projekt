package hft.projekt;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Random;

public class Kunde implements Serializable{

	protected String name;
	protected int kundennr;
	protected LocalDate beitrittsdatum;
	protected HashMap<Integer, Auftrag> auftraege;
	
	
	
	
	public Kunde(String name) {
		super();
		this.name = name;
		this.kundennr = kundenNummerErstellen();
		this.beitrittsdatum = LocalDate.now();
		
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
	
	
	public void displayInfo() {
		String padded1 = String.format("%1$-20s", "Name:");
		String padded2 = String.format("%1$-20s", "Kundennummer:");
		String padded3 = String.format("%1$-20s", "Kunde:");
		System.out.println("---------------------INFO--------------------------");
		System.out.print(padded1);
		System.out.printf("%s%n", name);
		System.out.print(padded2);
		System.out.printf("%s%n", kundennr);
		System.out.print(padded3);
		System.out.printf("%s%n", beitrittsdatum.toString());
		System.out.println("---------------------------------------------------");
	}
	
	public int kundenNummerErstellen() {
		Random rand = new Random();
		int[] ziffern = {0,1,2,3,4,5,6,7,8,9};
		Kundenverwaltung k = Speicherverwaltung.loadKundenverwaltung();
		String num = "";
		boolean duplicate = false;
	
		
		while(true) {
			
		
		for(int i = 0; i<5; i++) {
			int index = rand.nextInt(10);
			num = num +""+ ziffern[index];
		}
		
		//num ="48585";
		
		for(int i : k.getKunden().keySet()) {
			if(i == Integer.valueOf(num)) {
				duplicate = true;
			}
		}
		
		if(duplicate == false) {
			return Integer.valueOf(num);
		}
	}
	
	}
	
	
	
}
