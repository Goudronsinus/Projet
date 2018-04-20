package climat;
import worlds.*;

public class Ete extends Saison {
	public final double ETE_MAX = 24;
	public final double ETE_MOY = 18.125;
	public final double ETE_MIN = 12;
	public final double ETE_N = (9*60+8)*1000;
	public final double ETE_J = (24*60 *1000)- ETE_N;
	
	public Ete(String nomS, World w) {
		super(nomS, 93, w);
		probaPluie = 0.3612;
		_myworld.setPobaRep(World.getProbaRep()/10.);
		_myworld.setProbaInc(World.getProbaInc()*30.);
		day[0] = new Day(ETE_MAX, ETE_MIN, ETE_MOY, ETE_N, ETE_J);
		checkProbaInc(0.0000001, 0.0001, 0.00001);
		checkProbaRep(0.000001, 0.001, 0.0001);
	}

	@Override
	public void jourSuivant() throws IndexOutOfBoundsException {
		nbJour++;
		if(day[nbJour-1].getJour()) {
			day[nbJour] = new Day(ETE_MAX, ETE_MIN, day[nbJour-1].getTemp(), ETE_N, ETE_J,false);
		}
		else {day[nbJour] = new Day(ETE_MAX, ETE_MIN, day[nbJour-1].getTemp(), ETE_N, ETE_J,true);}
		
		
	}


}
