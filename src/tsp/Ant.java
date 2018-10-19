package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Ant {

	public Instance m_instance;
	public int originCity;
	public ArrayList<Integer> visitedCities;        // toutes les villes visitées par la fourmi
    public ArrayList<Integer> citiesStillToVisit;    // toutes les villes encore à visiter
    public int currentPosition;
    public long VisitedLength;
    public int[][] WentThisPath;

    
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

public void setOriginCity(int i) {
	this.originCity=i;
}
  
public ArrayList<Integer> getVisitedCities(){
        return this.visitedCities; 
}
   public ArrayList<Integer> getCitiesStillVisited(){
	   return this.citiesStillToVisit;
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