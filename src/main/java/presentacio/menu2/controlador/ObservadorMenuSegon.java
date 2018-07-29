package presentacio.menu2.controlador;

import presentacio.utils.Observador;

public interface ObservadorMenuSegon extends Observador {
	/**
	 * Notificació de que el boto triar partida s'ha premut.
	 */
	public void botoTriarPremut();
	/**
	 * Notificació de que el botó nova partida estàndar s'ha premut.
	 */
	public void botoEstandarPremut();
	/**
	 * Notificació de que el botó nova partida personalitzada s'ha premut.
	 */
	public void botoPersonalitzadaPremut();
}
