package climat;

import java.sql.Time;

public class Day {
	private long heureDebut; 
	/*private long TimeDebutJour;*/
	private long TimeDebutNuit;
	private boolean jour;
	private double temperature;
	private double periodeNuit;
	private double periodeJour;
	public static final int HEUREJOURNEE = 24*60*10; 
	
	
	public Day(double TMax, double TMin, double THier, double nuit, double pJour) {
		/*TimeDebutJour = System.currentTimeMillis();*/
		
		heureDebut = System.currentTimeMillis();
		if(Math.random() < 0.5) {
			jour = true;
			TimeDebutNuit = 0;
		}
		else { 
			jour = false;
			TimeDebutNuit = System.currentTimeMillis();
		}
		
		do {
			temperature = THier + ((int) (Math.random()*150)*(-1))*Math.random()*5;
		}while(temperature>TMax || temperature < TMin);
		periodeJour = pJour/100;
		periodeNuit = nuit/100;
		System.out.println(" PériodeJour = " +periodeJour + " Période Nuit = " + periodeNuit );
	}
	public Day(double TMax, double TMin, double THier, double nuit, double pjour, boolean jn) {
		heureDebut = System.currentTimeMillis();
		jour = jn;
		do {
			temperature = THier + ((int) (Math.random()*150)*(-1))*Math.random()*5;
		}while(temperature>TMax || temperature < TMin);
		periodeJour = pjour/100;
		periodeNuit = nuit/100;
	}
	public long dayTime() {
		if (jour)
			return ((System.currentTimeMillis() - heureDebut)%HEUREJOURNEE);
		else
			return((System.currentTimeMillis() - TimeDebutNuit)%HEUREJOURNEE);
	}
	public long getHeure() {
		return heureDebut;
	}
	
	public boolean setJour() {
		boolean change = false;
		if ((jour) &&  ((heureDebut )% HEUREJOURNEE >= periodeJour) ){
			jour = !jour;
			System.out.println("############################################# Nuit #########################################");
			
			TimeDebutNuit = System.currentTimeMillis();
			/*TimeDebutJour = 0;*/
		}
		
		else if( (!jour) && (((System.currentTimeMillis() - TimeDebutNuit )% HEUREJOURNEE )>= periodeNuit) ){
			jour = !jour;
			System.out.println("############################################# Jour #########################################");
			/*TimeDebutJour = System.currentTimeMillis();
			TimeDebutNuit = 0;*/
			heureDebut = System.currentTimeMillis();
			change = true;
		}		
		return change;
	}
	public boolean getJour() {
		return jour;
		}
	public double getTemp() {
		return temperature;
	}
	
	public void setTemperature(double temp) {
		temperature = temp;
	}
	/*public long getTJour() {
		return TimeDebutJour;
	}
	public long getTNuit() {
		return TimeDebutNuit;
	}*/
}