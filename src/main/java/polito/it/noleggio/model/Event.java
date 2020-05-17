package polito.it.noleggio.model;

import java.time.LocalTime;

public class Event implements Comparable<Event>{
	// classe enumerazione che definisce una serie di costanti
	public enum EventType {
		//specifico gli eventi che stiamo modellando
		// invece delle costanti numeriche uso delle costanti simboliche
		// in realtà è come se stessimo utilizzando numeri interi
		NEW_CLIENT, CAR_RETURNED
	}
	
	private LocalTime time;
	private EventType type;
	//potrei avere informazioni aggiuntive ma in questo caso no
	public Event(LocalTime time, EventType type) {
		super();
		this.time = time;
		this.type = type;
	}
	public LocalTime getTime() {
		return time;
	}
	public void setTime(LocalTime time) {
		this.time = time;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	
	
	//definisco qual è l'ordine degli eventi
	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.time);	
	}
	@Override
	public String toString() {
		return "Event [time=" + time + ", type=" + type + "]";
	}
	

}
