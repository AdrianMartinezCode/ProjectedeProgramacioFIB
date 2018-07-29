package Domini.Aplicacio.Stubs;

import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Collectors;

import Domini.IEntitatDomini;
import Domini.IEntitatDominiHerencia;

public class EntitatStubHerencia1 extends EntitatStubIEntitatDominiHerencia {

	
	@Override
	public String convertirToString() {
		String dades = super.convertirToString();
		/*String[] strs = dades.split(" ");
		strs[strs.length - 1] = this.getClass().getName();
		return Arrays.stream(strs).collect(Collectors.joining(" "));*/
		System.out.println(this.getClass().getName());
		return dades + " " + this.getClass().getName();
	}

	@Override
	public IEntitatDomini miClone() {
		IEntitatDominiHerencia e = new EntitatStubHerencia1();
		e.obtenerFromString(this.convertirToString());
		return e;
	}

	@Override
	public String novaEntitatParametres(int id, Vector<String> params) {
		// TODO Auto-generated method stub
		return super.novaEntitatParametres(id, params) + " " + EntitatStubHerencia1.class.getName();
	}
}
