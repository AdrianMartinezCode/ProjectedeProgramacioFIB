package Domini.Aplicacio.Drivers;

import java.util.Scanner;
import java.util.Vector;

import Domini.Controladors.ControladorMantenimentJugador;

public class DriverControladorMantenimentJugador {
	static Scanner reader;
	ControladorMantenimentJugador c;
	
	public void testCrearJugador() {
		System.out.println("TEST crearJugador");
		System.out.println("Introdueïx una id pel jugador:");
		int id = reader.nextInt();
		System.out.println("Introdueïx un nom pel jugador: ");
		int exitCode = c.crearJugador(id, reader.next());
		if (exitCode == 0)
			System.out.println("TEST PASSAT, S'ha creat correctament el jugador i s'ha carregat");
		else if (exitCode < 0) {
			System.out.println("TEST NO PASSAT, Ha ocorregut un error als fitxers.");
		} else {
			System.out.println("TEST NO PASSAT, Ha ocorregut un error a la capa del domini al crear-lo.");
		}
	}
	
	public void testConstructor() {
		System.out.println("TEST Constructor");
		c = new ControladorMantenimentJugador();
		System.out.println("TEST PASSAT, no peta el programa");
	}
	
	public void testGetLastIdPartidaJugadorEscollit() {
		System.out.println("TEST getLastIdPartidaJugadorEscollit");
		
		
		int id = c.getLastIdPartidaJugadorEscollit();
		if (id == -1)
			System.out.println("TEST NO PASSAT, no hi ha jugador escollit");
		else if (id == -2)
			System.out.println("TEST PASSAT, però no hi han partides guardades d'aquest jugador.");
		else
			System.out.println("TEST PASSAT, id última partida =" + id);
	}
	

	public void testCarregaJugadors() {
		System.out.println("TEST carregaJugadors");
		int exitCode = c.carregaJugadors();
		if (exitCode == 0)
			System.out.println("TEST PASSAT, s'han carregat correctament.");
		else if (exitCode < 0)
			System.out.println("TEST NO PASSAT, hi ha hagut algun error a la persistència.");
		else if (exitCode > 0)
			System.out.println("TEST NO PASSAT, hi ha hagut algun error a la capa domini.");
	}
	
	public void testCarregaPartidesGuardadesAJugadors() {
		System.out.println("TEST carregaPartidesGuardadesAJugadors");
		int exitCode = c.carregaPartidesGuardadesAJugadors();
		if (exitCode == 0)
			System.out.println("TEST PASSAT, s'han carregat correctament");
		else if (exitCode < 0)
			System.out.println("TEST NO PASSAT, hi ha hagut algun error a persistència.");
		else
			System.out.println("TEST NO PASSAT, hi ha hagut algun error a la capa domini.");
	}
	
	public void testGetDadesPartides() {
		System.out.println("TEST getDadesPartides");
		Vector<String> ps = c.getDadesPartides();
		if (ps == null) {
			System.out.println("TEST NO PASSAT, jugador no carregat o partides no carregades.");
			return;
		}
		System.out.println("TEST PASSAT, imprimint dades de les partides:");
		for (String d : ps) {
			System.out.println("- " + d);
		}
	}
	
	public void testEscollirJugador() {
		System.out.println("TEST escollirJugador");
		System.out.println("Escull id del jugador a carregar:");
		int id = reader.nextInt();
		if (!c.escollirJugador(id)) {
			System.out.println("TEST NO PASSAT, el jugador amb aquesta id no existeix.");
		} else {
			System.out.println("TEST PASSAT, s'ha escollit el jugador amb exit");
		}
	}
	
	public void testConsultarRanking() {
		System.out.println("TEST consultar ranking");
		System.out.println("Ordenar ranking per:");
		System.out.println("1. per puntuació");
		System.out.println("2. per wl ratio");
		System.out.println("3. per rècord");
		
		int opc = reader.nextInt();
		
		Vector<String> v = null;
		
		switch (opc) {
		case 1:
			v = c.rankingOrdenatPerPuntuacioTotal();
			break;
		case 2:
			v = c.rankingOrdenatPerWLRatio();
			break;
		case 3:
			v = c.rankingOrdenatPerRecord();
		}
		if (v.isEmpty())
			System.out.println("TEST PASSAT, però no hi han jugadors");
		else {
			System.out.println("TEST PASSAT, mostrant entrades del ranking:");
			int i = 1;
			for (String s : v) {
				System.out.println(i++ + " " + s);
			}
		}
	}
	
	public static void main(String[] args) {
		reader =  new Scanner(System.in);
		
		DriverControladorMantenimentJugador d = new DriverControladorMantenimentJugador();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testConstructor");
			System.out.println("2. testCarregaJugadors");
			System.out.println("3. testCarregaPartidesGuardadesAJugadors");
			System.out.println("4. testCrearJugador");
			System.out.println("5. testEscollirJugador");
			System.out.println("6. testGetDadesPartides");
			System.out.println("7. testGetLastIdPartidaJugadorEscollit");
			System.out.println("8. testConsultarRanking");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testCarregaJugadors();
				break;
			case 3:
				d.testCarregaPartidesGuardadesAJugadors();
				break;
			case 4:
				d.testCrearJugador();
				break;
			case 5:
				d.testEscollirJugador();
				break;
			case 6:
				d.testGetDadesPartides();
				break;
			case 7:
				d.testGetLastIdPartidaJugadorEscollit();
				break;
			case 8:
				d.testConsultarRanking();
			}
		}
	}
}
