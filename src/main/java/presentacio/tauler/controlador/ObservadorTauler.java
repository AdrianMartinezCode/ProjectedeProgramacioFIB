package presentacio.tauler.controlador;

import presentacio.utils.Observador;

public interface ObservadorTauler extends Observador{
	/**
	 * Notificació de que el botó següent del tauler s'ha premut.
	 */
	public void botoSeguentPremut();
	/**
	 * Notificació de que el botó guardar del menú del tauler s'ha premut.
	 */
	public void botoGuardarPremut();
	/**
	 * Notificació de que el botó reiniciar del menú del tauler s'ha premut.
	 */
	public void botoReiniciarPremut();
}
