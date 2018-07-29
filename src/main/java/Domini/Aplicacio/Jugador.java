package Domini.Aplicacio;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import Domini.IEntitatDomini;
import Domini.Aplicacio.Utils.EntradaGenerica;
import Domini.Aplicacio.Utils.EntradaJugadorRanking;

public class Jugador implements IEntitatDomini{
	
	/**
	 * Identificador del jugador.
	 */
	private int idJugador;
	/**
	 * Nom visible del jugador.
	 */
	private String nomJugador;
	/**
	 * Punts totals acumulats.
	 */
	private int totalPunts;
	/**
	 * Total de partides computables acabades.
	 */
	private int totalPartidesAcabades;
	/**
	 * Puntuació més alta (rècord).
	 */
	private int puntuacioMesAlta;
	public static final String SEPARADOR = " ";
	
	/**
	 * Associació TéGuardades entre Jugador i Partides.
	 */
	private Vector<Partida> partidesGuardades;

	public Jugador() {
		partidesGuardades = new Vector<Partida>();
	}
	
	/**
	 * Afegeix una partida guardada al jugador.
	 * Genera aliasing!
	 * @param p la partida a guardar.
	 */
	public void afegirPartida(Partida p) {
		partidesGuardades.add(p);
	}
	
	/**
	 * Obté les dades i l'identificador de totes les partides guardades
	 * del jugador.
	 * @return un diccionari amb el format de tupla següent:
	 * 	{codi partida, dades partida}, si no hi han partides
	 * 	guardades es retorna {-1, "No hi han partides guardades"}
	 */
	public Map<Integer, String> getDadesPartides() {
		Map<Integer, String> ret = new HashMap<Integer, String>();
		if (partidesGuardades != null) {
			for (Partida p : partidesGuardades) {
				ret.put(p.getCodiPartida(), p.convertirToString());
			}
		} else
			ret.put(-1, "No hi han partides guardades");
		return ret;
	}
	

	@Override
	public String convertirToString() {
		String ret = Integer.valueOf(idJugador) + SEPARADOR + nomJugador + SEPARADOR;
		ret += String.valueOf(totalPunts) + SEPARADOR + String.valueOf(totalPartidesAcabades) + SEPARADOR;
		ret += String.valueOf(puntuacioMesAlta);
		return ret;
	}

	@Override
	public int obtenerFromString(String vstr) {
		String[] strs = vstr.split(" ");
		try {
			if (!setIdJugador(Integer.valueOf(strs[0])) || 
					!setNomJugador(strs[1]) ||
					!setTotalPunts(Integer.valueOf(strs[2])) ||
					!setTotalPartidesAcabades(Integer.valueOf(strs[3])) ||
					!setPuntuacioMesAlta(Integer.valueOf(strs[4])))
			{
				return 2;
			}
		} catch (NumberFormatException e) {
			return 1;
		}
		return 0;
	}

	@Override
	public Jugador miClone() {
		Jugador j = new Jugador();
		j.obtenerFromString(this.convertirToString());
		return j;
	}


	public boolean setIdJugador(int idJugador) {
		if (idJugador < 0)
			return false;
		this.idJugador = idJugador;
		return true;
	}


	public boolean setNomJugador(String nomJugador) {
		this.nomJugador = nomJugador;
		return true;
	}


	public boolean setTotalPunts(int totalPunts) {
		this.totalPunts = totalPunts;
		return true;
	}
	
	/**
	 * Afegeix punts al jugador, la partida ha d'haver acabat per afegir aquests punts.
	 * @param punts els punts a afegir.
	 */
	public void afegirPuntsPartidaAcabada(int punts) {
		if (punts > puntuacioMesAlta)
			puntuacioMesAlta = punts;
		totalPartidesAcabades++;
		this.totalPunts += punts;
	}


	public boolean setTotalPartidesAcabades(int totalPartidesAcabades) {
		if (totalPartidesAcabades < 0)
			return false;
		this.totalPartidesAcabades = totalPartidesAcabades;
		return true;
	}


	public boolean setPuntuacioMesAlta(int puntuacioMesAlta) {
		this.puntuacioMesAlta = puntuacioMesAlta;
		return true;
	}
	
	

	@Override
	public String novaEntitatParametres(int id, Vector<String> params) {
		if (params.size() == 0)
			return null;
		return String.valueOf(id) + " " + params.get(0) + " 0 0 0";
	}

	@Override
	public String getSeparador() {
		return SEPARADOR;
	}

	@Override
	public void netejaObjecte() {
		partidesGuardades = new Vector<Partida>();
	}

	@Override
	public EntradaGenerica getEntradaGenerica() {
		return new EntradaJugadorRanking(idJugador, nomJugador, totalPunts, totalPartidesAcabades, puntuacioMesAlta);
	}
	
	/**
	 * Construeix una classe auxiliar contenidora de dades només, facilita la creació
	 * 	d'un ranking ordenat per l'entrada que es vulgui.
	 * @return la classe contenidora de les entrades: idJugador, nomJugador, totalPunts,
	 * 	totalPartidesAcabades, puntuacióMesAlta
	 */
	public EntradaJugadorRanking obteDadesRanking() {
		return new EntradaJugadorRanking(idJugador, nomJugador, totalPunts, totalPartidesAcabades, puntuacioMesAlta);

	}
	
	/**
	 * Elimina la partida amb idPart de la relació partidesGuardades
	 * @param idPart la id de la partida a eliminar de la relació.
	 */
	public void eliminaPartida(int idPart){
		for (int i = 0; i < partidesGuardades.size(); i++) {
			if (partidesGuardades.get(i).getCodiPartida() == idPart) {
				partidesGuardades.remove(i);
				return;
			}
		}
	}

}
