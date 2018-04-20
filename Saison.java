package climat;
import worlds.*;

public abstract class Saison {
	protected long heure;
	protected int nbJour;
	protected Day[] day;
	protected String nomSaison;
	protected boolean chaud;
	protected int forceVent;
	protected double probaPluie;
	protected World _myworld;
	
	public Saison(String nomS, int nbJourSaison, World myWorld) {
		nomSaison = nomS;
		day = new Day[nbJourSaison];
		_myworld = myWorld;
		heure = System.currentTimeMillis();
	}
	
	public abstract void jourSuivant() throws IndexOutOfBoundsException;
	
public  void stepSaison() {
		
		try {
			day[nbJour].setJour();
			if((System.currentTimeMillis()- getToday().getHeure()) > Day.HEUREJOURNEE) {
				jourSuivant();
				heure = System.currentTimeMillis();
			}
		}catch(IndexOutOfBoundsException iooe) {
			System.out.println("On change de saison");
			switch (_myworld.getIndexSais() ) {
			case 0 :
				_myworld.setSais(new Printemps("Printemps", _myworld));
				break;
			case 1 :
				_myworld.setSais((new Ete("Été",_myworld)));
				break;
			case 2:
				_myworld.setSais((new Automne("Automne", _myworld)));
				break;
			case 3:
				_myworld.setSais(new Hiver("Hiver", _myworld));
				break;					
			}
			_myworld.setIndexSais();
			
		}
		
	}
		
	public int getNbJour() {
		return nbJour;
	}
	
	public Day getToday() {
		return day[nbJour];
	}
	public Day[] getSaisDay() {
		return day;
	}
	
	public String getNomSaison() {
		return nomSaison;
	}
	
	protected void checkProbaInc(double limSup, double limInf, double initInc) {
		if(_myworld.getProbaInc() > limSup) {
			_myworld.setProbaInc(initInc);
		}
		if(_myworld.getProbaInc() < limInf) {
			_myworld.setProbaInc(initInc);
		}
	}
	protected void checkProbaRep(double limSup, double limInf, double initInc) {
		if(_myworld.getProbaRep() > limSup) {
			_myworld.setProbaInc(initInc);
		}
		if(_myworld.getProbaRep() < limInf) {
			_myworld.setProbaInc(initInc);
		}
	}
}
