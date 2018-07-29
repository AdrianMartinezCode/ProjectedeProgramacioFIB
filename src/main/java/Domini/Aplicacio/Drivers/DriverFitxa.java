package Domini.Aplicacio.Drivers;

import java.util.Scanner;

import Domini.Joc.Fitxa;

public class DriverFitxa {
	static Scanner reader;
	Fitxa f;
	
	public void testConstructor() {
		System.out.println("TEST Constructor");
		f = new Fitxa();
		System.out.println("Fitxa creada correctament");
	}
	
	public void testAssignarColor() {
		System.out.println("TEST AssignarColor");
		System.out.println("Els colors que es poden escollir son:");
		System.out.println("0. Vermell");
		System.out.println("1. Taronja");
		System.out.println("2. Groc");
		System.out.println("3. Verd");
		System.out.println("4. Blau");
		System.out.println("5. Marro");
		System.out.println("6. Blanc");
		System.out.println("7. Negre");
		
		int color = reader.nextInt();
		f = new Fitxa(color);
		f.assignarColor(color);
		System.out.println("La fitxa creada te numColor " + f.getNumColor() + ", que correspon al color " + f.getNomColor());
		
	}
	
	
	public static void main(String[] args) {
		reader =  new Scanner(System.in);
		DriverFitxa d = new DriverFitxa();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. testConstructor");
			System.out.println("2. testAssignarColor");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testAssignarColor();
				break;
			}
		}
	}

}
