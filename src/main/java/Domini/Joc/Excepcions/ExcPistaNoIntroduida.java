package Domini.Joc.Excepcions;

public class ExcPistaNoIntroduida extends RuntimeException {
	public ExcPistaNoIntroduida() {
	}
	public ExcPistaNoIntroduida(String msg) {
		super(msg);
	}
}
