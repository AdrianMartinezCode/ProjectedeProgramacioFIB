package Domini.Joc.Excepcions;

public class ExcPistaJaIntroduida extends RuntimeException{
	public ExcPistaJaIntroduida() {
	}
	public ExcPistaJaIntroduida(String msg) {
		super(msg);
	}
}
