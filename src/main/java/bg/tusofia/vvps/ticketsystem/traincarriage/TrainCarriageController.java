package bg.tusofia.vvps.ticketsystem.traincarriage;

import bg.tusofia.vvps.ticketsystem.seat.Seat;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
@Controller
public class TrainCarriageController {
    private final TrainCarriageService trainCarriageService;

    public TrainCarriageController(TrainCarriageService trainCarriageService) {
        this.trainCarriageService = trainCarriageService;
    }

    @GetMapping("/train-carriage")
    public String createTrainPage(Model model) {
        model.addAttribute("trainCarriage", new TrainCarriageDTO());
        return "train_carriage/train_carriage_add_page";
    }

    @PostMapping("/train-carriage")
    public String createTrain(Model model, @ModelAttribute TrainCarriageDTO trainCarriageDTO) {
        System.out.println(trainCarriageDTO);
        Long newTrainCarriageId = trainCarriageService.saveNewTrainCarriage(trainCarriageDTO);
        model.addAttribute("newTrainCarriageId", newTrainCarriageId);

        return "train_carriage/train_carriage_added_successfully";
    }

    @GetMapping("/train-carriage/{id}")
    public String getTrainCarriage(Model model, @PathVariable(name = "id") String id) {
        TrainCarriage trainCarriage = trainCarriageService.getTrainCarriage(id);
        model.addAttribute("trainCarriage", trainCarriage);
        return "train_carriage/train_carriage";
    }

    @GetMapping("/train-carriage/seat/{id}")
    public Seat getSeat(@PathVariable(name = "id") String id) {
        return null;
    }

    @GetMapping("/train-carriages")
    public String getTrains(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        Page<TrainCarriage> trainCarriagePage = trainCarriageService.getAllTrainCarriages(page);
        model.addAttribute("trainCarriagePage", trainCarriagePage);

        int totalPages = trainCarriagePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "train_carriage/train_carriages";
    }


}
