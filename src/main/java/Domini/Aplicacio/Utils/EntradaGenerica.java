package Domini.Aplicacio.Utils;

import java.util.Vector;

import Domini.Joc.Dificultat;
import Domini.Joc.Rol;


/**
 * Classe que serveix com a suport per a la gestió de les dades de les
 * 	classes que es vulguin representar com un llistat de strings i
 * 	ordenable.
 */
public abstract class EntradaGenerica {
	private static final ComparadorCondicional COMPARADOR_INTEGER;
	private static final ComparadorCondicional COMPARADOR_DOUBLE;
	private static final ComparadorCondicional COMPARADOR_STRING;
	private static final ComparadorCondicional COMPARADOR_BOOLEAN;
	private static final ComparadorCondicional COMPARADOR_ROL;
	private static final ComparadorCondicional COMPARADOR_DIFICULTAT;
	
	/**
	 * Configurem tots els comparados, on cada comparador ha d'ordenar d'una
	 * 	manera diferent segons el tipus que ordena.
	 */
	static {
		COMPARADOR_DIFICULTAT = new ComparadorCondicional<Dificultat>() {

			@Override
			public int compare(Dificultat o1, Dificultat o2) {
				if (o1 == null)
					return 1;
				if (o2 == null)
					return -1;
				int value = o2.ordinal() - o1.ordinal();
				if (this.ascendent()) {
					if (value > 0)
						return 1;
					return -1;
				} else {
					if (value < 0)
						return 1;
					return -1;
				}
			}

			
			
		};
		COMPARADOR_BOOLEAN = new ComparadorCondicional<Boolean>() {

			@Override
			public int compare(Boolean o1, Boolean o2) {
				if (ascendent())
					return o1.booleanValue() ? 1 : -1;
				return o2.booleanValue() ? 1 : -1;
			}
			
		};
		COMPARADOR_ROL = new ComparadorCondicional<Rol>() {

			@Override
			public int compare(Rol o1, Rol o2) {
				if (o1 == null)
					return 1;
				if (o2 == null)
					return -1;
				int value = o2.ordinal() - o1.ordinal();
				if (this.ascendent()) {
					if (value > 0)
						return 1;
					return -1;
				} else {
					if (value < 0)
						return 1;
					return -1;
				}
			}
		};
		COMPARADOR_STRING = new ComparadorCondicional<String>() {

			@Override
			public int compare(String o1, String o2) {
				if (ascendent())
					return o1.compareToIgnoreCase(o2);
				return o2.compareToIgnoreCase(o1);
			}
		};
		COMPARADOR_INTEGER = new ComparadorCondicional<Integer>() {

			@Override
			public int compare(Integer o1, Integer o2) {
				int comp = o2.intValue() - o1.intValue();
				if (this.ascendent()) {
					if (comp > 0)
						return 1;
					return -1;
				} else {
					if (comp < 0)
						return 1;
					return -1;
				}
			}
			
		};
		COMPARADOR_DOUBLE = new ComparadorCondicional<Double>() {

			@Override
			public int compare(Double o1, Double o2) {
				double comp = o2.doubleValue() - o1.doubleValue();
				if (this.ascendent()) {
					if (comp > 0)
						return 1;
					return -1;
				} else {
					if (comp < 0)
						return 1;
					return -1;
				}
			}
			
		};
	}

	public static ComparadorCondicional getComparadorDificultat() {
		return COMPARADOR_DIFICULTAT;
	}
	
	public static ComparadorCondicional getComparadorInteger() {
		return COMPARADOR_INTEGER;
	}

	public static ComparadorCondicional getComparadorDouble() {
		return COMPARADOR_DOUBLE;
	}

	public static ComparadorCondicional getComparadorString() {
		return COMPARADOR_STRING;
	}

	public static ComparadorCondicional getComparadorBoolean() {
		return COMPARADOR_BOOLEAN;
	}

	public static ComparadorCondicional getComparadorRol() {
		return COMPARADOR_ROL;
	}
	
	/**
	 * Recull totes les dades del contenidor i les deposita en un vector.
	 * @return el vector de dades.
	 */
	public abstract Vector<Object> toVectorType();

}
