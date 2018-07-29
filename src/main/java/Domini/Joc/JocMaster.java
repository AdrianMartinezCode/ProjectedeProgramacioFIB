package Domini.Joc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import java.util.Map.Entry;

import Domini.Aplicacio.IDadesPartida;
import Domini.Aplicacio.INotificaJocAcabat;
import Domini.Aplicacio.Utils.ColorGenerador;
import Domini.Aplicacio.Utils.Pair;
import Domini.ia.Prova;
/**
 * Aquesta classe contindr totes les funcions que ens permetran jugar com a jocMaster i tamb la Inteligncia Artificial
 *
 */
public class JocMaster extends JocActual {
	/**
	 * total s un HashMap amb totes les combinacions possibles de colors, per exemple, suposem que hi ha 6 colors i 4 posicions, els valors del HashMap anirn de [0, 0, 0, 0] fins a [5, 5, 5, 5] sense deixarse'n cap
	 */
	public	HashMap<Integer, Vector<Integer> > total;
	/**
	 * possibles al primer torn ser com el HashMap total, per, a mesura que avancem en el temps noms es quedaran les combinacions que puguin ser la combinaci amagada
	 */
	public HashMap<Integer, Vector<Integer> > possibles;
	
	public JocMaster(IDadesPartida dadesPart, INotificaJocAcabat not) {
		super(dadesPart, not);
		setTornActual(0);
	}

	@Override
	public Rol getRol() {
		return Rol.CodeMaster;
	}
	
	
	/**
	 * En IntroduirComb l'usuari afegir la combinaci que hi ha amagada i la mquina generar la primera combinaci que vol resoldre
	 * @param Combinacio
	 * @return
	 */
	public int IntroduirComb(Vector<Integer> Combinacio) {
		int numFitxes = super.getDadesPartida().getNPosicions();
		if (numFitxes != Combinacio.size()) 
			return 2;
		int numColors = super.getDadesPartida().getNColors();
		for(int i = 0; i < Combinacio.size(); i++) {
			if(numColors <= Combinacio.get(i))
				return 3;
		}
		//if(this.obtenirCombinacioAmagada().size() == numFitxes)
		//	return 1;
		this.introduirCombinacioAmagada(Combinacio);
		Vector<Integer> CombInicial = this.getCombinacioInicial(numFitxes);
		this.afegirCombinacio(CombInicial);
		return 0;
	}		
		
	public HashMap<Integer, Vector<Integer>> getTotal() {
		return total;
	}

	public HashMap<Integer, Vector<Integer>> getPossibles() {
		return possibles;
	}

		/**
		 * Genera la segent combinaci que la mquina voldr provar
		 * @param numColors
		 * @param numFitxes
		 * @return Una combinaci de fitxes generada aleatoriament
		 */
		public Vector<Integer> jugaFacil(int numColors,int numFitxes){
			Vector<Integer> Combinacio2 = new Vector<Integer>(numFitxes);
				Random rand = new Random();
				for(int i = 0; i<numFitxes; i++) {
					int nouColor = (int) rand.nextInt(numColors);
					Combinacio2.add(nouColor);
				}
			
			return Combinacio2;
		}
		
		
		/**
		 * al carregar la partida inicialitzarï¿½ els valors que tenien total i possibles
		 */
		public void inicialitzaPossiblesITotal() {
			int numFitxes = super.getDadesPartida().getNPosicions();
			int numColors = super.getDadesPartida().getNColors();

			total = this.crearTotal(numFitxes,numColors);
			possibles = this.crearTotal(numFitxes, numColors);

			for (int i = 0; i < super.getSizeTauler(); i++) {
				System.out.println("Torn actual: " + super.getTornActual());
				Pair<Integer, Integer> p = JocActual.comptaPistes(super.getPistaIessimaTauler(i));
				Vector<Integer> pistes = new Vector<Integer>();
				pistes.add(p.getSecond());
				pistes.add(p.getFirst());
				possibles = this.netejarPossibles(numFitxes, pistes, super.getCombinacioIessimaTauler(i), possibles, total);
			}
		}
		/**
		 * Depenent de la dificultat, cridar un mtode o un altre per resoldre quina ser la segent combinacio i en el cas de dificultat baixa l'afegir al tauler 
		 * @return 0
		 */
		public int seguentComb() {
			Vector<Integer> resp = this.obtenirUltimaPista();
			Vector<Integer> Resposta = new Vector<Integer>();
			System.out.println("PISTAAAA: " + resp.toString());
			int correctes = 0, malposades = 0;
			for (Integer i : resp) {
				if (i == ColorGenerador.getNumPistaBlanca())
					malposades++;
				else if (i == ColorGenerador.getNumPistaNegra())
					correctes++;
					
			}
			
			Resposta.add(correctes);
			Resposta.add(malposades);
			
			
			int numFitxes = super.getDadesPartida().getNPosicions();
			int numColors = super.getDadesPartida().getNColors();
			Vector<Integer> Combinacio2 = this.obtenirUltimaCombinacio();
			Dificultat dif = this.getDadesPartida().getDificultat();
			if(dif == Dificultat.Alta) {
				if(possibles == null || total == null) {
					
					inicialitzaPossiblesITotal();
				}
				System.out.println("passa per aqui");
				this.seguentComb2(numFitxes, Resposta, Combinacio2);
			}
			else if (dif == Dificultat.Baixa) {
				Vector<Integer> NovaCombinacio = this.jugaFacil(numColors, numFitxes);
				this.afegirCombinacio(NovaCombinacio);
			}
			return 0; 
		}
		
		/**
		 * Es cridar per trobar la segent combinaci a trobar quan dificultat s alta
		 * @param numFitxes
		 * @param Resposta
		 * @param Combinacio2
		 * @param possibles
		 * @param total
		 * @return 2
		 */
		public int seguentComb2 (int numFitxes,Vector<Integer> Resposta,Vector<Integer> Combinacio2){
			possibles = this.netejarPossibles(numFitxes, Resposta, Combinacio2, possibles, total);
			System.out.println(this.possibles.toString());
			//System.out.println(total.toString());
			Combinacio2 = this.getCombinacio2(numFitxes, Resposta, possibles, total);
			System.out.println("JOCMASTER: combinaciÃ³ escollida: " + Combinacio2.toString());
			System.out.println("NUM FITXES: " + numFitxes);
			this.afegirCombinacio(Combinacio2);
			System.out.println("JOCMASTER: combinaciÃ³ escollida: " + Combinacio2.toString());
			return 2;
		}
		
		/**
		 * Mira que les pistes que posa l'usuari coincideixin amb les que haurien de ser (amb ajuts)
		 * @param CorreccioUsuari
		 * @return 0 si tot estï¿½ bï¿½
		 */
		public int corregeixAmbAjuts(Vector<Integer> CorreccioUsuari) {
			int numFitxes = super.getDadesPartida().getNPosicions();
			int numColors =  super.getDadesPartida().getNColors();
			Vector<Integer> Combinacio = this.obtenirCombinacioAmagada();
			Vector<Integer> Combinacio2 = this.obtenirUltimaCombinacio();
			//System.out.println("Combinacio amagada");
			if(Combinacio2.size() != numFitxes) 
				return 1;
			if(!super.getDadesPartida().getAjuts())
				return 2;
			if(super.jocAcabat())
				return 3;
			if(CorreccioUsuari.size() != numFitxes)
				return 5;
			Prova corregirFitxes = new Prova();
			Vector<Integer> CorreccioMaquina = corregirFitxes.corregir2(numFitxes, Combinacio, Combinacio2);
			System.out.println("Pista maquina: " + CorreccioMaquina.toString());
			System.out.println("Pista introduida: " + CorreccioUsuari.toString());
			for (int i = 0; i < numFitxes; i++) {
				if(!CorreccioMaquina.get(i).equals(CorreccioUsuari.get(i))) 
					return 4;
			}
			super.afegirPista(CorreccioUsuari);
			return 0;
		}
		
		/**
		 * Mira que les pistes que posa l'usuari coincideixin amb les que haurien de ser (sense ajuts)
		 * @param RespostaUsuari
		 * @return 4 si l'usuari no ha corregit b, 3 si ho ha fet b
		 */
		public int corregeix2(Vector<Integer> RespostaUsuari) {
			int numFitxes = super.getDadesPartida().getNPosicions();
			Vector<Integer> Combinacio = this.obtenirCombinacioAmagada();
			Vector<Integer> Combinacio2 = this.obtenirUltimaCombinacio();
			if(Combinacio2.size() != numFitxes) 
				return 1;
			if(super.getDadesPartida().getAjuts() != false)
				return 2;
			if(super.jocAcabat() != false)
				return 3;
			Vector<Integer> Resposta = this.getResposta(numFitxes, Combinacio, Combinacio2);
			System.out.println("Pista interna obtinguda: " + Resposta.toString());
			System.out.println("Pista interna usuari: " + RespostaUsuari.toString());
			
			int l = corregeix(Resposta, RespostaUsuari);
			if (l == 4)return 4;
			else {
				Prova corregirFitxes = new Prova();
				Vector<Integer> CorreccioAAfegir = corregirFitxes.corregir2(numFitxes, Combinacio, Combinacio2);
				this.afegirPista(CorreccioAAfegir);
			}
			return 0;
		}
		
		/**
		 * Retorna 4 si s'equivoca i 0 si ho fa b
		 * @param Resposta s generada per la mquina al comparar les 2 combinacions
		 * @param RespostaUsuari s l'imput de l'usuari que vol corregir
		 * @return 4 si s'equivoca i 0 si ho fa b
		 */
		public int corregeix(Vector<Integer> Resposta, Vector<Integer> RespostaUsuari) {
			//Scanner sc = new Scanner(System.in);
			boolean ok = false;
			int Correctes = Resposta.get(0);
			int MalPosades = Resposta.get(1);
			//System.out.println("Escriu quantes fitxes coincideixen i quantes estan mal posades per el color s correcte");
			//int Correctes2 = sc.nextInt();
			//int MalPosades2 = sc.nextInt();
			
			/*if (!super.getDadesPartida().getAjuts()) {
				Pair<Integer, Integer> p = JocActual.comptaPistes(RespostaUsuari);
				RespostaUsuari = new Vector<Integer>();
				RespostaUsuari.add(p.getSecond());
				RespostaUsuari.add(p.getFirst());
				
				
			}*/
			
			System.out.println("Pista maquina: " + Resposta.toString());
			System.out.println("Pista huma: " + RespostaUsuari.toString());
			
			int Correctes2 = RespostaUsuari.get(0);
			int MalPosades2 = RespostaUsuari.get(1);
			
			ok = (Correctes == Correctes2 && MalPosades == MalPosades2);
			if(!ok)	return 4;
			else return 0;
		}
			
			
			
		/**
		 * s un mtode per crear HashMaps amb totes les combinacions possibles
		 * @param numFitxes
		 * @param numColors
		 * @return HashMap amb totes les combinacions possibles
		 */
		public HashMap<Integer, Vector<Integer> > crearTotal(int numFitxes, int numColors) {
			HashMap<Integer, Vector<Integer> > total = new HashMap<Integer, Vector<Integer> >(); //principi 1
			Vector<Integer> afegir = new Vector<Integer>(numFitxes);
			for(int as = 0; as < numFitxes; as++) {
				afegir.add(0);
			}
			Vector<Integer> afegir1 = new Vector<Integer>(numFitxes);
			for(int qw = 0; qw < numFitxes; qw++){
				afegir1.add(afegir.get(qw));
			}
			total.put(0, afegir1);
			for(int i = 0; i < Math.pow(numColors, numFitxes)-1; i++){
				afegir.set(0, afegir.get(0)+1);
				for(int l=numFitxes-1; l>=0;l--){
					if(afegir.get(l) == numColors) {
						afegir.set(l+1,afegir.get(l+1)+1);
						afegir.set(l, 0);
					}
				}
				Vector<Integer> afegir2 = new Vector<Integer>(numFitxes);
				for(int qw = numFitxes-1; qw >= 0; qw--){
					afegir2.add(afegir.get(qw));
				}	
				total.put(i+1, afegir2);
			}
			return total;
		}
		
		
		/**
		 * Genera la combinacio inicial
		 * @param numFitxes
		 * @return La primera combinacio que provar la mquina quan li toqui fer de codeBreaker
		 */
		public Vector<Integer> getCombinacioInicial (int numFitxes){
			//System.out.println(numFitxes);
			Vector<Integer> CombinacioInicial = new Vector<Integer>(numFitxes);
			for(int ini = 0; ini < numFitxes; ini++) {
				if(ini < numFitxes/2)CombinacioInicial.add(0);
				else CombinacioInicial.add(1);
			}
			//System.out.println(CombinacioInicial);
			return CombinacioInicial;
		}

		
		
		/**
		 * Compara dues combinacions i retorna les pistes
		 * @param numFitxes
		 * @param Combinacio
		 * @param Combinacio2
		 * @return les pistes sense ajuts, s a dir, un vector on la primera posici s el nmero de fitxes que coincideixen i el segon s el nmero de colors que coincideixen per estn mal posats
		 */
		public Vector<Integer> getResposta(int numFitxes, Vector<Integer> Combinacio, Vector<Integer> Combinacio2){
			Prova corregirFitxes = new Prova();
	       	Vector<Integer> Resposta = corregirFitxes.corregir(numFitxes, Combinacio, Combinacio2);
			return Resposta;
		}
		
		
		
		/**
		 * Agafa el HashMap possibles i, analitzant la combinaci posada i les pistes que ens retorna, elimina totes les combinacions que ja no sn possibles solucions
		 * @param numFitxes
		 * @param Resposta Sn les pistes
		 * @param Combinacio2
		 * @param possibles
		 * @param total
		 * @return
		 */
		public HashMap<Integer, Vector<Integer> > netejarPossibles (int numFitxes,Vector<Integer> Resposta,Vector<Integer> Combinacio2, HashMap<Integer, Vector<Integer> > possibles, HashMap<Integer, Vector<Integer> > total){
			Vector<Integer> Resposta2 = new Vector<Integer>();
			Vector<Integer> C2 = new Vector<Integer>();
			Set<Entry<Integer, Vector<Integer>>> entrySet = possibles.entrySet();
			Iterator<Entry<Integer, Vector<Integer>>> it = entrySet.iterator();
			System.out.println("PISTA: " + Resposta.toString());
			Prova corregirFitxes = new Prova();
			while(it.hasNext()){
				Map.Entry<Integer, Vector<Integer>> entry = it.next();
				C2 = entry.getValue();
		       	Resposta2 = corregirFitxes.corregir(numFitxes, C2, Combinacio2);
		       	if(!Resposta2.equals(Resposta))it.remove();
		    }
			return possibles;
		}	
		
		public int calcularNumeroNetejats(int numFitxes,Vector<Integer> Resposta,Vector<Integer> Combinacio2, HashMap<Integer, Vector<Integer> > possibles) {
			int comptador = 0; 
			Vector<Integer> Resposta2 = new Vector<Integer>();
			Vector<Integer> C2 = new Vector<Integer>();
			Set<Entry<Integer, Vector<Integer>>> entrySet = possibles.entrySet();
			Iterator<Entry<Integer, Vector<Integer>>> it = entrySet.iterator();
			Prova corregirFitxes = new Prova();
			while(it.hasNext()){
				Map.Entry<Integer, Vector<Integer>> entry = it.next();
				C2 = entry.getValue();
		       	Resposta2 = corregirFitxes.corregir(numFitxes, C2, Combinacio2);
		       	if(!Resposta2.equals(Resposta))comptador++;
		    }
			return comptador;

		}
		
		/**
		 * Busca quina ser la segent combinaci a provar utilitzant l'algorisme Minmax
		 * @param numFitxes
		 * @param Resposta
		 * @param possibles
		 * @param total
		 * @return La segent combinaci a provar
		 */
		public Vector<Integer> getCombinacio2 (int numFitxes, Vector<Integer> Resposta, HashMap<Integer, Vector<Integer> > possibles, HashMap<Integer, Vector<Integer> > total){
			Vector<Integer> Combinacio2 = new Vector<Integer>(numFitxes);
			Vector<Integer> totalV = new Vector<Integer>();
			Vector<Integer> possiblesV = new Vector<Integer>();
			int t = 0;
		    int provar = 0;
			Vector<Integer> amplitudRespostes = new Vector<Integer>();//SERVIRÀ PER EVITAR ERRORS
			for(int i = 0; i < total.size(); i++) {
				amplitudRespostes.add(0);
		    }
		    Vector<Integer> minim = new Vector<Integer>(total.size());
			for(int asd = 0; asd < total.size(); asd++) {
		       	minim.add(0);
		    }
			Vector<Integer> estaPossibles = new Vector<Integer>(total.size());
			for(int asdf = 0; asdf < total.size(); asdf++) {
				estaPossibles.add(0);
		    }
			Vector<Integer> totalEliminats = new Vector<Integer>();
			Set<Entry<Integer, Vector<Integer>>> entrySet2 = total.entrySet();
			Iterator<Entry<Integer, Vector<Integer>>> it2 = entrySet2.iterator();
			while(it2.hasNext()) {
				Map.Entry<Integer, Vector<Integer>> entry2 = it2.next();
				totalV = entry2.getValue();
				boolean esta = false;						
				int minimPossibles = possibles.size(); //minim eliminats per cada combinacio
				Vector<Integer> Eliminats = new Vector<Integer>();
				for(int lk = 0; lk <= numFitxes; lk++) {//possibles combinacions de bé malament
					for(int lk2 = numFitxes - lk; lk2 >= 0; lk2--) {
						Vector<Integer> RespostaProva = new Vector<Integer>();
						RespostaProva.add(lk);
						RespostaProva.add(lk2);
						//netejar possibles depenent de la combinacio i mirar size
						int possibles2 = calcularNumeroNetejats(numFitxes, RespostaProva, totalV, possibles);
						if(possibles2 < minimPossibles)minimPossibles = possibles2;
					}
				}
				totalEliminats.add(minimPossibles);
				if(esta)estaPossibles.set(t, 1);//vector que diu si la combinació està entre les possibles
				t++;
			}
			int maximEliminador = 0;//serà el num d'eliminats sobre el que volem
			for(int ir = 0; ir < totalEliminats.size(); ir++) {
				int act = totalEliminats.get(ir);
				if (maximEliminador < act)maximEliminador = act;
			}
			for(int jr = 0; jr < totalEliminats.size(); jr++) {
				if(totalEliminats.get(jr) == maximEliminador) {
					provar = jr;
					break;
				}
			}
			for(int qr = provar; qr < totalEliminats.size(); qr++) {
				if(possibles.containsKey(qr)) {
					provar = qr;
					break;
				}
			}
			Combinacio2 = total.get(provar);
			return Combinacio2;
		}

		
}
