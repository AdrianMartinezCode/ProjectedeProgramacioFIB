package presentacio.menu2;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;

import presentacio.utils.IntegerDocumentFilter;

/**
 * Vista de la finestra de configuració d'una partida no estàndar.
 *
 */
public class VistaConfiguracioPartidaPersonalitzada extends VistaConfiguracioPartidaEstandar {
	
	private JTextField fieldColors;
	private JTextField fieldPosicions;
	private JTextField fieldTorns;
	private JTextField fieldJocs;
	private int maxColors;
	private int minColors;
	private int minPosicions;
	private int maxPosicions;
	private int minTorns;
	private int maxTorns;
	private int minNbJocs;
	private int maxNbJocs;
	
	public static final int ID_CONFIGURACIO = 2;
	
	public VistaConfiguracioPartidaPersonalitzada(String nomDificultatAlta, String nomDificultatBaixa,
			int minColors, int maxColors, int minPosicions, int maxPosicions,
			int minTorns, int maxTorns, int minNbJocs, int maxNbJocs) {
		super(nomDificultatAlta, nomDificultatBaixa, ID_CONFIGURACIO);
		fieldColors = null;
		fieldPosicions = null;
		fieldTorns = null;
		fieldJocs = null;
		this.maxColors = maxColors;
		this.minColors = minColors;
		this.minPosicions = minPosicions;
		this.maxPosicions = maxPosicions;
		this.minTorns = minTorns;
		this.maxTorns = maxTorns;
		this.minNbJocs = minNbJocs;
		this.maxNbJocs = maxNbJocs;
	}
	
	@Override
	public void afegirListeners() {
		super.afegirListeners();
	}
	
	@Override
	public void inicialitzarComponents() {
		super.inicialitzarComponents();
		
		JPanel panelContenidor = new JPanel();
		
		JPanel labelsContenidor = new JPanel();
		labelsContenidor.setLayout(new BoxLayout(labelsContenidor, BoxLayout.Y_AXIS));
		JLabel labelColor = new JLabel("Colors:");
		JLabel labelPosicions = new JLabel("Posicions:");
		JLabel labelTorns = new JLabel("Torns:");
		JLabel labelNbJocs = new JLabel("Nb. Jocs:");
		labelsContenidor.add(labelColor);
		labelsContenidor.add(labelPosicions);
		labelsContenidor.add(labelTorns);
		labelsContenidor.add(labelNbJocs);
		
		JPanel fieldsContenidor = new JPanel();
		fieldsContenidor.setLayout(new BoxLayout(fieldsContenidor, BoxLayout.Y_AXIS));
		fieldColors = new JTextField();
		fieldPosicions = new JTextField();
		fieldTorns = new JTextField();
		fieldJocs = new JTextField();
		fieldsContenidor.add(fieldColors);
		fieldsContenidor.add(fieldPosicions);
		fieldsContenidor.add(fieldTorns);
		fieldsContenidor.add(fieldJocs);
		
		JPanel labelsMaximsMinims = new JPanel();
		labelsMaximsMinims.setLayout(new BoxLayout(labelsMaximsMinims, BoxLayout.Y_AXIS));
		JLabel labelColorMM = new JLabel(new String(minColors + " <= valor <= " + maxColors));
		JLabel labelPosicionsMM = new JLabel(new String(minPosicions + " <= valor <= " + maxPosicions));
		JLabel labelTornsMM = new JLabel(new String(minTorns + " <= valor <= " + maxTorns));
		JLabel labelJocsMM = new JLabel(new String(minNbJocs + " <= valor <= " + maxNbJocs));
		labelsMaximsMinims.add(labelColorMM);
		labelsMaximsMinims.add(labelPosicionsMM);
		labelsMaximsMinims.add(labelTornsMM);
		labelsMaximsMinims.add(labelJocsMM);
		
		panelContenidor.setLayout(new BoxLayout(panelContenidor, BoxLayout.X_AXIS));
		panelContenidor.add(labelsContenidor);
		panelContenidor.add(fieldsContenidor);
		panelContenidor.add(labelsMaximsMinims);
		
		IntegerDocumentFilter filter = new IntegerDocumentFilter();
		
		((PlainDocument)fieldColors.getDocument()).setDocumentFilter(filter);
		((PlainDocument)fieldPosicions.getDocument()).setDocumentFilter(filter);
		((PlainDocument)fieldTorns.getDocument()).setDocumentFilter(filter);
		((PlainDocument)fieldJocs.getDocument()).setDocumentFilter(filter);
		
		super.afegeixPanel(panelContenidor);
	};
	
	public int getNumColors() {
		return Integer.valueOf(fieldColors.getText());
	}
	
	public int getNumPosicions() {
		return Integer.valueOf(fieldPosicions.getText());
	}
	
	public int getNumTorns() {
		return Integer.valueOf(fieldTorns.getText());
	}
	
	public int getNbJocs() {
		return Integer.valueOf(fieldJocs.getText());
	}
}
