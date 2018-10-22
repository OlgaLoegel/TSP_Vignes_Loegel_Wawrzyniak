package tsp;

public class ChooseNextCity extends Ant {

	public static final int ALPHA=1;
	public static final int BETA=5;
	
	public Instance m_instance;
	public Pheromones pheromones; 
	public Visibilite visibilite;

	
	
	
	public ChooseNextCity(Instance m_instance) {
		super(m_instance);
		this.m_instance = m_instance;
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
