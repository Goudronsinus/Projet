// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package landscapegenerator;

public class RandomLandscapeGenerator {

    /**
     * 
     */
	
	public static void filtre(double[][]tab, double[][]gauss, int x, int y, int dimX, int dimY){
		
		//double somme = 0.;
		
		for (int i = 0; i < 5 ; i++) {
			for (int j = 0; j < 5; j++) {
				tab[((x-2+i)+dimX)%dimX][((y-2+i)+dimY)%dimY] = gauss[i][j]*tab[((x-2+i)+dimX)%dimX][((y-2+i)+dimY)%dimY];
			}
		}
		//return somme/25;
	}
	
    public static double[][] generateRandomLandscape ( int dxView, int dyView, double scaling, double landscapeAltitudeRatio, int perlinLayerCount )
    {
    	double landscape[][] = new double[dxView][dyView];
    	
    	double gauss[][] = {{0,	0.06,	0.17,	0.6,	0},
    			{0.06,	1.3,	3.6,	1.3,	0.06},
    			{0.17,	3.6,	7,		3.6,	0.17},
    			{0.06,	1.3,	3.6,	1.3,	0.06},
    			{0,		0.06,	0.17,	0.06,	0}};
    	
    	
		for ( int i = 0 ; i != dxView ; i++ )
			for ( int j = 0 ; j != dxView ; j++ )
			{
				landscape[i][j] = Math.random();
			}
		
		for (int x = 0; x < dxView ; x++) {
			for (int y = 0; y < dyView ; y++) {
				filtre(landscape, gauss, x, y, dxView, dyView);
			}
		}
		
		landscape = LandscapeToolbox.scaleAndCenter(landscape, scaling, landscapeAltitudeRatio);
		landscape = LandscapeToolbox.smoothLandscape(landscape);
		
		return landscape;
    }
    
}
