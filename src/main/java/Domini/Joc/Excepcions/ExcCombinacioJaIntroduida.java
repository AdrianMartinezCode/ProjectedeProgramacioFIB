package Domini.Joc.Excepcions;

public class ExcCombinacioJaIntroduida extends RuntimeException {
	public ExcCombinacioJaIntroduida() {
	}
	public ExcCombinacioJaIntroduida(String msg) {
		super(msg);
	}
}
