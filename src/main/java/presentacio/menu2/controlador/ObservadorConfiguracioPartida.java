package presentacio.menu2.controlador;

import presentacio.utils.Observador;

public interface ObservadorConfiguracioPartida extends Observador {
	
	/**
	 * Notificació que el botó acceptar s'ha premut.
	 * @param tipusConfiguracio el número de configuració.
	 */
	public void botoAcceptarPremut(int tipusConfiguracio);
	/**
	 * Notificació que el botó cancel·lar s'ha premut.
	 * @param tipusConfiguracio el número de configuració.
	 */
	public void botoCancelarPremut(int tipusConfiguracio);
}
