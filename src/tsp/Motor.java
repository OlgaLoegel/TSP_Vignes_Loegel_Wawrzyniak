package tsp;

import java.util.ArrayList;
import java.util.List;

public class Motor {
	
	private int n; //nb villes
	private int m; //nbfourmies
	private double[][] distances; //disances entre villes à récupérer dans données
	private double alpha; // paramètre pour attribuer plus ou moins d'importance aux phéromones
	private double beta; //paramètre pour attribuer plus ou moins d'importance à la visibilité
	private double p=0.01; //quantité initiale de phéromones qu'on dépose sur tous les arcs
	private Ant[] AntSystem;
	
	
	
	public List<Integer> motor() {
		//initialisation
		ArrayList<Integer> shortestWay = new ArrayList<Integer>(); //initialise la liste des villes du chemin plus court
		for(int i=1;i<n+1;i++) {		//donne une première réponse arbitraire en remplissant la liste de 1 à n et revient à 1
			shortestWay.add(i);
		}
		shortestWay.add(1);					//on oublie pas de boucler le cycle
		
		int N=0;						 	//nb cycle initialisé
		boolean sameParcours = false; 		//aucune fourmi ne fait le même cycle (car pas de cycle de fait)
		double[][] vis=new double[n][n]; 	//tableau des visibilités
		
		double[][] pher=new double[n][n]; 	//tableau des phéromones
		for (int i=0;i<n;i++) {				//initialise le tableau de pheromones 
			for (int j=0;j<n;j++) {
				pher[i][j]=p;
			}
		}
		for (Ant a : AntSystem) {									//pour chaque fourmis :
			a.setVisitedLength(0);									//met à O la distance parcourue
			int city = (int) Math.ceil(Math.random()*n);			//donne le numéro d'une ville au hasard
			a.setOriginCity(city);									//attribue cette ville comme ville d'origine (restera inchangée)
			a.setCurrentPosition(city);								//même ville pour ville où se trouve
			ArrayList<Integer> visited = new ArrayList<Integer>();	//crée une liste vide
			a.setVisitedCities(visited);							//attribue cette liste aux villes visitées
			ArrayList<Integer> toVisit = new ArrayList<Integer>();	//crée un liste vide
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
		
		
		
		//boucle while à faire !!

		
		
		return shortestWay;											//on return leplus court chemin
	}

}
