package bg.tusofia.vvps.ticketsystem;

import bg.tusofia.vvps.ticketsystem.train.Train;
import bg.tusofia.vvps.ticketsystem.train.TrainService;
import bg.tusofia.vvps.ticketsystem.trainstation.TrainStation;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;

@SpringBootApplication
public class RailStationTicketSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RailStationTicketSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Train system running");
	/*	TrainStation sofiaTrainStation = new TrainStation("Sofia Central station", 42.712115, 23.321046);
		TrainStation varnaTrainStation = new TrainStation("Varna railway station", 43.198056, 27.912222);
		TrainStation plovdivTrainStation = new TrainStation("Plovdiv Central railway station", 42.134444, 24.741389);
		TrainStation burgasTrainStation = new TrainStation("Burgas Central railway station", 42.490833, 27.4725);

		List<TrainStation> stops = new ArrayList<>();
		stops.add(sofiaTrainStation);
		stops.add(varnaTrainStation);
		stops.add(plovdivTrainStation);
		Route route = new Route(stops);
		//TrainCarriage trainCarriage = new TrainCarriage();
		//Train train = new Train(CarriageType.CLASS_A, );
		trainService.getTrainByArrivalStation("Burgas");
		trainService.getTrainByArrivalTime(LocalTime.of(5,20));*/

	}
}
