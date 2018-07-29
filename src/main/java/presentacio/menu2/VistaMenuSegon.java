package presentacio.menu2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import presentacio.VistaAplicacio;
import presentacio.menu2.controlador.AbstractObservableMenuSegon;
import presentacio.utils.DadesLlistaTableObtenible;
import presentacio.utils.ModelTaulaOrdenable;

/**
 * Vista del segon menú, representa un dels escenaris.
 *
 */
public class VistaMenuSegon extends AbstractObservableMenuSegon implements VistaAplicacio {
	
	private JFrame frame;
	private JTable llistaPartides;
	private JButton buttonTriar;
	private JButton buttonEstandar;
	private JButton buttonPers;
	private JScrollPane scPane;
	
	private ModelTaulaOrdenable modelTaula;
	
	private DadesLlistaTableObtenible dadesPart;
	
	public VistaMenuSegon(DadesLlistaTableObtenible dadesPart) {
		frame = null;
		llistaPartides = null;
		buttonTriar = null;
		buttonEstandar = null;
		buttonPers = null;
		this.dadesPart = dadesPart;
	}

	@Override
	public void inicialitzarComponents() {
		construeixVista();
		//afegirListeners();
	}
	
	private void construeixVista() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		modelTaula = new ModelTaulaOrdenable(
				dadesPart.dadesNomColumnes(), 
				dadesPart.getTypes(), 
				dadesPart.getDades(), 
				dadesPart.getComparadors());
		
		llistaPartides = new JTable(modelTaula);
		llistaPartides.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//llistaPartides.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		
		scPane = new JScrollPane(llistaPartides);
		frame.add(scPane, BorderLayout.CENTER);
		
		buttonTriar = new JButton("Triar");
		buttonEstandar = new JButton("Estàndar");
		buttonPers = new JButton("Personalitzada");
		JLabel labelNova = new JLabel("Nova partida:");
		
		JPanel panelButtons = new JPanel();
		panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.LINE_AXIS));
		panelButtons.add(buttonTriar);
		panelButtons.add(Box.createHorizontalGlue());
		panelButtons.add(labelNova);
		panelButtons.add(buttonEstandar);
		panelButtons.add(buttonPers);
		
		frame.add(panelButtons, BorderLayout.PAGE_END);
	}

	@Override
	public void mostraVista() {
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public Object getSeleccioList(int columnId) {
		return modelTaula.getValueAt(llistaPartides.getSelectedRow(), columnId);
	}

	@Override
	public void afegirListeners() {
		buttonTriar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaMenuSegon.this.notificaBotoTriar();
			}
		});
		buttonPers.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaMenuSegon.this.notificaBotoPersonalitzada();
			}
		});
		buttonEstandar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaMenuSegon.this.notificaBotoEstandar();
			}
		});
		
		llistaPartides.getTableHeader().addMouseListener(modelTaula.getListener());
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
