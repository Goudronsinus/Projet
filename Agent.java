package applications.simpleworld;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;

import worlds.World;

public abstract class Agent extends UniqueDynamicObject{
	
	boolean alive;
	
	public Agent ( int __x , int __y, World __world )
	{
		super(__x,__y,__world);
		
		alive = true;
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
	
	public double distance(Agent a) {
		return Math.sqrt(Math.pow(2,(this.x - a.x)) + Math.pow(2, (this.y - a.y)));
	}
	
	public Agent PlusProcheVoisin(World _w) {
		Agent voisin = null;
		double dist_min = Double.POSITIVE_INFINITY;		
		for (UniqueDynamicObject agent : _w.getUniqueDynObject()) {
			if (dist_min > this.distance((Agent) agent)) {
				dist_min = this.distance((Agent) agent);
				voisin = (Agent)agent;
			}
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
