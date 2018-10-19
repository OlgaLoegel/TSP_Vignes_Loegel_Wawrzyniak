package tsp;

public class Pheromones extends Fourmi {
	
	/*Paramètres*/
	public Instance m_instance;
	public int m_nbCities; 
	public int Q;
	public double[][] pheromones; /* Tableau indiquant la quantité de pheromones entre chaque ville i et j*/
	public double evaporation;    /* Taux d'évaporation */
	
	/*Constructeur*/
	
	public Pheromones(Instance instance) {    /* Constructeur initialisant la taille du tableau pheromones à m_nbCities^2 */
		pheromones = new float[m_nbCities][m_nbCities];
	}
	
	/*Methodes*/
	
	public void NbAntWentThisPath(int i, int j) {
		for (Fourmis fourmis : this.AntSystem) {
			fourmis.WentThisPath[i][j]
		}
		
	}
	
	public void setPheromones() {
		for (int i=0; i<m_nbCities; i++) {
	        for (int j=0; j<m_nbCities; j++){
	            pheromones[i][j] = pheromones[i][j]*(100-evaporation)/100 + this.NbAntWentThisPath(i,j);
	            pheromones[j][i] = pheromones[j][i]*(100-evaporation)/100 + this.NbAntWentThisPath(j,i);  
	}}
}}
