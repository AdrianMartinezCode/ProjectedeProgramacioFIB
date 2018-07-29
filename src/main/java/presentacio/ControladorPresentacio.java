package presentacio;

import Domini.Aplicacio.Excepcions.ExcErrorDomini;
import Domini.Aplicacio.Excepcions.ExcErrorPersistencia;
import Domini.Controladors.ControladorDomini;
import presentacio.error.VistaError;
import presentacio.login.ControladorVistaLogin;
import presentacio.menu1.controlador.ControladorMenuPrimer;
import presentacio.menu2.controlador.ControladorMenuSegon;
import presentacio.partida.controlador.ControladorVistaPartida;
import presentacio.tauler.controlador.ControladorTaulerBreaker;
import presentacio.tauler.controlador.ControladorTaulerMaster;

/**
 * Controlador de presentació, és el que gestiona tot, la classe que et trobes
 * 	al iniciar el programa, des d'aquí es controla tot.
 *
 */
public class ControladorPresentacio {
	
	public static void main(String[] args) {
		ControladorPresentacio cp = new ControladorPresentacio();
		cp.comencamentTransicio();
		
	}
	
	private ControladorDomini ctrlDom;
	
	private ControladorVistaLogin ctrlVistaLogin;
	private ControladorVistaPartida ctrlVistaPartida;
	private ControladorMenuPrimer ctrlMenuPrimer;
	private ControladorMenuSegon ctrlMenuSegon;
	private ControladorTaulerBreaker ctrlTaulerBreaker;
	private ControladorTaulerMaster ctrlTaulerMaster;
	
	/**
	 * Aquí s'inicialitza tot el domini, incloent la càrrega de de jugadors i partides
	 * 	al domini. Per veure la primera finestra del controlador cal cridar al
	 * 	métode: {@link ControladorPresentacio#comencamentTransicio()}
	 */
	public ControladorPresentacio() {
		ctrlDom = new ControladorDomini();
		try {
			ctrlDom.inicialitzarControlador();
		} catch (ExcErrorPersistencia e) {
			new VistaError().mostraDialogError(e.getMessage(), "Error persistencia!");
			throw e;
		} catch (ExcErrorDomini e) {
			new VistaError().mostraDialogError(e.getMessage(), "Error domini!");
			throw e;
		}
		
	}
	
	/**
	 * Inicialitza el controlador de la vista de login i la mostra.
	 */
	public void comencamentTransicio() {
    	ctrlVistaLogin = new ControladorVistaLogin(ctrlDom.getCtrlMantJugador(), this);
		ctrlVistaLogin.mostraVista();
	}
	

	// Quan es selecciona o es crea un jugador
	/**
	 * Tanca el controlador vista login, inicialitza el del menu primer i el mostra.
	 */
	public void transicio_vistaLogin_vistaMenuPrimer() {
		ctrlVistaLogin.amagaVista();
		ctrlMenuPrimer = new ControladorMenuPrimer(ctrlDom.getCtrlMantJugador(), this);
		ctrlMenuPrimer.construeixVista();
		ctrlMenuPrimer.mostraVista();
	}
	
	// Quan es dona al botó jugar
	/**
	 * Tanca el controlador de menu primer, inicialitza el del menu segon i el mostra.
	 */
	public void transicio_vistaMenuPrimer_vistaMenuSegon() {
		ctrlMenuPrimer.amagaVista();
		ctrlMenuSegon = new ControladorMenuSegon(ctrlDom.getCtrlMantPartida(), this);
		ctrlMenuSegon.construeixVista();
		ctrlMenuSegon.mostraVista();
	}
	
	// Quan es dona al boto nova partida estàndar
	/**
	 * Amaga el controlador del menu segon i mostra la vista de configuració de partida estàndar.
	 */
	public void transicio_vistaMenuSegon_vistaMenuSegonEstandar() {
		ctrlMenuSegon.amagaVista();
		ctrlMenuSegon.mostraVistaEstandar();
	}
	
	// Quan es dona al botó nova partida personalitzada
	/**
	 * Amaga el controlador del menu segon i mostra la vista de configuracio de partida no estàndar.
	 */
	public void transicio_vistaMenuSegon_vistaMenuSegonPersonalitzada() {
		ctrlMenuSegon.amagaVista();
		ctrlMenuSegon.mostraVistaPersonalitzada();
	}
	
	// Quan es dona al botó acceptar al configurar la partida
	/**
	 * Amaga la vista de configuracio de partida estandar i mostra la vista de menu segon.
	 */
	public void transicio_vistaMenuSegonEstandar_vistaMenuSegon() {
		ctrlMenuSegon.amagaVistaEstandar();
		ctrlMenuSegon.mostraVista();
	}
	
	// Quan es dona al botó acceptar al configurar la partida personalitzada
	/**
	 * Amaga la vista de configuracio de partida no estàndar i mostra la vista de menu segon.
	 */
	public void transicio_vistaMenuSegonPersonalitzada_vistaMenuSegon() {
		ctrlMenuSegon.amagaVistaPersonalitzada();
		ctrlMenuSegon.mostraVista();
	}
	
	// Quan es dona al botó cancelar al configurar la partida estàndar
	/**
	 * Amaga la vista de configuracio de partida estandar, inicialitza el controlador
	 * 	de vista partida i el mostra.
	 */
	public void transicio_vistaMenuSegonEstandar_vistaPartida() {
		ctrlMenuSegon.amagaVistaEstandar();
		ctrlVistaPartida = new ControladorVistaPartida(ctrlDom.getCtrlMantPartida(), this);
		ctrlVistaPartida.construeixVista();
		ctrlVistaPartida.mostraVista();
	}
	
	// Quan es dona al botó cancelar al configurar la partida personalitzada
	/**
	 * Amaga la vista de configuracio de partida no estandar, inicialitza el controlador
	 * 	de vista partida i el mostra.
	 */
	public void transicio_vistaMenuSegonPersonalitzada_vistaPartida() {
		ctrlMenuSegon.amagaVistaPersonalitzada();
		ctrlVistaPartida = new ControladorVistaPartida(ctrlDom.getCtrlMantPartida(), this);
		ctrlVistaPartida.construeixVista();
		ctrlVistaPartida.mostraVista();
	}
	
	//Quan es carrega una partida
	/**
	 * Amaga la vista de menu segon, inicialitza el controlador de vista partida i el mostra.
	 */
	public void transicio_vistaMenuSegon_vistaPartida() {
		ctrlMenuSegon.amagaVista();
		ctrlVistaPartida = new ControladorVistaPartida(ctrlDom.getCtrlMantPartida(), this);
		ctrlVistaPartida.construeixVista();
		ctrlVistaPartida.mostraVista();
	}
	
	/**
	 * Amaga la vista de partida, inicialitza el controlador de menu segon i el mostra.
	 */
	public void transicio_vistaPartida_vistaMenuSegon() {
		ctrlVistaPartida.amagaVista();
		ctrlMenuSegon = new ControladorMenuSegon(ctrlDom.getCtrlMantPartida(), this);
		ctrlMenuSegon.construeixVista();
		ctrlMenuSegon.mostraVista();
	}
	
	/**
	 * Amaga la vista de partida, inicialitza el controlador de tauler breaker i el mostra.
	 */
	public void transicio_vistaPartida_vistaTaulerBreaker() {
		ctrlVistaPartida.amagaVista();
		ctrlTaulerBreaker = new ControladorTaulerBreaker(ctrlDom.getCtrlMantJoc(), this);
		ctrlTaulerBreaker.construeixVista();
		ctrlTaulerBreaker.construeixJocComencat();
		//ctrlTaulerBreaker.
		ctrlTaulerBreaker.mostraVista();
	}
	
	/**
	 * Amaga la vista de partida, inicialitza el controlador de tauler master i el mostra.
	 */
	public void transicio_vistaPartida_vistaTaulerMaster() {
		ctrlVistaPartida.amagaVista();
		ctrlTaulerMaster = new ControladorTaulerMaster(ctrlDom.getCtrlMantJoc(), this);
		ctrlTaulerMaster.construeixVista();
		ctrlTaulerMaster.construeixJocComencat();
		//ctrlTaulerBreaker.
		ctrlTaulerMaster.mostraVista();
	}
	
	/**
	 * Amaga la vista de tauler breaker, inicialitza el conrolador de vista partida
	 * 	i el mostra.
	 */
	public void transicio_vistaTaulerBreaker_vistaPartida() {
		ctrlTaulerBreaker.amagaVista();
		ctrlVistaPartida = new ControladorVistaPartida(ctrlDom.getCtrlMantPartida(), this);
		ctrlVistaPartida.construeixVista();
		ctrlVistaPartida.mostraVista();
	}

	/**
	 * Amaga la vista de tauler master, inicialitza el conrolador de vista partida
	 * 	i el mostra.
	 */
	public void transicio_vistaTaulerMaster_vistaPartida() {
		ctrlTaulerMaster.amagaVista();
		ctrlVistaPartida = new ControladorVistaPartida(ctrlDom.getCtrlMantPartida(), this);
		ctrlVistaPartida.construeixVista();
		ctrlVistaPartida.mostraVista();
	}
}
