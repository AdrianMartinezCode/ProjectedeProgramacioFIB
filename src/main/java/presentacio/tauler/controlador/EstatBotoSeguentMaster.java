package presentacio.tauler.controlador;

/**
 * Classe que engloba tots els estats del botó següent quan el rol del
 * 	jugador és code master.
 */
public abstract class EstatBotoSeguentMaster {
	/**
	 * Notifica que s'ha premut el botó de següent.
	 * @param ctm el controlador del tauler master.
	 */
	public abstract void handle(ControladorTaulerMaster ctm);
}
