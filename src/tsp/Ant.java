package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Ant {
	private Instance m_instance;
	private int originCity;
	private ArrayList<Integer> visitedCities;        // toutes les villes visitées par la fourmi
    private ArrayList<Integer> citiesStillToVisit;    // toutes les villes encore à visiter
    private int currentPosition;
    private long VisitedLength;
    private int[][] WentThisPath;
    
    public Ant(Instance m_instance) {
    	super();
    	this.m_instance=m_instance;
    	this.visitedCities= new ArrayList<Integer>();
    	for (int i=1; i<=m_instance.getNbCities(); i++) {
    	     this.visitedCities.add(i);}
    	this.originCity= this.originCity;
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
  
   public long getTmpVisitedLength() {
 		return VisitedLength;
 	}
   
   public int getCurrentPosition() {
	   return this.currentPosition;
   }

    public void setVisitedCities(ArrayList<Integer> visitedCities) {
	   this.visitedCities = visitedCities;
}

  
	public void setTmpVisitedLength(long tmpVisitedLength) {
		this.VisitedLength = tmpVisitedLength;
	}


	public void setCurrentPosition(int currentPosition) {
	   this.currentPosition = currentPosition;
}
}