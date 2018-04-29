// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package objects;

import javax.media.opengl.GL2;

import worlds.World;

abstract public class UniqueDynamicObject // UniqueObject are object defined with particular, unique, properties (ex.: particular location)
{
	protected int x,y;
	protected World world;
	boolean alive;
	
	public UniqueDynamicObject(int __x, int __y, World __world) 
	{
		this.x = __x;
		this.y = __y;
		this.world = __world;
		alive = true;
	}
	
	abstract public void step();
	
	public int[] getCoordinate()
	{
		int coordinate[] = new int[2];
		coordinate[0] = this.x;
		coordinate[1] = this.y;
		return coordinate;
	}
	
	abstract public void displayUniqueObject(World myWorld, GL2 gl, int offsetCA_x, int offsetCA_y, float offset, float stepX, float stepY, float lenX, float lenY, float normalizeHeight );

	public int getY() {
		// TODO Auto-generated method stub
		return y;
	}
	
	public int getX() {
		return x;
	}

	public void mort() {
		// TODO Auto-generated method stub
		this.alive = false;
	}
	
	public boolean estVivant() {
		return alive == true;
	}

}
