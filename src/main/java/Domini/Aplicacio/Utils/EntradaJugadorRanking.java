package Domini.Aplicacio.Utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

/**
 * Només és una classe per la gestió dels rankings, serveix de contenidor de dades
 * per no tindre que fer-ho amb un vector de string i quedi bastant malament i
 * poc clar.
 *
 */
public class EntradaJugadorRanking extends EntradaGenerica {
	
	private int idJugador;
	private String nomJugador;
	private int totalPunts, totalPartidesAcabades, puntuacioMesAlta;
	
	private static final String NOMCOL_ID = "Id Jugador";
	private static final String NOMCOL_JUGADOR = "Nom Jugador";
	private static final String NOMCOL_PUNTUACIO = "Puntuació";
	private static final String NOMCOL_WLRATIO = "W/L Ratio";
	private static final String NOMCOL_RECORD = "Rècord";
	
	
	
	public EntradaJugadorRanking(int id, String nomJugador, int totalPunts, 
			int totalPartidesAcabades, int puntuacioMesAlta) {
		this.idJugador = id;
		this.nomJugador = nomJugador;
		this.totalPunts = totalPunts;
		this.totalPartidesAcabades = totalPartidesAcabades;
		this.puntuacioMesAlta = puntuacioMesAlta;
	}
	
	@Override
	public String toString() {
		double wlratio = (totalPartidesAcabades != 0) ? totalPunts/(double)totalPartidesAcabades : 0;
		return idJugador + " " + nomJugador + " " + totalPunts + " " + wlratio + " " + puntuacioMesAlta;
	}
	
	public int getIdJugador() {
		return idJugador;
	}
	
	public Vector<String> toVectorString() {
		return new Vector<String>(Arrays.asList(this.toString().split(" ")));
	}
	
	@Override
	public Vector<Object> toVectorType() {
		double wlratio = (totalPartidesAcabades != 0) ? totalPunts/(double)totalPartidesAcabades : 0;
		return new Vector<Object>(Arrays.asList(new Object[]{idJugador, nomJugador, totalPunts, wlratio, puntuacioMesAlta}));
	}
	
	public String getNomJugador() {
		return nomJugador;
	}
	public void setNomJugador(String nomJugador) {
		this.nomJugador = nomJugador;
	}
	public int getTotalPunts() {
		return totalPunts;
	}
	public void setTotalPunts(int totalPunts) {
		this.totalPunts = totalPunts;
	}
	public int getTotalPartidesAcabades() {
		return totalPartidesAcabades;
	}
	public void setTotalPartidesAcabades(int totalPartidesAcabades) {
		this.totalPartidesAcabades = totalPartidesAcabades;
	}
	public int getPuntuacioMesAlta() {
		return puntuacioMesAlta;
	}
	public void setPuntuacioMesAlta(int puntuacioMesAlta) {
		this.puntuacioMesAlta = puntuacioMesAlta;
	}
	
	public double getWLRatio() {
		if (getTotalPartidesAcabades() == 0)
			return 0;
		return totalPunts/(double)totalPartidesAcabades;
	}
	
	public int compareTo(EntradaJugadorRanking e) {
		e.getClass();
		return e.idJugador - idJugador;
	}
	
	/**
	 * Cada columna pot tindre un nom, aquesta ha de correspondre amb les dades
	 * 	retornades per {@link EntradaGenerica#toVectorType()}
	 * @return el vector de strings de cada columna.
	 */
	public static Vector<String> getNomColumnes() {
		return new Vector<String>(Arrays.asList(new String[]{
				NOMCOL_ID, 
				NOMCOL_JUGADOR, 
				NOMCOL_PUNTUACIO, 
				NOMCOL_RECORD, 
				NOMCOL_WLRATIO}));
	}
	
	/**
	 * Per a poder ordenar les diferents columnes, hem de poder saber com ordenar cada
	 * 	dada, aquest métode retorna per cada columna un comparador.
	 * @return un vector de comparadors on cada comparador correspon al comparador del tipus
	 * 	de cada columna.
	 */
	public static Vector<ComparadorCondicional> getComparadors() {
		return new Vector<ComparadorCondicional>(Arrays.asList(new ComparadorCondicional[]{
				EntradaGenerica.getComparadorInteger(), 
				EntradaGenerica.getComparadorString(), 
				EntradaGenerica.getComparadorInteger(), 
				EntradaGenerica.getComparadorDouble(), 
				EntradaGenerica.getComparadorInteger()}));
	}
	
	/**
	 * És possible que necessitem el tipus de cada columna en algun moment, degut a que
	 * 	treballem amb un vector de Object. Aquest métode retorna el tipus de cada objecte.
	 * @return un vector de Tipus, on cada Tipus correspon al tipus de cada columna.
	 */
	public static Vector<Class<?>> getTypes() {
		return new Vector<Class<?>>(Arrays.asList(new Class<?>[]{
				Integer.class, 
				String.class, 
				Integer.class, 
				Integer.class, 
				Double.class}));
	}
}
