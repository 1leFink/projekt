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

	
	final static String path = Misc.SAVE_PATH.getWert();

	
	public static Kundenverwaltung loadKundenverwaltung() {
		
		try {
			FileInputStream fileIn = new FileInputStream(path);
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
			FileOutputStream fOut = new FileOutputStream(path);
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

	public static boolean firstStart() {
		
		File file = new File(path);
		if(file.exists()) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
}
