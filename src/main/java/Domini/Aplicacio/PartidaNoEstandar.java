package Domini.Aplicacio;

public class PartidaNoEstandar extends Partida {

	public PartidaNoEstandar() {
		super();
	}
	
	@Override
	public Partida miClone() {
		Partida p = new PartidaNoEstandar();
		p.obtenerFromString(this.convertirToString());
		return p;
	}


	@Override
	public int ponderaPuntuacio(int tornPartida) {
		return 0;
	}

	@Override
	public String getNomClasse() {
		return this.getClass().getName();
	}
	
}
