package Domini.Aplicacio.Drivers;

import java.util.Scanner;
import java.util.Vector;

import Domini.Joc.JocActual;
import Domini.Joc.JocBreaker;
import Domini.Joc.JocMaster;


public class DriverControladorJocBreaker {

	/**
	 * Testeja inicialitzarCombinacio (de jocBreaker)
	 */
	public void testInicialitzarCombinacio() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		JocBreaker jugar = new JocBreaker();
		Vector<Integer> combGenerada = jugar.inicialitzarCombinacio(numFitxes, numColors);
		System.out.println(combGenerada);
	}

	/**
	 * Testeja introduirCombinacio (de jocBreaker)
	 */
	public void testIntroduirCombinacio() {
		JocBreaker jugar = new JocBreaker();
		Scanner sc = new Scanner(System.in);
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		System.out.println("introdueix combinacio inicial");
		Vector<Integer> combinacioIni = new Vector<Integer>();
		for(int i = 0; i<numFitxes; i++) {
			int nouElem = sc.nextInt();
			combinacioIni.add(nouElem);
		}
		jugar.introduirCombinacioAmagada(combinacioIni);
		System.out.println("1 si vols ajuts qualsevol altre cosa si no");
		int ajutsInt = sc.nextInt();
		boolean ajuts = false;
		if(ajutsInt == 1)ajuts = true;
		Vector<Integer> combinacio = new Vector<Integer>();
		System.out.println("introdueix la teva combinaci�");
		for(int j = 0; j<numFitxes; j++) {
			int nouElem2 = sc.nextInt();
			combinacio.add(nouElem2);
		}
		jugar.setnPosicions(numFitxes);
		jugar.setnColors(numColors);
		jugar.setAjuts(ajuts);
		jugar.introduirCombinacioAmagada(combinacioIni);
		jugar.setCombinacioIntroduida(true);
		jugar.introduirCombinacio(combinacio);
		Vector<Integer> Combinacio2 = jugar.obtenirUltimaCombinacio();
		Vector<Integer> Resposta = jugar.obtenirUltimaPista();
		System.out.print("S'ha introduit al tauler: ");
		System.out.print(Combinacio2);
		System.out.print(", i les pistes: ");
		System.out.println(Resposta);
	}
	
	/**
	 * Testeja codeBreakAjuts (de jocBreaker)
	 */
	public void testCodeBreakAjuts() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		JocBreaker jugar = new JocBreaker();
		System.out.println("Introdueix la primera combinacio");
		Vector<Integer> combinacio = new Vector<Integer>();
		for(int j = 0; j<numFitxes; j++) {
			int nouElem2 = sc.nextInt();
			combinacio.add(nouElem2);
		}
		System.out.println("Introdueix la segona combinacio");
		Vector<Integer> combinacio2 = new Vector<Integer>();
		for(int i = 0; i<numFitxes; i++) {
			int nouElem2 = sc.nextInt();
			combinacio2.add(nouElem2);
		}
		Vector<Integer> combGenerada = jugar.codeBreakAjuts(numColors, numFitxes, combinacio2, combinacio);
		System.out.println(combGenerada);
	}
	/**
	 * Men
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner reader =  new Scanner(System.in);
		
		DriverControladorJocBreaker d = new DriverControladorJocBreaker();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testInicialitzarCombinacio");
			System.out.println("2. testIntroduirCombinacio");
			System.out.println("3. testCodeBreakAjuts");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testInicialitzarCombinacio();
				break;
			case 2:
				d.testIntroduirCombinacio();
				break;
			case 3:
				d.testCodeBreakAjuts();
				break;
			}
		}
	}
	
}
