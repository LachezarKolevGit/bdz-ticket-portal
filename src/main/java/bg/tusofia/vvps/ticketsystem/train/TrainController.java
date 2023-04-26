package bg.tusofia.vvps.ticketsystem.train;

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
public class TrainController {

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @GetMapping("/trains")
    public String getTrains(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        Page<Train> trainPage = trainService.getAllTrains(page);
        System.out.println(trainPage.getContent());
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

    @PostMapping("/trains")
    public String createTrain(Model model, @RequestBody TrainDTO trainDTO) {
        if (trainDTO == null) {
            throw new IllegalArgumentException("Train can't be null");
        }
        System.out.println(trainDTO);
        Long newTrainId = trainService.createTrain(trainDTO);

        model.addAttribute("newTrainId", newTrainId);

        return "train/train_added_successfully";
    }

    @PostMapping("/trains/assign-route")
    public String createTrain(Model model, @RequestParam(name = "trainId") String trainId, @RequestParam(name = "trainId") String routeId) {
        trainService.assignTrainToRoute(trainId, routeId);



        return "train/train_added_successfully";
    }
}
