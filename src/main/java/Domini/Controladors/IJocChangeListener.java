package Domini.Controladors;

import Domini.Joc.JocActual;
import Domini.Joc.Rol;

public interface IJocChangeListener {
	public void changeJoc(Rol r, JocActual j);
}
