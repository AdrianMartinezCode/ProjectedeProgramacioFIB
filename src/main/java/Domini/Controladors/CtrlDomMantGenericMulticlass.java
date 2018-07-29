package Domini.Controladors;

import java.util.Map;
import java.util.Vector;

import Domini.IEntitatDomini;
import Domini.IEntitatDominiHerencia;
import Domini.Aplicacio.Excepcions.ExcErrorDomini;
import Domini.Aplicacio.Utils.EntradaGenerica;

public class CtrlDomMantGenericMulticlass<E extends IEntitatDominiHerencia> extends CtrlDominiMantGeneric<E> {

	/**
	 * Les instàncies de cadascuna de les subclasses de la superclasse.
	 */
	private Map<String, E> instancies;
	
	public CtrlDomMantGenericMulticlass(Map<String, E> instancies) {
		super(instancies.values().iterator().next());
		this.instancies = instancies;
	}
	
	@Override
	public int altaEntitat(int id, String dades) {
		//String nameClass = dades.split(" ")[1];
		String nameClass = getEntitat().getNomClasseFromDades(dades);
		//System.out.println(nameClass);
		E e = instancies.get(nameClass);
		if (e == null)
			throw new ExcErrorDomini("Nom de classe entitat incorrecta. ID entitat: " + id + " Nom classe incorrecta: " + nameClass);
		this.setEntitat(e);
		return super.altaEntitat(id, dades);
	}
	
	
	/**
	 * Crea una nova entitat amb els paràmetres definits per defecte a la pròpia entitat
	 * @param id identificador de la entitat.
	 * @param params paràmetres adicionals de creació de la nova entitat.
	 * @param classe quina classe de l'herència es vol donar d'alta.
	 * @return 1 si existeix la entitat amb aquest id, 3 si params és incorrecte, 
	 * 	retorna 4 si hi ha algun error en el format de les dades, 5 si algun filtre de l'entitat no passa. 
	 * 	retorna 0 , 0 si tot ha anat bé, 2 si el paràmetre id és diferent al primer
	 * 	element de l'string dades, 6 si el nom de la classe especificada és incorrecte.
	 */
	public int altaNovaEntitat(int id, Vector<String> params, String classe) {
		/*for (String s : instancies.keySet())
			System.out.println(s);*/
		E i = instancies.get(classe);
		if (i == null)
			return 6;
		//System.out.println(i);
		setEntitat(i);
		return super.altaNovaEntitat(id, params);
	}
}
