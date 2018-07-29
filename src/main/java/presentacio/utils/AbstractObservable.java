package presentacio.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * Classe creada amb la finalitat d'estalviar codi a l'hora de fer observables
 * 	a les vistes que creem, ja que necessitem notificar al controlador de que
 * 	un botó s'ha premut
 *
 * @param <T> ha de ser un tipus que extengui de Observador.
 */
public abstract class AbstractObservable<T extends Observador> {
	/**
	 * El conjunt d'observadors.
	 */
	private Set<T> obs;
	
	public AbstractObservable() {
		obs = new HashSet<T>();
	}
	
	/**
	 * Afegeix al conjunt dels observadors un altre observador.
	 * @param ot l'observador a afegir.
	 */
	public void donaAltaObservador(T ot) {
		obs.add(ot);
	}
	
	/**
	 * Elimina del conjunt d'observadors un altre observador.
	 * @param ot l'observador a donar de baixa.
	 */
	public void donaBaixaObservador(T ot) {
		obs.remove(ot);
	}
	
	/**
	 * Obté el conjunt d'observadors en una nova instància.
	 * @return el conjunt d'observadors.
	 */
	public Set<T> getObservadors() {
		return new HashSet<>(obs);
	}
}
