package presentacio.tauler.controlador;

import Domini.Joc.Excepcions.ExcColorFitxaIncorrecte;
import Domini.Joc.Excepcions.ExcNumeroFitxesIncorrecte;

/**
 * Estat quan el jugador està introduïnt una combinació al tauler tenint el rol
 * 	de code breaker.
 */
public class EstatBotoSeguentIntroduintCombinacio extends EstatBotoSeguentBreaker {

	@Override
	public void handle(ControladorTaulerBreaker ctm) {
		if (ctm.ultimaCombinacioColorada()) {
			try {
				ctm.introduirCombinacioDomini();
				if (ctm.jocAcabat()) {
					EstatBotoSeguentBreaker e = new EstatBotoSeguentBreakerJocAcabat();
					ctm.setEstat(e);
					ctm.eliminaListeners();
					ctm.introdueixPista();
					e.handle(ctm);
				} else {
					ctm.seguentLinia();
				}
			} catch (ExcColorFitxaIncorrecte e) {
				ctm.mostraDialogCombinacioIncompleta();
			} catch (ExcNumeroFitxesIncorrecte e) {
				e.printStackTrace();
			}
		}
		
	}

}
