package presentacio.tauler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import Domini.Aplicacio.Utils.ColorGenerador;
import presentacio.DadesPartidaObtenible;
import presentacio.tauler.VistaTaulerMaster.ClickListenerSeleccioColors;
import presentacio.tauler.VistaTaulerMaster.ClickListenerSeleccioPista;

/**
 * Hi han certes parts del tauler que canvien si ets code master.
 *
 */
public class VistaTaulerBreaker extends VistaTauler {
	
	private int indexLastColor;
	
	public VistaTaulerBreaker(int numTorns, int numColors, int numPosicions, DadesPartidaObtenible obt) {
		super(numColors, numColors, numPosicions, obt);
	}
	
	@Override
	public void inicialitzarComponents() {
		super.inicialitzarComponents();
		super.creaListenersColorsSeleccio(new ClickListenerSeleccioColors());
	}
	
	/**
	 * Posa colors a les pistes i avança a la següent linia.
	 * @param pista un vector de integers amb els identificadors dels colors.
	 */
	public void seguentLinia(Vector<Integer> pista) {
		panelCentralCentre.setColorsPistes(pista);
		super.seguentLinia();
	}
	
	/**
	 * Afegeix les pistes a la línia actual.
	 * @param v un vector de integers amb l'identificador de cada pista.
	 */
	public void afegeixPista(Vector<Integer> v) {
		panelCentralCentre.setColorsPistes(v);
		panelCentral.repaint();
	}
	
	/**
	 * Mira si la última combinació posada està completament posada (que no quedi cap fitxa per possar)
	 * @return cert si està totalment colorada, altrament fals.
	 */
	public boolean ultimaCombinacioColorada() {
		return panelCentralCentre.combinacioColorada();
	}
	
	/**
	 * Treu tots els components del panell de colors del code master.
	 */
	public void enfosquirPanelMaster() {
		panelColorsMaster.removeAll();
	}
	
	/**
	 * Incrementa l'índex del panell colorat del master.
	 */
	public void incrementaIndexLastColor() {
		indexLastColor++;
		indexLastColor = indexLastColor%panelsMaster.size();
	}
	
	class ClickListenerSeleccioColors extends MouseAdapter {
		@Override
		public void mouseReleased(MouseEvent e) {
			super.mouseReleased(e);
			int idColor = ((PanelDibuixantColor)e.getSource()).getIdColor();
			panelCentralCentre.setColorPosFitxa(indexLastColor, idColor);
			incrementaIndexLastColor();
		}
	}


}
