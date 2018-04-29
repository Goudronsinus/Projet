// ### WORLD OF CELLS ### 
// created by nicolas.bredeche(at)upmc.fr
// date of creation: 2013-1-12

package applications.simpleworld;

import graphics.Landscape;

public class MyEcosystem {
    
	public static void main(String[] args) {

		WorldOfTrees myWorld = new WorldOfTrees();
		
		// param�tres:
		// 1: le "monde" (ou sont d�finis vos automates cellulaires et agents
		// 2: (ca d�pend de la m�thode : g�n�ration al�atoire ou chargement d'image)
		// 3: l'amplitude de l'altitude (plus la valeur est �lev�e, plus haute sont les montagnes)
		// 4: la quantit� d'eau
		String [] land = new String[50];
		land [0] = "108381.png";
		land[1] = "220px-PerlinNoise2d.png";
		land[2] = "landscape_canyon-128.png";
		land[3] = "landscape_default-200.png";
		land[4] = "landscape-default2-12.png";
		land[5] = "landscape_paris-200.png";
		//Landscape myLandscape = new Landscape(myWorld, 128, 128, 0.5, 0.35);
		Landscape myLandscape = new Landscape(myWorld, land[3], 0.5, 0.3);	
		
		Landscape.run(myLandscape);
    }

}
