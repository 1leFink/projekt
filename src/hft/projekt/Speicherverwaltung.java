package hft.projekt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Speicherverwaltung implements Serializable{

	//Konstanten Dateipfade zu den serialisierten Objekten
	final static String kundenpath = Misc.KSAVE_PATH.getWert();
	final static String lagerpath = Misc.LSAVE_PATH.getWert();

	
	/**
	 * 
	 * @return Kundenverwaltung, Kundenverwaltung mit all ihren referenzierten Objekten (gesamten Kunden)
	 */
	public static Kundenverwaltung loadKundenverwaltung() {
		

		//Moegliche Fehler abfangen
		try {
			//Inputstreams erzeugen
			FileInputStream fileIn = new FileInputStream(kundenpath);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			
			//Deserialisierung
			Kundenverwaltung k = (Kundenverwaltung) objIn.readObject();
			
			//InputStreams schließen
			fileIn.close();
			objIn.close();
			
			//Kundenverwaltung zurueckgeben
			return k;
			
		//Exceptions
		} catch (FileNotFoundException e) {
			System.out.println("Kundenverwaltung konnte nicht geladen werden: Datei nicht gefunden");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Kundenverwaltung konnte nicht geladen werden: Fehler beim Lesen/Schreiben");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Kundenverwaltung konnte nicht geladen werden: referenzierte Klasse konnte nicht gefunden werden");
			e.printStackTrace();
		}
		return null;
		
		
	}

	/**
	 * @return Lager, Lager mit allen referenzierten Objekten (Alle Artikel...)
	 */
	public static Lager loadLager() {
		//Moegliche Fehler abfangen
		try {
			//Inputstreams erzeugen
			FileInputStream fileIn = new FileInputStream(lagerpath);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);

			//Deserialisierung
			Lager l = (Lager) objIn.readObject();
			
			//InputStreams schließen
			fileIn.close();
			objIn.close();
			
			//Lager zurueckgeben
			return l;
			
		} catch (FileNotFoundException e) {
			System.out.println("Lager konnte nicht geladen werden: Datei nicht gefunden");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Lager konnte nicht geladen werden: Fehler beim Lesen/Schreiben");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Lager konnte nicht geladen werden: referenzierte Klasse konnte nicht gefunden werden");
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	/**
	 * 
	 * @param m, Kundenverwaltungs Objekt das gespeichert (serialisiert) werden soll
	 */
	public static void saveKundenverwaltung(Kundenverwaltung m) {
		
		//Moegliche Fehler abfangen
		try {
			//OutputStreams erzeugen
			FileOutputStream fOut = new FileOutputStream(kundenpath);
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			
			//Serialisierung
			objOut.writeObject(m);
			
			//OutputStreams schliessen
			fOut.close();
			objOut.close();
			
		//Exceptions
		} catch (FileNotFoundException e) {
			System.out.println("Kundenverwaltung konnte nicht gespeichert werden: Datei nicht gefunden");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Kundenverwaltung konnte nicht gespeichert werden: Fehler beim Lesen/Schreiben");
			e.printStackTrace();
		}
	
	}
	
	/**
	 * 
	 * @param l, Lager Objekt das gespeichert (serialisiert) werden soll
	 */
	public static void saveLager(Lager l) {
		//Moegliche Fehler abfangen
		try {
			//OutputStreams erzeugen
			FileOutputStream fOut = new FileOutputStream(lagerpath);
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			
			//Serialisierung
			objOut.writeObject(l);
			
			//OutputStreams schliessen
			fOut.close();
			objOut.close();

		//Exceptions
		} catch (FileNotFoundException e) {
			System.out.println("Lager konnte nicht gespeichert werden: Datei nicht gefunden");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Lager konnte nicht gespeichert werden: Fehler beim Lesen/Schreiben");
			e.printStackTrace();
		}
	
	}


	/**
	 * 
	 * @return {@code true}, falls das Programm zum ersten Mal gestartet wurde (Kundenverwaltung noch nicht existiert)
	 */
	public static boolean firstStart() {
		
		File file = new File(kundenpath);
		if(file.exists()) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	/**
	 * 
	 * @return {@code true}, falls bereits ein Lager existiert (Lager.txt existiert - serialisiertes Objekt)
	 */
	public static boolean lagerExists() {
		
		File file = new File(lagerpath); 
			if(file.exists()) {
				return true;
			}
			else {
				return false;
			
		}
	}
	
}
