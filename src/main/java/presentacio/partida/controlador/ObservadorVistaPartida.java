package presentacio.partida.controlador;

import presentacio.utils.Observador;

public interface ObservadorVistaPartida extends Observador{
	/**
	 * Notificació de que el botó següent s'ha premut.
	 */
	public void botoSeguentJocPremut();
	/**
	 * Notificació de que el botó guardar s'ha premut.
	 */
	public void botoGuardarPremut();
	/**
	 * Notificació de que el botó reiniciar s'ha premut.
	 */
	public void botoReiniciarPremut();
}
