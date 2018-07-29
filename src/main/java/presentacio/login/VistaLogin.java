package presentacio.login;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

import presentacio.utils.IntegerDocumentFilter;

public class VistaLogin {
	
	//Controlador
	private ControladorVistaLogin ctrlPresentacio;
	
	//Components
	private JFrame frameVista;
	private JLabel lblUsuariID;
	private JTextField textUsuariID;
	private JLabel lblUsuariNOM;
	private JTextField textUsuariNOM;
	private JPanel panelCentral;
	private JLabel txtInfo1;
	private JLabel txtInfo2;
	private JPanel panelInferior;
	private JButton btnCrear;
	private JButton btnEscull;
	
	
	//CONSTRUCTORA I VISUALITZACIO
	
	public VistaLogin(ControladorVistaLogin ctrlPresVistaLogin) {
		ctrlPresentacio = ctrlPresVistaLogin;
		inicialitzarComponents();
	}
		
	public void visible() {
		frameVista.pack();
		frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frameVista.setVisible(true);
	}
	
	public void amagaVista() {
		frameVista.setVisible(false);
	}
	
	public void mostraMessageDialog(String missatge, String titol, int tipusError) {
		JOptionPane.showMessageDialog(frameVista, missatge, titol, tipusError);
	}
	
	//METODES INTERFACES LISTENER
	
	public void actionPerformed_buttonCrear (ActionEvent event) {
		if (textUsuariID.getText().equals("") && textUsuariNOM.getText().equals("")) {
			mostraMessageDialog("No has introduit cap valor ni a ID ni a Nom!", "Error!", JOptionPane.ERROR_MESSAGE);
		} else if (textUsuariID.getText().equals("")) {
			mostraMessageDialog("No has introduit cap valor a ID!", "Error!", JOptionPane.ERROR_MESSAGE);
		} else if (textUsuariNOM.getText().equals("")) {
			mostraMessageDialog("No has introduit cap valor a Nom!", "Error!", JOptionPane.ERROR_MESSAGE);
		} else {
			int textID = Integer.parseInt(textUsuariID.getText());
			String textNOM = textUsuariNOM.getText();
			ctrlPresentacio.creaJugador(textID, textNOM);
		}
	}
	
	public void actionPerformed_buttonEscolleix(ActionEvent event) {
		if (textUsuariID.getText().equals("")) {
			mostraMessageDialog("No has introduit cap valor a ID!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		try {
			int textID = Integer.parseInt(textUsuariID.getText());
			ctrlPresentacio.escollirJugador(textID);
		} catch (NumberFormatException e) {
			mostraMessageDialog("Número massa llarg!", "Error!", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	
	
	//METODES PRIVATS D'INICIALITZACI�
	
	private void inicialitzarComponents() {
		//FRAME PRINCIPAL
		frameVista = new JFrame();
		//frameVista.setBounds(100, 100, 380, 210);
		frameVista.setMinimumSize(new Dimension(380, 210));
		frameVista.setPreferredSize(frameVista.getMinimumSize());
		frameVista.setResizable(false);
		frameVista.setLocationRelativeTo(null);
		frameVista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frameVista.setLayout(new BorderLayout());
		panelCentral = new JPanel();
		panelInferior = new JPanel();
		
		//PANEL INFERIOR; BUTTONS
		frameVista.add(panelInferior, BorderLayout.SOUTH);
		btnEscull = new JButton("Escollir");
		//ASSIGNACIO DE LISTENERS
		btnEscull.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String text = ((JButton) event.getSource()).getText();
		        actionPerformed_buttonEscolleix(event);
			}
		});
		btnEscull.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelInferior.add(btnEscull);
		Component separadorBotons = Box.createRigidArea(new Dimension(40, 20));
		panelInferior.add(separadorBotons);
		btnCrear = new JButton("Crear");
		//ASSIGNACIO DE LISTENERS
		btnCrear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				String text = ((JButton) event.getSource()).getText();
		        actionPerformed_buttonCrear(event);
			}
		});
		btnCrear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelInferior.add(btnCrear);
		
		//PANEL CENTRAL; LABEL I TEXT ID JUGADOR
		frameVista.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(null);
		lblUsuariID = new JLabel("ID Jugador");
		lblUsuariID.setBounds(35, 14, 68, 17);
		lblUsuariID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelCentral.add(lblUsuariID);
		textUsuariID = new JTextField();
		textUsuariID.setBounds(124, 11, 150, 23);
		textUsuariID.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelCentral.add(textUsuariID);
		textUsuariID.setColumns(12);
		
		//PANEL CENTRAL; LABEL I TEXT NOM JUGADOR
		lblUsuariNOM = new JLabel("Nom Jugador");
		lblUsuariNOM.setBounds(20, 42, 83, 17);
		lblUsuariNOM.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelCentral.add(lblUsuariNOM);
		textUsuariNOM = new JTextField();
		textUsuariNOM.setBounds(124, 39, 150, 23);
		textUsuariNOM.setFont(new Font("Tahoma", Font.PLAIN, 14));
		panelCentral.add(textUsuariNOM);
		textUsuariNOM.setColumns(12);
		
		//PANEL CENTRAL; LABELS INFO
		txtInfo1 = new JLabel("Introdueix un ID existent de jugador per seleccionar-lo");
		txtInfo1.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtInfo1.setBounds(10, 80, 338, 14);
		panelCentral.add(txtInfo1);
		txtInfo2 = new JLabel("o introdueix el nom per crear-ne un de nou ");
		txtInfo2.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtInfo2.setBounds(35, 105, 278, 14);
		panelCentral.add(txtInfo2);
		
		
		
		
		
		IntegerDocumentFilter filter = new IntegerDocumentFilter();
		
		((PlainDocument)textUsuariID.getDocument()).setDocumentFilter(filter);
	}
}
