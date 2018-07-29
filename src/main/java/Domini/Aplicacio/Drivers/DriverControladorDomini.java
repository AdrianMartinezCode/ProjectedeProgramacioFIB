package Domini.Aplicacio.Drivers;

import java.io.IOException;
import java.util.Scanner;

import Domini.Controladors.ControladorDomini;

public class DriverControladorDomini {
	
	ControladorDomini c;
	
	
	public void testConstructor() {
		System.out.println("TEST Constructor");
		c = new ControladorDomini();
		System.out.println("TEST PASSAT, no peta el programa");
	}
	public void testInicialitzarControlador() {
		System.out.println("TEST inicialitzarControlador");
		int exitCode = c.inicialitzarControlador();
		if (exitCode == 0)
			System.out.println("TEST PASSAT");
		else if (exitCode == 3)
			System.out.println("TEST NO PASSAT, error en persistència");
		else if (exitCode == 2)
			System.out.println("TEST NO PASSAT, error al carregar partides als jugadors");
		else if (exitCode == 1)
			System.out.println("TEST NO PASSAT, error al carregar jugadors");
	}
	
	public void testGetCtrlMantJoc() {
		System.out.println("TEST getCtrlMantJoc");
		if (c.getCtrlMantJoc() != null)
			System.out.println("TEST PASSAT!");
		else
			System.out.println("TEST NO PASSAT, la referència és nul·la.");
	}
	
	public void testGetCtrlMantJugador() {
		System.out.println("TEST getCtrlMantJugador");
		if (c.getCtrlMantJugador() != null)
			System.out.println("TEST PASSAT!");
		else
			System.out.println("TEST NO PASSAT, la referència és nul·la.");
	}
	
	public void testGetCtrlMantPartida() {
		System.out.println("TEST getCtrlMantPartida");
		if (c.getCtrlMantPartida() != null)
			System.out.println("TEST PASSAT!");
		else
			System.out.println("TEST NO PASSAT, la referència és nul·la.");
	}
	
	

	public static void main(String[] args) throws IOException {
		Scanner reader = new Scanner(System.in);
		DriverControladorDomini d = new DriverControladorDomini();
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("MENU");
			System.out.println("1. testConstructor");
			System.out.println("2. testInicialitzarControlador");
			System.out.println("3. testGetCtrlMantJoc");
			System.out.println("4. testGetCtrlMantJugador");
			System.out.println("5. testGetCtrlMantPartida");
			System.out.println("0. Sortir");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testInicialitzarControlador();
				break;
			case 3:
				d.testGetCtrlMantJoc();
				break;
			case 4:
				d.testGetCtrlMantJugador();
				break;
			case 5:
				d.testGetCtrlMantPartida();
				break;
			}
		}
	}
}
