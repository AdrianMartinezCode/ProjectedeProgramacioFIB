package Domini.Aplicacio.Excepcions;

public class ExcJugadorNoEscollit extends RuntimeException {
	public ExcJugadorNoEscollit() {
		super();
	}
	public ExcJugadorNoEscollit(String msg) {
		super(msg);
	}
}
