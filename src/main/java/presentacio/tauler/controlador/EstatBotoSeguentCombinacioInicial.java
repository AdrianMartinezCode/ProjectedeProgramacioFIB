package presentacio.tauler.controlador;

/**
 * Estat quan s'està introduïnt la combinació inicial quan el jugador té
 * 	el rol de code master.
 */
public class EstatBotoSeguentCombinacioInicial extends EstatBotoSeguentMaster {

	@Override
	public void handle(ControladorTaulerMaster ctm) {
		if (ctm.combinacioMasterColorada()) {
			ctm.setEstat(new EstatBotoSeguentIntroduintPista());
			ctm.estatCombinacioInicialIntroduida();
		} else {
			ctm.mostraDialogCombinacioMasterIncompleta();
		}
	}

}
