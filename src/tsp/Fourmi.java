package tsp;

import java.io.IOException;
import java.util.ArrayList;

public class Fourmi {
	private Instance m_instance;
	private int originCity;
	private ArrayList<Integer> visitedCities;        // toutes les villes visitées par la fourmi
    private ArrayList<Integer> citiesStillToVisit;    // toutes les villes encore à visiter
    private int currentPosition;
    private long tmpVisitedLength;
    
    public Fourmi(Instance m_instance) {
    	super();
    	this.m_instance=m_instance;
    	this.visitedCities= new ArrayList<Integer>();
    	for (int i=1; i<=m_instance.getNbCities(); i++) {
    	     this.visitedCities.add(i);}
    	this.originCity= (int)Math.ceil((Math.random()*m_instance.getNbCities()));
        this.tmpVisitedLength=0;
    }
    
   
    
}
