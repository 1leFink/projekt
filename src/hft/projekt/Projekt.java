package hft.projekt;

public class Projekt{

	static boolean opened = false;
	
	//main methode - start
	public static void main(String[] args) {
		run();
		
	}

	/**
	 * run() dient als Anfang des Programs und initialisiert eine Kundenverwaltung oder Lager (falls diese vorhanden sind).
	 * Wird das Programm zum ersten mal gestartet, werden eine neue Kundenverwaltung und Lager erstellt.
	 * Abschließend wird die methode 'befehleEinlesen()' in 'Befehle' aufgerufen und der Nutzer kann Kommandos eingeben.
	 */
	public static void run() {
		//Check ob das Programm die run() methode das erste mal seit dem letzen Start ausgeführt hat. Nur wenn das der Fall ist soll das Banner erneut angezeigt werden.
		if(opened == false) {
			System.out.println(Misc.BANNER.getWert());
			opened = true;
			System.out.println("Fuer eine liste der befehle geben sie 'help' ein. Um das Program zu schliessen, geben Sie 'quit' ein.");

		}
		
		Kundenverwaltung verwaltung;
		Lager lager;
		
		//initalisierung Kundenverwaltung --> falls vorhanden
		if(Speicherverwaltung.firstStart() == true) {
			verwaltung = new Kundenverwaltung();
			Speicherverwaltung.saveKundenverwaltung(verwaltung);
			
		//erstellen einer neuen Kundenverwaltung --> falls nicht vorhanden
		}else {
			verwaltung = Speicherverwaltung.loadKundenverwaltung();
		}
		
		//initalisierung Lager --> falls vorhanden
		if(Speicherverwaltung.lagerExists() == true) {
			lager = Speicherverwaltung.loadLager();

		//erstellen einer neuen Lagers --> falls nicht vorhanden
		}else {
			lager = new Lager();
			Speicherverwaltung.saveLager(lager);
		}
		
		Befehle.befehlEinlesen();
	
	}
	
}
