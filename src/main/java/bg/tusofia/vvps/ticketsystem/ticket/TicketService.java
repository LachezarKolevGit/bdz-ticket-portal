package bg.tusofia.vvps.ticketsystem.ticket;

import bg.tusofia.vvps.ticketsystem.traincarriage.seat.SeatService;
import java.time.LocalDate;
import java.time.LocalTime;

import bg.tusofia.vvps.ticketsystem.client.Client;
import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.train.TrainService;

@Service
public class TicketService {

	private static int PRICE = 50;
	private Client client;
	private LocalTime currentTime ;  
	private SeatService seatService;
	private TrainService trainService;
 	public final static double DIESEL_PRICE = 2.80;

	public TicketService(Client client , LocalTime currentTime, SeatService seatService) {
			this.client = client;
			this.currentTime =currentTime ;
			this.seatService = seatService;
	}
	
	public double calculatePrice(Train train , int numberOfTickets) {
		/**
		 * the price will be calculated based on the km of the route and the class of the
		 */
		LocalTime morningPeakHourStart = LocalTime.of(7, 30);
		LocalTime morningPeakHourEnd = LocalTime.of(9, 30);
		
		LocalTime eveningPeakHourStart = LocalTime.of(16, 0);
		LocalTime eveningPeakHourEnd = LocalTime.of(19, 30);	

		//trainService.calculateDistance();
		
		if(LocalDate.now().getYear() - client.getChildBirthYear().getYear() < 16) {
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

	public void reserveSeat(Long seatId){
		seatService.changeSeatState(seatId);


	}
}
