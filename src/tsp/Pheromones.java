package tsp;

public class Pheromones  {
	
	/*Paramètres*/
	public Instance m_instance;
	public int m_nbCities; 
	public int Q;                 /*Taux d'évaporation*/
	public double[][] pheromones; /* Tableau indiquant la quantité de pheromones entre chaque ville i et j*/
	public double evaporation;    /* Taux d'évaporation */
	public Ant[] Antsystem;       /* Tableau constitué de l'ensemble des fourmis*/
	
	public Pheromones(Instance m_instance, int m_nbCities,  int Q, double[][] pheromones, double evaporation,
	Ant[] Antsystem) {
		super();
		this.m_instance=m_instance;
		this.m_nbCities=m_nbCities; 
		this.Q=Q;
		this.pheromones=pheromones;  
		this.evaporation=evaporation;
		this.Antsystem=Antsystem;
		
	}
	
	/*Methodes*/
	
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
}
