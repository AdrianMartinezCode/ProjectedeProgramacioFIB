package presentacio.tauler.liniadecorador;

import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;

/**
 * Classe que engloba les funcionalitats de les linies del tauler, és un patró decorador,
 * 	té la base i arrel d'aquesta es van afegint linies.
 */
public abstract class PanelGeneralLinia extends JPanel{
	
	/**
	 * Afegeix una nova línia al panell base.
	 * @param pl el panell ja configurat i llest per afegir.
	 */
	public abstract void afegeixLinia(PanelLinia pl);
	/**
	 * Afegeix l'escoltador per notificar els clics premuts a les pistes, només
	 * 	afecta a la última línia afegida.
	 * @param al l'escoltador.
	 */
	public abstract void afegirListenerPistes(MouseListener al);
	/**
	 * Afegeix l'escoltador per notificar els clics premuts a la combinació, només
	 * 	afecta a la última línia afegida.
	 * @param al l'escoltador.
	 */
	public abstract void afegirListenerFitxes(MouseListener al);
	/**
	 * Elimina els escoltadors configurats per a les pistes a la última línia afegida.
	 */
	public abstract void eliminaListenerPistes();
	/**
	 * Elimina els escoltadors configurats per a la combinació a la última ĺínia afegida.
	 */
	public abstract void eliminaListenerFitxes();
	/**
	 * Consulta el número total de línies que hi han.
	 * @return el nombre de línies.
	 */
	public abstract int getNumLinies();
	/**
	 * Modifica el color de una fitxa en concret de la última línia.
	 * @param pos l'índex de la posició de la fitxa.
	 * @param idColor l'identificador del color.
	 */
	public abstract void setColorPosFitxa(int pos, int idColor);
	/**
	 * Modifica els colors de la combinació de la última línia afegida, en el
	 * 	mateix ordre que hi ha al vector.
	 * @param fitxes el vector de integers amb els identificadors de color.
	 */
	public abstract void setColorsFitxes(Vector<Integer> fitxes);
	/**
	 * Modifica els colors de les pistes de la última línia afegida, en el
	 * 	mateix ordre que hi ha al vector.
	 * @param fitxes el vector de integers amb els identificadors de color.
	 */
	public abstract void setColorsPistes(Vector<Integer> pistes);
	/**
	 * Consulta si la última combinació està totalment colorada.
	 * @return cert si ho està, fals altrament.
	 */
	public abstract boolean combinacioColorada();
	
	/**
	 * Retorna la última pista introduida.
	 * @return un vector amb el número de color de cada pista,
	 * 	null si encara no hi ha línia creada.
	 */
	public abstract Vector<Integer> getUltimaPista();
	
	/**
	 * Retorna la última combinació introduïda.
	 * @return un vector amb el número de color de cada fitxa,
	 * 	null si encara no hi ha línia creada.
	 */
	public abstract Vector<Integer> getUltimaCombinacio();
}
