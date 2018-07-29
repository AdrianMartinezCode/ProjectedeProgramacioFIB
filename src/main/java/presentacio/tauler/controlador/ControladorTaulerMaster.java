package presentacio.tauler.controlador;

import java.util.Vector;

import javax.swing.JDialog;
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
import Domini.Joc.Excepcions.ExcJocAcabat;
import Domini.Joc.Excepcions.ExcNumeroFitxesIncorrecte;
import Domini.Joc.Excepcions.ExcPistesIncorrectes;
import presentacio.ControladorPresentacio;
import presentacio.tauler.VistaTaulerMaster;

/**
 * Controlador del tauler amb el rol de code master del jugador.
 */
public class ControladorTaulerMaster extends ControladorTauler {
	
	
	// Hi ha aliasing amb l'atribut de vista de super.
	/**
	 * Instancia de tipus VistaTaulerMaster, hi ha aliasing amb la referència
	 * 	del ControladorTauler de la mateixa vista, però es convenient tindre-la
	 * 	per poder accedir als métodes especialitzats d'aquesta vista.
	 */
	private VistaTaulerMaster vista;
	
	/**
	 * L'estat del botó següent.
	 */
	private EstatBotoSeguentMaster estat;

	public ControladorTaulerMaster(ControladorMantenimentJoc ctrlJoc, ControladorPresentacio ctrlPres) {
		super(ctrlJoc, ctrlPres);
		
	}
	
	/**
	 * Crea la instància de la vista i dóna l'ordre de construïr-la.
	 */
	public void construeixVista() {
		
		VistaTaulerMaster vtm = new VistaTaulerMaster(ctrlJoc.getNumTotalTorns(), ctrlJoc.getNumColors(), ctrlJoc.getNumPosicions(), this);
		super.construeixVista(vtm);
		this.vista = vtm;
	}
	
	/**
	 * Deixa el controlador en l'estat de combinació inicial introduïda,
	 * 	això vol dir que introdueïx la combinació inicial al controlador
	 * 	de joc introduïda al tauler i crea la següent línia al tauler
	 * 	agafant la següent combinació generada al domini.
	 */
	public void estatCombinacioInicialIntroduida() {
		
		try {
			this.ctrlJoc.introduirCombinacioInicial(this.vista.getCombinacioMaster());
			this.vista.estatCombinacioInicialIntroduida(ctrlJoc.getUltimaCombinacio());
		} catch (ExcNumeroFitxesIncorrecte e) {
			e.printStackTrace();
		} catch (ExcColorFitxaIncorrecte e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Deixa el controlador en l'estat de combinació inicial per
	 * 	introduïr, o sigui, a la vista deixa preparat el tauler
	 * 	per poder introduïr la combinació inicial i es crea el
	 * 	primer estat del botó següent.
	 */
	public void iniciaEstatCombinacioInicial() {
		this.vista.iniciaEstatCombinacioInicial();
		this.setEstat(new EstatBotoSeguentCombinacioInicial());
	}
	
	
	/**
	 * Consulta si la combinació del master està totalment col·lorada.
	 * @return cert si ho està, fals altrament.
	 */
	public boolean combinacioMasterColorada() {
		return vista.totsColoratsMaster();
	}
	
	/**
	 * Mostra el missatge d'error de que la combinació de master està incompleta.
	 */
	public void mostraDialogCombinacioMasterIncompleta() {
		vista.mostraMessageDialog("No hi ha una combinació inicial introduïda.", "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Mostra el missatge d'error de que les pistes són incorrectes.
	 */
	public void mostraDialogPistesIncorrectes() {
		vista.mostraMessageDialog("Les pistes introduïdes són incorrectes.", "Error!", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Introdueïx la següent línia amb la última combinació del domini.
	 */
	public void seguentLinia() {
		vista.seguentLinia(ctrlJoc.getUltimaCombinacio());
	}
	
	/**
	 * Introdueïx al domini les últimes pistes que s'han introduït al tauler.
	 * @throws ExcJocAcabat si el joc ja ha acabat.
	 * @throws ExcColorFitxaIncorrecte si hi ha algun color de fitxa incorrecte.
	 * @throws ExcPistesIncorrectes si les pistes introduïdes són incorrectes.
	 * @throws ExcNumeroFitxesIncorrecte si el número de fitxes introduïdes són incorrectes.
	 */
	public void introduirPistesDomini() throws ExcJocAcabat, ExcColorFitxaIncorrecte, ExcPistesIncorrectes, ExcNumeroFitxesIncorrecte {
		ctrlJoc.posarPistes(this.getUltimaPista());
	}
	
	@Override
	public void construeixJocComencat() {
		super.construeixJocComencat();
		if (!ctrlJoc.combinacioMasterPosada()) {
			iniciaEstatCombinacioInicial();
		} else {
			this.setEstat(new EstatBotoSeguentIntroduintPista());
		}
	}
	
	

	@Override
	public void botoSeguentPremut() {
		estat.handle(this);
		super.botoSeguentPremut();
	}

	/**
	 * Modifica l'estat actual del botó següent.
	 * @param estat al que es vol pasar.
	 */
	public void setEstat(EstatBotoSeguentMaster estat) {
		this.estat = estat;
	}


	@Override
	public void ferTransicioAVistaPartida() {
		ctrlPres.transicio_vistaTaulerMaster_vistaPartida();
	}


	@Override
	public void afegirUltimaLinia() {
		vista.afegeixListenersLiniaActual();
	}
	
}
