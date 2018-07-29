package Domini.Aplicacio.Excepcions;

public class ExcErrorDomini extends RuntimeException {
	public ExcErrorDomini() {
		super();
	}
	public ExcErrorDomini(String msg) {
		super(msg);
	}
}
