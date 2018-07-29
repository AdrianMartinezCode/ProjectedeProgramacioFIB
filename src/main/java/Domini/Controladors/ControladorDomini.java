package Domini.Controladors;

import Domini.Aplicacio.Excepcions.ExcErrorPersistencia;
import Persistencia.ControladorPersistencia;

public class ControladorDomini {
	
	/**
	 * Controlador que gestiona els jugadors.
	 */
	private ControladorMantenimentJugador ctrlMantJug;
	/**
	 * Controlador que gestiona les partides.
	 */
	private ControladorMantenimentPartida ctrlMantPart;
	/**
	 * Controlador que gestiona els jocs.
	 */
	private ControladorMantenimentJoc ctrlMantJoc;
	/**
	 * Controlador de persistència.
	 */
	private ControladorPersistencia ctrlPers;
	

	public ControladorDomini() {
		ctrlPers = ControladorPersistencia.getInstance();
		ctrlMantJug = new ControladorMantenimentJugador();
		ctrlMantPart = new ControladorMantenimentPartida(ctrlMantJug);
		ctrlMantJoc = new ControladorMantenimentJoc(ctrlMantPart);
		ctrlMantPart.setIJocChangeListener(ctrlMantJoc);
	}
	
	
	/*
	 * old:
	 * @return retorna 0 si no hi ha hagut cap error, retorna 3 si ha ocorregut algun
	 * 	error al inicialitzar la persistència, 1 si ha hagut algun error al carregar els
	 * 	jugador, 2 si ha hagut algun error al carregar les partides guardades als jugadors.
	 */
	/**
	 * Inicialitza tots els components del controlador del domini.
	 * @throws ExcErrorPersistencia ha hagut un error a la persistència.
	 * @see ControladorMantenimentJugador#carregaJugadors()
	 * @see ControladorMantenimentJugador#carregaPartidesGuardadesAJugadors()
	 */
	public void inicialitzarControlador() {
		if (ctrlPers.inicialitzaPersistencia() != 0)
			throw new ExcErrorPersistencia("Al inicialitzar la persistència hi ha hagut algun error.");
		
		ctrlMantJug.carregaJugadors();
		ctrlMantJug.carregaPartidesGuardadesAJugadors();
	}
	
	
	public ControladorMantenimentJoc getCtrlMantJoc() {
		return ctrlMantJoc;
	}
	
	public ControladorMantenimentJugador getCtrlMantJugador() {
		return ctrlMantJug;
	}
	
	public ControladorMantenimentPartida getCtrlMantPartida() {
		return ctrlMantPart;
	}
	
}
