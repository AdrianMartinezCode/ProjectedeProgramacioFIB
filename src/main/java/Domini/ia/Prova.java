package Domini.ia;

import java.util.Scanner;
import java.util.Vector;

import Domini.Aplicacio.Utils.ColorGenerador;
/**
 * Aquesta classe l'utilitzarem per, donades dues combinacions, saber les pistes que ens dona el codeMaster
 *
 */
public class Prova {

	/**
	 * pre: Combinacio i Combinacio2 tenen la mateixa mida
	 * @param numFitxes
	 * @param Combinacio
	 * @param Combinacio2
	 * @return Un vector on la primera posici s el nmero de fitxes que coincideixen i la segona posici indica quants colors coincideixen per estn mal posats
	 */
	public Vector<Integer> corregir(int numFitxes, Vector<Integer> Combinacio,Vector<Integer> Combinacio2) {		
		/*
		 * Primer mirem el numero de fitxes en la posici correcte
		 */
		int Correctes = 0;

		for (int i=0; i<numFitxes; i++) {
			Integer col1 = Combinacio.get(i);
		    Integer col2 = Combinacio2.get(i);
			if(col1.equals(col2)) Correctes++;
		}
		/*
		 * Mirem les fitxes que hem encertat el color per no la posici
		 */
		int MalPosades = 0;
		Vector<Integer> CombinacioTemp = new Vector<Integer>(numFitxes);
		for (int l = 0; l < numFitxes; l++) {
			CombinacioTemp.add(Combinacio.get(l));
		}
		for (int j=0; j<numFitxes;j++) {
			for (int q=0; q<numFitxes;q++) {
				if (Combinacio2.get(j).equals(CombinacioTemp.get(q))) {
					MalPosades++;
					CombinacioTemp.set(q,null);
					q = numFitxes; //aix surt del bucle
				}
			}
		}
		MalPosades = MalPosades-Correctes;
		Vector<Integer> Resposta = new Vector<Integer>(2);
		Resposta.add(Correctes);
		Resposta.add(MalPosades);
		return Resposta;
	}
	
	
	
	
	
	
	/**
	 * pre: Combinacio i Combinacio2 tenen la mateixa mida
	 * @param numFitxes
	 * @param Combinacio
	 * @param Combinacio2
	 * @return Un vector de mida numFitxes, on per cada fitxa et dir si el color coincideix (un 2), si el color s correcte per est a un altre lloc (un 1) o si s incorrecte (un 0)
	 */
	public Vector<Integer> corregir2(int numFitxes, Vector<Integer> Combinacio,Vector<Integer> Combinacio2) {		
		/*
		 * 0 --> Ni correcte ni hi ha el color
		 * 1 --> No s correcte per hi ha el color
		 * 2 --> s correcte
		 */
		Vector<Integer> correccio = new Vector<Integer>(numFitxes);
		for(int l = 0; l<numFitxes;l++) {
			correccio.add(ColorGenerador.getNumPistaIncorrecta());
			//correccio.add(0);
		}
		Vector<Integer> CombinacioTemp = new Vector<Integer>(numFitxes);
		for (int q = 0; q < numFitxes; q++) {
			CombinacioTemp.add(Combinacio.get(q));
		}
		for (int i=0; i<numFitxes; i++) {
			Integer col1 = Combinacio.get(i);
		    Integer col2 = Combinacio2.get(i);
			if(col1.equals(col2)) {
				correccio.set(i, ColorGenerador.getNumPistaNegra());
				//correccio.set(i, 2);
				CombinacioTemp.set(i,null);
			}
		}
		for (int r = 0; r < numFitxes; r++) {
			Integer col3 = Combinacio2.get(r);
		    Integer corr = correccio.get(r);
		    if (!(corr.equals(ColorGenerador.getNumPistaNegra()))) {
		    	
		    	int v = 0;
		    	boolean trobada = false;
		    	while (v < numFitxes && !trobada) {
		    		Integer col4 = CombinacioTemp.get(v);
		    		trobada = col3.equals(col4);
		    		if (trobada) {
		    			correccio.set(r, ColorGenerador.getNumPistaBlanca());
						CombinacioTemp.set(v,null);
		    		}
		    		++v;
		    	}
		    }
		}
		return correccio;
	}
}
