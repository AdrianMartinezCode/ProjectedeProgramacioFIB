package Domini.Aplicacio.Utils;

/**
 * Una classe que serveix per poder tindre parells de dos tipus diferents,
 * 	el propi java no té una classe com aquesta.
 * @param <A> el tipus primer.
 * @param <B> el tipus segon.
 */
public class Pair<A, B> {
	private A first;
	private B second;
	
	public Pair(A first, B second) {
		this.first = first;
		this.second = second;
	}
	
	/**
	 * @return el primer registre A.
	 */
	public A getFirst() {
		return first;
	}

	public void setFirst(A first) {
		this.first = first;
	}

	/**
	 * @return el segon registre B.
	 */
	public B getSecond() {
		return second;
	}

	public void setSecond(B second) {
		this.second = second;
	}
}
