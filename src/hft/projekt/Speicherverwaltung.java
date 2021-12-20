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

	
	final static String kundenpath = Misc.KSAVE_PATH.getWert();
	final static String lagerpath = Misc.LSAVE_PATH.getWert();

	
	public static Kundenverwaltung loadKundenverwaltung() {
		
		try {
			FileInputStream fileIn = new FileInputStream(kundenpath);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			
			Kundenverwaltung k = (Kundenverwaltung) objIn.readObject();
			
			fileIn.close();
			objIn.close();
			
			return k;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}
	
	
	public static void saveKundenverwaltung(Kundenverwaltung m) {
		
		try {
			FileOutputStream fOut = new FileOutputStream(kundenpath);
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			
			objOut.writeObject(m);
			
			fOut.close();
			objOut.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Kundenverwaltung konnte nicht gespeichert werden: Datei nicht gefunden");
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Kundenverwaltung konnte nicht gespeichert werden: Fehler beim Lesen/Schreiben");
	
			e.printStackTrace();
		}
	
	}
	
	public static void saveLager(Lager l) {
		
		try {
			FileOutputStream fOut = new FileOutputStream(lagerpath);
			ObjectOutputStream objOut = new ObjectOutputStream(fOut);
			
			objOut.writeObject(l);
			
			fOut.close();
			objOut.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Lager konnte nicht gespeichert werden: Datei nicht gefunden");
			
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Lager konnte nicht gespeichert werden: Fehler beim Lesen/Schreiben");
	
			e.printStackTrace();
		}
	
	}


	public static Lager loadLager() {
		
		try {
			FileInputStream fileIn = new FileInputStream(lagerpath);
			ObjectInputStream objIn = new ObjectInputStream(fileIn);
			
			Lager l = (Lager) objIn.readObject();
			
			fileIn.close();
			objIn.close();
			
			return l;
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
		
	}

	public static boolean firstStart() {
		
		File file = new File(kundenpath);
		if(file.exists()) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
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
