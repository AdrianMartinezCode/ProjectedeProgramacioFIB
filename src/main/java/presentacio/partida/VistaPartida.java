package presentacio.partida;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import presentacio.DadesPartidaObtenible;
import presentacio.PanelInformacio;
import presentacio.VistaAplicacio;
import presentacio.partida.controlador.AbstractObservableVistaPartida;

/**
 * Vista que representa l'escenari de mostrar les dades de la partida entre joc i joc, inclús
 * 	si la partida està començada es mostra.
 *
 */
public class VistaPartida extends AbstractObservableVistaPartida implements VistaAplicacio {
	
	private JFrame frame;
	private JButton buttonSeguent;
	private JButton buttonGuardar;
	private JButton buttonReiniciar;
	
	private PanelInformacio panelInfo;
	private DadesPartidaObtenible dadesPart;
	
	public VistaPartida(DadesPartidaObtenible dadesPart) {
		frame = null;
		buttonSeguent = null;
		buttonGuardar = null;
		buttonReiniciar = null;
		panelInfo = null;
		this.dadesPart = dadesPart;
	}

	@Override
	public void inicialitzarComponents() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		panelInfo = new PanelInformacio(dadesPart.getDescripcioDades(), dadesPart.getDades(), new JPanel());
		panelInfo.initPanel();
		buttonSeguent = new JButton("Següent Joc");
		buttonGuardar = new JButton("Guardar");
		buttonReiniciar = new JButton("Reiniciar");
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.LINE_AXIS));
		panelButtons.add(buttonSeguent);
		panelButtons.add(Box.createHorizontalGlue());
		panelButtons.add(buttonGuardar);
		panelButtons.add(buttonReiniciar);
		
		frame.add(panelButtons, BorderLayout.PAGE_END);
		frame.add(panelInfo, BorderLayout.CENTER);
	}

	@Override
	public void mostraVista() {
		//panelInfo.actualitzaLabelsDescripcio();
		actualitzaInfoDades();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	/**
	 * Actualitza la informació de les dades mostrades.
	 */
	public void actualitzaInfoDades() {
		panelInfo.actualitzaLabelsSecond(dadesPart.getDades());
	}

	@Override
	public void afegirListeners() {
		buttonSeguent.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaPartida.this.notificaBotoSeguentJoc();
			}
		});
		buttonGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaPartida.this.notificaBotoGuardar();
			}
		});
		buttonReiniciar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaPartida.this.notificaBotoReiniciar();
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
