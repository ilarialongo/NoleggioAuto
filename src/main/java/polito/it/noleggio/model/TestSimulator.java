package polito.it.noleggio.model;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

public class TestSimulator {

	public static void main(String args[]) {
		Simulator sim = new Simulator();
		//imposto i parametri di simulazione
		sim.setNumCars(13) ;
		sim.setClientFrequency(Duration.of(10, ChronoUnit.MINUTES)) ;
		//simulo la giornata
		sim.run() ;
		//prendo le variabili chiave
		int totClients = sim.getClienti() ;
		int dissatisfied = sim.getInsoddisfatti() ;
		
		System.out.format("Arrived %d clients, %d were dissatisfied\n", 
				totClients, dissatisfied);
	}
	
}
