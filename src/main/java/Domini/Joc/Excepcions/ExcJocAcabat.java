package Domini.Joc.Excepcions;

public class ExcJocAcabat extends RuntimeException {
	public ExcJocAcabat() {
		super();
	}
	public ExcJocAcabat(String msg) {
		super(msg);
	}
}
