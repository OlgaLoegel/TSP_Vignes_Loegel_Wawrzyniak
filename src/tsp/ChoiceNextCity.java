package tsp;

public class ChoiceNextCity extends Ant {

	public static final int ALPHA=1;
	public static final int BETA=5;
	
	public Instance m_instance;
	public Pheromones pheromones; 
	public Visibilite visibilite;

	
	
	
	public ChoiceNextCity(Instance m_instance) {
		super(m_instance);
		this.m_instance = m_instance;
	}
	
	
	public void ChoiceNextCity(Ant k, int i) {
		int max=0;
	
		for (int j=0; j<this.citiesStillToVisit.size(); j++) {
			
			if (probability(i,citiesStillToVisit.get(j)) > max) {  //on choisit une ville selon une probabilité calculée à partir 
				max=j;									          //du taux de phéromones et de la visibilité des villes non visitées
			}													  // on conserve la ville correspondant à la proba la + élevée
		}
		
		this.visitedCities.add(max); // on ajoute la ville sur laquelle elle vient d’arriver dans la liste des villes déjà visitées

		this.citiesStillToVisit.remove(max); //on retire cette ville des villes à visiter 
		try {
			this.setVisitedLength(this.VisitedLength + m_instance.getDistances(i, max)); //on ajoute à la longueur totale du trajet déjà effectué cette distance de l’arc parcouru
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
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
