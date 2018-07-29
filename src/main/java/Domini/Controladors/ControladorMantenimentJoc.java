package Domini.Controladors;

import java.util.Random;
import java.util.Vector;

import Domini.Aplicacio.Utils.Pair;
import Domini.Joc.JocActual;
import Domini.Joc.JocBreaker;
import Domini.Joc.JocMaster;
import Domini.Joc.Rol;
import Domini.Joc.Excepcions.*;

public class ControladorMantenimentJoc implements IJocChangeListener {
	
	/**
	 * Joc en cas de que el jugador sigui master.
	 */
	private JocMaster master;
	/**
	 * Joc en cas de que el jugador sigui breaker.
	 */
	private JocBreaker breaker;
	
	private ControladorMantenimentPartida ctrlMantPart;
	
	public ControladorMantenimentJoc (ControladorMantenimentPartida ctrlMantPart) {
		this.ctrlMantPart = ctrlMantPart;
	}
	
	/**
	 * @throws ExcNoHiHaJocComencat UNCHECKED si no hi ha un joc començat.
	 */
	private void jocComencat() {
		if (master == null && breaker == null)
			throw new ExcNoHiHaJocComencat();
	}
	
	/**
	 * {@link JocActual#getDadesJoc()}
	 * @return un vector de strings amb les dades del joc.
	 * @throws ExcNoHiHaJocComencat UNCHECKED en cas de que el joc no hagi començat encara
	 * 	i no es puguin extreure les dades.
	 */
	public Vector<String> getDadesJoc() {
		jocComencat();
		
		if (master == null)
			return breaker.getDadesJoc();
		return master.getDadesJoc();
	}
	
	/**
	 * {@link JocActual#getDescripcioDadesJoc()}
	 * @return un vector de strings amb les descripcions de les dades del joc.
	 * @throws ExcNoHiHaJocComencat UNCHECKED en cas de que el joc no hagi començat encara
	 * 	i no es puguin extreure les dades.
	 */
	public Vector<String> getDescripcioDadesJoc() {
		jocComencat();
		if (master == null)
			return breaker.getDescripcioDadesJoc();
		return master.getDescripcioDadesJoc();
	}
	
	/**
	 * @return el número de posicions de la partida propietaria del joc.
	 * @see ControladorMantenimentPartida#getNumPosicions()
	 */
	public int getNumPosicions() {
		return ctrlMantPart.getNumPosicions();
	}
	/**
	 * @return el número de colors de la partida propietaria del joc.
	 * @see ControladorMantenimentPartida#getNumColors()
	 */
	public int getNumColors() {
		return ctrlMantPart.getNumColors();
	}
	/**
	 * @return el número de torns de la partida propietaria del joc.
	 * @see ControladorMantenimentPartida#getNumTorns()
	 */
	public int getNumTotalTorns() {
		return ctrlMantPart.getNumTorns();
	}
	
	/**
	 * Obté la última combinació introduïda al joc.
	 * @return un vector de integers que representen els colors de la combinació.
	 * @throws ExcNoHiHaJocComencat UNCHECKED si no hi ha un joc començat
	 */
	public Vector<Integer> getUltimaCombinacio() {
		jocComencat();
		if (master == null)
			return breaker.obtenirUltimaCombinacio();
		return master.obtenirUltimaCombinacio();
	}
	
	/**
	 * Obté la última pista introduïda al joc.
	 * @return un vector de integers que representen els colors de la pista.
	 * @throws ExcNoHiHaJocComencat UNCHECKED si no hi ha un joc començat
	 */
	public Vector<Integer> getUltimaPista() {
		jocComencat();
		if (master == null)
			return breaker.obtenirUltimaPista();
		return master.obtenirUltimaPista();
	}
	
	
	
	
	
	
	/**
	 * Consulta si el joc actual ha acabat.
	 * @return cert si el joc ha acabat, fals altrament.
	 * @throws ExcNoHiHaJocComencat UNCHECKED si no hi ha un joc començat
	 */
	public boolean jocAcabat() {
		jocComencat();
		
		if (master == null)
			return breaker.jocAcabat();
		return master.jocAcabat();
	}
	
	
	/**
	 * Obté la string amb el nom del rol del guanyador del joc actual,
	 * 	ha d'estar acabat el joc.
	 * @return la string que correspon al rol del guanyador del joc.
	 * 
	 * @throws ExcNoHiHaJocComencat UNCHECKED si no hi ha un joc començat
	 * @throws ExcJocNoAcabat UNCHECKED si encara no ha acabat el joc.
	 */
	public String getGuanyador() {
		jocComencat();
		
		if (master == null && !breaker.jocAcabat())
			throw new ExcJocNoAcabat();
		
		if (breaker == null && !master.jocAcabat())
			throw new ExcJocNoAcabat();
		
		if (master == null)
			return breaker.guanyador();
		return master.guanyador();
	}
	
	/* Escenari joc */
	/**
	 * -- CAS D'ÚS --
	 * Guarda l'estat actual de la partida.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 */
	public void guardarPartida() {
		jocComencat();
		ctrlMantPart.guardarPartida();
	}
	
	/**
	 * -- CAS D'ÚS --
	 * Reinicia l'estat de la partida.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 */
	public void reiniciarPartida() {
		jocComencat();
		ctrlMantPart.reiniciaPartida();
	}
	
	/* Joc Master */
	
	/**
	 * Posa les pistes de la combinació actual.
	 * Depenent de si la partida és amb ajuts o no mirar:
	 * @see ControladorMantenimentJoc#posarPistesAjuts(Vector)
	 * @see ControladorMantenimentJoc#posarPistesSenseAjuts(int, int)
	 * @param pistes un vector de integers, on cada posició és una pista,
	 * 	si la partida és sense ajuts, les pistes poden estar desordenades,
	 * 	altrament, si la partida és amb ajuts cada pista ha de correspondre
	 * 	amb cadascuna de les fitxes de la combinació introduïda.
	 */
	public void posarPistes(Vector<Integer> pistes) throws ExcJocAcabat, ExcColorFitxaIncorrecte, ExcPistesIncorrectes, ExcNumeroFitxesIncorrecte {
		jocComencat();
		if (master == null)
			throw new ExcPistaSenseSerMaster("No pots afegir les pistes si no ets el master");
		
		if (!ctrlMantPart.getAjuts()) {
			Pair<Integer, Integer> p = JocActual.comptaPistes(pistes);
			this.posarPistesSenseAjuts(p.getFirst(), p.getSecond());
		} else
			this.posarPistesAjuts(pistes);
	}
	
	/**
	 * -- CAS D'ÚS --
	 * 
	 * pre: Resposta és un vector de 2 posicions on la primera indica quantes fitxes coincideixen i la segona quants colors coincideixen però estàn mal posats	 * 
	 * 
	 * @param Resposta vector de fitxes amb la combinació de pistes (només dos colors i de
	 * 	tantes fitxes com posicions).
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 * @throws ExcPistaSenseSerMaster UNCHECKED s'està intentant possar una pista sense ser el master
	 * @throws ExcPosarPistaSenseCombinacio UNCHECKED s'està intentant possar una pista a una combinació 
	 * 		de la mateixa línia que no existeix.
	 * @throws ExcPosarPistaSenseAjuts UNCHECKED la partida és sense ajuts i s'està tractant de possar una
	 * 		pista per a una partida amb ajuts.
	 * @throws ExcJocAcabat CHECKED el joc ja ha acabat
	 * @throws ExcNumeroFitxesIncorrecte CHECKED el nombre de fitxes proporcionat és incorrecte.
	 * @throws ExcPistesIncorrectes CHECKED les pistes posades no s�n les correctes
	 */
	public void posarPistesAjuts(Vector<Integer> Resposta) throws ExcJocAcabat, 
			ExcNumeroFitxesIncorrecte, ExcPistesIncorrectes
	{
		//this.cridaPosaPistes(Resposta);
		int codiError = master.corregeixAmbAjuts(Resposta);
		if(codiError != 0) {
			if(codiError == 1)
				throw new ExcPosarPistaSenseCombinacio("No pots posar les pistes si no hi ha combinaci� a corregir");
			if(codiError == 2)
				throw new ExcPosarPistaSenseAjuts("Estas intentant posar pistes sense ajuts a una partida amb ajuts");
			if(codiError == 3)
				throw new ExcJocAcabat("El joc ja ha acabat");
			if(codiError == 4) 
				throw new ExcPistesIncorrectes ("Les pistes posades no s�n les correctes");		
			if(codiError == 5)
				throw new ExcNumeroFitxesIncorrecte ("El n�mero de fitxes corregides no coincideix amb el n�mero de fitxes total");
		}
		master.seguentComb();
	}
	
	/**
	 * 
	 * @param p1 el nombre de fitxes que estàn, però a una altre posició
	 * @param p2 el nombre de fitxes que estàn a la posició correcta.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 * @throws ExcPistaSenseSerMaster UNCHECKED s'està intentant possar una pista sense ser el master
	 * @throws ExcPosarPistaSenseCombinacio UNCHECKED s'està intentant posar una pista a una combinació 
	 * 		que no existeix de la mateixa línia.
	 * @throws ExcPosarPistaAmbAjuts UNCHECKED la partida és amb ajuts i s'està tractant de posar una pista
	 * 		no específica (o sigui, sense ajuts) per a una partida amb ajuts.
	 * @throws ExcJocAcabat CHECKED el joc ja ha acabat.
	 * @throws ExcPistesIncorrectes CHECKED les pistes posades no són les correctes
	 * @throws ExcNumeroFitxesIncorrecte CHECKED 
	 */
	public void posarPistesSenseAjuts(int p1, int p2)  throws ExcJocAcabat, ExcPistesIncorrectes, ExcNumeroFitxesIncorrecte
	{
		
		Vector<Integer> RespostaUsuari = new Vector<Integer>(2);
		RespostaUsuari.add(p2);
		RespostaUsuari.add(p1);
		int codiExit =  master.corregeix2(RespostaUsuari);
		if(codiExit != 0) {
			if(codiExit == 1)
				throw new ExcPosarPistaSenseCombinacio("No pots posar les pistes si no hi ha combinaci� a corregir");
			if(codiExit == 2)
				throw new ExcPosarPistaAmbAjuts("Estas intentant posar pistes amb ajuts a una partida sense ajuts");
			if(codiExit == 3)
				throw new ExcJocAcabat("El joc ja ha acabat");
			if(codiExit == 4) 
				throw new ExcPistesIncorrectes ("Les pistes posades no s�n les correctes");		
		}
		master.seguentComb();
	}
	

	
	/**
	 * -- CAS D'ÚS --
	 * En IntroduirComb l'usuari afegirà la combinació que hi ha amagada i la màquina generarà la primera combinació que vol resoldre
	 * 
	 * @param combInicial la combinació inicial del joc.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 * @throws ExcCombinacioInicialIntroduida UNCHECKED ja hi ha una combinació inicial introduïda.
	 * @throws ExcCombinacioInicialSenseSerMaster UNCHECKED s'està intentant introduïr una combinació
	 * 		inicial sense ser el master.
	 * @throws ExcNumeroFitxesIncorrecte CHECKED el número de fitxes introduït és incorrecte.
	 * @throws ExcColorFitxaIncorrecte CHECKED hi ha alguna fitxa amb un color incorrecte.
	 * 
	 */
	public void introduirCombinacioInicial(Vector<Integer> combInicial) throws ExcNumeroFitxesIncorrecte, ExcColorFitxaIncorrecte 
	{
		jocComencat();
		if(breaker != null)
			throw new ExcCombinacioInicialSenseSerMaster("No pots afegir la combinaci� inicial si no ets el master");
		int codiExit = master.IntroduirComb(combInicial);
		if (codiExit != 0) {
			if(codiExit == 1)
				throw new ExcCombinacioInicialIntroduida("Ja hi ha una combinaci� inicial introduida");
			if(codiExit == 2)
				throw new ExcNumeroFitxesIncorrecte("No has posat el n�mero de fitxes correctes");
			if(codiExit == 3)
				throw new ExcColorFitxaIncorrecte("Has introduit alguna fitxa amb un color incorrecte");
		}
	}
	
	/* Joc Breaker */
	/**
	 * -- CAS D'ÚS --
	 * 	Si no hi ha la combinaci� amagada, la genera aleatoriament, despr�s afegeix la combinaci� c al tauler i les seves corresponents pistes 
	 * @param c és la combinació a introduïr.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 * @throws ExcCombinacioInicialNoIntroduida UNCHECKED no s'ha introduït una combinació inicial.
	 * @throws ExcPistaNoIntroduidaCombinacioAnterior UNCHECKED l'anterior combinació no té cap pista introduïda.
	 * @throws ExcRolIncorrecte UNCHECKED s'està utilitzant aquest métode amb un rol incorrecte.
	 * @throws ExcNumeroFitxesIncorrecte CHECKED el nombre de fitxes és incorrecte.
	 * @throws ExcColorFitxaIncorrecte CHECKED hi ha alguna fitxa amb un color incorrecte.
	 *
	 */
	public void introduirCombinacio(Vector<Integer> c) throws ExcColorFitxaIncorrecte, ExcNumeroFitxesIncorrecte {
		jocComencat();
		if (breaker == null)
			throw new ExcRolIncorrecte();
		
		if(!breaker.combinacioInicialIntroduida())
			throw new ExcCombinacioInicialNoIntroduida();
			
		
		if(!breaker.teUltimaPistaPosada())
			throw new ExcPistaNoIntroduidaCombinacioAnterior();
			
		
		int exitcode = breaker.introduirCombinacio(c);
		if (exitcode == 1) throw new ExcNumeroFitxesIncorrecte();
		else if(exitcode == 2) throw new ExcColorFitxaIncorrecte();
	}

	/**
	 * Introdueïx una combinació inicial aleatòria, pensada per
	 * 	un joc on el rol del jugador és CodeBreaker i o té sentit
	 * 	possar una combinació preestablerta.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 * @throws ExcCombinacioInicialSenseSerBreaker s'està cridant el métode
	 * 	sense que el rol del jugador sigui CodeBreaker.
	 * @throws ExcCombinacioJaIntroduida ja s'ha introduït una combinació inicial
	 * 	al joc actual.
	 */
	public void introduirCombinacioInicialAleatoria() {
		jocComencat();
		
		if (breaker == null)
			throw new ExcCombinacioInicialSenseSerBreaker();
		
		if (breaker.combinacioInicialIntroduida())
			throw new ExcCombinacioJaIntroduida();
		
		Vector<Integer> v = new Vector<Integer>();
		Random rn = new Random();
		for (int i = 0; i < this.getNumPosicions(); i++)
			v.add(rn.nextInt(this.getNumColors()));
		breaker.introduirCombinacioAmagada(v);
	}
	
	/**
	 * Obté el torn actual del joc actual.
	 * @return el número de torn actual.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 */
	public int getTornActual() {
		jocComencat();
		if (breaker != null)
			return breaker.getTornActual();
		return master.getTornActual();
	}
	
	/**
	 * Obté la combinació iéssima.
	 * @param n l'iéssim índex de la combinació a obtindre.
	 * @return un vector de integers que representen el color amb la combinació iéssima.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 * @throws ExcAccesForaRang CHECKED el accés està fora de rang.
	 */
	public Vector<Integer> getCombinacioTornN(int n) throws ExcAccesForaRang {
		jocComencat();
		if (breaker != null)
			return breaker.obtenirCombinacioIessima(n);
		return master.obtenirCombinacioIessima(n);
	}
	/**
	 * Obté la pista iéssima.
	 * @param n l'iéssim índex de la pista a obtindre.
	 * @return un vector de integers que representen el color amb la pista iéssima.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 * @throws ExcAccesForaRang CHECKED el accés està fora de rang.
	 */
	public Vector<Integer> getPistesTornN(int n) throws ExcAccesForaRang {
		jocComencat();
		if (breaker != null)
			return breaker.obtenirPistaIessima(n);
		return master.obtenirPistaIessima(n);
	}
	
	/**
	 * Consulta si la combinació inicial ja ha sigut introduïda.
	 * @return cert si la combinació inicial ja ha sigut introduïda, altrament
	 * 	fals.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 */
	public boolean combinacioMasterPosada() {
		jocComencat();
		if (breaker != null)
			return breaker.combinacioInicialIntroduida();
		return master.combinacioInicialIntroduida();
	}
	
	/**
	 * Obté la combinació amagada introduïda.
	 * @return un vector de integers on cada posició correspon a la fitxa
	 * 	introduïda, retorna null si encara no s'ha introduït cap fitxa.
	 * @throws ExcNoHiHaJocComencat UNCHECKED no hi ha joc començat
	 */
	public Vector<Integer> getCombinacioMaster() {
		jocComencat();
		if (breaker != null)
			return breaker.obtenirCombinacioAmagada();
		return master.obtenirCombinacioAmagada();
	}


	@Override
	public void changeJoc(Rol r, JocActual j) {
		if (r == Rol.CodeBreaker) {
			master = null;
			breaker = (JocBreaker)j;
		} else {
			breaker = null;
			master = (JocMaster)j;
		}
	}
	
}
