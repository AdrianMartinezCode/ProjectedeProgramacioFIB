package Persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

public class ControladorPersistencia {
	
	/**
	 * Degut al patró Singleton.
	 */
	private static ControladorPersistencia instance;
	
	private static final String PATH = ".";
	private static final String MAIN_DIR = "MasterMindPers";
	
	private static final String JUG_FILE_INFO = "info.txt";
	private static final String PART_FILE_INFO = "info.txt";
	
	private ControladorPersistencia() {}
	
	/**
	 * Obtenció de la instància del controlador.
	 * @return la instància del controlador.
	 */
	public static ControladorPersistencia getInstance() {
		if (instance == null)
			instance = new ControladorPersistencia();
		return instance;
	}
	
	/**
	 * Mira a veure si està creat el conjunt de carpetes i fitxers de l'aplicació
	 * necessaris per funcionar correctament, i si no ho estàn, els crea.
	 * @return retorna 0 si ha funcionat correctament, 1 altrament.
	 */
	public int inicialitzaPersistencia() {
		File f = new File(MAIN_DIR);
		if (!f.exists() || !f.isDirectory())
			if (!f.mkdirs())
				return 1;
		return 0;
	}
	
	
	/**
	 * Guarda una partida d'un jugador.
	 * @param idJugador id del jugador.
	 * @param idPartida id de la partida.
	 * @param partida les dades de la partida.
	 * @return retorna 0 si s'ha guardat correctament, altrament 1
	 */
	public int guardarPartida(int idJugador, int idPartida, String partida) {
		File f = new File(MAIN_DIR + "/" + String.valueOf(idJugador) + "/" + String.valueOf(idPartida));
		if (!f.exists() || !f.isDirectory())
			if (!f.mkdirs())
				return 1;
		File finfo = new File(f.getPath() + "/" + PART_FILE_INFO);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(finfo))) {
			bw.write(partida);
			
		} catch (IOException e) {
			return 1;
		}
		return 0;
	}
	
	/**
	 * 
	 * @return retorna un diccionari de clau i string, on per cada línea hi han les dades: 
	 * 	codi jugador i l'string conté totes les dades del jugador, o retorna null en cas
	 * 	que hi hagi algun error a l'hora de llegir les dades.
	 */
	public Map<Integer, String> getDadesJugadors() {
		TreeMap<Integer, String> jugadors = new TreeMap<Integer, String>();
		File[] flist = new File(MAIN_DIR).listFiles();
		for(File f : flist) {
			if (f.isDirectory()) {
				try (BufferedReader buf = new BufferedReader(
								new InputStreamReader(
										new FileInputStream(
												new File(f.getPath() + "/" + JUG_FILE_INFO))))){
					int codi = Integer.valueOf(f.getName());
					String jug = this.getJugador(codi);
					if (jug != null)
						jugadors.put(codi, jug);
				} catch (NumberFormatException e) {
					continue;
				} catch (IOException e1) {
					return null;
				}
			}
		}
		return jugadors;
	}
	
	/**
	 * @param nomJugador és el nom jugador del que carregar les seves dades
	 * @return retorna un string amb les dades del jugador si existeix, altrament
	 * 	retorna null.
	 */
	public String getJugador(int nomJugador) {
		File f = new File(MAIN_DIR + "/" + String.valueOf(nomJugador) + "/" + JUG_FILE_INFO);
		if (!f.exists())
			return null;
		String content = null;
		try(BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
			// Només hi ha una línia a l'arxiu.
			content = buf.readLine();
		} catch (/*FileNotFoundException e |*/ IOException e) {
			return null;
		}
		return content;
	}
	
	/**
	 * Crea les dades corresponents a un jugador
	 * @param idJug ha de ser el identificador del jugador a guardar.
	 * @param jugador ha de ser un string amb totes les dades d'un jugador separades amb
	 * espais!!!
	 * @return si s'ha creat correctament retorna 0, si existeix retorna 1, si hi ha hagut
	 * 	un error i/o retorna 2.
	 */
	public int crearJugador(int idJug, String jugador) {
		File f = new File(MAIN_DIR + "/" + String.valueOf(idJug));
		if (f.exists())
			return 1;
		if (!f.mkdir())
			return 2;
		File finfo = new File(f.getPath() + "/" + JUG_FILE_INFO);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(finfo))) {
			bw.write(jugador);
			
		} catch (IOException e) {
			e.printStackTrace();
			return 2;
		}
		
		return 0;
	}
	
	
	/**
	 * Obté totes les partides guardades d'un jugador.
	 * @param jug és la id del jugador d'on agafar les partides guardades.
	 * @return un diccionari d'strings amb les partides guardades del jugador
	 * 	amb id jug, altrament, si hi ha hagut un error, retorna null.
	 */
	public Map<Integer, String> getPartidesGuardades(int jug) {
		TreeMap<Integer, String> partides = new TreeMap<Integer, String>();
		File f = new File(MAIN_DIR + "/" + String.valueOf(jug));
		if (!f.exists())
			return null;
		File[] flist = f.listFiles();
		for (File ff : flist) {
			if (ff.isDirectory()) {
				try {
					int codiPart = Integer.valueOf(ff.getName());
					String dataPartida = this.getPartidaJugador(jug, codiPart);
					if (dataPartida != null)
						partides.put(codiPart, dataPartida);
				} catch (NumberFormatException e) {
					// directori no és un integer
					continue;
				}
			}
		}
		return partides;
	}
	
	
	/**
	 * Obtñe les dades d'una partida d'un jugador.
	 * @param codiJug codi del jugador que es vol carregar la partida.
	 * @param codiPart codi de la partida que es vol carregar les dades.
	 * @return retorna les dades d'una partida, altrament retorna null si hi ha
	 * 	hagut un error.
	 */
	public String getPartidaJugador(int codiJug, int codiPart) {
		File f = new File(MAIN_DIR + "/" + String.valueOf(codiJug) + "/" + String.valueOf(codiPart) + "/" + PART_FILE_INFO);
		if (!f.exists())
			return null;
		String content = null;
		try(BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(f)))) {
			content = buf.readLine();
		} catch (/*FileNotFoundException|*/ IOException e) {
			return null;
		}
		
		return content;
	}
	
	/**
	 * Elimina una partida, si no existeix la partida o el jugador no fa res.
	 * @param codiJug La id del jugador propietari de la partida.
	 * @param codiPart La id de la partida.
	 */
	public void eliminaPartida(int codiJug, int codiPart) {
		File f = new File(MAIN_DIR + "/" + String.valueOf(codiJug) + "/" + String.valueOf(codiPart));
		//System.out.println(f.getAbsolutePath() + "/" + f.getName());
		if (!f.exists())
			return;
		for (File fl : f.listFiles()) {
			fl.delete();
		}
		f.delete();
		//f.
	}
	
	public void actualitzaJugador(int idJug, String jug) {
		File f = new File(MAIN_DIR + "/" + String.valueOf(idJug));
		/*if (!f.exists())
			return 1;*/
		File finfo = new File(f.getPath() + "/" + JUG_FILE_INFO);
		finfo.delete();
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(finfo))) {
			bw.write(jug);
			
		} catch (IOException e) {
			e.printStackTrace();
			//return 2;
		}
	}
}
