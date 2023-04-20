package bg.tusofia.vvps.ticketsystem.client;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getLoggedInUser() { //rework !!!
        Client client = new Client("Gosho", 50 , true, LocalDate.of(1976,2,1));
        return client;
    }
}
