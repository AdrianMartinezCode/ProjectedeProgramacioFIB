package Domini.Joc.Excepcions;

public class ExcCombinacioInicialNoIntroduida extends RuntimeException {
	public ExcCombinacioInicialNoIntroduida() {
		super();
	}
	public ExcCombinacioInicialNoIntroduida(String msg) {
		super(msg);
	}
}
