package Domini.Joc;

import java.util.Random;
import java.util.Vector;

import Domini.Aplicacio.IDadesPartida;
import Domini.Aplicacio.INotificaJocAcabat;
import Domini.Aplicacio.Utils.ColorGenerador;
import Domini.ia.Prova;

public class JocBreaker extends JocActual {
	
	/**
	 * Guardem si s'ha introduit la combinació inicial.
	 */
	
	public JocBreaker(IDadesPartida dadesPart, INotificaJocAcabat not) {
		super(dadesPart, not);
	}
	
	@Override
	public Rol getRol() {
		return Rol.CodeBreaker;
	}
	
	@Override
	public void introduirCombinacioAmagada(Vector<Integer> comb) {
		super.introduirCombinacioAmagada(comb);
	}
	/**
	 * Genera una combinaci aleatria
	 * @param numFitxes
	 * @param numColors
	 * @return Una combinaci aleatria
	 */
	public Vector<Integer> inicialitzarCombinacio(int numFitxes, int numColors){
		Vector<Integer> Combinacio = new Vector<Integer>(numFitxes);
		Random rand = new Random();
		for(int i = 0; i<numFitxes; i++) {
			int nouColor = (int) rand.nextInt(numColors);
			Combinacio.add(nouColor);
		}
		return Combinacio;
		}
	
	/*@Override
	public boolean teUltimaPistaPosada() {
		if (super.getTornActual() == 1)
			return true;
		return super.teUltimaPistaPosada();
	}*/
	
	/**
	 * Afegeix la combinacio inicial al tauler si no existia encara, afegeix la combinaci introduda per l'usuari al tauler i la seva pista corresponent
	 * @param c
	 * @return retorna 0 si ha anat tot be, 1 si el tamany de la combinaci� que es pasa es diferent al num de fitxes i 2 si algun color esta fora el rang
	 * dels colors maxim a utilitzar
	 */
	public int introduirCombinacio(Vector <Integer> c) {
		int numFitxes = super.getDadesPartida().getNPosicions();
		if (numFitxes != c.size()) return 1;
		int numColors = super.getDadesPartida().getNColors();
		for (int u = 0; u < c.size(); u++) {
			if (c.get(u) >= numColors) return 2;
		}
		//boolean ajuts = super.getDadesPartida().getAjuts();
		/*if (!combinacioIntroduida) {
			Vector<Integer> Combinacio= inicialitzarCombinacio(numFitxes, numColors);
			this.introduirCombinacioAmagada(Combinacio);
			combinacioIntroduida = true;
		}*/
		Vector<Integer> combinacio2 = this.obtenirCombinacioAmagada();
		
		Vector<Integer> Pista2;
		if (getDadesPartida().getAjuts()) {
			Pista2 = this.codeBreakAjuts(numFitxes, combinacio2, c);
		} else {
			Pista2 = this.codeBreakSenseAjuts(numFitxes, combinacio2, c);
		}
		
		super.afegirCombinacio(c);
		super.afegirPista(Pista2);
		return 0;
	}
		
		
		/**
		 * Retorna les pistes amb ajuts
		 * @param numColors
		 * @param numFitxes
		 * @param Combinacio2
		 * @param Combinacio
		 * @return Un vector de mida igual al nmero de posicions on indica, per cada posici, si els colors de les dues combinacions coincideixen(un 2), si el color est b per la posici no (un 1), o si est malament (un 0)
		 */
		public Vector<Integer> codeBreakAjuts(int numFitxes,Vector<Integer> Combinacio2, Vector<Integer> Combinacio) {
			Prova corregirFitxes = new Prova();
			Vector<Integer> Resposta = new Vector<Integer>();
			//System.out.println(Combinacio2);
			Resposta = corregirFitxes.corregir2(numFitxes ,Combinacio2, Combinacio);
			return Resposta;
		}

		public Vector<Integer> codeBreakSenseAjuts(int numFitxes,Vector<Integer> Combinacio2, Vector<Integer> Combinacio) {
			Vector<Integer> resp = new Prova().corregir(numFitxes, Combinacio, Combinacio2);
			Vector<Integer> real = new Vector<Integer>();
			int negres = resp.get(0);
			int blanques = resp.get(1);
			for (int i = 0; i < negres; i++) {
				real.add(ColorGenerador.getNumPistaNegra());
			}
			for (int i = 0; i < blanques; i++) {
				real.add(ColorGenerador.getNumPistaBlanca());
			}
			for (int i = 0; i < numFitxes - negres - blanques; i++) {
				real.add(ColorGenerador.getNumPistaIncorrecta());
			}
			
			return real;
		}
		
	
}
