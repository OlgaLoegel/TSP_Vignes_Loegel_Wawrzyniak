package tsp;

public class Pheromones {
	
	/*Paramètres*/
	
	private int m_nbCities; 
	private float[][] pheromones; /* Tableau indiquant la quantité de pheromones entre chaque ville i et j*/
	private float evaporation;    /* Taux d'évaporation */
	
	/*Constructeur*/
	
	public Pheromones(Instance instance) {    /* Constructeur initialisant la taille du tableau pheromones à m_nbCities^2 */
		m_nbCities = instance.getNbCities();
		pheromones = new float[m_nbCities][m_nbCities];
	}
	
	/*Methodes*/
	public void setPheromones(int origin, int destination, int tmpVisitedlength) {              /* Depose des pheromones entre la ville origin et la ville destination*/
		pheromones[origin][destination]= pheromones[origin][destination]+100/tmpVisitedlength;
		pheromones[destination][origin]= pheromones[destination][origin]+100/tmpVisitedlength;
	}
	
	public void evaporatePheromones() {           /* Evapore les pheromones entre les villes i et j */
		 for (int i=0; i<m_nbCities; i++) {
		        for (int j=0; j<i; j++){
		            pheromones[i][j] = pheromones[i][j]*(100-evaporation)/100;
		            pheromones[j][i] = pheromones[j][i]*(100-evaporation)/100;             
		        }}
		
	}
}
