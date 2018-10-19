package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Ant {

	public Instance m_instance;
	public int originCity;                          // ville d'origine de la fourmi
	public ArrayList<Integer> visitedCities;        // liste de toutes les villes visitées par la fourmi
    public ArrayList<Integer> citiesStillToVisit;    // liste de toutes les villes encore à visiter
    public int currentPosition;                    // position actuelle de la fourmi //
    public long VisitedLength;                     // distance déjà parcourue par la fourmi //
    public int[][] WentThisPath;                   // tableau de 0 et de 1 indiquant 0 en [i][j] et [j][i] lorsque la fourmi a emprunté le chemin i->j (ou j->i), 0 sinon.   

    
    public Ant(Instance m_instance) {
    	super();
    	this.m_instance=m_instance;
    	this.visitedCities= new ArrayList<Integer>();
    	for (int i=1; i<=m_instance.getNbCities(); i++) {
    	     this.visitedCities.add(i);}

    	this.originCity= originCity;

        this.VisitedLength=0;}
    
public int getWentThisPath(int i, int j) {
	return this.WentThisPath[i][j];
}

public void setWentThisPath(int i, int j) {
	this.WentThisPath[i][j]=1;
}

public void setOriginCity(int i) {
	this.originCity=i;
}
  
public ArrayList<Integer> getVisitedCities(){
        return this.visitedCities; 
}
   public ArrayList<Integer> getCitiesStillToVisit(){
	   return this.citiesStillToVisit;
   }
  
   public void setCitiesStillToVisit(ArrayList<Integer> citiesToVisit) {
	   this.citiesStillToVisit= citiesToVisit;
   }


   public long getVisitedLength() {

 		return VisitedLength;
 	}
   
   public int getCurrentPosition() {
	   return this.currentPosition;
   }

    public void setVisitedCities(ArrayList<Integer> visitedCities) {
	   this.visitedCities = visitedCities;
}

  

	public void setVisitedLength(long VisitedLength) {
		this.VisitedLength = VisitedLength;
	}


	public void setCurrentPosition(int currentPosition) {
	   this.currentPosition = currentPosition;
}
}