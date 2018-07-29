package presentacio.menu1.controlador;

import presentacio.utils.AbstractObservable;

public class AbstractObservableMenuPrimer extends AbstractObservable<ObservadorMenuPrimer> {
	
	/**
	 * Notifica el boto de jugar a tots els observadors.
	 */
	public void notificaBotoJugar() {
		for (ObservadorMenuPrimer o : super.getObservadors())
			o.botoJugarPremut();
	}
	
}
