package Domini.Aplicacio.Stubs;

import java.util.Vector;

import Domini.IEntitatDomini;
import Domini.IEntitatDominiHerencia;

public abstract class EntitatStubIEntitatDominiHerencia implements IEntitatDominiHerencia {
	
	private int id;
	private String param;
	
	public EntitatStubIEntitatDominiHerencia() {}

	public boolean setId(int id) {
		if (id < 0)
			return false;
		this.id = id;
		return true;
	}
	
	@Override
	public String getNomClasseFromDades(String dades) {
		String[] strs = dades.split(" ");
		return strs[strs.length - 1];
	}

	public boolean setParam(String param) {
		this.param = param;
		return true;
	}

	@Override
	public String convertirToString() {
		return String.valueOf(id) + " " + param;
	}

	@Override
	public int obtenerFromString(String vstr) {
		String[] str = vstr.split(" ");
		try {
			if (!setId(Integer.valueOf(str[0])) || !setParam(str[1]))
				return 2;
		} catch (NumberFormatException e) {
			return 1;
		}
		return 0;
	}

	@Override
	public String novaEntitatParametres(int id, Vector<String> params) {
		if (params == null)
			return null;
		return id + " " + params.get(0);
	}
	
	@Override
	public String getSeparador() {
		return " ";
	}
}
