package tsp;

import tsp.metaheuristic.AMetaheuristic;

public class Visibilite {

	
	private Instance m_instance;
	
	

	public Visibilite(Instance m_instance) {
		super();
		this.m_instance = m_instance;
	}


	// plus une ville est loin, moins elle a de chance d’être choisie =« visibilité »
	
		public void visibilite(int i, int j) {
			try {
				if (m_instance.getDistances(i, j) != 0) {
				long  v=1/m_instance.getDistances(i, j);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		
	
}
