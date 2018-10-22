package tsp;

public class Pheromones  {
	


public int DeposedPheromones(int i, int j, Ant[] AntSystem, int Q) {         /*Si la fourmis a parcouru i->j ou j->i, retourne la quantité de pheromones déposés sur l'arc i-j, sinon retourne 0.*/
	int s=0;
	for (Ant ant : AntSystem) {
		s+=ant.getWentThisPath(i, j)*Q/ant.getVisitedLength();
	}return s;
	
}

public void setPheromones( Ant[] AntSystem, double[][] pheromones, double evaporation, int Q) {       /*Met a jour la quantite de pheronomes present sur tous les arcs*/
	for (int i=0; i<n; i++) {
        for (int j=0; j<n; j++){
            pheromones[i][j] = pheromones[i][j]*(100-evaporation)/100 + this.DeposedPheromones(i, j, AntSystem, Q);
            pheromones[j][i] = pheromones[j][i]*(100-evaporation)/100 + this.DeposedPheromones(j, i , AntSystem, Q);  
}}