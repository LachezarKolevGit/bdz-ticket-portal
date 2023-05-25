package bg.tusofia.vvps.ticketsystem.train;

import bg.tusofia.vvps.ticketsystem.route.Route;
import bg.tusofia.vvps.ticketsystem.route.RouteService;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriage;
import bg.tusofia.vvps.ticketsystem.traincarriage.TrainCarriageService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class TrainController {
    private final TrainService trainService;
    private final RouteService routeService;
    private final TrainCarriageService trainCarriageService;

    @GetMapping("/train")
    public String createTrain(Model model) {
        List<Route> routes = routeService.getAllRoutes();
        List<TrainCarriage> trainCarriages = trainCarriageService.getAllTrainCarriages();
        model.addAttribute("trainCarriages", trainCarriages);
        model.addAttribute("routes", routes);
        model.addAttribute("train", new TrainDTO());
        return "train/train_add";
    }

    @PostMapping("/train")
    public String createTrain(Model model, @RequestBody TrainDTO trainDTO) {
        if (trainDTO == null) {
            throw new IllegalArgumentException("Train can't be null");
        }
        Long newTrainId = trainService.createTrain(trainDTO);
        model.addAttribute("newTrainId", newTrainId);

        return "train/train_added_successfully";
    }

    @GetMapping("/train/{id}")
    public String getTrainById(Model model, @PathVariable(name = "id") String id) {
       Train train = trainService.getTrainById(Long.valueOf(id));
        model.addAttribute("train", train);
        return "train/train_details";
    }

    @GetMapping("/trains")
    public String getTrains(Model model,
                            @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        Page<Train> trainPage = trainService.getAllTrains(page);
        model.addAttribute("trainPage", trainPage);

        int totalPages = trainPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "train/trains";
    }

    @GetMapping("/trains/search")
    public String getTrainByDestinationAndDepartureHour(Model model, @RequestParam(name = "destination") String destination,
                                                        @RequestParam(name = "departureDateTime") LocalDateTime departureDateTime) {
        List<Train> trains = trainService.getTrainByArrivalStationAndDepartureTime(destination, departureDateTime);
        model.addAttribute("trains", trains);

        return "train/trains_by_destination_and_date_time";
    }

    @PostMapping("/trains/assign-route")
    public void assignRoute(Model model, @RequestParam(name = "trainId") String trainId,
                            @RequestParam(name = "trainId") String routeId) {
        trainService.assignTrainToRoute(trainId, routeId);
        model.addAttribute(trainId);
        //redirect

    }
}
