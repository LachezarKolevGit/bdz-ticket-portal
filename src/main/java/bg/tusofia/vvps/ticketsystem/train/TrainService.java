package bg.tusofia.vvps.ticketsystem.train;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainService {

    private final Map<String, List<Train>> hashmap = new HashMap<>();

    public Train searchForTrain(String destination, LocalTime departingTime) {
       List<Train> trainList = hashmap.get("Sofia");

        return null;
    }

}
