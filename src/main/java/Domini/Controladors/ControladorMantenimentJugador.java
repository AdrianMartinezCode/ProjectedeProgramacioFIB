package Domini.Controladors;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import Domini.Aplicacio.Jugador;
import Domini.Aplicacio.Partida;
import Domini.Aplicacio.PartidaEstandar;
import Domini.Aplicacio.PartidaNoEstandar;
import Domini.Aplicacio.Utils.ComparadorCondicional;
import Domini.Aplicacio.Utils.EntradaGenerica;
import Domini.Aplicacio.Utils.EntradaJugadorRanking;
import Domini.Aplicacio.Excepcions.*;
import Persistencia.ControladorPersistencia;

public class ControladorMantenimentJugador {
	
	/**
	 * Controlador genèric per a jugadors.
	 */
	private CtrlDominiMantGeneric<Jugador> ctrlDomMantGenJug;
	/**
	 * Associació de controladors per partides, cada controlador identificat
	 * 	per la id del jugador, per tant, cada controlador diguem que pertany
	 * 	a un jugador i conté les seves partides guardades.
	 */
	private Map<Integer, CtrlDomMantGenericMulticlass<Partida> > ctrlsDomMantGenPart;
	/**
	 * La instància del controlador de persistència.
	 */
	private ControladorPersistencia ctrlPers;
	/**
	 * El jugador escollit actualment, si no n'hi ha, serà més petit que 0.
	 */
	private int jugadorEscollit;
	
	/**
	 * Instàncies de les classes que utilitzarem al controlador genèric
	 * 	multiclass, la key és el nom de la classe com si es fes un
	 * 	Partida.class.getName() i el valor és la instància.
	 */
	private Map<String, Partida> classes;
	
	/**
	 * Boleà que indica si els jugadors han sigut carregats.
	 */
	private boolean jugadorsCarregats;
	/**
	 * Boleà que indica si les partides han sigut carregades als jugadors.
	 */
	private boolean partidesCarregades;
	
	
	public ControladorMantenimentJugador(){
		ctrlDomMantGenJug = new CtrlDominiMantGeneric<Jugador>(new Jugador());
		classes = new TreeMap<String, Partida>();
		classes.put(PartidaNoEstandar.class.getName(), new PartidaNoEstandar());
		classes.put(PartidaEstandar.class.getName(), new PartidaEstandar());
		ctrlsDomMantGenPart = new TreeMap<Integer, CtrlDomMantGenericMulticlass<Partida> >();
		ctrlPers = ControladorPersistencia.getInstance();
		
		jugadorEscollit = -1;
		jugadorsCarregats = false;
		partidesCarregades = false;
	}
	
	/**
	 * @throws ExcJugadorNoEscollit si no s'ha escollit el jugador.
	 */
	private void jugadorEscollit() {
		if (jugadorEscollit < 0)
			throw new ExcJugadorNoEscollit();
	}
	/**
	 * @throws ExcJugadorsNoCarregats si no s'han carregat els jugadors.
	 */
	private void jugadorsCarregats() {
		if (!jugadorsCarregats)
			throw new ExcJugadorsNoCarregats();
	}
	/**
	 * @throws ExcPartidesNoCarregades si no s'han carregat les partides.
	 */
	private void partidesCarregades() {
		if (!partidesCarregades)
			throw new ExcPartidesNoCarregades();
	}
	
	/**
	 * Afegeix punts al jugador escollit.
	 * @param punts els punts a afegir al jugador.
	 */
	private void afegeixPuntsJugador(int punts) {
		ctrlDomMantGenJug.getEntitat(jugadorEscollit).afegirPuntsPartidaAcabada(punts);
	}
	
	/**
	 * Acaba la partida de forma correcta:
	 * 	<ul><li>Afegeix punts al jugador</li><li>Elimina la partida de la persistència</li>
	 * 	<li>Elimina la partida del jugador</li><li>Elimina la partida del controlador genèric
	 * 	de gestió de partides del jugador</li><li>Actualitza el jugador a la persistència</li></ul>
	 * @param p la partida a acabar.
	 * 
	 * @throws ExcJugadorsNoCarregats UNCHECKED si no s'han carregat els jugadors.
	 * @throws ExcJugadorsNoCarregats UNCHECKED si no s'han carregat els jugadors.
	 * @throws ExcJugadorNoEscollit UNCHECKED si no s'ha escollit el jugador.
	 */
	protected void acabaPartida(Partida p) {
		jugadorsCarregats();
		partidesCarregades();
		jugadorEscollit();
		
		afegeixPuntsJugador(p.getPuntsPartida());
		ctrlPers.eliminaPartida(jugadorEscollit, p.getCodiPartida());
		ctrlDomMantGenJug.getEntitat(jugadorEscollit).eliminaPartida(p.getCodiPartida());
		ctrlsDomMantGenPart.get(jugadorEscollit).baixaEntitat(p.getCodiPartida());
		ctrlPers.actualitzaJugador(jugadorEscollit, ctrlDomMantGenJug.getStringEntitat(jugadorEscollit));
	}
	
	/**
	 * Consulta l'última id de totes les partides del jugador escollit.
	 * @return la id mencionada, si no té partides guardades retorna -2
	 * 
	 * @throws ExcJugadorNoEscollit UNCHECKED No hi ha jugador escollit.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcPartidesNoCarregades UNCHECKED No s'han carregat les partides.
	 */
	public int getLastIdPartidaJugadorEscollit() {
		jugadorsCarregats();
		partidesCarregades();
		jugadorEscollit();
		CtrlDomMantGenericMulticlass<Partida> p = ctrlsDomMantGenPart.get(jugadorEscollit);
		int idPart = p.getLastIdentifier();
		if (idPart == -1)
			return -2;
		
		return idPart;
	}
	
	/**
	 * Es carreguen tots els jugadors guardats a la persistència. S'esborren els que ja havien al domini.
	 * 
	 * @throws ExcErrorPersistencia UNCHECKED Ha ocorregut algun error a la persistència.
	 * @throws ExcErrorDomini UNCHECKED Ha ocorregut algun error al domini al intenar donar d'alta una entitat.
	 */
	public void carregaJugadors() {
		ctrlDomMantGenJug.cleanEntitats();
		Map<Integer, String> dadesJugs = ctrlPers.getDadesJugadors();
		if (dadesJugs == null)
			throw new ExcErrorPersistencia("No s'ha pogut carregar cap dada dels jugadors, és possible que l'estructura del directori sigui incorrecta.");
		for (Entry<Integer, String> ent : dadesJugs.entrySet()) {
			int codiExit = ctrlDomMantGenJug.altaEntitat(ent.getKey(), ent.getValue());
			if (codiExit != 0) {
				jugadorsCarregats = false;
				String msg = "";
				if (codiExit == 1)
					msg = " Id d'algun jugador repetit";
				else if (codiExit == 2)
					msg = " El nom de carpeta no correspon a la id del fitxer de text";
				else if (codiExit == 4)
					msg = " Hi ha algun error en el format de les dades";
				else if (codiExit == 5)
					msg = " Algun filtre de l'entitat no ha passat";
				throw new ExcErrorDomini("Error al intentar carregar un jugador al domini." + msg + " ID jugador: " + ent.getKey());
			}
			CtrlDomMantGenericMulticlass<Partida> ctrlJug = new CtrlDomMantGenericMulticlass<Partida>(classes);
			ctrlsDomMantGenPart.put(ent.getKey(), ctrlJug);
		}
		jugadorsCarregats = true;
	}
	
	/**
	 * Diu si el jugador ha sigut escollit.
	 * @return true si s'ha escollit, fals si no.
	 * 
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 */
	public boolean hiHaJugadorEscollit() {
		jugadorsCarregats();
		try {
			jugadorEscollit();
		} catch (ExcJugadorNoEscollit e) {
			return false;
		}
		return true;
	}
	
	/**
	 * @return retorna la id del jugador escollit
	 * 
	 * @throws ExcJugadorNoEscollit UNCHECKED No hi ha jugador escollit.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 */
	public int getIdJugadorEscollit() {
		jugadorEscollit();
		jugadorsCarregats();
		return jugadorEscollit;
	}
	
	/**
	 * @param part identificador de la partida guardada del jugador escollit.
	 * @return retorna la instància de la partida (hi ha aliasing) amb identificador
	 * 	part del jugador carregat, si és null, és que no existeix al domini.
	 * @throws ExcJugadorNoEscollit UNCHECKED No hi ha jugador escollit.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcPartidesNoCarregades UNCHECKED No s'han carregat les partides.
	 * @throws ExcNoTePartidesGuardades CHECKED El jugador no té partides guardades.
	 */
	protected Partida getPartidaGuardada(int part) throws ExcNoTePartidesGuardades {
		jugadorsCarregats();
		partidesCarregades();
		jugadorEscollit();
		
		CtrlDomMantGenericMulticlass<Partida> ctrlPart = ctrlsDomMantGenPart.get(jugadorEscollit);
		// Mai hauria de ser null
		if (ctrlPart.numEntitatsEmmagatzemades() == 0)
			throw new ExcNoTePartidesGuardades();
		
		return ctrlPart.getEntitat(part);
	}

	
	/**
	 * Carrega les partides guardades a persistència a tots els jugadors. Esborra totes les partides que
	 * 	hi han carregades al domini encara que no s'hagin guardat!!!
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcErrorPersistencia UNCHECKED Ha ocorregut algun error a la persistència.
	 * @throws ExcErrorDomini UNCHECKED Ha ocorregut algun error al domini al intenar donar d'alta una entitat.
	 */
	public void carregaPartidesGuardadesAJugadors() {
		jugadorsCarregats();
		for (int id : ctrlDomMantGenJug.getKeys()) {
			// No pot donar-se que sigui null això, ctrlJug
			CtrlDomMantGenericMulticlass<Partida> ctrlJug = ctrlsDomMantGenPart.get(id);
			ctrlJug.cleanEntitats();
			Map<Integer, String> partidesGuardadesStr = ctrlPers.getPartidesGuardades(id);
			if (partidesGuardadesStr == null)
				throw new ExcErrorPersistencia("Error al intentar obtindre les partides guardades de fitxers.");
			for (Entry<Integer, String> etr : partidesGuardadesStr.entrySet()) {
				int codiExit = -1;
				try {
					codiExit = ctrlJug.altaEntitat(etr.getKey(), etr.getValue());
				} catch (ExcErrorDomini e) {
					throw new ExcErrorDomini(e.getMessage() + ", ID jugador: " + id);
				}
				if (codiExit != 0) {
					partidesCarregades = false;
					String msg = "";
					if (codiExit == 1)
						msg = " Id d'alguna partida d'un jugador repetit";
					else if (codiExit == 2)
						msg = " El nom de carpeta no correspon a la id del fitxer de text";
					else if (codiExit == 4)
						msg = " Hi ha algun error en el format de les dades.";
					else if (codiExit == 5)
						msg = " Algun filtre de l'entitat no ha passat";
					throw new ExcErrorDomini("Error al intentar carregar una partida d'un jugador al domini." + msg + " ID Partida: " + etr.getKey() + "; ID Jugador: " + id);
				}
				ctrlDomMantGenJug.getEntitat(id).afegirPartida(ctrlJug.getEntitat(etr.getKey()));
			}
		}
		partidesCarregades = true;
	}
	
	/**
	 * Obté la descripció de cada columna de dades de cada partida.
	 * @return retorna un vector amb les entrades dades partida del jugador carregat.
	 * @throws ExcJugadorNoEscollit UNCHECKED No hi ha jugador escollit.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcPartidesNoCarregades UNCHECKED No s'han carregat les partides.
	 */
	public Vector<String> getDadesPartides() {
		//if (jugadorEscollit <= 0)
		//	return null;
		jugadorsCarregats();
		partidesCarregades();
		jugadorEscollit();
		return ctrlsDomMantGenPart.get(jugadorEscollit).consultaEntitats();
	}
	
	/**
	 * Obté les dades de cada jugador, un vector de les dades de cada jugador,
	 * 	on cada entrada del vector és un altre vector de dades, on cada dada és
	 * 	un valor únic {@link EntradaGenerica#toVectorType()}
	 * @return retorna un vector de vectors de les dades de cada jugador.
	 * @throws ExcJugadorNoEscollit UNCHECKED No hi ha jugador escollit.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcPartidesNoCarregades UNCHECKED No s'han carregat les partides.
	 */
	public Vector<Vector<Object>> getDadesPartidesReals() {
		jugadorsCarregats();
		partidesCarregades();
		jugadorEscollit();
		return ctrlsDomMantGenPart.get(jugadorEscollit).getEntradesDades();
		
	}
	
	
	
	/** --- CAS D'ÚS ---
	 * Escull el jugador nomJ (realment crea la instància en base
	 * 	a les dades de l'arxiu que existeixen, i assigna aquesta instància
	 * a jugador carregat). NO CARREGA PARTIDES AL JUGADOR ESCOLLIT!!
	 * @param idJug és el jugador a escollir.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcNoExisteixJugador CHECKED No existeix el jugador amb id jug
	 */
	public void escollirJugador(int idJug) throws ExcNoExisteixJugador {
		jugadorsCarregats();
		if (!ctrlDomMantGenJug.existeixEntitat(idJug))
			throw new ExcNoExisteixJugador();
		jugadorEscollit = idJug;
	}
	
	/** --- CAS D'ÚS ---
	 * Crea el jugador nomJ, amb tots els seus atributs a 0 menys el nom.
	 * @param id és la id del jugador a crear.
	 * @param nomJ és el nom del jugador a crear.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcErrorPersistencia UNCHECKED Ha ocorregut algun error a la persistència.
	 * @throws ExcErrorDomini UNCHECKED Ha ocorregut algun error al domini al intenar donar d'alta una entitat.
	 * @throws ExcJaExisteixJugador CHECKED Ja existeix el jugador amb aquesta id.
	 */
	public void crearJugador(int id, String nomJ) throws ExcJaExisteixJugador {
		jugadorsCarregats();
		Vector<String> params = new Vector<String>();
		params.add(nomJ);
		int codiExit = ctrlDomMantGenJug.altaNovaEntitat(id, params);
		
		if (codiExit != 0) {
			String msg = "";
			if (codiExit == 1)
				throw new ExcJaExisteixJugador();
				//msg = "Ja existeix un jugador amb aquesta id al domini.";
			else if (codiExit == 2)
				msg = "Id és diferent al inserit a la string de dades. (Error intern, mirar la implementació de altaNovaEntitat de jugador).";
			else if (codiExit == 3)
				msg = "El vector params és incorrecte.";
			else if (codiExit == 4)
				msg = "Hi ha algun error en el format de les dades.";
			else if (codiExit == 5)
				msg = "Algun filtre de l'entitat no ha passat.";
			throw new ExcErrorDomini("Error al intentar donar d'alta un jugador. " + msg);
		}
		codiExit = ctrlPers.crearJugador(id, ctrlDomMantGenJug.getStringEntitat(id)) * -1;
		if (codiExit != 0) {
			ctrlDomMantGenJug.baixaEntitat(id);
			String msg = "";
			if (codiExit == 1)
				msg = "Ja existeix un jugador a la persistència amb aquest nom.";
			else if (codiExit == 2)
				msg = "Hi ha hagut un error en la sortida als fitxers.";
			throw new ExcErrorPersistencia("No s'ha pogut completar l'operació." + msg);
		}
		ctrlsDomMantGenPart.put(id, new CtrlDomMantGenericMulticlass<Partida>(classes));
		// L'entitat existeix segur.
		try {
			escollirJugador(id);
		} catch (ExcNoExisteixJugador e) {
			// Improbable que passi, és una contradicció que passi ja que l'acabem de crear i
			//	resideix a memòria, altrament hauria donat una fallada
			e.printStackTrace();
		}
	}
	
	/**
	 * Dona d'alta una entitat partida nova amb els paràmetres per defecte.
	 * @param id identificador de la partida.
	 * @param params paràmetres adicionals del tipus de la partida.
	 * @param cl nom de la classe de l'objecte a crear.
	 * @throws ExcJugadorNoEscollit UNCHECKED No hi ha jugador escollit.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcPartidesNoCarregades UNCHECKED No s'han carregat les partides.
	 * @throws ExcErrorDomini UNCHECKED Ha ocorregut algun error al domini al intenar donar d'alta una entitat.
	 * @throws ExcJaExisteixPartida CHECKED Ja existeix una partida amb aquesta id.
	 * @throws ExcParametreIncorrecte CHECKED Un dels paràmetres adicionals de la partida és incorrecte.
	 */
	protected void novaPartida(int id, Vector<String> params, String cl) throws ExcJaExisteixPartida, 
			ExcParametreIncorrecte
	{
		jugadorsCarregats();
		partidesCarregades();
		jugadorEscollit();
		
		CtrlDomMantGenericMulticlass<Partida> ctrlPart = ctrlsDomMantGenPart.get(jugadorEscollit);
		int code = ctrlPart.altaNovaEntitat(id, params, cl);
		if (code != 0) {
			String msg = "";
			if (code == 1)
				throw new ExcJaExisteixPartida();
			else if (code == 3)
				throw new ExcParametreIncorrecte();
			else if (code == 4)
				msg = "Error en el format de les dades";
			else if (code == 5)
				msg = "Error en algun filtre de les dades.";
			else if (code == 6)
				msg = "La classe especificada no existeix per a partida";
			else if (code == 2)
				msg = "La id especificada és diferent al que conté les dades generades per altaNovaEntitat.";
			throw new ExcErrorDomini("Error en el domini. " + msg);
		}
	}
	
	/**
	 * Elimina una partida del domini (i només del domini)
	 * @param id la id de la partida a esborrar.
	 * @throws ExcJugadorNoEscollit UNCHECKED No hi ha jugador escollit.
	 * @throws ExcJugadorsNoCarregats UNCHECKED No s'han carregat els jugadors.
	 * @throws ExcPartidesNoCarregades UNCHECKED No s'han carregat les partides.
	 * @throws ExcPartidaNoExisteix UNCHECKED no existeix cap partida amb aquesta id
	 */
	protected void eliminaPartida(int id) {
		jugadorsCarregats();
		partidesCarregades();
		jugadorEscollit();
		CtrlDomMantGenericMulticlass<Partida> ctrlPart = ctrlsDomMantGenPart.get(jugadorEscollit);
		int errCode = ctrlPart.baixaEntitat(id);
		if (errCode == 1)
			throw new ExcPartidaNoExisteix();
	}
	
	
	/**
	 * Obté les dades de cada jugador, un vector on cada entrada
	 * 	són les dades d'un jugador, i en cada entrada del vector
	 * 	és un vector amb cada entrada és una dada del jugador.
	 * @return vector de vectors de les dades dels jugadors.
	 */
	public Vector<Vector<Object>> getDadesJugadors() {
		jugadorsCarregats();
		Vector<Vector<Object>> vec = new Vector<Vector<Object>>();
		for (int id : ctrlDomMantGenJug.getKeys()) {
			Jugador j = ctrlDomMantGenJug.getEntitat(id);
			EntradaJugadorRanking ent = j.obteDadesRanking();
			vec.add(ent.toVectorType());
		}
		return vec;
	}
	
	/**
	 * {@link EntradaJugadorRanking#getNomColumnes()}
	 * @return un vector amb els noms de columna.
	 */
	public Vector<String> obtindreNomColumnesRanking() {
		return EntradaJugadorRanking.getNomColumnes();
	}
	
	/**
	 * {@link EntradaJugadorRanking#getComparadors()}
	 * @return un vector amb els comparadors condicionals de les columnes.
	 */
	public Vector<ComparadorCondicional> getComparadors() {
		return EntradaJugadorRanking.getComparadors();
	}
	
	/**
	 * {@link EntradaJugadorRanking#getTypes()}
	 * @return un vector amb els Tipus de dades de cada columna.
	 */
	public Vector<Class<?>> getTypesDadesJugadors() {
		return EntradaJugadorRanking.getTypes();
	}
}
