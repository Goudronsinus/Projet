package applications.simpleworld;

import javax.media.opengl.GL2;

import worlds.World;

public abstract class Prey extends Agent {
	
	boolean reproduction = false;
	
	public Prey (int _x, int _y, World _w){
		super(_x, _y, _w);
	}
	
	public abstract void deplacement();
		
	public void step() {
		if (!this.estVivant()) {
			world.getUniqueDynObject().remove(this);
			System.out.println("Remove Prey \n");
			return;
		}
		if (this.reproduction) {
			if (this instanceof Poisson) {
				world.getUniqueDynObject().add(new Poisson((int)(Math.random()*world.getWidth()), 
						(int)(Math.random()*world.getHeight()), world));
			}
		}
		
		if (super.fatigue >= 50 && energie > 0) {
			//System.out.println("Prey is alive \n");
			this.deplacement();
		}
		else if (super.fatigue < 50 && super.fatigue >= 25 && energie > 0) {
			if (world.getIteration() % 2 == 0) {
				System.out.println("Prey is tired \n");
				super.recuperation_fatigue(3);
				this.deplacement();
			}
			
		}
		else if (super.fatigue < 25 && energie > 0){
			super.perte_energie();
			super.recuperation_fatigue(5);
			if (world.getIteration() % 5 == 0) {
				System.out.println("Prey is a zombie \n");
				this.deplacement();
			}
		}
		else {
			System.out.println("Prey is dead\n");
			this.mort();
		}
	}
	
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, 
			float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
    {

        // display a monolith
        
        //gl.glColor3f(0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()),0.f+(float)(0.5*Math.random()));
        
    	int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();

    	float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	
        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        
        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);

        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(1.0f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 5.f);
        
      
    }
	
}
