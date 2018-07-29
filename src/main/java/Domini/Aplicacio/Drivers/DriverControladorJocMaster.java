package Domini.Aplicacio.Drivers;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Vector;

import Domini.Joc.Dificultat;
import Domini.Joc.JocActual;
import Domini.Joc.JocMaster;

public class DriverControladorJocMaster {
	
	/**
	 * Testeja IntroduirComb (de jocMaster)
	 */
	public void testIntroduirComb () {
		Scanner sc = new Scanner(System.in);
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		System.out.println("1 si vols ajuts qualsevol altre cosa si no");
		int ajutsInt = sc.nextInt();
		boolean ajuts = false;
		if(ajutsInt == 1)ajuts = true;
		Vector<Integer> combinacio = new Vector<Integer>();
		System.out.println("introdueix la teva combinaci");
		for(int j = 0; j<numFitxes; j++) {
			int nouElem2 = sc.nextInt();
			combinacio.add(nouElem2);
		}
		JocMaster jugar = new JocMaster();
		jugar.setnPosicions(numFitxes);
		jugar.setnColors(numColors);
		jugar.setAjuts(ajuts);
		jugar.IntroduirComb(combinacio);
		Vector<Integer> comb2 = jugar.obtenirUltimaCombinacio();
		Vector<Integer> com = jugar.obtenirCombinacioAmagada();
		System.out.print("La combinacio amagada �s");
		System.out.println(com);
		System.out.print("La combinacio afegida �s: ");

		System.out.println(comb2);
		
	}
	/**
	 * Testeja jugarFacil (de jocMaster)
	 */
	public void testJugaFacil() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		JocMaster jugar = new JocMaster();
		Vector<Integer> comb = jugar.jugaFacil(numColors, numFitxes);
		System.out.print("la combinacio generada s: ");
		System.out.println(comb);
	}
	/**
	 * Testeja crearTotal (de jocMaster)
	 */
	public void testCrearTotal(){
		Scanner sc = new Scanner(System.in);
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		HashMap<Integer, Vector<Integer> > total = new HashMap<Integer, Vector<Integer> >();
		JocMaster jugar = new JocMaster();		
		total = jugar.crearTotal(numFitxes, numColors);
		System.out.print("Aquest s el set Total: ");
		System.out.println(total);
		}
	/**
	 * Testeja getCombinacioInicial (de jocMaster)
	 */
	public void testGetCombinacioInicial() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		JocMaster jugar = new JocMaster();		
		Vector<Integer> combInicial = jugar.getCombinacioInicial(numFitxes);
		System.out.print("Aquesta s la combinacio inicial: ");
		System.out.println(combInicial);				
	}
	/**
	 * Testeja getResposta (de jocMaster)
	 */
	public void testGetResposta() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		Vector<Integer> combinacio = new Vector<Integer>();
		System.out.println("introdueix la teva combinaci");
		for(int i = 0; i<numFitxes; i++) {
			int nouElem = sc.nextInt();
			combinacio.add(nouElem);
		}
		Vector<Integer> combinacio2 = new Vector<Integer>();
		System.out.println("introdueix l'altra combinaci");
		for(int j = 0; j<numFitxes; j++) {
			int nouElem2 = sc.nextInt();
			combinacio2.add(nouElem2);
		}
		JocMaster jugar = new JocMaster();	
		Vector<Integer> Resposta = jugar.getResposta(numFitxes, combinacio, combinacio2);
		System.out.print("Les pistes sn: ");
		System.out.println(Resposta);				
	}
	/**
	 * Testeja tant netejarPossibles com getCombinacio2 (de jocMaster)
	 */
	public void testNetejarPossiblesIGetCombinacio2() {//per facilitar les coses crearem els HashMaps amb la funci crearTotal, ja que sin seria un caos
		Scanner sc = new Scanner(System.in);
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		HashMap<Integer, Vector<Integer> > total = new HashMap<Integer, Vector<Integer> >();
		JocMaster jugar = new JocMaster();		
		total = jugar.crearTotal(numFitxes, numColors);
		HashMap<Integer, Vector<Integer> > possibles = new HashMap<Integer, Vector<Integer> >();	
		possibles = jugar.crearTotal(numFitxes, numColors);
		Vector<Integer> combinacio = new Vector<Integer>();
		System.out.println("introdueix la teva combinaci");
		for(int i = 0; i<numFitxes; i++) {
			int nouElem = sc.nextInt();
			combinacio.add(nouElem);
		}		
		System.out.println("introdueix les pistes que t'hauria donat");
		Vector<Integer> Resposta = new Vector<Integer>();
		int Correctes = sc.nextInt();
		int MalPosades = sc.nextInt();
		Resposta.add(Correctes);
		Resposta.add(MalPosades);
		possibles = jugar.netejarPossibles(numFitxes, Resposta, combinacio, possibles, total);
		System.out.print("possibles ha quedat aix: ");
		System.out.println(possibles);
		Vector<Integer> combinacio2 = jugar.getCombinacio2(numFitxes, Resposta, possibles, total);
		System.out.print("la segent combinacio a provar ser: ");
		System.out.println(combinacio2);
	}

	public void testInicialitzaPossiblesITotal() {
		Scanner sc = new Scanner(System.in);
		JocMaster jugar = new JocMaster();		
		System.out.println("Amb quants colors vols jugar?");
		int numColors = sc.nextInt();
		jugar.setnColors(numColors);
		System.out.println("Quantes fitxes vols posar?");
		int numFitxes = sc.nextInt();
		jugar.setnPosicions(numFitxes);
		System.out.println("Per quin torn anaves?");
		int tornActual = sc.nextInt();
		jugar.setTornActual(tornActual);
		System.out.println("1 si vols ajuts qualsevol altre cosa si no");
		int ajutsInt = sc.nextInt();
		boolean ajuts = false;
		if(ajutsInt == 1)ajuts = true;
		for(int i = 0; i<tornActual;i++) {
			System.out.print("Escriu la combinaci� que hi havia al torn ");
			System.out.println(i);
			Vector<Integer> CombinacioTauler = new Vector<Integer>();
			for(int j = 0; j < numFitxes; j++) {
				int color = sc.nextInt();
				CombinacioTauler.add(color);				
			}
			jugar.afegirCombinacio(CombinacioTauler);
			System.out.print("Escriu les pistes que hi ha guardades a la posici� ");
			System.out.println(i);
			Vector<Integer> Pistes = new Vector<Integer>();
			for(int j = 0; j < numFitxes; j++) {
				int result = sc.nextInt();
				Pistes.add(result);				
			}
			jugar.afegirPista(Pistes, ajuts);
		}
		jugar.inicialitzaPossiblesITotal();
		System.out.print("Total: ");
		System.out.println(jugar.total);
		System.out.print("Possibles: ");
		System.out.println(jugar.possibles);
	}
	
	/**
	 * Men
	 */
	public static void main(String[] args) {
		Scanner reader =  new Scanner(System.in);
		
		DriverControladorJocMaster d = new DriverControladorJocMaster();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testIntroduirComb");
			System.out.println("2. testJugaFacil");
			System.out.println("3. testCrearTotal");
			System.out.println("4. testGetCombinacioInicial");
			System.out.println("5. testGetResposta");
			System.out.println("6. testNetejarPossiblesIGetCombinacio2");
			System.out.println("7. testInicialitzaPossiblesITotal");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testIntroduirComb();
				break;
			case 2:
				d.testJugaFacil();
				break;
			case 3:
				d.testCrearTotal();
				break;
			case 4:
				d.testGetCombinacioInicial();
				break;
			case 5:
				d.testGetResposta();
				break;
			case 6:
				d.testNetejarPossiblesIGetCombinacio2();
				break;
			case 7:
				d.testInicialitzaPossiblesITotal();
				break;
			}
		}
	}
	
	
}
