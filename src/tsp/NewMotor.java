package tsp;

import java.util.ArrayList;

public class NewMotor {



	private int n; 								//nb villes
	private int m;								//nbfourmies
	private long[][] distances;  				//distances entre villes à récupérer dans données
	private double alpha;						//paramètre pour attribuer plus ou moins d'importance aux phéromones
	private double beta;						//paramètre pour attribuer plus ou moins d'importance à la visibilité
	private double p;							//quantité initiale de phéromones qu'on dépose sur tous les arcs
	private Ant2[] AntSystem; 					//tableau de fourmis
	private int Q; 								// constante liée à la quantité de phéromones déposée
	private double evaporation; 				// constante liée à la quantité de phéromones évaporée
	long startTime = System.currentTimeMillis(); // début du chronomètre
	long spentTime = 0; 						// temps écoulé depuis le début du chronomètre

	/**
	 * The Solution that will be returned by the program.

	 */
	private Solution m_solution;
	/** The Instance of the problem. */
	private Instance m_instance;
	/** Time given to solve the problem. */
	private long m_timeLimit;
	/**
	 * Contructeur de la classe NewMotor
	 @param int nombre de fourmis
	 @param double nombre attribuant plus ou moins d'importance aux phéromones

	 @param double nombre attribuant plus ou moins d'importance à la visibilité

	 @param double quantité initiale de phéromones qu'on dépose sur tous les arcs

	 @param int constante liée à la quantité de phéromones déposée

	 @param double constante liée à la quantité de phéromones évaporée

	 @param Instance l'instance du problème
	 @param long temps imposé pour résoudre le problème

	 @ param m_instance donnée du problème associée à la solution

	 */
	public NewMotor(int m, double alpha, double beta, double p, int Q, double evaporation, Instance instance, long timeLimit) {
		this.m_instance = instance;
		this.n=instance.getNbCities();
		this.m=m;
		this.distances=instance.getDistances();
		this.alpha=alpha;
		this.beta=beta;
		this.p=p;
		this.AntSystem = new Ant2[m];
		for (int i=0; i<m; i++) {
			AntSystem[i]=new Ant2(instance);
		}
		this.Q=Q;
		this.evaporation=evaporation;
		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;
	}
	/**
	 * Mise à jour de la solution
	 */
	public void motor() {
		boolean sameWay=false;
		ArrayList<Integer> shortestWay = new ArrayList<Integer>();

		long shortest=0;

		float[][] vis=new float[n][n]; 			//création tableau des visibilités
		for (int i=0;i<n;i++) {					//initialisation tableau de visibilité
			for (int j=0;j<n;j++) {
				vis[i][j]=visibilite(i, j);
			}
		}

		double[][] pher=new double[n][n];		//tableau des phéromones
		for (int k=0;k<n;k++) {					//initialise le tableau de pheromones 
			for (int j=0;j<n;j++) {
				pher[k][j]=p;
			}
		}

		ArrayList<Integer> theFourmiChemin = new ArrayList<Integer>();
		long theFourmiLongueur = 0;

		while (spentTime < (m_timeLimit * 1000 - 100) && !sameWay) {

				
			//initialisation



			for (Ant2 a : AntSystem) {
				//pour chaque fourmis :
				a.VilleDepart= (int) (Math.random()*(n));
				a.setVisitedLength(0);								//met à O la distance parcourue
				a.setCurrentPosition(a.VilleDepart);				//donne la position de départ
				a.setVisitedCities(new ArrayList<Integer>());		//attribue la liste visited à la liste des villes visitées
				a.visitedCities.add(a.VilleDepart);
				a.setCitiesStillToVisit(new ArrayList<Integer>()); 	//on attribue cette ville a la liste des villes à visiter pour chaque fourmis
			
				for (int l=0;l<n;l++) {								//remplit la liste vide des villes restantes à visiter
					if (l!=a.VilleDepart) {							//on ne met pas dans la liste la ville d'origine qui sera choisie 	la dernière 
						a.citiesStillToVisit.add(l);
					}
				}

				for(int h=0;h<n;h++) {
					for(int j=0;j<n;j++) {
						a.WentThisPath[h][j]=0;						//on initialise tout à 0;
					}
				}
				

			}


			//fin initialisation



			//début de la boucle qui opère tant qu'on n'a pas atteint le temps max ou 	que les fourmis ne font pas toutes le même chemin

			// pour chaque itération : (ie : on fait un cycle)



			for (int b=0;b<n-1;b++) {
				// on s'arrête à b=n-1 car la fourmi doit déjà rentrer

				//pour chaque fourmi, on remet à jour ses données

				for(Ant2 a : AntSystem) {

					a.proba.clear();

					double s=0;
					
					for (int k=0; k<a.citiesStillToVisit.size(); k++) {

						s=s+ (Math.pow(pher[a.currentPosition][a.citiesStillToVisit.get(k)], alpha) * Math.pow(visibilite(a.currentPosition,a.citiesStillToVisit.get(k)), beta));

					}
					
					for (int e=0; e<a.citiesStillToVisit.size(); e++) {

						a.proba.add(e, (
								Math.pow(pher[a.currentPosition][a.citiesStillToVisit.get(e)], alpha) *
								Math.pow(visibilite(a.currentPosition, a.citiesStillToVisit.get(e)), beta) ) /s);}


					int c = this.chooseNextCity(a);


					a.citiesStillToVisit.remove(a.citiesStillToVisit.indexOf(c));


					a.visitedCities.add(c);

					a.VisitedLength=a.VisitedLength+distances[a.getCurrentPosition()][c];

					a.WentThisPath[a.getCurrentPosition()][c]=1;

					a.WentThisPath[c][a.getCurrentPosition()]=1;


					a.setCurrentPosition(c);

				}
			}

			for (Ant2 a: AntSystem) {
				//on n'oublie pas de revenir à la case départ pour toutes les 	fourmis
				a.visitedCities.add(a.VilleDepart);

				a.WentThisPath[a.getCurrentPosition()][a.VilleDepart]=1;

				a.VisitedLength=a.VisitedLength+distances[a.getCurrentPosition()][0];

				a.citiesStillToVisit = new ArrayList<Integer>();


			}
			sameWay=compareWaysCombination();  										//retourne true si toutes les fourmis font le 	même chemin
			this.setPheromones(AntSystem, pher, evaporation, Q);					//remet à jour les pheromones sur tous les arcs
 
			
			/* On compare les distances parcourues par les fourmis lors du cycle et on prend la plus courte si meilleure qu'avant*/
			for(Ant2 a : AntSystem) {
				if(a.getVisitedLength()<theFourmiLongueur || theFourmiLongueur==0) {
					theFourmiLongueur=a.getVisitedLength();
					theFourmiChemin=a.getVisitedCities();
				}
			}

			
			
			
			shortest=theFourmiLongueur; 		//remplace par la nouvelle plus courte longueur trouvée au cours du cyle précédent 	si plus courte qu'avant
			System.out.println(""+shortest);
			shortestWay=theFourmiChemin; 	//retourne la liste des villes dont le chemin est le plus court parmi tous leschemin parcourues par les fourmis
			spentTime = System.currentTimeMillis() - startTime;


		} 
		int[] cities = new int[n+1]; 			//transformation en tableau
		for (int s=0;s<n+1;s++) {
			cities[s]=shortestWay.get(s);
		}
		m_solution.setM_cities(cities); 		//affectation du tableau à la solution

		m_solution.setObjectiveValue(shortest); //affectation de la plus courte longueur à la solution de la fonction objectif

	}

	
	
	
	
	
	/**
	 * Renvoie true si toutes les fourmis effectuent le même chemin, false sinon

	 */
	private boolean compareWaysCombination() {
		boolean b=true;
		for (int i=0 ; i<m ; i++) {
			for (int j=0; j<m; j++) {
				if (!AntSystem[i].getVisitedCities().equals(AntSystem[j].getVisitedCities())){
					return false;
				}
			}
		}
		return b; 
	}
	
	
	/**
	 *Si la fourmis a parcouru i->j ou j->i, retourne la quantité de pheromones déposés
	sur l'arc i-j, sinon retourne 0
	 @param int ville où se trouve actuellement la fourmi

	 @param int ville où se rend la fourmi
	 @param Ant[] tableau de fourmis
	 @param int constante liée à la quantité de phéromones déposée

	 */
	public long DeposedPheromones(int i, int j, Ant2[] AntSystem, int Q) {

		long s=0;
		for (Ant2 ant : AntSystem) {
			if (ant.WentThisPath[i][j]==1) { //A REVOIR

				s=s+Q/ant.getVisitedLength();

			}
		}
		return s;
	}
	/**
	 * Met a jour la quantite de pheronomes present sur tous les arcs

	 @param Ant2 [] tableau de fourmis
	 @param double [][] tableau indiquant les quantités de phéromones

	 @param double constante liée à la quantité de phéromones évaporée

	 @param int constante liée à la quantité de phéromones déposée

	 */
	public void setPheromones( Ant2[] AntSystem, double[][] pheromones, double evaporation, int Q) {
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++){
				pheromones[i][j] = pheromones[i][j]*evaporation + this.DeposedPheromones(i, j, AntSystem, Q);

			}
		}
	}

	
	
	// plus une ville est loin, moins elle a de chance d’être choisie =« visibilité »

	public float visibilite(int i, int j) {
		float v=0;
		//try {
		if (distances[i][j] != 0) {
			float b=(float)distances[i][j];

			v=1/b;
			//}

		} // (Exception e) { 
		// e.printStackTrace();
		//}
		return v;
	}
	
	
	public int chooseNextCity(Ant2 k) {
		double c = Math.random();
		int i =0;
		double d= k.proba.get(i);
		while (c>d && d<=1) {
			i++;
			d=d+k.proba.get(i);
		}
		return k.citiesStillToVisit.get(i);
	}

	public Solution getM_solution() {
		return m_solution;
	}


	public String versString(double[][] pher) {
		String s= "[";
		
		for (double[] x:pher) {
			
			for(double y:x) {
				s+=y+", ";
			}
			s+="]\n";
		}
		s+="]";
		
		return s;
	}



}
