package Domini.Aplicacio.Drivers;

import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import Domini.Aplicacio.Stubs.EntitatStubIEntitatDomini;
import Domini.Controladors.CtrlDominiMantGeneric;

public class DriverCtrlDominiMantGeneric {
	CtrlDominiMantGeneric<EntitatStubIEntitatDomini> c;
	static Scanner reader;
	
	public void testConstructor() {
		System.out.println("TEST CONSTRUCTOR");
		c = new CtrlDominiMantGeneric<EntitatStubIEntitatDomini>(new EntitatStubIEntitatDomini());
		System.out.println("TEST PASSAT");
	}
	public void testConsultaEntitats() {
		System.out.println("TEST testConsultaEntitats");
		Vector<String> v = c.consultaEntitats();
		System.out.println("TEST PASSAT, llistant dades entitats");
		for (String s : v) {
			System.out.println("- " + s);
		}
	}
	public void testGetKeys() {
		System.out.println("TEST testGetKeys");
		
		Set<Integer> s = c.getKeys();
		System.out.println("TEST PASSAT, llistant les claus de les entitats");
		for(Integer i : s) {
			System.out.println("- " + i.intValue());
		}
	}
	public void testGetStringEntitat() {
		System.out.println("TEST testGetStringEntitat");
		System.out.println("Introdueïx la id de la entitat per obtindre les dades: ");
		int id = reader.nextInt();
		String d = c.getStringEntitat(id);
		if (d == null)
			System.out.println("TEST PASSAT, però no existeix la entitat.");
		else
			System.out.println("TEST PASSAT, dades entitat: " + d);
	}
	public void testAltaEntitat() {
		System.out.println("TEST testAltaEntitat");
		System.out.println("Introdueïx una línia amb el format següent (format concret per aquesta classe EntitatStubIEntitatDomini:");
		System.out.println("id param");
		System.out.println("on id és un valor númeric més gran que 0 i param una string qualsevol sense espais.");
		reader.nextLine();
		String s = reader.nextLine();
		int exitCode = c.altaEntitat(Integer.valueOf(s.split(" ")[0]), s);
		if (exitCode == 0)
			System.out.println("TEST PASSAT, cap error");
		else if (exitCode == 1)
			System.out.println("TEST NO PASSAT, ja existeix la entitat");
		else if (exitCode == 2)
			System.out.println("TEST NO PASSAT, el paràmetre id és diferent al de la string");
		else if (exitCode == 3)
			System.out.println("TEST NO PASSAT, hi ha hagut algun error.");
	}
	public void testAltaNovaEntitat() {
		System.out.println("TEST testAltaNovaEntitat");
		System.out.println("Introdueïx un identificador numèric: ");
		int id = reader.nextInt();
		System.out.println("Intrdueïx una cadena de caràcters sense espais com a paràmetre: ");
		String s = reader.next();
		Vector<String> v = new Vector<String>();
		v.add(s);
		int exitCode = c.altaNovaEntitat(id, v);
		if (exitCode == 0)
			System.out.println("TEST PASSAT, cap error.");
		else if (exitCode == 1)
			System.out.println("TEST NO PASSAT, ja existeix una entitat amb aquesta id");
		else if (exitCode == 2)
			System.out.println("TEST NO PASSAT, el paràmetre id és diferent al de la string");
		else if (exitCode == 3)
			System.out.println("TEST NO PASSAT, hi ha hagut algun error");
		else if (exitCode == 4)
			System.out.println("TEST NO PASSAT, el vector de paràmetres és incorrecte.");
	}
	public void testBaixaEntitat() {
		System.out.println("TEST testBaixaEntitat");
		System.out.println("Introdueïx un identificador numèric: ");
		int id = reader.nextInt();
		int exitCode = c.baixaEntitat(id);
		if (exitCode == 0)
			System.out.println("TEST PASSAT, s'ha donat de baixa correctament");
		else if (exitCode == 1)
			System.out.println("TEST NO PASSAT, no existeix una entitat amb aquest identificador.");
	}
	public void testExisteixEntitat() {
		System.out.println("TEST testAltaNovaEntitat");
		System.out.println("Introdueïx un identificador numèric: ");
		int id = reader.nextInt();
		if (c.existeixEntitat(id))
			System.out.println("TEST PASSAT, existeix la entitat");
		else
			System.out.println("TEST PASSAT, no existeix la entitat");
	}
	public void testGetLastIdentifier() {
		System.out.println("TEST testGetLastIdentifier");
		int id = c.getLastIdentifier();
		if (id == -1)
			System.out.println("TEST PASSAT, però no hi han entitats donades d'alta");
		else
			System.out.println("TEST PASSAT, últim identificador és: " + id);
	}
	
	public static void main(String[] args) {
		reader =  new Scanner(System.in);
		
		DriverCtrlDominiMantGeneric d = new DriverCtrlDominiMantGeneric();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testConstructor");
			System.out.println("2. testConsultaEntitats");
			System.out.println("3. testGetKeys");
			System.out.println("4. testGetStringEntitat");
			System.out.println("5. testAltaEntitat");
			System.out.println("6. testAltaNovaEntitat");
			System.out.println("7. testBaixaEntitat");
			System.out.println("8. testExisteixEntitat");
			System.out.println("9. testGetLastIdentifier");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testConsultaEntitats();
				break;
			case 3:
				d.testGetKeys();
				break;
			case 4:
				d.testGetStringEntitat();
				break;
			case 5:
				d.testAltaEntitat();
				break;
			case 6:
				d.testAltaNovaEntitat();
				break;
			case 7:
				d.testBaixaEntitat();
				break;
			case 8:
				d.testExisteixEntitat();
				break;
			case 9:
				d.testGetLastIdentifier();
			}
		}
	}
}
