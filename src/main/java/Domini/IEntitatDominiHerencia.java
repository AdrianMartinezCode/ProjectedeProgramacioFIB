package Domini;

public interface IEntitatDominiHerencia extends IEntitatDomini {
	
	/**
	 * Retorna el nom de la classe real.
	 * @param dades les dades en una string que retorna el métode convertirToString()
	 * @return una string amb el nom de la classe.
	 */
	public String getNomClasseFromDades(String dades);
}
