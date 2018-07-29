package Domini.Aplicacio.Excepcions;

public class ExcPartidesNoCarregades extends RuntimeException {
	public ExcPartidesNoCarregades() {
		super();
	}
	public ExcPartidesNoCarregades(String msg) {
		super(msg);
	}
}
