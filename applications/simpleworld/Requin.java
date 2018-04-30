package applications.simpleworld;
//import java.util.ArrayList;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;
//import objects.UniqueDynamicObject;
import worlds.World;


public class Requin extends Predator { 
	
	public Requin(int x, int y, World _w) {
		
		super(x, y, _w);

	}
	
	public void chasse(World _w) {
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Poisson){
				if (a.getX() == this.getX() && a.getY() == this.getY()){
					System.out.println("Requin a mange Poisson");
					a.mort();
					this.recuperation_nrj(MAX_NRJ);
					this.recuperation_fatigue(MAX_FATIGUE);
				}
			}
			if (a instanceof Humain && a.getX() == this.getX() && a.getY() == this.getY()) {
				System.out.println("Requin a ete mangé");
				this.mort();
			}
		}
		this.perte_fatigue();
	}
	
	
	public void reproduction(World _w){
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		int nb_tour = 0;
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Requin){
				if (a.getX() == this.getX() && a.getY() == this.getY() && this.male != ((Requin)a).male){
					nb_tour++;
				}			
				if (nb_tour >= 5 && Math.random() >= 0.75) {
					nb_tour = 0;
					reproduction = true;
					nb_tour = nb_tour%3;
					System.out.println("Reproduction Requin");
					_w.setCellValue(this.x, this.y, 4);
				}
			}
		}
	}
	
	public void deplacement() {
		int maxWidth = super.world.getWidth();
		int maxHeight = super.world.getHeight();
		Agent voisin = this.PlusProcheVoisin(10, super.world);
		this.chasse(super.world);
		this.reproduction(super.world);
		
		if (!reproduction && voisin instanceof Requin) {
			this.x = (int)(this.x - (Math.signum(this.x - voisin.getX())) + maxWidth)%maxWidth;
			this.y = (int)(this.y - (Math.signum(this.y - voisin.getY())) + maxHeight)%maxHeight;
		}
		
		if (voisin instanceof Poisson) {
			this.x = (int)(this.x - (Math.signum(this.x - voisin.getX())) + maxWidth)%maxWidth;
			this.y = (int)(this.y - (Math.signum(this.y - voisin.getY())) + maxHeight)%maxHeight;
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
		if (super.world.getCellValue(this.x, this.y) != -1) {
			if (super.world.getCellValue((this.x - 1 + maxHeight)%maxHeight, this.y) == -1) {
				this.x = (this.x - 1 + maxHeight)%maxHeight;
			}
			if (super.world.getCellValue(this.x, (this.y - 1 + maxWidth)%maxWidth) == -1) {
				this.y = (this.y - 1 + maxWidth)%maxWidth;
			}
			if (super.world.getCellValue((this.x + 1)%maxHeight, this.y) == -1) {
				this.x = (this.x + 1)%maxHeight;
			}
			if (super.world.getCellValue(this.x, (this.y + 1)%maxWidth) == -1) {
				this.y = (this.y + 1)%maxWidth;
			}
			if (super.world.getCellValue((this.x + 1)%maxHeight, (this.y + 1)%maxWidth) == -1) {
				this.x = (this.x + 1)%maxHeight;
				this.y = (this.y + 1)%maxWidth;
			}
			if (super.world.getCellValue((this.x + 1)%maxHeight, (this.y - 1 + maxWidth)%maxWidth) == -1) {
				this.x = (this.x + 1)%maxHeight;
				this.y = (this.y - 1 + maxWidth)%maxWidth;
			}
			if (super.world.getCellValue((this.x - 1 + maxHeight)%maxHeight, (this.y - 1 + maxWidth)%maxWidth) == -1) {
				this.x = (this.x - 1 + maxHeight)%maxHeight;
				this.y = (this.y - 1 + maxWidth)%maxWidth;
			}
			if (super.world.getCellValue((this.x - 1 + maxHeight)%maxHeight, (this.y + 1)%maxWidth) == -1) {
				this.x = (this.x - 1 + maxHeight)%maxHeight;
				this.y = (this.y + 1)%maxWidth;
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
    	
    	//derriere
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);
        
        //devant
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        
        //gauche
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        
        //droite
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);

        
        //Au dessus
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 2.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 2.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 2.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 2.f);
    }
	
}
