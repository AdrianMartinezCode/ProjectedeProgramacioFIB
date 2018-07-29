package presentacio.tauler.controlador;

import presentacio.utils.AbstractObservable;

public abstract class AbstractObservableTauler extends AbstractObservable<ObservadorTauler> {
	
	/**
	 * Notifica als observadors subscrits al observable de que el botó següent s'ha premut.
	 */
	public void notificaBotoSeguent() {
		for (ObservadorTauler ot : super.getObservadors()) {
			ot.botoSeguentPremut();
		}
	}
	/**
	 * Notifica que el botó guardar s'ha premut.
	 */
	public void notificaBotoGuardar() {
		for (ObservadorTauler ot : super.getObservadors()) {
			ot.botoGuardarPremut();
		}
	}
	/**
	 * Notifica que el botó reiniciar s'ha premut.
	 */
	public void notificaBotoReiniciar() {
		for (ObservadorTauler ot : super.getObservadors()) {
			ot.botoReiniciarPremut();
		}
	}
}
