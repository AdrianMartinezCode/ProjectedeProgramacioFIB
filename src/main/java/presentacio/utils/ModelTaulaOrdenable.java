package presentacio.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.JTableHeader;

import Domini.Aplicacio.Utils.ComparadorCondicional;
import Domini.Aplicacio.Utils.Pair;

/**
 * Classe creada per facilitar la creació d'una taula de registres
 * 	que sigui ordenable i seleccionable.
 *
 */
public class ModelTaulaOrdenable extends AbstractTableModel{
	
	/**
	 * Els noms de cada columna que es mostrarà a la capçalera de la taula.
	 */
	private Vector<String> colnames;
	/**
	 * Els tipus de cada columna.
	 */
	private Vector<Class<?>> tipusCols;
	/**
	 * Les dades que volem mostrar.
	 */
	private Vector<Vector<Object>> dades;
	/**
	 * Els comparadors que farem servir a l'hora d'ordenar
	 * 	la taula.
	 */
	private Vector<ComparadorCondicional> comparators;
	
	/**
	 * Tradueix l'índex de la taula on es selecciona una fila per
	 * 	l'índex proporcionat al crear la classe. Ens estalvia
	 * 	tindre que ordenar totes les dades cada vegada que
	 * 	es fa clic a la capçalera d'una columna.
	 */
	private Vector<Map.Entry<Object, Integer>> ordenada;
	
	public ModelTaulaOrdenable(Vector<String> colnames, Vector<Class<?>> tipusCols, Vector<Vector<Object>> dades, Vector<ComparadorCondicional> comparators) {
		this.colnames = colnames;
		this.tipusCols = tipusCols;
		this.dades = dades;
		this.comparators = comparators;
		
		orderByValues(0);
	}
	
	/**
	 * Creació de la llista ordenada de índexos per a poder
	 * 	saber quina fila estem seleccionant realment i poder accedir
	 * 	a la dada.
	 * @param column índex de la columna a ordenar.
	 */
	private void orderByValues(int column) {
		TreeMap<Object, Integer> ordenada = new TreeMap<>(comparators.get(column));
		for(int i = 0; i < dades.size(); i++) {
			ordenada.put(dades.get(i).get(column), i);
		}
		this.ordenada = new Vector<>(ordenada.entrySet());
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return tipusCols.get(columnIndex);
	}
	
	@Override
	public String getColumnName(int column) {
		return colnames.get(column);
	}

	@Override
	public int getRowCount() {
		return dades.size() < 100 ? dades.size() : 100;
	}

	@Override
	public int getColumnCount() {
		return colnames.size();
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (rowIndex < 0 || rowIndex >= ordenada.size())
			return null;
		int realRow = ordenada.get(rowIndex).getValue();
		if (realRow < 0 || realRow >= dades.size())
			return null;
		return dades.get(realRow).get(columnIndex);
	}
	
	/**
	 * Crea un listener per a poder obtindre l'esdeveniment que
	 * 	es dispara quan seleccionem una capçalera d'alguna columna.
	 * @return el listener.
	 */
	public MouseListener getListener() {
		MouseListener ml = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JTableHeader h = ((JTableHeader)e.getSource());
				int colId = h.columnAtPoint(e.getPoint());
				orderByValues(colId);
				comparators.get(colId).changeState();
				super.mouseReleased(e);
			}
		};
		return ml;
	}

}
