// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import cellularautomata.CellularAutomataDouble;
import cellularautomata.CellularAutomataInteger;
import worlds.World;

public class ForestCA extends CellularAutomataInteger {

	CellularAutomataDouble _cellsHeightValuesCA;
	
	World world;
	
	public ForestCA ( World __world, int __dx , int __dy, CellularAutomataDouble cellsHeightValuesCA )
	{
		super(__dx,__dy,true ); // buffering must be true.
		
		_cellsHeightValuesCA = cellsHeightValuesCA;
		
		this.world = __world;
	}
	
	public void init()
	{
		for ( int x = 0 ; x != _dx ; x++ )
    		for ( int y = 0 ; y != _dy ; y++ )
    		{
    			/*if ( _cellsHeightValuesCA.getCellState(x,y) >= 0.2*world.getMaxEverHeight()  &&
    				_cellsHeightValuesCA.getCellState(x,y) <= 0.8*world.getMaxEverHeight())
    			{
    				if ( Math.random() <= 0.53*world.getMaxEverHeight() ) // was: 0.71
    					this.setCellState(x, y, 1); // tree
    				else
    					this.setCellState(x, y, 0); // empty
    			}
    			else
    			{
    				this.setCellState(x, y, -1); // water (ignore)
    			}*/
    			

    			if ( _cellsHeightValuesCA.getCellState(x,y) >= 0.2 * world.getMaxEverHeight()
    					&& _cellsHeightValuesCA.getCellState(x,y) <= 0.9*world.getMaxEverHeight())
    			{
    				if ( Math.random() >= _cellsHeightValuesCA.getCellState(x,y) * 30*world.getMaxEverHeight() ) // was: 0.71
    				{
    					//if (Math.random() > 0.65)
    						this.setCellState(x, y, 1); // tree
    					//else
    						//this.setCellState(x, y, 12); // ble						
    				}
    				else if ( Math.random() >= _cellsHeightValuesCA.getCellState(x,y) * 10*world.getMaxEverHeight() )
    					this.setCellState(x, y, 12);
    					
    				else
    					this.setCellState(x, y, 0); // empty
    			}
    			else
    			{
    				this.setCellState(x, y, -1); // water (ignore)
    			}
    		}
    	this.swapBuffer();

	}

	public void step()
	{
    	for ( int i = 0 ; i != _dx ; i++ )
    		for ( int j = 0 ; j != _dy ; j++ )
    		{
    			if ( this.getCellState(i, j) >= 0 && getCellState(i, j) <= 11)
    			{
	    			if ( this.getCellState(i,j) == 1 ) // tree?
	    			{
	    				// check if neighbors are burning
	    				if ( 
	    						(this.getCellState( (i+_dx-1)%(_dx) , j ) == 2 ||
	    						this.getCellState( (i+_dx+1)%(_dx) , j ) == 2 ||
	    						this.getCellState( i , (j+_dy+1)%(_dy) ) == 2 ||
	    						this.getCellState( i , (j+_dy-1)%(_dy) ) == 2 ) ||
	    						( (Math.random() < 0.00001) && 
	    						( this.getCellState( (i+_dx-1)%(_dx) , (j+_dy-1)%(_dy)) == 3 ||
	    						this.getCellState( (i+_dx-1)%(_dx) , (j+_dy+1)%(_dy) ) == 3 ||
	    						this.getCellState( (i+_dx+1)%(_dx), (j+_dy+1)%(_dy) ) == 3 ||
	    						this.getCellState( (i+_dx+1)%(_dx) , (j+_dy-1)%(_dy) ) == 3 )) 		// Proba vent
	    					)
	    				{
	    					this.setCellState(i,j,2);
	
	    				}
	    				else {
	    					if ( Math.random() < 0.00001 ) // spontaneously take fire ?
	    					{
	    						this.setCellState(i,j,2);
	    					}
	    					else
	    					{
	    						this.setCellState(i,j,1); // copied unchanged
	    					}
	    				}
	    			}
	    			else
	    			{
	        				switch (this.getCellState( i , j ))  // burning?
	        				{
	        				case 0 :
	        					if(Math.random() < 0.000001) {
	        						setCellState(i, j, 1);
	        					}
	        					else {
	        						setCellState(i, j, getCellState(i, j));
	        					}
	        					break;
	        					
	        				case 2: 
	        					this.setCellState(i,j,3); // burnt
	        					break;
	        				case 3:
	        					this.setCellState(i,j,4);
	        					break;
	        				case 4 :
	        					this.setCellState(i,j,5);
	        					break;
	        				case 5:
	        					this.setCellState(i,j,6);
	        					break;
	        				case 6 : 
	        					this.setCellState(i,j,7);
	        					break;
	        				case 7 : 
	        					this.setCellState(i,j,8);
	        					break;
	        				case 8:
	        					this.setCellState(i,j,9);
	       						break;
	       					case 9:
	       						this.setCellState(i,j,10);
	       						break;
	       					case 10:
	       						this.setCellState(i,j,11);
	       						break;
	       					case 11: 
	       						this.setCellState(i,j,0);
	       						break;
	       					/*case 12:
	       						this.setCellState(i, j, 12);*/
	       					default:
	       						this.setCellState(i, j, this.getCellState(i,j));
	       				}
	    			}
	    			
	    			float color[] = new float[3];
	    			//float height = (float) world.getCellHeight(i, j);
	    			switch ( this.getCellState(i, j) )
	    			{
	    			
			        case 0:
	    				color[0] = 0.f;
	    				color[1] = 0.8f;
	    				color[2] = 0.2f;
	    				break;
	    			case 1:
	    				color[0] = 0.f;
	    				color[1] = 0.3f;
	    				color[2] = 0.f;
	    				break;
	    			case 2: // burning tree
	    				color[0] = 1.f;
	    				color[1] = 0.f;
	    				color[2] = 0.f;
	    				break;
	    			case 3: // incandescent tree
	    				color[0] = 0.9f;
	    				color[1] = 0.1f;
	    				color[2] = 0.1f;
	    				break;
	    			case 4 : 
	    				color[0] = 0.8f;
	    				color[1] = 0.1f;
	    				color[2] = 0.1f;
	    				break;
	    			case 5:
	    				color[0] = 0.7f;
	    	    		color[1] = 0.2f;
	    	    		color[2] = 0.2f;
	    	    		break;
	    			case 6:
	    				color[0] = 0.6f;
	    	    		color[1] = 0.3f;
	    	    		color[2] = 0.3f;
	    	    		break;
	    			case 7:
	    				color[0] = 0.5f;
	    	    		color[1] = 0.5f;
	    	    		color[2] = 0.5f;
	    	    		break;
	    			case 8: 
	    					color[0] = 0.4f;
	    	    		color[1] = 0.4f;
	    	    		color[2] = 0.4f;
	    	    		break;
	    			case 9:
	    				color[0] = 0.3f;
	    	    		color[1] = 0.3f;
	    	    		color[2] = 0.3f;
	    	    		break;
	    			case 10:
	    				color[0] = 0.2f;
	    	    		color[1] = 0.2f;
	    	    		color[2] = 0.2f;
	    	    		break;
	    			case 11:
	    				color[0] = 0.f;
	    				color[1] = 0.f;
	    				color[2] = 0.f;
	    				break;
	    			case 12:
	    				color[0] = 0.6f;
	    				color[1] = 0.5f;
	    				color[2] = 0.1f;
	    			default :
	    				System.out.print("cannot interpret CA state: " + this.getCellState(i, j));
	    				System.out.println(" (at: " + i + "," + j + " -- height: " + this.world.getCellHeight(i,j) + " )");
	    		} 
	    		this.world.cellsColorValues.setCellState(i, j, color);
    		}
    	}
    	this.swapBuffer();
	}

}
