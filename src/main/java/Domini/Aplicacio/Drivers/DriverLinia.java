package Domini.Aplicacio.Drivers;

import java.util.Scanner;
import java.util.Vector;

import Domini.Joc.Linia;

public class DriverLinia {
	static Scanner reader;
	Linia l;
	
	public void testConstructor() {
		System.out.println("TEST Constructor");
		System.out.println("0. Constructor sense parametre");
		System.out.println("1. Constructor amb parametres");
		int id = reader.nextInt();
		if (id == 0) {
			l = new Linia();
			System.out.println("TEST PASSAT");
		} else if (id == 1) {
			System.out.println("Introdueix el numero de posicions maxim que tindran les combinacions:");
			int n = reader.nextInt();
			System.out.println("Estem en un joc amb ajuts? (true/false)");
			boolean b = reader.nextBoolean();
			l = new Linia(n,b);
			System.out.println("TEST PASSAT");
		} else
			System.out.println("Opcio incorrecte, sortint del test.");
	}
	
	public void testAfegirObtenirCombinacio() {
		System.out.println("TEST Afegir/ObtenirCombinacio");
		l = new Linia(4,false);
		System.out.println("Escriu una combinacio de 4 nombres separats per guions (nMax=6)");
		String s = reader.next();
		String[] ss = s.split("-");
		if (ss.length != l.getnPosicions()) {
			System.out.println("Combinacio no ocupa nPosicons");
			System.out.println("TEST NO PASSAT");
		} else {
			Vector<Integer> v = new Vector<Integer>();
			for (int i=0; i < ss.length;i++) {
				v.add(Integer.valueOf(ss[i]));
			}
			l.afegirCombinacio(v);
			System.out.println("Obtenim la ultima combinacio introduida");
			v = l.obtenirUltimaCombinacio();
			for (int i = 0; i < v.size(); i++) {
				System.out.print(v.get(i));
			}
			System.out.println("");
			System.out.println("TEST PASSAT");
		}
	}
	
	public void testAfegirObtenirPistes() {
		System.out.println("Introdueix les pistes separades per guions (6=Blanc;7=Negre)");
		l = new Linia(4, false);
		String p = reader.next();
		String[] pp = p.split("-");
		if (pp.length > l.getnPosicions()) {
			System.out.println("Combinacio massa llarga");
			System.out.println("TEST NO PASSAT");
		} else {
			Vector<Integer> v = new Vector<Integer>();
			for (int i=0; i < pp.length;i++) {
				v.add(Integer.valueOf(pp[i]));
			}
			l.afegirPistes(v, false);
			v = l.obtenirUltimaPista();
			for (int i = 0; i < v.size(); i++) {
				System.out.print(v.get(i));
			}
			System.out.println("");
			System.out.println("TEST PASSAT");
		}
	}
	
	public void testObtenirLinia() {
		System.out.println("TEST ObtenirLinia");
		l = new Linia(4,false);
		System.out.println("Escriu una combinacio de 4 nombres separats per guions (nMax=6)");
		String s = reader.next();
		String[] ss = s.split("-");
		if (ss.length != l.getnPosicions()) {
			System.out.println("Combinacio no ocupa nPosicions");
			System.out.println("TEST NO PASSAT");
		} else {
			Vector<Integer> v = new Vector<Integer>();
			for (int i=0; i < ss.length;i++) {
				v.add(Integer.valueOf(ss[i]));
			}
			l.afegirCombinacio(v);
		}
		System.out.println("Introdueix les pistes separades per guions (6=Blanc;7=Negre)");
		String p = reader.next();
		String[] pp = p.split("-");
		if (pp.length > l.getnPosicions()) {
			System.out.println("Combinacio massa llarga");
			System.out.println("TEST NO PASSAT");
		} else {
			Vector<Integer> v = new Vector<Integer>();
			for (int i=0; i < pp.length;i++) {
				v.add(Integer.valueOf(pp[i]));
			}
			l.afegirPistes(v, false);
		}
		System.out.println(l.obtenirLinia());
		System.out.println("TEST PASSAT");
	}
	
	public void testConvertirToString() {
		System.out.println("TEST convertir to string");
		l = new Linia(4, false);
		Vector<Integer> comb = new Vector<Integer>();
		comb.add(1);
		comb.add(0);
		comb.add(2);
		comb.add(3);
		l.afegirCombinacio(comb);
		comb.clear();
		comb.add(6);
		comb.add(7);
		l.afegirPistes(comb, false);
		System.out.println(l.convertirToString());
		System.out.println("TEST PASSAT");
	}
	
	public void testObtenirFromString() {
		System.out.println("TEST obtenir from string");
		String s = "4&&false&&true&&4%%1%%0%%2%%3&&6%%7%%";
		System.out.println("String d'on obtindrem: " + s);
		l = new Linia();
		l.obtenerFromString(s);
		System.out.println("Linia creada");
		if (l.convertirToString().equals(s))
			System.out.println("TEST PASSAT. Conversio correcte.");
		else
			System.out.println("TEST NO PASSAT. Conversio incorrecte.");
	}
	
	
	public static void main(String[] args) {
		reader =  new Scanner(System.in);
		DriverLinia d = new DriverLinia();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testConstructor");
			System.out.println("2. testAfegirObtenirCombinacio");
			System.out.println("3. testAfegirObtenirPista");
			System.out.println("4. testObtenirLinia");
			System.out.println("5. testConvertirToString");
			System.out.println("6. testObtenerFromString");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testAfegirObtenirCombinacio();
				break;
			case 3:
				d.testAfegirObtenirPistes();
				break;
			case 4:
				d.testObtenirLinia();
				break;
			case 5:
				d.testConvertirToString();
				break;
			case 6:
				d.testObtenirFromString();
				break;
			}
		}
	}

}
