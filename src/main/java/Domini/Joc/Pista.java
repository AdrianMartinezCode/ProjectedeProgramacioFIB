package Domini.Joc;

import Domini.Aplicacio.IDadesPartida;
import Domini.Aplicacio.Utils.ColorGenerador;
import Domini.Joc.Excepcions.ExcPistaColorIncorrecte;

/**
 * Representa un grup de fitxes, però en aquest cas com una pista.
 *
 */
public class Pista extends GrupFitxes {
	
	public Pista(IDadesPartida dadesPart) {
		super(dadesPart);
	}
	
	
	/**
	 * Metode que afegeix les pistes a la combinacio. Si te ajuts, la pista es collocar en el seu lloc corresponent amb
	 * la seva fitxa de color, mentre que si no hi ha ajuts es collocar al final 
	 * @param c color de la pista (negre, blanc o sense color)
	 */
	@Override
	public boolean afegirFitxa(int c) {
		if (ColorGenerador.getNumPistaBlanca() != c &&
				ColorGenerador.getNumPistaNegra() != c &&
				ColorGenerador.getNumPistaIncorrecta() != c)
			throw new ExcPistaColorIncorrecte("La pista introduïda no es correspon a cap color de pista.");
		super.afegeixFitxa(c);
		return true;
	}

}
