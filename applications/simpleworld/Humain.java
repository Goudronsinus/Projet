package applications.simpleworld;

import java.util.ArrayList;

import javax.media.opengl.GL2;

import objects.UniqueDynamicObject;
import worlds.World;

public class Humain extends Agent {
boolean reproduction = false;
	
	public Humain (int _x, int _y, World _w){
		super(_x, _y, _w);
		
		//_w.setCellValue(_x, _y, 13);
	}
	
	public void deplacement(){
		int maxWidth = super.world.getWidth();
		int maxHeight = super.world.getHeight();
		Agent voisin = this.PlusProcheVoisin(20, super.world);
		this.chasse(super.world);
		this.reproduction(super.world);
		
		if (!reproduction && voisin instanceof Humain) {
			this.x = (int)(this.x - (Math.signum(this.x - voisin.getX())) + maxWidth)%maxWidth;
			this.y = (int)(this.y - (Math.signum(this.y - voisin.getY())) + maxHeight)%maxHeight;
		}
		
		if (voisin instanceof Predator || voisin instanceof Prey) {
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
	}
	
	public void reproduction(World _w) {
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		int nb_tour = 0;
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Humain){
				if (a.getX() == this.getX() && a.getY() == this.getY() && this.male != ((Humain)a).male){
					nb_tour++;
				}			
				if (nb_tour >= 5 && Math.random() >= 0.75) {
					nb_tour = 0;
					reproduction = true;
					nb_tour = nb_tour%3;
					System.out.println("Reproduction Humain");
					_w.setCellValue(this.x, this.y, 4);
				}
			}
		}
	}
	
	public void chasse (World w) {
		ArrayList<UniqueDynamicObject> listeAgents = w.getUniqueDynObject();
		
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Predator || a instanceof Prey){
				if (a.getX() == this.getX() && a.getY() == this.getY()){
					System.out.println("Humain a mange");
					a.mort();
					this.recuperation_nrj(MAX_NRJ);
					this.recuperation_fatigue(MAX_FATIGUE);
				}
			}
		}
		this.perte_fatigue();
	}
	
	public void step() {
		if (!this.estVivant()) {
			world.getUniqueDynObject().remove(this);
			return;
		}
		if (this.reproduction) {
			world.getUniqueDynObject().add(new Humain(this.x, this.y, world));

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
			System.out.println("Humain is dead\n");
			this.mort();
		}
	}
		
	public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, 
			float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight)
    {

        // display a monolith
        
        gl.glColor3f(0.8f, 0.9f, 0.5f);
        
    	int x2 = (x-(offsetCA_x%myWorld.getWidth()));
    	if ( x2 < 0) x2+=myWorld.getWidth();
    	int y2 = (y-(offsetCA_y%myWorld.getHeight()));
    	if ( y2 < 0) y2+=myWorld.getHeight();

    	float height = Math.max ( 0 , (float)myWorld.getCellHeight(x, y) );
    	
    	
    	//derriere
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);
        
        //devant
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        
        //gauche
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        
        //droite
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight);

        
        //Au dessus
        gl.glColor3f(0.5f,0.9f,0.9f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX-3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY+3*lenY, height*normalizeHeight + 7.f);
        gl.glVertex3f( offset+x2*stepX+3*lenX, offset+y2*stepY-3*lenY, height*normalizeHeight + 7.f);
      
    }
}
