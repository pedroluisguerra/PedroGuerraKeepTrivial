package io.keepcoding.pgkeeptrivial.model;

import java.util.HashSet;
import java.util.Set;

public class Teams {
	private String name;
	private Set<String> cheeseswon; // This interface will storage cheeses obtained by each team.

    public Teams(String name) {
    	this.name = name;
	    this.cheeseswon = new HashSet<>();	// Initialized the constructor
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void addCheese(String cheese) {
    	this.cheeseswon.add(cheese);
    }
    
    public boolean gameWon() {
    	return this.cheeseswon.size() == 5; // We control when a team wins and game over.
    }
    
    public Set<String> getCheesesObtained(){
    	return this.cheeseswon;
    }
}

    
