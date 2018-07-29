package Domini.Joc;

import java.util.Vector;

import Domini.IEntitatCarregable;
import Domini.Aplicacio.IDadesPartida;
import Domini.Aplicacio.INotificaJocAcabat;
import Domini.Aplicacio.Utils.ColorGenerador;
import Domini.Aplicacio.Utils.Pair;
import Domini.Joc.Excepcions.ExcAccesForaRang;
import Domini.Joc.Excepcions.ExcCombinacioInicialIntroduida;
import Domini.Joc.Excepcions.ExcCombinacioInicialNoIntroduida;
import Domini.Joc.Excepcions.ExcJocAcabat;
import Domini.Joc.Excepcions.ExcPosarPistaSenseCombinacio;


public abstract class JocActual implements IEntitatCarregable{
	
	/**
	 * Instància des d'on poder obtindre les diferents dades de la partida.
	 */
	private IDadesPartida dadesPart;
	/**
	 * Instància d'un objecte notificable de que s'ha acabat el joc.
	 */
	private INotificaJocAcabat notificable;
	
	private int tornActual;
	private boolean acabada;
	
	/*
	 * Si tauler té una línia és que la combinació s'ha col·locat o bé la pista ha sigut col·locada
	 *  per tant, si tauler té diverses línies, totes les línies anteriors a la última
	 *  tenen una combinació i una pista.
	 * La última línia del tauler sempre ha de satisfer una de les dues condicions:
	 * 	- La combinació ha sigut introduïda
	 * 	- La combinació i la pista d'aquesta combinació ha sigut introduïda
	 */
	
	
	/**
	 * Relació CombinacióMestra que inclou GrupFitxes amb JocActual.
	 */
	private GrupFitxes combinacioMestre;

	/**
	 * Relació ComposatDe que inclou Linia amb JocActual.
	 */
	private Vector<Linia> tauler;
	
	private static final String NULL_STRING = "PPPPLLLL";
	private static final String SEPARADOR = "SEPARADORSPLITDEJOCACTUAL";
	
	public JocActual(IDadesPartida dadesPart, INotificaJocAcabat notificable) {
		this.dadesPart = dadesPart;
		this.notificable = notificable;
		this.tauler = new Vector<Linia>();
		this.tornActual = 0;
		combinacioMestre = null;
		acabada = false;
	}

	public IDadesPartida getDadesPartida() {
		return dadesPart;
	}

	/**
	 * @throws ExcCombinacioInicialNoIntroduida si no s'ha introduït completament la combinació inicial.
	 */
	private void combinacioInicialNoIntroduida() {
		if (combinacioMestre == null || (combinacioMestre != null && !combinacioMestre.grupPossatComplet()))
			throw new ExcCombinacioInicialNoIntroduida();
	}
	
	/**
	 * Getter de l'atribut tornActual, retorna el valor d'aquest atribut
	 * @return un integer amb el valor de tornActual
	 */
	public int getTornActual() {
		return tornActual;
	}
	
	/**
	 * Setter de l'atribut tornActual. El valor del nou tornActual ser el valor passat com a parametre
	 * @param tornActual sera el nou valor de l'atribut
	 * @return true si l'operacio s'ha efectuat amb exit
	 */
	public boolean setTornActual(int tornActual) {
		if (dadesPart.getTotalTorns() < tornActual)
			return false;
		this.tornActual = tornActual;
		return true;
	}
	
	/**
	 * Getter de l'atribut partidaAcabada, retorna el valor d'aquest atribut
	 * @return un boolean amb el valor de partidaAcabada
	 */
	public boolean jocAcabat() {
		return this.acabada;
	}
	
	

	/**
	 * Getter de l'atribut Rol, retorna el valor d'aquest atribut. Abstract, en funci de les classes filles, retornar Maker o Breaker
	 * @return el valor de Rol
	 */
	public abstract Rol getRol();
	
	/**
	 * Introdueix la combinacio inicial a l'atribut combinacioMestre.
	 * @param comb conte els valor de les fitxes que s'introduiran com a combinacio mestre
	 * @throws ExcCombinacioInicialIntroduida si ja s'ha introduït la combinació inicial totalment.
	 */
	public void introduirCombinacioAmagada(Vector<Integer> comb) {
		if (combinacioMestre != null && !combinacioMestre.grupPossatComplet())
			throw new ExcCombinacioInicialIntroduida();
		combinacioMestre = new GrupFitxes(this.dadesPart);
		for (int i=0; i<this.dadesPart.getNPosicions(); i++) {
			combinacioMestre.afegirFitxa(comb.get(i));
		}
		tornActual++;
	}
	
	/**
	 * Consulta si la combinació inicial s'ha introduït.
	 * @return si hi ha combinació mestre introduïda, això implica que totes les
	 * 	fitxes de la combinació mestre estigui completa.
	 */
	public boolean combinacioInicialIntroduida() {
		return combinacioMestre != null && combinacioMestre.grupPossatComplet();
	}
	
	/**
	 * Crea una nova linia que contindra el valor de la combinacio
	 * @param comb conte els valor de les fitxes que s'introduiran com a nova combinacio
	 * @throws ExcJocAcabat si ja ha acabat el joc.
	 * @throws ExcCombinacioInicialNoIntroduida si no s'ha introduït completament la combinació inicial.
	 */
	public void afegirCombinacio(Vector<Integer> comb) {
		combinacioInicialNoIntroduida();
		if (acabada)
			throw new ExcJocAcabat();
		Linia l = new Linia(dadesPart);
		l.afegirCombinacio(comb);
		tauler.add(l);
		acabada = l.igualsCombinacio(combinacioMestre) || (tornActual >= dadesPart.getTotalTorns() - 1/* && tauler.lastElement().pistaPosada()*/);
		
		if (acabada) {
			notificable.notificaJocAcabat();
			System.out.println("PARTIDA ACABADA");
		}
	}
	
	/**
	 * Afegeix una combinacio de pistes a l'ultima linia del tauler
	 * @param pista cont els valors de les pistes que s'introduiran
	 * @throws ExcCombinacioInicialNoIntroduida si no s'ha introduït completament la combinació inicial.
	 * @throws ExcPosarPistaSenseCombinacio s'està intentant introduïr una pista sense haver introduït una combinació.
	 */
	public void afegirPista(Vector<Integer> pista) {
		combinacioInicialNoIntroduida();
		if (tauler.isEmpty() || tauler.lastElement().pistaPosada() || !tauler.lastElement().combinacioIntroduidaCompletament())
			throw new ExcPosarPistaSenseCombinacio("S'està tractant de possar una pista sense haver possat una combinació.");
		
		tauler.lastElement().afegirPistes(pista);
		if (!acabada)
			++tornActual;
	}
	
	/**
	 * Retorna la combinacio mestre en forma de vector.
	 * @return vector amb la combinacio mestre del joc, un vector de integer on cada integer
	 * 	correspon al color corresponent a la posició de la combinació amagada. Retorna null si encara
	 * 	no hi ha combinació mestre introduïda.
	 */
	public Vector<Integer> obtenirCombinacioAmagada(){
		if (combinacioMestre == null)
			return null;
		Vector<Integer> ret = new Vector<Integer>();
		for (int i = 0; i < this.combinacioMestre.obtenirMida(); i++) {
			ret.add(this.combinacioMestre.obtenirColor(i));
		}
		return ret;
	}
	
	/**
	 * Retorna una String amb el nom del rol del guanyador.
	 * @return null si no ha acabat la partida, altrament, retorna el nom de
	 * 	rol de qui ha guanyat.
	 */
	public String guanyador() {
		if (!acabada)
			return null;
		
		Vector<Integer> itcomb = tauler.lastElement().obtenirCombinacio();
		boolean diferents = !combinacioMestre.iguals(itcomb);

		if (diferents || tornActual >= dadesPart.getTotalTorns())
			return Rol.CodeMaster.name();
		return Rol.CodeBreaker.name();
	}
	
	/**
	 * 
	 * @return la mida del tauler.
	 */
	public int getSizeTauler() {
		return tauler.size();
	}
	
	/**
	 * Retorna la pista iessima del tauler, com a vector de integers.
	 * @param n l'índex de la pista.
	 * @return un vector de integers, que correspon a la pista iéssima.
	 */
	protected Vector<Integer> getPistaIessimaTauler(int n) {
		return tauler.elementAt(n).obtenirPista();
	}
	/**
	 * Retorna la combinació iessima del tauler, com a vector de integers.
	 * @param n l'índex de la combinació.
	 * @return un vector de integers, que correspon a la combinació iéssima.
	 */
	protected Vector<Integer> getCombinacioIessimaTauler(int n) {
		return tauler.elementAt(n).obtenirCombinacio();
	}
	
	/**
	 * Retorna la pista iessima del tauler, com a vector de integers.
	 * @param n l'índex de la pista.
	 * @return un vector de integers, que correspon a la pista iéssima.
	 * @throws ExcAccesForaRang si l'índex és fora de rang.
	 */
	public Vector<Integer> obtenirPistaIessima(int n) throws ExcAccesForaRang {
		if (n >= tauler.size())
			throw new ExcAccesForaRang();
		return getPistaIessimaTauler(n);
	}
	/**
	 * Retorna la combinació iessima del tauler, com a vector de integers.
	 * @param i l'índex de la combinació.
	 * @return un vector de integers, que correspon a la combinació iéssima.
	 * @throws ExcAccesForaRang si l'índex és fora de rang.
	 */
	public Vector<Integer> obtenirCombinacioIessima(int i) throws ExcAccesForaRang {
		if (i >= tauler.size())
			throw new ExcAccesForaRang();
		return getCombinacioIessimaTauler(i);
	}
	
	/**
	 * Métode per comptar les pistes d'un vector de integers, o sigui
	 * 	obtindre de un vector de integers de pistes el nombre de fitxes
	 * 	blanques i negres.
	 * @param pista el vector de integers que representen les pistes.
	 * @return retorna un pair de dos integers, blanques i negres respectivament.
	 */
	public static Pair<Integer, Integer> comptaPistes(Vector<Integer> pista) {
		int blanques = 0, negres = 0;
		for (Integer i : pista) {
			if (i.intValue() == ColorGenerador.getNumPistaBlanca())
				++blanques;
			else if (i.intValue() == ColorGenerador.getNumPistaNegra())
				++negres;
		}
		return new Pair<Integer, Integer>(new Integer(blanques), new Integer(negres));
	}
	
	/**
	 * Retorna la combinacio de la ultima jugada en forma de vector. A cada posicio del vector hi haur un int corresponent a la fitxa.
	 * @return vector amb la combinacio de la última jugada efectuada, o si encara
	 * 	no s'han fet jugades, retorna null.
	 * @throws ExcCombinacioInicialNoIntroduida si la combinació inicial no s'ha introduït encara.
	 */
	public Vector<Integer> obtenirUltimaCombinacio(){
		if (combinacioMestre == null)
			throw new ExcCombinacioInicialNoIntroduida();
		if (!tauler.isEmpty())
			return tauler.lastElement().obtenirCombinacio();
		return null;
	}
	/**
	 * Retorna si l'ultima casella del tauler on hi ha una combinacio te les pistes posades
	 * @return boolean si te o no te la ultima combinacio te la pista posada.
	 * @throws ExcCombinacioInicialNoIntroduida si la combinació inicial no s'ha introduït encara.
	 */
	public boolean teUltimaPistaPosada() {
		if (combinacioMestre == null)
			throw new ExcCombinacioInicialNoIntroduida();
		if (!tauler.isEmpty())
			return tauler.lastElement().pistaPosada();
		return true;
	}
	
	/**
	 * Retorna la combinacio de les ultimes pistes del tauler en forma de vector. 
	 * A cada posicio del vector hi haur un int corresponent a la pista.
	 * @return vector amb la ultima combinacio de pistes, si no hi han pistes
	 * 	al tauler, retorna null.
	 * @throws ExcCombinacioInicialNoIntroduida si la combinació inicial no s'ha introduït encara.
	 */
	public Vector<Integer> obtenirUltimaPista(){
		if (combinacioMestre == null)
			throw new ExcCombinacioInicialNoIntroduida();
		if (!tauler.isEmpty())
			return tauler.lastElement().obtenirPista();
		return null;
	}
	
	/**
	 * Obté les dades del joc en format de vector string.
	 * @return un vector de strings amb les dades del joc, una
	 * 	string per dada.
	 */
	public Vector<String> getDadesJoc() {
		Vector<String> info = new Vector<String>();
		info.add(String.valueOf(dadesPart.getCodiJoc()));
		info.add(String.valueOf(tornActual));
		if (dadesPart.getAjuts())
			info.add("Si");
		else
			info.add("No");
		info.add(dadesPart.getDificultat().name());
		info.add(String.valueOf(dadesPart.getNColors()));
		info.add(String.valueOf(dadesPart.getNPosicions()));
		return info;
	}
	
	/**
	 * Obté les descripcions de cada dada del joc en format vector string,
	 * 	en el mateix ordre en que es retornen a {@link JocActual#getDadesJoc()}
	 * @return un vector de strings amb les descripcions de les dades del joc,
	 * 	una string per cada descripció de la dada.
	 */
	public Vector<String> getDescripcioDadesJoc() {
		Vector<String> desc = new Vector<String>();
		desc.add("Joc número");
		desc.add("Torn");
		desc.add("Ajuts");
		desc.add("Dificultat");
		desc.add("Número colors");
		desc.add("Número posicions");
		return desc;
	}

	@Override
	public String convertirToString() {
		String ret = "";
		
		if (tauler.isEmpty())
			ret += 0;
		else {
			ret += tauler.size();
			for (Linia l : tauler) {
				ret += SEPARADOR;
				ret += l.convertirToString();
			}
		}
		
		ret += SEPARADOR;
		
		if (combinacioMestre == null)
			ret += NULL_STRING;
		else
			ret += combinacioMestre.convertirToString();
		
		ret += SEPARADOR;
		
		ret += tornActual;
		
		ret += SEPARADOR;
		
		ret += String.valueOf(acabada);
		
		return ret;
	}

	@Override
	public int obtenerFromString(String vstr) {
		String[] ss = vstr.split(SEPARADOR);
		if (ss.length < 4) {
			netejaObjecte();
			return 1;
		}
		
		try {
			int index = 0;
			// 4 és el nombre d'atributs
			int numLinies = Integer.valueOf(ss[index]);
			if (numLinies + 4 != ss.length)
				return 1;
			tauler = new Vector<Linia>();
			++index;
			for (int i = 0; i < numLinies; i++) {
				Linia l = new Linia(dadesPart);
				int codeExit = l.obtenerFromString(ss[i + index]);
				if (codeExit != 0) {
					netejaObjecte();
					return codeExit;
				}
				tauler.add(l);
			}
			index += numLinies;
			
			if (ss[index].equals(NULL_STRING))
				combinacioMestre = null;
			else {
				combinacioMestre = new GrupFitxes(dadesPart);
				int codeExit = combinacioMestre.obtenerFromString(ss[index]);
				if (codeExit != 0) {
					netejaObjecte();
					return codeExit;
				}
			}
			++index;
			
			if (!setTornActual(Integer.valueOf(ss[index++]))) {
				netejaObjecte();
				return 2;
			}
			
			acabada = Boolean.valueOf(ss[index]);
			
		} catch (NumberFormatException e) {
			netejaObjecte();
			return 1;
		}
		
		return 0;
	}
	
	@Override
	public void netejaObjecte() {
		tauler = new Vector<Linia>();
		combinacioMestre = null;
		acabada = false;
	}

	
	@Override
	public String getSeparador() {
		return SEPARADOR;
	}
}
