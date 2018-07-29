package Domini;

public interface IEntitatCarregable {
	/**
	 * Obté totes les dades de la entitat en forma de string i amb tots els seus
	 * 	camps separats per un espai.
	 * @return la string amb totes les dades.
	 */
	public String convertirToString();
	
	/**
	 * Configura els atributs de la classe amb les dades passades per
	 * 	el paràmetre.
	 * @param vstr les dades generades inicialment amb el métode convertirToString()
	 * @return retorna 1 si hi ha un format incorrecte en alguna dada, 2 si hi ha hagut un
	 * 	error en algun filtre d'algún atribut i 0 si tot ha anat bé.
	 */
	public int obtenerFromString(String vstr);
	
	/**
	 * Retorna el separador corresponent al que s'ha fet servir al exportar les dades com a string
	 * @return el separador entre dada i dada.
	 */
	public String getSeparador();
	
	/**
	 * Deixa l'objecte en un estat com si es fessi un new
	 */
	public void netejaObjecte();
}
