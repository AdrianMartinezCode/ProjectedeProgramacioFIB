package Domini.Aplicacio.Excepcions;

public class ExcErrorPersistencia extends RuntimeException{
	public ExcErrorPersistencia() {
		super();
	}
	public ExcErrorPersistencia(String msg) {
		super(msg);
	}
}
