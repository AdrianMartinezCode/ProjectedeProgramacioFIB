package presentacio.tauler.controlador;

/**
 * Estat de quan ja ha acabat el joc quan el jugador té el rol de
 * 	code master.
 */
public class EstatBotoSeguentJocAcabatMaster extends EstatBotoSeguentMaster {

	@Override
	public void handle(ControladorTaulerMaster ctm) {
		ctm.mostraDialogFinalJoc();
		ctm.ferTransicioAVistaPartida();
	}
	
}
