package applications.simpleworld;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;
import worlds.World;

public class Poisson extends Prey{
	
	public Poisson(int x, int y, World w) {
		super(x, y, w);
	}
	
	public void chasse(World _w) {
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Requin){
				if (a.getX() == this.getX() && a.getY() == this.getY() ){
					System.out.println("Poisson a été mangé Requin");
					this.mort();
				}
			}
		}
		this.perte_fatigue();
	}
	
	public void reproduction(World _w){
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		int nb_tour = 0;
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Poisson){
				if (a.getX() == this.getX() && a.getY() == this.getY() && this.male != ((Poisson) a).male){
					System.out.println("Deux poissons en ["+this.getX()+", "+this.getY()+"] ; \t nb_tour = "+nb_tour);
					nb_tour++;
				}
			
				if (nb_tour >= 3 && Math.random() >= 0.5){
					reproduction = true;
					nb_tour = nb_tour%3;
					System.out.println("Reproduction Poisson");
					_w.setCellValue(this.x, this.y, 4);
				}
			}
		}
	}
	
	public void deplacement() {
		int maxWidth = super.world.getWidth();
		int maxHeight = super.world.getHeight();
		Agent voisin = this.PlusProcheVoisin(20, super.world);
		this.chasse(super.world);
		this.reproduction(super.world);
		
		if (!reproduction && voisin instanceof Poisson) {
			this.x = (int)(this.x - (Math.signum(this.x - voisin.getX())) + maxWidth)%maxWidth;
			this.y = (int)(this.y - (Math.signum(this.y - voisin.getY())) + maxHeight)%maxHeight;
		}
		
		else if (voisin instanceof Requin || reproduction) {
			this.x = (int)(this.x + (Math.signum(this.x - voisin.getX())) + maxWidth)%maxWidth;
			this.y = (int)(this.y + (Math.signum(this.y - voisin.getY())) + maxHeight)%maxHeight;
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
    	
        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        
        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight);

        gl.glColor3f(1.f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 1.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight);

        gl.glColor3f(1.0f,1.f,0.4f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY-lenY, height*normalizeHeight + 2.f);
        gl.glVertex3f( offset+x2*stepX-lenX, offset+y2*stepY+lenY, height*normalizeHeight + 2.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY+lenY, height*normalizeHeight + 2.f);
        gl.glVertex3f( offset+x2*stepX+lenX, offset+y2*stepY-lenY, height*normalizeHeight + 2.f);
        
      
    }
	
}
