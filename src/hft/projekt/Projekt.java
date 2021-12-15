package hft.projekt;

public class Projekt{

	static boolean opened = false;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		run();
	}

	public static void run() {
		
		
		if(opened == false) {
			System.out.println(Misc.BANNER.getWert());
			opened = true;
		}
		
		
		
		if(Speicherverwaltung.firstStart() == true) {
			Kundenverwaltung verwaltung = new Kundenverwaltung();
			Speicherverwaltung.saveKundenverwaltung(verwaltung);
			
			
		}else {
			Kundenverwaltung verwaltung = Speicherverwaltung.loadKundenverwaltung();
		}
		


		
		Befehle.befehlEinlesen();
	
	}
	
}
