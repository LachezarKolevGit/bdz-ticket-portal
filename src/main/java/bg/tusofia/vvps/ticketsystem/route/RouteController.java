package bg.tusofia.vvps.ticketsystem.route;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/route")
    public String createRoute(Model model) {
        RouteDTO routeDTO = new RouteDTO(null, null);
        model.addAttribute("routeDTO", routeDTO);
        return "route/route_upload_page";
    }

    @PostMapping("/route")
    public String createRoutes(Model model, @ModelAttribute RouteDTO routeDTO) {
        Long newRouteId = routeService.createNewRoute(routeDTO);
        model.addAttribute("newRouteId", newRouteId);

        return "route/route_created_successfully";
    }

    @GetMapping("/route/{id}")
    public String getRoute(Model model, @PathVariable(name = "id") String id) {
        Route route = routeService.getRoute(Long.valueOf(id));
        model.addAttribute("route", route);

        return "route/route_train_stations";
    }

    @GetMapping("/routes")
    public String getRoutes(Model model, @RequestParam(name = "page", defaultValue = "0") int page) {
        Page<Route> routePage = routeService.getRoutes(page);
        model.addAttribute("routePage", routePage);

        int totalPages = routePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "route/routes";
    }
}
