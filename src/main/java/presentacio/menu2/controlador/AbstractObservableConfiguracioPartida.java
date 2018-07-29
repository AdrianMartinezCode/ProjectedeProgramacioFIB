package presentacio.menu2.controlador;

import presentacio.utils.AbstractObservable;

public class AbstractObservableConfiguracioPartida extends AbstractObservable<ObservadorConfiguracioPartida> {
	
	/**
	 * Notifica el boto acceptar.
	 * @param tipusConfifuracio la id del tipus de configuració.
	 */
	public void notificaBotoAcceptar(int tipusConfifuracio) {
		for (ObservadorConfiguracioPartida o : super.getObservadors())
			o.botoAcceptarPremut(tipusConfifuracio);
	}
	
	/**
	 * Notifica el boto cancel·lar.
	 * @param tipusConfifuracio la id del tipus de configuració.
	 */
	public void notificaBotoCancelar(int tipusConfifuracio) {
		for (ObservadorConfiguracioPartida o : super.getObservadors())
			o.botoCancelarPremut(tipusConfifuracio);
	}
}
