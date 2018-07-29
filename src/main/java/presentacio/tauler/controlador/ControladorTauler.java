package presentacio.tauler.controlador;

import java.util.Vector;

import javax.swing.JOptionPane;

import Domini.Aplicacio.Excepcions.ExcErrorIntern;
import Domini.Controladors.ControladorMantenimentJoc;
import Domini.Joc.Excepcions.ExcAccesForaRang;
import Domini.Joc.Excepcions.ExcPistaNoIntroduida;
import presentacio.ControladorPresentacio;
import presentacio.DadesPartidaObtenible;
import presentacio.tauler.VistaTauler;

/**
 * Controlador que enllaça les funcionalitats del domini amb la vista del tauler.
 *
 */
public abstract class ControladorTauler implements ObservadorTauler, DadesPartidaObtenible {
	
	protected ControladorPresentacio ctrlPres;
	protected ControladorMantenimentJoc ctrlJoc;
	protected VistaTauler vista;
	
	
	
	public ControladorTauler(ControladorMantenimentJoc ctrlJoc, ControladorPresentacio ctrlPres) {
		this.ctrlJoc = ctrlJoc;
		this.ctrlPres = ctrlPres;
	}
	
	/**
	 * Si el joc està començat, construeix la vista en base les dades que existeixen
	 * 	al joc en el domini.
	 */
	public void construeixJocComencat() {
		if (ctrlJoc.combinacioMasterPosada()) {
			vista.setCombinacioMaster(ctrlJoc.getCombinacioMaster());
			
			int tornActual = ctrlJoc.getTornActual();
			for (int i = 0; i < tornActual; i++) {
				
				Vector<Integer> vc;
				try {
					vc = ctrlJoc.getCombinacioTornN(i);
					vista.seguentLinia();
					try {
						Vector<Integer> vp = ctrlJoc.getPistesTornN(i);
						vista.ompleLiniaCompleta(vc, vp);
					} catch (ExcPistaNoIntroduida e) {
						vista.setUltimaCombinacio(vc);
					}
				} catch (ExcAccesForaRang e1) {
				}
			}
			afegirUltimaLinia();
		}
	}
	
	/**
	 * El funcionament és diferent si construeixes la vista sent master o breaker.
	 */
	public abstract void afegirUltimaLinia();
	
	/**
	 * Inicialitza els components de la vista vt i dona d'alta l'observador a la vista. 
	 * @param vt la vista tauler corresponent a aquest controlador.
	 */
	protected void construeixVista(VistaTauler vt) {
		vista = vt;
		vista.inicialitzarComponents();
		vista.donaAltaObservador(this);
	}
	
	public void mostraVista() {
		vista.mostraVista();
	}
	public void amagaVista() {
		vista.amagaVista();
	}
	
	public void mostraDialogFinalJoc() {
		vista.mostraMessageDialog("Ha guanyat el rol: " + ctrlJoc.getGuanyador(), "Partida acabada", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Obté la última pista introduïda al tauler.
	 * @return un vector de integers amb els identificadors dels colors de cada posició.
	 */
	public Vector<Integer> getUltimaPista() {
		return vista.getUltimaPista();
	}
	
	/**
	 * Obté la última combinació introduïda al tauler.
	 * @return un vector de integers amb el identificador del color de cada posició.
	 */
	public Vector<Integer> getUltimaCombinacio() {
		return vista.getUltimaCombinacio();
	}
	
	@Override
	public void botoSeguentPremut() {
		vista.setDadesDreta(getDades());
	}

	@Override
	public void botoGuardarPremut() {
		try {
			ctrlJoc.guardarPartida();
			vista.mostraMessageDialog("Partida guardada", "", JOptionPane.INFORMATION_MESSAGE);
		} catch (ExcErrorIntern e) {
			vista.mostraMessageDialog("Partida no guardada: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}

	@Override
	public void botoReiniciarPremut() {
		try {
			ctrlJoc.reiniciarPartida();
			vista.mostraMessageDialog("Partida reiniciada", "", JOptionPane.INFORMATION_MESSAGE);
			this.ferTransicioAVistaPartida();
		} catch (ExcErrorIntern e) {
			vista.mostraMessageDialog("Partida no reiniciada: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Fa la transició a la vista partida, el joc possiblement s'ha reiniciat o ja ha acabat.
	 */
	public abstract void ferTransicioAVistaPartida();
	
	@Override
	public Vector<String> getDades() {
		return ctrlJoc.getDadesJoc();
	}

	@Override
	public Vector<String> getDescripcioDades() {
		return ctrlJoc.getDescripcioDadesJoc();
	}

	
	/**
	 * Consulta si ha acabat el joc.
	 * @return cert si el joc ha acabat, fals altrament.
	 */
	public boolean jocAcabat() {
		return ctrlJoc.jocAcabat();
	}

}
