package presentacio.tauler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Domini.Aplicacio.Utils.ColorGenerador;
import Domini.Controladors.ControladorMantenimentJoc;
import presentacio.DadesPartidaObtenible;
import presentacio.PanelInformacio;
import presentacio.VistaAplicacio;
import presentacio.tauler.VistaTaulerMaster.ClickListenerSeleccioColors;
import presentacio.tauler.VistaTaulerMaster.ClickListenerSeleccioPista;
import presentacio.tauler.controlador.AbstractObservableTauler;
import presentacio.tauler.liniadecorador.PanelContenidor;
import presentacio.tauler.liniadecorador.PanelGeneralLinia;
import presentacio.tauler.liniadecorador.PanelLinia;

public abstract class VistaTauler extends AbstractObservableTauler implements VistaAplicacio {
	
	// Per la descripció de cada atribut de la partida agafarem el getInfoPartida
	// 	que ens donarà pair<Vector<String>, Vector<String>> i serà el que mostrarem
	//	al panell.
	
	protected int numTorns; // és el nombre de línies màxim
	protected int numColors;
	protected int numPosicions; // nombre de fitxes a una línia
	private DadesPartidaObtenible dadesPart;
	
	protected static final Dimension SIZE_FITXA = new Dimension(75, 75);
	protected static final Dimension PANEL_ESQUERRA_SIZE = new Dimension(SIZE_FITXA.width, 530);
	protected static final Dimension BOTO_ESQUERRA_SIZE = new Dimension(SIZE_FITXA.width, 60);
	protected static final Dimension PANEL_DRETA_SIZE = new Dimension(200, 530);
	protected static final Dimension START_FRAME_SIZE = new Dimension((int) (PANEL_ESQUERRA_SIZE.getWidth() + PANEL_DRETA_SIZE.getWidth() + 400), 560);
	
	public VistaTauler(int numTorns, int numColors, int numPosicions, DadesPartidaObtenible dadesPart) {
		this.numColors = numColors;
		this.numTorns = numTorns;
		this.numPosicions = numPosicions;
		this.dadesPart = dadesPart;
	}

	protected JFrame frameVista;
	protected JPanel panelEsquerra;
	protected JPanel panelDreta;
	protected JPanel panelCentral;
	/**
	 * Els panells dels colors que utilitzarem per escollir un color.
	 */
	protected Vector<PanelDibuixant> colorsSeleccio;
	protected JButton buttonSeguent;
	
	protected JScrollPane scPanelEsquerra;
	protected JPanel panelBotoEsquerra;
	protected JScrollPane scPanelDreta;
	protected JScrollPane scPanelCentral;
	protected PanelInformacio panelInfo;
	/**
	 * El decorador del panel central, les línies que es veuen al tauler.
	 */
	protected PanelGeneralLinia panelCentralCentre;
	
	protected JPanel panelColorsMaster; // S'ha de enfosquir quan s'és breaker
	/**
	 * Vector de panells que pertanyen a la combinació del master.
	 */
	protected Vector<PanelDibuixantColor> panelsMaster;
	
	//Menus
	protected JMenuBar menuBar;
	protected JMenu menu;
	protected JMenuItem itemMenuReiniciar;
	protected JMenuItem itemMenuGuardar;
	
	
	@Override
	public void inicialitzarComponents() {
		inicialitzarFramePrincipal();
		inicialitzarPanelEsquerre();
		inicialitzarPanelCentral();
		inicialitzarPanelDreta();
		
		inicialitzaMenus();
		configuraFrame();
		afegirListeners();
	}
	
	public Component getFrame() {
		return frameVista;
	}
	@Override
	public void afegirListeners() {
		buttonSeguent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaTauler.super.notificaBotoSeguent();
			}
		});
		itemMenuGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaTauler.super.notificaBotoGuardar();
			}
		});
		itemMenuReiniciar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VistaTauler.super.notificaBotoReiniciar();
			}
		});
	}
	@Override
	public void mostraVista() {
		frameVista.pack();
		frameVista.setSize(START_FRAME_SIZE);
		frameVista.setVisible(true);
	}
	
	private void configuraFrame() {
		frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	private void inicialitzarFramePrincipal() {
		frameVista = new JFrame();
		frameVista.setLayout(new BorderLayout());
		panelEsquerra = new JPanel();
		panelDreta = new JPanel();
		panelCentral = new JPanel();
		
		frameVista.add(panelEsquerra, BorderLayout.WEST);
		
		frameVista.add(panelCentral, BorderLayout.CENTER);
		
		frameVista.add(panelDreta, BorderLayout.EAST);
		
		frameVista.setMinimumSize(new Dimension(PANEL_DRETA_SIZE.width + PANEL_ESQUERRA_SIZE.width, PANEL_DRETA_SIZE.height));
	}
	
	private void inicialitzarPanelEsquerre() {
		panelEsquerra.setLayout(new BorderLayout());
		
		JPanel panelColors = new JPanel();
		JScrollPane scPane = new JScrollPane(panelColors);
		scPanelEsquerra = scPane;
		scPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelColors.setLayout(new BoxLayout(panelColors, BoxLayout.Y_AXIS));
		panelColors.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		scPane.setPreferredSize(PANEL_ESQUERRA_SIZE);
		panelEsquerra.setPreferredSize(new Dimension(PANEL_ESQUERRA_SIZE.width + 20, PANEL_ESQUERRA_SIZE.height));

		panelEsquerra.add(scPane, BorderLayout.CENTER);
		colorsSeleccio = new Vector<PanelDibuixant>();
		for (int i = 0; i < numColors; i++) {
			PanelDibuixantColor pane = new PanelDibuixantColor();
			pane.setColor(ColorGenerador.getColor(i), i );
			pane.setPreferredSize(new Dimension(SIZE_FITXA));
			pane.setMaximumSize(SIZE_FITXA);
			panelColors.add(pane);
			colorsSeleccio.add(pane);
		}
		JPanel panel = new JPanel();
		panelBotoEsquerra = panel;
		panelEsquerra.add(panel, BorderLayout.PAGE_END);

		buttonSeguent = new JButton("Següent");

		panelBotoEsquerra.add(buttonSeguent);
	}
	
	private void inicialitzarPanelCentral() {
		panelCentral.setLayout(new BorderLayout());
		panelCentralCentre = new PanelContenidor();
		
		JScrollPane scPane = new JScrollPane(panelCentralCentre);
		scPanelCentral = scPane;
		scPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelCentral.add(scPane, BorderLayout.CENTER);
		
		panelColorsMaster = new JPanel();
		JScrollPane scPaneMaster = new JScrollPane(panelColorsMaster);
		scPaneMaster.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scPaneMaster.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		panelCentral.add(scPaneMaster, BorderLayout.PAGE_END);
		panelsMaster = new Vector<PanelDibuixantColor>();
		for (int i = 0; i < numPosicions; i++) {
			PanelDibuixantColor pane = new PanelDibuixantColor();
			pane.setPreferredSize(SIZE_FITXA);
			panelColorsMaster.add(pane);
			panelsMaster.add(pane);
		}
	}
	
	/**
	 * Obté la pista de la última línia
	 * @return retorna un vector amb el id dels colors de les pistes,
	 * 	null si encara no hi han línies.
	 * @see PanelGeneralLinia#getUltimaPista()
	 */
	public Vector<Integer> getUltimaPista() {
		return panelCentralCentre.getUltimaPista();
	}
	/**
	 * Obté la combinació de la última línia.
	 * @return retorna un vector amb l'última combinació de la línia.
	 * @see PanelGeneralLinia#getUltimaCombinacio()
	 */
	public Vector<Integer> getUltimaCombinacio() {
		return panelCentralCentre.getUltimaCombinacio();
	}
	
	/**
	 * Afegeix un nou panell de {@link PanelLinia} a {@link PanelGeneralLinia}.
	 * O sigui, que s'afegeix una nova línia al tauler.
	 */
	public void seguentLinia() {
		PanelLinia pl = new PanelLinia(panelCentralCentre, numPosicions, SIZE_FITXA);
		panelCentralCentre = pl;
		pl.construeixPanels();
	}
	/**
	 * Afegeix els colors a la línia actual i també afegeix les pistes a aquesta línia.
	 * @param colors vector de integers que representen el color.
	 * @param pistes vector de integers que representen el color de la pista.
	 */
	public void ompleLiniaCompleta(Vector<Integer> colors, Vector<Integer> pistes) {
		panelCentralCentre.setColorsFitxes(colors);
		panelCentralCentre.setColorsPistes(pistes);
	}
	/**
	 * Afegeix els colors a la combinació de la línia actual.
	 * @param colors vector de integers que representen el color.
	 */
	public void setUltimaCombinacio(Vector<Integer> colors) {
		panelCentralCentre.setColorsFitxes(colors);
	}
	
	/**
	 * Afegeix els colors a la combinació master. Si el nombre de colors del vector
	 * 	és inferior al nombre de panells que hi han al tauler de la combinació de
	 * 	master llavors no es fa res.
	 * @param colors un vector de integers on cada integer correspon a un color.
	 */
	public void setCombinacioMaster(Vector<Integer> colors) {
		if (colors.size() != panelsMaster.size())
			return;
		Iterator<Integer> it = colors.iterator();
		for (PanelDibuixantColor pd : panelsMaster) {
			int idColor = it.next();
			pd.setColor(ColorGenerador.getColor(idColor), idColor);
		}
	}
	
	@Override
	public void mostraMessageDialog(String missatge, String titol, int tipusError) {
		JOptionPane.showMessageDialog(frameVista, missatge, titol, tipusError);
	}
	
	private void inicialitzarPanelDreta() {
		panelDreta.setLayout(new GridLayout(1, 1));
		panelInfo = new PanelInformacio(dadesPart.getDescripcioDades(), dadesPart.getDades(), new JPanel());
		panelInfo.initPanel();
		panelDreta.add(panelInfo);
		panelInfo.setPreferredSize(PANEL_DRETA_SIZE);
	}
	
	private void inicialitzaMenus() {
		menuBar = new JMenuBar();
		menu = new JMenu("Partida");
		itemMenuGuardar = new JMenuItem("Guardar");
		itemMenuReiniciar = new JMenuItem("Reiniciar");
		
		menuBar.add(menu);
		menu.add(itemMenuReiniciar);
		menu.add(itemMenuGuardar);
		
		frameVista.setJMenuBar(menuBar);
	}
	
	/**
	 * Actualitza les dades del panell de la dreta (només les dades, no les descripcions)
	 * @param v1 el vector de strings.
	 */
	public void setDadesDreta(Vector<String> v1) {
		panelInfo.actualitzaLabelsSecond(v1);
	}
	
	/**
	 * Crea els listeners dels panells de la selecció de colors.
	 * @param ml un escoltador per notificar.
	 */
	public void creaListenersColorsSeleccio(MouseListener ml) {
		for(PanelDibuixant pd : colorsSeleccio) {
			pd.addMouseListener(ml);
		}
	}
	/**
	 * Elimina els listeners que existeixen als panells de la selecció de colors.
	 */
	public void eliminaListenersColorsSeleccio() {
		for(PanelDibuixant pd : colorsSeleccio)
			for (MouseListener al : pd.getListeners(MouseListener.class))
				pd.removeMouseListener(al);
	}
	/**
	 * Obté els identificadors dels colors de la combinació master introduïda.
	 * @return un vector de integers amb els identificadors dels colors.
	 */
	public Vector<Integer> getCombinacioMaster() {
		Vector<Integer> v = new Vector<Integer>();
		for (PanelDibuixantColor pdc : panelsMaster) {
			v.add(pdc.getIdColor());
		}
		return v;
	}
	
	@Override
	public void amagaVista() {
		frameVista.setVisible(false);
	}
}
