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
		for (Ant a : AntSystem) {
			a.setVisitedLength(0);
			int city = (int) Math.ceil(Math.random()*n);
			a.setOriginCity(city);
			a.setCurrentPosition(city);
			
		}

		return shortestWay;
	}

}
