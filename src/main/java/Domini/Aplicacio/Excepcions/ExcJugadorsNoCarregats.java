package Domini.Aplicacio.Excepcions;

public class ExcJugadorsNoCarregats extends RuntimeException{
	public ExcJugadorsNoCarregats() {
		super();
	}
	public ExcJugadorsNoCarregats(String msg) {
		super(msg);
	}
}
