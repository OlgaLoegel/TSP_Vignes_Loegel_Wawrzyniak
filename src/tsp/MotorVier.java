package tsp;

import java.util.ArrayList;

public class MotorVier {
	private int n; 													//nb villes
	private int m; 													//nbfourmies
	private long[][] distances; 										//distances entre villes à récupérer dans données
	private double alpha;											//paramètre pour attribuer plus ou moins d'importance aux phéromones
	private double beta; 											//paramètre pour attribuer plus ou moins d'importance à la visibilité
	private double p; 												//quantité initiale de phéromones qu'on dépose sur tous les arcs
	private Ant[] AntSystem; 										//tableau de fourmis
	private int Q;													// constante liée à la quantité de phéromones déposée
	private double evaporation;										// constante liée à la quantité de phéromones évaporée
	long startTime = System.currentTimeMillis();						// début du chronomètre
	long spentTime = 0;												// temps écoulé depuis le début du chronomètre


	/**
	 * The Solution that will be returned by the program.
	 */
	private Solution m_solution;

	/** The Instance of the problem. */
	private Instance m_instance;

	/** Time given to solve the problem. */
	private long m_timeLimit;


	/** 
	 * Contructeur de la classe MotorBis 
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
	public MotorVier(int m, double alpha, double beta, double p, int Q, double evaporation, Instance instance, long timeLimit) {
		this.m_instance = instance;
		this.n=instance.getNbCities();
		this.m=m;
		this.distances=instance.getDistances();
		this.alpha=alpha;
		this.beta=beta;
		this.p=p;
		this.AntSystem = new Ant[m];
		for (int i=0; i<m; i++) {
			AntSystem[i]=new Ant(instance);
		}

		this.Q=Q;
		this.evaporation=evaporation;

		m_solution = new Solution(m_instance);
		m_timeLimit = timeLimit;

	}

	/** 
	 * Mise à jour de la solution 
	 */
	public void motor()  {

		//initialisation
		ArrayList<Integer> shortestWay = new ArrayList<Integer>();	//initialise la liste des villes du chemin plus court


		boolean sameWay = false; 									//aucune fourmi ne fait le même cycle (car pas de cycle de fait)

		float[][] vis=new float[n][n]; 								//création tableau des visibilités 
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
		



		for (Ant a : AntSystem) {									//pour chaque fourmis :
			a.setVisitedLength(0);									//met à O la distance parcourue
			int villeDepart = (int) (Math.random()*(n));
			ArrayList<Integer> visited = new ArrayList<Integer>();		//crée une liste vide pour les villes visitées
			visited.add(villeDepart);
			ArrayList<Integer> toVisit = new ArrayList<Integer>();		//crée un liste vide pour les villes à visiter
			
			a.setCurrentPosition(villeDepart);						//donne la position de départ
			
			for (int l=0;l<n;l++) {										//remplit la liste vide des villes restantes à visiter
				if(l!=villeDepart) {
					toVisit.add(l);									//on ne met pas dans la liste la ville d'origine qui sera choisie la dernière
				}
				
			}
				
			a.setVisitedCities(visited);								//attribue la liste visited à la liste des villes visitées
			a.setCitiesStillToVisit(toVisit);						//on attribue cette ville a la liste des villes à visiter pour chaque fourmis		
			
			int[][] wentThisPath = new int[n][n];						//on crée le tableau dont les valeurs seront 1 si la fourmi est passée entre i et j, 0 sinon
			for(int h=0;h<n;h++) {
				for(int j=0;j<n;j++) {
					wentThisPath[h][j]=0;								//on initialise tout à 0;
				}
			}		
			a.WentThisPath=wentThisPath;								//attribue le tableau initialisé à l'attribue wentThisPath de chaque fourmi a

		}
		Ant theFourmi = AntSystem[0];								//la première fourmi par défaut
		
		shortestWay=theFourmi.getVisitedCities();
		long shortest=theFourmi.getVisitedLength();
		

		//fin initialisation


		//début de la boucle qui opère tant qu'on n'a pas atteint le temps max ou que les fourmis ne font pas toutes le même chemin
		while (spentTime < (m_timeLimit * 1000 - 100) && !sameWay) {
			// pour chaque itération : (ie : on fait un cycle)
			for (int b=0;b<n-1;b++) {  														// on s'arrête à b=n-1 car la fourmi doit déjà rentrer
				System.out.println("\n >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>b=" + b);
				//pour chaque fourmi, on remet à jour ses données
				for(Ant a : AntSystem) {
					System.out.println("\n fourmi : " +a);
					System.out.println("a.citiesStillToVisit : "+a.citiesStillToVisit);
					System.out.println("currentPosition :"+ a.getCurrentPosition());
					
					int c = this.chooseNextCity(a,a.currentPosition,a.citiesStillToVisit,pher);
					System.out.println("next city : " +c);

					a.citiesStillToVisit.remove(a.citiesStillToVisit.indexOf(c));
					System.out.println("toVisit :" + a.citiesStillToVisit);
					
					a.visitedCities.add(c);
					a.VisitedLength=a.VisitedLength+distances[a.getCurrentPosition()][c];
					a.WentThisPath[a.getCurrentPosition()][c]=1;
					a.setCurrentPosition(c);
					System.out.println("liste des villes visitées : " + a.getVisitedCities());
					System.out.println("mise à jour de wentThisPath\n"+this.versString(a.WentThisPath));
				}


			}

			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>fin de cycle");
			
			for (Ant a: AntSystem) {										//on n'oublie pas de revenir à la case départ pour toutes les fourmis
				a.visitedCities.add(a.visitedCities.get(0));
				a.WentThisPath[a.getCurrentPosition()][a.visitedCities.get(0)]=1;
				a.VisitedLength=a.VisitedLength+distances[a.getCurrentPosition()][0];
				System.out.println(""+a);
				System.out.println("tableau final :\n"+this.versString(a.WentThisPath));
				System.out.println("longueur parcouru : "+a.getVisitedLength());
				
			}

			//sameWay=compareWaysCombination();										//retourne true si toutes les fourmis font le même chemin

			pher=setPheromones(AntSystem, pher, evaporation, Q);					//remet à jour les pheromones sur tous les arcs
			System.out.println("\n le tableau mis à jour des phéromones est :\n"+this.versString(pher));


			theFourmi = this.compareTo(AntSystem, theFourmi);
			shortest=theFourmi.VisitedLength;		//remplace par la nouvelle plus courte longueur trouvée au cours du cyle précédent si plus courte qu'avant
			System.out.println("longueur minimale : "+shortest);
			
			shortestWay=theFourmi.visitedCities;	//retourne la liste des villes dont le chemin est le plus court parmi tous les chemin parcourues par les fourmis
			System.out.println("chemin parcouru dont distance est la plus courte : "+shortestWay);
			
			spentTime = System.currentTimeMillis() - startTime;
			
			//on réinitialise tout pour les fourmis seulement !
			

			for (Ant a : AntSystem) {									//pour chaque fourmis :
				a.setVisitedLength(0);									//met à O la distance parcourue
				int villeDepart = (int) (Math.random()*(n));
				ArrayList<Integer> visited = new ArrayList<Integer>();		//crée une liste vide pour les villes visitées
				visited.add(villeDepart);
				ArrayList<Integer> toVisit = new ArrayList<Integer>();		//crée un liste vide pour les villes à visiter
				
				a.setCurrentPosition(villeDepart);						//donne la position de départ
				
				for (int l=0;l<n;l++) {										//remplit la liste vide des villes restantes à visiter
					if(l!=villeDepart) {
						toVisit.add(l);									//on ne met pas dans la liste la ville d'origine qui sera choisie la dernière
					}
					
				}
					
				a.setVisitedCities(visited);								//attribue la liste visited à la liste des villes visitées
				a.setCitiesStillToVisit(toVisit);						//on attribue cette ville a la liste des villes à visiter pour chaque fourmis		
				
				int[][] wentThisPath = new int[n][n];						//on crée le tableau dont les valeurs seront 1 si la fourmi est passée entre i et j, 0 sinon
				for(int h=0;h<n;h++) {
					for(int j=0;j<n;j++) {
						wentThisPath[h][j]=0;								//on initialise tout à 0;
					}
				}		
				a.WentThisPath=wentThisPath;								//attribue le tableau initialisé à l'attribue wentThisPath de chaque fourmi a

			}
			
			//on repart pour un nouveau cycle (on repart dans la boucle while)
			
		}

		int[] cities = new int[n+1];										//transformation en tableau
		for (int s=0;s<n+1;s++) {
			cities[s]=shortestWay.get(s);
		}
		m_solution.setM_cities(cities);		//affectation du tableau à la solution

		m_solution.setObjectiveValue(shortest);								//affectation de la plus courte longueur
		System.out.println(this.versString(distances));
		System.out.println(this.versString(vis));
		
	}



	/** 
	 * méthode qui complète la méthode monitor
	 */
	private Ant compareTo(Ant[] AntSystem, Ant theFourmi) {
		
		for (Ant a : AntSystem) {
			if (a.VisitedLength<theFourmi.getVisitedLength() || a.getVisitedCities().size()<3) {					//prend en compte la première fois qu'on l'utilise
				theFourmi=a;
			}
		}
		return theFourmi;
	}	


	/** 
	 * Renvoie true si toutes les fourmis effectuent le même chemin, false sinon
	 */
	private boolean compareWaysCombination() {
		// TODO Auto-generated method stub
		return false;
	}


	/** 
	 *Si la fourmis a parcouru i->j ou j->i, retourne la quantité de pheromones déposés sur l'arc i-j, sinon retourne 0
     @param int ville où se trouve actuellement la fourmi
     @param int ville où se rend la fourmi
     @param Ant[] tableau de fourmis
     @param int constante liée à la quantité de phéromones déposée
	 */
	public double DeposedPheromones(int i, int j, Ant[] AntSystem, int Q) {     
		int s=0;
		for (Ant ant : AntSystem) {
                      
			s+=ant.getWentThisPath(i, j)*Q/ant.getVisitedLength();
			
		}
		return s;

	}


	/** 
	 * Met a jour la quantite de pheronomes present sur tous les arcs
     @param Ant [] tableau de fourmis
     @param double [][] tableau indiquant les quantités de phéromones 
     @param double constante liée à la quantité de phéromones évaporée
     @param int constante liée à la quantité de phéromones déposée
	 */
	public double[][] setPheromones( Ant[] AntSystem, double[][] pheromones, double evaporation, int Q) {      
		double[][] pher = new double[n][n];
		for (int i=0; i<n; i++) {
			for (int j=0; j<n; j++){
				pher[i][j] = pheromones[i][j]*evaporation+ this.DeposedPheromones(i, j, AntSystem, Q);
				pher[j][i] = pheromones[j][i]*evaporation + this.DeposedPheromones(j, i , AntSystem, Q);  
			}
		}
		return pher;
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
		//	e.printStackTrace();
		//}

		return v;
	}

	public int chooseNextCity(Ant k, int i, ArrayList<Integer> toVisit, double[][] pher) {
		int max=toVisit.get(0);
		double probaMax=probability(i,toVisit.get(0),toVisit,pher);

		for (int j=1; j<toVisit.size(); j++) {
			double probInter= probability(i,toVisit.get(j),toVisit,pher);
			if ( probInter> probaMax) {  //on choisit une ville selon une probabilité calculée à partir 
				max=toVisit.get(j);	
				probaMax=probInter;//du taux de phéromones et de la visibilité des villes non visitées
			}													  // on conserve la ville correspondant à la proba la + élevée
		}

		return (max);
	}


	public double probability(int i, int j,ArrayList<Integer> toVisit, double[][] pher) {
		double proba;
		double s=0;
		for (int k=0; k<toVisit.size(); k++) {
			if (toVisit.contains(k)) {
				s=s+ Math.pow(pher[toVisit.get(k)][j], alpha) * Math.pow(visibilite(i,toVisit.get(k)), beta);
			}
		}

		proba=( Math.pow(pher[i][j], alpha) * Math.pow(visibilite(i, j), beta) ) /s;

		return proba;
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
	
	public String versString(long[][] vis) {
		String s= "[";
		
		for (long[] x:vis) {
			
			for(long y:x) {
				s+=y+", ";
			}
			s+="]\n";
		}
		s+="]";
		
		return s;
	}

	public String versString(float[][] vis) {
		String s= "[";
		
		for (float[] x:vis) {
			
			for(float y:x) {
				s+=y+", ";
			}
			s+="]\n";
		}
		s+="]";
		
		return s;
	}
	
	public String versString(int[][] vis) {
		String s= "[";
		
		for (int[] x:vis) {
			
			for(int y:x) {
				s+=y+", ";
			}
			s+="]\n";
		}
		s+="]";
		
		return s;
	}

}
