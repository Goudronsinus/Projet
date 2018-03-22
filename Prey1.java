package applications.simpleworld;

import java.util.ArrayList;

import objects.UniqueDynamicObject;
import worlds.World;

public class Prey1 extends Agent {
	
	public Prey1 (int _x, int _y, World _w){
		super(_x, _y, _w);
	}
	
	public void chasse(World _w) {
		ArrayList<UniqueDynamicObject> listeAgents = _w.getUniqueDynObject();
		
		for (UniqueDynamicObject a : listeAgents){
			if (a instanceof Predator1){
				if (a.getX() == this.getX() && a.getY() == this.getY()){
					this.mort();
				}
			}
		}
	}
	
}
