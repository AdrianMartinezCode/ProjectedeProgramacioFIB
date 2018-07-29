package presentacio.menu1;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import presentacio.VistaAplicacio;
import presentacio.menu1.controlador.AbstractObservableMenuPrimer;
import presentacio.utils.DadesLlistaTableObtenible;
import presentacio.utils.ModelTaulaOrdenable;

/**
 * Classe que representa la vista de l'escenari del primer menú.
 *
 */
public class VistaMenuPrimer extends AbstractObservableMenuPrimer implements VistaAplicacio {
	
	
	private JFrame frame;
	private JButton buttonJugar;
	private JTable taulaRanking;
	private JScrollPane scPane;
	private ModelTaulaOrdenable modelTable;
	
	private DadesLlistaTableObtenible dadesObt;
	
	
	public VistaMenuPrimer(DadesLlistaTableObtenible dadesObt) {
		frame = null;
		//contentPane = null;
		buttonJugar = null;
		taulaRanking = null;
		scPane = null;
		this.dadesObt = dadesObt;
	}
	
	@Override
	public void inicialitzarComponents() {
		construeixVista();
		afegirListeners();
	}
	
	private void construeixVista() {
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		
		modelTable = new ModelTaulaOrdenable(dadesObt.dadesNomColumnes(),
				dadesObt.getTypes(), dadesObt.getDades(), dadesObt.getComparadors());
		
		taulaRanking = new JTable(modelTable);
		
		taulaRanking.setRowSelectionAllowed(false);
		taulaRanking.setCellSelectionEnabled(false);
		taulaRanking.setRowSelectionAllowed(false);
		scPane = new JScrollPane(taulaRanking);
		frame.add(scPane, BorderLayout.CENTER);
		
		
		buttonJugar = new JButton("Jugar");
		JPanel jp = new JPanel();
		jp.add(buttonJugar);
		frame.add(jp, BorderLayout.NORTH);
		
	}
	@Override
	public void mostraVista() {
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	@Override
	public void afegirListeners() {
		buttonJugar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaMenuPrimer.super.notificaBotoJugar();
			}
		});
		taulaRanking.getTableHeader().addMouseListener(modelTable.getListener());
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
