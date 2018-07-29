package presentacio;

import java.util.Vector;

public interface DadesPartidaObtenible {
	/**
	 * Obté les dades de la partida actual en forma de vector string.
	 * @return un vector de string, on cada string és una dada.
	 */
	public Vector<String> getDades();
	/**
	 * Obté les descripcions de cada dada de la partida actual en forma de vector string.
	 * @return un vector de string, on cada string és la descripció
	 * 	de una dada.
	 */
	public Vector<String> getDescripcioDades();
}
