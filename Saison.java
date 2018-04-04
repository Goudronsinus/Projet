package climat;

public class Saison {
	public static final String [] saisons = {"Printemps","Ete","Automne","Hiver"};
	private int  nbJour; 
	private String nomSaison;
	private Day day;
	
	public Saison(String nomS) {
		nomSaison = nomS;
		nbJour = 1;
		day = new Day();
	}
	
	/*public void jourSuivant() {
		day.setJour();
		if(day.getJour())
			nbJour++;
	}*/
	
	public int getIndexSaison() {
		int cpt = 0;
		while( ! nomSaison.equals(saisons[cpt])) 
			cpt++;
		return cpt;
	}
	public void setSaison() {
		day.setJour();
		if(day.getJour())
			nbJour++;
		if(nbJour > 50) {
			nomSaison = saisons[(getIndexSaison() + 1) % saisons.length];
			nbJour = 0;
		}
	}
		
	public int getNbJour() {
		return nbJour;
	}
	
	public Day getDay() {
		return day;
	}
	
	public String getNomSaison() {
		return nomSaison;
	}
}
