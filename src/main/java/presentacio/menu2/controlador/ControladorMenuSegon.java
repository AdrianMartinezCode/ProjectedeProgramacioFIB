package presentacio.menu2.controlador;

import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import Domini.Aplicacio.Excepcions.ExcFiltreNoPassat;
import Domini.Aplicacio.Excepcions.ExcJaExisteixJugador;
import Domini.Aplicacio.Excepcions.ExcNoTePartidesGuardades;
import Domini.Aplicacio.Utils.ComparadorCondicional;
import Domini.Aplicacio.Utils.Pair;
import Domini.Controladors.ControladorDomini;
import Domini.Controladors.ControladorMantenimentJugador;
import Domini.Controladors.ControladorMantenimentPartida;
import presentacio.ControladorPresentacio;
import presentacio.menu2.VistaConfiguracioPartidaEstandar;
import presentacio.menu2.VistaConfiguracioPartidaPersonalitzada;
import presentacio.menu2.VistaMenuSegon;
import presentacio.utils.DadesLlistaTableObtenible;

public class ControladorMenuSegon implements DadesLlistaTableObtenible, ObservadorMenuSegon, ObservadorConfiguracioPartida{
	
	private ControladorPresentacio ctrlDom;
	private ControladorMantenimentPartida cmp;
	private VistaMenuSegon vista;
	private VistaConfiguracioPartidaEstandar vistaEstandar;
	private VistaConfiguracioPartidaPersonalitzada vistaPersonalitzada;

	
	public ControladorMenuSegon(ControladorMantenimentPartida cmp, ControladorPresentacio ctrlDom) {
		this.cmp = cmp;
		this.ctrlDom = ctrlDom;
	}
	
	public void construeixVista() {
		vista = new VistaMenuSegon(this);
		vista.inicialitzarComponents();
		vista.afegirListeners();
		vista.donaAltaObservador(this);
		
		String nomDificultatAlta = cmp.getNomDificultatAlta();
		String nomDificultatBaixa = cmp.getNomDificultatBaixa();
		Pair<Integer, Integer> pColors = cmp.getMinMaxColors();
		Pair<Integer, Integer> pPosicions = cmp.getMinMaxPosicions();
		Pair<Integer, Integer> pTorns = cmp.getMinMaxTorns();
		Pair<Integer, Integer> pNbJocs = cmp.getMinMaxNbJocs();
		
		vistaPersonalitzada = new VistaConfiguracioPartidaPersonalitzada(
				nomDificultatAlta, nomDificultatBaixa, pColors.getFirst(), pColors.getSecond(),
				pPosicions.getFirst(), pPosicions.getSecond(), pTorns.getFirst(), pTorns.getSecond(),
				pNbJocs.getFirst(), pNbJocs.getSecond());
		vistaPersonalitzada.inicialitzarComponents();
		vistaPersonalitzada.afegirListeners();
		vistaPersonalitzada.donaAltaObservador(this);
		
		vistaEstandar = new VistaConfiguracioPartidaEstandar(nomDificultatAlta, nomDificultatBaixa);
		vistaEstandar.inicialitzarComponents();
		vistaEstandar.afegirListeners();
		vistaEstandar.donaAltaObservador(this);
	}
	
	public void mostraVista() {
		vista.mostraVista();
	}
	public void mostraVistaPersonalitzada() {
		vistaPersonalitzada.mostraVista();
	}
	public void mostraVistaEstandar() {
		vistaEstandar.mostraVista();
	}
	public void amagaVista() {
		vista.amagaVista();
	}
	public void amagaVistaPersonalitzada() {
		vistaPersonalitzada.amagaVista();
	}
	public void amagaVistaEstandar() {
		vistaEstandar.amagaVista();
	}


	@Override
	public void botoTriarPremut() {
		try {
			Object idPartObj = vista.getSeleccioList(cmp.getColumnIndexId());
			if (idPartObj == null) {
				vista.mostraMessageDialog("Error, no hi ha seleccionada cap partida.", "Error", JOptionPane.ERROR_MESSAGE);
				return;
			}
			int idPart = (int)idPartObj;
			cmp.triarPartidaGuardada(idPart);
			ctrlDom.transicio_vistaMenuSegon_vistaPartida();
		} catch (ExcNoTePartidesGuardades e) {
			vista.mostraMessageDialog("No existeixen partides guardades.", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}


	@Override
	public void botoEstandarPremut() {
		ctrlDom.transicio_vistaMenuSegon_vistaMenuSegonEstandar();
	}


	@Override
	public void botoPersonalitzadaPremut() {
		ctrlDom.transicio_vistaMenuSegon_vistaMenuSegonPersonalitzada();
	}
	
	@Override
	public void botoAcceptarPremut(int tipusConfifuracio) {
		if (tipusConfifuracio == VistaConfiguracioPartidaEstandar.ID_CONFIGURACIO) {
			try {
				cmp.novaPartidaEstandar(vistaEstandar.getAjuts(), vistaEstandar.getDificultat());
				ctrlDom.transicio_vistaMenuSegonEstandar_vistaPartida();
			} catch (ExcFiltreNoPassat e) {
				vistaEstandar.mostraMessageDialog(e.getMessage(), "Error en atribut", JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
			
		} else if (tipusConfifuracio == VistaConfiguracioPartidaPersonalitzada.ID_CONFIGURACIO) {
			try {
				cmp.novaPartidaPersonalitzada(
						vistaPersonalitzada.getAjuts(), 
						vistaPersonalitzada.getDificultat(),
						vistaPersonalitzada.getNumColors(),
						vistaPersonalitzada.getNumPosicions(), 
						vistaPersonalitzada.getNumTorns(),
						vistaPersonalitzada.getNbJocs());
				ctrlDom.transicio_vistaMenuSegonPersonalitzada_vistaPartida();
			} catch (NumberFormatException e) {
				vistaEstandar.mostraMessageDialog("Error en algun dels camps!!", "Error en un camp", JOptionPane.ERROR_MESSAGE);
			} catch (ExcFiltreNoPassat e) {
				vistaEstandar.mostraMessageDialog(e.getMessage(), "Error en atribut", JOptionPane.ERROR_MESSAGE);
				//e.printStackTrace();
			}
		}
	}

	@Override
	public void botoCancelarPremut(int tipusConfifuracio) {
		if (tipusConfifuracio == VistaConfiguracioPartidaEstandar.ID_CONFIGURACIO) {
			ctrlDom.transicio_vistaMenuSegonEstandar_vistaMenuSegon();
		} else if (tipusConfifuracio == VistaConfiguracioPartidaPersonalitzada.ID_CONFIGURACIO) {
			ctrlDom.transicio_vistaMenuSegonPersonalitzada_vistaMenuSegon();
		}
	}

	@Override
	public Vector<Vector<Object>> getDades() {
		return cmp.getDadesPartidesGuardades();
	}

	@Override
	public Vector<ComparadorCondicional> getComparadors() {
		return cmp.getComparadors();
	}

	@Override
	public Vector<Class<?>> getTypes() {
		return cmp.getTypesEntrades();
	}

	@Override
	public Vector<String> dadesNomColumnes() {
		return cmp.getNomColumnes();
	}

	
	
	
}
