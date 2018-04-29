package climat;

import worlds.World;

public class Automne extends Saison {
	public final double AUT_MAX = 21;
	public final double AUT_MOY = 10.375;
	public final double AUT_MIN = 5;
	public final double AUT_N = (13*60+39)*1000;
	public final double AUT_J = (24*60*1000)-AUT_N;
	
	public Automne(String nom, World w) {
		super(nom, 91, w);
		_myworld.setPobaRep(World.getProbaRep()*50);
		_myworld.setProbaInc(World.getProbaInc()/5);
		probaPluie = 0.5;
		checkProbaInc(0.0000001, 0.0001, 0.00001);
		checkProbaRep(0.000001, 0.001, 0.0001);
	}
	
	public void jourSuivant() throws IndexOutOfBoundsException{
		nbJour++;
		if(day[nbJour-1].getJour()) {
			day[nbJour] = new Day(AUT_MAX, AUT_MIN, day[nbJour-1].getTemp(), AUT_N, AUT_J,false);
		}
		else {day[nbJour] = new Day(AUT_MAX, AUT_MIN, day[nbJour-1].getTemp(), AUT_N, AUT_J,true);
		}
		
	}
}