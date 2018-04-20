	// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import graphics.Landscape;

public class MyEcosystem {
    
	public static void main(String[] args) {

		WorldOfTrees myWorld = new WorldOfTrees();
		
		// param�tres:
		// 1: le "monde" (ou sont dŽfinis vos automates cellulaires et agents
		// 2: (ca dŽpend de la mŽthode : gŽnŽration alŽatoire ou chargement d'image)
		// 3: l'amplitude de l'altitude (plus la valeur est ŽlevŽe, plus haute sont les montagnes)
		// 4: la quantitŽ d'eau
		String [] land = new String[6];
		land [0] = "108381.png";
		land[1] = "220px-PerlinNoise2d.png";
		land[2] = "landscape_canyon-128.png";
		land[3] = "landscape_default-200.png";
		land[4] = "landscape_default2-128.png";
		land[5] = "landscape_paris-200.png";
		
		//Landscape myLandscape = new Landscape(myWorld, 128, 128, 0.1, 0.7);
		//Landscape myLandscape = new Landscape(myWorld, "landscape_default-200.png", 0.4, 0.5);
		Landscape myLandscape = new Landscape(myWorld, land[(int)(Math.random()*6)],0.4, 0.5);
		Landscape.run(myLandscape);
    }

}
