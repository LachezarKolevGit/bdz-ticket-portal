package bg.tusofia.vvps.ticketsystem.route;

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
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/routes")
    public String getRoutes(Model model, @RequestParam(name = "page") int page){
        Page<Route> routePage = routeService.getRoutes(page);
        model.addAttribute("routePage", routePage);

        int totalPages = routePage.getTotalPages();
        if(totalPages > 0){
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "route/routes";
    }

    @PostMapping("/routes/create")
    public String createRoutes(Model model, @RequestBody RouteDTO routeDTO){
       Long newRouteId = routeService.saveRoute(routeDTO);
        model.addAttribute("newRouteId", newRouteId);

        return "route/successful_creation_route";
    }
}
