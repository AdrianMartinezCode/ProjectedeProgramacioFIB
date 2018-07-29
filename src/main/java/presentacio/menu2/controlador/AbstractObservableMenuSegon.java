package presentacio.menu2.controlador;

import presentacio.utils.AbstractObservable;

public class AbstractObservableMenuSegon extends AbstractObservable<ObservadorMenuSegon> {
	
	/**
	 * Notifica als observadors que s'ha premut el botó de triar.
	 */
	public void notificaBotoTriar() {
		for (ObservadorMenuSegon o : super.getObservadors())
			o.botoTriarPremut();
	}
	/**
	 * Notifica que s'ha premut el botó de crear partida estàndar.
	 */
	public void notificaBotoEstandar() {
		for (ObservadorMenuSegon o : super.getObservadors())
			o.botoEstandarPremut();
	}
	/**
	 * Notifica que s'ha premut el botó de crear partida personalitzada.
	 */
	public void notificaBotoPersonalitzada() {
		for (ObservadorMenuSegon o : super.getObservadors())
			o.botoPersonalitzadaPremut();
	}
}
