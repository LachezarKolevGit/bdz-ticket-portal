package bg.tusofia.vvps.ticketsystem;

import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.train.TrainService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;

@SpringBootApplication
public class RailStationTicketSystemApplication implements CommandLineRunner {
	TrainService trainService;

	public RailStationTicketSystemApplication(TrainService trainService) {
		this.trainService = trainService;
	}

	public static void main(String[] args) {
		SpringApplication.run(RailStationTicketSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Train system running");
		TrainCarriage trainCarriage = new TrainCarriage();
		Train train = new Train(CarriageType.CLASS_A, );
		trainService.getTrainByArrivalStation("Burgas");
		trainService.getTrainByArrivalTime(LocalTime.of(5,20));

	}
}
