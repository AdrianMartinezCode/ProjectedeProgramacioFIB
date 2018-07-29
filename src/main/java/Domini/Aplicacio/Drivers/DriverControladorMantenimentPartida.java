package Domini.Aplicacio.Drivers;

import java.util.Scanner;
import java.util.Vector;

import Domini.Controladors.ControladorMantenimentJoc;
import Domini.Controladors.ControladorMantenimentJugador;
import Domini.Controladors.ControladorMantenimentPartida;
import Domini.Joc.Dificultat;
import Persistencia.ControladorPersistencia;

public class DriverControladorMantenimentPartida {
	static Scanner reader;
	ControladorMantenimentPartida c;
	
	public void testConstructor() {
		System.out.println("TEST CONSTRUCTOR i inicialitzacions, s'escollirà un jugador predefinit.");
		ControladorMantenimentJugador cm = new ControladorMantenimentJugador();
		ControladorPersistencia cp = ControladorPersistencia.getInstance();
		cp.inicialitzaPersistencia();
		cm.carregaJugadors();
		cm.carregaPartidesGuardadesAJugadors();
		System.out.println("Codi error crear jugador: " + cm.crearJugador(5, "ProvaJugador"));
		System.out.println("Codi error escollir jugador: " + cm.escollirJugador(5));
		c = new ControladorMantenimentPartida(cm);
		
		ControladorMantenimentJoc cmj = new ControladorMantenimentJoc(c);
		c.setIJocChangeListener(cmj);
		System.out.println("TEST PASSAT");
	}
	
	
	public void testLlistarPartidesGuardades() {
		System.out.println("TEST testLlistarPartidesGuardades");
		Vector<String> v = c.llistarPartidesGuardades();
		if (v == null) {
			System.out.println("TEST NO PASSAT, potser no s'ha carregat el jugador o bé no s'han carregat les partides");
		} else {
			System.out.println("TEST PASSAT");
			System.out.println("Partides guardades:");
			for (String s : v) {
				System.out.println("- " + s);
			}
		}
	}
	
	
	public void testTriarPartidaGuardada() {
		System.out.println("TEST testTriarPartidaGuardada");
		System.out.println("Introdueïx la id de la partida guardada per carregar");
		int id = reader.nextInt();
		int exitCode = c.triarPartidaGuardada(id);
		if (exitCode == 1) {
			System.out.println("TEST NO PASSAT, no existeix la partida amb aquesta id.");
		} else if (exitCode == 2) {
			System.out.println("TEST NO PASSAT, no s'ha escollit el jugador");
		} else {
			System.out.println("TEST PASSAT, s'ha triat la partida correctament.");
		}
	}
	public void testNovaPartidaEstandar() {
		System.out.println("TEST testNovaPartidaEstandar");
		int exitCode = c.novaPartidaEstandar();
		if (exitCode == 1)
			System.out.println("TEST NO PASSAT, la entitat generada automàticament ja existeix..");
		else if (exitCode == -1)
			System.out.println("TEST NO PASSAT, no hi ha jugador escollit");
		else if (exitCode == 3)
			System.out.println("TEST NO PASSAT, hi ha hagut algun error a l'hora de crear la nova partida");
		else if (exitCode == 0)
			System.out.println("TEST PASSAT");
		else if (exitCode == 4)
			System.out.println("TEST NO PASSAT, algun paràmetre predefinit és incorrecte.");
		else
			System.out.println("TEST NO PASSAT, codi: " + exitCode);
	}
	public void testNovaPartidaPersonalitzada() {
		System.out.println("TEST testNovaPartidaPersonalitzada");
		int exitCode = c.novaPartidaPersonalitzada();
		if (exitCode == 1)
			System.out.println("TEST NO PASSAT, la entitat generada automàticament ja existeix..");
		else if (exitCode == -1)
			System.out.println("TEST NO PASSAT, no hi ha jugador escollit");
		else if (exitCode == 3)
			System.out.println("TEST NO PASSAT, hi ha hagut algun error a l'hora de crear la nova partida");
		else if (exitCode == 0)
			System.out.println("TEST PASSAT");
		else if (exitCode == 4)
			System.out.println("TEST NO PASSAT, algun paràmetre predefinit és incorrecte.");
		else
			System.out.println("TEST NO PASSAT, codi: " + exitCode);
	}
	public void testEscollirAjuts() {
		System.out.println("TEST testEscollirAjuts");
		System.out.println("Introdueïx si/no:");
		String r = reader.next();
		boolean ajuts = r == "si";
		int exitCode = c.escollirAjuts(ajuts);
		if (exitCode == 1)
			System.out.println("TEST NO PASSAT, hi ha hagut un error en assignar els ajuts");
		else
			System.out.println("TEST PASSAT");
	}
	public void testEscollirDificultat() {
		System.out.println("TEST testEscollirDificultat");
		System.out.println("Introdueïx: Baixa | Mitjana | Alta");
		Dificultat d = Dificultat.valueOf(reader.next());
		if (c.escollirDificultat(d) == 1)
			System.out.println("TEST NO PASSAT, hi ha hagut un error en assignar els ajuts");
		else
			System.out.println("TEST PASSAT");
	}
	public void testEscollirColors() {
		System.out.println("TEST testEscollirColors");
		System.out.println("Introdueïx num. colors:");
		int colors = Integer.valueOf(reader.next());
		if (c.escollirColors(colors) == 1)
			System.out.println("TEST NO PASSAT, hi ha hagut un error al escollir els colors");
		else
			System.out.println("TEST PASSAT");
	}
	public void testEscollirPosicions() {
		System.out.println("TEST testEscollirPosicions");
		System.out.println("Introdueïx num. posicions:");
		int pos = Integer.valueOf(reader.next());
		if (c.escollirPosicions(pos) == 1)
			System.out.println("TEST NO PASSAT, hi ha hagut un error al escollir les posicions");
		else
			System.out.println("TEST PASSAT");
	}
	public void testEscollirTorns() {
		System.out.println("TEST testEscollirTorns");
		System.out.println("Introdueïx num. torns:");
		int torns = Integer.valueOf(reader.next());
		if (c.escollirTorns(torns) == 1)
			System.out.println("TEST NO PASSAT, hi ha hagut un error al escollir els torns");
		else
			System.out.println("TEST PASSAT");
	}
	public void testEscollirNombreJocs() {
		System.out.println("TEST testEscollirNombreJocs");
		System.out.println("Introdueïx num. jocs:");
		int jocs = Integer.valueOf(reader.next());
		if (c.escollirNombreJocs(jocs) == 1)
			System.out.println("TEST NO PASSAT, hi ha hagut un error al escollir els jocs");
		else
			System.out.println("TEST PASSAT");
	}
	public void testContinuarAlSeguentJoc() {
		System.out.println("TEST testContinuarAlSeguentJoc");
		int exitCode = c.continuarAlSeguentJoc();
		if (exitCode == 0)
			System.out.println("TEST PASSAT");
		else if (exitCode == -1)
			System.out.println("TEST NO PASSAT, no hi ha partida carregada");
		else if (exitCode == 1)
			System.out.println("TEST NO PASSAT, encara no ha acabat el joc");
		else
			System.out.println("TEST NO PASSAT, ja ha acabat la partida.");
	}
	public void testLlistarEstadistiquesPartida() {
		System.out.println("TEST testLlistarEstadistiquesPartida");
		String dades = c.llistarEstadistiquesPartida();
		if (dades == null)
			System.out.println("TEST NO PASSAT, no hi ha partida carregada.");
		else
			System.out.println("TEST PASSAT, dades partida:\n" + dades);
	}
	
	public void testReiniciaPartida() {
		System.out.println("TEST testReiniciaPartida");
		int exitCode = c.reiniciaPartida();
		if (exitCode == 0)
			System.out.println("TEST PASSAT");
		else if (exitCode == 1)
			System.out.println("TEST NO PASSAT, no hi ha partida seleccionada");
		else if (exitCode == 2)
			System.out.println("TEST NO PASSAT, no hi ha jugador escollit");
		else if (exitCode == 3)
			System.out.println("TEST NO PASSAT, ha ocorregut algun error intern");
	}
	
	public void testGuardarPartida() {
		System.out.println("TEST testGuardarPartida");
		int exitCode = c.guardarPartida();
		if (exitCode == 0)
			System.out.println("TEST PASSAT");
		else if (exitCode == 2)
			System.out.println("TEST NO PASSAT, no hi ha partida seleccionada");
		else if (exitCode == 1)
			System.out.println("TEST NO PASSAT, no hi ha jugador escollit");
		else if (exitCode == 3)
			System.out.println("TEST NO PASSAT, ha ocorregut algun error al guardar la partida");
	}
	
	public static void main(String[] args) {
		reader =  new Scanner(System.in);
		
		DriverControladorMantenimentPartida d = new DriverControladorMantenimentPartida();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testConstructor");
			System.out.println("2. testLlistarPartidesGuardades");
			System.out.println("3. testTriarPartidaGuardada");
			System.out.println("4. testNovaPartidaEstandar");
			System.out.println("5. testNovaPartidaPersonalitzada");
			System.out.println("6. testEscollirAjuts");
			System.out.println("7. testEscollirDificultat");
			System.out.println("8. testEscollirColors");
			System.out.println("9. testEscollirPosicions");
			System.out.println("10. testEscollirTorns");
			System.out.println("11. testEscollirNombreJocs");
			System.out.println("12. testContinuarAlSeguentJoc");
			System.out.println("13. testLlistarEstadistiquesPartida");
			System.out.println("14. testReiniciaPartida");
			System.out.println("15. testGuardarPartida");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testLlistarPartidesGuardades();
				break;
			case 3:
				d.testTriarPartidaGuardada();
				break;
			case 4:
				d.testNovaPartidaEstandar();
				break;
			case 5:
				d.testNovaPartidaPersonalitzada();
				break;
			case 6:
				d.testEscollirAjuts();
				break;
			case 7:
				d.testEscollirDificultat();
				break;
			case 8:
				d.testEscollirColors();
				break;
			case 9:
				d.testEscollirPosicions();
				break;
			case 10:
				d.testEscollirTorns();
				break;
			case 11:
				d.testEscollirNombreJocs();
				break;
			case 12:
				d.testContinuarAlSeguentJoc();
				break;
			case 13:
				d.testLlistarEstadistiquesPartida();
				break;
			case 14:
				d.testReiniciaPartida();
				break;
			case 15:
				d.testGuardarPartida();
			}
		}
	}
}
