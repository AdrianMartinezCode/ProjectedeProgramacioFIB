package presentacio.menu1.controlador;

import java.util.Vector;

import Domini.Aplicacio.Excepcions.ExcJaExisteixJugador;
import Domini.Aplicacio.Utils.ComparadorCondicional;
import Domini.Controladors.ControladorDomini;
import Domini.Controladors.ControladorMantenimentJugador;
import presentacio.ControladorPresentacio;
import presentacio.menu1.VistaMenuPrimer;
import presentacio.utils.DadesLlistaTableObtenible;

/**
 * Controlador de l'escenari de menu primer.
 *
 */
public class ControladorMenuPrimer implements DadesLlistaTableObtenible, ObservadorMenuPrimer {
	
	/**
	 * Hem de tindre el controlador de manteniment jugador per anar notificant
	 * 	els canvis.
	 */
	private ControladorMantenimentJugador cmj;
	/**
	 * Hem de tindre el controlador de presentació per fer les transicions
	 * 	de les finestres.
	 */
	private ControladorPresentacio ctrlDom;
	private VistaMenuPrimer vista;
	
	public ControladorMenuPrimer(ControladorMantenimentJugador cmj, ControladorPresentacio ctrlDom) {
		this.ctrlDom = ctrlDom;
		this.cmj = cmj;
	}
	
	public void construeixVista() {
		vista = new VistaMenuPrimer(this);
		vista.inicialitzarComponents();
		vista.donaAltaObservador(this);
	}
	
	public void mostraVista() {
		vista.mostraVista();
	}
	public void amagaVista() {
		vista.amagaVista();
	}

	@Override
	public Vector<Vector<Object>> getDades() {
		return cmj.getDadesJugadors();
	}

	@Override
	public Vector<String> dadesNomColumnes() {
		return cmj.obtindreNomColumnesRanking();
	}

	@Override
	public Vector<ComparadorCondicional> getComparadors() {
		return cmj.getComparadors();
	}

	@Override
	public Vector<Class<?>> getTypes() {
		return cmj.getTypesDadesJugadors();
	}

	@Override
	public void botoJugarPremut() {
		this.ctrlDom.transicio_vistaMenuPrimer_vistaMenuSegon();
	}
}
