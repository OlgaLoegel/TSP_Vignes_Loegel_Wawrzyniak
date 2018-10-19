package tsp;

public class Pheromones  {
	
	/*Paramètres*/
	public Instance m_instance;
	public int m_nbCities; 
	public int Q;
	public double[][] pheromones; /* Tableau indiquant la quantité de pheromones entre chaque ville i et j*/
	public double evaporation;    /* Taux d'évaporation */
	public Ant[] Antsystem;
	
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
	
	public int NbAntWentThisPath(int i, int j, Ant[] AntSystem) {
		int s=0;
		for (Ant fourmis : AntSystem) {
			s+=fourmis.getWentThisPath(i, j);
		}return s;
		
	}
	
	public void setPheromones( Ant[] AntSystem) {
		for (int i=0; i<m_nbCities; i++) {
	        for (int j=0; j<m_nbCities; j++){
	            pheromones[i][j] = pheromones[i][j]*(100-evaporation)/100 + this.NbAntWentThisPath(i,j, AntSystem)*Q/this.getTmpVisitedLength();
	            pheromones[j][i] = pheromones[j][i]*(100-evaporation)/100 + this.NbAntWentThisPath(j,i, AntSystem)*Q/this.getTmpVisitedLength();  
	}}
}}
