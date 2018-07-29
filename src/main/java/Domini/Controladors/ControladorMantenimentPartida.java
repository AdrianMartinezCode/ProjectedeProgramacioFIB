package Domini.Controladors;

import java.util.Vector;

import Domini.Aplicacio.Partida;
import Domini.Aplicacio.PartidaEstandar;
import Domini.Aplicacio.PartidaNoEstandar;
import Domini.Joc.Dificultat;
import Domini.Joc.Rol;
import Domini.Aplicacio.Excepcions.*;
import Domini.Aplicacio.Utils.ComparadorCondicional;
import Domini.Aplicacio.Utils.EntradaPartidaLlista;
import Domini.Aplicacio.Utils.Pair;
import Persistencia.ControladorPersistencia;

public class ControladorMantenimentPartida {
	
	/**
	 * La partida que es gestionada pel controlador.
	 */
	private Partida partidaActual;
	/**
	 * La instància del controlador que gestiona jugador per poder fer operacions
	 * 	sobre el jugador (enmascara el jugador).
	 */
	private ControladorMantenimentJugador ctrlMantJug;
	/**
	 * Necessitem notificar quan cambiem de joc.
	 */
	private IJocChangeListener listener;
	/**
	 * On anirem quan volguem guardar una partida.
	 */
	private ControladorPersistencia ctrlPers;
	
	public ControladorMantenimentPartida(ControladorMantenimentJugador ctrlMantJug) {
		this.ctrlMantJug = ctrlMantJug;
		ctrlPers = ControladorPersistencia.getInstance();
	}
	
	/**
	 * @throws ExcPartidaNoSeleccionada si no s'ha seleccionat una partida encara.
	 */
	private void partidaSeleccionada() {
		if (partidaActual == null)
			throw new ExcPartidaNoSeleccionada();
	}
	
	/**
	 * Obté les posicions configurades de la partida actual.
	 * @return el nombre de posicions de la partida.
	 * @throws ExcPartidaNoSeleccionada si no s'ha seleccionat una partida encara.
	 */
	public int getNumPosicions() {
		partidaSeleccionada();
		return partidaActual.getNPosicions();
	}
	/**
	 * Obté el nombre de colors configurats a la partida actual.
	 * @return el nombre de colors de la partida.
	 * @throws ExcPartidaNoSeleccionada si no s'ha seleccionat una partida encara.
	 */
	public int getNumColors() {
		partidaSeleccionada();
		return partidaActual.getNColors();
	}
	/**
	 * Obté el nombre de torns configurats a la partida actual.
	 * @return el nombre de torns de la partida.
	 * @throws ExcPartidaNoSeleccionada si no s'ha seleccionat una partida encara.
	 */
	public int getNumTorns() {
		partidaSeleccionada();
		return partidaActual.getTotalTorns();
	}
	/**
	 * Obté els ajuts configurats a la partida actual.
	 * @return cert si hi han ajuts, fals altrament.
	 * @throws ExcPartidaNoSeleccionada si no s'ha seleccionat una partida encara.
	 */
	public boolean getAjuts() {
		partidaSeleccionada();
		return partidaActual.getAjuts();
	}
	
	/* Escenari menú 2 */
	
	/**
	 * Configura el notificable que necessitem al canviar de joc.
	 * @param listener el notificador.
	 */
	public void setIJocChangeListener(IJocChangeListener listener) {
		this.listener = listener;
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Tria la partida guardada d'un jugador per jugar en ella.
	 * @param numPartida número de la partida a carregar.
	 * @throws ExcNoTePartidesGuardades CHECKED El jugador no té partides guardades.
	 * @see ControladorMantenimentJugador#getPartidaGuardada(int)
	 */
	public void triarPartidaGuardada(int numPartida) throws ExcNoTePartidesGuardades {
		partidaActual = ctrlMantJug.getPartidaGuardada(numPartida);
		
		if (partidaActual.getJoc() != null)
			listener.changeJoc(partidaActual.getRolActual(), partidaActual.getJoc());
	}
	
	/**
	 *  -- CAS D'ÚS --
	 * Consulta les partides guardades del jugador carregat (mai és null)
	 * @return un vector amb cada dada de la partida guardada.
	 * @see ControladorMantenimentJugador#getDadesPartides()
	 */
	public Vector<String> llistarPartidesGuardades() {
		return ctrlMantJug.getDadesPartides();
	}
	
	/**
	 * Obté les dades de les partides guardades.
	 * @return un vector de vectors amb les partides guardades.
	 * @see ControladorMantenimentJugador#getDadesPartidesReals()
	 */
	public Vector<Vector<Object>> getDadesPartidesGuardades() {
		return ctrlMantJug.getDadesPartidesReals();
	}
	
	/**
	 * Obté els tipus de cada dada de les partides.
	 * @return un vector amb els tipus de cada dada de una partida.
	 * @see EntradaPartidaLlista#getTypes()
	 */
	public Vector<Class<?>> getTypesEntrades() {
		return EntradaPartidaLlista.getTypes();
	}
	
	/**
	 * Obté el nom de cada dada de les partides.
	 * @return un vector amb la string de cada descripció de la dada de una partida.
	 * @see EntradaPartidaLlista#getNomColumnes()
	 */
	public Vector<String> getNomColumnes() {
		return EntradaPartidaLlista.getNomColumnes();
	}
	 /**
	  * Obté els comparadors de cada dada de la partida.
	  * @return un vector de comparadors, en ordre de les dades d'una partida.
	  * @see EntradaPartidaLlista#getComparadors()
	  */
	public Vector<ComparadorCondicional> getComparadors() {
		return EntradaPartidaLlista.getComparadors();
	}
	
	/**
	 * Obté el index de la columna que fa referència al identificador de la partida.
	 * @return el index de la columna del identificador de la partida.
	 */
	public int getColumnIndexId() {
		return EntradaPartidaLlista.getColumnIndexId();
	}
	
	
	/**
	 * Crea una nova partida amb id + 1 de l'últim identificador de partida
	 *	del jugador escollit, i es crea la partida per aquest jugador escollit.
	 * @param classe és una string amb el nom de la classe que volem crear.
	 * @throws ExcErrorIntern UNCHECKED S'ha produït un error intern.
	 * @see ControladorMantenimentJugador#getLastIdPartidaJugadorEscollit()
	 * @see ControladorMantenimentJugador#novaPartida(int, Vector, String)
	 * @see ControladorMantenimentJugador#getPartidaGuardada(int)
	 */
	private void novaPartida(String classe) {
		
		int newId = ctrlMantJug.getLastIdPartidaJugadorEscollit();

		if (newId == -2)
			newId = 0;
		++newId;
		try {
			ctrlMantJug.novaPartida(newId, new Vector<String>(), classe);
		} catch (ExcJaExisteixPartida e) {
			throw new ExcErrorIntern("Ja existeix una partida amb aquesta id");
		} catch (ExcParametreIncorrecte e) {
			throw new ExcErrorIntern("Algun paràmetre per defecte és incorrecte");
		}
		try {
			partidaActual = ctrlMantJug.getPartidaGuardada(newId);
		} catch (ExcNoTePartidesGuardades e) {
			throw new ExcErrorIntern("Hi ha hagut un error al obtindre la partida creada.");
		}
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Crea una nova partida estàndar amb id + 1 de l'últim identificador de partida
	 *	del jugador escollit, i es crea la partida per aquest jugador escollit.
	 * @param ajuts si la nova partida estàndar és amb ajuts.
	 * @param dificultat la dificultat de la nova partida.
	 * @throws ExcFiltreNoPassat UNCHECKED Algún filtre d'un atribut de la partida no s'ha passat
	 * @throws ExcErrorIntern S'ha produït un error intern.
	 * @see ControladorMantenimentPartida#novaPartida(String)
	 */
	public void novaPartidaEstandar(boolean ajuts, String dificultat) throws ExcFiltreNoPassat {
		this.novaPartida(PartidaEstandar.class.getName());
		this.escollirAjuts(ajuts);
		this.escollirDificultat(dificultat);
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Crea una nova partida NO estàndar amb id + 1 de l'últim identificador de partida
	 *	del jugador escollit, i es crea la partida per aquest jugador escollit.
	 * @param ajuts si la nova partida estàndar és amb ajuts.
	 * @param dificultat la dificultat de la nova partida.
	 * @param colors el nombre de colors de la nova partida.
	 * @param posicions el nombre de posicions de la nova partida.
	 * @param torns el nombre de torns de la nova classe.
	 * @param nbJocs el nombre de jocs de la nova classe.
	 * @throws ExcFiltreNoPassat UNCHECKED Algún filtre d'un atribut de la partida no s'ha passat
	 * @throws ExcErrorIntern UNCHECKED S'ha produït un error intern.
	 * @see ControladorMantenimentPartida#novaPartida(String)
	 */
	public void novaPartidaPersonalitzada(boolean ajuts, String dificultat, 
			int colors, int posicions, int torns, int nbJocs) throws ExcFiltreNoPassat {
		this.novaPartida(PartidaNoEstandar.class.getName());
		this.escollirAjuts(ajuts);
		this.escollirDificultat(dificultat);
		this.escollirColors(colors);
		this.escollirNombreJocs(nbJocs);
		this.escollirTorns(torns);
		this.escollirPosicions(posicions);
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Escull si es volen ajuts a la nova partida creada.
	 * @param ajuts si es volen ajuts a la partida o no.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcFiltreNoPassat UNCHECKED No s'ha escollit correctament el paràmetre.
	 */
	public void escollirAjuts(boolean ajuts) throws ExcFiltreNoPassat {
		partidaSeleccionada();
		if (!partidaActual.setAjuts(ajuts)) {
			ctrlMantJug.eliminaPartida(partidaActual.getCodiPartida());
			throw new ExcFiltreNoPassat("Els ajuts configurats són incorrectes.");
		}
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Escull el nivell de dificultat de la partida.
	 * @param dificultat la dificultat de la màquina de la partida.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcFiltreNoPassat UNCHECKED No s'ha escollit correctament el paràmetre.
	 */
	public void escollirDificultat(String dificultat) throws ExcFiltreNoPassat {
		partidaSeleccionada();
		if (!partidaActual.setDificultat(dificultat)) {
			ctrlMantJug.eliminaPartida(partidaActual.getCodiPartida());
			throw new ExcFiltreNoPassat("La dificultat configurada és incorrecte.");
		}
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Escull el nombre de colors que tindrà la partida personalitzada.
	 * @param colors quants colors diferents es volen a la partida.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcFiltreNoPassat UNCHECKED No s'ha escollit correctament el paràmetre.
	 */
	public void escollirColors(int colors) throws ExcFiltreNoPassat {
		partidaSeleccionada();
		if (!partidaActual.setTotalColors(colors)) {
			ctrlMantJug.eliminaPartida(partidaActual.getCodiPartida());
			throw new ExcFiltreNoPassat("El nombre de colors configurats són incorrectes.");
		}
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Escull el nombre de posicions a una línia que tindrà la partida personalitzada.
	 * @param posicions el nombre de posicions que tindrà una línia de joc.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcFiltreNoPassat UNCHECKED No s'ha escollit correctament el paràmetre.
	 */
	public void escollirPosicions(int posicions) throws ExcFiltreNoPassat {
		partidaSeleccionada();
		if (!partidaActual.setTotalPosicions(posicions)) {
			ctrlMantJug.eliminaPartida(partidaActual.getCodiPartida());
			throw new ExcFiltreNoPassat("El nombre de posicions configurades són incorrectes.");
		}
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Escull el nombre total de torns de la partida personalitzada.
	 * @param torns és el nombre de torns total.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcFiltreNoPassat UNCHECKED No s'ha escollit correctament el paràmetre.
	 */
	public void escollirTorns(int torns) throws ExcFiltreNoPassat {
		partidaSeleccionada();
		if (!partidaActual.setTotalTorns(torns)) {
			ctrlMantJug.eliminaPartida(partidaActual.getCodiPartida());
			throw new ExcFiltreNoPassat("El nombre de torns configurats són incorrectes.");
		}
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Escull el nombre de jocs total de la partida personalitzada.
	 * @param nbJocs és el nombre de jocs.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcFiltreNoPassat UNCHECKED No s'ha escollit correctament el paràmetre.
	 */
	public void escollirNombreJocs(int nbJocs) throws ExcFiltreNoPassat {
		partidaSeleccionada();
		if (!partidaActual.setNombreJocs(nbJocs)) {
			ctrlMantJug.eliminaPartida(partidaActual.getCodiPartida());
			throw new ExcFiltreNoPassat("El nombre de jocs configurats són incorrectes.");
		}
	}
	
	/* Escenari partida */
	
	/**
	 *  -- CAS D'ÚS --
	 *  Continua al següent joc
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcErrorIntern UNCHECKED Ha ocorregut un error intern.
	 * @throws ExcNoHaAcabatJoc CHECKED Encara no ha acabat el joc.
	 * @throws ExcPartidaAcabada CHECKED Ja ha acabat la partida.
 
	 */
	public void continuarAlSeguentJoc()  throws ExcNoHaAcabatJoc, ExcPartidaAcabada {
		partidaSeleccionada();
		int exitCode = partidaActual.seguentJoc();
		if (exitCode == 1)
			throw new ExcNoHaAcabatJoc();
		else if (exitCode == 2)
			throw new ExcPartidaAcabada();
		else if (exitCode == 3)
			throw new ExcErrorIntern("Ha ocorregut algun error al renovar el joc.");
		listener.changeJoc(partidaActual.getRolActual(), partidaActual.getJoc());
	}
	
	/**
	 * Acaba la partida actual, {@link ControladorMantenimentJugador#acabaPartida(Partida)}
	 * @throws ExcPartidaNoSeleccionada UNCHECKED no hi ha partida seleccionada.
	 * @throws ExcPartidaNoAcabada UNCHECKED la partida seleccionada encara no ha acabat.
	 */
	public void acabarPartida() {
		partidaSeleccionada();
			
		if (!partidaActual.partidaAcabada())
			throw new ExcPartidaNoAcabada();
		ctrlMantJug.acabaPartida(partidaActual);
		partidaActual = null;
	}
	
	/**
	 * 
	 * @return "Cap" si encara no hi ha rol definit, "CodeMaster" si és master,
	 * 	"CodeBreaker" si és breaker.
	 * 
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 */
	public String getNomRol() {
		partidaSeleccionada();
		Rol r = partidaActual.getRolActual();
		if (r == null)
			return "Cap";
		return r.name();
	}

	/**
	 * -- CAS D'ÚS --
	 * Reinicia la partida actual escollida.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcErrorIntern UNCHECKED S'ha produït un error intern.
	 */
	public void reiniciaPartida() {
		partidaSeleccionada();
		if (!partidaActual.reiniciaPartida())
			throw new ExcErrorIntern("Ha ocorregut algun error a l'hora de reiniciar la partida.");
	}

	/**
	 * -- CAS D'ÚS --
	 * Guarda la partida actual.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 * @throws ExcErrorIntern UNCHECKED S'ha produït un error intern.
	 * @see ControladorMantenimentJugador#getIdJugadorEscollit()
	 */
	public void guardarPartida() {
		// Supossem que sempre retorna bé
		int idJug = ctrlMantJug.getIdJugadorEscollit();
		partidaSeleccionada();
		int exitCode = ctrlPers.guardarPartida(idJug, partidaActual.getCodiPartida(), partidaActual.convertirToString());
		if (exitCode == 1)
			throw new ExcErrorIntern("Ha ocorregut un error a la persistència a l'hora de guardar la partida.");
	}
	
	/**
	 * Obté les dades de la partida seleccionada.
	 * @return un vector de strings on cada string és una dada concreta.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 */
	public Vector<String> getDadesPartida() {
		partidaSeleccionada();
		return partidaActual.getDadesPartida();
	}
	
	/**
	 * Obté les descripcions de les dades de la partida, en el mateix
	 * 	ordre en que es donen les dades de {@link ControladorMantenimentPartida#getDadesPartida()}
	 * @return un vector de strings, on cada string és una descripció d'una dada.
	 * @throws ExcPartidaNoSeleccionada UNCHECKED No hi ha partida sel·leccionada.
	 */
	public Vector<String> getDescripcioDadesPartida() {
		partidaSeleccionada();
		return partidaActual.getDescripcioDadesPartida();
	}
	
	
	/**
	 * Obté el nom de la dificultat alta.
	 * @return el nom de la dificultat alta.
	 */
	public String getNomDificultatAlta() {
		return Dificultat.Alta.name();
	}
	
	/**
	 * Obté el nom de la dificultat baixa.
	 * @return el nom de la dificultat baixa.
	 */
	public String getNomDificultatBaixa() {
		return Dificultat.Baixa.name();
	}

	/**
	 * Obté el mínim i el màxim dels colors que es poden configurar a una partida.
	 * @return un parell on el primer valor és el mínim i el segon valor el màxim.
	 */
	public Pair<Integer, Integer> getMinMaxColors() {
		return new Pair<>(Partida.MIN_COLORS, Partida.MAX_COLORS);
	}
	/**
	 * Obté el mínim i el màxim de les posicions que es poden configurar a una partida.
	 * @return un parell on el primer valor és el mínim i el segon valor el màxim.
	 */
	public Pair<Integer, Integer> getMinMaxPosicions() {
		return new Pair<>(Partida.MIN_POSICIONS, Partida.MAX_POSICIONS);
	}
	/**
	 * Obté el mínim i el màxim dels torns que es poden configurar a una partida.
	 * @return un parell on el primer valor és el mínim i el segon valor el màxim.
	 */
	public Pair<Integer, Integer> getMinMaxTorns() {
		return new Pair<>(Partida.MIN_TORNS, Partida.MAX_TORNS);
	}
	/**
	 * Obté el mínim i el màxim del nombre de jocs que es poden configurar a una partida.
	 * @return un parell on el primer valor és el mínim i el segon valor el màxim.
	 */
	public Pair<Integer, Integer> getMinMaxNbJocs() {
		return new Pair<>(Partida.MIN_JOCS, Partida.MAX_JOCS);
	}
}
