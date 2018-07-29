package Domini.Aplicacio;

import Domini.Aplicacio.Utils.EntradaGenerica;

public class PartidaEstandar extends Partida {
	
	private static final int TOTAL_TORNS = 20;
	private static final int TOTAL_COLORS = 6;
	private static final int NOMBRE_JOCS = 8;
	private static final int TOTAL_POSICIONS = 4;

	public PartidaEstandar() {
		super();
	}
	
	@Override
	public int getTotalColorsDefecte() {
		return TOTAL_COLORS;
	}
	
	@Override
	public int getNombreJocsDefecte() {
		return NOMBRE_JOCS;
	}
	
	@Override
	public int getTotalPosicionsDefecte() {
		return TOTAL_POSICIONS;
	}
	
	@Override
	public int getTotalTornsDefecte() {
		return TOTAL_TORNS;
	}
	
	@Override
	public boolean configuraColors(int colors) {
		return false;
	}
	
	@Override
	public boolean configuraNombreJocs(int nbJocs) {
		return false;
	}
	
	@Override
	public boolean configuraTorns(int torns) {
		return false;
	}
	
	@Override
	public boolean configuraPosicions(int posicions) {
		return false;
	}
	
	@Override
	public Partida miClone() {
		Partida p = new PartidaEstandar();
		p.obtenerFromString(this.convertirToString());
		return p;
	}
	
	@Override
	public int ponderaPuntuacio(int tornPartida) {
		double p = (double)1/(double)tornPartida;
		return (int)(p*100);
	}

	@Override
	public String getNomClasse() {
		return this.getClass().getName();
	}

	

	

	

	
	
	
}
