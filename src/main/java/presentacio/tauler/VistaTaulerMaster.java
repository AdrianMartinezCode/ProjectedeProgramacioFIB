package presentacio.tauler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import Domini.Aplicacio.Utils.ColorGenerador;
import presentacio.DadesPartidaObtenible;
import presentacio.tauler.liniadecorador.PanelLinia;

/**
 * Vista del tauler quan el jugador té el rol de Code Master.
 */
public class VistaTaulerMaster extends VistaTauler {

	private int indexLastColor;
	
	public VistaTaulerMaster(int numTorns, int numColors, int numPosicions, DadesPartidaObtenible obt) {
		super(numTorns, numColors, numPosicions, obt);
		indexLastColor = 0;
	}
	
	@Override
	public void inicialitzarComponents() {
		super.inicialitzarComponents();
	}
	
	/**
	 * Inicia l'estat de introducció de colors de la combinació inicial.
	 */
	public void iniciaEstatCombinacioInicial() {
		createListenersEstatInicial();
	}
	
	/**
	 * Afegeix un listener per a la línia actual que hi ha al tauler, en aquest
	 * 	cas afegeix el listener per a les pistes.
	 */
	public void afegeixListenersLiniaActual() {
		panelCentralCentre.afegirListenerPistes(new ClickListenerSeleccioPista());
	}
	
	/**
	 * Afegeix la següent línia, afegint-li els listeners de la linia actual i
	 * 	pintant la combinació d'aquesta nova línia, llest tot per afegir les pistes
	 * 	manualment mitjançant el clic del ratolí.
	 * @param colors el vector de integers on cada integer identifica un color.
	 */
	public void seguentLinia(Vector<Integer> colors) {
		super.seguentLinia();
		afegeixListenersLiniaActual();
		panelCentralCentre.setColorsFitxes(colors);
	}
	
	/**
	 * Canvia l'estat del tauler per el de combinació inicial introduïda, 
	 * 	a partir d'aquest només s'afegiràn línies i es ficaràn pistes.
	 * @param primeraLinia La primera línia generada per la màquina
	 */
	public void estatCombinacioInicialIntroduida(Vector<Integer> primeraLinia) {
		eliminaListenersEstatInicial();
		seguentLinia();
		panelCentralCentre.setColorsFitxes(primeraLinia);
		panelCentralCentre.afegirListenerPistes(new ClickListenerSeleccioPista());
	}
	
	private void eliminaListenersEstatInicial() {
		super.eliminaListenersColorsSeleccio();
		for(PanelDibuixant pd : panelsMaster)
			for (MouseListener al : pd.getListeners(MouseListener.class))
				pd.removeMouseListener(al);
	}
	
	private void createListenersEstatInicial() {
		super.creaListenersColorsSeleccio(new ClickListenerSeleccioColors());
		MouseListener mms = new ClickListenerMasterColors();
		
		for(PanelDibuixant pd : panelsMaster) {
			pd.addMouseListener(mms);
		}
	}
	
	public void incrementaIndexLastColor() {
		indexLastColor++;
		indexLastColor = indexLastColor%panelsMaster.size();
	}
	
	/**
	 * Mira si tots els panells del master estàn colorats.
	 * @return cert si estàn tots colorats, fals altrament.
	 */
	public boolean totsColoratsMaster() {
		int i = 0;
		while(i < panelsMaster.size()) {
			if (!panelsMaster.get(i).teColor())
				return false;
			++i;
		}
		return true;
	}
	
	/**
	 * Retorna l'índex del panel dibuixant especificat.
	 * @param pd el panell dibuixant.
	 * @return l'índex del panell al vector de panells master, -1 si no l'ha trobat.
	 */
	public int indexOf(PanelDibuixant pd) {
		int i = 0;
		while(i < panelsMaster.size()) {
			if (panelsMaster.get(i) == pd)
				return i;
			i++;
		}
		return -1;
	}
	
	class ClickListenerSeleccioPista extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			PanelDibuixantPista pd = (PanelDibuixantPista)e.getComponent();
			pd.changeColor();
			pd.repaint();
		}
	}
	
	class ClickListenerSeleccioColors extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			int idColor = ((PanelDibuixantColor)e.getSource()).getIdColor();
			PanelDibuixantColor pd = panelsMaster.get(indexLastColor);
			pd.setColor(ColorGenerador.getColor(idColor), idColor);
			pd.repaint();
			incrementaIndexLastColor();
		}
	}
	
	
	
	class ClickListenerMasterColors extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			PanelDibuixantColor pd = ((PanelDibuixantColor)e.getSource());
			pd.quitaColor();
			pd.repaint();
			indexLastColor = indexOf(pd);
		}
	}
}
