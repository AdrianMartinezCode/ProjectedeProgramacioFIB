
package presentacio;

/**
 * Classe guia, no compleix una funció en especial, simplement s'utilitza
 * per guiar a l'hora de crear una vista.
 * @author adrian
 *
 */
public interface VistaAplicacio {
	/**
	 * Inicialitza els components i els construeïx.
	 */
	public void inicialitzarComponents();
	/**
	 * Mostra la vista.
	 */
	public void mostraVista();
	/**
	 * Amaga la vista.
	 */
	public void amagaVista();
	/**
	 * Afegeix els listeners.
	 */
	public void afegirListeners();
	/**
	 * Mostra un missatge en un dialog.
	 * @param missatge la string del missatge a mostrar.
	 * @param titol el títol del dialog.
	 * @param tipusError un int que indica la ícona del dialog.
	 */
	public void mostraMessageDialog(String missatge, String titol, int tipusError);
}
