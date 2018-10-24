package tsp;

import java.util.ArrayList;
import java.util.List;

public class MotorBis {
	private int n; 					//nb villes
	private int m; 				//nbfourmies
	private long[][] distances; 	//distances entre villes à récupérer dans données
	private double alpha;		//paramètre pour attribuer plus ou moins d'importance aux phéromones
	private double beta; 		//paramètre pour attribuer plus ou moins d'importance à la visibilité
	private double p; 			//quantité initiale de phéromones qu'on dépose sur tous les arcs
	private Ant[] AntSystem; 		//tableau de fourmis
	private int Q;
	private double evaporation;
	long startTime = System.currentTimeMillis();
	long spentTime = 0;
	
	/**
	 * The Solution that will be returned by the program.
	 */
	private Solution m_solution;

	/** The Instance of the problem. */
	private Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;
	
	
	
	public MotorBis(int m, double alpha, double beta, double p, int Q, double evaporation, Instance instance, long timeLimit) {
		this.n=instance.getNbCities();
		this.m=m;
		this.distances=instance.getDistances();
		this.alpha=alpha;
		this.beta=beta;
		this.p=p;
		this.AntSystem=new Ant[m];
		this.Q=Q;
		this.evaporation=evaporation;
		m_instance = instance;
		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;
		
	}


	public void motor()  {
		
		//initialisation
		ArrayList<Integer> shortestWay = new ArrayList<Integer>();	//initialise la liste des villes du chemin plus court
		shortestWay.add(0);											// la première ville sera 0
				
		long shortest=0;											//on initialise la plus courte distance à la première combinaison de shortestWay

		boolean sameWay = false; 									//aucune fourmi ne fait le même cycle (car pas de cycle de fait)
		
		long[][] vis=new long[n][n]; 								//création tableau des visibilités 
		for (int i=0;i<n;i++) {										//initialisation tableau de visibilité
			for (int j=0;j<n;j++) {
				vis[i][j]=visibilite(i, j);
			}
		}
		
		double[][] pher=new double[n][n]; 							//tableau des phéromones
		for (int k=0;k<n;k++) {										//initialise le tableau de pheromones 
			for (int j=0;j<n;j++) {
				pher[k][j]=p;
			}
		}
		ArrayList<Integer> visited = new ArrayList<Integer>();		//crée une liste vide pour les villes visitées
		ArrayList<Integer> toVisit = new ArrayList<Integer>();		//crée un liste vide pour les villes à visiter
		visited.add(0);												//les fourmis partent de 0
		
		for (int l=1;l<n;l++) {										//remplit la liste vide des villes restantes à visiter
																	//on ne met pas dans la liste la ville d'origine qui sera choisie la dernière (ici, 0)
				toVisit.add(l);
		}
		
		int[][] wentThisPath = new int[n][n];						//on crée le tableau dont les valeurs seront 1 si la fourmi est passée entre i et j, 0 sinon
		for(int h=0;h<n;h++) {
			for(int j=0;j<n;j++) {
				wentThisPath[h][j]=0;								//on initialise tout à 0;
			}
		}
		
		
		
		for (Ant a : AntSystem) {									//pour chaque fourmis :
			a.setVisitedLength(0);									//met à O la distance parcourue
			a.setCurrentPosition(0);								//donne la position de départ					
			a.setVisitedCities(visited);							//attribue la liste vide à la liste des villes visitées
			a.setCitiesStillToVisit(toVisit);						//on attribue cette ville a la liste des villes à visiter pour chaque fourmis		
			a.WentThisPath=wentThisPath;							//attribue le tableau initialisé à l'attribue wentThisPath de chaque fourmi a

		}
		
		//fin initialisation
		
		
		//début de la boucle qui opère tant qu'on n'a pas atteint le temps max ou que les fourmies ne font pas toutes le même chemin
		while (spentTime < (m_timeLimit * 1000 - 100) || !sameWay) {
			// pour chaque itération : (ie : on fait un cycle)
			for (int b=0;b<this.n;b++) {
				
				//pour chaque fourmi, on remet à jour ses données
				for(Ant a : AntSystem) {
					int c = this.chooseNextCity(a,a.currentPosition,a.citiesStillToVisit,pher);
					a.citiesStillToVisit.remove(c);
					a.visitedCities.add(c);
					a.VisitedLength=a.VisitedLength+distances[a.getCurrentPosition()][c];
					a.WentThisPath[a.getCurrentPosition()][c]=1;
					a.setCurrentPosition(c);
				}
				
			}
			
			for (Ant a: AntSystem) {										//on n'oublie pas de revenir à la case départ pour toutes les fourmies
				a.visitedCities.add(0);
				a.WentThisPath[a.getCurrentPosition()][0]=1;
				a.VisitedLength=a.VisitedLength+distances[a.getCurrentPosition()][0];
			}

			sameWay=compareWaysCombination();										//retourne true si toutes les fourmies font le même chemin
			
			this.setPheromones(AntSystem, pher, evaporation, Q);					//remet à jour les pheromones sur tous les arcs
			
			shortest=AntSystem[compareTo(AntSystem, shortest)].VisitedLength;		//remplace par la nouvelle plus courte longueur trouvée au cours du cyle précédent si plus courte qu'avant
			
			shortestWay=AntSystem[compareTo(AntSystem, shortest)].visitedCities;	//retourne la liste des villes dont le chemin est le plus court parmi tous les chemin parcourues par les fourmis
			spentTime = System.currentTimeMillis() - startTime;
		}
		
		int[] cities = new int[n+1];										//transformation en tableau
		for (int s=0;s<n+1;s++) {
			cities[s]=shortestWay.get(s);
		}
		m_solution.setM_cities(cities);								//affectation du tableau à la solution
		
		m_solution.setObjectiveValue(shortest);								//affectation de la plus courte longueur
		
							
	}


	


//Méthodes à faire pour compléter la méthode monitor

	private int compareTo(Ant[] AntSystem, long shortest) {
		int i = -1; 
		int theFourmi = 0;
		for (Ant a : AntSystem) {
			i++;
			if (a.VisitedLength<shortest || shortest==0) {					//prend en compte la première fois qu'on l'utilise
				shortest=a.VisitedLength;
				theFourmi=i;
			}
		}
		return theFourmi;
	}	



	private boolean compareWaysCombination() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int DeposedPheromones(int i, int j, Ant[] AntSystem, int Q) {         /*Si la fourmis a parcouru i->j ou j->i, retourne la quantité de pheromones déposés sur l'arc i-j, sinon retourne 0.*/
		int s=0;
		for (Ant ant : AntSystem) {
			s+=ant.getWentThisPath(i, j)*Q/ant.getVisitedLength();
		}return s;
		
	}

	public void setPheromones( Ant[] AntSystem, double[][] pheromones, double evaporation, int Q) {       /*Met a jour la quantite de pheronomes present sur tous les arcs*/
		for (int i=0; i<n; i++) {
	        for (int j=0; j<n; j++){
	            pheromones[i][j] = pheromones[i][j]*(100-evaporation)/100 + this.DeposedPheromones(i, j, AntSystem, Q);
	            pheromones[j][i] = pheromones[j][i]*(100-evaporation)/100 + this.DeposedPheromones(j, i , AntSystem, Q);  
	}}}
	
	// plus une ville est loin, moins elle a de chance d’être choisie =« visibilité »
	
	public long visibilite(int i, int j) {
		long v=0;
		try {
			if (distances[i][j] != 0) {
			 v=1/distances[i][j];
			}				
		} catch (Exception e) {	
			e.printStackTrace();
		}
				
		return v;
	}
	
	public int chooseNextCity(Ant k, int i, ArrayList<Integer> toVisit, double[][] pher) {
		int max=0;
	
		for (int j=0; j<toVisit.size(); j++) {
			
			if (probability(i,toVisit.get(j),toVisit,pher) > max) {  //on choisit une ville selon une probabilité calculée à partir 
				max=j;									          //du taux de phéromones et de la visibilité des villes non visitées
			}													  // on conserve la ville correspondant à la proba la + élevée
		}
		
		return (max);
	}
	
	
	public double probability(int i, int j,ArrayList<Integer> toVisit, double[][] pher) {
		double proba;
		double s=0;
		for (int k=0; k<toVisit.size(); k++) {
			if (toVisit.contains(k)) {
			s=s+ Math.pow(pher[k][j], alpha) * Math.pow(visibilite(i,k), beta);
			}
		}
	
		proba=( Math.pow(pher[i][j], alpha) * Math.pow(visibilite(i, j), beta) ) /s;
		
		return proba;
	}

	public Solution getM_solution() {
		return m_solution;
	}
	
	
	

}
