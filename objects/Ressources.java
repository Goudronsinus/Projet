package objects;

import java.util.ArrayList;

import applications.simpleworld.*;
//import worlds.World;


public class Ressources {
	
	WorldOfTrees __w;
		
	ArrayList<Object> ListeRessources;
	
	public ArrayList<Object> init_List() {
		int[] tab;  		
		tab = new int[3];
		
		for (int x = 0; x < __w.getHeight() ; x++) {
			for(int y = 0; y < __w.getWidth() ; y++) {
				
				if (__w.getCellValue(x, y) == -1) {
					tab[0] = x;
					tab[1] = y;
					tab[2] = (int)(Math.random()*100);
					
					ListeRessources.add(tab);
				}
				
			}
		}
		
		return ListeRessources;
	}
	
	public void step() {
		
	}
	
}
