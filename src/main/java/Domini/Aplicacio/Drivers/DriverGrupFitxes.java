package Domini.Aplicacio.Drivers;

import java.util.Scanner;

import Domini.Joc.GrupFitxes;

public class DriverGrupFitxes {
	static Scanner reader;
	GrupFitxes gf;
	
	public void testConstructor() {
		System.out.println("TEST Constructor");
		System.out.println("0. Constructor sense parametre");
		System.out.println("1. Constructor amb parametre numero de posicions");
		int id = reader.nextInt();
		if (id == 0) {
			gf = new GrupFitxes();
			System.out.println("TEST PASSAT");
		} else if (id == 1) {
			System.out.println("Introdueix el numero de posicions de la combinacio:");
			id = reader.nextInt();
			gf = new GrupFitxes(id);
			System.out.println("Combinaci de " + gf.getnPosicions() + " fitxes.");
			System.out.println("TEST PASSAT");
		} else
			System.out.println("Opcio incorrecte, sortint del test.");
	}
	
	public void testAfegirFitxes() {
		System.out.println("TEST Afegir Fitxes");
		System.out.println("Introdueix el numero de posicions de la combinacio:");
		int n = reader.nextInt();
		gf = new GrupFitxes(n);
		System.out.println("Introdueix totes les fitxes de la combinacio (0 a nPosicions-1)");
		int i = 0;
		while (i < n) {
			System.out.println("Introdueix el color de la fitxa");
			int c = reader.nextInt();
			gf.afegirFitxa(c);
			System.out.println("Fitxa afegida correctament");
			i++;
		}
		System.out.println("TEST PASSAT");
	}
	
	public void testObtencioDades() {
		System.out.println("TEST Obtenir dades combinacio");
		System.out.println("Combinacio de 4 fitxes");
		gf = new GrupFitxes(4);
		System.out.println("Mida: " + gf.obtenirMida());
		gf.afegirFitxa(0);
		gf.afegirFitxa(3);
		gf.afegirFitxa(1);
		gf.afegirFitxa(2);
		System.out.println("Fitxes afegides via codi");
		System.out.println("Mida: " + gf.obtenirMida());
		System.out.println("Obtenir combinacio");
		System.out.println(gf.obtenirCombinacio());
		System.out.println("Introdueix una posicio per saber quina fitxa hi ha:");
		int n = reader.nextInt();
		if (n < 0 || n > gf.obtenirMida()-1) {
			System.out.println("Error en el numero");
			System.out.println("TEST NO PASSAT");
		} else {
			System.out.println(gf.obtenirColor(n));
			System.out.println("TEST PASSAT");
		}
	}
	
	public void testConvertirToString() {
		System.out.println("TEST convertir to string");
		gf = new GrupFitxes(4);
		gf.afegirFitxa(0);
		gf.afegirFitxa(3);
		gf.afegirFitxa(1);
		gf.afegirFitxa(2);
		System.out.println(gf.convertirToString());
		System.out.println("TEST PASSAT");
	}
	
	public void testObtenirFromString() {
		System.out.println("TEST obtenir from string");
		String s = "4%%0%%3%%1%%2";
		System.out.println("String d'on obtindrem: " + s);
		gf = new GrupFitxes();
		gf.obtenerFromString(s);
		System.out.println("Grup de fitxes creat");
		if (gf.convertirToString().equals(s))
			System.out.println("TEST PASSAT. Conversio correcte.");
		else
			System.out.println("TEST NO PASSAT. Conversio incorrecte.");
	}
	
	
	public static void main(String[] args) {
		reader =  new Scanner(System.in);
		DriverGrupFitxes d = new DriverGrupFitxes();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testConstructor");
			System.out.println("2. testAfegirFitxes");
			System.out.println("3. testObtencioDadesCombinacio");
			System.out.println("4. testConvertirToString");
			System.out.println("5. testObtenerFromString");
			
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testAfegirFitxes();
				break;
			case 3:
				d.testObtencioDades();
				break;
			case 4:
				d.testConvertirToString();
				break;
			case 5:
				d.testObtenirFromString();
				break;
			}			
		}
	}
}
