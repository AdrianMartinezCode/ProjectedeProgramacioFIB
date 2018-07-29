package presentacio.tauler;

import java.awt.Color;

import Domini.Aplicacio.Utils.ColorGenerador;

/**
 * Ens facilita la gestió de colors d'una pista.
 *
 */
public class PanelDibuixantPista extends PanelDibuixant {
	
	/**
	 * 0 -> sense color
	 * 1 -> blanc
	 * 2 -> negre
	 */
	private int estat;
	
	
	public PanelDibuixantPista() {
		super();
		estat = 0;
	}
	
	/**
	 * Canvia el color de la fitxa pel següent que correspongui.
	 */
	public void changeColor() {
		++estat;
		estat = estat%3;
	}

	/**
	 * Modifica el color de la fitxa.
	 * @param estat és el identificador del color de la fitxa de {@link ColorGenerador}
	 */
	public void setColor(int estat) {
		if (estat == ColorGenerador.getNumPistaBlanca())
			this.estat = 1;
		else if (estat == ColorGenerador.getNumPistaNegra())
			this.estat = 2;
		else {
			this.estat = 0;
		}
		//super.repaint();
	}

	@Override
	public Color getColor() {
		if (estat == 1)
			return ColorGenerador.getColorPistaBlanca();
		else if (estat == 2)
			return ColorGenerador.getColorPistaNegra();
		return null;
	}

	@Override
	public boolean teColor() {
		return estat != 0;
	}

}
