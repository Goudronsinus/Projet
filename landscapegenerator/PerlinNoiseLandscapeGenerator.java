// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package landscapegenerator;

public class PerlinNoiseLandscapeGenerator {

	public static void noise (double[][] tab, int dimX, int dimY){
		for (int x = 0 ; x < dimX ; x++) {
			for (int y = 0 ; y < dimY ; y++) {
				tab[x][y] = Math.random();
			}
		}
	}
	
	public static double noise_3d(int x, int y, int z, double[][] tab)
	{
	    return tab[(x + z*tab.length*tab[0].length)%tab.length][(y*tab.length + z*tab.length*tab[0].length)%tab.length];
	}
	
	//Interpolation des valeurs situées entre p0 et p1
	//Nécessite deux points qui précèdent (resp. succèdent)
	//à p1 (rep. p2).
	public static double cubic_interpolate (double before_p0, double p0,
            double p1, double after_p1, double t) {
		//Calcul des coefficients de notre polynôme
	    double a3 = -0.5f*before_p0 + 1.5f*p0 - 1.5f*p1 + 0.5f*after_p1;
	    double a2 = before_p0 - 2.5f*p0 + 2.f*p1 - 0.5f*after_p1;
	    double a1 = -0.5f*before_p0 + 0.5f*p1;
	    double a0 = p0;

	    //Calcul de la valeur de ce polynôme en t
	    return (a3 * t*t*t) + (a2 * t*t) + (a1 * t) + a0;
	}
	
	public static double smooth_noise(double x, double y, /*double z*/ double[][] tab)
	{
	  //Partie entière : E(x)
	  int integer_x = (int)x;
	  int integer_y = (int)y;
	  int integer_z = (int)tab[(int)x][(int)y]/*z*/;
	  //Partie fractionnaire : x - E(x)
	  double fractional_x = x - integer_x;
	  double fractional_y = y - integer_y;
	  double fractional_z = tab[integer_x][integer_y]/*z*/ - integer_z;

	  //Bruit des quatre points d'un cube
	  double a0 = noise_3d(integer_x,     integer_y,     /*tab[integer_x][integer_y]*/integer_z, tab);
	  double a1 = noise_3d(integer_x + 1, integer_y,     /*tab[integer_x + 1][integer_y]*/integer_z, tab);

	  double b0 = noise_3d(integer_x,     integer_y + 1, /*tab[integer_x][integer_y + 1]*/integer_z, tab);
	  double b1 = noise_3d(integer_x + 1, integer_y + 1, /*tab[integer_x + 1][integer_y + 1]*/integer_z, tab);

	  double c0 = noise_3d(integer_x,     integer_y,     /*tab[integer_x][integer_y]*/integer_z + 1, tab);
	  double c1 = noise_3d(integer_x + 1, integer_y,     /*tab[integer_x + 1][integer_y]*/integer_z + 1, tab);

	  double d0 = noise_3d(integer_x,     integer_y + 1, /*tab[integer_x][integer_y + 1]*/integer_z + 1, tab);
	  double d1 = noise_3d(integer_x + 1, integer_y + 1, /*tab[integer_x + 1][integer_y + 1]*/integer_z + 1, tab);

	  //Interpolation sur la face inférieure du cube :
	  double a = linear_interpolate(a0, a1, fractional_x);
	  double b = linear_interpolate(b0, b1, fractional_x);
	  double v = linear_interpolate(a, b, fractional_y);
	  //Interpolation sur la face supérieure du cube :
	  double c = linear_interpolate(c0, c1, fractional_x);
	  double d = linear_interpolate(d0, d1, fractional_x);
	  double w = linear_interpolate(c, d, fractional_y);

	  //Interpolation entre les points
	  // situés sur chacune des deux faces :
	  return linear_interpolate(v, w, fractional_z);
	}
	
	public static double linear_interpolate(double a, double b, double t)
	{
	    return (1. - t) * a + t * b;
	}

	public static double[][] generatePerlinNoiseLandscape ( int dxView, int dyView, double scaling, double landscapeAltitudeRatio, int perlinLayerCount )
    {
    	double landscape[][] = new double[dxView][dyView];
    	
    	noise(landscape, dxView, dyView);
    	for (int i = 0 ; i<perlinLayerCount ; i++) {
    	for (int x = 0 ; x < dxView ; x++) {
    		for (int y = 0 ; y < dyView ; y++) {
    			landscape[x][y] = cubic_interpolate(landscape[(x-2+dxView)%dxView][y/*(y-2+dyView)%dyView*/], landscape[(x-1+dxView)%dxView][y/*(y-1+dyView)%dyView*/],
    					landscape[(x+1)%dxView][y/*(y+1)%dyView*/], landscape[(x+2)%dxView][y/*(y+2)%dyView*/], landscape[x][y]);
    			//System.out.println("landscape["+x+", "+y+"] = "+landscape[x][y]);
    			
    		}
    	}
    	}
    	/*for (int x = 0; x < dxView; x++)
    		for (int y = 0; y < dyView; y++)
    			landscape[x][y] = smooth_noise(x, y, landscape);
    	*/// A ECRIRE ! 
    	// ...
    	/*for ( int x = 0 ; x < dxView ; x++ )
    		for ( int y = 0 ; y < dyView ; y++ )
    			landscape[x][y] = Math.random();
    	*/
    	// ...
    	// cf. http://freespace.virgin.net/hugo.elias/models/m_perlin.htm pour une explication


    	// scaling and polishing
    	landscape = LandscapeToolbox.scaleAndCenter(landscape, scaling, landscapeAltitudeRatio);
    	landscape = LandscapeToolbox.smoothLandscape(landscape);
    	
		return landscape;
    }
}
    //http://cochoy-jeremy.developpez.com/tutoriels/2d/introduction-bruit-perlin/
    
    //public static double[][] generatePerlinNoiseLandscape2 ( int dxView, int dyView, double scaling, double landscapeAltitudeRatio, int perlinLayerCount )
    //{
    //	double landscape[][] = new double[dxView][dyView];
    	/*int perm[] = {
    	    //0 à 256
    	    151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,
    	    36,103,30,69,142,8,99,37,240,21,10,23,190,6,148,247,120,234,
    	    75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,88,237,
    	    149,56,87,174,20,125,136,171,168,68,175,74,165,71,134,139,
    	    48,27,166,77,146,158,231,83,111,229,122,60,211,133,230,220,
    	    105,92,41,55,46,245,40,244,102,143,54,65,25,63,161,1,216,80,
    	    73,209,76,132,187,208,89,18,169,200,196,135,130,116,188,159,
    	    86,164,100,109,198,173,186,3,64,52,217,226,250,124,123,5,
    	    202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,
    	    182,189,28,42,223,183,170,213,119,248,152,2,44,154,163,70,
    	    221,153,101,155,167,43,172,9,129,22,39,253,19,98,108,110,79,
    	    113,224,232,178,185,112,104,218,246,97,228,251,34,242,193,
    	    238,210,144,12,191,179,162,241,81,51,145,235,249,14,239,107,
    	    49,192,214,31,181,199,106,157,184,84,204,176,115,121,50,45,
    	    127,4,150,254,138,236,205,93,222,114,67,29,24,72,243,141,
    	    128,195,78,66,215,61,156,180,
    	    //257 à 512
    	    151,160,137,91,90,15,131,13,201,95,96,53,194,233,7,225,140,
    	    36,103,30,69,142,8,99,37,240,21,10,23,190,6,148,247,120,234,
    	    75,0,26,197,62,94,252,219,203,117,35,11,32,57,177,33,88,237,
    	    149,56,87,174,20,125,136,171,168,68,175,74,165,71,134,139,
    	    48,27,166,77,146,158,231,83,111,229,122,60,211,133,230,220,
    	    105,92,41,55,46,245,40,244,102,143,54,65,25,63,161,1,216,80,
    	    73,209,76,132,187,208,89,18,169,200,196,135,130,116,188,159,
    	    86,164,100,109,198,173,186,3,64,52,217,226,250,124,123,5,
    	    202,38,147,118,126,255,82,85,212,207,206,59,227,47,16,58,17,
    	    182,189,28,42,223,183,170,213,119,248,152,2,44,154,163,70,
    	    221,153,101,155,167,43,172,9,129,22,39,253,19,98,108,110,79,
    	    113,224,232,178,185,112,104,218,246,97,228,251,34,242,193,
    	    238,210,144,12,191,179,162,241,81,51,145,235,249,14,239,107,
    	    49,192,214,31,181,199,106,157,184,84,204,176,115,121,50,45,
    	    127,4,150,254,138,236,205,93,222,114,67,29,24,72,243,141,
    	    128,195,78,66,215,61,156,180,};*/

    /*	int _grad3[][] = { //Le cube
 		    {1,1,0},{-1,1,0},{1,-1,0},{-1,-1,0},
   		    {1,0,1},{-1,0,1},{1,0,-1},{-1,0,-1},
   		    {0,1,1},{0,-1,1},{0,1,-1},{0,-1,-1},
   		    //Ainsi qu'un tétraèdre supplémentaire
   		    {1,1,0},{-1,1,0},{0,-1,1},{0,-1,-1}};
    */	
    	
    	
    //	return landscape;
    //}
    
    
  ////
  //Fonction : Produit scalaire
  ////
  //Effectue le produit scalaire du vecteur V(v[0], v[1], v[2])
  // avec U(x, y, z).
  //Cette fonction nous servira à calculer le produit scalaire
  // entre nos gradients de couleur et nos vecteurs dirigés
  // vers notre point.
   // double fast_dot(int[] v, double x, double y, double z){
     //   return v[0] * x + v[1] * y + v[2] * z;
  //  }

  ////
  //Fonction : Obtention du gradient pour un point P(x, y, z)
//              de l'espace
  ////
 /* int get_grad(int x, int y, int z)
  {
      //Calcul un bruit aléatoire de 3 variables, via la table
      // de permutation.
      int rand_value = perm[z + perm[y + perm[x]]];
      //Applique un modulo 16 à cette valeur pour obtenir un
      // gradient de couleur, puis renvoie un pointeur sur
      // cette élément.
      return _grad3[rand_value & 15];
  }*/
    
    
    

    
//}


