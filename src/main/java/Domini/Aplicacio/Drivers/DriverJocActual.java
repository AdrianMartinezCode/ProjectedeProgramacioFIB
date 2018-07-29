package Domini.Aplicacio.Drivers;

import java.util.Scanner;
import java.util.Vector;

import Domini.Aplicacio.IDadesPartida;
import Domini.Aplicacio.INotificaJocAcabat;
import Domini.Joc.Dificultat;
import Domini.Joc.JocBreaker;
import Domini.Joc.Excepcions.ExcAccesForaRang;

public class DriverJocActual implements IDadesPartida, INotificaJocAcabat {
	static Scanner reader;
	JocBreaker jb;
	
	public void testConstructor() {
		jb = new JocBreaker(this, this);
		System.out.println("Joc construit!");
	}
	
	public void testAfegirCombinacioAmagada() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			System.out.println("Voleu Introduir una combinacio inicial?	1: SI	2:NO");
			int n = reader.nextInt();
			if (n == 1) {
				Vector<Integer> comb = new Vector<Integer>();
				System.out.println("Escriu una combinació de 5 numeros");
				for (int i = 0; i < this.getNPosicions(); i++) {
					comb.add(reader.nextInt());
				}
				jb.introduirCombinacioAmagada(comb);
				System.out.println("Combinació Inicial Introduida");
			} else if (n == 2) {
				System.out.println("Combinació Inicial NO Introduida");
			}else System.out.println("Opcio no vàlida");
			
		}
	}
	
	public void testAfegirCombinacio() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			Vector<Integer> comb = new Vector<Integer>();
			System.out.println("Escriu una combinació de 5 numeros");
			for (int i = 0; i < this.getNPosicions(); i++) {
				comb.add(reader.nextInt());
			}
			jb.afegirCombinacio(comb);
			System.out.println("Combinació Introduida");
		}
	}
	
	public void testAfegirPistes() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			Vector<Integer> pista = new Vector<Integer>();
			System.out.println("Pista generada per defecte");
			pista.add(Integer.MAX_VALUE);
			pista.add(Integer.MAX_VALUE - 1);
			pista.add(Integer.MAX_VALUE - 1);
			pista.add(Integer.MAX_VALUE - 2);
			pista.add(Integer.MAX_VALUE - 2);
			jb.afegirPista(pista);
			System.out.println("Pista Introduida");
		}
	}	
	
	public void testObtenirCombinacioAmagada() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			Vector<Integer> combIntr = jb.obtenirCombinacioAmagada();
			for (int i = 0; i < combIntr.size(); i++) {
				System.out.print(combIntr.get(i));
			}
			System.out.println("");
		}
	}
	
	public void testObtenirCombinacio() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			System.out.println("Vols obtenir la ultima combinació o una en concret?	1:Ultima	2:Concreta");
			int n = reader.nextInt();
			if (n == 1) {
				System.out.println("Última combinació:");
				Vector<Integer> comb = jb.obtenirUltimaCombinacio();
				for (int i = 0; i < comb.size(); i++) {
					System.out.print(comb.get(i));
				}
				System.out.println("");
			} else if (n == 2) {
				System.out.println("Introdueix la posició desitjada:");
				n = reader.nextInt();
				System.out.println("Combinació nº" + n + " :");
				try {
					Vector<Integer> comb = jb.obtenirCombinacioIessima(n);
					for (int i = 0; i < comb.size(); i++) {
						System.out.print(comb.get(i));
					}
					System.out.println("");
				} catch (ExcAccesForaRang e) {
					e.printStackTrace();
				}
			}else System.out.println("Opcio no vàlida");
		}
	}
	
	public void testObtenirPista() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			System.out.println("Vols obtenir la ultima pista o una en concret?	1:Ultima	2:Concreta");
			int n = reader.nextInt();
			if (n == 1) {
				if (jb.teUltimaPistaPosada()) {
					System.out.println("Última pista:");
					Vector<Integer> pista = jb.obtenirUltimaPista();
					for (int i = 0; i < pista.size(); i++) {
						System.out.print(pista.get(i) + " ");
					}
					System.out.println("");
				}else System.out.println("Pista NO introduida");
			} else if (n == 2) {
				System.out.println("Introdueix la posició desitjada:");
				n = reader.nextInt();
				System.out.println("Pista nº" + n + " :");
				try {
					Vector<Integer> comb = jb.obtenirPistaIessima(n);
					for (int i = 0; i < comb.size(); i++) {
						System.out.print(comb.get(i) + " ");
					}
					System.out.println("");
				} catch (ExcAccesForaRang e) {
					e.printStackTrace();
				}
			}else System.out.println("Opcio no vàlida");
		}
	}
	
	public void testObtenerFromString() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			int n = jb.obtenerFromString("3SEPARADORSPLITDEJOCACTUAL1%%%%1%%%%0%%%%0%%%%0SEPARADORLINIASPLIT2147483647%%%%2147483646%%%%2147483646%%%%2147483645%%%%2147483645SEPARADORSPLITDEJOCACTUAL2%%%%2%%%%0%%%%1%%%%1SEPARADORLINIASPLIT2147483647%%%%2147483646%%%%2147483646%%%%2147483645%%%%2147483645SEPARADORSPLITDEJOCACTUAL1%%%%1%%%%1%%%%1%%%%1SEPARADORLINIASPLITPPPPLLLLSEPARADORSPLITDEJOCACTUAL1%%%%2%%%%0%%%%1%%%%1SEPARADORSPLITDEJOCACTUAL3SEPARADORSPLITDEJOCACTUALfalse");
		}
	}
	
	public void testConvertirToString() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			String ret = jb.convertirToString();
			System.out.println(ret);
		}
	}
	
	
	public void testDadesJoc() {
		if (jb == null) {
			System.out.println("Joc no construit!");
		} else {
			System.out.println("Dades del Joc:");
			Vector<String> infoDades = jb.getDadesJoc();
			Vector<String> desc = jb.getDescripcioDadesJoc();
			for (int i = 0; i < desc.size(); i++) {
				System.out.println(desc.get(i) + " " + infoDades.get(i));
			}
		}
	}
	
	
	
	
	public static void main(String[] args) {
		reader =  new Scanner(System.in);
		
		DriverJocActual d = new DriverJocActual();
		
		int opc = -2;
		while (opc != 0 && opc != -1) {
			System.out.println("---------------------");
			System.out.println("MENU");
			System.out.println("1. Constructor");
			System.out.println("2. ConvertirToString");
			System.out.println("3. ObtenerFromString");
			System.out.println("4. AfegirCombinacioAmagada");
			System.out.println("5. AfegirCombinacio");
			System.out.println("6. AfegirPista");
			System.out.println("7. ObtenirCombinacioAmagada");
			System.out.println("8. ObtenirCombinacio");
			System.out.println("9. ObtenirPista");			
			System.out.println("10. testDadesJoc");
			
			opc = reader.nextInt();
			
			switch (opc) {
			case 1:
				d.testConstructor();
				break;
			case 2:
				d.testConvertirToString();
				break;
			case 3:
				d.testObtenerFromString();
				break;
			case 4:
				d.testAfegirCombinacioAmagada();
				break;
			case 5:
				d.testAfegirCombinacio();
				break;
			case 6:
				d.testAfegirPistes();
				break;
			case 7:
				d.testObtenirCombinacioAmagada();
				break;
			case 8:
				d.testObtenirCombinacio();
				break;
			case 9:
				d.testObtenirPista();
				break;
			case 10:
				d.testDadesJoc();
				break;
			}
		}
	}

	@Override
	public int getNPosicions() {
		// TODO Auto-generated method stub
		return 5;
	}

	@Override
	public int getNColors() {
		// TODO Auto-generated method stub
		return 6;
	}

	@Override
	public int getTotalTorns() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public boolean getAjuts() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Dificultat getDificultat() {
		// TODO Auto-generated method stub
		return Dificultat.Alta;
	}

	@Override
	public int getCodiJoc() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void notificaJocAcabat() {
		// TODO Auto-generated method stub
		System.out.println("Combinacio encertada!");
	}

}
