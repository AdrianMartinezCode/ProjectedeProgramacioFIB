package Domini.Aplicacio.Utils;

import java.util.Comparator;

public abstract class ComparadorCondicional<T> implements Comparator<T> {
	
	/**
	 * L'ordre és ascendent o descendent.
	 */
	private boolean ascendent;
	
	public ComparadorCondicional() {
		ascendent = true;
	}
	/*public ComparadorCondicional(ComparadorCondicional cmp) {
		
	}*/
	
	public void changeState() {
		ascendent = !ascendent;
	}
	
	public boolean ascendent() {
		return ascendent;
	}

}
