package applications.simpleworld;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;
import worlds.World;

public class Prey extends Agent {
	
	boolean sens = true;
	
	public Prey (int _x, int _y, World _w){
		super(_x, _y, _w);
	}
	
	public void step() {
		if (!this.estVivant()) {
			System.out.println("remove Proie");
			world.getUniqueDynObject().remove(this);
			return;
		}
		int maxWidth = super.world.getWidth();
		int maxHeight = super.world.getHeight();
		Agent voisin = this.PlusProcheVoisin(super.world);
		this.chasse(super.world);
		this.reproduction(super.world);
		if (voisin instanceof Predator) {
			this.x = (int)(this.x + Math.signum(this.x - voisin.getX())+maxWidth)%maxWidth;
			this.y = (int)(this.y + Math.signum(this.y - voisin.getY())+maxHeight)%maxHeight;
		}
		else if (voisin instanceof Prey) {
			if (sens){
				this.x = (int)(this.x - (Math.signum(this.x - voisin.getX())) + maxWidth)%maxWidth;
				this.y = (int)(this.y - (Math.signum(this.y - voisin.getY())) + maxHeight)%maxHeight;
			}
		}
		else {
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
		
	}
	
	public void chasse(World _w) {
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Predator){
				if (a.getX() == this.getX() && a.getY() == this.getY()){
					System.out.println("Proie a été mangée par Predator");
					this.mort();
				}
			}
		}
	}
	
	public void reproduction(World _w){
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		int nb_tour = 0;
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Prey){
				if (a.getX() == this.getX() && a.getY() == this.getY()){
					//System.out.println("Deux proie en ["+this.getX()+", "+this.getY()+"]\t ; \t nb_tour = "+nb_tour);
					nb_tour++;
				}
			}
			if (nb_tour >= 3){
				sens = false;
				System.out.println("Reproduction Prey");
				//listeAgents.add(new Prey( (int)(Math.random()*_w.getHeight()), (int)(Math.random()*_w.getWidth()), _w ));
			}
		}
	}
	
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
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
