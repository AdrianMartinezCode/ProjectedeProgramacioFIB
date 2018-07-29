package Domini.Joc;

import java.util.Vector;

import Domini.IEntitatCarregable;
import Domini.Aplicacio.IDadesPartida;
import Domini.Aplicacio.Utils.ColorGenerador;
import Domini.Joc.Excepcions.ExcNumeroFitxesCompletGrup;

/**
 * Representa un grup de fitxes de un tauler, o sigui, una combinació.
 *
 */
public class GrupFitxes implements IEntitatCarregable{
	private IDadesPartida dadesPart;
	/**
	 * La composició de fitxes.
	 */
	private Vector<Integer> combinacio;
	
	private static final String SEPARADOR = "%%%%";
	
	public GrupFitxes(IDadesPartida dadesPart) {
		this.dadesPart = dadesPart;
		this.combinacio = new Vector<Integer>();
	}
	
	public IDadesPartida getDadesPartida() {
		return dadesPart;
	}
	
	public boolean grupPossatComplet() {
		return combinacio.size() == dadesPart.getNPosicions();
	}
	
	
	/**
	 * Metode que retorna el tamany del vector combinacio, es a dir, quantes fitxes t.
	 * @return un int amb el valor del tamany de combinacio
	 */
	public int obtenirMida() {
		return combinacio.size();
	}
	
	/**
	 * Afegeix una nova fitxa al grup actual de fitxes.
	 * @param c el color de la fitxa.
	 * @throws ExcNumeroFitxesCompletGrup si ja hi és el nombre màxim de fitxes al
	 * 	grup actual.
	 */
	protected void afegeixFitxa(int c) {
		if (this.grupPossatComplet())
			throw new ExcNumeroFitxesCompletGrup("Massa fitxes per aquest grup de fitxes.");
		this.combinacio.add(c);
	}
	
	/**
	 * Metode que afegeix una fitxa, amb n com a valor numColor, al final de la combinacio
	 * @param c valor del numero de color de la fitxa
	 * @return cert si la fitxa s'ha afegit correctament, altrament fals.
	 */
	public boolean afegirFitxa(int c) {
		if (c >= dadesPart.getNColors())
			return false;
		afegeixFitxa(c);
		return true;
	}
	
	/**
	 * Obt el valor del numero del color de la fitxa a la posicio n de la combinacio
	 * @param n posicio de la fitxa de la qual volem saber el seu color
	 * @return un integer amb el valor del numero de color de la fitxa a la posicio n
	 */
	public int obtenirColor(int n) {
		return combinacio.get(n).intValue();
	}
	
	
	@Override
	public String convertirToString() {
		String ret = "";
		boolean first = true;
		for (Integer i : combinacio) {
			if (first)
				first = false;
			else
				ret += SEPARADOR;
			ret += i.intValue();
		}
		return ret;
	}

	@Override
	public int obtenerFromString(String vstr) {
		try {
			String[] strs = vstr.split(SEPARADOR);
			if (strs.length != dadesPart.getNPosicions())
				return 2;
			for (String s : strs) {
				// Podria fer un throw de ExcNumeroFitxesCompletGrup, en aquest cas podria retornar 2 com a codi d'error
				if (!this.afegirFitxa(Integer.valueOf(s))) {
					return 2;
				}
			}
		} catch (NumberFormatException e) {
			netejaObjecte();
			return 1;
		}
		return 0;
	}
	
	/**
	 * Compara si un altre grup de fitxes és igual a l'actual.
	 * @param gf el grup de fitxes a comparar.
	 * @return cert si gf és igual al grup de fitxes actual.
	 */
	public boolean iguals(GrupFitxes gf) {
		return iguals(gf.combinacio);
	}
	
	/**
	 * Obté els identificadors dels colors del grup de fitxes actual.
	 * @return un nou vector amb els identificadors dels colors.
	 */
	public Vector<Integer> obtindreColors() {
		return new Vector<Integer>(combinacio);
	}
	
	/**
	 * Compara si un vector de colors és igual al vector intern de colors de la classe.
	 * @param v el vector de colors.
	 * @return cert si són iguals, altrament fals.
	 */
	public boolean iguals(Vector<Integer> v) {
		if (combinacio.size() != v.size())
			return false;
		
		for (int i = 0; i < v.size() && i < combinacio.size(); i++) {
			if (v.get(i) != combinacio.get(i))
				return false;
		}
		return true;
	}

	@Override
	public String getSeparador() {
		return SEPARADOR;
	}

	@Override
	public void netejaObjecte() {
		this.combinacio = new Vector<Integer>();
	}

}
