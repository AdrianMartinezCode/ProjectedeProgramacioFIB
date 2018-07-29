package Domini.Aplicacio;

import java.util.Arrays;
import java.util.Vector;

import Domini.IEntitatDominiHerencia;
import Domini.Aplicacio.Utils.EntradaGenerica;
import Domini.Aplicacio.Utils.EntradaPartidaLlista;
import Domini.Joc.Dificultat;
import Domini.Joc.JocActual;
import Domini.Joc.JocBreaker;
import Domini.Joc.JocMaster;
import Domini.Joc.Rol;

public abstract class Partida implements IEntitatDominiHerencia, IDadesPartida, INotificaJocAcabat {
	
	/**
	 * El identificador de la partida.
	 */
	private int codiPartida;
	/**
	 * La dificultat de la partida, por variar entre Fàcil i Difícil.
	 */
	private Dificultat dificultat;
	/**
	 * Si la partida en té ajuts o no.
	 */
	private boolean ajuts;
	/**
	 * El nombre total de torns d'un joc (quantes línies es juguen).
	 */
	private int totalTorns;
	/**
	 * El nombre total de colors diferents que es poden jugar a una partida.
	 */
	private int totalColors;
	/**
	 * El nombre total de posicions dins de una línia (el nombre de fitxes d'una línia).
	 */
	private int totalPosicions;
	/**
	 * El nombre de jocs que una partida ha de jugar.
	 */
	private int nombreJocs;
	/**
	 * Els punts acumulats de tots els jocs jugats fins ara.
	 */
	private int puntsActuals;
	/**
	 * El número de joc jugat fins ara.
	 */
	private int tornAJoc;
	
	public static final int MAX_COLORS = 7;
	public static final int MAX_POSICIONS = 6;
	public static final int MAX_JOCS = 20;
	public static final int MAX_TORNS = 30;
	
	public static final int MIN_COLORS = 1;
	public static final int MIN_TORNS = 1;
	public static final int MIN_JOCS = 2;
	public static final int MIN_POSICIONS = 1;
	
	
	public static final String SEPARADOR = ":::";
	public static final String NULL_SEPARATOR = ";;;;";
	
	
	private Rol rolActual;
	
	// És possible que no tingui el joc carregat i sigui null aquesta referència.
	/**
	 * L'associació Joc - Partida.
	 */
	private JocActual joc;

	public Partida() {
		tornAJoc = 0;
		joc = null;
	}
	
	/**
	 * Mira si la partida ha acabat.
	 * @return cert si ha acabat, fals altrament.
	 */
	public boolean partidaAcabada() {
		return tornAJoc >= nombreJocs;
	}
	
	/**
	 * Obté els punts acumulats de la partida.
	 * @return els punts acumulats dels jocs.
	 */
	public int getPuntsPartida() {
		return puntsActuals;
	}

	
	@Override
	public EntradaGenerica getEntradaGenerica() {
		return new EntradaPartidaLlista(codiPartida, dificultat, ajuts, totalTorns, totalColors, totalPosicions, nombreJocs, puntsActuals, tornAJoc, rolActual);
	}
	
	/**
	 * Obté la descripció de cada dada retornada per {@link Partida#getDadesPartida()}
	 * @return un vector de strings, on cada string és la dada de cada partida.
	 */
	public Vector<String> getDescripcioDadesPartida() {
		Vector<String> v = new Vector<String>(Arrays.asList(new String[] {
				"ID partida", "Dificultat",
				"Ajuts", "Total torns joc",
				"Número colors", "Número posicions",
				"Nombre de jocs", "Punts acumulats",
				"Número de joc jugat", "Joc començat?",
				"Torn actual joc", "Rol jugador en el joc"
		}));
		return v;
	}
	
	/**
	 * Obté cada dada de la partida en forma de string. L'ordre correspon a
	 * 	cada entrada de {@link Partida#getDescripcioDadesPartida()}
	 * @return un vector de strings, on cada string és una dada de la partida.
	 */
	public Vector<String> getDadesPartida() {
		String ajuts = this.ajuts ? "Sí" : "No";
		String jocComencat = (this.joc == null || this.joc.jocAcabat()) ? "No" : "Sí";
		String tornJoc = (this.joc == null || this.joc.jocAcabat()) ? "- - -" : String.valueOf(joc.getTornActual());
		String rolJoc = (this.joc == null || this.joc.jocAcabat()) ? "- - -" : joc.getRol().name();
		String tornAJoc = (joc == null) ? String.valueOf(this.tornAJoc) : String.valueOf(this.tornAJoc + 1);
		Vector<String> v = new Vector<String>(Arrays.asList(new String[]{
				String.valueOf(codiPartida), dificultat.name(),
				ajuts, String.valueOf(totalTorns),
				String.valueOf(totalColors), String.valueOf(totalPosicions),
				String.valueOf(nombreJocs), String.valueOf(puntsActuals),
				tornAJoc, jocComencat,
				tornJoc, rolJoc
		}));
		return v;
	}
	
	
	@Override
	public String getNomClasseFromDades(String dades) {
		String[] strs = dades.split(SEPARADOR);
		return strs[9];
	}
	
	/**
	 * 
	 * @return el codi de la partida.
	 */
	public int getCodiPartida() {
		return codiPartida;
	}
	
	/**
	 * Continua al següent joc (elimina l'instància actual i crea una de nova), on
	 * 	els rols s'intercanviaràn. Si la partida encara no ha iniciat cap joc llavors
	 * 	el crea.
	 * @return retorna 1 si encara no ha acabat el joc, 2 si ja ha acabat la partida,
	 *  0 si tot ha anat bé, retorna 3 si ha hagut algun error al renovar el joc.
	 */
	public int seguentJoc() {
		if (tornAJoc >= nombreJocs)
			return 2;
		if (joc == null) {
			creaNouJocInicial();
			return 0;
		}
		int actTorn = joc.getTornActual();
		if (actTorn < totalTorns && !joc.jocAcabat())
			return 1;
		++tornAJoc;
		renovaJoc();
		return 0;
	}
	
	@Override
	public void notificaJocAcabat() {
		int actTorn = joc.getTornActual();
		int punts = this.ponderaPuntuacio(actTorn);
		puntsActuals += punts;
		tornAJoc++;
	}
	
	/**
	 * PRE: tornAJoc més petit que nombreJocs, joc != null
	 * Crea un nou joc i elimina l'actual, intercanviant rols.
	 */
	private void renovaJoc() {
		rolActual = joc.getRol();
		Rol[] rols = Rol.values();
		int size = rols.length;
		rolActual = rols[(rolActual.ordinal()+1)%size];
		creaInstanciaJoc();
		//String jc = joc.novaEntitatParametres(tornAJoc, new Vector<String>());
		
		/*
		 * Se suposa que al ser un nou joc, NO el deixa inconsistent ja que es crea a sí mateix
		 * 	i no requereix d'una dada externa ja que li hem passat íntegra amb lo que la entitat
		 * 	ha generat, llavors el codi d'error sempre serà 0.
		 */
		//return configuraJoc();
		//joc.obtenerFromString(jc);
	}
	
	public int getTornAJoc() {
		return tornAJoc;
	}


	/**
	 * Obté el joc de l'associació Partida-Joc
	 * @return una referència de la instància del joc, compte! Hi ha
	 * 	aliasing.
	 */
	public JocActual getJoc() {
		return joc;
	}

	
	public boolean setRolActual(Rol rolActual) {
		this.rolActual = rolActual;
		return true;
	}


	public Rol getRolActual() {
		return rolActual;
	}
	
	//private void configura
	
	/**
	 * Depenent el rol configurat en aquesta partida crearà
	 * 	una partida o una altre.
	 */
	private void creaInstanciaJoc() {
		if (rolActual == Rol.CodeBreaker)
			joc = new JocBreaker(this, this);
		else
			joc = new JocMaster(this, this);
	}


	/**
	 * Crea un nou joc per a la partida.
	 */
	private void creaNouJocInicial() {

		setRolActual(Rol.obteRolRandom());
		
		// menys l'atribut combinació mestre
		creaInstanciaJoc();
	}
	

	/**
	 * Obté una puntuació ponderada depenent del tipus de Partida que sigui, potser
	 * 	que jugar un tipus de partida específica doni més o menys punts pel ranking. 
	 * @param tornPartida s'utilitza per saber quantes rondes ha durat el joc.
	 * @return retorna la puntuació ponderada.
	 */
	public abstract int ponderaPuntuacio(int tornPartida);
	
	/**
	 * Obté el nom de la classe com a String.
	 * @return el nom de la classe incloent també els paquets.
	 */
	public abstract String getNomClasse();
	
	/**
	 * Métode a utilitzar quan es vulgui guardar una partida, aquest métode recopila
	 * totes les configuracions de la partida per a poder guardar-la. No retorna res relacionat
	 * amb el joc actual.
	 * @return la string amb les dades.
	 * 
	 * Posicions:
	 * 0:codipartida, 1:dificultat, 2:ajuts, 3:tornAJoc, 4:totalTorns, 5:totalColors, 
	 * 6:nombreJocs, 7:totalPosicions, 8:puntsActuals, 9:nombreClasse, 10:rolActual,
	 * 11:dadesjocActual
	 */	
	@Override
	public String convertirToString() {
		String ret = codiPartida + SEPARADOR;
		if (dificultat == null)
			ret += NULL_SEPARATOR;
		else
			ret += dificultat.name();
		ret += SEPARADOR;
		if (ajuts)
			ret += "si";
		else
			ret += "no";
		ret += SEPARADOR + tornAJoc + SEPARADOR + totalTorns + SEPARADOR + totalColors + SEPARADOR + nombreJocs + SEPARADOR + totalPosicions + SEPARADOR + puntsActuals;
		ret += SEPARADOR + this.getNomClasse();
		if (rolActual != null) {
			ret += SEPARADOR + rolActual.name();
			if (joc != null)
				ret += SEPARADOR + joc.convertirToString();
		}
		return ret;
	}
	@Override
	public int obtenerFromString(String vstr) {
		String[] strs = vstr.split(SEPARADOR);
		try {
			if ((!strs[1].equals(NULL_SEPARATOR) && !setDificultat(strs[1])) || 
					!setCodiPartida(Integer.valueOf(strs[0])) ||
					!setAjuts(strs[2]) ||
					!setTotalTorns(Integer.valueOf(strs[4])) ||
					!setTotalColors(Integer.valueOf(strs[5])) ||
					!setNombreJocs(Integer.valueOf(strs[6])) ||
					!setTotalPosicions(Integer.valueOf(strs[7])) ||
					!setPuntsActuals(Integer.valueOf(strs[8])) ||
					!setTornAJoc(Integer.valueOf(strs[3]))) 
			{
				netejaObjecte();
				return 2;
			}
			if (strs.length > 10) {
				if (!setRolActual(strs[10])) {
					netejaObjecte();
					return 2;
				}
				if (strs.length > 11) {
					creaInstanciaJoc();
					int codeExit = joc.obtenerFromString(strs[11]);
					if (codeExit != 0) {
						return codeExit;
					}
				}
			}
		} catch (NumberFormatException e) {
			return 1;
		}
				
		return 0;
	}
	
	@Override
	public void netejaObjecte() {
		tornAJoc = 0;
		joc = null;
		rolActual = null;
	}
	
	/**
	 * Configura el rol de la partida.
	 * @param rol La string amb el nom de Rol.
	 * @return false si la string amb el rol actual no correspon a un
	 * 	rol especificat a {@link Rol}. Altrament, és cert.
	 */
	public boolean setRolActual(String rol) {
		boolean ret = false;
		
		try {
			ret = setRolActual(Rol.valueOf(rol));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return ret;
	}
	
	@Override
	public String novaEntitatParametres(int id, Vector<String> params) {
		// Se suposa que es configuren els parámetres un a un
		if (params.size() < 0)
			return null;
		String ret = id + SEPARADOR + getDificultatDefecte().name() + SEPARADOR + getAjutsDefecte();
		ret += SEPARADOR + getTornAJocDefecte() + SEPARADOR + getTotalTornsDefecte() + SEPARADOR + getTotalColorsDefecte() + SEPARADOR + getNombreJocsDefecte() + SEPARADOR + getTotalPosicionsDefecte() + SEPARADOR + getPuntsActualsDefecte();
		ret += SEPARADOR + this.getNomClasse();
		return ret;
	}
	
	public Dificultat getDificultatDefecte() {
		return Dificultat.Baixa;
	}
	public String getAjutsDefecte() {
		return "si";
	}
	public int getTotalTornsDefecte() {
		return MIN_TORNS;
	}
	
	public int getTotalColorsDefecte() {
		return MIN_COLORS;
	}
	public int getNombreJocsDefecte() {
		return MIN_JOCS;
	}
	public int getTotalPosicionsDefecte() {
		return MIN_POSICIONS;
	}
	public int getPuntsActualsDefecte() {
		return 0;
	}
	public int getTornAJocDefecte() {
		return 0;
	}
	
	
	/**
	 * Reinicia la partida començant de nou els jocs i sense comptabilitzar cap puntuació.
	 * @return true si s'ha reiniciar bé, altrament fals, en cas de retornar fals la partida
	 * 	no s'ha reiniciat.
	 */
	public boolean reiniciaPartida() {
		int punts = puntsActuals;
		if (this.setPuntsActuals(0) && this.setTornAJoc(0)) {
			joc = null;
			return true;
		}
		this.setPuntsActuals(punts);
		return false;
	}
	
	public boolean setTornAJoc(int tornAJoc) {
		if (tornAJoc > this.nombreJocs)
			return false;
		this.tornAJoc = tornAJoc;
		return true;
	}


	
	private boolean partidaComencada() {
		return tornAJoc != 0 || joc != null;
	}

	


	public boolean setCodiPartida(int codiPartida) {
		if (partidaComencada())
			return false;
		if (codiPartida < 0)
			return false;
		this.codiPartida = codiPartida;
		return true;
	}


	public boolean setDificultat(Dificultat dificultat) {
		if (partidaComencada())
			return false;
		this.dificultat = dificultat;
		return true;
	}
	
	/**
	 * Configura la dificultat de la partida.
	 * @param dificultat La string amb el nom de la dificultat.
	 * @return false si la string amb la dificultat actual no correspon a una
	 * 	dificultat especificada a {@link Dificultat}, o bé, la partida ja ha començat.
	 * 	Altrament és cert.
	 */
	public boolean setDificultat(String dificultat) {
		boolean ret = false;
		try {
			ret = setDificultat(Dificultat.valueOf(dificultat));
		} catch (IllegalArgumentException e) {
			return false;
		}
		return ret;
	}


	private boolean setAjuts(String ajuts) {
		return (ajuts.equals("si")) ? setAjuts(true) : ((ajuts.equals("no")) ? setAjuts(false) : false);
	}
	
	/**
	 * Configura els ajuts.
	 * @param ajuts els ajuts a configurar.
	 * @return cert si s'han configurat els ajuts correctament, altrament
	 * 	és fals si ola partida ha començat.
	 */
	public boolean setAjuts(boolean ajuts) {
		if (partidaComencada())
			return false;
		this.ajuts = ajuts;
		return true;
	}

	
	public boolean setTotalTorns(int totalTorns) {
		if (partidaComencada())
			return false;
		if (totalTorns < MIN_TORNS || totalTorns > MAX_TORNS)
			return false;
		this.totalTorns = totalTorns;
		return true;
	}


	public boolean setTotalColors(int totalColors) {
		if (partidaComencada())
			return false;
		if (totalColors < MIN_COLORS || totalColors > MAX_COLORS)
			return false;
		this.totalColors = totalColors;
		return true;
	}


	public boolean setNombreJocs(int nombreJocs) {
		if (partidaComencada())
			return false;
		if (nombreJocs < MIN_JOCS || nombreJocs%2 != 0 || nombreJocs > MAX_JOCS)
			return false;
		this.nombreJocs = nombreJocs;
		return true;
	}


	public boolean setPuntsActuals(int puntsActuals) {
		if (puntsActuals < 0)
			return false;
		this.puntsActuals = puntsActuals;
		return true;
	}
	
	public boolean setTotalPosicions(int totalPosicions) {
		if (partidaComencada())
			return false;
		if (totalPosicions < MIN_POSICIONS || totalPosicions > MAX_POSICIONS)
			return false;
		this.totalPosicions = totalPosicions;
		return true;
	}
	
	/**
	 * Configura els ajuts de la partida.
	 * @param ajuts Si en té ajuts o no.
	 * @return si s'ha configurat correctament.
	 */
	public boolean configuraAjuts(boolean ajuts) {
		return setAjuts(ajuts);
	}
	/**
	 * Configura la dificultat de la partida.
	 * @param dif és la dificultat de la partida.
	 * @return si s'ha configurat correctament.
	 */
	public boolean configuraDificultat(Dificultat dif) {
		return setDificultat(dif);
	}
	public boolean configuraDificultat(String dif) {
		return setDificultat(dif);
	}
	/**
	 * Configura els colors del joc.
	 * @param colors és el número de colors diferents de la partida.
	 * @return si s'ha configurat correctament.
	 */
	public boolean configuraColors(int colors) {
		return setTotalColors(colors);
	}
	/**
	 * Configura les posicions d'un joc.
	 * @param posicions el número de fitxes d'una línia.
	 * @return si s'ha configurat correctament.
	 */
	public boolean configuraPosicions(int posicions) {
		return setTotalPosicions(posicions);
	}
	/**
	 * Configura el nombre de torns dels jocs.
	 * @param torns el nombre de línies d'un joc.
	 * @return si s'ha configurat correctament.
	 */
	public boolean configuraTorns(int torns) {
		return setTotalTorns(torns);
	}
	/**
	 * Configura el nombre de jocs d'una partida.
	 * @param nbJocs el nombre de jocs de la partida.
	 * @return si s'ha configurat correctament.
	 */
	public boolean configuraNombreJocs(int nbJocs) {
		return setNombreJocs(nbJocs);
	}
	
	@Override
	public String getSeparador() {
		return SEPARADOR;
	}
	
	/*@Override
	public int getTornActual() {
		return tornAJoc;
	}*/
	@Override
	public int getNPosicions() {
		return totalPosicions;
	}
	@Override
	public int getNColors() {
		return totalColors;
	}
	@Override
	public boolean getAjuts() {
		return ajuts;
	}
	@Override
	public Dificultat getDificultat() {
		return dificultat;
	}
	@Override
	public int getTotalTorns() {
		return totalTorns;
	}
	@Override
	public int getCodiJoc() {
		return tornAJoc;
	}
	
	/*public int getPuntsActuals() {
		return puntsActuals;
	}


	public int getNombreJocs() {
		return nombreJocs;
	}


	public int getTotalTorns() {
		return totalTorns;
	}


	public int getTotalColors() {
		return totalColors;
	}


	public int getTotalPosicions() {
		return totalPosicions;
	}*/


	/*public boolean isAjuts() {
		return ajuts;
	}*/


	/*public Dificultat getDificultat() {
		return dificultat;
	}*/
}
