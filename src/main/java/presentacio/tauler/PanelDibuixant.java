package presentacio.tauler;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

/**
 * Panell utilitzat per pintar fitxes al nostre tauler.
 *
 */
public abstract class PanelDibuixant extends JPanel {
	
	/**
	 * Atributs per facilitar el pintat d'un cercle.
	 */
	private double w, h, width, height, xcent, ycent;
	
	public PanelDibuixant() {
		super();
	}
	
	/**
	 * Consulta el color de la fitxa.
	 * @return obté una instància del color de la fitxa.
	 */
	public abstract Color getColor();
	/**
	 * Consulta si hi ha color a la fitxa.
	 * @return cert si en té, fals altrament.
	 */
	public abstract boolean teColor();
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		w = super.getWidth();
		h = super.getHeight();
		xcent = (w/2 - w/2.5);
		ycent = (h/2 - h/2.5);
		width = w - xcent*2;
		height = h - ycent*2;
		if (this.teColor()) {
			g.setColor(this.getColor());
			g.fillOval((int)xcent, (int)ycent, (int)width, (int)height);
		} else {
			g.setColor(Color.BLACK);
			g.drawOval((int)xcent, (int)ycent, (int)width, (int)height);
		}
	}
	
	@Override
	protected void processMouseEvent(MouseEvent e) {
		double radi = width/2;
		double distAct = Math.sqrt(Math.pow(e.getX() - (xcent + radi), 2) + Math.pow(e.getY() - (ycent + radi), 2));
		if (distAct < radi)
			super.processMouseEvent(e);
	}
}
