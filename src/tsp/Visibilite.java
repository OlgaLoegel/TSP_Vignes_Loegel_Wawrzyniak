package tsp;

import tsp.metaheuristic.AMetaheuristic;

public class Visibilite extends AMetaheuristic {

	// plus une ville est loin, moins elle a de chance d’être choisie =« visibilité »
	
	
		public Visibilite(Instance instance, String name) throws Exception {
		super(instance, name);
		// TODO Auto-generated constructor stub
	}

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

		@Override
		public Solution solve(Solution sol) throws Exception {
			// TODO Auto-generated method stub
			return null;
		}
	
}
