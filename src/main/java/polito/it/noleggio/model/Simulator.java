package polito.it.noleggio.model;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.PriorityQueue;

import polito.it.noleggio.model.Event.EventType;

public class Simulator {
	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue= new PriorityQueue<>();
	
	//PARAMETRI DI SIMULAZIONE impostabili dall'esterno
	//imposto valori di default per evitare errori
	private int NC=10; //numero di macchine
	private Duration T_IN= Duration.of(10, ChronoUnit.MINUTES); // intervallo tra i clienti
	private final LocalTime oraApertura= LocalTime.of(8, 00);
	private final LocalTime oraChiusura= LocalTime.of(17, 00);
	
	//MODELLO DEL MONDO
	//numero di auto ancora disponibili nel deposito (tra 0 e NC)
	private int nAuto;
	
	//VALORI DA CALCOLARE
	private int clienti;
	private int insoddisfatti;

	// METODI PER IMPOSTARE I PARAMETRI
		public void setNumCars(int N) {
			this.NC=N;
		}
		public void setClientFrequency (Duration d) {
			this.T_IN=d;
		}
		
	// METODI PER RESTITUIRE I RISULTATI
	public int getClienti() {
		return clienti;
	}
	public int getInsoddisfatti() {
		return insoddisfatti;
	}
	
	
	
	//SIMULAZIONE VERA E PROPRIA
	public void run() {
		//PREPARAZIONE INIZIALE (mondo+coda eventi)
		//impostare a un valore sensato le variabili che compongono il modello del mondo
		//devo predisporre gli eventi che verranno simulati
		this.nAuto=NC; //all'inizio ho tutte le auto in deposito
		this.clienti= this.insoddisfatti=0;
		//pulisci sempre prima la coda
		this.queue.clear();
		LocalTime oraArrivoCliente=this.oraApertura; //alle 8 arriva un cliente
		do {
			Event e= new Event (oraArrivoCliente, EventType.NEW_CLIENT); //dico che alle 8 è arrivato un cliente
			oraArrivoCliente= oraArrivoCliente.plus(this.T_IN); //mi predispongo per il cliente successivo
			
		} while (oraArrivoCliente.isBefore(this.oraChiusura));
		
		
		//ESECUZIONE DEL CICLO DI SIMULAZIONE
		//basata su un ciclo che estrae ogni volta un evento dalla coda in ordine di tempo
		//ad ogni evento devo verificare se la coda è vuota o no
		//se non è vuota, ho ancora eventi da simulare
		while(!this.queue.isEmpty()) {
			Event e= this.queue.poll();
			System.out.println(e);
			processEvent(e);	
		}
		
	}
	
	
	
	private void processEvent(Event e) {
		switch (e.getType()) {
		case NEW_CLIENT:
			if (this.nAuto>0) {
				//cliente viene servito, auto noleggiata
				//1. aggiorno il modello del mondo
				this.nAuto--;
				//2. aggiorno i risultati della simulazione
				this.clienti++;
				//3. eventualmente inserire nuovi eventi (genera nuovi eventi)
				//restituisco l'auto ma quando?
				double num= Math.random(); // numero [0,1)
				Duration travel;
				if (num<1.0/3.0) 
					travel= Duration.of(1, ChronoUnit.HOURS);
				
				else if (num<2.0/3.0) 
					travel=Duration.of(2, ChronoUnit.HOURS);
				
				else 
					travel=Duration.of(3, ChronoUnit.HOURS);
				
				System.out.println(travel);
				Event nuovo= new Event(e.getTime().plus(travel),EventType.CAR_RETURNED);
				this.queue.add(nuovo);
			}
			else {
				//cliente insoddisfatto
				this.clienti++;
				this.insoddisfatti++;
			}
			break;
			
		case CAR_RETURNED:
			this.nAuto++;
			break;
		}
		
		
	}

}
