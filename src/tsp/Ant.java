package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Ant {
<<<<<<< HEAD
	private Instance m_instance;
	private int originCity;
	private ArrayList<Integer> visitedCities;        // toutes les villes visitées par la fourmi
    private ArrayList<Integer> citiesStillToVisit;    // toutes les villes encore à visiter
    private int currentPosition;
    private long VisitedLength;
    private int[][] WentThisPath;
=======
	public Instance m_instance;
	public int originCity;
	public ArrayList<Integer> visitedCities;        // toutes les villes visitées par la fourmi
    public ArrayList<Integer> citiesStillToVisit;    // toutes les villes encore à visiter
    public int currentPosition;
    public long VisitedLength;
>>>>>>> branch 'master' of https://github.com/OlgaLoegel/TSP_Vignes_Loegel_Wawrzyniak.git
    
    public Ant(Instance m_instance) {
    	super();
    	this.m_instance=m_instance;
    	this.visitedCities= new ArrayList<Integer>();
    	for (int i=1; i<=m_instance.getNbCities(); i++) {
    	     this.visitedCities.add(i);}
<<<<<<< HEAD
    	this.originCity= this.originCity;
=======
    		this.originCity=originCity;
>>>>>>> branch 'master' of https://github.com/OlgaLoegel/TSP_Vignes_Loegel_Wawrzyniak.git
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
  
<<<<<<< HEAD
   public long getTmpVisitedLength() {
=======
   public long getVisitedLength() {
>>>>>>> branch 'master' of https://github.com/OlgaLoegel/TSP_Vignes_Loegel_Wawrzyniak.git
 		return VisitedLength;
 	}
   
   public int getCurrentPosition() {
	   return this.currentPosition;
   }

    public void setVisitedCities(ArrayList<Integer> visitedCities) {
	   this.visitedCities = visitedCities;
}

  
<<<<<<< HEAD
	public void setTmpVisitedLength(long tmpVisitedLength) {
		this.VisitedLength = tmpVisitedLength;
=======
	public void setVisitedLength(long VisitedLength) {
		this.VisitedLength = VisitedLength;
>>>>>>> branch 'master' of https://github.com/OlgaLoegel/TSP_Vignes_Loegel_Wawrzyniak.git
	}


	public void setCurrentPosition(int currentPosition) {
	   this.currentPosition = currentPosition;
}
}