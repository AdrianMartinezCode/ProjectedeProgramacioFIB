package Domini.Aplicacio.Excepcions;

public class ExcPartidaNoSeleccionada extends RuntimeException{
	public ExcPartidaNoSeleccionada() {
		super();
	}
	public ExcPartidaNoSeleccionada(String msg) {
		super(msg);
	}
}
