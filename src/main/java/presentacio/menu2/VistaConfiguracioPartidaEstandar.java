package presentacio.menu2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import presentacio.VistaAplicacio;
import presentacio.menu2.controlador.AbstractObservableConfiguracioPartida;

/**
 * Vista per la configuració de una nova partida estàndar.
 *
 */
public class VistaConfiguracioPartidaEstandar extends AbstractObservableConfiguracioPartida implements VistaAplicacio{
	
	private JFrame frame;
	
	private ButtonGroup radioButtonsDificultat;
	private JRadioButton dificultatAlta;
	private JRadioButton dificultatBaixa;
	
	private ButtonGroup radioButtonsAjuts;
	private JRadioButton ambAjuts;
	private JRadioButton senseAjuts;
	private JButton buttonCancelar;
	private JButton buttonAcceptar;
	
	private JPanel panelCentral;
	
	private String nomDificultatAlta;
	private String nomDificultatBaixa;
	
	public static final int ID_CONFIGURACIO = 1;
	private int idConfiguracio;
	
	public static void main(String[] args) {
		VistaConfiguracioPartidaEstandar vcpe = new VistaConfiguracioPartidaEstandar("Dificil", "Facil");
		vcpe.inicialitzarComponents();
		vcpe.mostraVista();
	}
	
	public VistaConfiguracioPartidaEstandar(String nomDificultatAlta, String nomDificultatBaixa) {
		frame = null;
		radioButtonsDificultat = null;
		dificultatAlta = null;
		dificultatBaixa = null;
		radioButtonsAjuts = null;
		ambAjuts = null;
		senseAjuts = null;
		buttonCancelar = null;
		buttonAcceptar = null;
		this.nomDificultatAlta = nomDificultatAlta;
		this.nomDificultatBaixa = nomDificultatBaixa;
		idConfiguracio = ID_CONFIGURACIO;
	}
	public VistaConfiguracioPartidaEstandar(String nomDificultatAlta, String nomDificultatBaixa, int idConfiguracio) {
		this(nomDificultatAlta, nomDificultatBaixa);
		this.idConfiguracio = idConfiguracio;
	}

	@Override
	public void inicialitzarComponents() {
		creaComponents();
		colocaComponents();
	}
	
	private void creaComponents() {
		frame = new JFrame();
		
		dificultatAlta = new JRadioButton("Difícil");
		dificultatBaixa = new JRadioButton("Fàcil");
		radioButtonsDificultat = new ButtonGroup();
		radioButtonsDificultat.add(dificultatAlta);
		radioButtonsDificultat.add(dificultatBaixa);
		radioButtonsDificultat.setSelected(dificultatAlta.getModel(), true);
		
		ambAjuts = new JRadioButton("Sí");
		senseAjuts = new JRadioButton("No");
		radioButtonsAjuts = new ButtonGroup();
		radioButtonsAjuts.add(ambAjuts);
		radioButtonsAjuts.add(senseAjuts);
		radioButtonsAjuts.setSelected(senseAjuts.getModel(), true);
		
		buttonAcceptar = new JButton("Acceptar");
		buttonCancelar = new JButton("Cancel·lar");
		
		panelCentral = new JPanel();
	}
	
	private void colocaComponents() {
		frame.setLayout(new BorderLayout());
		
		JPanel panelAlt = new JPanel();
		panelAlt.setLayout(new BoxLayout(panelAlt, BoxLayout.Y_AXIS));
		
		JPanel panelAjuts = new JPanel();
		panelAjuts.setLayout(new BoxLayout(panelAjuts, BoxLayout.LINE_AXIS));
		
		JLabel labelAjuts = new JLabel("Ajuts:");
		panelAjuts.add(labelAjuts);
		panelAjuts.add(ambAjuts);
		panelAjuts.add(senseAjuts);
		
		panelAlt.add(panelAjuts);
		
		
		JPanel panelDificultat = new JPanel();
		panelDificultat.setLayout(new BoxLayout(panelDificultat, BoxLayout.LINE_AXIS));
		
		JLabel labelDif = new JLabel("Dificultat:");
		panelDificultat.add(labelDif);
		panelDificultat.add(dificultatAlta);
		panelDificultat.add(dificultatBaixa);
		
		panelAlt.add(panelDificultat);
		
		panelCentral.setLayout(new BoxLayout(panelCentral, BoxLayout.Y_AXIS));
		panelCentral.add(panelAlt);
		
		JPanel panelBaix = new JPanel();
		panelBaix.setLayout(new BoxLayout(panelBaix, BoxLayout.LINE_AXIS));
		panelBaix.add(buttonAcceptar);
		panelBaix.add(Box.createHorizontalGlue());
		panelBaix.add(buttonCancelar);
		
		frame.add(panelCentral, BorderLayout.CENTER);
		frame.add(panelBaix, BorderLayout.PAGE_END);
	}
	
	/**
	 * Afegeix un panel al panel central de la vista.
	 * @param cmp un component a afegir.
	 */
	protected void afegeixPanel(JComponent cmp) {
		panelCentral.add(cmp);
	}

	@Override
	public void mostraVista() {
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public String getDificultat() {
		if (radioButtonsDificultat.isSelected(dificultatAlta.getModel()))
			return nomDificultatAlta;
		return nomDificultatBaixa;
	}
	
	public boolean getAjuts() {
		return radioButtonsAjuts.isSelected(ambAjuts.getModel());
	}
	
	

	@Override
	public void afegirListeners() {
		buttonAcceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaConfiguracioPartidaEstandar.this.notificaBotoAcceptar(idConfiguracio);
			}
		});
		buttonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaConfiguracioPartidaEstandar.this.notificaBotoCancelar(idConfiguracio);
			}
		});
	}

	@Override
	public void amagaVista() {
		frame.setVisible(false);
	}
	
	@Override
	public void mostraMessageDialog(String missatge, String titol, int tipusError) {
		JOptionPane.showMessageDialog(frame, missatge, titol, tipusError);
	}
}
