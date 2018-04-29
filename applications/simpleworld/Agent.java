package applications.simpleworld;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;

import worlds.World;

public abstract class Agent extends UniqueDynamicObject{
	
	final static int MAX_NRJ = 20;
	final static int MAX_FATIGUE = 100;
	boolean alive;
	boolean male;
	int energie = 20;
	int fatigue = 100;
		
	
	public Agent ( int __x , int __y, World __world )
	{
		super(__x,__y,__world);
		
		alive = true;
		
		if (Math.random() >= 0.45) {
			male = false;
		}
		else {
			male = true;
		}
		
	}
	
	public abstract void step(); 
	/*{
		if (!this.estVivant()) {
			world.getUniqueDynObject().remove(this);
			return;
		}
		if ( world.getIteration() % 5 == 0 )
		{
			double dice = Math.random();
			if ( dice < 0.25 )
				this.x = ( this.x + 1 ) % this.world.getWidth() ;
			else
				if ( dice < 0.5 )
					this.x = ( this.x - 1 +  this.world.getWidth() ) % this.world.getWidth() ;
				else
					if ( dice < 0.75 )
						this.y = ( this.y + 1 ) % this.world.getHeight() ;
					else
						this.y = ( this.y - 1 +  this.world.getHeight() ) % this.world.getHeight() ;
		}
	} */
	
	public void perte_fatigue() {
		fatigue = fatigue--;
	}
	
	public void perte_energie() {
		energie--;
	}
	
	/*public int etat_fatigue() {
		return fatigue;
	}
	
	public int etat_energie() {
		return energie;
	}*/
	
	public void recuperation_fatigue(int fat) {
		fatigue = (fatigue + fat)%(MAX_FATIGUE+2);
	}
		
	public void recuperation_nrj(int nrj) {
		energie = (energie + nrj)%(MAX_NRJ+1);
	}
	
	public double distance(Agent a) {
		return Math.sqrt(Math.pow(2,(this.x - a.x)) + Math.pow(2, (this.y - a.y)));
	}
	
	public Agent PlusProcheVoisin(int d, World _w) {
		Agent voisin = null;
		double dist_min = Double.POSITIVE_INFINITY;		
		for (UniqueDynamicObject agent : _w.getUniqueDynObject()) {
			if (dist_min > this.distance((Agent) agent) && this.distance((Agent) agent) <= d) {
				dist_min = this.distance((Agent) agent);
				voisin = (Agent)agent;
			}
			//else {
			//	voisin = null;
			//}
		}
		return voisin;
	}

    public abstract void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight);
   // {

        // display a monolith
        
        //gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
        
    	/*int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();
		*/
    	//float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	

   // }
}
