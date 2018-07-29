package presentacio.menu1.controlador;

import presentacio.utils.Observador;

public interface ObservadorMenuPrimer extends Observador{
	/**
	 * Notificació de que s'ha premut el boto de jugar.
	 */
	public void botoJugarPremut();
}
