package tsp;

import java.util.ArrayList;
import java.util.List;

public class Motor {
	
	private int n; 					//nb villes
	private int m; 					//nbfourmies
	private long[][] distances; 	//distances entre villes à récupérer dans données
	private double alpha=0.5;		//paramètre pour attribuer plus ou moins d'importance aux phéromones
	private double beta=0.5; 		//paramètre pour attribuer plus ou moins d'importance à la visibilité
	private double p=0.01; 			//quantité initiale de phéromones qu'on dépose sur tous les arcs
	private Ant[] AntSystem; 		//tableau de fourmis
	private int Nmax=10;			//nombre de cycles à faire avant d'arrêter le programme
	private int Q;
	private double evaporation;
	
	public Motor (int n, int m, long[][] distances, double alpha, double beta, double p, Ant[] AntSystem, int Nmax, double evaporation) {
		this.n=n;
		this.m=m;
		this.distances=distances;
		this.alpha=0.5;
		this.beta=0.5;
		this.p=0.01;
		this.AntSystem=AntSystem;
		this.Nmax=10;
		this.Q=Q;
		this.evaporation=evaporation;
		
	}
	
	public List<Integer> motor()  {
		
		//initialisation
		ArrayList<Integer> shortestWay = new ArrayList<Integer>(); //initialise la liste des villes du chemin plus court
		for(int i=1;i<n+1;i++) {		//donne une première réponse arbitraire en remplissant la liste de 1 à n et revient à 1
			shortestWay.add(i);
		}
		shortestWay.add(1);					//on oublie pas de boucler le cycle
		
		int N=0;						 	//nb cycle initialisé
		boolean sameWay = false; 			//aucune fourmi ne fait le même cycle (car pas de cycle de fait)
		double[][] vis=new double[n][n]; 	//tableau des visibilités
		
		double[][] pher=new double[n][n]; 	//tableau des phéromones
		for (int i=0;i<n;i++) {				//initialise le tableau de pheromones 
			for (int j=0;j<n;j++) {
				pher[i][j]=p;
			}
		}
		ArrayList<Integer> visited = new ArrayList<Integer>();	//crée une liste vide pour les villes visitées
		ArrayList<Integer> toVisit = new ArrayList<Integer>();	//crée un liste vide pour les villes à visiter
		
		for (Ant a : AntSystem) {									//pour chaque fourmis :
			a.setVisitedLength(0);									//met à O la distance parcourue
			int city = (int) Math.ceil(Math.random()*n);			//donne le numéro d'une ville au hasard
			a.setOriginCity(city);									//attribue cette ville comme ville d'origine (restera inchangée)
			a.setCurrentPosition(city);								//même ville pour ville où se trouve
			
			a.setVisitedCities(visited);							//attribue cette liste aux villes visitées
			
			for (int i=0;i<n;i++) {									//remplit la liste vide des villes restantes à visiter
				if (i+1!=city) {									//on ne met pas dans la liste la ville d'origine qui sera choisie la dernière
					toVisit.add(i+1);
				}
			}
			a.setCitiesStillToVisit(toVisit);						//on attribue cette ville a la liste des villes à visiter pour chaque fourmis		
		}
		int[][] wentThisPath = new int[n][n];						//on crée le tableau dont les valeurs seront 1 si la fourmi est passé entre i et j, 0 sinon
		for(int i=0;i<n;i++) {
			for(int j=0;j<n;j++) {
				wentThisPath[i][j]=0;								//on initialise tout à 0;
			}
		}
		//fin initialisation
		
		
		//début de la boucle qui opère tant qu'on n'a pas atteint le maximum Nmax ou que les fourmies ne font pas toutes le même chemin
		while (N!=Nmax || !sameWay) {
			// pour chaque itération :
			for (int i=0;i<n;i++) {
				
				//pour chaque fourmi, on remet à jour ses données
				for(Ant a : AntSystem) {
					int c = this.chooseNextCity(a,a.currentPosition);
					toVisit.remove(c);
					a.setCitiesStillToVisit(toVisit);
					visited.add(c);
					a.setVisitedCities(visited);
					a.setVisitedLength(distances[a.getCurrentPosition()][c]);
					wentThisPath[a.getCurrentPosition()][c]=1;
					a.setCurrentPosition(c);
				}
				
			}
					
			N+=1;
			sameWay=compareWaysCombination();	//retourne true si toutes les fourmies font le même chemin
			this.setPheromones(AntSystem, pher, evaporation, Q);			//remet à jour les pheromones sur tous les arcs
			shortestWay=compareWaysLength();	//retourne la liste des villes dont le chemin est le plus court parmi tous les chemin parcourues par les fourmis
			
		}	
		
		return shortestWay;						//on return le plus court chemin
	}


	
//Méthodes à faire pour compléter la méthode monitor

	private ArrayList<Integer> compareWaysLength() {
		// TODO Auto-generated method stub
		return null;
	}



	private boolean compareWaysCombination() {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int DeposedPheromones(int i, int j, Ant[] AntSystem) {         /*Si la fourmis a parcouru i->j ou j->i, retourne la quantité de pheromones déposés sur l'arc i-j, sinon retourne 0.*/
		int s=0;
		for (Ant ant : AntSystem) {
			s+=ant.getWentThisPath(i, j)*Q/ant.getVisitedLength();
		}return s;
		
	}
	
	public void setPheromones( Ant[] AntSystem, double[][] pheromones) {       /*Met a jour la quantite de pheronomes present sur tous les arcs*/
		for (int i=0; i<m_nbCities; i++) {
	        for (int j=0; j<m_nbCities; j++){
	            pheromones[i][j] = pheromones[i][j]*(100-evaporation)/100 + this.DeposedPheromones(i, j, AntSystem);
	            pheromones[j][i] = pheromones[j][i]*(100-evaporation)/100 + this.DeposedPheromones(j, i , AntSystem);  
	}}}
	
	// plus une ville est loin, moins elle a de chance d’être choisie =« visibilité »
	
	public long visibilite(int i, int j) {
		long v=0;
		try {
			if (m_instance.getDistances(i, j) != 0) {
			 v=1/m_instance.getDistances(i, j);
			}				
		} catch (Exception e) {	
			e.printStackTrace();
		}
				
		return v;
	}
	
	public int chooseNextCity(Ant k, int i) {
		int max=0;
	
		for (int j=0; j<this.citiesStillToVisit.size(); j++) {
			
			if (probability(i,citiesStillToVisit.get(j)) > max) {  //on choisit une ville selon une probabilité calculée à partir 
				max=j;									          //du taux de phéromones et de la visibilité des villes non visitées
			}													  // on conserve la ville correspondant à la proba la + élevée
		}
		

		try {
			this.setVisitedLength(this.VisitedLength + m_instance.getDistances(i, max)); //on ajoute à la longueur totale du trajet déjà effectué cette distance de l’arc parcouru
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return (max);
	}
	
	
	public double probability(int i, int j) {
		double proba;
		double s=0;
		for (int k=0; k<this.citiesStillToVisit.size(); k++) {
			if (this.citiesStillToVisit.contains(k)) {
			s=s+ Math.pow(pheromones.pheromones[k][j], ALPHA) * Math.pow(visibilite.visibilite(i,k), BETA);
			}
		}
	
		proba=( Math.pow(this.pheromones.pheromones[i][j], ALPHA) * Math.pow(visibilite.visibilite(i, j), BETA) ) /s;
		
		return proba;
	}

}
