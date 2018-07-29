package presentacio.partida.controlador;

import presentacio.utils.AbstractObservable;

public class AbstractObservableVistaPartida extends AbstractObservable<ObservadorVistaPartida> {
	/**
	 * Notifica als observadors de que el botó de següent joc s'ha premut.
	 */
	public void notificaBotoSeguentJoc() {
		for (ObservadorVistaPartida o : super.getObservadors())
			o.botoSeguentJocPremut();
	}
	/**
	 * Notifica que el botó de reiniciar s'ha premut.
	 */
	public void notificaBotoReiniciar() {
		for (ObservadorVistaPartida o : super.getObservadors())
			o.botoReiniciarPremut();
	}
	/**
	 * Notifica que el botó de guardar s'ha premut.
	 */
	public void notificaBotoGuardar() {
		for (ObservadorVistaPartida o : super.getObservadors())
			o.botoGuardarPremut();
	}
}
