package Domini.Controladors;

import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import Domini.IEntitatDomini;
import Domini.Aplicacio.Utils.EntradaGenerica;

public class CtrlDominiMantGeneric<E extends IEntitatDomini> {

	/**
	 * Les entitats emmagatzemades al contenidor.
	 */
	private TreeMap<Integer, E> entitats;
	/**
	 * L'entitat a replicar.
	 */
	private E newEntitat;
	
	public CtrlDominiMantGeneric(E ent) {
		entitats = new TreeMap<Integer, E>();
		newEntitat = ent;
	}
	
	/**
	 * Canvia l'instància de l'entitat que s'utilitza per copiar.
	 * @param ent instància d'una entitat.
	 */
	protected void setEntitat(E ent) {
		//System.out.println("Entitat seleccionada: " + ent.getClass().getName());
		newEntitat = ent;
		//System.out.println("Entitat configurada: " + newEntitat.getClass().getName());
	}
	
	/**
	 * Retorna la referència de la entitat (hi ha aliasing)
	 * @return retorna la referència de la entitat emmagatzemada internament.
	 */
	protected E getEntitat() {
		return newEntitat;
	}
	
	/**
	 * Obté el nombre d'entitats donades d'alta.
	 * @return el nombre d'entitats emmagatzemades.
	 */
	public int numEntitatsEmmagatzemades() {
		return entitats.size();
	}
	
	/**
	 * Obté totes les dades de totes les entitats.
	 * @return retorna un vector de strings amb les dades de cada entitat.
	 */
	public Vector<String> consultaEntitats() {
		Vector<String> ents = new Vector<String>();
		for (E et : entitats.values()) {
			ents.add(et.convertirToString());
		}
		return ents;
	}
	
	/**
	 * Obté totes les claus de les entitats donades d'alta.
	 * @return retorna un conjunt de identificadors de cada entitat.
	 */
	public Set<Integer> getKeys() {
		return entitats.keySet();
	}
	
	/**
	 * Obté una entitat.
	 * @param id de la entitat que es vol recuperar.
	 * @return retorna la referència de la entitat NO LA CLONA.
	 */
	protected E getEntitat(int id) {
		return entitats.get(id);
	}
	
	/**
	 * Obté la string amb les dades de la entitat.
	 * @param id de la identitat que es vol recuperar.
	 * @return retorna la string amb les dades de la entitat, null si la entitat
	 * 	no existeix.
	 */
	public String getStringEntitat(int id) {
		E ent = entitats.get(id);
		if (ent == null)
			return null;
		return ent.convertirToString();
	}
	
	/**
	 * Emmagatzema una nova entitat al controlador.
	 * @param id identificador de la entitat.
	 * @param dades separada cada dada amb un espai, la primera dada ha de ser
	 * 	la id del primer argument.
	 * @return retorna 1 si existeix la entitat, retorna 2 si el parámetre id és diferent
	 * 	al primer element de l'string dades, retorna 4 si hi ha algun error en el format
	 * 	de les dades, 5 si algun filtre de l'entitat no passa.
	 * 	retorna 0 si no hi ha hagut cap error.
	 */
	public int altaEntitat(int id, String dades) {
		if (existeixEntitat(id))
			return 1;
		else {
			// Comprovació consistència
		}
		
		// Mirem que la id passada sigui la mateixa que la del vector
		//System.out.println(dades + " || id: " + id  + "|| splitada: " + dades.split(" ")[0]);
		//System.out.println(newEntitat);
		E entitat = (E) newEntitat.miClone();
		if (id != Integer.valueOf((dades.split(entitat.getSeparador())[0]))) {
			return 2;
		}
		System.out.println(dades);
		int coderr = entitat.obtenerFromString(dades);
		System.out.println(coderr);
		if (coderr == 1)
			return 4;
		else if (coderr == 2)
			return 5;
		entitats.put(new Integer(id), entitat);
		return 0;
	}
	
	/**
	 * Crea una nova entitat amb els paràmetres definits per defecte a la pròpia entitat
	 * @param id identificador de la entitat.
	 * @param params paràmetres adicionals de creació de la nova entitat.
	 * @return 1 si existeix la entitat amb aquest id, 3 si params és incorrecte, 
	 * 	retorna 4 si hi ha algun error en el format de les dades, 5 si algun filtre de l'entitat no passa. 
	 * 	retorna 0 , 0 si tot ha anat bé, 2 si el paràmetre id és diferent al primer
	 * 	element de l'string dades.
	 */
	public int altaNovaEntitat(int id, Vector<String> params) {
		String dades = newEntitat.novaEntitatParametres(id, params);
		System.out.println(dades);
		if (dades == null)
			return 4;
		return this.altaEntitat(id, dades);
	}
	
	
	/**
	 * Dóna de baixa una entitat que prèviament s'havia donat d'alta.
	 * @param id de la entitat per donar de baixa.
	 * @return retorna 1 si no existeix la entitat, 0 si s'ha donat de baixa correctament.
	 */
	public int baixaEntitat(int id) {
		if (!existeixEntitat(id))
			return 1;
		entitats.remove(id);
		return 0;
	}
	
	/**
	 * Mira si la entitat ha sigut donada de alta.
	 * @param ident id de la identitat.
	 * @return retorna true si s'ha donat d'alta, altrament fals.
	 */
	public boolean existeixEntitat(int ident) {
		return entitats.containsKey(ident);
	}
	
	/**
	 * Retorna l'últim identificador que hi ha registrat a les entitats donades d'alta.
	 * @return l'identificador, si no hi han entitats donades d'alta, retorna -1.
	 */
	public int getLastIdentifier() {
		int id;
		try {
			id = entitats.lastKey();
		} catch (NoSuchElementException e) {
			id = -1;
		}
		return id;
	}
	
	/**
	 * Esborra totes les entitats donades d'alta.
	 */
	public void cleanEntitats() {
		entitats = new TreeMap<Integer, E>();
	}
	
	public Vector<Vector<Object>> getEntradesDades() {
		Vector<Vector<Object>> entrades = new Vector<Vector<Object>>();
		for (E e : entitats.values())
			entrades.add(e.getEntradaGenerica().toVectorType());
		return entrades;
	}
	
	public EntradaGenerica getAnyEntradaGenerica() {
		Entry<Integer, E> e = entitats.lastEntry();
		
		return entitats.lastEntry().getValue().getEntradaGenerica();
	}
}
