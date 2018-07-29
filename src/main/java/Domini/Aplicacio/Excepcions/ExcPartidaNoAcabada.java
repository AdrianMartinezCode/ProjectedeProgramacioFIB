package Domini.Aplicacio.Excepcions;

public class ExcPartidaNoAcabada extends RuntimeException{
	public ExcPartidaNoAcabada() {
		super();
	}
	public ExcPartidaNoAcabada(String msg) {
		super(msg);
	}
}
