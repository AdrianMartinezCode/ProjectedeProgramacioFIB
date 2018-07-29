package Domini.Aplicacio.Utils;

import java.util.Arrays;
import java.util.Vector;

import Domini.Joc.Dificultat;
import Domini.Joc.Rol;

public class EntradaPartidaLlista extends EntradaGenerica {
	
	private int codiPartida;
	private Dificultat dificultat;
	private boolean ajuts;
	private int totalTorns;
	private int totalColors;
	private int totalPosicions;
	private int nombreJocs;
	private int puntsActuals;
	private int tornAJoc;
	
	private Rol rolActual;
	
	private static final String NOMCOL_ID = "Codi Partida";
	private static final String NOMCOL_DIF = "Dificultat";
	private static final String NOMCOL_AJUTS = "Ajuts";
	private static final String NOMCOL_TOTALTORNS = "Torns";
	private static final String NOMCOL_TOTALCOLORS = "Colors";
	private static final String NOMCOL_TOTALPOSICIONS = "Posicions";
	private static final String NOMCOL_NOMBREJOCS = "Nº Jocs";
	private static final String NOMCOL_PUNTSACTUALS = "Punts actuals";
	private static final String NOMCOL_TORNAJOC = "Torn actual joc";
	private static final String NOMCOL_ROLACTUAL = "Rol en joc actual";
	
	public EntradaPartidaLlista(int codiPartida, Dificultat dificultat, boolean ajuts,
				int totalTorns, int totalColors, int totalPosicions, int nombreJocs,
				int puntsActuals, int tornAJoc, Rol rolActual) {
		this.codiPartida = codiPartida;
		this.dificultat = dificultat;
		this.ajuts = ajuts;
		this.totalTorns = totalTorns;
		this.totalColors = totalColors;
		this.totalPosicions = totalPosicions;
		this.nombreJocs = nombreJocs;
		this.puntsActuals = puntsActuals;
		this.tornAJoc = tornAJoc;
		this.rolActual = rolActual;
	}
	
	@Override
	public Vector<Object> toVectorType() {
		return new Vector<Object>(Arrays.asList(new Object[]{
				codiPartida,
				dificultat,
				ajuts,
				totalTorns,
				totalColors,
				totalPosicions,
				nombreJocs,
				puntsActuals,
				tornAJoc,
				rolActual
		}));
		
	}
	
	/**
	 * Obté el índex de la columna que fa referència a la ID de la partida.
	 * @return el índex de la columna que fa referència a la id de la partida.
	 */
	public static int getColumnIndexId() {
		return 0;
	}
	

	/**
	 * Cada columna pot tindre un nom, aquesta ha de correspondre amb les dades
	 * 	retornades per {@link EntradaGenerica#toVectorType()}
	 * @return el vector de strings de cada columna.
	 */
	public static Vector<String> getNomColumnes() {
		return new Vector<String>(Arrays.asList(new String[]{
				NOMCOL_ID,
				NOMCOL_DIF,
				NOMCOL_AJUTS,
				NOMCOL_TOTALTORNS,
				NOMCOL_TOTALCOLORS,
				NOMCOL_TOTALPOSICIONS,
				NOMCOL_NOMBREJOCS,
				NOMCOL_PUNTSACTUALS,
				NOMCOL_TORNAJOC,
				NOMCOL_ROLACTUAL
		}));
	}
	
	/**
	 * Per a poder ordenar les diferents columnes, hem de poder saber com ordenar cada
	 * 	dada, aquest métode retorna per cada columna un comparador.
	 * @return un vector de comparadors on cada comparador correspon al comparador del tipus
	 * 	de cada columna.
	 */
	public static Vector<ComparadorCondicional> getComparadors() {
		return new Vector<ComparadorCondicional>(Arrays.asList(new ComparadorCondicional[]{
			getComparadorInteger(),
			getComparadorDificultat(),
			getComparadorBoolean(),
			getComparadorInteger(),
			getComparadorInteger(),
			getComparadorInteger(),
			getComparadorInteger(),
			getComparadorInteger(),
			getComparadorInteger(),
			getComparadorRol()
		}));
	}

	/**
	 * És possible que necessitem el tipus de cada columna en algun moment, degut a que
	 * 	treballem amb un vector de Object. Aquest métode retorna el tipus de cada objecte.
	 * @return un vector de Tipus, on cada Tipus correspon al tipus de cada columna.
	 */
	public static Vector<Class<?>> getTypes() {
		return new Vector<Class<?>>(Arrays.asList(new Class<?>[]{
			Integer.class,
			Dificultat.class,
			Boolean.class,
			Integer.class,
			Integer.class,
			Integer.class,
			Integer.class,
			Integer.class,
			Integer.class,
			Rol.class
		}));
	}

}
