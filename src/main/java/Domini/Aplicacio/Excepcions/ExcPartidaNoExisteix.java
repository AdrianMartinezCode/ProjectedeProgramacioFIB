package Domini.Aplicacio.Excepcions;

public class ExcPartidaNoExisteix extends RuntimeException {
	public ExcPartidaNoExisteix() {
		super();
	}
	public ExcPartidaNoExisteix(String msg) {
		super(msg);
	}
}
