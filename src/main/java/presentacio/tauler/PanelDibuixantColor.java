package presentacio.tauler;

import java.awt.Color;

/**
 * Ens facilita la gestió de colors de una fitxa.
 *
 */
public class PanelDibuixantColor extends PanelDibuixant{
	
	private Color color;
	private int numCol;
	private boolean teColor;
	
	public PanelDibuixantColor() {
		super();
	}
	
	public void setColor(Color color, int numCol) {
		this.color = color;
		this.numCol = numCol;
		teColor = true;
	}
	/**
	 * Elimina el color del panel.
	 */
	public void quitaColor() {
		teColor = false;
	}
	/**
	 * Consulta l'identificador del color.
	 * @return l'identificador del color.
	 */
	public int getIdColor() {
		return numCol;
	}
	@Override
	public boolean teColor() {
		return teColor;
	}

	@Override
	public Color getColor() {
		return color;
	}
}
