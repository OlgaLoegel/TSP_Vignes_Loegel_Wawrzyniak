package tsp;

import tsp.metaheuristic.AMetaheuristic;

public class Visibilite {

	
	private Instance m_instance;
	
	

	public Visibilite(Instance m_instance) {
		super();
		this.m_instance = m_instance;
	}


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

		
	
}
