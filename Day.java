package climat;

public class Day {
	private long heureDebut; // on commence arbitrairement à 6h du matin 
								// On aura donc 13h de jour + 9h de nuit
	private long TimeDebutJour;
	private long TimeDebutNuit;
	private boolean jour;
	//private Saison saison;
	
	public Day() {
		heureDebut = System.currentTimeMillis();
		//saison = s;
		TimeDebutJour = heureDebut;
		TimeDebutNuit = 0;
		jour = true;
				
	}
	
	public long dayTime() {
		if (jour)
			return (TimeDebutJour - System.currentTimeMillis());
		else
			return(TimeDebutNuit - System.currentTimeMillis());
	}
	
	public void setJour() {
		if (jour &&   System.currentTimeMillis() - TimeDebutJour  >= 13*1000) {
			jour = false;
			TimeDebutNuit = System.currentTimeMillis();
			TimeDebutJour = 0;
		}
		else if( !jour && System.currentTimeMillis() - TimeDebutNuit >= 9*1000) {
			jour = true;
			TimeDebutJour = System.currentTimeMillis();
			TimeDebutNuit = 0;
		}		
	}
	public boolean getJour() {
		return jour;
	}
	/*public String getNomSaison() {
		return saison.getNomSaison();
	}*/
}
