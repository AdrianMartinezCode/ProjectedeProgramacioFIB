package Domini.Joc;


import java.util.Vector;

import Domini.IEntitatCarregable;
import Domini.Aplicacio.IDadesPartida;
import Domini.Joc.Excepcions.ExcCombinacioJaIntroduida;
import Domini.Joc.Excepcions.ExcCombinacioNoIntroduida;
import Domini.Joc.Excepcions.ExcPistaJaIntroduida;
import Domini.Joc.Excepcions.ExcPistaNoIntroduida;

public class Linia implements IEntitatCarregable{

	private IDadesPartida dadesPart;
	/**
	 * La combinació de colors.
	 */
	private GrupFitxes jugada;
	/**
	 * La combinació de pistes.
	 */
	private Pista pista;
	
	private static final String SEPARADOR = "SEPARADORLINIASPLIT";
	private static final String NULL_STRING = "PPPPLLLL";
	
	public Linia(IDadesPartida dadesPart) {
		this.dadesPart = dadesPart;
		jugada = null;
		pista = null;
	}
	
	
	
	/**
	 * Getter de l'atribut pistaPosada, retorna el valor d'aquest atribut
	 * @return un boolean amb el valor de pistaPosada
	 */
	public boolean pistaPosada() {
		return pista != null && this.pista.grupPossatComplet();
	}
	
	/**
	 * Afegeix una combinacio passada com a parametre en forma de vector a l'atribut jugada de linia
	 * @param comb conte els valor de les fitxes que s'introduiran com a nova combinacio
	 * @throws ExcCombinacioJaIntroduida si la combinació ja s'ha introduït.
	 */
	public void afegirCombinacio(Vector<Integer> comb) {
		if (jugada != null)
			throw new ExcCombinacioJaIntroduida();
		jugada = new GrupFitxes(dadesPart);
		for (Integer i : comb) {
			jugada.afegirFitxa(i);
		}
	}
	
	/**
	 * Afegeix una combinacio passada com a parametre en forma de vector de integers a l'atribut pistes de linia
	 * @param pistes cont els valors de les pistes que s'introduiran
	 * @throws ExcPistaJaIntroduida si ja s'ha introduït la pista.
	 * @throws ExcCombinacioNoIntroduida si la combinació d'aquesta línia encara
	 * 	no s'ha introduït.
	 */
	public void afegirPistes(Vector<Integer> pistes) {
		if (pista != null)
			throw new ExcPistaJaIntroduida();
		if (jugada == null)
			throw new ExcCombinacioNoIntroduida();
		pista = new Pista(dadesPart);
		for (Integer i : pistes) {
			pista.afegirFitxa(i);
		}
	}
	
	/**
	 * Retorna la combinacio de jugada en forma de vector. A cada posicio del vector hi haur un int corresponent a la fitxa.
	 * @return vector amb la combinacio de jugada
	 * @throws ExcCombinacioNoIntroduida si la combinació encara no s'ha introduït.
	 */
	public Vector<Integer> obtenirCombinacio(){
		if (jugada == null)
			throw new ExcCombinacioNoIntroduida();
		Vector<Integer> ret = new Vector<Integer>();
		for (int i = 0; i < jugada.obtenirMida(); i++) {
			ret.add(jugada.obtenirColor(i));
		}
		return ret;
	}
	
	/**
	 * Retorna la combinacio de pistes en forma de vector. A cada posicio del vector hi haur un int corresponent a la pista.
	 * @return vector amb la combinacio de pistes
	 * @throws ExcPistaNoIntroduida si encara no s'ha introduït la pista.
	 */
	public Vector<Integer> obtenirPista(){
		if (pista == null)
			throw new ExcPistaNoIntroduida();
		Vector<Integer> ret = new Vector<Integer>();
		for (int i=0; i < pista.obtenirMida(); i++)
			ret.add(pista.obtenirColor(i));
		
		return ret;
	}

	@Override
	public String convertirToString() {
		String ret = "";
		if (jugada == null)
			ret += NULL_STRING;
		else
			ret += jugada.convertirToString();
		ret += SEPARADOR;
		if (pista == null)
			ret += NULL_STRING;
		else
			ret += pista.convertirToString();
		return ret;
	}

	@Override
	public int obtenerFromString(String vstr) {
		String[] ss = vstr.split(SEPARADOR);
		
		if (ss.length != 2)
			return 2;
		
		if (ss[0].equals(NULL_STRING))
			jugada = null;
		else {
			jugada = new GrupFitxes(dadesPart);
			int codeError = jugada.obtenerFromString(ss[0]);
			if (codeError != 0) {
				netejaObjecte();
				return codeError;
			}
		}
		
		if (ss[1].equals(NULL_STRING))
			pista = null;
		else {
			pista = new Pista(dadesPart);
			int codeError = pista.obtenerFromString(ss[1]);
			if (codeError != 0) {
				netejaObjecte();
				return codeError;
			}
		}
		
		return 0;
	}
	
	/**
	 * Mira si la combinació (grupFitxes) és igual a la que hi ha a la línia.
	 * @param gf el grup de fitxes a comparar.
	 * @return cert si el grup de fitxes gf és igual al que existeix a la línia
	 * 	actual, fals altrament.
	 * @throws ExcCombinacioNoIntroduida si la combinació d'aquesta línia no
	 * 	s'ha introduït encara.
	 */
	public boolean igualsCombinacio(GrupFitxes gf) {
		if (jugada == null)
			throw new ExcCombinacioNoIntroduida();
		return jugada.iguals(gf);
	}
	
	/**
	 * Consulta si la combinació de la línia ha sigut introduïda completament.
	 * @return cert si la combinació ha sigut introduïda, fals altrament.
	 */
	public boolean combinacioIntroduidaCompletament() {
		return jugada != null && jugada.grupPossatComplet();
	}

	@Override
	public String getSeparador() {
		return SEPARADOR;
	}

	@Override
	public void netejaObjecte() {
		jugada = null;
		pista = null;
	}

}
