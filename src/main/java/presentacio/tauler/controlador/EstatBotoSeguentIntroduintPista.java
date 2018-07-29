package presentacio.tauler.controlador;

import Domini.Joc.Excepcions.ExcColorFitxaIncorrecte;
import Domini.Joc.Excepcions.ExcJocAcabat;
import Domini.Joc.Excepcions.ExcNumeroFitxesIncorrecte;
import Domini.Joc.Excepcions.ExcPistesIncorrectes;

/**
 * Estat de quan s'està introduïnt pistes quan el jugador té el
 * 	rol de code master.
 */
public class EstatBotoSeguentIntroduintPista extends EstatBotoSeguentMaster {
	
	private void canviaEstatAcabat(ControladorTaulerMaster ctm) {
		EstatBotoSeguentMaster e = new EstatBotoSeguentJocAcabatMaster();
		ctm.setEstat(e);
		e.handle(ctm);
	}

	@Override
	public void handle(ControladorTaulerMaster ctm) {
		try {
			// Supossem que al possar pista ja ha avançat a la següent combinació la màquina.
			ctm.introduirPistesDomini();
			ctm.seguentLinia();
			if (ctm.jocAcabat())
				canviaEstatAcabat(ctm);
		} catch (ExcJocAcabat e) {
			canviaEstatAcabat(ctm);
		} catch (ExcNumeroFitxesIncorrecte | ExcPistesIncorrectes e) {
			ctm.mostraDialogPistesIncorrectes();
		} catch (ExcColorFitxaIncorrecte e) {
			// En principi cosa impossible, depèn de la implementació de la vista si
			// 	ha agafat bé el número de color.
			e.printStackTrace();
		}
		
	}

}
