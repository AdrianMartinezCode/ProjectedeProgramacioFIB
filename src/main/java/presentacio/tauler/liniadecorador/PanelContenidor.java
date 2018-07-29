package presentacio.tauler.liniadecorador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;

public class PanelContenidor extends PanelGeneralLinia {
	
	public PanelContenidor() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	}

	@Override
	public void afegeixLinia(PanelLinia pl) {
		Dimension d = pl.getLiniaSize();
		super.add(pl);
		pl.setBorder(BorderFactory.createMatteBorder(3, 0, 0, 0, Color.BLACK));
		pl.setMaximumSize(new Dimension(Integer.MAX_VALUE, d.height + 3));
	}

	@Override
	public void eliminaListenerPistes() {
		// No s'ha de fer res
	}

	@Override
	public void eliminaListenerFitxes() {
		// No s'ha de fer res
	}

	@Override
	public int getNumLinies() {
		return 0;
	}

	@Override
	public Vector<Integer> getUltimaPista() {
		return null;
	}

	@Override
	public Vector<Integer> getUltimaCombinacio() {
		return null;
	}

	@Override
	public void afegirListenerPistes(MouseListener al) {
		
	}

	@Override
	public void afegirListenerFitxes(MouseListener al) {
		
	}

	@Override
	public void setColorPosFitxa(int pos, int idColor) {
		
	}

	@Override
	public void setColorsFitxes(Vector<Integer> fitxes) {
		
	}

	@Override
	public boolean combinacioColorada() {
		return false;
	}

	@Override
	public void setColorsPistes(Vector<Integer> pistes) {
		
	}
}
