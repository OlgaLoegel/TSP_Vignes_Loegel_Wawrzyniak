package tsp;

import java.util.ArrayList;
import java.util.List;

public class MotorBis {
	private int n; 					//nb villes
	private int m=10; 				//nbfourmies
	private long[][] distances; 	//distances entre villes à récupérer dans données
	private double alpha=1;		//paramètre pour attribuer plus ou moins d'importance aux phéromones
	private double beta=1; 		//paramètre pour attribuer plus ou moins d'importance à la visibilité
	private double p=0.5; 			//quantité initiale de phéromones qu'on dépose sur tous les arcs
	private Ant[] AntSystem; 		//tableau de fourmis
	private int Q=100;
	private double evaporation=0.5;
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
	
	
	
	public MotorBis(int n, int m, long[][] distances, double alpha, double beta, double p, Ant[] AntSystem, int Q, double evaporation, Instance instance, long timeLimit) {
		this.n=instance.getNbCities();
		this.m=m;
		this.distances=instance.getDistances();
		this.alpha=0.5;
		this.beta=0.5;
		this.p=0.01;
		this.AntSystem=AntSystem;
		this.Q=Q;
		this.evaporation=evaporation;
		m_instance = instance;
		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;
		
	}
	
	public MotorBis() {
		
	}
	
	public void motor()  {
		
		//initialisation
		ArrayList<Integer> shortestWay = new ArrayList<Integer>();	//initialise la liste des villes du chemin plus court
		shortestWay.add(0);											// la première ville sera 0
		for(int i=1;i<n;i++) {										//donne une première réponse arbitraire en remplissant la liste de 1 à n et revient à 1
			shortestWay.add(i);
		}
		shortestWay.add(0);											//on oublie pas de boucler le cycle en revenant à 0
		
		long shortest=0;											//on initialise la plus courte distance à la première combinaison de shortestWay
		for(int e=0;e<this.n+1;e++) {
			shortest += distances[shortestWay.get(e)][shortestWay.get(e+1)];
		}
		
		
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
		
		for (Ant a : AntSystem) {									//pour chaque fourmis :
			a.setVisitedLength(0);									//met à O la distance parcourue
			int city = 0;											//affecte la ville de départ à 0
			a.setCurrentPosition(city);								//donne la position de départ
			
			a.setVisitedCities(visited);							//attribue la liste vide à la liste des villes visitées
			
			for (int l=0;l<n;l++) {									//remplit la liste vide des villes restantes à visiter
				if (l!=city) {										//on ne met pas dans la liste la ville d'origine qui sera choisie la dernière (ici, 0)
					toVisit.add(l);
				}
			}
			a.setCitiesStillToVisit(toVisit);						//on attribue cette ville a la liste des villes à visiter pour chaque fourmis		
		}
		
		int[][] wentThisPath = new int[n][n];						//on crée le tableau dont les valeurs seront 1 si la fourmi est passée entre i et j, 0 sinon
		for(int h=0;h<n;h++) {
			for(int j=0;j<n;j++) {
				wentThisPath[h][j]=0;								//on initialise tout à 0;
			}
		}
		//fin initialisation
		
		
		//début de la boucle qui opère tant qu'on n'a pas atteint le temps max ou que les fourmies ne font pas toutes le même chemin
		while (spentTime < (m_timeLimit * 1000 - 100) || !sameWay) {
			// pour chaque itération : (ie : on fait un cycle)
			for (int b=0;b<this.n;b++) {
				
				//pour chaque fourmi, on remet à jour ses données
				for(Ant a : AntSystem) {
					int c = this.chooseNextCity(a,a.currentPosition,toVisit,pher);
					toVisit.remove(c);
					a.setCitiesStillToVisit(toVisit);
					visited.add(c);
					a.setVisitedCities(visited);
					a.setVisitedLength(distances[a.getCurrentPosition()][c]);
					wentThisPath[a.getCurrentPosition()][c]=1;
					a.setCurrentPosition(c);
				}
				
			}
			
			for (Ant a: AntSystem) {										//on n'oublie pas de revenir à la case départ pour toutes les fourmies
				ArrayList<Integer> vi = a.getVisitedCities();
				vi.add(0);
				a.setVisitedCities(vi);
			}

			sameWay=compareWaysCombination();								//retourne true si toutes les fourmies font le même chemin
			
			this.setPheromones(AntSystem, pher, evaporation, Q);			//remet à jour les pheromones sur tous les arcs
			
			shortest=compareTo(AntSystem, shortest);						//remplace par la nouvelle plus courte longueur trouvée au cours du cyle précédent si plus courte qu'avant
			
			
			shortestWay=compareWaysLength(AntSystem);						//retourne la liste des villes dont le chemin est le plus court parmi tous les chemin parcourues par les fourmis
			spentTime = System.currentTimeMillis() - startTime;
		}
		
		int[] cities = new int[n+1];										//transformation en tableau
		for (int s=0;s<n+1;s++) {
			cities[s]=shortestWay.get(s);
		}
		m_solution.setM_cities(cities);										//affectation du tableau à la solution
		
		m_solution.setObjectiveValue(shortest);								//affectation de la plus courte longueur
		
							
	}


	


//Méthodes à faire pour compléter la méthode monitor

	private long compareTo(Ant[] AntSystem, long shortest) {
		long shor = shortest;
		for (int z=0;z<this.m;z++) {
			ArrayList<Integer> vis=AntSystem[z].getVisitedCities();
			long loc = 0;
			for(int y=0;y<this.n+1;y++) {
				loc +=distances[vis.get(y)][vis.get(y+1)];
			}
			if (loc<shortest) {
				shor=loc;
			}
		}
		return shor;
	}	
		
		
		
		
		
		
	//	Retourne la liste des villes visitées dont le chemin est le plus court
	//  et prends en paramètre le tableau de fourmies mis à jour à la fin du cycle (voir motor)
		
		
	private ArrayList<Integer> compareWaysLength(Ant[] AntSystem) {
		long l = AntSystem[0].getVisitedLength();					//initialise la longeur à celle de la première fourmie
		int numberOfTheAnt = 0;										//donne par défaut la première fourmie comme ayant le plus court chemin
		for (int i=0;i<this.m;i++) {								//pour chaque fourmie : on compare la distance à celle enregistrée précédemment
			Ant a = AntSystem[i];									//                      et si celle-ci est plus petite, on enregistre le numéro de la fourmie
			if (a.getVisitedLength()<l) {
				l=a.getVisitedLength();
				numberOfTheAnt = i;
			}
		
		}
				
		return AntSystem[numberOfTheAnt].getVisitedCities();		//on retourne la liste des villes visitées par  
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
	
	
	

}
