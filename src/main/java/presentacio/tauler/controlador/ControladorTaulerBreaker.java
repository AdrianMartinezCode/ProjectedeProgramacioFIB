package presentacio.tauler.controlador;

import java.util.Vector;

import javax.swing.JOptionPane;

import Domini.Aplicacio.Excepcions.ExcJaExisteixJugador;
import Domini.Aplicacio.Excepcions.ExcNoHaAcabatJoc;
import Domini.Aplicacio.Excepcions.ExcPartidaAcabada;
import Domini.Controladors.ControladorDomini;
import Domini.Controladors.ControladorMantenimentJoc;
import Domini.Controladors.ControladorMantenimentJugador;
import Domini.Controladors.ControladorMantenimentPartida;
import Domini.Joc.Dificultat;
import Domini.Joc.Rol;
import Domini.Joc.Excepcions.ExcColorFitxaIncorrecte;
import Domini.Joc.Excepcions.ExcNumeroFitxesIncorrecte;
import presentacio.ControladorPresentacio;
import presentacio.tauler.VistaTaulerBreaker;

/**
 * Controlador del tauler com a code breaker.
 */
public class ControladorTaulerBreaker extends ControladorTauler {
	
	/**
	 * Fa falta tindre una referència a la vista de code breaker ja que necessitem cridar
	 * 	a métodes que només es fan en aquesta.
	 */
	private VistaTaulerBreaker vista;
	/**
	 * Estat del botó següent.
	 */
	private EstatBotoSeguentBreaker estat;

	public ControladorTaulerBreaker(ControladorMantenimentJoc ctrlJoc, ControladorPresentacio ctrlPres) {
		super(ctrlJoc, ctrlPres);
	}
	
	/**
	 * Construeïx la vista com a code breaker.
	 */
	public void construeixVista() {
		vista = new VistaTaulerBreaker(ctrlJoc.getNumTotalTorns(), ctrlJoc.getNumColors(), ctrlJoc.getNumPosicions(), this);
		super.construeixVista(vista);
		
	}
	
	/**
	 * Inicialitza el tauler afegir la primera línia, enfosquint el panell de la combinació
	 * 	de master i creant el primer estat del botó següent.
	 */
	public void inicialitzaTaulerBreaker() {
		vista.seguentLinia();
		vista.enfosquirPanelMaster();
		vista.setDadesDreta(ctrlJoc.getDadesJoc());
		estat = new EstatBotoSeguentIntroduintCombinacio();
	}
	
	@Override
	public void construeixJocComencat() {
		super.construeixJocComencat();
		inicialitzaTaulerBreaker();
		if (!ctrlJoc.combinacioMasterPosada())
			ctrlJoc.introduirCombinacioInicialAleatoria();
	}
	
	/**
	 * Consulta si la última combinació introduïda està completament colorada.
	 * @return cert si està colorada, fals altrament.
	 */
	public boolean ultimaCombinacioColorada() {
		return vista.ultimaCombinacioColorada();
	}
	
	/**
	 * Mostra un diàleg que indica que la combinació que es vol afegir no està completament
	 * 	colorada.
	 */
	public void mostraDialogCombinacioIncompleta() {
		vista.mostraMessageDialog("La combinació actual no està completa.", "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Avança a la següent línia introduïnt les últimes pistes generades
	 * 	al domini.
	 */
	public void seguentLinia() {
		vista.seguentLinia(ctrlJoc.getUltimaPista());
	}
	
	/**
	 * Afegeix l'última pista del controlador del domini a la vista.
	 */
	public void introdueixPista() {
		vista.afegeixPista(ctrlJoc.getUltimaPista());
	}
	
	/**
	 * Elimina els listeners del panell de colors de selecció.
	 */
	public void eliminaListeners() {
		vista.eliminaListenersColorsSeleccio();
	}

	@Override
	public void botoSeguentPremut() {
		estat.handle(this);
		super.botoSeguentPremut();
	}
	
	/**
	 * Modifica l'estat del botó següent.
	 * @param estat el nou estat.
	 */
	public void setEstat(EstatBotoSeguentBreaker estat) {
		this.estat = estat;
	}
	
	public void introduirCombinacioDomini() throws ExcColorFitxaIncorrecte, ExcNumeroFitxesIncorrecte {
		ctrlJoc.introduirCombinacio(this.getUltimaCombinacio());
	}

	@Override
	public void ferTransicioAVistaPartida() {
		ctrlPres.transicio_vistaTaulerBreaker_vistaPartida();
	}

	@Override
	public void afegirUltimaLinia() {
		// No cal fer res.
	}

	

	
}
