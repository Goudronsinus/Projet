// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import javax.media.opengl.GL2;

import objects.*;
import objects.BridgeBlock;
import objects.Monolith;
import worlds.World;

public class WorldOfTrees extends World {

    protected ForestCA2 cellularAutomata;

    public void init ( int __dxCA, int __dyCA, double[][] landscape )
    {
    	super.init(__dxCA, __dyCA, landscape);
    	
    	// add colors
    	
    	for ( int x = 0 ; x < __dxCA ; x++ )
    		for ( int y = 0 ; y < __dyCA ; y++ )
    		{
	        	float color[] = new float[3];

	        	float height = (float) this.getCellHeight(x, y);
		    	
		        if ( height >= 0.2*this.getMaxEverHeight()  && height <= 0.45*this.getMaxEverHeight()  )
		        {
		        	// green mountains
		        	/**/
		        	color[0] = height / ( (float)this.getMaxEverHeight() );
					color[1] = 0.9f + 0.1f * height / ( (float)this.getMaxEverHeight() );
					color[2] = height / ( (float)this.getMaxEverHeight() );
					/**/
					this.setCellValue(x, y, -4);
		        }
		        else if (height > 0.45*this.getMaxEverHeight() )
		        {
		        	// snowy mountains
		        	
		        	color[0] = height / (float)this.getMaxEverHeight();
					color[1] = height / (float)this.getMaxEverHeight();
					color[2] = height / (float)this.getMaxEverHeight();
					/**/
					this.setCellValue(x, y, -3);
		        }
		        else if (height >= 0 && height < 0.2*this.getMaxEverHeight() )
		        {
		        	//sand
		        	/**/
		        	color[0] = 0.7f + 0.1f * height / ( (float)this.getMaxEverHeight() );
		        	color[1] = 0.7f + 0.1f * height / ( (float)this.getMaxEverHeight() );
		        	color[2] = 0.5f + 0.1f * height / ( (float)this.getMaxEverHeight() );
		        	/**/
		        	this.setCellValue(x, y, -2);
		        }
		        else
		        {
		        	// water
					color[0] = -height;
					color[1] = -height;
					color[2] = 1.f;
					
					this.setCellValue(x, y, -1);
		        }
		        this.cellsColorValues.setCellState(x, y, color);
    		}
    	
    	// add some objects
    	for ( int i = 0 ; i < 11 ; i++ )
    	{
    		if ( i%10 == 0 )
    			uniqueObjects.add(new Monolith(110,110+i,this));
    		else
    			uniqueObjects.add(new BridgeBlock(110,110+i,this));
    	}
    	int cptR = 0;
    	int cptP = 0;
    	int cptJ = 0;
    	for (int x = 0; x < __dxCA ; x++) {
    		for (int y = 0; y < __dyCA ; y++) {
    			if (this.getCellValue(x, y) == -1) {
    				if (cptR != 6 && Math.random() >= 0.5) {
    					uniqueDynamicObjects.add(new Requin(x, y, this));
    					cptR++;
    				}
    				else if (cptP != 6 && Math.random() >= 0.5) {
    					uniqueDynamicObjects.add(new Poisson(x, y, this));
    					cptP++;
    				}
    			}
    			else {
    				if (cptJ != 6 && Math.random() >= 0.5) {
    					uniqueDynamicObjects.add(new Jaguar(x, y, this));
    					cptJ++;
    				}
    			}
    		}
    	}
    	for (int i = 0; i < 6 ; i++ ){
    	//	uniqueDynamicObjects.add(new Prey((int)(Math.random()*__dxCA), (int)(Math.random()*__dyCA),this));
    	//	uniqueDynamicObjects.add(new Predator((int)(Math.random()*__dxCA),(int)(Math.random()*__dyCA),this));
    		uniqueDynamicObjects.add(new Requin((int)(Math.random()*__dxCA), (int)(Math.random()*__dyCA), this));
    		uniqueDynamicObjects.add(new Jaguar((int)(Math.random()*__dxCA), (int)(Math.random()*__dyCA), this));
    		uniqueDynamicObjects.add(new Poisson((int)(Math.random()*__dxCA), (int)(Math.random()*__dyCA), this));
    	}
    }
    
    protected void initCellularAutomata(int __dxCA, int __dyCA, double[][] landscape)
    {
    	cellularAutomata = new ForestCA2(this,__dxCA,__dyCA,cellsHeightValuesCA);
    	cellularAutomata.init();
    }
    
    protected void stepCellularAutomata()
    {
    	if ( iteration%10 == 0 )
    		cellularAutomata.step();
    }
    
    protected void stepAgents()
    {
    	// nothing to do.
    	if( iteration % 10 == 0)
    	for ( int i = 0 ; i < this.uniqueDynamicObjects.size() ; i++ )
    	{
    		this.uniqueDynamicObjects.get(i).step();
    	}
    }

    public int getCellValue(int x, int y) // used by the visualization code to call specific object display.
    {
    	return cellularAutomata.getCellState(x%dxCA,y%dyCA);
    }

    public void setCellValue(int x, int y, int state)
    {
    	cellularAutomata.setCellState( x%dxCA, y%dyCA, state);
    }
    
	public void displayObjectAt(World _myWorld, GL2 gl, int cellState, int x,
			int y, double height, float offset,
			float stepX, float stepY, float lenX, float lenY,
			float normalizeHeight) 
	{
		switch ( cellState )
		{
		case 1: // trees: green, fire, burnt
		case 2:
		case 3:
			Tree.displayObjectAt(_myWorld,gl, cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
		//case 5:
			//Ble.displayObjectAt(_myWorld, gl, cellState, x, y, height, offset, stepX, stepY, lenX, lenY, normalizeHeight);
		default:
			// nothing to display at this location.
		}
	}

	//public void displayObject(World _myWorld, GL2 gl, float offset,float stepX, float stepY, float lenX, float lenY, float heightFactor, double heightBooster) { ... } 
    
   
}
