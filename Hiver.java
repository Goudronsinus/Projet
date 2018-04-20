package climat;

import applications.simpleworld.MyEcosystem;
import worlds.World;

public class Hiver extends Saison {
	public final double HIV_MAX = 7;
	public final double HIV_MOY = 4.125;
	public final double HIV_MIN = 2;
	public final double HIV_N = (14*60+24)*1000;
	public final double HIV_J = (24*60*1000) - HIV_N;
	
	public Hiver(String nomS, World w) {
		super(nomS, 90, w);
		_myworld.setPobaRep(World.getProbaRep()/10);
		_myworld.setProbaInc(World.getProbaInc()/50);
		probaPluie = 0.48;
		day[0] = new Day(HIV_MAX, HIV_MIN, HIV_MOY, HIV_N, HIV_J);
		checkProbaInc(0.0000001, 0.0001, 0.00001);
		checkProbaRep(0.000001, 0.001, 0.0001);
		
		}

	@Override
	public void jourSuivant() throws IndexOutOfBoundsException{
		nbJour++;
		if(day[nbJour-1].getJour()) {
			day[nbJour] = new Day(HIV_MAX, HIV_MIN, day[nbJour-1].getTemp(), HIV_N, HIV_J,false);
		}
		else {day[nbJour] = new Day(HIV_MAX, HIV_MIN, day[nbJour-1].getTemp(), HIV_N, HIV_J,true);}
		
	}
}