package climat;

import worlds.World;

public class Printemps extends Saison {
	public final double PRINT_MAX = 16.75;
	public final double PRINT_MOY = 11.375;
	public final double PRINT_MIN = 8.5;
	public final double PRINT_N = (9*60+58)*1000;
	public final double PRINT_J = (24*60*1000) - PRINT_N;	
	
	
	public Printemps(String nomS, World w) {
		super(nomS, 92, w);
		probaPluie = 0.516;
		_myworld.setProbaInc(World.getProbaInc()*0.3);
		_myworld.setPobaRep(World.getProbaRep()*50);
		nbJour = 0;
		day[nbJour] = new Day(PRINT_MAX, PRINT_MIN, PRINT_MOY, PRINT_N, PRINT_J);
		checkProbaInc(0.0000001, 0.0001, 0.00001);
		checkProbaRep(0.000001, 0.001, 0.0001);
	}



	@Override
	public void jourSuivant() throws IndexOutOfBoundsException {
		nbJour++;
		
		if(day[nbJour-1].getJour()) {
			day[nbJour] = new Day(PRINT_MAX, PRINT_MIN, day[nbJour-1].getTemp(), PRINT_N, PRINT_J,false);
		}
		else {day[nbJour] = new Day(PRINT_MAX, PRINT_MIN, day[nbJour-1].getTemp(), PRINT_N, PRINT_J,true);}
		
		
	}

}