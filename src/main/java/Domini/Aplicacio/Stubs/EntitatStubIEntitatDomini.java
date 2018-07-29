package Domini.Aplicacio.Stubs;

import java.util.Vector;

import Domini.IEntitatDomini;

public class EntitatStubIEntitatDomini implements IEntitatDomini {
	
	private int id;
	private String param;
	
	public EntitatStubIEntitatDomini() {}

	public boolean setId(int id) {
		if (id < 0)
			return false;
		this.id = id;
		return true;
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
	public IEntitatDomini miClone() {
		EntitatStubIEntitatDomini e = new EntitatStubIEntitatDomini();
		e.id = this.id;
		e.param = this.param;
		return e;
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
