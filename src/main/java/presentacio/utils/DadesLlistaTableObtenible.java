package presentacio.utils;

import java.util.Vector;

import Domini.Aplicacio.Utils.ComparadorCondicional;

/**
 * Volem un tipus d'on només poguem extreure les dades de la llista ordenable, donem
 * 	només els métodes necessaris per a poder crear i mantidre la llista.
 */
public interface DadesLlistaTableObtenible {
	
	/**
	 * Consulta totes les dades.
	 * @return vector de vectors amb cadascuna de les dades existents.
	 */
	public Vector<Vector<Object>> getDades();
	/**
	 * Obté el comparadors condicionals que serveixen per definir criteris d'ordenació.
	 * @return un vector de comparadors, un per cada columna.
	 */
	public Vector<ComparadorCondicional> getComparadors();
	/**
	 * Obté els tipus de cada columna.
	 * @return un vector de tipus, un per cada columna.
	 */
	public Vector<Class<?>> getTypes();
	/**
	 * Obté els noms de totes les columnes.
	 * @return un vector de strings, una string per nom de columna.
	 */
	public Vector<String> dadesNomColumnes();
}
