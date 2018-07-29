package presentacio.tauler.controlador;

/**
 * Classe que engloba tots els estats del botó següent quan el rol del
 * 	jugador és code breaker.
 */
public abstract class EstatBotoSeguentBreaker {
	/**
	 * Notifica que s'ha premut el botó de següent.
	 * @param ctm el controlador del tauler breaker.
	 */
	public abstract void handle(ControladorTaulerBreaker ctm);
}
