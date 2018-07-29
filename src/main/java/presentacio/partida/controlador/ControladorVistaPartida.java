package presentacio.partida.controlador;

import java.util.Vector;

import javax.swing.JOptionPane;

import Domini.Aplicacio.Excepcions.ExcErrorIntern;
import Domini.Aplicacio.Excepcions.ExcNoHaAcabatJoc;
import Domini.Aplicacio.Excepcions.ExcPartidaAcabada;
import Domini.Controladors.ControladorMantenimentPartida;
import presentacio.ControladorPresentacio;
import presentacio.DadesPartidaObtenible;
import presentacio.partida.VistaPartida;

/**
 * Fa la conexió entre la vista i el controlador del domini associat.
 *
 */
public class ControladorVistaPartida implements ObservadorVistaPartida, DadesPartidaObtenible{
	
	private ControladorPresentacio ctrlPres;
	private ControladorMantenimentPartida ctrlPart;
	private VistaPartida vista;
	
	public ControladorVistaPartida(ControladorMantenimentPartida ctrlPart, ControladorPresentacio ctrlPres) {
		this.ctrlPart = ctrlPart;
		this.ctrlPres = ctrlPres;
		vista = null;
	}
	
	public void construeixVista() {
		vista = new VistaPartida(this);
		vista.inicialitzarComponents();
		vista.afegirListeners();
		vista.donaAltaObservador(this);
	}
	
	public void mostraVista() {
		vista.mostraVista();
	}
	public void amagaVista() {
		vista.amagaVista();
	}

	@Override
	public Vector<String> getDades() {
		return ctrlPart.getDadesPartida();
	}

	@Override
	public Vector<String> getDescripcioDades() {
		return ctrlPart.getDescripcioDadesPartida();
	}

	@Override
	public void botoSeguentJocPremut() {
		try {
			ctrlPart.continuarAlSeguentJoc();
			//System.out.println("Boto seguent premut");
			
		} catch (ExcNoHaAcabatJoc e) {
			//e.printStackTrace();
		} catch (ExcPartidaAcabada e) {
			//e.printStackTrace();
			vista.mostraMessageDialog("Partida acabada!", "", JOptionPane.INFORMATION_MESSAGE);
			ctrlPart.acabarPartida();
			ctrlPres.transicio_vistaPartida_vistaMenuSegon();
			return;
		}
		String r = ctrlPart.getNomRol();
		if (r == "CodeMaster") {
			ctrlPres.transicio_vistaPartida_vistaTaulerMaster();
		} else if (r == "CodeBreaker") {
			ctrlPres.transicio_vistaPartida_vistaTaulerBreaker();
		}
	}

	@Override
	public void botoGuardarPremut() {
		try {
			ctrlPart.guardarPartida();
			vista.mostraMessageDialog("Partida guardada", "", JOptionPane.INFORMATION_MESSAGE);
		} catch (ExcErrorIntern e) {
			vista.mostraMessageDialog("Partida no guardada: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
	}

	@Override
	public void botoReiniciarPremut() {
		try {
			ctrlPart.reiniciaPartida();
			vista.mostraMessageDialog("Partida reiniciada", "", JOptionPane.INFORMATION_MESSAGE);
			vista.actualitzaInfoDades();
		} catch (ExcErrorIntern e) {
			vista.mostraMessageDialog("Partida no reiniciada: " + e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		
	}
	
}
