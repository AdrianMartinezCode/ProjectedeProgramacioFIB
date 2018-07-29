package presentacio.tauler.liniadecorador;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;

import Domini.Aplicacio.Utils.ColorGenerador;
import presentacio.tauler.PanelDibuixant;
import presentacio.tauler.PanelDibuixantColor;
import presentacio.tauler.PanelDibuixantPista;


/**
 * És el component que representa una línia, sobre aquest anem afegint més línies.
 */
public class PanelLinia extends PanelGeneralLinia {
	/**
	 * La combinació de fitxes de la línia.
	 */
	private Vector<PanelDibuixantColor> panelsFitxes;
	/**
	 * La combinació de pistes de la línia.
	 */
	private Vector<PanelDibuixantPista> panelsPistes;
	/**
	 * El panell anterior, pot ser o el panel contenidor o una línia.
	 */
	private PanelGeneralLinia paneAnterior;

	private int numPosicions;
	/**
	 * La dimensió d'una fitxa en aquest panell.
	 */
	private Dimension sizeFitxa;
	
	public PanelLinia(PanelGeneralLinia paneAnterior, int numPosicions, Dimension sizeFitxa) {
		super();
		this.numPosicions = numPosicions;
		this.sizeFitxa = sizeFitxa;
		panelsFitxes = new Vector<PanelDibuixantColor>();
		panelsPistes = new Vector<PanelDibuixantPista>();
		this.paneAnterior = paneAnterior;
	}
	
	/**
	 * Obté la mida de la línia total.
	 * @return retorna un objecte Dimension amb la mida.
	 */
	public Dimension getLiniaSize() {
		return new Dimension((numPosicions + 1)*sizeFitxa.width, sizeFitxa.height);
	}
	
	@Override
	public void afegeixLinia(PanelLinia pl) {
		paneAnterior.afegeixLinia(pl);
	}
	
	@Override
	public void eliminaListenerPistes() {
		for (PanelDibuixant pd : panelsPistes) {
			for (MouseListener ml : pd.getListeners(MouseListener.class)) {
				pd.removeMouseListener(ml);
			}
		}
	}

	@Override
	public void eliminaListenerFitxes() {
		for (PanelDibuixant pd : panelsFitxes) {
			for (MouseListener ml : pd.getListeners(MouseListener.class)) {
				pd.removeMouseListener(ml);
			}
		}
	}
	
	
	@Override
	public void afegirListenerPistes(MouseListener al) {
		paneAnterior.eliminaListenerPistes();
		for (PanelDibuixant pd : panelsPistes)
			pd.addMouseListener(al);
	}
	
	@Override
	public void afegirListenerFitxes(MouseListener al) {
		paneAnterior.eliminaListenerFitxes();
		for (PanelDibuixant pd : panelsFitxes)
			pd.addMouseListener(al);
	}
	
	private int getXYAxisPistes() {
		double l = Math.sqrt(numPosicions);
		int n = (int)l;
		if (l%1 > 0)
			n++;
		return n;
	}
	
	
	public void construeixPanels() {
		super.setLayout(new GridBagLayout());
		for (int i = 0; i < numPosicions; i++) {
			PanelDibuixantColor pd = new PanelDibuixantColor();
			pd.setPreferredSize(sizeFitxa);
			GridBagConstraints c = new GridBagConstraints();
			c.gridx = i;
			c.gridy = 0;
			c.fill = GridBagConstraints.BOTH;
			super.add(pd, c);
			panelsFitxes.add(pd);
		}
		JPanel panePista = new JPanel();
		int n = getXYAxisPistes();
		panePista.setLayout(new GridLayout(1, numPosicions));
		
		for (int i = 0; i < numPosicions; i++) {
			PanelDibuixantPista pd = new PanelDibuixantPista();
			Dimension sizeFitxaPista = new Dimension(sizeFitxa.width/n, sizeFitxa.height/n);
			pd.setPreferredSize(sizeFitxaPista);
			panePista.add(pd);
			panelsPistes.add(pd);
		}
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = numPosicions;
		c.gridy = 0;
		c.gridwidth = numPosicions/2;
		super.add(panePista, c);
		this.afegeixLinia(this);
	}

	@Override
	public int getNumLinies() {
		return paneAnterior.getNumLinies() + 1;
	}

	@Override
	public Vector<Integer> getUltimaPista() {
		Vector<Integer> v = new Vector<Integer>();
		for (PanelDibuixant pdc : panelsPistes) {
			v.add(ColorGenerador.getNumByColor(pdc.getColor()));
		}
		return v;
	}

	@Override
	public Vector<Integer> getUltimaCombinacio() {
		Vector<Integer> v = new Vector<Integer>();
		for (PanelDibuixantColor pdc : panelsFitxes)
			v.add(pdc.getIdColor());
		return v;
	}

	@Override
	public void setColorPosFitxa(int pos, int idColor) {
		PanelDibuixantColor pdc = panelsFitxes.get(pos);
		pdc.setColor(ColorGenerador.getColor(idColor), idColor);
		pdc.repaint();
	}

	@Override
	public void setColorsFitxes(Vector<Integer> fitxes) {
		int pos = 0;
		for (Integer i : fitxes) {
			PanelDibuixantColor pdc = panelsFitxes.get(pos);
			pdc.setColor(ColorGenerador.getColor(i), i);
			pdc.repaint();
			++pos;
		}
	}

	@Override
	public boolean combinacioColorada() {
		for (PanelDibuixantColor pdc : panelsFitxes)
			if (!pdc.teColor())
				return false;
		return true;
	}

	@Override
	public void setColorsPistes(Vector<Integer> pistes) {
		int pos = 0;
		for (Integer i : pistes) {
			PanelDibuixantPista pd = panelsPistes.get(pos);
			pd.setColor(i.intValue());
			pd.repaint();
			pos++;
		}
	}

	

	

	

}
