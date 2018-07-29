package Domini;

import java.util.Vector;

import Domini.Aplicacio.Utils.EntradaGenerica;

public interface IEntitatDomini extends IEntitatCarregable{
	
	/**
	 * Clona l'entitat creant una instància nova exactament amb els mateixos
	 * 	atributs, però instància diferent.
	 * @return la referència de la instància del nou objecte.
	 */
	public IEntitatDomini miClone();
	
	/**
	 * Retorna la string corresponent a les dades d'una nova instància de la entitat.
	 * @param id clau de la entitat.
	 * @param params parámetres adicionals de la classe.
	 * @return retorna null si params és incorrecte, altrament retorna les dades de la entitat
	 * 	com si fos nova.
	 */
	public String novaEntitatParametres(int id, Vector<String> params);
	
	/**
	 * Obté l'entrada genèrica d'una entitat carregable, serveix per la
	 * 	facilitat de obtenció de dades.
	 * @return retorna una instància del tipus específic de la classe en concret, en aquest
	 * 	cas la més general.
	 */
	public EntradaGenerica getEntradaGenerica();
}
