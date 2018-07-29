package Domini.Aplicacio.Drivers;

import java.util.Vector;

import org.junit.Test;
import static org.junit.Assert.*;

import Domini.Aplicacio.Partida;
import Domini.Aplicacio.PartidaEstandar;
import Domini.Aplicacio.PartidaNoEstandar;
import Domini.Joc.Dificultat;
import Domini.Joc.JocActual;
import Domini.Joc.Rol;

public class TestPartida {
	
	private Partida getPartidaPreparada() {
		Partida pe = new PartidaEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(dades);
		return pe;
	}
	
	@Test
	public void getNomClasseFromDades() {
		Partida pe = new PartidaEstandar();
		String dades = pe.novaEntitatParametres(0, new Vector<String>());
		String classe = pe.getNomClasseFromDades(dades);
		assertEquals(classe, PartidaEstandar.class.getName());
		
		Partida pne = new PartidaNoEstandar();
		dades = pne.novaEntitatParametres(0, new Vector<String>());
		classe = pne.getNomClasseFromDades(dades);
		assertEquals(classe, PartidaNoEstandar.class.getName());
	}
	
	@Test
	public void getCodiPartida() {
		Partida pe = getPartidaPreparada();
		int codi = pe.getCodiPartida();
		assertEquals(8, codi);
		
	}
	@Test
	public void seguentJoc() {
		Partida pe = getPartidaPreparada();
		int codi = pe.seguentJoc();
		assertEquals(0, codi);
	}
	@Test
	public void getJoc() {
		Partida pe = getPartidaPreparada();
		
		JocActual ja = pe.getJoc();
		assertEquals(null, ja);
		
		int codi = pe.seguentJoc();
		assertEquals(0, codi);
		ja = pe.getJoc();
		assertNotEquals(null, ja);
		
		int codiJoc = ja.getCodiJoc();
		int tornActual = pe.getTornAJoc();
		assertEquals(tornActual, codiJoc);
	}
	@Test
	public void setRolActual() {
		Partida pe = getPartidaPreparada();
		pe.setRolActual(Rol.CodeMaster);
		assertEquals(Rol.CodeMaster, pe.getRolActual());
		pe.setRolActual(Rol.CodeBreaker);
		assertEquals(Rol.CodeBreaker, pe.getRolActual());
	}
	public void getRolActual() {
		Partida pe = getPartidaPreparada();
		pe.setRolActual(Rol.CodeMaster);
		assertEquals(Rol.CodeMaster, pe.getRolActual());
		pe.setRolActual(Rol.CodeBreaker);
		assertEquals(Rol.CodeBreaker, pe.getRolActual());
	}
	public void ponderaPuntuacio() {
		
	}
	@Test
	public void convertirToString() {
		Partida pe = new PartidaEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(dades);
		
		assertEquals(dades, pe.convertirToString());
	}
	
	@Test
	public void obtenerFromString() {
		Partida pe = new PartidaEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(new String(dades));
		assertEquals(dades, pe.convertirToString());
	}
	
	@Test
	public void reiniciaPartida() {
		Partida pe = getPartidaPreparada();
		int exitCode = pe.seguentJoc();
		assertEquals(0, exitCode);
		boolean b = pe.reiniciaPartida();
		assertEquals(true, b);
		JocActual j = pe.getJoc();
		assertEquals(null, j);
		pe.seguentJoc();
		j = pe.getJoc();
		assertEquals(0, j.getCodiJoc());
		assertEquals(0, pe.getTornAJoc());
	}
	
	@Test
	public void setTornAJoc() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setTornAJoc(5);
		assertEquals(true, b);
		assertEquals(5, pe.getTornAJoc());
	}
	@Test
	public void setCodiPartida() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setCodiPartida(2);
		assertEquals(true, b);
		assertEquals(2, pe.getCodiPartida());
	}
	@Test
	public void setDificultat() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setDificultat(Dificultat.Alta);
		assertEquals(true, b);
		assertEquals(Dificultat.Alta, pe.getDificultat());
	}
	@Test
	public void setAjuts() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setAjuts(false);
		assertEquals(true, b);
		assertEquals(false, pe.isAjuts());
	}
	@Test
	public void setTotalTorns() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setTotalTorns(4);
		assertEquals(true, b);
		assertEquals(4, pe.getTotalTorns());
	}
	@Test
	public void setTotalColors() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setTotalColors(4);
		assertEquals(true, b);
		assertEquals(4, pe.getTotalColors());
	}
	@Test
	public void setNombreJocs() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setNombreJocs(4);
		assertEquals(true, b);
		assertEquals(4, pe.getNombreJocs());
	}
	@Test
	public void setPuntsActuals() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setPuntsActuals(4);
		assertEquals(true, b);
		assertEquals(4, pe.getPuntsActuals());
	}
	@Test
	public void setTotalPosicions() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.setTotalPosicions(4);
		assertEquals(true, b);
		assertEquals(4, pe.getTotalPosicions());
	}
	@Test
	public void configuraAjuts() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.configuraAjuts(true);
		assertEquals(true, b);
		assertEquals(true, pe.isAjuts());

	}
	@Test
	public void configuraDificultat() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.configuraDificultat(Dificultat.Alta);
		assertEquals(true, b);
		assertEquals(Dificultat.Alta, pe.getDificultat());
	}
	@Test
	public void configuraColors() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.configuraColors(5);
		assertEquals(false, b);
		
		pe = new PartidaNoEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(dades);
		
		b = pe.configuraColors(5);
		assertEquals(true, b);
		assertEquals(5, pe.getTotalColors());
		
	}
	@Test
	public void configuraPosicions() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.configuraPosicions(5);
		assertEquals(false, b);
		
		pe = new PartidaNoEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(dades);
		
		b = pe.configuraPosicions(5);
		assertEquals(true, b);
		assertEquals(5, pe.getTotalPosicions());
	}
	@Test
	public void configuraTorns() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.configuraTorns(5);
		assertEquals(false, b);
		
		pe = new PartidaNoEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(dades);
		
		b = pe.configuraTorns(5);
		assertEquals(true, b);
		assertEquals(5, pe.getTotalTorns());
	}
	@Test
	public void configuraNombreJocs() {
		Partida pe = getPartidaPreparada();
		boolean b = pe.configuraNombreJocs(6);
		assertEquals(false, b);
		
		pe = new PartidaNoEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(dades);
		
		b = pe.configuraNombreJocs(6);
		assertEquals(true, b);
		assertEquals(6, pe.getNombreJocs());
	}
	
	@Test
	public void miClone() {
		Partida pe = getPartidaPreparada();
		Partida pp = (Partida) pe.miClone();
		assertNotEquals(pe.hashCode(), pp.hashCode());
		assertEquals(pe.convertirToString(), pp.convertirToString());
	}
	
	@Test
	public void novaEntitatParametres() {
		Partida pe = new PartidaEstandar();
		String dades = pe.novaEntitatParametres(8, new Vector<String>());
		pe.obtenerFromString(dades);
		
		assertEquals(dades, pe.convertirToString());
	}
}
