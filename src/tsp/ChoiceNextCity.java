package tsp;

public class ChoiceNextCity extends Ant {

	public static final int ALPHA=1;
	public static final int BETA=5;
	
	private Instance m_instance;
	
	
	public ChoiceNextCity(Instance m_instance) {
		super(m_instance);
		this.m_instance = m_instance;
	}
	
	
	public void ChoiceNextCity(Ant k, int i) {
		int max=0;
	
		for (int j=0; j<this.citiesStillToVisit.size(); j++) {
			
			if (probability(i,citiesStillToVisit.get(j)) > max) {
				max = j;
			}
		}
		
		this.visitedCities.add(max);
		this.citiesStillToVisit.remove(max);
		try {
			this.setVisitedLength(this.VisitedLength + m_instance.getDistances(i, max));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
	
	
	public double probability(int i, int j) {
		double proba;
		double s=0;
		for (int k=0; k<this.citiesStillToVisit.size(); k++) {
			if (citiesStillToVisit.contains(k)) {
			s=s+ Math.pow(Pheromones.pheromones[k][j], ALPHA) * Math.pow(Visibilite.visibilite(i,k), BETA);
			}
		}
	
		proba=( Math.pow(this.pheromones[i][j], ALPHA) * Math.pow(Visibilite.visibilite(i, j), BETA) ) /s;
		
		return proba;
	}
	
}
