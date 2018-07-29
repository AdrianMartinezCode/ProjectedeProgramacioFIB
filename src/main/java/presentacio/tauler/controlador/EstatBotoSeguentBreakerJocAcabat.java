package presentacio.tauler.controlador;

/**
 * Estat que té el botó següent quan ja s'ha acabat el joc quan l'usuari té el
 * 	rol de code breaker.
 */
public class EstatBotoSeguentBreakerJocAcabat extends EstatBotoSeguentBreaker {

	@Override
	public void handle(ControladorTaulerBreaker ctm) {
		ctm.mostraDialogFinalJoc();
		ctm.ferTransicioAVistaPartida();
	}
	
}
