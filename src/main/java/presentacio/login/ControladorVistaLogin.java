package presentacio.login;

import javax.swing.JOptionPane;

import Domini.Aplicacio.Excepcions.ExcErrorDomini;
import Domini.Aplicacio.Excepcions.ExcErrorPersistencia;
import Domini.Aplicacio.Excepcions.ExcJaExisteixJugador;
import Domini.Aplicacio.Excepcions.ExcNoExisteixJugador;
import Domini.Controladors.ControladorMantenimentJugador;
import presentacio.ControladorPresentacio;

public class ControladorVistaLogin {
	
	private ControladorPresentacio ctrlPres;
	private ControladorMantenimentJugador ctrlJugador;
	private VistaLogin vistaLogin;
	
	//Constructores i m�tode d'inicialitzaci�
	
	public ControladorVistaLogin(ControladorMantenimentJugador ctrlJugador, ControladorPresentacio ctrlPres) {
		this.ctrlJugador = ctrlJugador;
		this.ctrlPres = ctrlPres;
		vistaLogin = new VistaLogin(this);
	}
	
	public void mostraVista() {
		vistaLogin.visible();
	}
	
	public void amagaVista() {
		vistaLogin.amagaVista();
	}
	
	//METODES SINCRONITZACIO ENTRE VISTES ?
	
	/* PER PROVAR COM ES VEU
	public static void main (String[] args) {
	    javax.swing.SwingUtilities.invokeLater (
	      new Runnable() {
	        public void run() {
	        	ControladorPresentacioVistaLogin ctrlPresentacion = new ControladorPresentacioVistaLogin();
	          ctrlPresentacion.inicialitzarPresentacio();
	    }});
	  }
	*/
	
	//CRIDES AL CONTROLADOR DE DOMINI
	
	public void creaJugador(int id, String nomJ) {
		try {
			ctrlJugador.crearJugador(id, nomJ);
			ctrlPres.transicio_vistaLogin_vistaMenuPrimer();
		} catch (ExcJaExisteixJugador e) {
			//e.printStackTrace();
			vistaLogin.mostraMessageDialog("Ja existeix un jugador amb aquesta ID", "Error!", JOptionPane.ERROR_MESSAGE);
			
		} catch(ExcErrorDomini e) {
			e.printStackTrace();
		} catch(ExcErrorPersistencia e) {
			e.printStackTrace();
		}
		
	}
	
	public void escollirJugador(int idJug) {
		try {
			ctrlJugador.escollirJugador(idJug);
			ctrlPres.transicio_vistaLogin_vistaMenuPrimer();
		} catch (ExcNoExisteixJugador e) {
			vistaLogin.mostraMessageDialog("No existeix cap jugador amb aquesta ID", "Error!", JOptionPane.ERROR_MESSAGE);
		}
	}

}
