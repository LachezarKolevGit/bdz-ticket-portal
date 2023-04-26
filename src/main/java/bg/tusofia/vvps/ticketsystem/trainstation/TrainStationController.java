package bg.tusofia.vvps.ticketsystem.trainstation;

import bg.tusofia.vvps.ticketsystem.train.TrainStationDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TrainStationController {

    private final TrainStationService trainStationService;

    public TrainStationController(TrainStationService trainStationService) {
        this.trainStationService = trainStationService;
    }

    @GetMapping("/train-stops")
    public String getAllTrainStations(Model model, @RequestParam("page") int page) {
        Page<TrainStation> trainStationsPage = trainStationService.getAllTrainStations(page);
        System.out.println(trainStationsPage.getContent());
        model.addAttribute("trainStationsPage", trainStationsPage);
        //trainStationsPage.getContent()

       int totalPages = trainStationsPage.getTotalPages();
       if(totalPages > 0){
           List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                   .boxed()
                   .collect(Collectors.toList());
           model.addAttribute("pageNumbers", pageNumbers);
       }

        return "train_station/train_stations";
    }

    @PostMapping("/train-stops")
    public String createNewTrainStation(@RequestBody TrainStationDTO trainStationDTO, Model model) {
        Long trainStationId = trainStationService.createNewTrainStation(trainStationDTO);
        model.addAttribute("newTrainStationId", trainStationId);

        return "train_station/successful_upload_train_station";
    }
}
