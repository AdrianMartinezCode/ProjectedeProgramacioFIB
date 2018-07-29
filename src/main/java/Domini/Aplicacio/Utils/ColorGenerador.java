package Domini.Aplicacio.Utils;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ColorGenerador {
	
	private static final Map<Integer, Color> colors;
	private static final Color defecte = Color.DARK_GRAY;
	private static final int pistablanca = Integer.MAX_VALUE;
	private static final int pistanegra = Integer.MAX_VALUE - 1;
	private static final int pistasense = Integer.MAX_VALUE - 2;
	
	static {
		colors = new HashMap<>();
		colors.put(0, Color.YELLOW);
		colors.put(1, Color.BLUE);
		colors.put(2, Color.CYAN);
		colors.put(3, Color.GREEN);
		colors.put(4, Color.MAGENTA);
		colors.put(5, Color.ORANGE);
		colors.put(6, Color.RED);
		colors.put(7, Color.GRAY);
		
		colors.put(pistasense, null);
		colors.put(pistanegra, Color.BLACK);
		colors.put(pistablanca, Color.WHITE);
	}
	
	
	/**
	 * Transforma una variable de tipus entera a una classe
	 * 	de tipus {@link Color}
	 * @param n la variable entera,
	 * @return una instància de tipus Color, si el color no es
	 * 	correspon a un valor correcte retorna {@link Color#DARK_GRAY}.
	 */
	public static Color getColor(int n) {
		Color c = colors.get(n);
		if (c == null)
			return defecte;
		return c;
	}
	
	/**
	 * Tradueix el color especificat amb el identificador corresponent.
	 * @param c El color que volem traduïr.
	 * @return la variable de tipus entera que correspon al color.
	 */
	public static int getNumByColor(Color c) {
		for (Entry<Integer, Color> ent : colors.entrySet()) {
			if ((ent.getValue() == null && c == null) || (ent.getValue() != null && c != null && c.equals(ent.getValue())))
				return ent.getKey();
		}
		return -1;
	}
	
	/**
	 * Retorna el color corresponent a una pista blanca.
	 * @return una instància del color de la pista blanca.
	 */
	public static Color getColorPistaBlanca() {
		return colors.get(pistablanca);
	}
	/**
	 * Retorna el color corresponent a una pista negra.
	 * @return una instància del color de la pista negra.
	 */
	public static Color getColorPistaNegra() {
		return colors.get(pistanegra);
	}
	/**
	 * 
	 * @return la variable de tipus entera de la pista blanca.
	 */
	public static int getNumPistaBlanca() {
		return pistablanca;
	}
	/**
	 * 
	 * @return la variable de tipus entera de la pista negra.
	 */
	public static int getNumPistaNegra() {
		return pistanegra;
	}
	/**
	 * 
	 * @return la variable de tipus entera de la pista que no és correcta.
	 */
	public static int getNumPistaIncorrecta() {
		return pistasense;
	}

}
