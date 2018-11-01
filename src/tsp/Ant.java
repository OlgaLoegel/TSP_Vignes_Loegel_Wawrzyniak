package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Ant {
	

	public Instance m_instance;
	public ArrayList<Integer> visitedCities;        // liste de toutes les villes visitées par la fourmi
    public ArrayList<Integer> citiesStillToVisit;    // liste de toutes les villes encore à visiter
    public int currentPosition;                    // position actuelle de la fourmi //
    public long VisitedLength;                     // distance déjà parcourue par la fourmi //
    public int[][] WentThisPath;                   // tableau de 0 et de 1 indiquant 0 en [i][j] et [j][i] lorsque la fourmi a emprunté le chemin i->j (ou j->i), 0 sinon.   
    public int VilleDepart;
    /** 
     * Contructeur de la classe fourmi 
     * on ajoute à la liste solution les villes visitées 
     * l'origine est attribuée à la ville d'origine de la fourmi
     * la longueur du trajet effectué est initialisé à 0
     @ param m_instance donnée du problème associée à la solution
    */
    public Ant(Instance m_instance) {
    	this.m_instance=m_instance;
    	this.visitedCities= new ArrayList<Integer>();
    	this.citiesStillToVisit= new ArrayList<Integer>();
    	this.currentPosition=0;
    	this.WentThisPath= new int[m_instance.getNbCities()][m_instance.getNbCities()];
        for (int i =0; i<m_instance.getNbCities(); i++) {
        	for (int j =0; j<m_instance.getNbCities(); j++) {
        		this.WentThisPath[i][j]=0;
        	}
        	
        }
        this.VisitedLength=0;
        }
    
    
    /**
     * retourne 1 si la fourmi a emprunté le chemin i->j (ou j->i), 0 sinon
     @param int la ville où se trouve actuellement la fourmi
     @param int la prochaine ville à visiter
     */
    public int getWentThisPath(int i, int j) {
	return this.WentThisPath[i][j];
}


	/**
	 * on indique 1 si la fourmi a emprunté le chemin i->j (ou j->i), 0 sinon
	 @param int la ville où se trouve actuellement la fourmi
     @param int la prochaine ville à visiter
	 */
	public void setWentThisPath(int i, int j) {
	this.WentThisPath[i][j]=1;
}

  
	/**
	 * Retourne la liste des villes visitées par la fourmi
	 */
	 public ArrayList<Integer> getVisitedCities(){
        return this.visitedCities; 
}
	 
	 /**
	 * Retourne la liste des villes encore à visiter par la fourmi
	 */
   public ArrayList<Integer> getCitiesStillToVisit(){
	   return this.citiesStillToVisit;
   }
  
   	/**
	 * Remplace la liste des villes encore à visiter par la fourmi par la liste indiquée en paramètre
	 @param ArrayList<Integer> liste des villes à visiter 
	 */
   public void setCitiesStillToVisit(ArrayList<Integer> citiesToVisit) {
	   this.citiesStillToVisit= citiesToVisit;
   }

   /**
	 * Retourne la distance déjà parcourue par la fourmi
	 */
   public long getVisitedLength() {
 		return VisitedLength;
 	}
   

   /**
	 * Retourne la position actuelle de la fourmi
	 */
   public int getCurrentPosition() {
	   return this.currentPosition;
   }


   /**
	 * Remplace la liste des villes visitées par la fourmi par la liste indiquée en paramètre
	 @param ArrayList<Integer> liste des villes visitées
	 */
    public void setVisitedCities(ArrayList<Integer> visitedCities) {
	   this.visitedCities = visitedCities;
}

  
    /**
	 * Remplace la distance parcourue par la fourmi par la distance indiquée en paramètre
	 @param long distance parcourue par la fourmi
	 */
	public void setVisitedLength(long VisitedLength) {
		this.VisitedLength = VisitedLength;
	}

	/**
	 * Remplace la position actuelle de la fourmi par la position indiquée en paramètre
	 @param int position actuelle de la fourmi
	 */
	public void setCurrentPosition(int currentPosition) {
	   this.currentPosition = currentPosition;
}
}