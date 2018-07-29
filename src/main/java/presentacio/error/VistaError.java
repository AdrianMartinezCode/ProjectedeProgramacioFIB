package presentacio.error;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * Serveix per mostrar ràpidament un missatge d'error on no tinguem un JFrame actiu.
 *
 */
public class VistaError extends JFrame {
	
	
	public VistaError() {}
	
	public void mostraDialogError(String msg, String title) {
		JOptionPane.showMessageDialog(this, msg, title, JOptionPane.ERROR_MESSAGE);
		this.dispose();
	}
}
