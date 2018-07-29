package presentacio;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;

public class PanelInformacio extends JScrollPane {
	
	private Vector<String> descripcioDades;
	private Vector<String> dades;
	private Vector<JLabel> labelsDesc;
	private Vector<JLabel> labelsDades;
	private JPanel panel;
	
	/**
	 * Inicialitza el panel.
	 * @param descripcioDades un vector de strings amb la descripció de cada dada a mostrar.
	 * @param dades un vector de strings, amb cada dada a mostrar.
	 * @param panel el panel on volem ubicar les dades.
	 */
	public PanelInformacio(Vector<String> descripcioDades, Vector<String> dades, JPanel panel) {
		super(panel);
		this.panel = panel;
		this.descripcioDades = descripcioDades;
		this.dades = dades;
		labelsDades = new Vector<JLabel>();
		labelsDesc = new Vector<JLabel>();
	}
	
	/**
	 * Construeix el panel.
	 */
	public void initPanel() {
		super.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		super.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		GridBagLayout sl = new GridBagLayout();
		panel.setLayout(sl);
		//setLayout(new GridLayout(1, 1));
		
		Iterator<String> it = descripcioDades.iterator();
		int i = 0;
		for(String s : dades) {
			JLabel l = new JLabel(it.next() + ":");
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = i;
			gbc.anchor = GridBagConstraints.EAST;
			
			panel.add(l, gbc);
			labelsDesc.add(l);
			
			gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = i;
			JPanel jp = new JPanel();
			jp.setPreferredSize(new Dimension(10, l.getHeight()));
			panel.add(jp, gbc);
			
			JLabel lf = new JLabel(s);
			gbc = new GridBagConstraints();
			gbc.gridx = 2;
			gbc.gridy = i;
			gbc.anchor = GridBagConstraints.WEST;
			panel.add(lf, gbc);
			labelsDades.add(lf);
			
			i++;
		}
		
	}
	
	/**
	 * Actualitza els components referents a les dades.
	 * @param v el vector de strings, on cada string és una dada a mostrar.
	 */
	public void actualitzaLabelsSecond(Vector<String> v) {
		if (v.size() != labelsDades.size())
			return;
		int i = 0;
		for (String s : v) {
			labelsDades.get(i).setText(s);
			++i;
		}
	}
	
	/**
	 * Actualitza els components referents a les descripcions de cada dada.
	 * @param v el vector de strings, in cada string és la descripció de la dada a mostrar.
	 */
	public void actualitzaLabelsDescripcio(Vector<String> v) {
		if (v.size() != labelsDesc.size())
			return;
		int i = 0;
		for (String s : v) {
			labelsDesc.get(i).setText(s + ":");
			++i;
		}
	}
	
}
