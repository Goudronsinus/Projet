
package applications.simpleworld;

import javax.media.opengl.GL2;

import worlds.World;

public abstract class Predator extends Agent{
	
	boolean reproduction = false;
	
	public Predator (int _x, int _y, World _w){
		super(_x, _y, _w);
		
		//_w.setCellValue(_x, _y, 13);
	}
	
	public abstract void deplacement();
	
	public abstract void chasse(World w);
	
	public void step() {
		if (!this.estVivant()) {
			world.getUniqueDynObject().remove(this);
			System.out.println("Remove Predator \n");
			return;
		}
		if (this.reproduction) {
			if (this instanceof Requin) {
				world.getUniqueDynObject().add(new Requin((int)(Math.random()*world.getWidth()),
						(int)(Math.random()*world.getHeight()), world));
			}
			if (this instanceof Jaguar) {
				world.getUniqueDynObject().add(new Jaguar(this.x, this.y, world));
			}
		}
		
		if (super.fatigue >= 50 && energie > 0) {
			this.deplacement();
		}
		else if (super.fatigue < 50 && super.fatigue >= 25 && energie > 0) {
			if (world.getIteration() % 2 == 0) {
				super.recuperation_fatigue(3);
				this.deplacement();
			}
			
		}
		else if (super.fatigue < 25 && energie > 0){
			super.perte_energie();
			super.recuperation_fatigue(5);
			if (world.getIteration() % 5 == 0) {
				this.deplacement();
			}
		}
		else {
			System.out.println("Pred is dead\n");
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
    	
    	
    	//derriere
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-2*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-2*lenY, height*normalizeHeight);
        
        //devant
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+2*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+2*lenY, height*normalizeHeight);
        
        //gauche
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-2*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+2*lenY, height*normalizeHeight);
        
        //droite
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+2*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-2*lenY, height*normalizeHeight + 4.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-2*lenY, height*normalizeHeight);

        
        //Au dessus
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-2*lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+2*lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+2*lenY, height*normalizeHeight + 5.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-2*lenY, height*normalizeHeight + 5.f);
      
    }


}