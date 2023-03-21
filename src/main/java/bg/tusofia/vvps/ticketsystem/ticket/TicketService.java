package bg.tusofia.vvps.ticketsystem.ticket;

import java.time.LocalTime;

import bg.tusofia.vvps.ticketsystem.client.Client;
import bg.tusofia.vvps.ticketsystem.train.Train;


public class TicketService {

	private static int PRICE = 50; //have to be in Enum just for now
	private Client client;
	private LocalTime currentTime ;  
	
	public TicketService(Client client , LocalTime currentTime) {
			this.client = client;
			this.currentTime =currentTime ;
	}
	
	public double calculatePrice(Train train , int numberOfTickets) {
		
		LocalTime morningPeakHourStart = LocalTime.of(7, 30);
		LocalTime morningPeakHourEnd = LocalTime.of(9, 30);
		
		LocalTime eveningPeakHourStart = LocalTime.of(16, 00);
		LocalTime eveningPeakHourEnd = LocalTime.of(19, 30);		
		
		if(client.isHasFamilyWithChildBelow16() == true) {
			return PRICE * numberOfTickets * 50/100;
		}
		
		if(client.getAge() > 60) {
			double priceFirstTicket = (PRICE * 34)/100 ;
			return priceFirstTicket + (numberOfTickets - 1) * PRICE;
		}
		
		if(client.isHasFamily() == true) {
			return (numberOfTickets * PRICE * 10)/100;
		}		
		
		if(currentTime.isBefore(morningPeakHourStart) || currentTime.isAfter(morningPeakHourEnd)) {
	
		 double discount = PRICE * 0.05;
		 return (PRICE - discount) * numberOfTickets;
			 
		}
		
		if(currentTime.isBefore(eveningPeakHourStart) || currentTime.isAfter(eveningPeakHourEnd)) {
			
			double discount = PRICE * 0.05;
			return (PRICE - discount) * numberOfTickets;
		}
		
		return PRICE * numberOfTickets;
		
	}
}
