package Domini.Aplicacio;

import Domini.Joc.Dificultat;

/**
 * Serveix per no tindre que anar passant la partida cap a les classes que conformen
 * 	el tauler o no tindre que replicar atributs a l'hora de la creació de classes.
 */
public interface IDadesPartida {
	
	public int getNPosicions();
	public int getNColors();
	public int getTotalTorns();
	public boolean getAjuts();
	public Dificultat getDificultat();
	public int getCodiJoc();
}
