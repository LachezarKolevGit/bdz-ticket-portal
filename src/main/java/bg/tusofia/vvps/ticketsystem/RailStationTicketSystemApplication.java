package bg.tusofia.vvps.ticketsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RailStationTicketSystemApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RailStationTicketSystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("running");
	}
}
